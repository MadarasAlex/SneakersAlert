package com.example.sneakersalert.UI.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterSearch
import com.example.sneakersalert.DataClasses.Spec
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import drawable.NewShoe
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(R.layout.fragment_search) {
    val s = ArrayList<NewShoe>()
    lateinit var rec: RecyclerView
    lateinit var adapter: AdapterSearch
    val drawer: DrawerLayout? = activity?.drawer_layout
    val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)
    val displays = ArrayList<NewShoe>()
    var sp = ArrayList<Spec>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(false)
        drawer?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(false)
        drawer?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        rec = view.findViewById(R.id.recyclerViewSearch)
        rec.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        rec.isNestedScrollingEnabled = false
        rec.setHasFixedSize(true)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("WrongConstant")
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                Log.d("SEARCH", "Done")
                println("here1")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                println("here2")
                println(adapter.filter.filter(newText))

                Log.d("xsff", adapter.filter.filter(newText).toString())
                return false
            }
        })
        addList()
    }

    fun addList() {
        sp.add(Spec(R.drawable.shield, "Anti-pollution, anti-dust"))
        sp.add(Spec(R.drawable.crossing, "Reusable"))
        sp.add(Spec(R.drawable.happy_face, "Pleated at sides for extra comfort"))
        sp.add(Spec(R.drawable.sun, "Wider face coverage for maximum \n" + "protection"))
        s.add(
            NewShoe(
                R.drawable.air_max_london, "Nike air max 1", "LONDON", 289, "",
                arrayListOf(39, 40, 41), arrayListOf(
                    Spec(0, "Anti-pollution, anti-dust"),
                    Spec(0, "Reusable"), Spec(0, "Pleated at sides for extra comfort"), Spec(
                        0, "Wider face coverage for maximum \n" +
                                "protection "
                    )
                )
            )
        )
        s.add(
            NewShoe(
                R.drawable.air_max_1_have_a_nikeday, "Nike air max 1", "Have A Nike Day", 149, "",
                arrayListOf(39, 40, 41), arrayListOf(
                    Spec(0, "Anti-pollution, anti-dust"),
                    Spec(0, "Reusable"), Spec(0, "Pleated at sides for extra comfort"), Spec(
                        0, "Wider face coverage for maximum \n" +
                                "protection "
                    )
                )
            )
        )
        s.add(
            NewShoe(
                R.drawable.limeade, "Nike air max 1", "Limeade", 209, "",
                arrayListOf(39, 40, 41), arrayListOf(
                    Spec(0, "Anti-pollution, anti-dust"),
                    Spec(0, "Reusable"), Spec(0, "Pleated at sides for extra comfort"), Spec(
                        0, "Wider face coverage for maximum \n" +
                                "protection "
                    )
                )
            )
        )
        s.add(
            NewShoe(
                R.drawable.jordan_canyon, "Jordan 1 mid", "\" Canyon Rust\"", 230, "",
                arrayListOf(39, 40, 41), arrayListOf(
                    Spec(0, "Anti-pollution, anti-dust"),
                    Spec(0, "Reusable"), Spec(0, "Pleated at sides for extra comfort"), Spec(
                        0, "Wider face coverage for maximum \n" +
                                "protection "
                    )
                )
            )
        )
        s.add(
            NewShoe(
                R.drawable.jordan_particle, "Jordan 1 mid", "SE Particle Beige", 210, "",
                arrayListOf(39, 40, 41), arrayListOf(
                    Spec(0, "Anti-pollution, anti-dust"),
                    Spec(0, "Reusable"), Spec(0, "Pleated at sides for extra comfort"), Spec(
                        0, "Wider face coverage for maximum \n" +
                                "protection "
                    )
                )
            )
        )
        s.add(
            NewShoe(
                R.drawable.jordan_yellow, "Jordan 1 mid", "SE Voltage Yellow", 170, "",
                arrayListOf(39, 40, 41), arrayListOf(
                    Spec(0, "Anti-pollution, anti-dust"),
                    Spec(0, "Reusable"), Spec(0, "Pleated at sides for extra comfort"), Spec(
                        0, "Wider face coverage for maximum \n" +
                                "protection "
                    )
                )
            )
        )
        s.add(
            NewShoe(
                R.drawable.airmax_90_crock, "Nike airmax 90", "Croc", 190, "",
                arrayListOf(39, 40, 41), arrayListOf(
                    Spec(0, "Anti-pollution, anti-dust"),
                    Spec(0, "Reusable"), Spec(0, "Pleated at sides for extra comfort"), Spec(
                        0, "Wider face coverage for maximum \n" +
                                "protection "
                    )
                )
            )
        )
        s.add(
            NewShoe(
                R.drawable.dance_floor_green, "Nike airmax 90", "90S Dancefloor Green", 190, "",
                arrayListOf(39, 40, 41), arrayListOf(
                    Spec(0, "Anti-pollution, anti-dust"),
                    Spec(0, "Reusable"), Spec(0, "Pleated at sides for extra comfort"), Spec(
                        0, "Wider face coverage for maximum \n" +
                                "protection "
                    )
                )
            )
        )
        s.add(
            NewShoe(
                R.drawable.duck_camo, "Nike airmax 90", "Duck Camo Orange", 140, "",
                arrayListOf(39, 40, 41), arrayListOf(
                    Spec(0, "Anti-pollution, anti-dust"),
                    Spec(0, "Reusable"), Spec(0, "Pleated at sides for extra comfort"), Spec(
                        0, "Wider face coverage for maximum \n" +
                                "protection "
                    )
                )
            )
        )
        adapter = AdapterSearch(s, object : AdapterSearch.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = s[position].price!!
                Global.name = s[position].name
                Global.model = s[position].model
                Global.sizes = s[position].sizes
                Global.pic = s[position].image!!
                Global.sp = s[position].spec
                Global.infoText = s[position].text
                view?.findNavController()?.navigate(R.id.buyingProducts)
            }
        })
        rec.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        println(findNavController().currentDestination?.id)

    }


}
