
package com.example.sneakersalert

import Repositories.OnSwipeTouchListener
import android.annotation.SuppressLint
import android.graphics.Color
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
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header.view.*


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
                R.id.nav_wishlist,
                R.id.nav_request
            ), drawerLayout
        )
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        setupActionBarWithNavController(navController)
        navigationView.setupWithNavController(navController)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        actionBar?.setDisplayShowCustomEnabled(true)
        toolbar.setupWithNavController(navController)
        drawerLayout.setOnTouchListener(@SuppressLint("ClickableViewAccessibility")
        object : OnSwipeTouchListener(this) {
            @SuppressLint("ClickableViewAccessibility")
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
            }
        })
        navigationView.getHeaderView(0).search.setOnClickListener {
            navController.navigate(R.id.nav_search)
        }
        navController.addOnDestinationChangedListener { _, _, _ ->
            toolbar.setNavigationIcon(R.drawable.short_text_24px)
            if (navController.currentDestination?.id == R.id.nav_search) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }
            navigationView.setItemBackgroundResource(R.drawable.nav_view_select)
            navigationView.setItemTextAppearance(R.color.alert)
            open = false

            if (navController.currentDestination?.id == R.id.nav_orders)
                logout.visibility = View.VISIBLE
            else logout.visibility = View.INVISIBLE
            if (navController.currentDestination?.id == R.id.nav_cart) {
                supportActionBar?.setDisplayShowTitleEnabled(true)
                toolbar.setTitleTextColor(Color.WHITE)
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
                text_login.visibility = View.INVISIBLE

            }
            if (navController.currentDestination?.id == R.id.nav_payment) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                text_login.setTextColor(Color.BLACK)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.short_text_black24px)
                toolbar.setNavigationIcon(R.drawable.short_text_black24px)
                toolbar.navigationIcon?.setTint(Color.BLACK)
                toolbar.setBackgroundColor(Color.WHITE)
                your_cart.visibility = View.VISIBLE
                your_cart.setTextColor(Color.BLACK)
                your_cart.text = "Checkout"
                logo.visibility = View.INVISIBLE
                shop.visibility = View.INVISIBLE
                toolbar.background.setTint(Color.WHITE)
                tback.background.setTint(Color.WHITE)
                tback.setBackgroundColor(Color.WHITE)


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
            if (navController.currentDestination?.id == R.id.nav_menuLogin
                || navController.currentDestination?.id == R.id.nav_login
                || navController.currentDestination?.id == R.id.nav_signup
            )
                logo.visibility = View.INVISIBLE
            if (navController.currentDestination?.id != R.id.nav_signup
                && navController.currentDestination?.id != R.id.nav_login
                && navController.currentDestination?.id != R.id.nav_orders
                && navController.currentDestination?.id != R.id.nav_details
            ) {
                text_login.visibility = View.INVISIBLE
            }
            if (navController.currentDestination?.id == R.id.nav_signup)
                text_login.setTextColor(Color.WHITE)
            if (navController.currentDestination?.id == R.id.nav_login)
                toolbar.elevation = 0F
            else
                toolbar.elevation = 2F
            if (navController.currentDestination?.id != R.id.nav_fill
                && navController.currentDestination?.id != R.id.nav_fillInvoice
                && navController.currentDestination?.id != R.id.nav_fillAddress
                && navController.currentDestination?.id != R.id.nav_details
                && navController.currentDestination?.id != R.id.nav_orders
                && navController.currentDestination?.id != R.id.nav_login
                && navController.currentDestination?.id != R.id.nav_invoice
                && navController.currentDestination?.id != R.id.nav_payment
            ) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.short_text_24px)
                toolbar.setNavigationIcon(R.drawable.short_text_24px)
                toolbar.navigationIcon?.setTint(Color.WHITE)
                your_cart.setTextColor(Color.WHITE)
                toolbar.setBackgroundColor(Color.BLACK)
                tback.setBackgroundColor(Color.BLACK)
                tback.background.setTint(Color.BLACK)
                toolbar.background.setTint(Color.BLACK)
                logo.setImageDrawable(resources.getDrawable(R.drawable.logo, theme))
                toolbar.elevation = 2F
                tback.elevation = 2F
                appBarLayout.elevation = 2F
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                text_login.setTextColor(Color.BLACK)
                tback.elevation = 0F
                appBarLayout.elevation = 0F
                supportActionBar?.setHomeAsUpIndicator(R.drawable.short_text_black24px)
                toolbar.setNavigationIcon(R.drawable.short_text_black24px)
                toolbar.navigationIcon?.setTint(Color.BLACK)
                toolbar.setBackgroundColor(Color.WHITE)
                your_cart.setTextColor(Color.BLACK)
                toolbar.background.setTint(Color.WHITE)
                tback.background.setTint(Color.WHITE)
                tback.setBackgroundColor(Color.WHITE)
                logo.setImageDrawable(resources.getDrawable(R.drawable.logo, theme))
            }
            if (navController.currentDestination?.id == R.id.nav_request || navController.currentDestination?.id == R.id.nav_sneakers) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.elevation = 0F
                tback.elevation = 0F
                appBarLayout.elevation = 0F
                supportActionBar?.setHomeAsUpIndicator(R.drawable.short_text_black24px)
                toolbar.setNavigationIcon(R.drawable.short_text_black24px)
                toolbar.navigationIcon?.setTint(Color.BLACK)
                shop.drawable.setTint(Color.BLACK)
                toolbar.setBackgroundColor(Color.WHITE)
                toolbar.background.setTint(Color.WHITE)
                tback.background.setTint(Color.WHITE)
                tback.setBackgroundColor(Color.WHITE)
                logo.setImageDrawable(resources.getDrawable(R.drawable.logo, theme))
            } else {
                shop.drawable.setTint(Color.WHITE)
                toolbar.elevation = 2F
                tback.elevation = 2F
                appBarLayout.elevation = 2F
            }
        }

        if (navController.currentDestination?.id != R.id.nav_search
            && navController.currentDestination?.id != R.id.nav_sneakers
        ) {
            toolbar.setNavigationOnClickListener {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.back2)
                /*  toolbar.setNavigationIcon(R.drawable.back)
                  toolbar.setCollapseIcon(R.drawable.back2)

                 */
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
                onSupportNavigateUp()
            }
        } else toolbar.setNavigationOnClickListener {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
            /*  actionBar?.setHomeAsUpIndicator(R.drawable.back)
             toolbar.setNavigationIcon(R.drawable.back)
             toolbar.setCollapseIcon(R.drawable.back)

             */
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
            onSupportNavigateUp()
        }
        if (navController.currentDestination?.id != R.id.nav_fill && navController.currentDestination?.id != R.id.nav_fillInvoice
            && navController.currentDestination?.id != R.id.nav_fillAddress && navController.currentDestination?.id != R.id.nav_details
        ) {

            logo.setImageDrawable(resources.getDrawable(R.drawable.logo, null))
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




