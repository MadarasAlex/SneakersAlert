package com.example.sneakersalert.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.R
import drawable.NewShoe

class AdapterJordan( private var li:ArrayList<NewShoe>,val onClickListener:OnClickListener):
    RecyclerView.Adapter<AdapterJordan.ViewHolder>()
    {
        private var search=ArrayList<NewShoe>(li)

        interface OnClickListener
        {
            fun onItemClick(position:Int)
        }
        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view= LayoutInflater.from(parent.context.applicationContext).inflate(R.layout.card_jordan, null)
            return ViewHolder(view)
        }


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.apply {
                li[position].image?.let {
                    itemView.findViewById<ImageView>(R.id.jordan_image).setImageResource(
                        it
                    )
                }
                itemView.findViewById<TextView>(R.id.jordan_name).text=li[position].name
                itemView.findViewById<TextView>(R.id.jordan_model).text=li[position].model
                itemView.findViewById<TextView>(R.id.price_jordan).text=li[position].price.toString()
            }
            holder.itemView.setOnClickListener {
                onClickListener.onItemClick(position)
            }

        }

        override fun getItemCount(): Int {
            return li.size
        }
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }





    }

