package com.example.sneakersalert.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sneakersalert.ui.Airmax1Fragment
import com.example.sneakersalert.ui.Airmax90Fragment
import com.example.sneakersalert.ui.JordanFragment


class ViewPagerAdapter(l: ArrayList<Fragment>, fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(
        fm,
        lifecycle
    ) {
    private val fragmentList = l
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Airmax1Fragment()
            1 -> Airmax90Fragment()
            2 -> JordanFragment()
            else -> Fragment()
        }
    }


}