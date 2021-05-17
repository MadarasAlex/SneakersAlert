package com.example.sneakersalert.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_payment_method.*

class PaymentMethodFragment : Fragment(R.layout.fragment_payment_method) {
    enum class options {
        PAYPAL, IDEAL, MASTERCARD, APPLEPAY
    }

    lateinit var choice: options

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_method, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signup_action2.setOnClickListener {
            findNavController().navigate(R.id.nav_orders)
        }
        add_button.setOnClickListener {
            findNavController().navigate(R.id.nav_fillAddress)
        }
        "€${Global.total}".also { total_price2.text = it }
        val shippingPrice: Double =
            price_shipping.text.filter { it.isDigit() }.toString().toDouble()
        if (checkBox.isChecked) {
            price_shipping.text = method_price.text
            ("€" + (Global.total + 6.95).toString()).also { price_final2.text = it }
        }
        if (!checkBox.isChecked) {
            ("€" + 0.toString()).also { price_shipping.text = it }
            ("€" + Global.total.toString()).also { price_final2.text = it }
        }
        checkBox.setOnClickListener {

            if (checkBox.isChecked) {
                checkBox.buttonDrawable =
                    resources.getDrawable(R.drawable.ic_baseline_radio_button_checked_24, null)
                price_shipping.text = method_price.text
                ("€" + (Global.total + 6.95).toString()).also { price_final2.text = it }
            }
            if (!checkBox.isChecked) {
                checkBox.buttonDrawable =
                    resources.getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24, null)
                ("€" + 0.toString()).also { price_shipping.text = it }
                ("€" + Global.total.toString()).also { price_final2.text = it }
            }
        }

        if (!requireActivity().navigationView.menu.findItem(R.id.nav_cart).isChecked) {
            requireActivity().navigationView.menu.setGroupCheckable(R.id.gr, true, false)
            requireActivity().navigationView.menu.setGroupCheckable(R.id.nav_cart, true, false)
            requireActivity().navigationView.menu.getItem(3).isCheckable = true
            requireActivity().navigationView.menu.getItem(3).isChecked = true

        }
        check_option1.setOnClickListener {
            check_option1.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_checked_24, null)
            check_option2.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24, null)
            check_option3.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24, null)
            check_option4.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24, null)
            check_option2.isChecked = !check_option1.isChecked
            check_option3.isChecked = !check_option1.isChecked
            check_option4.isChecked = !check_option1.isChecked
            choice = options.PAYPAL
        }
        check_option2.setOnClickListener {
            check_option2.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_checked_24, null)
            check_option1.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24, null)
            check_option3.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24, null)
            check_option4.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24, null)
            check_option1.isChecked = !check_option2.isChecked
            check_option3.isChecked = !check_option2.isChecked
            check_option4.isChecked = !check_option2.isChecked
            choice = options.IDEAL
        }
        check_option3.setOnClickListener {
            check_option3.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_checked_24, null)
            check_option2.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24, null)
            check_option1.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24, null)
            check_option4.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24, null)
            check_option2.isChecked = !check_option3.isChecked
            check_option1.isChecked = !check_option3.isChecked
            check_option4.isChecked = !check_option3.isChecked
            choice = options.MASTERCARD
        }
        check_option4.setOnClickListener {
            check_option4.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_checked_24, null)
            check_option2.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24, null)
            check_option3.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24, null)
            check_option1.buttonDrawable =
                resources.getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24, null)
            check_option2.isChecked = !check_option4.isChecked
            check_option3.isChecked = !check_option4.isChecked
            check_option1.isChecked = !check_option4.isChecked
            choice = options.APPLEPAY
        }


    }
}