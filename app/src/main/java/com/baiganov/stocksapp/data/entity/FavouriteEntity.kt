package com.baiganov.stocksapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baiganov.stocksapp.data.model.Stock

@Entity(tableName = "favourite_stocks")
class StockEntity(
    val currency: String,
    @PrimaryKey
    val name: String,
    val ticker: String,
    val logo: String,
    val currentPrice: Float,
    val percentDelta: Double,
    var isFavourite: Boolean
)

fun convert(stock: Stock): StockEntity {
    return StockEntity(
        currency = stock.currency,
        name = stock.name,
        ticker = stock.ticker,
        logo = stock.logo,
        currentPrice = stock.currentPrice,
        percentDelta = stock.percentDelta,
        isFavourite = stock.isFavourite
    )
}

