package com.example.sneakersalert.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.Global
import com.example.sneakersalert.Global.Companion.logged
import com.example.sneakersalert.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_info_orders.*

class InfoOrdersFragment : Fragment(R.layout.fragment_info_orders), LifecycleOwner {

    val mAuth = FirebaseAuth.getInstance()
    val mUser = mAuth.currentUser
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mUser == null) {
            findNavController().navigate(R.id.nav_menuLogin)
        } else {
            getUsername()
            getCountry()
            if (!requireActivity().navigationView.menu.findItem(R.id.nav_orders).isChecked) {
                requireActivity().navigationView.menu.setGroupCheckable(R.id.gr, true, false)
                requireActivity().navigationView.menu.setGroupCheckable(
                    R.id.nav_orders,
                    true,
                    false
                )
                requireActivity().navigationView.menu.getItem(2).isCheckable = true
                requireActivity().navigationView.menu.getItem(2).isChecked = true

            }
            "Your Cart (${Global.p.size})".also { footer.text = it }
            account_name.text = Global.username
            account_country.text = Global.country
            edit_account.setOnClickListener {
                if (account_name.text != null && account_country.text != null)
                    findNavController().navigate(R.id.nav_details)
            }
            footer.setOnClickListener {
                findNavController().navigate(R.id.nav_cart)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        if (mUser == null) {
            findNavController().navigate(R.id.nav_menuLogin)
        } else {
            getUsername()
            getCountry()
            if (!requireActivity().navigationView.menu.findItem(R.id.nav_orders).isChecked) {
                requireActivity().navigationView.menu.setGroupCheckable(R.id.gr, true, false)
                requireActivity().navigationView.menu.setGroupCheckable(
                    R.id.nav_orders,
                    true,
                    false
                )
                requireActivity().navigationView.menu.getItem(2).isCheckable = true
                requireActivity().navigationView.menu.getItem(2).isChecked = true

            }
            "Your Cart (${Global.p.size})".also { footer.text = it }
            account_name.text = Global.username
            account_country.text = Global.country
            edit_account.setOnClickListener {
                if (account_name.text != null && account_country.text != null)
                    findNavController().navigate(R.id.nav_details)
            }
            footer.setOnClickListener {
                findNavController().navigate(R.id.nav_cart)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        "Your Cart (${Global.p.size})".also { footer.text = it }
        if (mUser == null) {
            findNavController().navigate(R.id.nav_menuLogin)
        } else {
            getUsername()
            getCountry()
            if (!requireActivity().navigationView.menu.findItem(R.id.nav_orders).isChecked) {
                requireActivity().navigationView.menu.setGroupCheckable(R.id.gr, true, false)
                requireActivity().navigationView.menu.setGroupCheckable(
                    R.id.nav_orders,
                    true,
                    false
                )
                requireActivity().navigationView.menu.getItem(2).isCheckable = true
                requireActivity().navigationView.menu.getItem(2).isChecked = true

            }
            val logoutButton = requireActivity().logout
            logoutButton.setOnClickListener {
                logged = false
                mAuth.signOut()
                findNavController().navigate(R.id.nav_menuLogin)
            }

            edit_account.setOnClickListener {
                if (account_name.text != null && account_country.text != null)
                    findNavController().navigate(R.id.nav_details)
            }
            footer.setOnClickListener {
                findNavController().navigate(R.id.nav_cart)
            }
        }
    }

    private fun getCountry() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid

        val country = databaseUsers.child(id.toString()).child("country")
        country.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                account_country.text = snapshot.value.toString()
                Global.country = snapshot.value.toString()
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
                account_name.text = snapshot.value.toString()
                Global.username = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}







