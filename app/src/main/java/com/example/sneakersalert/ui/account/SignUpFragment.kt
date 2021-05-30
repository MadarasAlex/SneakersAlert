package com.example.sneakersalert.ui.account

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.Global
import com.example.sneakersalert.Interfaces.OnBack
import com.example.sneakersalert.R
import com.example.sneakersalert.ui.account.login.ValidateInput
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUpFragment : Fragment(R.layout.fragment_sign_up), OnBack {


    lateinit var email: String
    lateinit var passwordText: String
    lateinit var user: String

// ...
// Initialize Firebase Auth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val validateInput: ValidateInput = ValidateInput(
            this.requireActivity(),
            mail,
            password,
            username,
            password2
        )
        register.setOnClickListener {
            signUp()

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

    }

    fun signUp() {
        val validateInput: ValidateInput = ValidateInput(
            this.requireActivity(),
            mail,
            password,
            username,
            password2
        )
        val emailVerified: Boolean = validateInput.validateEmail()
        val passVerified: Boolean = validateInput.validatePassword()
        val repeatPasswordVerified = validateInput.validateRepeatPassword()
        val userVerified = validateInput.validateUsername()
        if (emailVerified && passVerified && repeatPasswordVerified && userVerified) {
            email = mail.text.toString().trim()
            Global.username = username.text.toString().trim()
            passwordText = password.text.toString().trim()
            Global.logged = true
            findNavController().navigate(R.id.nav_orders)

        } else {
            // If sign in fails, display a message to the user.
            Toast.makeText(
                activity, "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()

        }
                }


    /* fun loadingAnimation()
     {
         val builder=AlertDialog.Builder(this.activity)
         val inflater=layoutInflater
         builder.setView(inflater.inflate(R.layout.loading_screen,null))
         builder.setCancelable(false)
         val dialog: AlertDialog =builder.create()
         dialog.show()
     }

     */
    override fun onBackPressed() {
        onBackPressed()
        findNavController().navigate(R.id.nav_menuLogin)
    }


}