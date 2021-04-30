package com.example.sneakersalert.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.Interfaces.OnBack
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUpFragment : Fragment(R.layout.fragment_sign_up), OnBack {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        register.setOnClickListener {

        }
        login_action.setOnClickListener {
            findNavController().navigate(R.id.nav_login)
        }
        if (!requireActivity().navigationView.menu.findItem(R.id.nav_orders).isChecked) {
            requireActivity().navigationView.menu.setGroupCheckable(R.id.gr, true, false)
            requireActivity().navigationView.menu.setGroupCheckable(R.id.nav_orders, true, false)
            requireActivity().navigationView.menu.getItem(2).isCheckable = true
            requireActivity().navigationView.menu.getItem(2).isChecked = true
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onBackPressed() {
        onBackPressed()
        findNavController().navigate(R.id.nav_menuLogin)
    }


}