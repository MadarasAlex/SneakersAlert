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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_jordan.view.*
import java.util.*
import kotlin.collections.ArrayList



class AdapterSearch(private var li: ArrayList<NewShoe>, private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    var search = ArrayList<NewShoe>()
    private lateinit var mcontext: Context

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
            Picasso.get().load(search[position].image).resize(600,400).into(holder.itemView.jordan_image)
            itemView.findViewById<TextView>(R.id.jordan_name).text = search[position].name
            itemView.findViewById<TextView>(R.id.jordan_model).text = search[position].model
            itemView.findViewById<TextView>(R.id.price_jordan).text =
                search[position].price.toString()
        }
        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }
        holder.adapterPosition
        holder.setIsRecyclable(false)
    }
    override fun getItemCount(): Int {
        return search.size
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint.toString()

                search = if (queryString.isEmpty())
                    li
                else {
                    val resultList = ArrayList<NewShoe>()
                    for (it in li) {
                        if (it.name.toLowerCase(Locale.ROOT)
                                .contains(queryString.toLowerCase(Locale.ROOT)) || it.price.toString()
                                .contains(queryString.toLowerCase(Locale.ROOT)) ||
                            it.model.toLowerCase(Locale.ROOT)
                                .contains(queryString.toLowerCase(Locale.ROOT))
                        )
                            resultList.add(it)
                    }
                    resultList
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

