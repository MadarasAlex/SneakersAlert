package com.example.sneakersalert.ui.account

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    val mAuth = FirebaseAuth.getInstance()
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseUsers = database.getReference("Users")
    val id = mAuth.currentUser?.uid
    val rootReference = database.reference
    val user = mAuth.currentUser
    val savedReference = rootReference.child("Users").child(user?.uid.toString()).child("saved")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        o.add(Order(Global.orderNumber, Date(2021 / 1 / 20), "A.M.", 150.00, "VIEW"))
        o.add(Order(Global.orderNumber, Date(2021 / 2 / 2), "A.M.", 250.00, "VIEW"))
        o.add(Order(Global.orderNumber, Date(2021 / 3 / 20), "A.M.", 150.00, "VIEW"))
        o.add(Order(Global.orderNumber, Date(2021 / 4 / 4), "A.M.", 50.00, "VIEW"))
        o.add(Order(Global.orderNumber, Date(2021 / 4 / 20), "A.M.", 550.00, "VIEW"))
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val databaseUsers = database.getReference("Users")
        val companyName = databaseUsers.child(id.toString()).child("company_name")
        val id = mAuth.currentUser?.uid
        companyName.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.companyName = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        val lastName = databaseUsers.child(id.toString()).child("lastname")
        lastName.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.lastName = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        val type = databaseUsers.child(id.toString()).child("type")

        type.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.type = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


        return super.onCreateView(inflater, container, savedInstanceState)
    }
    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val colorDrawable = ColorDrawable(Color.WHITE)
        if (business_name.text.isNullOrBlank()) {
            business_name.text = ""
        }
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

        getLastName()

        val type = databaseUsers.child(id.toString()).child("type")
        type.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.type = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        if (Global.type != 1) {

            val companyName = databaseUsers.child(id.toString()).child("company_name")
            companyName.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Global.companyName = snapshot.value.toString()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

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
            findNavController().navigate(R.id.nav_fillAddress)
        }
        change_3.setOnClickListener {
            findNavController().navigate(R.id.nav_fillInvoice)
        }

        savedReference.addValueEventListener(object : ValueEventListener {
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
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        if (business_name.text.isNullOrBlank()) {
            business_name.text = ""
        }
        val lastName = databaseUsers.child(id.toString()).child("lastname")
        lastName.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.lastName = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        val type = databaseUsers.child(id.toString()).child("type")
        type.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.type = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        if (Global.type != 1) {
            val companyName = databaseUsers.child(id.toString()).child("company_name")

            companyName.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    Global.companyName = snapshot.value.toString()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

        mail_contact.text = mAuth.currentUser?.email
        mail_shipping.text = mAuth.currentUser?.email
        business_name_shipping.text = Global.companyName
        business_name.text = Global.companyName
        person_name.text = Global.username + " " + Global.lastName
        person_name_shipping.text = Global.username + " " + Global.lastName
        println(Global.lastName)
        account_name2.text = Global.username
        account_country2.text = Global.country
    }

    override fun onResume() {
        super.onResume()
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val lastName = databaseUsers.child(id.toString()).child("lastname")
        lastName.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.lastName = snapshot.value.toString()
                println("Name:${Global.lastName}")
                person_name_shipping.text = Global.username + " " + snapshot.value.toString()
                person_name.text = Global.username + " " + snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        if (business_name.text == null) {
            business_name.text = ""
        }

        val type = databaseUsers.child(id.toString()).child("type")

        type.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.type = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        if (Global.type != 1) {
            val companyName = databaseUsers.child(id.toString()).child("company_name")

            companyName.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Global.companyName = snapshot.value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        }

        mail_contact.text = mAuth.currentUser?.email
        mail_shipping.text = mAuth.currentUser?.email
        business_name_shipping.text = Global.companyName
        business_name.text = Global.companyName
        person_name.text = Global.username + " " + Global.lastName
        person_name_shipping.text = Global.username + " " + Global.lastName
        println(Global.lastName)
        account_name2.text = Global.username
        account_country2.text = Global.country

    }

    fun getType() {
        val type = databaseUsers.child(id.toString()).child("type")

        type.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.type = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getCompanyName() {
        val companyName = databaseUsers.child(id.toString()).child("company_name")

        companyName.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.companyName = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getLastName() {
        val lastName = databaseUsers.child(id.toString()).child("lastname")
        lastName.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.lastName = snapshot.value.toString()
                println("Name:${Global.lastName}")
                person_name_shipping.text = Global.username + " " + snapshot.value.toString()
                person_name.text = Global.username + " " + snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}