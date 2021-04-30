
package com.example.sneakersalert

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var drawerToggle: ActionBarDrawerToggle
    var open = false

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint(
        "RestrictedApi", "UseCompatLoadingForDrawables", "ResourceAsColor",
        "ResourceType", "NewApi", "RtlHardcoded", "SetTextI18n"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
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
        navigationView.isVerticalScrollBarEnabled = true
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_sneakers,
                R.id.nav_airmax1,
                R.id.nav_jordan,
                R.id.nav_airmax90,
                R.id.nav_faq,
                R.id.nav_cart,
                R.id.nav_menuLogin,
                R.id.nav_signup,
                R.id.nav_login,
                R.id.nav_orders,
                R.id.nav_wishlist
            ), drawerLayout
        )
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        setupActionBarWithNavController(navController)
        navigationView.setupWithNavController(navController)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        actionBar?.setDisplayShowCustomEnabled(true)
        toolbar.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, _, _ ->
            toolbar.setNavigationIcon(R.drawable.short_text_24px)
            if (navController.currentDestination?.id == R.id.nav_search) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                println("yes")
            }
            navigationView.setItemBackgroundResource(R.drawable.nav_view_select)
            navigationView.setItemTextAppearance(R.color.alert)
            if (navController.currentDestination?.id == R.id.nav_home) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                findViewById<ConstraintLayout>(R.id.tback).setBackgroundColor(R.color.alert)
                toolbar.setBackgroundColor(R.color.alert)
                supportActionBar?.setBackgroundDrawable(ColorDrawable(R.color.alert))
                actionBar?.setBackgroundDrawable(ColorDrawable(R.color.alert))
            } else {
                findViewById<ConstraintLayout>(R.id.tback).setBackgroundColor(R.color.alert)
                toolbar.setBackgroundColor(R.color.alert)
                supportActionBar?.setBackgroundDrawable(ColorDrawable(R.color.alert))
                actionBar?.setBackgroundDrawable(ColorDrawable(R.color.alert))
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
            open = false
            if (navController.currentDestination?.id == R.id.nav_cart) {
                supportActionBar?.setDisplayShowTitleEnabled(true)
                toolbar.setTitleTextColor(Color.WHITE)
                findViewById<ConstraintLayout>(R.id.tback).setBackgroundColor(Color.WHITE)
                supportActionBar?.setDisplayShowTitleEnabled(true)
                your_cart.visibility = View.VISIBLE
                logo.visibility = View.INVISIBLE
                shop.visibility = View.INVISIBLE
            } else {
                your_cart.visibility = View.INVISIBLE
                logo.visibility = View.VISIBLE
                shop.visibility = View.VISIBLE
            }
            if (navController.currentDestination?.id == R.id.nav_login) {
                navigationView.menu.getItem(2).isCheckable = true
                navigationView.menu.getItem(2).isChecked = true
                navigationView.setCheckedItem(R.id.nav_menuLogin)
                your_cart.visibility = View.INVISIBLE
                logo.visibility = View.INVISIBLE
                shop.visibility = View.INVISIBLE
                text_login.text = "Log In"
                text_login.visibility = View.VISIBLE

            }
            if (navController.currentDestination?.id == R.id.nav_signup) {
                your_cart.visibility = View.INVISIBLE
                logo.visibility = View.INVISIBLE
                shop.visibility = View.INVISIBLE
                text_login.text = "Sign Up"
                text_login.visibility = View.VISIBLE
            }
            if (navController.currentDestination?.id == R.id.nav_orders) {
                navigationView.menu.getItem(2).isCheckable = true
                navigationView.menu.getItem(2).isChecked = true
                navigationView.setCheckedItem(R.id.nav_menuLogin)
                your_cart.visibility = View.INVISIBLE
                logo.visibility = View.INVISIBLE
                shop.visibility = View.INVISIBLE
                text_login.text = "My Account"
                text_login.visibility = View.VISIBLE

            }
            if (navController.currentDestination?.id == R.id.nav_details) {
                navigationView.menu.getItem(2).isCheckable = true
                navigationView.menu.getItem(2).isChecked = true
                navigationView.setCheckedItem(R.id.nav_menuLogin)
                your_cart.visibility = View.INVISIBLE
                logo.visibility = View.INVISIBLE
                shop.visibility = View.INVISIBLE
                text_login.text = "Account"
                text_login.visibility = View.VISIBLE

            }
            if (navController.currentDestination?.id != R.id.nav_signup
                && navController.currentDestination?.id != R.id.nav_login
                && navController.currentDestination?.id != R.id.nav_orders
                && navController.currentDestination?.id != R.id.nav_details
            ) {
                text_login.visibility = View.INVISIBLE
            }
        }

        if (navController.currentDestination?.id != R.id.nav_search) {
            toolbar.setNavigationOnClickListener {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.back2)
                toolbar.setNavigationIcon(R.drawable.back2)
                if (navController.currentDestination?.id == R.id.nav_home) {
                    actionBar?.setBackgroundDrawable(ColorDrawable(R.color.alert))
                } else {
                    toolbar.popupTheme = R.style.NavigationViewStyle
                    actionBar?.setBackgroundDrawable(ColorDrawable(R.color.alert))
                }
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
                onSupportNavigateUp()
            }

        }


        findViewById<ImageView>(R.id.shop).setOnClickListener {
            navController.navigate(R.id.nav_cart)
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onBackPressed() {

    }

    fun changeFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.nav_host_fragment, fragment).addToBackStack(null)
        ft.commit()
    }

    @SuppressLint("SetTextI18n")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu this adds items to the action bar if it is present.

        return true
    }

    override fun onSupportNavigateUp(): Boolean {

        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun lockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun unlockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }



}




