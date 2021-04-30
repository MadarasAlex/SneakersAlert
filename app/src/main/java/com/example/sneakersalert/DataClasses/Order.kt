package com.example.sneakersalert.DataClasses

import java.util.*

data class Order(
    var number: Int,
    var date: Date,
    var size: String,
    var price: Double,
    var status: String
)
