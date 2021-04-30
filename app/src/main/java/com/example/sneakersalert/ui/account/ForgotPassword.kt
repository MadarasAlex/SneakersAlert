package com.example.sneakersalert.ui.account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sneakersalert.R

class ForgotPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        setSupportActionBar(findViewById(R.id.toolbar))


    }
}