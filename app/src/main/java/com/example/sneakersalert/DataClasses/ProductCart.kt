package com.example.sneakersalert.DataClasses

data class ProductCart(
    val image: Int,
    val name: String,
    val model: String,
    var price: Int,
    val size: Int,
    var amount: Int
) :
    Comparable<ProductCart> {


    override fun compareTo(other: ProductCart): Int {
        if (this.price < other.price) return 1
        return 0

    }

}
