package com.example.sneakersalert.UI.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakersalert.Adapters.AdapterImageSlider
import com.example.sneakersalert.DataClasses.ShoeType
import com.example.sneakersalert.Global.Companion.positionSelected
import com.example.sneakersalert.R
import com.smarteist.autoimageslider.SliderView
import drawable.AdapterNewShoe
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(R.layout.fragment_home) {
    val l = ArrayList<ShoeType>()


    override fun onCreate(savedInstanceState: Bundle?) {
        l.add(ShoeType(R.drawable.jordan_canyon, "SNEAKERS"))
        l.add(ShoeType(R.drawable.air_max_1_have_a_nikeday, "NIKE AIR MAX 1"))
        l.add(ShoeType(R.drawable.duck_camo, "NIKE AIR MAX 90"))
        l.add(ShoeType(R.drawable.jordan_particle, "JORDAN 1"))

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imageSlider = view.findViewById<SliderView>(R.id.imageSlider)
        recyclerViewHome.layoutManager = GridLayoutManager(this.activity, 2)

        recyclerViewHome.adapter = AdapterNewShoe(l, object : AdapterNewShoe.OnClickListener {
            override fun onItemClick(position: Int) {
                if (position == 0) {
                    findNavController().navigate(R.id.nav_sneakers)
                    positionSelected = 0
                }
                if (position == 1) {
                    findNavController().navigate(R.id.nav_sneakers)
                    positionSelected = 0
                }
                if (position == 2) {
                    findNavController().navigate(R.id.nav_sneakers)
                    positionSelected = 1
                }
                if (position == 3) {
                    positionSelected = 2
                    findNavController().navigate(R.id.nav_sneakers)
                }
            }
        })
        val imageList: ArrayList<Int> = ArrayList()
        imageList.add(R.drawable.sneakers_home)
        imageList.add(R.drawable.sneakers_home)
        imageList.add(R.drawable.sneakers_home)
        imageList.add(R.drawable.sneakers_home)
        LinearLayoutManager.HORIZONTAL
        setImageInSlider(imageList, imageSlider)
    }

    private fun setImageInSlider(images: ArrayList<Int>, imageSlider: SliderView) {
        val adapter = AdapterImageSlider()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
    }

}