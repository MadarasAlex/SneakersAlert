package com.example.sneakersalert.ui.sneakers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterJordan
import com.example.sneakersalert.R
import drawable.NewShoe

class SneakersFragment : Fragment(R.layout.fragment_sneakers) {
    val s = ArrayList<NewShoe>()
    override fun onCreate(savedInstanceState: Bundle?) {
        s.add(NewShoe(R.drawable.air_max_london, "Nike air max 1", "LONDON",289))
        s.add(NewShoe(R.drawable.air_max_1_have_a_nikeday, "Nike air max 1", "Have A Nike Day",149))
        s.add(NewShoe(R.drawable.limeade, "Nike air max 1", "Limeade",209 ))
        s.add(NewShoe(R.drawable.jordan_canyon, "Jordan 1 mid", "\" Canyon Rust\"", 230))
        s.add(NewShoe(R.drawable.jordan_particle, "Jordan 1 mid", "SE Particle Beige", 210))
        s.add(NewShoe(R.drawable.jordan_yellow, "Jordan 1 mid", "SE Voltage Yellow", 170))
        s.add(NewShoe(R.drawable.airmax_90_crock, "Nike airmax 90", "Croc", 190))
        s.add(NewShoe(R.drawable.dance_floor_green, "Nike airmax 90", "90S Dancefloor Green", 190))
        s.add(NewShoe(R.drawable.duck_camo, "Nike airmax 90", "Duck Camo Orange", 140))


        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView=view.findViewById<RecyclerView>(R.id.recyclerViewSneakers)
        recyclerView.layoutManager= GridLayoutManager(this.context?.applicationContext,2)
        recyclerView.isNestedScrollingEnabled=false
        recyclerView.adapter= AdapterJordan(s,object: AdapterJordan.OnClickListener{
            override fun onItemClick(position: Int) {

            }

        })


        if (recyclerView.itemDecorationCount == 0)
        {
            println("Empty")
        }
        println(s.size)
        super.onViewCreated(view, savedInstanceState)
    }
}