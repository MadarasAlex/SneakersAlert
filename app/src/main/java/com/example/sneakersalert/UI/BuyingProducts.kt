package com.example.sneakersalert.UI

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakersalert.DataClasses.Spec
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.activity_buying_products.*
import kotlinx.android.synthetic.main.activity_cart.toolbar3
import layout.AdapterSpec

class BuyingProducts : AppCompatActivity() {
    var sp=ArrayList<Spec>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buying_products)
        setSupportActionBar(findViewById(R.id.toolbar3))
        val i=intent.getSerializableExtra("spec")
        println(i)
        sp= i as ArrayList<Spec>
        recyclerViewSpec.adapter=AdapterSpec(sp)
        recyclerViewSpec.layoutManager=LinearLayoutManager(this)
        toolbar3.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}