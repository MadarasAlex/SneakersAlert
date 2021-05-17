package com.example.sneakersalert.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.DataClasses.NewShoe
import com.example.sneakersalert.R
import java.util.*
import kotlin.collections.ArrayList

var search = ArrayList<NewShoe>()

class AdapterSearch(private var li: ArrayList<NewShoe>, val onClickListener: OnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    lateinit var mcontext: Context

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    class ShoeHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        search = li
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val searchView = LayoutInflater.from(parent.context.applicationContext)
            .inflate(R.layout.card_jordan, parent, false)
        val sch = ShoeHolder(searchView)
        mcontext = parent.context
        return sch
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.apply {
            search[position].image?.let {
                itemView.findViewById<ImageView>(R.id.jordan_image).setImageResource(it)
            }
            itemView.findViewById<TextView>(R.id.jordan_name).text = search[position].name
            itemView.findViewById<TextView>(R.id.jordan_model).text = search[position].model
            itemView.findViewById<TextView>(R.id.price_jordan).text =
                search[position].price.toString()
        }
        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }
        holder.adapterPosition
    }
    override fun getItemCount(): Int {
        return search.size
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint.toString()

                if (queryString.isEmpty())
                    search = li
                else {
                    val resultList = ArrayList<NewShoe>()
                    for (it in li) {
                        if (it.name.toLowerCase(Locale.ROOT)
                                .contains(queryString.toLowerCase()) || it.price.toString()
                                .contains(queryString.toLowerCase()) ||
                            it.model.toLowerCase(Locale.ROOT)
                                .contains(queryString.toLowerCase())
                        )
                            resultList.add(it)
                    }
                    search = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = search
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                search = filterResults.values as ArrayList<NewShoe>
                notifyDataSetChanged()
            }
        }
    }
}

