package com.example.sneakersalert

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterJordan
import drawable.NewShoe

class WishlistFragment : Fragment(R.layout.fragment_wishlist) {
    val w=ArrayList<NewShoe>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewWish)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.adapter = AdapterJordan(Global.w, object : AdapterJordan.OnClickListener {
            override fun onItemClick(position: Int) {

            }

        })


        super.onViewCreated(view, savedInstanceState)
    }

}