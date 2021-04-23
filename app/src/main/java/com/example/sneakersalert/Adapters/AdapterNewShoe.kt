package drawable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.DataClasses.ShoeType
import com.example.sneakersalert.R

class AdapterNewShoe(private var list: ArrayList<ShoeType>, val onClickListener: OnClickListener) :
    RecyclerView.Adapter<AdapterNewShoe.ViewHolder>() {
    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_new_shoes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            list[position].image.let {
                itemView.findViewById<ImageView>(R.id.picture).setImageResource(
                    it
                )
            }
            itemView.findViewById<TextView>(R.id.name).text = list[position].name
            holder.itemView.setOnClickListener {
                onClickListener.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    }