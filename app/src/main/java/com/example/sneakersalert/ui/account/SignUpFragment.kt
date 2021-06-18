package com.example.sneakersalert.ui.account

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.Global
import com.example.sneakersalert.Interfaces.OnBack
import com.example.sneakersalert.R
import com.example.sneakersalert.ui.account.login.ValidateInput
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUpFragment : Fragment(R.layout.fragment_sign_up), OnBack {


    lateinit var email: String
    lateinit var passwordText: String

    val mAuth = FirebaseAuth.getInstance()

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
            mAuth.createUserWithEmailAndPassword(email, passwordText).addOnCompleteListener(
                this.requireActivity()
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    saveData()
                    val user = mAuth.currentUser
                    findNavController().navigate(R.id.nav_orders)
                } else {
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        layoutInflater.context,
                        "Authentication Failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
    }

    fun saveData() {

        val database = FirebaseDatabase.getInstance()
        val rootReference = database.reference
        val user = mAuth.currentUser
        val nameReference = rootReference.child("Users").child(user?.uid.toString()).child("name")
        val emailReference = rootReference.child("Users").child(user?.uid.toString()).child("mail")
        val passReference =
            rootReference.child("Users").child(user?.uid.toString()).child("password")
        passReference.setValue(password.text.toString().trim())
        emailReference.setValue(mail.text.toString().trim())
        nameReference.setValue(username.text.toString().trim())
        Global.username = username.text.toString().trim()
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