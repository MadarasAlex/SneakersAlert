package com.example.sneakersalert.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.example.sneakersalert.DataClasses.NewShoe
import com.example.sneakersalert.DataClasses.Spec
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment(R.layout.fragment_search) {
    val s = ArrayList<NewShoe>()
    lateinit var rec: RecyclerView
    lateinit var adapter: AdapterSearch
    val drawer: DrawerLayout? = activity?.drawer_layout
    val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)
    val displays = ArrayList<NewShoe>()
    var sp = ArrayList<Spec>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(false)
        drawer?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(false)
        drawer?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        rec = view.findViewById(R.id.recyclerViewSearch)
        categories.visibility = View.VISIBLE
        recyclerViewSearch.visibility = View.INVISIBLE

        rec.isNestedScrollingEnabled = true
        rec.setHasFixedSize(true)
        jordan_1.setOnClickListener {
            findNavController().navigate(R.id.nav_sneakers)
            Global.positionSelected = 2
        }
        airmax_1.setOnClickListener {
            findNavController().navigate(R.id.nav_sneakers)
            Global.positionSelected = 0
        }
        airmax_90.setOnClickListener {
            findNavController().navigate(R.id.nav_sneakers)
            Global.positionSelected = 1
        }
        searchView.setOnCloseListener {
            categories.visibility = View.VISIBLE
            recyclerViewSearch.visibility = View.INVISIBLE
            true
        }

    }

    private fun addList() {
        sp.add(Spec(R.drawable.shield, "Anti-pollution, anti-dust"))
        sp.add(Spec(R.drawable.crossing, "Reusable"))
        sp.add(Spec(R.drawable.happy_face, "Pleated at sides for extra comfort"))
        sp.add(Spec(R.drawable.sun, "Wider face coverage for maximum \n" + "protection"))
        s.add(
            NewShoe(
                R.drawable.air_max_london.toString(), "Nike air max 1", "LONDON", 289, "",
                arrayListOf(39, 40, 41), sp
            )
        )

        s.add(
            NewShoe(
                R.drawable.air_max_1_have_a_nikeday.toString(),
                "Nike air max 1",
                "Have A Nike Day",
                149,
                "",
                arrayListOf(39, 40, 41),
                sp
            )
        )

        s.add(
            NewShoe(
                R.drawable.limeade.toString(), "Nike air max 1", "Limeade", 209, "",
                arrayListOf(39, 40, 41), sp
            )
        )

        s.add(
            NewShoe(
                R.drawable.jordan_canyon.toString(), "Jordan 1 mid", "\" Canyon Rust\"", 230, "",
                arrayListOf(39, 40, 41), sp
            )
        )

        s.add(
            NewShoe(
                R.drawable.jordan_particle.toString(), "Jordan 1 mid", "SE Particle Beige", 210, "",
                arrayListOf(39, 40, 41), sp
            )
        )

        s.add(
            NewShoe(
                R.drawable.jordan_yellow.toString(), "Jordan 1 mid", "SE Voltage Yellow", 170, "",
                arrayListOf(39, 40, 41), sp
            )
        )

        s.add(
            NewShoe(
                R.drawable.airmax_90_crock.toString(), "Nike airmax 90", "Croc", 190, "",
                arrayListOf(39, 40, 41), sp
            )
        )
        s.add(
            NewShoe(
                R.drawable.dance_floor_green.toString(),
                "Nike airmax 90",
                "90S Dancefloor Green",
                190,
                "",
                arrayListOf(39, 40, 41),
                sp
            )
        )
        s.add(
            NewShoe(
                R.drawable.duck_camo.toString(), "Nike airmax 90", "Duck Camo Orange", 140, "",
                arrayListOf(39, 40, 41), sp
            )
        )

        rec.setHasFixedSize(false)
    }

    override fun onStart() {
        super.onStart()
        if (s.isEmpty())
            addList()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("WrongConstant")
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchList = ArrayList<NewShoe>()
                val search = searchView.query.toString().toLowerCase(Locale.ROOT).trim()
                searchList.clear()
                for (i in 0 until s.size) {
                    val name = s[i].name
                    if (name.toLowerCase(Locale.ROOT).contains(search)) {
                        val shoe: NewShoe = s[i]
                        searchList.add(shoe)
                    }

                }
                recyclerViewSearch.visibility = View.VISIBLE
                categories.visibility = View.INVISIBLE

                if (searchList.size > 0) {
                    rec.layoutManager =
                        GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
                    val adapter = AdapterSearch(searchList, object : AdapterSearch.OnClickListener {

                        override fun onItemClick(position: Int) {

                            Global.price = searchList[position].price!!
                            Global.name = searchList[position].name
                            Global.model = searchList[position].model
                            Global.sizes = searchList[position].sizes
                            Global.pic = searchList[position].image
                            Global.sp = searchList[position].spec
                            Global.infoText = searchList[position].text
                            view?.findNavController()?.navigate(R.id.buyingProducts)

                        }
                    })
                    adapter.notifyDataSetChanged()
                    rec.adapter = adapter

                }


                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isEmpty()) {
                    val adapter = AdapterSearch(s, object : AdapterSearch.OnClickListener {

                        override fun onItemClick(position: Int) {

                            Global.price = s[position].price!!
                            Global.name = s[position].name
                            Global.model = s[position].model
                            Global.sizes = s[position].sizes
                            Global.pic = s[position].image
                            Global.sp = s[position].spec
                            Global.infoText = s[position].text
                            view?.findNavController()?.navigate(R.id.buyingProducts)

                        }
                    })
                    adapter.notifyDataSetChanged()
                    rec.layoutManager =
                        GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
                    rec.adapter = adapter
                }
                return true
            }

        })
        println(findNavController().currentDestination?.id)

    }


}
