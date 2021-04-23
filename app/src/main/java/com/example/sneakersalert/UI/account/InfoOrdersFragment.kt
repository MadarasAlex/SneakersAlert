package com.example.sneakersalert.UI.account

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakersalert.Adapters.AdapterOrders
import com.example.sneakersalert.DataClasses.Order
import com.example.sneakersalert.Global.Companion.logged
import com.example.sneakersalert.Global.Companion.o
import com.example.sneakersalert.Global.Companion.orderNumber
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.fragment_info_orders.*
import java.time.LocalDateTime

class InfoOrdersFragment : Fragment(R.layout.fragment_info_orders), LifecycleOwner {
    lateinit var adapterOrders: AdapterOrders

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        o.add(Order(orderNumber, LocalDateTime.now(), "M", 150.00, "Sent"))
        o.add(Order(orderNumber, LocalDateTime.now(), "M", 150.00, "Sent"))
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!logged) {


        } else {
            submit2.setOnClickListener {
                logged = false
                println(logged)


            }
            recyclerViewOrders.layoutManager = LinearLayoutManager(activity)
            adapterOrders = AdapterOrders(o)
            recyclerViewOrders.adapter = adapterOrders
        }


    }

}


