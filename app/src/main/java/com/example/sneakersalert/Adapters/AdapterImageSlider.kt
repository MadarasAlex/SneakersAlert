package com.example.sneakersalert.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.sneakersalert.R
import com.smarteist.autoimageslider.SliderViewAdapter

class AdapterImageSlider :
    SliderViewAdapter<AdapterImageSlider.VH>() {
    private var mSliderItems = ArrayList<Int>()
    fun renewItems(sliderItems: ArrayList<Int>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: Int) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): VH {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.image_holder, null)
        return VH(inflate)
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        //load image into view
        viewHolder.imageView.setImageResource(mSliderItems[position])
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    inner class VH(itemView: View) : ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.imageSlider)

    }
}