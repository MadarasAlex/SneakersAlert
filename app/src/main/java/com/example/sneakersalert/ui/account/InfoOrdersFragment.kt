package com.example.sneakersalert.ui.account

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.Adapters.AdapterOrders
import com.example.sneakersalert.Global
import com.example.sneakersalert.Global.Companion.logged
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_info_orders.*

class InfoOrdersFragment : Fragment(R.layout.fragment_info_orders), LifecycleOwner {
    lateinit var adapterOrders: AdapterOrders

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!logged) {
            findNavController().navigate(R.id.nav_menuLogin)
        } else Toast.makeText(this.context, "Welcome", Toast.LENGTH_SHORT).show()
        if (!requireActivity().navigationView.menu.findItem(R.id.nav_orders).isChecked) {
            requireActivity().navigationView.menu.setGroupCheckable(R.id.gr, true, false)
            requireActivity().navigationView.menu.setGroupCheckable(R.id.nav_orders, true, false)
            requireActivity().navigationView.menu.getItem(2).isCheckable = true
            requireActivity().navigationView.menu.getItem(2).isChecked = true

        }
        footer.text = "Your Cart (${Global.p.size})"
        edit_account.setOnClickListener {
            findNavController().navigate(R.id.nav_details)
        }
        footer.setOnClickListener {
            findNavController().navigate(R.id.nav_cart)
        }


    }

}


