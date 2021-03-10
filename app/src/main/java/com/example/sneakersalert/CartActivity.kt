package com.example.sneakersalert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterProductCart
import com.example.sneakersalert.DataClasses.ProductCart
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {
    val p=ArrayList<ProductCart>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        toolbar2.setNavigationOnClickListener {
            onBackPressed()
        }
        p.add(ProductCart(R.drawable.air_max_london,"Nike air max 1","London",289,40,1))
        p.add(ProductCart(R.drawable.air_max_1_have_a_nikeday,"Nike air max 1","Have A Nike Day",149,41,2))
        val recyclerView:RecyclerView=findViewById(R.id.recyclerViewCart)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled=true
        recyclerView.adapter=AdapterProductCart(p)
        var totalPrice=0
        findViewById<TextView>(R.id.price_total).text=totalPrice.toString()
        for(el in p)
        {
            totalPrice=totalPrice+(el.price*el.amount)
            findViewById<TextView>(R.id.price_total).text=totalPrice.toString()
        }
    }
}