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
    constructor(image: Int, name: String, model: String, price: Int, size: Int) : this(
        image,
        name,
        model,
        price,
        size,
        1
    )

    override fun compareTo(other: ProductCart): Int {
        if (this.price < other.price) return 1
        return 0

    }

}
