package com.baiganov.stocksapp.repositories

import com.baiganov.stocksapp.api.ApiServiceFin
import com.baiganov.stocksapp.data.model.News
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.StocksDao

class DetailRepositoryImpl(private val stocksDao: StocksDao, private val apiServiceFin: ApiServiceFin) {

    suspend fun get(stockTicker: String): Stock {
        return stocksDao.getStock(stockTicker)
    }

    suspend fun getNews(ticker: String): List<News> {
        val data = apiServiceFin.getNews(ticker = ticker)
        val result = mutableListOf<News>()
        data.forEach {
            result.add(
                News(
                    id = it.id,
                    datetime = it.datetime,
                    headline = it.headline,
                    summary = it.summary,
                    source = it.source
                )
            )
        }
        return result
    }

    suspend fun updateStock(stock: Stock) {
        stocksDao.updateStock(stock)
    }
}