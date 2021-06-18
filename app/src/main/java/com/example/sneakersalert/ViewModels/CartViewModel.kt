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
    private val amount = MutableLiveData<Int>()
    private val country = MutableLiveData<String>()
    private val name = MutableLiveData<String>()

    init {
        totalPrice.value = Global.total
        list.value = Global.p
        finalPrice.value = Global.total
        amount.value = Global.p.size
        country.value = Global.country
        name.value = Global.username
    }

    fun refreshData() {
        country.value = Global.country
        name.value = Global.name
    }

    fun calculatePrice() {
        totalPrice.value = 0
        amount.value = Global.p.size
        for (el in Global.p) {
            Thread.sleep(500)
            totalPrice.value = totalPrice.value!! + (el.amount * el.price)
            finalPrice.value = totalPrice.value
        }


    }

    fun getName(): LiveData<String> {
        return name
    }

    fun getCountry(): LiveData<String> {
        return country
    }

    fun getTotal(): LiveData<Int> {
        calculatePrice()
        return totalPrice
    }

    fun getAmount(): LiveData<Int> {
        return amount
    }

    fun getFinal(): LiveData<Int> {
        calculatePrice()
        return finalPrice
    }

}