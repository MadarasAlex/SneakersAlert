package com.example.sneakersalert.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.DataClasses.Spec
import com.example.sneakersalert.R
import drawable.AdapterNewShoe
import drawable.NewShoe

class HomeFragment : Fragment(R.layout.fragment_home) {
    val l=ArrayList<NewShoe>()
    val sizeHaveANikeDay=ArrayList<Int>(3)
    override fun onCreate(savedInstanceState: Bundle?) {
        l.add(NewShoe(R.drawable.air_max_1_have_a_nikeday,"Nike Air Max 1","Have A Nike Day",149,"",
            arrayListOf(39,40,41), arrayListOf(Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"),Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection "))))
        l.add(NewShoe(R.drawable.limeade,"Nike Air Max 1","Limeade",209,"",
            arrayListOf(39,40,41), arrayListOf(Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"),Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection "))))
        l.add(NewShoe(R.drawable.limeade2,"Nike Air Max 1","Limeade",289,"",
            arrayListOf(39,40,41), arrayListOf(Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"),Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection "))))
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerViewHome=view.findViewById<RecyclerView>(R.id.recyclerViewHome)
        recyclerViewHome.layoutManager= LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
        recyclerViewHome.isNestedScrollingEnabled=false
        LinearLayoutManager.HORIZONTAL

        recyclerViewHome.adapter=AdapterNewShoe(l,object: AdapterNewShoe.OnClickListener{
            override fun onItemClick(position: Int) {

            }
        })
    }
}