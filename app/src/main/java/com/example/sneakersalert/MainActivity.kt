package com.example.sneakersalert


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.view.GravityCompat
import androidx.core.view.isInvisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var drawerLayout:DrawerLayout
    lateinit var drawerToggle: ActionBarDrawerToggle
    var open=false
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("RestrictedApi", "UseCompatLoadingForDrawables", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout=findViewById(R.id.drawer_layout)

        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setActionBar(Toolbar(applicationContext))
        val navigationView=findViewById<NavigationView>(R.id.navigationView)
        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(drawerToggle)

        drawerToggle.syncState()
        navigationView.bringToFront()
        navigationView.isVerticalScrollBarEnabled=true
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = findNavController(R.id.nav_host_fragment)


        appBarConfiguration=AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_sneakers,
                R.id.nav_airmax1,
                R.id.nav_jordan,
                R.id.nav_airmax90,
                R.id.nav_faq,
                R.id.nav_account
            ), drawerLayout
        )

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)
        actionBar?.setDisplayShowCustomEnabled(true);

        findViewById<ImageView>(R.id.search).isInvisible=false
        navController.addOnDestinationChangedListener { _, _, _ ->
           toolbar.setNavigationIcon(R.drawable.short_text_24px)

            bottomNavigationView.isInvisible=navController.currentDestination?.id==R.id.nav_faq
            if(navController.currentDestination?.id==R.id.nav_home)
            {
                findViewById<ConstraintLayout>(R.id.tback).setBackgroundColor(R.color.gray)

                toolbar.setBackgroundColor(R.color.gray)
                supportActionBar?.setBackgroundDrawable(ColorDrawable(R.color.gray))
                actionBar?.setBackgroundDrawable(ColorDrawable(R.color.gray))
            }
            else
            {

                findViewById<ConstraintLayout>(R.id.tback).setBackgroundColor(R.color.white)

                toolbar.setBackgroundColor(R.color.white)
                supportActionBar?.setBackgroundDrawable(ColorDrawable(R.color.white))
                actionBar?.setBackgroundDrawable(ColorDrawable(R.color.white))
            }
            open=false
        }
        toolbar.setNavigationOnClickListener {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.back)

            toolbar.setNavigationIcon(R.drawable.back)
            if(navController.currentDestination?.id==R.id.nav_home)
            {

                actionBar?.setBackgroundDrawable(ColorDrawable(R.color.gray))
            }
            else
            {
                toolbar.popupTheme = R.style.NavigationViewStyle

                actionBar?.setBackgroundDrawable(ColorDrawable(R.color.white))
            }

            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)

            onSupportNavigateUp()
        }
        findViewById<ImageView>(R.id.shop).setOnClickListener {
            val i=Intent(this, CartActivity::class.java)
            startActivity(i)
        }


    }

    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.short_text_24px)
            super.onBackPressed()
        }
    }

    fun changeFragment(fragment: Fragment){



        var ft=supportFragmentManager.beginTransaction()
        ft.replace(R.id.nav_host_fragment, fragment).addToBackStack(null)
        ft.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true
    }

    override fun onSupportNavigateUp(): Boolean {

        val navController = findNavController(R.id.nav_host_fragment)


        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onResume() {

        super.onResume()
    }

    override fun onRestart() {

        super.onRestart()
    }
 

}


