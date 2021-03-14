package com.example.sneakersalert

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterSearch
import com.example.sneakersalert.DataClasses.Spec
import drawable.NewShoe
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment(R.layout.fragment_search) {
    val s = ArrayList<NewShoe>()
    val displays=ArrayList<NewShoe>()
    override fun onCreate(savedInstanceState: Bundle?) {
        s.add(
            NewShoe(R.drawable.air_max_london, "Nike air max 1", "LONDON",289,"",
            arrayListOf(39,40,41), arrayListOf(
                Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection ")
            ))
        )
        s.add(
            NewShoe(R.drawable.air_max_1_have_a_nikeday, "Nike air max 1", "Have A Nike Day",149,"",
            arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection ")
                ))
        )
        s.add(
            NewShoe(R.drawable.limeade, "Nike air max 1", "Limeade",209,"",
            arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection ")
                ))
        )
        s.add(
            NewShoe(R.drawable.jordan_canyon, "Jordan 1 mid", "\" Canyon Rust\"", 230,"",
            arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection ")
                ))
        )
        s.add(
            NewShoe(R.drawable.jordan_particle, "Jordan 1 mid", "SE Particle Beige", 210,"",
            arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection ")
                ))
        )
        s.add(
            NewShoe(R.drawable.jordan_yellow, "Jordan 1 mid", "SE Voltage Yellow", 170,"",
            arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection ")
                ))
        )
        s.add(
            NewShoe(R.drawable.airmax_90_crock, "Nike airmax 90", "Croc", 190,"",
            arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection ")
                ))
        )
        s.add(
            NewShoe(R.drawable.dance_floor_green, "Nike airmax 90", "90S Dancefloor Green", 190,"",
            arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection ")
                ))
        )
        s.add(
            NewShoe(R.drawable.duck_camo, "Nike airmax 90", "Duck Camo Orange", 140,"",
            arrayListOf(39,40,41), arrayListOf(
                    Spec(0,"Anti-pollution, anti-dust"),
                Spec(0,"Reusable"), Spec(0,"Pleated at sides for extra comfort"), Spec(0,"Wider face coverage for maximum \n" +
                        "protection ")
                ))
        )
        displays.addAll(s)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView=view.findViewById<RecyclerView>(R.id.recyclerViewSearch)
        recyclerView.layoutManager= GridLayoutManager(this.context?.applicationContext,2)
        recyclerView.isNestedScrollingEnabled=false
        recyclerView.adapter= AdapterSearch(s,android.R.layout.simple_list_item_1,object: AdapterSearch.OnClickListener{
            override fun onItemClick(position: Int) {

            }

        })
        val adapter=AdapterSearch(s,android.R.layout.simple_list_item_1,object: AdapterSearch.OnClickListener{
            override fun onItemClick(position: Int) {
                TODO("Not yet implemented")
            }

        })
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                if(s.contains(query))
                {
                    adapter.filter.filter(query)
                }
                else
                    Toast.makeText(context,"Item not found",Toast.LENGTH_LONG).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_item,menu)
        val item: MenuItem =menu.findItem(R.id.action_search)
        val searchView: SearchView? = view?.findViewById(R.id.searchView)
        if (searchView != null) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty())
                    {
                        displays.clear()
                        val search=newText.toLowerCase(Locale.getDefault())
                        s.forEach{
                            if(it.name.toLowerCase(Locale.getDefault()).contains(search))
                            {
                                displays.add(it)
                            }
                        }
                        recyclerViewSearch.adapter!!.notifyDataSetChanged()
                    }
                    else
                    {
                        displays.clear()
                        displays.addAll(s)
                        recyclerViewSearch.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }

            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
}
