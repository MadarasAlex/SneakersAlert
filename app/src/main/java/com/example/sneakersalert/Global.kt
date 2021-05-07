package com.example.sneakersalert

import android.app.Application
import androidx.fragment.app.Fragment
import com.example.sneakersalert.DataClasses.Order
import com.example.sneakersalert.DataClasses.ProductCart
import com.example.sneakersalert.DataClasses.ShoeIn
import com.example.sneakersalert.DataClasses.Spec
import com.example.sneakersalert.ui.Airmax1Fragment
import com.example.sneakersalert.ui.Airmax90Fragment
import com.example.sneakersalert.ui.JordanFragment
import drawable.NewShoe
import java.time.LocalDateTime
import kotlin.properties.Delegates

class Global : Application() {
    companion object {
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
        lateinit var dateOfOrder: LocalDateTime
        init {
            orderNumber = 0
        }
    }

    enum class Login {
        LOGGED, NOTLOGGED
    }


}