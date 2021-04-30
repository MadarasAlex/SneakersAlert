package com.example.sneakersalert.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.sneakersalert.MainActivity
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        logo.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.nav_default_enter_anim
            )
        )
        logo.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.nav_default_pop_exit_anim
            )
        )
        val SPLASH_TIME_OUT: Long = 1000
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)

        }, SPLASH_TIME_OUT)
    }
}
