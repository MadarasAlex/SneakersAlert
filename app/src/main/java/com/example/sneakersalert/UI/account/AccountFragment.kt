package com.example.sneakersalert.UI.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.sneakersalert.Global.Companion.logged
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment(R.layout.fragment_account) {
    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (logged) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment, InfoOrdersFragment())?.commit()
        }
        activity.let {
            login_button.setOnClickListener {
                println("here")
                logged = true
                println(logged)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.nav_host_fragment, InfoOrdersFragment())?.commit()
            }
        }
    }

}



