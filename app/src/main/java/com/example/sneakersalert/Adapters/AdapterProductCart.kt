package com.example.sneakersalert.Adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.DataClasses.ProductCart
import com.example.sneakersalert.DataClasses.ShoeIn
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_product_in_cart.view.*


class AdapterProductCart(
    private var l: ArrayList<ProductCart>,
    price_total: TextView?,
    price_final: TextView?,
    your_cart: TextView?,
    count_items: TextView?
) : RecyclerView.Adapter<AdapterProductCart.ViewHolder>() {
    var price: TextView? = null
    var final: TextView? = null
    var your: TextView? = null
    var count: TextView? = null

    init {
        this.price = price_total
        this.final = price_final
        this.your = your_cart
        this.count = count_items
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
            Picasso.get().load(l[position].image).into(holder.itemView.product_image)
            itemView.title_product.text = l[position].name
            itemView.model_product.text = l[position].model
            itemView.price_product.text = l[position].price.toString()
            itemView.amount_products.text = l[position].amount.toString()
            itemView.size_number.text = l[position].size.toString()
            var c = l[position].amount
            var pr = itemView.price_product.text.toString().toInt()
            if (c == 1)
                itemView.minus.setTextColor(Color.GRAY)
            else itemView.minus.setTextColor(Color.BLACK)
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
                    itemView.minus.setTextColor(Color.BLACK)
                    c -= 1
                    Global.total -= pr
                    pr -= pr
                    itemView.amount_products.text = c.toString()
                    l[position].amount = c
                    notifyDataSetChanged()
                    price?.text = Global.total.toString()
                    final?.text = price?.text


                } else if (c == 1)
                    itemView.minus.setTextColor(Color.GRAY)
            }
            itemView.findViewById<TextView>(R.id.remove_product).setOnClickListener {

                val shoe = ShoeIn(
                    l[position].image,
                    l[position].name,
                    l[position].model,
                    l[position].price,
                    l[position].size
                )
                Global.total -= (l[position].amount * l[position].price)
                l.removeAt(position)
                Global.p = l
                Global.list.remove(shoe)
                price?.text = Global.total.toString()
                final?.text = price?.text
                your?.text = "Your Cart(" + l.size.toString() + ")"
                count?.text = l.size.toString()
                notifyDataSetChanged()
            }


        }
    }
    override fun getItemCount(): Int {
        return l.size
    }

}