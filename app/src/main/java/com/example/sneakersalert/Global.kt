package com.example.sneakersalert

import android.app.Application
import androidx.fragment.app.Fragment
import com.example.sneakersalert.DataClasses.Order
import com.example.sneakersalert.DataClasses.ProductCart
import com.example.sneakersalert.DataClasses.ShoeIn
import com.example.sneakersalert.DataClasses.Spec
import com.example.sneakersalert.UI.Airmax1Fragment
import com.example.sneakersalert.UI.Airmax90Fragment
import com.example.sneakersalert.UI.JordanFragment

import drawable.NewShoe
import kotlin.properties.Delegates

class Global : Application() {
    companion object {
        var positionSelected = 0
        var tabsAdded = false
        var p = ArrayList<ProductCart>()
        var w = ArrayList<NewShoe>()
        var wish = ArrayList<String>()
        val fragmentList = arrayListOf<Fragment>(
            Airmax1Fragment(), Airmax90Fragment(),
            JordanFragment()
        )

        //NewShoe
        var pic by Delegates.notNull<Int>()
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

        init {
            orderNumber = 0
        }
    }

    enum class Login {
        LOGGED, NOTLOGGED
    }


}