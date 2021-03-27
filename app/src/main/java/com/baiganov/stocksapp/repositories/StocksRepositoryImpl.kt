package com.baiganov.stocksapp.repositories

import android.content.Context
import com.baiganov.stocksapp.api.ApiServiceFin
import com.baiganov.stocksapp.data.json.StockInfo
import com.baiganov.stocksapp.data.json.StockPrice
import com.baiganov.stocksapp.data.loadStocks
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.StocksDao
import kotlin.math.abs

class StocksRepositoryImpl(
    private val apiServiceFin: ApiServiceFin,
    private val stocksDao: StocksDao,
    private val context: Context
) : StocksRepository {

    override suspend fun getStocks(): List<Stock> {
        return stocksDao.getStocks()
    }

    override suspend fun updateStock(stock: Stock) {
        stocksDao.updateStock(stock)
    }

    override suspend fun updateDate(count: Int): List<Stock> {
        val stocks = loadStocks(context)
        val result = mutableListOf<Stock>()
        for (i in 0*count..25*(count+1)) {
            result.add(stocks[i])
        }
        stocksDao.deleteAll()
        stocksDao.insertStocks(stocks)
        return result
    }

    override suspend fun getStockInfo(ticker: String): StockInfo {
        return apiServiceFin.getInfoStock(ticker)
    }

    override suspend fun getStockPrice(ticker: String): StockPrice {
        return apiServiceFin.getInfoPriceStock(ticker)
    }

    private fun calculatePercent(yesterday: Float, today: Float): Double {
        val result = (today / yesterday  - 1 ) * 100
        return abs((Math.round(result * 100.0) / 100.0).toDouble())
    }

    private fun calculateDeltaPrice(yesterday: Float, today: Float): Double {
        val result = today - yesterday
        return (Math.round(result * 100.0) / 100.0).toDouble()
    }
}