package com.baiganov.stocksapp.repositories

import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.StocksDao

class DetailRepositoryImpl(private val stocksDao: StocksDao) {

    suspend fun get(stockTicker: String): Stock {
        return stocksDao.getStock(stockTicker)
    }

    fun getNews(stockTicker: String) {

    }

    suspend fun updateStock(stock: Stock) {
        stocksDao.updateStock(stock)
    }
}