package com.example.sneakersalert.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterProductCart
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import com.example.sneakersalert.ViewModels.CartViewModel
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.app_bar_main.*


class CartFragment : Fragment(R.layout.activity_cart), Parcelable {

    private var created = false

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println(Global.p)
        if (Global.p.size == 0) {
            empty.visibility = View.VISIBLE
            recyclerViewCart.visibility = View.GONE
            details.visibility = View.GONE

            continue_shopping.setOnClickListener {
                findNavController().navigate(R.id.nav_sneakers)
            }
        } else {
            empty.visibility = View.GONE
            recyclerViewCart.visibility = View.VISIBLE
            details.visibility = View.VISIBLE
            val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerViewCart)
            recyclerView.layoutManager = LinearLayoutManager(this.activity)
            recyclerView.isNestedScrollingEnabled = true
            recyclerView.adapter =
                AdapterProductCart(Global.p, price_total, price_final, requireActivity().your_cart)
            val adapter =
                AdapterProductCart(Global.p, price_total, price_final, requireActivity().your_cart)
            adapter.notifyDataSetChanged()
            recyclerView.setRecycledViewPool(object : RecyclerView.RecycledViewPool() {})
            recyclerView.recycledViewPool.setMaxRecycledViews(0, 0)
            val model: CartViewModel by viewModels()
            model.getAmount().observe(this.requireActivity(), { newAmount ->
                requireActivity().your_cart.text = "Your Cart ($newAmount)"
            })

            adapter.notifyDataSetChanged()
            model.getFinal().observe(this.requireActivity(), { newPrice ->
                price_final.text = newPrice.toString()
                price_total.text = newPrice.toString()
            })

            checkout.setOnClickListener {
                if (Global.p.size > 0)
                    findNavController().navigate(R.id.nav_payment)
                else Toast.makeText(activity, "Your cart is empty.", Toast.LENGTH_SHORT).show()
            }
            super.onViewCreated(view, savedInstanceState)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)


    override fun onStart() {
        super.onStart()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (created) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartFragment> {
        override fun createFromParcel(parcel: Parcel): CartFragment {
            return createFromParcel(parcel)
        }

        override fun newArray(size: Int): Array<CartFragment?> {
            return arrayOfNulls(size)
        }
    }


}