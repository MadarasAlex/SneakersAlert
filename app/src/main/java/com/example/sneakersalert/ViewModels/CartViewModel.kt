package com.example.sneakersalert.ViewModels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sneakersalert.DataClasses.ProductCart
import com.example.sneakersalert.Global

class CartViewModel : ViewModel() {
    private val totalPrice = MutableLiveData<Int>(0)
    private val finalPrice = MutableLiveData<Int>(0)
    private val list = MutableLiveData<ArrayList<LiveData<ProductCart>>>(arrayListOf())
    private val amount = MutableLiveData<Int>(0)
    private val country = MutableLiveData<String>("")
    private val name = MutableLiveData<String>("")

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

    private fun calculatePrice() {
        amount.value = Global.p.size
        println("Final price: ${finalPrice.value}")
        println("Total price: ${totalPrice.value}")
        totalPrice.value=Global.total

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
    fun initialize(view:View)
    {
    }

}