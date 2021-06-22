package com.example.sneakersalert.ui.account

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakersalert.Adapters.AdapterOrders
import com.example.sneakersalert.DataClasses.Order
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_order_details.*
import java.util.*
import kotlin.collections.ArrayList


class OrderDetails : Fragment(R.layout.fragment_order_details) {

    val o = ArrayList<Order>()
    private val mAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseUsers = database.getReference("Users")
    val id = mAuth.currentUser?.uid
    private val rootReference = database.reference
    val user = mAuth.currentUser
    private val savedReference =
        rootReference.child("Users").child(user?.uid.toString()).child("saved")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addOrders()
        arguments?.let {
        }
    }

    private fun addOrders() {
        o.add(Order(Global.orderNumber, Date(2021 / 1 / 20), "A.M.", 150.00, "VIEW"))
        o.add(Order(Global.orderNumber, Date(2021 / 2 / 2), "A.M.", 250.00, "VIEW"))
        o.add(Order(Global.orderNumber, Date(2021 / 3 / 20), "A.M.", 150.00, "VIEW"))
        o.add(Order(Global.orderNumber, Date(2021 / 4 / 4), "A.M.", 50.00, "VIEW"))
        o.add(Order(Global.orderNumber, Date(2021 / 4 / 20), "A.M.", 550.00, "VIEW"))
    }

    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val colorDrawable = ColorDrawable(Color.WHITE)
        activity?.actionBar?.setBackgroundDrawable(colorDrawable)
        requireActivity().toolbar.navigationIcon = resources.getDrawable(R.drawable.back, null)
        requireActivity().drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        requireActivity().actionBar?.setHomeAsUpIndicator(R.drawable.back)
        requireActivity().toolbar.setNavigationOnClickListener {
            requireActivity().drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            findNavController().navigate(R.id.nav_orders)
        }
        mail_contact.text = mAuth.currentUser?.email
        mail_shipping.text = mAuth.currentUser?.email
        getFirstName()
        getLastName()
        getType()
        getCompanyName()
        getCity()
        getCountry()
        getStreet()
        getHouseNumber()
        getExtra()
        getZIP()
        if (!requireActivity().navigationView.menu.findItem(R.id.nav_orders).isChecked) {
            requireActivity().navigationView.menu.setGroupCheckable(R.id.gr, true, false)
            requireActivity().navigationView.menu.setGroupCheckable(R.id.nav_orders, true, false)
            requireActivity().navigationView.menu.getItem(2).isCheckable = true
            requireActivity().navigationView.menu.getItem(2).isChecked = true
            requireActivity().toolbar.setBackgroundColor(Color.WHITE)
            requireActivity().tback.setBackgroundColor(Color.WHITE)
            requireActivity().logo.setImageDrawable(resources.getDrawable(R.drawable.logo, null))
            requireActivity().actionBar?.setHomeAsUpIndicator(R.drawable.short_text_black24px)
        }
        recyclerViewOrders.layoutManager = LinearLayoutManager(activity)
        recyclerViewOrders.adapter = AdapterOrders(o)
        save_address.setOnClickListener {
            findNavController().navigate(R.id.nav_orders)
        }
        change_1.setOnClickListener {
            findNavController().navigate(R.id.nav_fill)
        }
        change_2.setOnClickListener {
            findNavController().navigate(R.id.nav_fillInvoice)
        }
        change_3.setOnClickListener {
            findNavController().navigate(R.id.nav_fillInvoice)
        }
        savedReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.saved = snapshot.value as Boolean
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val colorDrawable = ColorDrawable(Color.WHITE)
        activity?.actionBar?.setBackgroundDrawable(colorDrawable)
    }

    override fun onStart() {
        super.onStart()
        mail_contact.text = mAuth.currentUser?.email
        mail_shipping.text = mAuth.currentUser?.email
    }

    override fun onResume() {
        super.onResume()
        mail_contact.text = mAuth.currentUser?.email
        mail_shipping.text = mAuth.currentUser?.email
    }

    private fun getType() {
        val type = databaseUsers.child(id.toString()).child("type")
        type.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                    Global.type = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun getCompanyName() {
        getType()
        val companyName = databaseUsers.child(id.toString()).child("company_name")
        companyName.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    if (Global.type != "1") {
                        Global.companyName = snapshot.value.toString()
                        business_name_shipping.text = Global.companyName
                        business_name.text = Global.companyName
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


    }

    private fun getLastName() {
        val lastName = databaseUsers.child(id.toString()).child("lastname")
        lastName.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.lastName = snapshot.value.toString()
                println("Name:${Global.lastName}")
                (person_name.text.toString() + " " + snapshot.value.toString()).also {
                    person_name.text = it
                }
                (person_name_shipping.text.toString() + " " + snapshot.value.toString()).also {
                    person_name_shipping.text = it
                }
                (account_name2.text.toString() + " " + snapshot.value.toString()).also {
                    account_name2.text = it
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun getCountry() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val country = databaseUsers.child(id.toString()).child("country")
        country.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.country = snapshot.value.toString()
                account_country2.text = snapshot.value.toString()
                (person_address.text.toString() + ", " + snapshot.value.toString()).also {
                    person_address.text = it
                }
                (shipping_address_text.text.toString() + ", " + snapshot.value.toString()).also {
                    shipping_address_text.text = it
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }


    private fun getUsername() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val username = databaseUsers.child(id.toString()).child("name")
        username.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.username = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun getFirstName() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val username = databaseUsers.child(id.toString()).child("firstname")
        username.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.firstName = snapshot.value.toString()
                person_name.text = snapshot.value.toString()
                person_name_shipping.text = snapshot.value.toString()
                account_name2.text = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.toString())
            }
        })
    }

    private fun getStreet() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val street = databaseUsers.child(id.toString()).child("street")
        street.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.street = snapshot.value.toString()
                (person_address.text.toString() + "\n" + snapshot.value.toString()).also {
                    person_address.text = it
                }
                (shipping_address_text.text.toString() + "\n" + snapshot.value.toString()).also {
                    shipping_address_text.text = it
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.toString())
            }
        })
    }

    private fun getCity() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val city = databaseUsers.child(id.toString()).child("city")
        city.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.city = snapshot.value.toString()
                person_address.text = snapshot.value.toString()
                shipping_address_text.text = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.toString())
            }
        })
    }

    private fun getZIP() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val zip = databaseUsers.child(id.toString()).child("postal")
        zip.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.zip = snapshot.value.toString()
                person_name3.text = snapshot.value as String
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.toString())
            }
        })
    }

    private fun getHouseNumber() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val houseNumber = databaseUsers.child(id.toString()).child("house_number")
        houseNumber.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.houseNumber = snapshot.value.toString().toInt()
                (person_address.text.toString() + " " + snapshot.value.toString()).also {
                    person_address.text = it
                }
                (shipping_address_text.text.toString() + " " + snapshot.value.toString()).also {
                    shipping_address_text.text = it
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.toString())
            }
        })
    }

    private fun getExtra() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val extra = databaseUsers.child(id.toString()).child("extra_address")
        extra.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.extra = snapshot.value.toString()
                if (snapshot.value != null) {
                    (person_address.text.toString() + " " + snapshot.value.toString()).also {
                        person_address.text = it
                    }
                    (shipping_address_text.text.toString() + " " + snapshot.value.toString()).also {
                        shipping_address_text.text = it
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.toString())
            }
        })
    }
}