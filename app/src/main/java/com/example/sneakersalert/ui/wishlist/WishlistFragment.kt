package com.example.sneakersalert.ui.wishlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterJordan
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import drawable.NewShoe
import kotlinx.android.synthetic.main.fragment_wishlist.*

class WishlistFragment : Fragment(R.layout.fragment_wishlist) {
    val w=ArrayList<NewShoe>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Global.w.size == 0) {
            empty_wishlist.visibility = View.VISIBLE
            wishlist_title.visibility = View.GONE
            recyclerViewWish.visibility = View.GONE
            continue_shopping2.setOnClickListener {
                findNavController().navigate(R.id.nav_sneakers)
            }
        } else {
            empty_wishlist.visibility = View.GONE
            wishlist_title.visibility = View.VISIBLE
            recyclerViewWish.visibility = View.VISIBLE
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewWish)
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            recyclerView.isNestedScrollingEnabled = false
            recyclerView.adapter = AdapterJordan(Global.w, object : AdapterJordan.OnClickListener {
                override fun onItemClick(position: Int) {
                    Global.price = Global.w[position].price!!
                    Global.name = Global.w[position].name
                    Global.model = Global.w[position].model
                    Global.sizes = Global.w[position].sizes
                    Global.pic = Global.w[position].image!!
                    Global.sp = Global.w[position].spec
                    Global.infoText = Global.w[position].text
                    findNavController().navigate(R.id.buyingProducts)

                }

            })
        }
    }

}