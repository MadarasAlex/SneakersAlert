package com.example.sneakersalert.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterJordan
import com.example.sneakersalert.DataClasses.NewShoe
import com.example.sneakersalert.DataClasses.Spec
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.paypal.pyplcheckout.home.view.adapters.CartItemsAdapter
import kotlinx.android.synthetic.main.fragment_airmax1.*

class Airmax1Fragment : Fragment(R.layout.fragment_airmax1), AdapterView.OnItemSelectedListener {

    private val mAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val databaseProducts = database.getReference("Products")
    val a = ArrayList<NewShoe>()
    val sp = ArrayList<Spec>()
    lateinit var adapter:AdapterJordan


    override fun onCreate(savedInstanceState: Bundle?) {
        sp.add(Spec(R.drawable.shield, "Anti-pollution, anti-dust"))
        sp.add(Spec(R.drawable.crossing, "Reusable"))
        sp.add(Spec(R.drawable.happy_face, "Pleated at sides for extra comfort"))
        sp.add(Spec(R.drawable.sun, "Wider face coverage for maximum \n" + "protection"))
        super.onCreate(savedInstanceState)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isAdded) {
            return
        }
        val options= FirebaseRecyclerOptions.Builder<NewShoe>().setQuery(databaseProducts,NewShoe::class.java).build()
        getData()

        val spinner: Spinner = view.findViewById(R.id.spinner2)
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
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewAirmax1)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.isNestedScrollingEnabled = true

        println(a)
        adapter = AdapterJordan(a, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = a[position].price!!
                Global.name = a[position].name
                Global.model = a[position].model
                Global.stock=a[position].stock
                Global.sizes = a[position].sizes
                Global.pic = a[position].image
                Global.sp = a[position].spec
                Global.infoText = a[position].text
                view.findNavController().navigate(R.id.buyingProducts)
            }

        })
        recyclerView.adapter = adapter
        if (recyclerView.itemDecorationCount == 0) {
            println("Empty")
        }
        println(a.size)
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

        val options= FirebaseRecyclerOptions.Builder<NewShoe>().setQuery(databaseProducts,NewShoe::class.java).build()
        adapter = AdapterJordan(a, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = a[position].price!!
                Global.name = a[position].name
                Global.model = a[position].model
                Global.sizes = a[position].sizes
                Global.pic = a[position].image
                Global.sp = a[position].spec
                Global.infoText = a[position].text
                view?.findNavController()?.navigate(R.id.buyingProducts)
            }

        })
        recyclerViewAirmax1.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun sortbySelling() {

    }

    private fun sortbyNew() {
        val options= FirebaseRecyclerOptions.Builder<NewShoe>().setQuery(databaseProducts,NewShoe::class.java).build()
        a.reverse()
        adapter = AdapterJordan(a, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = a[position].price!!
                Global.name = a[position].name
                Global.model = a[position].model
                Global.sizes = a[position].sizes
                Global.pic = a[position].image
                Global.sp = a[position].spec
                Global.stock=a[position].stock
                Global.infoText = a[position].text
                view?.findNavController()?.navigate(R.id.buyingProducts)
            }

        })
        recyclerViewAirmax1?.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun sortbyRecommended() {

    }

    private fun sortbyHighLow() {
        a.sortByDescending { it.price }
        val options= FirebaseRecyclerOptions.Builder<NewShoe>().setQuery(databaseProducts,NewShoe::class.java).build()
        adapter = AdapterJordan(a, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = a[position].price!!
                Global.name = a[position].name
                Global.model = a[position].model
                Global.stock=a[position].stock
                Global.sizes = a[position].sizes
                Global.pic = a[position].image
                Global.sp = a[position].spec
                Global.infoText = a[position].text
                view?.findNavController()?.navigate(R.id.buyingProducts)
            }

        })
        recyclerViewAirmax1?.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun sortbyLowHigh() {
        val options= FirebaseRecyclerOptions.Builder<NewShoe>().setQuery(databaseProducts,NewShoe::class.java).build()
        a.sortWith(nullsLast(compareBy { it.price }))

        adapter = AdapterJordan(a, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = a[position].price!!
                Global.name = a[position].name
                Global.model = a[position].model
                Global.sizes = a[position].sizes
                Global.pic = a[position].image
                Global.stock=a[position].stock
                Global.sp = a[position].spec
                Global.infoText = a[position].text
                view?.findNavController()?.navigate(R.id.buyingProducts)
            }

        })
        recyclerViewAirmax1?.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
    private fun getData() {
        val productReference = database.reference.child("Products")
        productReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (el in snapshot.children) {
                        val name = el.child("name").getValue(String::class.java)
                        val model = el.child("model").getValue(String::class.java)
                        val price = el.child("price").getValue(Int::class.java)
                        val type = el.child("type").getValue(String::class.java)
                        val image = el.child("image").getValue(String::class.java)
                        val stock = el.child("stock").getValue(Int::class.java)
                        val text = el.child("text").getValue(String::class.java)
                        val sizesRef = el.child("sizes").getValue(String::class.java)
                        val sizes = ArrayList<Int>()
                        var size = ""
                        println(sizesRef)
                        for(i in 0 until sizesRef?.length!!)
                        {

                            if(sizesRef?.get(i).toString().equals(",") || sizesRef[i].toString().equals(null))
                            {
                                sizes.add(size.toInt())
                                println(size)
                                size=""
                            }
                            else if(i==sizesRef.length-1)
                            {
                                size+=sizesRef[sizesRef?.length-1]
                                sizes.add(size.toInt())
                                println(size)
                            }
                            else
                            {
                                size+=sizesRef[i]
                            }

                        }
                        if (type == "Air Max 1") {
                            val item =
                                NewShoe(image!!, name!!, model!!, price, text!!, sizes, sp, stock!!)
                            println(item)
                            a.add(item)
                        }
                    }
                    recyclerViewAirmax1.adapter=AdapterJordan(a,object : AdapterJordan.OnClickListener {
                        override fun onItemClick(position: Int) {
                            Global.price = a[position].price!!
                            Global.name = a[position].name
                            Global.model = a[position].model
                            Global.stock = a[position].stock
                            Global.sizes = a[position].sizes
                            Global.pic = a[position].image
                            Global.sp = a[position].spec
                            Global.infoText = a[position].text
                            view?.findNavController()?.navigate(R.id.buyingProducts)
                        }
                        })
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}