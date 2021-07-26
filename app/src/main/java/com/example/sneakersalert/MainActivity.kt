
package com.example.sneakersalert

import Repositories.OnSwipeTouchListener
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.sneakersalert.Adapters.AdapterProductCart
import com.example.sneakersalert.DataClasses.ProductCart
import com.example.sneakersalert.Global.Companion.p
import com.example.sneakersalert.ViewModels.CartViewModel
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header.view.*
import java.util.function.Consumer


class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var drawerToggle: ActionBarDrawerToggle
    private var open = false
    val mAuth = FirebaseAuth.getInstance()
    private val mUser = mAuth.currentUser
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
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
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        setupActionBarWithNavController(navController)
        navigationView.setupWithNavController(navController)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        actionBar?.setDisplayShowCustomEnabled(true)
        toolbar.setupWithNavController(navController)
        appBarConfiguration= AppBarConfiguration.Builder(navController.graph).build()
        drawerLayout.performClick()
        drawerLayout.setOnTouchListener(
            object : OnSwipeTouchListener(this) {
                override fun onSwipeLeft() {
                    super.onSwipeLeft()
                    drawerLayout.performClick()
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

                override fun onSwipeRight() {
                    super.onSwipeRight()
                    drawerLayout.performClick()
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
                }

            },
        )

        count_items.text = p.size.toString()
        navigationView.getHeaderView(0).search.setOnClickListener {
            navController.navigate(R.id.nav_search)
        }
        if(mUser!=null)
            getData()
        navController.addOnDestinationChangedListener { _, _, _ ->
            toolbar.setNavigationIcon(R.drawable.short_text_24px)
            if (navController.currentDestination?.id == R.id.nav_search) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
            navigationView.setItemBackgroundResource(R.drawable.nav_view_select)
            navigationView.setItemTextAppearance(R.color.alert)
            open = false
            if (navController.currentDestination?.id == R.id.nav_orders) {
                logout.visibility = View.VISIBLE
                count_items.visibility = View.INVISIBLE
                imageButton.visibility = View.INVISIBLE
            } else logout.visibility = View.INVISIBLE
            if (navController.currentDestination?.id == R.id.nav_cart) {
                supportActionBar?.setDisplayShowTitleEnabled(true)
                count_items.visibility = View.INVISIBLE
                imageButton.visibility = View.INVISIBLE
                your_cart.visibility = View.VISIBLE
                logo.visibility = View.INVISIBLE
                shop.visibility = View.INVISIBLE
            } else {
                count_items.visibility = View.VISIBLE
                your_cart.visibility = View.INVISIBLE
                logo.visibility = View.VISIBLE
                imageButton.visibility = View.VISIBLE
                shop.visibility = View.VISIBLE
            }
            if (navController.currentDestination?.id == R.id.nav_login) {
                navigationView.menu.getItem(2).isCheckable = true
                navigationView.menu.getItem(2).isChecked = true
                navigationView.setCheckedItem(R.id.nav_menuLogin)
                your_cart.visibility = View.INVISIBLE
                count_items.visibility = View.INVISIBLE
                imageButton.visibility = View.INVISIBLE
                logo.visibility = View.INVISIBLE
                shop.visibility = View.INVISIBLE
                text_login.visibility = View.INVISIBLE

            }
            if (navController.currentDestination?.id == R.id.nav_payment) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                your_cart.visibility = View.VISIBLE
                your_cart.text = "Checkout"
                logo.visibility = View.INVISIBLE
                shop.visibility = View.INVISIBLE
            }
            if (navController.currentDestination?.id == R.id.nav_signup) {
                your_cart.visibility = View.INVISIBLE
                logo.visibility = View.INVISIBLE
                shop.visibility = View.INVISIBLE
                count_items.visibility = View.INVISIBLE
                imageButton.visibility = View.INVISIBLE
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
                count_items.visibility = View.INVISIBLE
                imageButton.visibility = View.INVISIBLE
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
                || navController.currentDestination?.id == R.id.nav_orders
            ) {
                logo.visibility = View.INVISIBLE
                count_items.visibility = View.INVISIBLE
                imageButton.visibility = View.INVISIBLE
            } else if (navController.currentDestination?.id == R.id.nav_cart) {
                logo.visibility = View.INVISIBLE

            }
            else logo.visibility = View.VISIBLE
            if (navController.currentDestination?.id != R.id.nav_signup
                && navController.currentDestination?.id != R.id.nav_login
                && navController.currentDestination?.id != R.id.nav_orders
                && navController.currentDestination?.id != R.id.nav_details
            ) {
                text_login.visibility = View.INVISIBLE
            }
            if (navController.currentDestination?.id == R.id.nav_login)
                toolbar.elevation = 0F
            else
                toolbar.elevation = 2F
            if (navController.currentDestination?.id != R.id.nav_fill
                && navController.currentDestination?.id != R.id.nav_fillShipping

                && navController.currentDestination?.id != R.id.nav_fillAddress
                && navController.currentDestination?.id != R.id.nav_details
                && navController.currentDestination?.id != R.id.nav_orders
                && navController.currentDestination?.id != R.id.nav_login
                && navController.currentDestination?.id != R.id.nav_invoice
                && navController.currentDestination?.id != R.id.nav_payment
            ) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.short_text_24px)
                toolbar.elevation = 2F
                tback.elevation = 2F
                appBarLayout.elevation = 2F
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                tback.elevation = 0F
                count_items.visibility = View.INVISIBLE
                imageButton.visibility = View.INVISIBLE
                shop.visibility = View.INVISIBLE
                appBarLayout.elevation = 0F
            }
            if (navController.currentDestination?.id == R.id.nav_request || navController.currentDestination?.id == R.id.nav_sneakers) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.elevation = 0F
                tback.elevation = 0F
                appBarLayout.elevation = 0F
            } else {
                toolbar.elevation = 2F
                tback.elevation = 2F
                appBarLayout.elevation = 2F
            }
            if (navController.currentDestination?.id == R.id.nav_invoice
                || navController.currentDestination?.id == R.id.buyingProducts
                || navController.currentDestination?.id == R.id.nav_details
                || navController.currentDestination?.id == R.id.nav_fill
                || navController.currentDestination?.id == R.id.nav_fillAddress
                || navController.currentDestination?.id == R.id.nav_fillShipping

            ) {
                toolbar.navigationIcon = resources.getDrawable(R.drawable.back, null)
                toolbar.setNavigationOnClickListener {
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
            } else {
                toolbar.navigationIcon =
                    resources.getDrawable(R.drawable.short_text_black24px, null)
                toolbar.setNavigationOnClickListener {
                    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.short_text_black24px)
                }
            }
        }
        if (navController.currentDestination?.id != R.id.nav_search
            && navController.currentDestination?.id != R.id.nav_sneakers
        ) {
            toolbar.setNavigationOnClickListener {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
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
        if (navController.currentDestination?.id != R.id.nav_fill && navController.currentDestination?.id != R.id.nav_fillShipping

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

    private fun getData() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val productsReference = databaseUsers.child(id.toString()).child("Cart")
        productsReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    p.clear()
                    for (el in snapshot.children) {
                        val name = el.child("name").getValue(String::class.java)
                        val model = el.child("model").getValue(String::class.java)
                        val price = el.child("price").getValue(Int::class.java)
                        val image = el.child("image").getValue(String::class.java)
                        val amount = el.child("amount").getValue(Int::class.java)
                        val idItem=el.child("id").getValue(Int::class.java)
                        val size = el.child("size").getValue(Int::class.java)
                        val item = ProductCart(image!!, name!!, model!!, price!!, size!!, amount!!,idItem!!)
                        println(item)
                        p.add(item)

                    }
                    val adapter =
                        AdapterProductCart(
                            p,
                            price_total,
                            price_final,
                            your_cart,
                            count_items
                        )
                    adapter.notifyDataSetChanged()
                    count_items.text= p.size.toString()
                    val model=ViewModelProvider(this@MainActivity).get(CartViewModel::class.java)
                    model.getAmount().observe(this@MainActivity, { newAmount ->
                      "Your Cart ($newAmount)".also { your_cart.text = it }
                       count_items.text = newAmount.toString()
                    })

                    adapter.notifyDataSetChanged()
                    model.getFinal().observe(this@MainActivity, { newPrice ->
                        Global.total=newPrice
                    })
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onPerformDirectAction(
        actionId: String,
        arguments: Bundle,
        cancellationSignal: CancellationSignal,
        resultListener: Consumer<Bundle>
    ) {
        super.onPerformDirectAction(actionId, arguments, cancellationSignal, resultListener)
    }

    override fun onActionModeStarted(mode: ActionMode?) {
        val model=ViewModelProvider(this).get(CartViewModel::class.java)
        super.onActionModeStarted(mode)
    }


}




