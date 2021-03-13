package com.example.sneakersalert.ui.airmax1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterJordan
import com.example.sneakersalert.DataClasses.Spec
import com.example.sneakersalert.R
import drawable.NewShoe

class Airmax1Fragment : Fragment(R.layout.fragment_airmax1) {
    val a=ArrayList<NewShoe>()
    override fun onCreate(savedInstanceState: Bundle?) {
        a.add(NewShoe(R.drawable.airmax_90_crock, "Nike airmax 90", "Croc", 190,"",
            arrayListOf(39,40,41), arrayListOf(
                Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection ")
            )))
        a.add(NewShoe(R.drawable.dance_floor_green, "Nike airmax 90", "90S Dancefloor Green", 190,"",
            arrayListOf(39,40,41), arrayListOf(Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"),Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection "))))
        a.add(NewShoe(R.drawable.duck_camo, "Nike airmax 90", "Duck Camo Orange", 140,"",
            arrayListOf(39,40,41), arrayListOf(Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"),Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection "))))
        a.add(NewShoe(0, "", "",0,"",
            arrayListOf(39,40,41), arrayListOf(Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"),Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection "))))
        a.add(NewShoe(0, "", "",0,"",
            arrayListOf(39,40,41), arrayListOf(Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"),Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection "))))
        a.add(NewShoe(0, "", "",0,"",
            arrayListOf(39,40,41), arrayListOf(Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"),Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection "))))
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerView=view.findViewById<RecyclerView>(R.id.recyclerViewAirmax1)
        recyclerView.layoutManager= GridLayoutManager(context,2)
        recyclerView.isNestedScrollingEnabled=false
        recyclerView.adapter= AdapterJordan(a,object: AdapterJordan.OnClickListener{
            override fun onItemClick(position: Int) {

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