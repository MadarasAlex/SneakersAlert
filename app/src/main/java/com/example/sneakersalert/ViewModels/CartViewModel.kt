package com.example.sneakersalert.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sneakersalert.DataClasses.ProductCart
import com.example.sneakersalert.Global

class CartViewModel : ViewModel() {
    private val totalPrice = MutableLiveData<Int>()
    private val finalPrice = MutableLiveData<Int>()
    private val list = MutableLiveData<ArrayList<ProductCart>>()

    init {
        totalPrice.value = Global.total
        list.value = Global.p
        finalPrice.value = Global.total
    }

    fun calculatePrice() {
        totalPrice.value = 0
        for (el in Global.p) {
            Thread.sleep(500)
            totalPrice.value = totalPrice.value!! + (el.amount * el.price)
            finalPrice.value = totalPrice.value
        }


    }

    fun getTotal(): LiveData<Int> {
        calculatePrice()
        return totalPrice
    }

    fun getFinal(): LiveData<Int> {
        calculatePrice()
        return finalPrice
    }

}