package com.example.sneakersalert.ui.account

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakersalert.Adapters.AdapterOrders
import com.example.sneakersalert.DataClasses.Order
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_order_details.*
import java.util.*
import kotlin.collections.ArrayList


class OrderDetails : Fragment() {

    val o = ArrayList<Order>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        o.add(Order(Global.orderNumber, Date(2021 / 1 / 20), "A.M.", 150.00, "VIEW"))
        o.add(Order(Global.orderNumber, Date(2021 / 2 / 2), "A.M.", 250.00, "VIEW"))
        o.add(Order(Global.orderNumber, Date(2021 / 3 / 20), "A.M.", 150.00, "VIEW"))
        o.add(Order(Global.orderNumber, Date(2021 / 4 / 4), "A.M.", 50.00, "VIEW"))
        o.add(Order(Global.orderNumber, Date(2021 / 4 / 20), "A.M.", 550.00, "VIEW"))
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_details, container, false)
    }

    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val colorDrawable = ColorDrawable(Color.WHITE)
        activity?.actionBar?.setBackgroundDrawable(colorDrawable)


        if (!requireActivity().navigationView.menu.findItem(R.id.nav_orders).isChecked) {
            requireActivity().navigationView.menu.setGroupCheckable(R.id.gr, true, false)
            requireActivity().navigationView.menu.setGroupCheckable(R.id.nav_orders, true, false)
            requireActivity().navigationView.menu.getItem(2).isCheckable = true
            requireActivity().navigationView.menu.getItem(2).isChecked = true
            requireActivity().toolbar.setBackgroundColor(Color.WHITE)
            requireActivity().tback.setBackgroundColor(Color.WHITE)
            requireActivity().logo.setImageDrawable(resources.getDrawable(R.drawable.logo, null))
            requireActivity().actionBar?.setHomeAsUpIndicator(R.drawable.short_text_black24px)
        }
        recyclerViewOrders.layoutManager = LinearLayoutManager(activity)
        recyclerViewOrders.adapter = AdapterOrders(o)

        save_address.setOnClickListener {
            findNavController().navigate(R.id.nav_orders)
        }
        change_1.setOnClickListener {
            findNavController().navigate(R.id.nav_fill)
        }
        change_2.setOnClickListener {
            findNavController().navigate(R.id.nav_fillAddress)
        }
        change_3.setOnClickListener {
            findNavController().navigate(R.id.nav_fillInvoice)
        }

    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderDetails().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val colorDrawable = ColorDrawable(Color.WHITE)
        activity?.actionBar?.setBackgroundDrawable(colorDrawable)
    }
}