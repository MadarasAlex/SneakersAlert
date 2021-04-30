package com.example.sneakersalert.ui.account.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.Global
import com.example.sneakersalert.Interfaces.OnBack
import com.example.sneakersalert.R
import com.example.sneakersalert.ui.login.LoggedInUserView
import com.example.sneakersalert.ui.login.LoginViewModel
import com.example.sneakersalert.ui.login.LoginViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(R.layout.fragment_login), OnBack {

    private lateinit var loginViewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory()).get(LoginViewModel::class.java)
        val usernameEditText = view.findViewById<EditText>(R.id.mail)
        val passwordEditText = view.findViewById<EditText>(R.id.password)
        val loginButton = view.findViewById<Button>(R.id.loginB)
        val loadingProgressBar = view.findViewById<ProgressBar>(R.id.loading)
        requireActivity().navigationView.setCheckedItem(R.id.nav_orders)
        if (!requireActivity().navigationView.menu.findItem(R.id.nav_orders).isChecked) {
            requireActivity().navigationView.menu.setGroupCheckable(R.id.gr, true, false)
            requireActivity().navigationView.menu.setGroupCheckable(R.id.nav_orders, true, false)
            requireActivity().navigationView.menu.getItem(2).isCheckable = true
            requireActivity().navigationView.menu.getItem(2).isChecked = true
        }
        loginB.setOnClickListener {
            println("here2")
            Global.logged = true
            findNavController().navigate(R.id.nav_orders)
            println("here3")
        }
        /*    loginViewModel.loginFormState.observe(this.requireActivity(),
               Observer { loginFormState ->
                   if (loginFormState == null) {
                       return@Observer
                   }
                   loginButton.isEnabled = loginFormState.isDataValid
                   loginFormState.usernameError?.let {
                       usernameEditText.error = getString(it)
                   }
                   loginFormState.passwordError?.let {
                       passwordEditText.error = getString(it)
                   }
               })

           loginViewModel.loginResult.observe(this.requireActivity(),
               Observer { loginResult ->
                   loginResult ?: return@Observer
                   loadingProgressBar.visibility = View.GONE
                   loginResult.error?.let {
                       showLoginFailed(it)
                   }
                   loginResult.success?.let {
                       updateUiWithUser(it)
                   }
               })

           val afterTextChangedListener = object : TextWatcher {
               override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                   // ignore
               }

               override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                   // ignore
               }

               override fun afterTextChanged(s: Editable) {
                   loginViewModel.loginDataChanged(
                       usernameEditText.text.toString(),
                       passwordEditText.text.toString()
                   )
               }
           }
           usernameEditText.addTextChangedListener(afterTextChangedListener)
           passwordEditText.addTextChangedListener(afterTextChangedListener)
           passwordEditText.setOnEditorActionListener { _, actionId, _ ->
               if (actionId == EditorInfo.IME_ACTION_DONE) {
                   loginViewModel.login(
                       usernameEditText.text.toString(),
                       passwordEditText.text.toString()
                   )
               }
               false
           }


            */
        /*     loginButton.setOnClickListener {
                 loadingProgressBar.visibility = View.VISIBLE
               /* loginViewModel.login(
                     usernameEditText.text.toString(),
                     passwordEditText.text.toString()
                 )

                */
                 Global.logged=true
                 findNavController().navigate(R.id.nav_orders)
                 println("here1")
             }
     */
        signup_action.setOnClickListener {
            findNavController().navigate(R.id.nav_signup)
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        // TODO : initiate successful logged in experience
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        findNavController().navigate(R.id.nav_menuLogin)
    }

}
