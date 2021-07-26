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

    private val mAuth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val validateInput: ValidateInput = ValidateInput(
            this.requireActivity(),
            mail,
            password,
            username,
            username2,
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

    private fun signUp() {
        val validateInput: ValidateInput = ValidateInput(
            this.requireActivity(),
            mail,
            password,
            username,
            username2,
            password2
        )
        val emailVerified: Boolean = validateInput.validateEmail()
        val passVerified: Boolean = validateInput.validatePassword()
        val repeatPasswordVerified = validateInput.validateRepeatPassword()
        val userVerified = validateInput.validateUsername()
        val lastNameVerified=validateInput.validateLastName()
        if (emailVerified && passVerified && repeatPasswordVerified && userVerified && lastNameVerified) {
            email = mail.text.toString().trim()
            Global.firstName = username.text.toString().trim()
            Global.lastName=username2.text.toString().trim()
            passwordText = password.text.toString().trim()
            Global.logged = true
            mAuth.createUserWithEmailAndPassword(email, passwordText).addOnCompleteListener(
                this.requireActivity()
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    saveData()
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

    private fun saveData() {
        val database = FirebaseDatabase.getInstance()
        val rootReference = database.reference
        val user = mAuth.currentUser
        val cityReference=rootReference.child("Users").child(user?.uid.toString()).child("city")
        val countryReference = rootReference.child("Users").child(user?.uid.toString()).child("country")
        val emailReference = rootReference.child("Users").child(user?.uid.toString()).child("mail")
        val passReference =
            rootReference.child("Users").child(user?.uid.toString()).child("password")
        val savedReference = rootReference.child("Users").child(user?.uid.toString()).child("saved")
        val savedReference2 = rootReference.child("Users").child(user?.uid.toString()).child("saved_2")
        val savedReference3 = rootReference.child("Users").child(user?.uid.toString()).child("saved_3")
        val birthdayReference =
            rootReference.child("Users").child(user?.uid.toString()).child("birthday")
        val typeCustomerReference =
            rootReference.child("Users").child(user?.uid.toString()).child("type")
        val phoneReference = rootReference.child("Users").child(user?.uid.toString()).child("phone")
        val companyNameReference =
            rootReference.child("Users").child(user?.uid.toString()).child("company_name")
        val houseNumberReference=rootReference.child("Users").child(user?.uid.toString()).child("house_number")
        val vatReference = rootReference.child("Users").child(user?.uid.toString()).child("VAT")
        val taxNumberReference =
            rootReference.child("Users").child(user?.uid.toString()).child("tax_number")
        val lastNameReference =
            rootReference.child("Users").child(user?.uid.toString()).child("lastname")
        val mailReference = rootReference.child("Users").child(user?.uid.toString()).child("mail")
        val firstNameReference =
            rootReference.child("Users").child(user?.uid.toString()).child("firstname")
        val extraInvReference=rootReference.child("Users").child(user?.uid.toString()).child("extra_address")
        val postalReference=rootReference.child("Users").child(user?.uid.toString()).child("postal")
        val streetInvReference =
            rootReference.child("Users").child(user?.uid.toString()).child("street_invoice")
        val streetReference =
            rootReference.child("Users").child(user?.uid.toString()).child("street")
        val countryInvReference =
            rootReference.child("Users").child(user?.uid.toString()).child("country_invoice")
        val zipCodeReference =
            rootReference.child("Users").child(user?.uid.toString()).child("postal_invoice")
        val cityInvReference = rootReference.child("Users").child(user?.uid.toString()).child("city_invoice")
        val savedInvReference =
            rootReference.child("Users").child(user?.uid.toString()).child("saved_3")
        val houseNumberInvReference =
            rootReference.child("Users").child(user?.uid.toString()).child("house_number_invoice")
        val extraReference =
            rootReference.child("Users").child(user?.uid.toString()).child("extra_address_invoice")
        val wishlistReference=rootReference.child("Wishlist")
        val cartReference=rootReference.child("Cart")
        countryReference.setValue("")
        countryInvReference.setValue("")
        firstNameReference.setValue("")
        mailReference.setValue("")
        taxNumberReference.setValue("")
        companyNameReference.setValue("")
        typeCustomerReference.setValue("1")
        birthdayReference.setValue("")
        phoneReference.setValue("")
        cityReference.setValue("")
        cityInvReference.setValue("")
        lastNameReference.setValue("")
        vatReference.setValue("")
        postalReference.setValue("")
        zipCodeReference.setValue("")
        extraReference.setValue("")
        houseNumberReference.setValue(0)
        extraInvReference.setValue("")
        houseNumberInvReference.setValue(0)
        streetReference.setValue("")
        streetInvReference.setValue("")
        savedReference.setValue(false)
        savedReference2.setValue(false)
        savedReference3.setValue(false)
        passReference.setValue(password.text.toString().trim())
        emailReference.setValue(mail.text.toString().trim())
        firstNameReference.setValue(username.text.toString().trim())
        lastNameReference.setValue(username2.text.toString().trim())
        Global.username = username.text.toString().trim()

        wishlistReference.push()
        cartReference.push()
        cityReference.push()
        postalReference.push()
        extraReference.push()
        countryReference.push()
        firstNameReference.push()
        mailReference.push()
        taxNumberReference.push()
        companyNameReference.push()
        typeCustomerReference.push()
        birthdayReference.push()
        phoneReference.push()
        lastNameReference.push()
        vatReference.push()
        houseNumberReference.push()
        savedReference.push()
        savedReference2.push()
        savedReference3.push()
        passReference.push()
        emailReference.push()
        firstNameReference.push()
        cityInvReference.push()
        zipCodeReference.push()
        extraInvReference.push()
        houseNumberInvReference.push()
        streetReference.push()
        countryInvReference.push()
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