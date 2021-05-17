package com.example.sneakersalert.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.DataClasses.ProductCart
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.card_product_in_cart.view.*


class AdapterProductCart(
    private var l: ArrayList<ProductCart>,
    price_total: TextView?,
    price_final: TextView?,
    your_cart: TextView?
) : RecyclerView.Adapter<AdapterProductCart.ViewHolder>() {
    var price: TextView? = null
    var final: TextView? = null
    var your: TextView? = null


    init {
        this.price = price_total
        this.final = price_final
        this.your = your_cart

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterProductCart.ViewHolder {
        val view = LayoutInflater.from(parent.context.applicationContext).inflate(
            R.layout.card_product_in_cart,
            parent,
            false
        )
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AdapterProductCart.ViewHolder, position: Int) {
        holder.apply {
            itemView.product_image.setImageResource(l[position].image)
            itemView.title_product.text = l[position].name
            itemView.model_product.text = l[position].model
            itemView.price_product.text = l[position].price.toString()
            itemView.amount_products.text = l[position].amount.toString()
            itemView.size_number.text = l[position].size.toString()
            var c = l[position].amount
            var pr = itemView.price_product.text.toString().toInt()
            itemView.plus.setOnClickListener {
                c += 1
                itemView.amount_products.text = c.toString()
                l[position].amount = c
                Global.total += pr
                pr += pr
                price?.text = Global.total.toString()
                final?.text = price?.text
                notifyDataSetChanged()
            }
            itemView.minus.setOnClickListener {
                if (c > 1) {
                    c -= 1
                    Global.total -= pr
                    pr -= pr
                    itemView.amount_products.text = c.toString()
                    l[position].amount = c
                    notifyDataSetChanged()
                    price?.text = Global.total.toString()
                    final?.text = price?.text

                }
            }
            itemView.findViewById<TextView>(R.id.remove_product).setOnClickListener {
                Global.total -= (l[position].amount * l[position].price)
                price?.text = Global.total.toString()
                final?.text = price?.text
                l.removeAt(position)
                your?.text = "Your Cart(" + l.size.toString() + ")"
                notifyDataSetChanged()
            }


        }
    }
    override fun getItemCount(): Int {
        return l.size
    }

}