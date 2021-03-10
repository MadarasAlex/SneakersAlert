package com.example.sneakersalert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterJordan
import drawable.NewShoe
import kotlinx.android.synthetic.main.activity_cart.*

class WishlistFragment : Fragment(R.layout.fragment_wishlist) {
    val w=ArrayList<NewShoe>()
    override fun onCreate(savedInstanceState: Bundle?) {
       
            w.add(NewShoe(R.drawable.air_max_london, "Nike air max 1", "London", 289))
            w.add(NewShoe(R.drawable.jordan_particle, "Jordan 1 mid", "SE Particle Beige", 210))
            w.add(NewShoe(0, "", "",0 ))
            w.add(NewShoe(0, "", "",0 ))
            w.add(NewShoe(0, "", "",0 ))
            w.add(NewShoe(0, "", "",0 ))
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView=view.findViewById<RecyclerView>(R.id.recyclerViewWish)
        recyclerView.layoutManager= GridLayoutManager(context,2)
        recyclerView.isNestedScrollingEnabled=false
        recyclerView.adapter= AdapterJordan(w,object: AdapterJordan.OnClickListener{
            override fun onItemClick(position: Int) {

            }

        })


        super.onViewCreated(view, savedInstanceState)
    }

}