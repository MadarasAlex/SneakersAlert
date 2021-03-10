package com.example.sneakersalert.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.DataClasses.ProductCart
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.card_product_in_cart.view.*

class AdapterProductCart(private var l:ArrayList<ProductCart>):
    RecyclerView.Adapter<AdapterProductCart.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterProductCart.ViewHolder {
        val view= LayoutInflater.from(parent.context.applicationContext).inflate(R.layout.card_product_in_cart,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterProductCart.ViewHolder, position: Int) {
        holder.apply {
            itemView.product_image.setImageResource(l[position].image)
            itemView.title_product.text=l[position].name
            itemView.model_product.text=l[position].model
            itemView.price_product.text=l[position].price.toString()
            itemView.amount_products.text=l[position].amount.toString()
            var c=l[position].amount
            var pr=itemView.price_product.text.toString().toInt()
            itemView.plus.setOnClickListener {
                c+=1
                itemView.amount_products.text= c.toString()
                l[position].amount=c
                pr=pr*2
                l[position].price*=2
            }
            if(c>1)
                itemView.minus.setOnClickListener {
                    c-=1
                    itemView.amount_products.text= c.toString()
                    l[position].amount=c

            }
        }
    }

    override fun getItemCount(): Int {
        return l.size
    }
}