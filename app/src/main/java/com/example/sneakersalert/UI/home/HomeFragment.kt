package com.example.sneakersalert.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.DataClasses.Spec
import com.example.sneakersalert.R
import com.example.sneakersalert.UI.BuyingProducts
import drawable.AdapterNewShoe
import drawable.NewShoe
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    val l=ArrayList<NewShoe>()
    val l2=ArrayList<NewShoe>()
    var sp=ArrayList<Spec>()
    val sizeHaveANikeDay=ArrayList<Int>(3)
    override fun onCreate(savedInstanceState: Bundle?) {
        sp.add(Spec(R.drawable.shield,"Anti-pollution, anti-dust"))
        sp.add(Spec(R.drawable.crossing,"Reusable"))
        sp.add(Spec(R.drawable.happy_face,"Pleated at sides for extra comfort"))
        sp.add(Spec(R.drawable.sun,"Wider face coverage for maximum \n" + "protection"))
        l.add(NewShoe(R.drawable.air_max_1_have_a_nikeday,"Nike Air Max 1","Have A Nike Day",149,"",
            arrayListOf(39,40,41), sp))
        l.add(NewShoe(R.drawable.limeade,"Nike Air Max 1","Limeade",209,"",
            arrayListOf(39,40,41), arrayListOf(Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"),Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection "))))
        l.add(NewShoe(R.drawable.limeade2,"Nike Air Max 1","Limeade",289,"",
            arrayListOf(39,40,41), arrayListOf(Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"),Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection "))))
        l2.add(NewShoe(R.drawable.air_max_london, "Nike air max 1", "LONDON",289,"",
                arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                    Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                            "protection ")
                )))
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerViewHome=view.findViewById<RecyclerView>(R.id.recyclerViewHome)
        recyclerViewHome.layoutManager= LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
        recyclerViewHome.isNestedScrollingEnabled=false
        LinearLayoutManager.HORIZONTAL

        recyclerViewHome.adapter=AdapterNewShoe(l,object: AdapterNewShoe.OnClickListener{
            override fun onItemClick(position: Int) {
                    val i=Intent(activity,BuyingProducts::class.java)
                    startActivity(i)
            }
        })
        arrow.setOnClickListener {
            val i=Intent(activity,BuyingProducts::class.java)
            sp=l2[0].spec
            i.putExtra("spec",sp)
            println(sp)
            startActivity(i)

        }
    }

}