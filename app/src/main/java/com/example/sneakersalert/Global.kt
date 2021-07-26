package com.example.sneakersalert

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.sneakersalert.DataClasses.*
import com.example.sneakersalert.ui.Airmax1Fragment
import com.example.sneakersalert.ui.Airmax90Fragment
import com.example.sneakersalert.ui.JordanFragment
import java.time.LocalDateTime
import kotlin.properties.Delegates

class Global : Application() {
    companion object {
        enum class options {
            PAYPAL, IDEAL, MASTERCARD, APPLEPAY
        }

        val id: Int=0
        var stock: Int=99
        var countryInv: String=""
        var extraInv: String=""
        var zipInv: String = ""
        var cityInv: String = ""
        var streetInv: String = ""
        var houseNumberInv: Int = 0
        var saved: Boolean = false
        var saved2: Boolean = false
        var saved3:Boolean=false
        var extra: String = ""
        var houseNumber: Int = 0
        var zip: String = ""
        var city: String = ""
        var street: String = ""
        var birthday: String = ""
        var type = "1"
        var phone: String = ""
        var firstName: String = ""
        var lastName: String = ""
        var companyName: String = ""
        var vat: String = ""
        var tax: String = ""
        lateinit var choice: options
        var country: String? = ""
        var shipping: Boolean = false
        var positionSelected = 0
        var sizeSelected = 0
        var username: String = "Your Name"
        var p = ArrayList<ProductCart>()
        var size = p.size
        var w = ArrayList<NewShoe>()
        val fragmentList = arrayListOf<Fragment>(
            Airmax1Fragment(), Airmax90Fragment(),
            JordanFragment()
        )

        //NewShoe
        lateinit var pic: String
        var sp = ArrayList<Spec>()
        var s = ArrayList<NewShoe>()
        var sizes = ArrayList<Int>()
        lateinit var name: String
        lateinit var model: String
        var price by Delegates.notNull<Int>()
        lateinit var infoText: String
        var logged: Boolean = false
        var total: Int = 0
        var list = ArrayList<ShoeIn>()
        var orderNumber by Delegates.notNull<Int>()
        var o = ArrayList<Order>()
        lateinit var dateOfOrder: LocalDateTime
        init {
            orderNumber = 0
        }
    }

    enum class Login {
        LOGGED, NOTLOGGED
    }


}