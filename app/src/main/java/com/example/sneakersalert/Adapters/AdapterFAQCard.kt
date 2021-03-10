package com.example.sneakersalert.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.DataClasses.FAQCard
import com.example.sneakersalert.R

class AdapterFAQCard(private var l:ArrayList<FAQCard>):
    RecyclerView.Adapter<AdapterFAQCard.ViewHolder>()
{
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFAQCard.ViewHolder {
        val view= LayoutInflater.from(parent.context.applicationContext).inflate(R.layout.card_faq,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterFAQCard.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.faq_name).text=l[position].text
        holder.itemView.findViewById<ImageView>(R.id.arrow).setImageResource(l[position].image)
    }

    override fun getItemCount(): Int {
        return l.size
    }

}