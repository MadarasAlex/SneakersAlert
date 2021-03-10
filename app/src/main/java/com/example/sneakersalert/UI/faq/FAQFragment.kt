package com.example.sneakersalert.ui.faq

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersalert.Adapters.AdapterFAQCard
import com.example.sneakersalert.DataClasses.FAQCard
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.fragment_jordan.*

class FAQFragment : Fragment(R.layout.fragment_f_a_q) {
    var l=ArrayList<FAQCard>()
    override fun onCreate(savedInstanceState: Bundle?) {
        l.add(FAQCard("ORDER PROCESS",R.drawable.arrow))
        l.add(FAQCard("payment",R.drawable.arrow))
        l.add(FAQCard("shipping",R.drawable.arrow))
        l.add(FAQCard("return",R.drawable.arrow))
        l.add(FAQCard("privacy",R.drawable.arrow))
        l.add(FAQCard("reviews",R.drawable.arrow))
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView:RecyclerView=view.findViewById(R.id.RecyclerViewFAQ)
        recyclerView.layoutManager=LinearLayoutManager(this.activity)
        recyclerView.isNestedScrollingEnabled=false
        recyclerView.adapter=AdapterFAQCard(l)

        super.onViewCreated(view, savedInstanceState)
    }

}