package com.example.sneakersalert.DataClasses

import java.io.Serializable

data class NewShoe(
    val image: String,
    val name: String,
    val model: String,
    var price: Int?,
    val text: String,
    val sizes: ArrayList<Int>,
    val spec: ArrayList<Spec>,
    val stock:Int
) : Serializable
