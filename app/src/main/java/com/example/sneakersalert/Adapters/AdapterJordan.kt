package com.example.sneakersalert.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.DataClasses.NewShoe
import com.example.sneakersalert.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_jordan.view.*

class AdapterJordan(private var li: ArrayList<NewShoe>, val onClickListener: OnClickListener) :
    RecyclerView.Adapter<AdapterJordan.ViewHolder>() {
    private var search = ArrayList<NewShoe>(li)

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_jordan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            Picasso.get().load(li[position].image).resize(600,400).into(holder.itemView.jordan_image)
            itemView.findViewById<TextView>(R.id.jordan_name).text = li[position].name
            itemView.findViewById<TextView>(R.id.jordan_model).text=li[position].model
            itemView.findViewById<TextView>(R.id.price_jordan).text=li[position].price.toString()
            if(li[position].stock<=0)
            {
                li.removeAt(position)
            }
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

