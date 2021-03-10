package com.example.sneakersalert

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterJordan


import drawable.NewShoe

class JordanFragment : Fragment(R.layout.fragment_jordan) {
    val j = ArrayList<NewShoe>()
    override fun onCreate(savedInstanceState: Bundle?) {
        j.add(NewShoe(R.drawable.jordan_canyon, "Jordan 1 mid", "\" Canyon Rust\"", 230))
        j.add(NewShoe(R.drawable.jordan_particle, "Jordan 1 mid", "SE Particle Beige", 210))
        j.add(NewShoe(R.drawable.jordan_yellow, "Jordan 1 mid", "SE Voltage Yellow", 170))
        j.add(NewShoe(null, "", "",null ))
        j.add(NewShoe(null, "", "",null ))
        j.add(NewShoe(null, "", "",null ))
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView=view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager=GridLayoutManager(this.context?.applicationContext,2)
        recyclerView.isNestedScrollingEnabled=true
        recyclerView.adapter=AdapterJordan(j,object:AdapterJordan.OnClickListener{
            override fun onItemClick(position: Int) {

            }

        })


        if (recyclerView.itemDecorationCount == 0)
        {
           println("Empty")
        }
        println(j.size)
        super.onViewCreated(view, savedInstanceState)
    }




}