package com.example.sneakersalert.UI


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterJordan
import com.example.sneakersalert.DataClasses.Spec
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import drawable.NewShoe
import kotlinx.android.synthetic.main.fragment_jordan.*

class JordanFragment : Fragment(R.layout.fragment_jordan), AdapterView.OnItemSelectedListener {
    val j = ArrayList<NewShoe>()
    val adapter = AdapterJordan(j, object : AdapterJordan.OnClickListener {
        override fun onItemClick(position: Int) {
            Global.price = j[position].price!!
            Global.name = j[position].name
            Global.model = j[position].model
            Global.sizes = j[position].sizes
            Global.pic = j[position].image!!
            Global.sp = j[position].spec
            Global.infoText = j[position].text
            view?.findNavController()?.navigate(R.id.buyingProducts)
        }


    })


    var sp = ArrayList<Spec>()
    override fun onCreate(savedInstanceState: Bundle?) {
        sp.add(Spec(R.drawable.shield, "Anti-pollution, anti-dust"))
        sp.add(Spec(R.drawable.crossing, "Reusable"))
        sp.add(Spec(R.drawable.happy_face, "Pleated at sides for extra comfort"))
        sp.add(Spec(R.drawable.sun, "Wider face coverage for maximum \n" + "protection"))
        j.add(
            NewShoe(
                R.drawable.jordan_canyon, "Jordan 1 mid", "\" Canyon Rust\"", 230, "",
                arrayListOf(39, 40, 41), sp
            )
        )
        j.add(
            NewShoe(
                R.drawable.jordan_particle, "Jordan 1 mid", "SE Particle Beige", 210, "",
                arrayListOf(39, 40, 41), sp
            )
        )
        j.add(
            NewShoe(
                R.drawable.jordan_yellow, "Jordan 1 mid", "SE Voltage Yellow", 170, "",
                arrayListOf(39, 40, 41), sp
            )
        )

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isAdded) {
            return
        }
        val spinner: Spinner = view.findViewById(R.id.spinner4)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }


        spinner.onItemSelectedListener = this
        recyclerView.layoutManager = GridLayoutManager(this.context?.applicationContext, 2)
        recyclerView.isNestedScrollingEnabled = true
        val adapter = AdapterJordan(j, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = j[position].price!!
                Global.name = j[position].name
                Global.model = j[position].model
                Global.sizes = j[position].sizes
                Global.pic = j[position].image!!
                Global.sp = j[position].spec
                Global.infoText = j[position].text
                view.findNavController().navigate(R.id.buyingProducts)
            }

        })
        recyclerView.adapter = adapter




        if (recyclerView.itemDecorationCount == 0) {
            println("Empty")
        }
        println(j.size)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position == 0)
            sortbyRecommended()
        if (position == 1)
            sortbyNew()
        if (position == 2)
            sortbySelling()
        if (position == 3)
            sortbyHighLow()
        if (position == 4)
            sortbyLowHigh()
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)


        recyclerView?.adapter = AdapterJordan(j, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                val i = Intent(activity, BuyingProducts::class.java)
                i.putExtra("price", j[position].price)
                i.putExtra("name", j[position].name)
                i.putExtra("model", j[position].model)
                i.putExtra("sizes", j[position].sizes)
                i.putExtra("picture", j[position].image)
                i.putExtra("spec", sp)
                startActivity(i)
            }

        })
        recyclerView?.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun sortbySelling() {

    }

    private fun sortbyNew() {
        j.reverse()
        recyclerView?.adapter = AdapterJordan(j, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = j[position].price!!
                Global.name = j[position].name
                Global.model = j[position].model
                Global.sizes = j[position].sizes
                Global.pic = j[position].image!!
                Global.sp = j[position].spec
                Global.infoText = j[position].text
                view?.findNavController()?.navigate(R.id.buyingProducts)
            }

        })
        recyclerView?.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun sortbyRecommended() {

    }

    private fun sortbyHighLow() {
        j.sortByDescending { it.price }
        recyclerView?.adapter = AdapterJordan(j, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = j[position].price!!
                Global.name = j[position].name
                Global.model = j[position].model
                Global.sizes = j[position].sizes
                Global.pic = j[position].image!!
                Global.sp = j[position].spec
                Global.infoText = j[position].text
                view?.findNavController()?.navigate(R.id.buyingProducts)
            }

        })
        recyclerView?.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun sortbyLowHigh() {
        j.sortWith(nullsLast(compareBy { it.price }))

        recyclerView?.adapter = AdapterJordan(j, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = j[position].price!!
                Global.name = j[position].name
                Global.model = j[position].model
                Global.sizes = j[position].sizes
                Global.pic = j[position].image!!
                Global.sp = j[position].spec
                Global.infoText = j[position].text
                view?.findNavController()?.navigate(R.id.buyingProducts)
            }

        })
        recyclerView?.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }


}