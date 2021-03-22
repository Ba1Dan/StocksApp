package com.baiganov.stocksapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.data.model.StockResponse

@Entity(tableName = "favourite_stocks")
class FavouriteEntity(
    val currency: String,
    @PrimaryKey
    val name: String,
    val ticker: String,
    val logo: String,
    val currentPrice: Float,
    val percentDelta: Double,
    val priceDelta: Double,
    var isFavourite: Boolean
)

fun convertToStock(favouriteStock: FavouriteEntity): Stock {
    return Stock(
        currency = favouriteStock.currency,
        name = favouriteStock.name,
        ticker = favouriteStock.ticker,
        logo = favouriteStock.logo,
        currentPrice = favouriteStock.currentPrice,
        percentDelta = favouriteStock.percentDelta,
        isFavourite = favouriteStock.isFavourite,
        priceDelta = favouriteStock.priceDelta
    )
}

fun convertToFavourite(stock: Stock): FavouriteEntity {
    return FavouriteEntity(
        currency = stock.currency,
        name = stock.name,
        ticker = stock.ticker,
        logo = stock.logo,
        currentPrice = stock.currentPrice,
        percentDelta = stock.percentDelta,
        isFavourite = stock.isFavourite,
        priceDelta = stock.priceDelta
    )
}

