package com.example.sneakersalert.ui.airmax90

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterJordan
import com.example.sneakersalert.R
import drawable.NewShoe

class Airmax90Fragment : Fragment(R.layout.fragment_airmax90) {
    val a=ArrayList<NewShoe>()
    override fun onCreate(savedInstanceState: Bundle?) {
        a.add(NewShoe(R.drawable.airmax_90_crock, "Nike airmax 90", "Croc", 190))
        a.add(NewShoe(R.drawable.dance_floor_green, "Nike airmax 90", "90S Dancefloor Green", 190))
        a.add(NewShoe(R.drawable.duck_camo, "Nike airmax 90", "Duck Camo Orange", 140))
        a.add(NewShoe(0, "", "",0 ))
        a.add(NewShoe(0, "", "",0 ))
        a.add(NewShoe(0, "", "",0 ))
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val recyclerView=view.findViewById<RecyclerView>(R.id.recyclerViewAirmax90)
        recyclerView.adapter= AdapterJordan(a,object: AdapterJordan.OnClickListener{
            override fun onItemClick(position: Int)
            {

            }

        })
        recyclerView.layoutManager= GridLayoutManager(context,2)
        recyclerView.isNestedScrollingEnabled=false
        recyclerView.adapter= AdapterJordan(a,object: AdapterJordan.OnClickListener{
            override fun onItemClick(position: Int)
            {
            }
        })

        if (recyclerView.itemDecorationCount == 0)
        {
            println("Empty")
        }
        println(a.size)
        super.onViewCreated(view, savedInstanceState)
    }
}