
package com.example.sneakersalert

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.sneakersalert.DataClasses.ItemNav
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlin.collections.set


class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration: AppBarConfiguration
    val navigationDest = ArrayList<ItemNav>()
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var expandableListAdapter: ExpandableListAdapter
    val listDataHeader = ArrayList<ItemNav>()
    val listDataChild = HashMap<ItemNav, ArrayList<ItemNav>>()
    lateinit var expandableListView: ExpandableListView
    var open = false

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint(
        "RestrictedApi", "UseCompatLoadingForDrawables", "ResourceAsColor",
        "ResourceType", "NewApi", "RtlHardcoded"
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
                R.id.nav_orders,
                R.id.nav_wishlist
            ), drawerLayout
        )
        navigationDest.add(ItemNav("Home", 0))
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

                toolbar.title = "Your Cart(" + Global.p.size + ")"
                toolbar.setTitleTextColor(Color.WHITE)
                toolbar.title = "Your Cart(" + Global.p.size + ")"

                findViewById<ConstraintLayout>(R.id.tback).setBackgroundColor(Color.WHITE)
                actionBar?.title = "Your Cart(" + Global.p.size + ")"
                supportActionBar?.title = "Your Cart(" + Global.p.size + ")"
                supportActionBar?.setDisplayShowTitleEnabled(true)
                your_cart.visibility = View.VISIBLE
                logo.visibility = View.INVISIBLE
                your_cart.text = "Your Cart(" + Global.p.size + ")"
                shop.visibility = View.INVISIBLE
            } else {
                your_cart.visibility = View.INVISIBLE
                logo.visibility = View.VISIBLE
                shop.visibility = View.VISIBLE
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.back2)
            actionBar?.setBackgroundDrawable(ColorDrawable(R.color.alert))
            toolbar.setBackgroundColor(R.color.alert)
            supportActionBar?.setBackgroundDrawable(ColorDrawable(R.color.alert))
            actionBar?.setBackgroundDrawable(ColorDrawable(R.color.alert))
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.short_text_24px)
            super.onBackPressed()
        }
    }

    fun changeFragment(fragment: Fragment){
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.nav_host_fragment, fragment).addToBackStack(null)
        ft.commit()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_cart) {
            toolbar.title = "Your Cart(" + Global.p.size + ")"
            findViewById<ConstraintLayout>(R.id.tback).setBackgroundColor(Color.WHITE)
            actionBar?.title = "Your Cart(" + Global.p.size + ")"
            supportActionBar?.title = "Your Cart(" + Global.p.size + ")"
            your_cart.visibility = View.VISIBLE
            logo.visibility = View.INVISIBLE
            your_cart.text = "Your Cart(" + Global.p.size + ")"

            setTheme(R.style.Toolbar)
        }
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

    private fun prepareListData() {


        // Adding child data
        listDataHeader.add(ItemNav("Home Page", 0))
        listDataHeader.add(ItemNav("Shop Products", R.drawable.dropdown))
        listDataHeader.add(ItemNav("My Account", 0))
        listDataHeader.add(ItemNav("My Cart", 0))
        listDataHeader.add(ItemNav("FAQ", 0))
        // Adding child data
        val shoes = ArrayList<ItemNav>()
        shoes.add(ItemNav("AIR MAX 1", 0))
        shoes.add(ItemNav("AIR MAX 90", 0))
        shoes.add(ItemNav("JORDAN 1", 0))

        listDataChild[listDataHeader[1]] = shoes // Header, Child data

    }

}




