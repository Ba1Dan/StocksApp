package com.baiganov.stocksapp.repositories

import com.baiganov.stocksapp.data.json.StockInfo
import com.baiganov.stocksapp.data.json.StockPrice
import com.baiganov.stocksapp.data.model.Stock
import kotlinx.coroutines.flow.Flow

interface StocksRepository {

    suspend fun getStocks(): List<Stock>

    suspend fun updateDate(count: Int): List<Stock>

    suspend fun getStockInfo(ticker: String): StockInfo

    suspend fun getStockPrice(ticker: String): StockPrice

    suspend fun updateStock(stock: Stock)
}