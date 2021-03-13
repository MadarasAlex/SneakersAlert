package com.example.sneakersalert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterJordan
import com.example.sneakersalert.DataClasses.Spec
import drawable.NewShoe
import kotlinx.android.synthetic.main.activity_cart.*

class WishlistFragment : Fragment(R.layout.fragment_wishlist) {
    val w=ArrayList<NewShoe>()
    override fun onCreate(savedInstanceState: Bundle?) {
       
            w.add(NewShoe(R.drawable.air_max_london, "Nike air max 1", "London", 289,"",
                arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                    Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                            "protection ")
                )))
            w.add(NewShoe(R.drawable.jordan_particle, "Jordan 1 mid", "SE Particle Beige", 210,"",
                arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                    Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                            "protection "))))
            w.add(NewShoe(0, "", "",0,"",
                arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                    Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                            "protection "))))
            w.add(NewShoe(0, "", "",0,"",
                arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                    Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                            "protection "))))
            w.add(NewShoe(0, "", "",0,"",
                arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                    Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                            "protection "))))
            w.add(NewShoe(0, "", "",0,"",
                arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                    Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                            "protection "))))
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