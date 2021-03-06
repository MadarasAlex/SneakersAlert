package com.example.sneakersalert.ui.account.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.DataClasses.ProductCart
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(R.layout.fragment_login) {
    val mAuth = FirebaseAuth.getInstance()
    lateinit var email: String
    lateinit var passwordText: String
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val usernameEditText = view.findViewById<TextInputEditText>(R.id.mail)
        val passwordEditText = view.findViewById<TextInputEditText>(R.id.password)
        val validateInput = ValidateInput(this.requireActivity(), usernameEditText, passwordEditText)
        requireActivity().navigationView.setCheckedItem(R.id.nav_orders)
        if (!requireActivity().navigationView.menu.findItem(R.id.nav_orders).isChecked) {
            requireActivity().navigationView.menu.setGroupCheckable(R.id.gr, true, false)
            requireActivity().navigationView.menu.setGroupCheckable(R.id.nav_orders, true, false)
            requireActivity().navigationView.menu.getItem(2).isCheckable = true
            requireActivity().navigationView.menu.getItem(2).isChecked = true
        }
        loginB.setOnClickListener {
            login()
        }

    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    fun onBackPressed() {
        findNavController().navigate(R.id.nav_menuLogin)
    }

    fun login() {
        println("here")

        val usernameEditText = view?.findViewById<TextInputEditText>(R.id.mail)
        val passwordEditText = view?.findViewById<TextInputEditText>(R.id.password)
        val validateInput = ValidateInput(
            this.requireActivity(),
            usernameEditText!!,
            passwordEditText!!
        )
        val emailVerified: Boolean = validateInput.validateEmail()
        val passVerified: Boolean = validateInput.validatePassword()
        println("Mail:" + emailVerified)
        println("Pass:" + passVerified)
        if (emailVerified && passVerified) {
            email = usernameEditText.text.toString().trim()
            passwordText = passwordEditText.text.toString().trim()
            mAuth.signInWithEmailAndPassword(email, passwordText).addOnCompleteListener(
                this.requireActivity()
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth.currentUser
                    println("Mail entered:$email")
                    val databaseUsers = database.getReference("Users")
                    val id = mAuth.currentUser?.uid
                    val username = databaseUsers.child(id.toString()).child("name")
                    username.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val userString = snapshot.value.toString()
                            Global.username=userString
                            println(userString)
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                    username.runTransaction(object : Transaction.Handler {
                        override fun doTransaction(currentData: MutableData): Transaction.Result {
                            val name = currentData.getValue(String::class.java)
                            println(name)
                            return Transaction.success(currentData)
                        }

                        override fun onComplete(
                            error: DatabaseError?,
                            committed: Boolean,
                            currentData: DataSnapshot?
                        ) {
                            val nameUser = currentData?.getValue(String::class.java)
                            if (nameUser != null) {
                                Global.username = nameUser
                            }
                        }
                    })
                    println(username)
                    val database = FirebaseDatabase.getInstance()
                    val rootReference = database.reference
                    val savedReference =
                        rootReference.child("Users").child(mAuth.currentUser!!.uid).child("saved")
                    savedReference.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            Global.saved = snapshot.value as Boolean
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                    if (Global.saved) {

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
                                Global.type = snapshot.getValue(String::class.java).toString()
                            }

                            override fun onCancelled(error: DatabaseError) {
                            }
                        })

                        if (Global.type != "1") {
                            val companyName =
                                databaseUsers.child(id.toString()).child("company_name")

                            companyName.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {

                                    Global.companyName = snapshot.value.toString()
                                }

                                override fun onCancelled(error: DatabaseError) {

                                }
                            })
                        }
                    }
                    Global.logged = true
                    getData()
                    findNavController().navigate(R.id.nav_orders)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        layoutInflater.context,
                        "Authentication Failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
        signup_action.setOnClickListener {
            findNavController().navigate(R.id.nav_signup)
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
                    for (el in snapshot.children) {
                        val name = el.child("name").getValue(String::class.java)
                        val model = el.child("model").getValue(String::class.java)
                        val price = el.child("price").getValue(Int::class.java)
                        val image = el.child("image").getValue(String::class.java)
                        val amount = el.child("amount").getValue(Int::class.java)
                        val size = el.child("size").getValue(Int::class.java)
                        val id=el.child("id").getValue(Int::class.java)
                        val item = ProductCart(image!!, name!!, model!!, price!!, size!!, amount!!,id!!)
                        println(item)
                        Global.p.add(item)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}