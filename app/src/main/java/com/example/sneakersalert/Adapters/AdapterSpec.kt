package layout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.DataClasses.Spec
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.card_spec.view.*

class AdapterSpec(private val l:ArrayList<Spec>):RecyclerView.Adapter<AdapterSpec.ViewHolder>()
{
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterSpec.ViewHolder {
        val view= LayoutInflater.from(parent.context.applicationContext).inflate(R.layout.card_spec, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterSpec.ViewHolder, position: Int) {
        holder.apply {
            itemView.text_spec.text=l[position].text
            l[position].pic?.let { itemView.logo_spec.setImageResource(it) }
        }
    }

    override fun getItemCount(): Int {
        return l.size
    }
}