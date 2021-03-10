package com.example.sneakersalert.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.R
import com.example.sneakersalert.ui.login.ui.login.LoginViewModel

class AccountFragment : Fragment(R.layout.fragment_account) {
    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }

    private val userViewModel: LoginViewModel by activityViewModels()
    private lateinit var savedStateHandle: SavedStateHandle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle.set(LOGIN_SUCCESSFUL, false)

        val usernameEditText = view.findViewById<TextView>(R.id.username)
        val passwordEditText = view.findViewById<TextView>(R.id.password)
        val loginButton = view.findViewById<TextView>(R.id.login)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            Toast.makeText(this.activity,"Wrong credentials!",Toast.LENGTH_SHORT).show()
        }
    }



    fun showErrorMessage() {
        // Display a snackbar error message
    }
}