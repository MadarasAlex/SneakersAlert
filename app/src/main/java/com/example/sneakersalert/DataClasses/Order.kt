package com.example.sneakersalert.DataClasses

import java.time.LocalDateTime

data class Order(
    var number: Int,
    var date: LocalDateTime,
    var size: String,
    var price: Double,
    var status: String
)
