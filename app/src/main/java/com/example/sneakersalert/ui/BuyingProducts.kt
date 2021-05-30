package com.example.sneakersalert.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakersalert.Adapters.AdapterImageSlider
import com.example.sneakersalert.DataClasses.NewShoe
import com.example.sneakersalert.DataClasses.ProductCart
import com.example.sneakersalert.DataClasses.ShoeIn
import com.example.sneakersalert.Global
import com.example.sneakersalert.Global.Companion.list
import com.example.sneakersalert.Global.Companion.p
import com.example.sneakersalert.Global.Companion.sizes
import com.example.sneakersalert.Global.Companion.sp
import com.example.sneakersalert.R
import com.example.sneakersalert.R.layout
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.activity_buying_products.*
import layout.AdapterSpec
import kotlin.properties.Delegates

class BuyingProducts : Fragment(layout.activity_buying_products) {
    var buttons = ArrayList<Button>()
    lateinit var name: String
    lateinit var model: String
    var price by Delegates.notNull<Int>()
    var selectedSize = 0
    @SuppressLint("ResourceAsColor")
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

        val imageList: ArrayList<Int> = ArrayList()
        imageList.add(Global.pic)
        imageList.add(Global.pic)
        imageList.add(Global.pic)
        LinearLayoutManager.HORIZONTAL
        setImageInSlider(imageList, shoe_pic)
        price_shoe.text = Global.price.toString()
        text_home.text = Global.name
        london_text.text = Global.model

        size1.setOnClickListener {
            selectedSize = size1.text.toString().toInt()
            size1.visibility = View.INVISIBLE
            size2.visibility = View.VISIBLE
            size3.visibility = View.VISIBLE
            size1_checked.visibility = View.VISIBLE
            size2_checked.visibility = View.INVISIBLE
            size3_checked.visibility = View.INVISIBLE

        }
        size2.setOnClickListener {
            selectedSize = size2.text.toString().toInt()
            size2.visibility = View.INVISIBLE
            size1.visibility = View.VISIBLE
            size3.visibility = View.VISIBLE
            size2_checked.visibility = View.VISIBLE
            size1_checked.visibility = View.INVISIBLE
            size3_checked.visibility = View.INVISIBLE
        }
        size3.setOnClickListener {
            selectedSize = size3.text.toString().toInt()
            size3.visibility = View.INVISIBLE
            size1.visibility = View.VISIBLE
            size2.visibility = View.VISIBLE
            size3_checked.visibility = View.VISIBLE
            size1_checked.visibility = View.INVISIBLE
            size2_checked.visibility = View.INVISIBLE
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
        recyclerViewSpec.adapter = AdapterSpec(sp)
        recyclerViewSpec.layoutManager = LinearLayoutManager(this.activity)
        add_cart.setOnClickListener {
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
                if (prod in p || shoe in list)
                    Toast.makeText(
                        context,
                        "You already added this item",
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    p.add(prod)
                    list.add(shoe)
                    thankyou_layout.visibility = View.VISIBLE
                    size1.visibility = View.INVISIBLE
                    size2.visibility = View.INVISIBLE
                    size3.visibility = View.INVISIBLE
                    size1_checked.visibility = View.INVISIBLE
                    size2_checked.visibility = View.INVISIBLE
                    size3_checked.visibility = View.INVISIBLE
                    info_text.visibility = View.INVISIBLE
                    read_more.visibility = View.INVISIBLE
                    spec_title.visibility = View.INVISIBLE
                    add_cart.visibility = View.INVISIBLE
                    recyclerViewSpec.visibility = View.INVISIBLE
                    item_picture.setImageResource(Global.pic)
                    name_item.text = Global.name
                    model_item.text = Global.model
                    "€ ${Global.price}".also { price_item.text = it }
                    size_item.text = selectedSize.toString()
                    checkout_now.setOnClickListener {
                        findNavController().navigate(R.id.action_buyingProducts_to_nav_cart)
                        Global.total += Global.price
                    }
                }
            } else Toast.makeText(
                context,
                "No size selected",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    override fun onStart() {
        super.onStart()
        thankyou_layout.visibility = View.GONE
        size1.visibility = View.VISIBLE
        size2.visibility = View.VISIBLE
        size3.visibility = View.VISIBLE
        size1_checked.visibility = View.INVISIBLE
        size2_checked.visibility = View.INVISIBLE
        size3_checked.visibility = View.INVISIBLE
        info_text.visibility = View.VISIBLE
        read_more.visibility = View.VISIBLE
        spec_title.visibility = View.VISIBLE
        add_cart.visibility = View.VISIBLE
        recyclerViewSpec.visibility = View.VISIBLE


    }

    private fun setImageInSlider(images: ArrayList<Int>, imageSlider: SliderView) {
        val adapter = AdapterImageSlider()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = false
        //   imageSlider.startAutoCycle()
    }


}