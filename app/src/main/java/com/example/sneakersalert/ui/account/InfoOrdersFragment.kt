package com.example.sneakersalert.ui.account

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.Global
import com.example.sneakersalert.Global.Companion.logged
import com.example.sneakersalert.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_info_orders.*

class InfoOrdersFragment : Fragment(R.layout.fragment_info_orders), LifecycleOwner {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mUser: FirebaseUser
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        if (mAuth.currentUser == null) {
            findNavController().navigate(R.id.nav_menuLogin)
        } else {
            mUser = mAuth.currentUser
            Toast.makeText(this.context, "Welcome, ${mUser.email}", Toast.LENGTH_SHORT).show()
            account_name.text = mUser.email

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
                mAuth.signOut()
                logged = false
                Toast.makeText(this.activity, "Please log in to continue.", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.nav_menuLogin)
            }
            "Your Cart (${Global.p.size})".also { footer.text = it }
            edit_account.setOnClickListener {
                findNavController().navigate(R.id.nav_details)
            }
            footer.setOnClickListener {
                findNavController().navigate(R.id.nav_cart)
            }
        }
    }

}




