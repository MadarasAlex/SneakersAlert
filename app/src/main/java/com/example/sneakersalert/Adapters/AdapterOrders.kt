package com.example.sneakersalert.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.DataClasses.Order
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.card_order.view.*

class AdapterOrders(private var l: ArrayList<Order>) :
    RecyclerView.Adapter<AdapterOrders.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            itemView.order_number.text = Global.orderNumber.toString()
            itemView.order_date.text = l[position].date.toString()
            itemView.order_price.text = l[position].price.toString()
            itemView.order_size.text = l[position].size
            itemView.order_status.text = l[position].status
        }
        Global.orderNumber++
    }

    override fun getItemCount(): Int {
        return l.size
    }
}