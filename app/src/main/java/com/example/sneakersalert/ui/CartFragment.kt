package com.example.sneakersalert.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterJordan
import com.example.sneakersalert.Adapters.AdapterProductCart
import com.example.sneakersalert.DataClasses.NewShoe
import com.example.sneakersalert.DataClasses.ProductCart
import com.example.sneakersalert.Global
import com.example.sneakersalert.Global.Companion.model
import com.example.sneakersalert.MainActivity
import com.example.sneakersalert.R
import com.example.sneakersalert.ViewModels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_jordan.*


class CartFragment : Fragment(R.layout.activity_cart), Parcelable {

    private var created = false
    private val mAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseCart = database.getReference("Cart")
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println(Global.p)
        if(mAuth.currentUser!=null)
        {
            getData()
        }
        if (Global.p.size == 0) {
            empty.visibility = View.VISIBLE
            recyclerViewCart.visibility = View.GONE
            details.visibility = View.GONE
            continue_shopping.setOnClickListener {
                findNavController().navigate(R.id.nav_sneakers)
            }
        } else {
            empty.visibility = View.GONE
            recyclerViewCart.visibility = View.VISIBLE
            details.visibility = View.VISIBLE
            val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerViewCart)
            recyclerView.layoutManager = LinearLayoutManager(this.activity)
            recyclerView.isNestedScrollingEnabled = true
            recyclerView.adapter =
                AdapterProductCart(
                    Global.p,
                    price_total,
                    price_final,
                    requireActivity().your_cart,
                    requireActivity().count_items
                )
            val model=ViewModelProvider(this.requireActivity()).get(CartViewModel::class.java)
            model.getFinal().observe(viewLifecycleOwner, { newPrice ->
                price_total.text = newPrice.toString()
                price_final.text = newPrice.toString()
            })
            model.getTotal().observe(viewLifecycleOwner, { newPrice ->
                price_total.text = (newPrice).toString()
                price_final.text = (newPrice).toString()
            })


            price_final.setText(model.getTotal())
            price_total.text=Global.total.toString()
            recyclerView.setRecycledViewPool(object : RecyclerView.RecycledViewPool() {})
            recyclerView.recycledViewPool.setMaxRecycledViews(0, 0)
            recyclerView.isNestedScrollingEnabled = false
            checkout.setOnClickListener {
                if (Global.p.size > 0)
                    findNavController().navigate(R.id.nav_payment)
                else Toast.makeText(activity, "Your cart is empty.", Toast.LENGTH_SHORT).show()
            }

        }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResume() {
        super.onResume()
        if(mAuth.currentUser!=null)
        {
            getData()
        }
        if (Global.p.size == 0) {
            empty.visibility = View.VISIBLE
            recyclerViewCart.visibility = View.GONE
            details.visibility = View.GONE

            continue_shopping.setOnClickListener {
                findNavController().navigate(R.id.nav_sneakers)
            }
        } else {
            empty.visibility = View.GONE
            recyclerViewCart.visibility = View.VISIBLE
            details.visibility = View.VISIBLE

            val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerViewCart)
            recyclerView.layoutManager = LinearLayoutManager(this.activity)
            recyclerView.isNestedScrollingEnabled = true
            recyclerView.adapter =
                AdapterProductCart(
                    Global.p,
                    price_total,
                    price_final,
                    requireActivity().your_cart,
                    requireActivity().count_items
                )

            val model=ViewModelProvider(this.requireActivity()).get(CartViewModel::class.java)
            val adapter =
                AdapterProductCart(
                    Global.p,
                    price_total,
                    price_final,
                    requireActivity().your_cart,
                    requireActivity().count_items
                )
            adapter.notifyDataSetChanged()
            recyclerView.setRecycledViewPool(object : RecyclerView.RecycledViewPool() {})
            recyclerView.recycledViewPool.setMaxRecycledViews(0, 0)
            recyclerView.isNestedScrollingEnabled = false



            checkout.setOnClickListener {
                if (Global.p.size > 0)
                    findNavController().navigate(R.id.nav_payment)
                else Toast.makeText(activity, "Your cart is empty.", Toast.LENGTH_SHORT).show()
            }

        }
    }
    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser != null) {
            getData()
        }
        if (Global.p.size == 0) {
            empty.visibility = View.VISIBLE
            recyclerViewCart.visibility = View.GONE
            details.visibility = View.GONE

            continue_shopping.setOnClickListener {
                findNavController().navigate(R.id.nav_sneakers)
            }
        } else {
            empty.visibility = View.GONE
            recyclerViewCart.visibility = View.VISIBLE
            details.visibility = View.VISIBLE
            val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerViewCart)
            recyclerView.layoutManager = LinearLayoutManager(this.activity)
            recyclerView.isNestedScrollingEnabled = true
            recyclerView.adapter =
                AdapterProductCart(
                    Global.p,
                    price_total,
                    price_final,
                    requireActivity().your_cart,
                    requireActivity().count_items
                )
            val model=ViewModelProvider(this.requireActivity()).get(CartViewModel::class.java)
            val adapter =
                AdapterProductCart(
                    Global.p,
                    price_total,
                    price_final,
                    requireActivity().your_cart,
                    requireActivity().count_items
                )
            adapter.notifyDataSetChanged()
            recyclerView.setRecycledViewPool(object : RecyclerView.RecycledViewPool() {})
            recyclerView.recycledViewPool.setMaxRecycledViews(0, 0)
            recyclerView.isNestedScrollingEnabled = false

            checkout.setOnClickListener {
                if (Global.p.size > 0)
                    findNavController().navigate(R.id.nav_payment)
                else Toast.makeText(activity, "Your cart is empty.", Toast.LENGTH_SHORT).show()
            }

        }
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (created) 1 else 0)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<CartFragment> {
        override fun createFromParcel(parcel: Parcel): CartFragment {
            return createFromParcel(parcel)
        }

        override fun newArray(size: Int): Array<CartFragment?> {
            return arrayOfNulls(size)
        }
    }
        private fun getData() {
            val databaseUsers = database.getReference("Users")
            val id = mAuth.currentUser?.uid
            val productsReference = databaseUsers.child(id.toString()).child("Cart")
            productsReference.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Global.p.clear()
                        println("Children:" +snapshot.children)
                        for (el in snapshot.children) {
                            val name = el.child("name").getValue(String::class.java)
                            val idItem=el.child("id").getValue(Int::class.java)
                            val model = el.child("model").getValue(String::class.java)
                            val price = el.child("price").getValue(Int::class.java)
                            val image = el.child("image").getValue(String::class.java)
                            val amount= el.child("amount").getValue(Int::class.java)
                            val size = el.child("size").getValue(Int::class.java)
                                val item = ProductCart(image!!,name!!,model!!,price!!,size!!,amount!!,idItem!!)
                                val mutableItem= liveData<ProductCart> {item}
                                println(idItem)
                                Global.p.add(item)
                            println(Global.p)
                            }
                        val adapter =
                            AdapterProductCart(
                                Global.p,
                                price_total,
                                price_final,
                                requireActivity().your_cart,
                                requireActivity().count_items
                            )
                        recyclerViewCart.adapter=adapter
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
}