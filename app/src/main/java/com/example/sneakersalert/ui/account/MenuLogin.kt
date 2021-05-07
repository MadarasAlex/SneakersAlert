package com.example.sneakersalert.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_menu_login.*

class MenuLogin : Fragment(R.layout.fragment_menu_login) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_option.setOnClickListener {
            findNavController().navigate(R.id.nav_login)
        }
        signup_option.setOnClickListener {
            findNavController().navigate(R.id.nav_signup)
        }
    }

    override fun onStart() {
        super.onStart()
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
        Toast.makeText(this.activity, "Please log in to continue.", Toast.LENGTH_SHORT).show()
    }
}