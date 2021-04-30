package com.example.sneakersalert.ui.sneakers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.sneakersalert.Adapters.ViewPagerAdapter
import com.example.sneakersalert.DataClasses.Spec
import com.example.sneakersalert.Global.Companion.fragmentList
import com.example.sneakersalert.Global.Companion.positionSelected
import com.example.sneakersalert.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import drawable.NewShoe
import kotlinx.android.synthetic.main.fragment_sneakers.*

class SneakersFragment : Fragment(R.layout.fragment_sneakers) {
    val s = ArrayList<NewShoe>()
    val sp = ArrayList<Spec>()
    private lateinit var tabLayout: TabLayout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabLayout = view.findViewById(R.id.tab_layout)

        val adapterPager =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        pager.adapter = adapterPager
        tabLayout.addTab(tabLayout.newTab().setText("AIR MAX 1"))
        tabLayout.addTab(tabLayout.newTab().setText("AIR MAX 90"))
        tabLayout.addTab(tabLayout.newTab().setText("JORDAN 1"))
        adapterPager.createFragment(positionSelected)
        println(positionSelected)
        tabLayout.selectTab(tabLayout.getTabAt(positionSelected))
        pager.currentItem = positionSelected
        println(positionSelected)
        pager.currentItem = tabLayout.selectedTabPosition

        TabLayoutMediator(
            tabLayout, pager, true, false
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "AIR MAX 1"
                1 -> tab.text = "AIR MAX 90"
                2 -> tab.text = "JORDAN 1"

                else -> tab.text = "undefined"
            }
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                positionSelected = position
                tabLayout.selectTab(tabLayout.getTabAt(position))
                super.onPageSelected(position)
            }

        })

        super.onViewCreated(view, savedInstanceState)
    }


}


// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
