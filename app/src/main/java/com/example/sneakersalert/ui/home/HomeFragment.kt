package com.example.sneakersalert.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakersalert.Adapters.AdapterImageSlider
import com.example.sneakersalert.Adapters.ViewPagerAdapter
import com.example.sneakersalert.Global.Companion.fragmentList
import com.example.sneakersalert.Global.Companion.positionSelected
import com.example.sneakersalert.R
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(R.layout.fragment_home) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imageSlider = view.findViewById<SliderView>(R.id.imageSlider)

        card1.setOnClickListener {
            findNavController().navigate(R.id.nav_sneakers)
            positionSelected = 0
        }
        card2.setOnClickListener {
            findNavController().navigate(R.id.nav_sneakers)
            positionSelected = 0
        }
        card3.setOnClickListener {
            findNavController().navigate(R.id.nav_sneakers)
            positionSelected = 1
        }
        card4.setOnClickListener {
            findNavController().navigate(R.id.nav_sneakers)
            positionSelected = 2
        }
        ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        ).createFragment(positionSelected)
        card1.setOnLongClickListener {
            long_clicked.visibility = View.VISIBLE
            long_clicked2.visibility = View.INVISIBLE
            long_clicked3.visibility = View.INVISIBLE
            long_clicked4.visibility = View.INVISIBLE

            return@setOnLongClickListener false
        }
        card2.setOnLongClickListener {
            long_clicked2.visibility = View.VISIBLE
            long_clicked.visibility = View.INVISIBLE
            long_clicked3.visibility = View.INVISIBLE
            long_clicked4.visibility = View.INVISIBLE

            return@setOnLongClickListener false
        }
        card3.setOnLongClickListener {
            long_clicked3.visibility = View.VISIBLE
            long_clicked2.visibility = View.INVISIBLE
            long_clicked.visibility = View.INVISIBLE
            long_clicked4.visibility = View.INVISIBLE

            return@setOnLongClickListener false
        }
        card4.setOnLongClickListener {
            long_clicked4.visibility = View.VISIBLE
            long_clicked2.visibility = View.INVISIBLE
            long_clicked3.visibility = View.INVISIBLE
            long_clicked.visibility = View.INVISIBLE

            return@setOnLongClickListener false
        }
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