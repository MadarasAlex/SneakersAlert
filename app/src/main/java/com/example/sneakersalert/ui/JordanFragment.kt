package com.example.sneakersalert.ui


import android.content.Intent
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
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_airmax1.*
import kotlinx.android.synthetic.main.fragment_jordan.*

class JordanFragment : Fragment(R.layout.fragment_jordan), AdapterView.OnItemSelectedListener {
    val j = ArrayList<NewShoe>()
    private val database = FirebaseDatabase.getInstance()
    private val databaseProducts = database.getReference("Products")
    lateinit var adapter:AdapterJordan
    var sp = ArrayList<Spec>()
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
        val spinner: Spinner = view.findViewById(R.id.spinner4)
// Create an ArrayAdapter using the string array and j default spinner layout
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
        getData()
        spinner.onItemSelectedListener = this
        recyclerView.layoutManager = GridLayoutManager(this.context?.applicationContext, 2)
        recyclerView.isNestedScrollingEnabled = true
        val options= FirebaseRecyclerOptions.Builder<NewShoe>().setQuery(databaseProducts,NewShoe::class.java).build()
        adapter = AdapterJordan(j, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = j[position].price!!
                Global.name = j[position].name
                Global.stock=j[position].stock
                Global.model = j[position].model
                Global.sizes = j[position].sizes
                Global.pic = j[position].image
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

        val options= FirebaseRecyclerOptions.Builder<NewShoe>().setQuery(databaseProducts,NewShoe::class.java).build()
        adapter = AdapterJordan(j, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = j[position].price!!
                Global.name = j[position].name
                Global.model = j[position].model
                Global.sizes = j[position].sizes
                Global.pic = j[position].image
                Global.sp = j[position].spec
                Global.infoText = j[position].text
                view?.findNavController()?.navigate(R.id.buyingProducts)
            }

        })
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun sortbySelling() {

    }

    private fun sortbyNew() {
        val options= FirebaseRecyclerOptions.Builder<NewShoe>().setQuery(databaseProducts,NewShoe::class.java).build()
        j.reverse()
        adapter = AdapterJordan(j, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = j[position].price!!
                Global.name = j[position].name
                Global.model = j[position].model
                Global.sizes = j[position].sizes
                Global.pic = j[position].image
                Global.sp = j[position].spec
                Global.stock=j[position].stock
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
        val options= FirebaseRecyclerOptions.Builder<NewShoe>().setQuery(databaseProducts,NewShoe::class.java).build()
        adapter = AdapterJordan(j, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = j[position].price!!
                Global.name = j[position].name
                Global.model = j[position].model
                Global.stock=j[position].stock
                Global.sizes = j[position].sizes
                Global.pic = j[position].image
                Global.sp = j[position].spec
                Global.infoText = j[position].text
                view?.findNavController()?.navigate(R.id.buyingProducts)
            }

        })
        recyclerView?.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun sortbyLowHigh() {
        val options= FirebaseRecyclerOptions.Builder<NewShoe>().setQuery(databaseProducts,NewShoe::class.java).build()
        j.sortWith(nullsLast(compareBy { it.price }))

        adapter = AdapterJordan(j, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {
                Global.price = j[position].price!!
                Global.name = j[position].name
                Global.model = j[position].model
                Global.sizes = j[position].sizes
                Global.pic = j[position].image
                Global.stock=j[position].stock
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
                        if (type == "Jordan 1") {
                            val item =
                                NewShoe(image!!, name!!, model!!, price, text!!, sizes, sp, stock!!)
                            println(item)
                            j.add(item)
                        }
                    }
                    recyclerView.adapter=AdapterJordan(j,object : AdapterJordan.OnClickListener {
                        override fun onItemClick(position: Int) {
                            Global.price = j[position].price!!
                            Global.name = j[position].name
                            Global.model = j[position].model
                            Global.stock = j[position].stock
                            Global.sizes = j[position].sizes
                            Global.pic = j[position].image
                            Global.sp = j[position].spec
                            Global.infoText = j[position].text
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