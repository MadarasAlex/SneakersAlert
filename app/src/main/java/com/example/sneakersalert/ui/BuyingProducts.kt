package com.example.sneakersalert.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakersalert.DataClasses.ProductCart
import com.example.sneakersalert.DataClasses.ShoeIn
import com.example.sneakersalert.Global
import com.example.sneakersalert.Global.Companion.sizes
import com.example.sneakersalert.Global.Companion.sp
import com.example.sneakersalert.R
import drawable.NewShoe
import kotlinx.android.synthetic.main.activity_buying_products.*
import layout.AdapterSpec
import kotlin.properties.Delegates

class BuyingProducts : Fragment(R.layout.activity_buying_products) {
    var buttons = ArrayList<Button>()
    lateinit var name: String
    lateinit var model: String
    var price by Delegates.notNull<Int>()
    var selectedSize = 0

    @SuppressLint(
        "ResourceAsColor", "UseCompatLoadingForColorStateLists",
        "UseCompatLoadingForDrawables"
    )
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val n = sizes.size
        val pr = NewShoe(
            Global.pic,
            Global.name,
            Global.model,
            Global.price,
            Global.infoText, sizes,
            sp
        )
        if (pr in Global.w) {
            wish.isInvisible = true
            wish2.isInvisible = false
        } else {
            wish2.isInvisible = true
            wish.isInvisible = false
        }
        when (n) {
            1 -> {
                buttons.add(size1)
                size1.text = sizes[0].toString()
                size2.isInvisible = true
                size3.isInvisible = true
            }
            2 -> {
                buttons.add(size1)
                buttons.add(size2)
                size3.isInvisible = true
                size1.text = sizes[0].toString()
                size2.text = sizes[1].toString()
            }
            3 -> {
                buttons.add(size1)
                buttons.add(size2)
                buttons.add(size3)
                size1.text = sizes[0].toString()
                size2.text = sizes[1].toString()
                size3.text = sizes[2].toString()
            }
        }
        shoe_pic.setImageResource(Global.pic)
        price_shoe.text = Global.price.toString()
        text_home.text = Global.name
        london_text.text = Global.model
        size1.setOnClickListener {
            size1.setBackgroundDrawable(resources.getDrawable(R.drawable.sizebullet_selected, null))
            size2.setBackgroundDrawable(resources.getDrawable(R.drawable.sizebullet, null))
            size3.setBackgroundDrawable(resources.getDrawable(R.drawable.sizebullet, null))
            selectedSize = size1.text.toString().toInt()
            size2.setTextColor(R.color.item_color)
            size1.setTextColor(Color.BLACK)
            size3.setTextColor(R.color.item_color)
            size2.background.setTint(Color.WHITE)
            size3.background.setTint(Color.WHITE)
        }
        size2.setOnClickListener {
            size2.background = resources.getDrawable(R.drawable.sizebullet_selected, null)
            size3.background = resources.getDrawable(R.drawable.sizebullet, null)
            size1.background = resources.getDrawable(R.drawable.sizebullet, null)
            size2.setBackgroundDrawable(resources.getDrawable(R.drawable.sizebullet_selected, null))
            size1.setBackgroundDrawable(resources.getDrawable(R.drawable.sizebullet, null))
            size3.setBackgroundDrawable(resources.getDrawable(R.drawable.sizebullet, null))
            size1.background.setTint(Color.WHITE)
            size3.background.setTint(Color.WHITE)
            size1.setTextColor(R.color.item_color)
            size2.setTextColor(Color.BLACK)
            size3.setTextColor(R.color.item_color)
            size2.background.setTint(Color.BLACK)
            selectedSize = size2.text.toString().toInt()
        }
        size3.setOnClickListener {
            size3.setBackgroundDrawable(resources.getDrawable(R.drawable.sizebullet_selected, null))
            size2.setBackgroundDrawable(resources.getDrawable(R.drawable.sizebullet, null))
            size1.setBackgroundDrawable(resources.getDrawable(R.drawable.sizebullet, null))
            size1.background.setTint(Color.WHITE)
            size2.background.setTint(Color.WHITE)
            size3.background.setTint(Color.BLACK)
            size1.setTextColor(R.color.item_color)
            size3.setTextColor(Color.BLACK)
            size2.setTextColor(R.color.item_color)
            selectedSize = size3.text.toString().toInt()
        }
        pic1.setOnClickListener {
            pic1.backgroundTintList = resources.getColorStateList(R.color.white)
            pic2.backgroundTintList = resources.getColorStateList(R.color.but)
            pic3.backgroundTintList = resources.getColorStateList(R.color.but)
            selectedSize = sizes[0]
        }
        pic2.setOnClickListener {
            pic2.backgroundTintList = resources.getColorStateList(R.color.white)
            pic1.backgroundTintList = resources.getColorStateList(R.color.but)
            pic3.backgroundTintList = resources.getColorStateList(R.color.but)
            selectedSize = sizes[1]
        }
        pic3.setOnClickListener {
            pic3.backgroundTintList = resources.getColorStateList(R.color.white)
            pic2.backgroundTintList = resources.getColorStateList(R.color.but)
            pic1.backgroundTintList = resources.getColorStateList(R.color.but)
            selectedSize = sizes[2]
        }
        wish.setOnClickListener {
            wish.isInvisible = true
            wish2.isInvisible = false
            Global.w.add(pr)
        }
        wish2.setOnClickListener {
            wish2.isInvisible = true
            wish.isInvisible = false
            Global.w.remove(pr)
        }
        add_cart.setOnClickListener { v ->
            if (selectedSize != 0) {
                val prod = ProductCart(
                    Global.pic,
                    Global.name,
                    Global.model,
                    Global.price,
                    selectedSize
                )
                val shoe =
                    ShoeIn(Global.pic, Global.name, Global.model, Global.price, selectedSize)
                if (prod in Global.p || shoe in Global.list)
                    Toast.makeText(
                        context,
                        "You already added this item",
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    Global.p.add(prod)
                    Global.list.add(shoe)
                    v?.findNavController()?.navigate(R.id.action_buyingProducts_to_nav_cart)
                    Global.total += Global.price
                }
            } else Toast.makeText(
                context,
                "No size selected",
                Toast.LENGTH_SHORT
            ).show()
        }
        recyclerViewSpec.adapter = AdapterSpec(sp)
        recyclerViewSpec.layoutManager = LinearLayoutManager(this.activity)
    }


}