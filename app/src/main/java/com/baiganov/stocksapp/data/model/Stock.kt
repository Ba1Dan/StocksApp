package com.baiganov.stocksapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_stocks")
data class Stock(
    val currency: String,
    @PrimaryKey
    val name: String,
    val ticker: String,
    val logo: String,
    val currentPrice: Float,
    val percentDelta: Double = 1.0,
    var isFavourite: Boolean = false
)

