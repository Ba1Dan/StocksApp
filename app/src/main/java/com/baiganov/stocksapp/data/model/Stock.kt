package com.baiganov.stocksapp.data.model

data class Stock(
    val currency: String,
    val name: String,
    val ticker: String,
    val logo: String,
    val currentPrice: Float,
    val percentDelta: Double = 1.0,
    var isFavourite: Boolean = false
)

