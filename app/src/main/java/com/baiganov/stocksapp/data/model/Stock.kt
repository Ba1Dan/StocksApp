package com.baiganov.stocksapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "list_stocks")
data class Stock(
    val currency: String,
    @PrimaryKey
    val name: String,
    val ticker: String,
    val logo: String,
    val currentPrice: Float,
    val percentDelta: Double = 1.0,
    val priceDelta: Double,
    var isFavourite: Boolean = false
) : StockResponse()

data class Suggestion(
    val name: String,
    val stocks: List<String>
) : StockResponse()

data class Section(
    val name: String,
    val stocks: List<Stock>
) : StockResponse()

sealed class StockResponse

