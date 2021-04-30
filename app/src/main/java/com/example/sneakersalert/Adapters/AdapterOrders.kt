package com.example.sneakersalert.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.DataClasses.Order
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.card_order.view.*
import java.util.*

class AdapterOrders(private var l: ArrayList<Order>) :
    RecyclerView.Adapter<AdapterOrders.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_order, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            itemView.order_number.text = Calendar.getInstance().get(Calendar.YEAR)
                .toString() + "-" + Global.orderNumber.toString()
            itemView.order_date.text = l[position].date.toString()
            itemView.order_price.text =
                "€" + l[position].price.toLong().toString().format("%.2f", l[position].price)
            itemView.order_person.text = l[position].size
            itemView.order_status.text = l[position].status
            if (position == 0)
                itemView.card.radius = 20.dpToPixels(itemView.context)
            if (position == l.size - 1)
                itemView.card.radius = 20.dpToPixels(itemView.context)
            itemView.order_status.setOnClickListener {
                findNavController(this.itemView).navigate(R.id.nav_invoice)
            }
        }

        Global.orderNumber++
    }

    override fun getItemCount(): Int {
        return l.size
    }

    fun Int.dpToPixels(context: Context): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    )

}