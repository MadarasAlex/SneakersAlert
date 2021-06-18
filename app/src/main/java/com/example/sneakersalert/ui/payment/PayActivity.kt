package com.example.sneakersalert.ui.payment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adyen.checkout.card.CardConfiguration
import com.adyen.checkout.core.api.Environment.TEST
import com.example.sneakersalert.BuildConfig
import com.example.sneakersalert.Global
import com.example.sneakersalert.Global.Companion.choice
import com.example.sneakersalert.Global.Companion.p
import com.example.sneakersalert.Global.Companion.shipping
import com.example.sneakersalert.Global.Companion.total
import com.example.sneakersalert.R
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.Order
import com.paypal.checkout.order.PurchaseUnit
import kotlinx.android.synthetic.main.activity_pay.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.math.roundToInt

class PayActivity : AppCompatActivity() {
    private val SHIPPING_COST_CENTS = 9 * PaymentsUtil.CENTS.toLong()

    private lateinit var paymentsClient: PaymentsClient
    private lateinit var garmentList: JSONArray
    private lateinit var selectedGarment: JSONObject

    /**
     * Arbitrarily-picked constant integer you define to track a request for payment data activity.
     *
     * @value #LOAD_PAYMENT_DATA_REQUEST_CODE
     */
    private val LOAD_PAYMENT_DATA_REQUEST_CODE = 991

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)
        val x = intent.getIntExtra("total", 0)
        println("SUM TOTAL: $x")
        if (p.size > 1) total_amount5.text = "Products(${Global.p.size} items):"
        else total_amount5.text = "Products(${Global.p.size} item):"
        total_price4.text = "€" + Global.total.toString()
        if (!shipping) total_price3.text = "FREE"
        else total_price3.text = "€" + 6.95
        total_price.text = "€" + Global.total.toString()
        if (choice == Global.Companion.options.PAYPAL) {
            payPalButton.visibility = View.VISIBLE
            paymentPayPal()
        }
        if (choice == Global.Companion.options.MASTERCARD) {
            payPalButton.visibility = View.INVISIBLE
            paymentsClient = PaymentsUtil.createPaymentsClient(this)
            possiblyShowGooglePayButton()
            googlePayButton.setOnClickListener { requestPayment() }
        }
    }

    private fun setGooglePayAvailable(available: Boolean) {
        if (available) {
            googlePayButton.visibility = View.VISIBLE
        } else {
            Toast.makeText(
                this,
                "Unfortunately, Google Pay is not available on this device",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun requestPayment() {

        // Disables the button to prevent multiple clicks.
        googlePayButton.isClickable = false

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        val garmentPrice = total.toDouble()
        val priceCents =
            (garmentPrice * PaymentsUtil.CENTS.toLong()).roundToInt() + SHIPPING_COST_CENTS

        val paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents)
        if (paymentDataRequestJson == null) {
            Log.e("RequestPayment", "Can't fetch payment data request")
            return
        }
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        AutoResolveHelper.resolveTask(
            paymentsClient.loadPaymentData(request), this, LOAD_PAYMENT_DATA_REQUEST_CODE
        )
    }

    fun paymentPayPal() {
        val x = intent.getIntExtra("total", 0)
        val YOUR_CLIENT_ID =
            "AZk3VRdt-M-efLyyXd_if1iLl6Rz5bBpv9oBW5BFMFL3xTUKx9we7RA2h32jnt8vDc4rCInOLb58W2gk"
        val config = CheckoutConfig(
            application = application,
            clientId = YOUR_CLIENT_ID,
            environment = Environment.SANDBOX,
            returnUrl = "${BuildConfig.APPLICATION_ID}://paypalpay",
            currencyCode = CurrencyCode.EUR,
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(
                loggingEnabled = true
            )
        )
        PayPalCheckout.setConfig(config)

        payPalButton.setup(
            createOrder = CreateOrder { createOrderActions ->
                val order = Order(
                    intent = OrderIntent.CAPTURE,
                    appContext = AppContext(
                        userAction = UserAction.PAY_NOW
                    ),
                    purchaseUnitList = listOf(
                        PurchaseUnit(
                            amount = Amount(
                                currencyCode = CurrencyCode.EUR,
                                value = "x"
                            )
                        )
                    )
                )
                createOrderActions.create(order)
            },
            onApprove = OnApprove { approval ->
                approval.orderActions.capture { captureOrderResult ->
                    Log.i("CaptureOrder", "CaptureOrderResult: $captureOrderResult")
                }
            }
        )
    }

    fun paymentIDEAL() {

        val cardConfiguration =
            CardConfiguration.Builder(applicationContext)
                // When you're ready to accept live payments, change the value to one of our live environments.
                .setEnvironment(TEST)
                // The public key from your Customer Area.
                .setPublicKey("YOUR_PUBLIC_KEY")
                // Optional. Use to set the language rendered in Component, overriding the default device language setting. See list of Supported languages at https://github.com/Adyen/adyen-android/tree/master/card-ui-core/src/main/res
                .setShopperLocale(Locale("nl", "NL"))
                // Create the configuration for the payment method that you want to add.
                .build()
    }

    private fun possiblyShowGooglePayButton() {

        val isReadyToPayJson = PaymentsUtil.isReadyToPayRequest() ?: return
        val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString()) ?: return

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        val task = paymentsClient.isReadyToPay(request)
        task.addOnCompleteListener { completedTask ->
            try {
                completedTask.getResult(ApiException::class.java).let(::setGooglePayAvailable)
            } catch (exception: ApiException) {
                // Process error
                Log.w("isReadyToPay failed", exception)
            }
        }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // Value passed in AutoResolveHelper
            LOAD_PAYMENT_DATA_REQUEST_CODE -> {
                when (resultCode) {
                    RESULT_OK ->
                        data?.let { intent ->
                            PaymentData.getFromIntent(intent)?.let(::handlePaymentSuccess)
                        }

                    RESULT_CANCELED -> {
                        // The user cancelled the payment attempt
                    }

                    AutoResolveHelper.RESULT_ERROR -> {
                        AutoResolveHelper.getStatusFromIntent(data)?.let {
                            handleError(it.statusCode)
                        }
                    }
                }

                // Re-enables the Google Pay payment button.
                googlePayButton.isClickable = true
            }
        }
    }

    private fun handlePaymentSuccess(paymentData: PaymentData) {
        val paymentInformation = paymentData.toJson() ?: return

        try {
            // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
            val paymentMethodData =
                JSONObject(paymentInformation).getJSONObject("paymentMethodData")
            val billingName = paymentMethodData.getJSONObject("info")
                .getJSONObject("billingAddress").getString("name")
            Log.d("BillingName", billingName)


            // Logging token string.
            Log.d(
                "GooglePaymentToken", paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("token")
            )

        } catch (e: JSONException) {
            Log.e("handlePaymentSuccess", "Error: " + e.toString())
        }

    }

    /**
     * At this stage, the user has already seen a popup informing them an error occurred. Normally,
     * only logging is required.
     *
     * @param statusCode will hold the value of any constant from CommonStatusCode or one of the
     * WalletConstants.ERROR_CODE_* constants.
     * @see [
     * Wallet Constants Library](https://developers.google.com/android/reference/com/google/android/gms/wallet/WalletConstants.constant-summary)
     */
    private fun handleError(statusCode: Int) {
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode))
    }
    /*   private fun fetchRandomGarment() : JSONObject {
           if (!::garmentList.isInitialized) {
               garmentList = Json.readFromResources(this, R.raw.tshirts)
           }

           val randomIndex:Int = Math.round(Math.random() * (garmentList.length() - 1)).toInt()
           return garmentList.getJSONObject(randomIndex)
       }

       private fun displayGarment(garment:JSONObject) {
           detailTitle.setText(garment.getString("title"))
           detailPrice.setText("\$${garment.getString("price")}")

           val escapedHtmlText:String = Html.fromHtml(garment.getString("description")).toString()
           detailDescription.setText(Html.fromHtml(escapedHtmlText))

           val imageUri = "@drawable/${garment.getString("image")}"
           val imageResource = resources.getIdentifier(imageUri, null, packageName)
           detailImage.setImageResource(imageResource)
       }

     */
}



