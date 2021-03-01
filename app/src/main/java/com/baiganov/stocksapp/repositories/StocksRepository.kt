package com.baiganov.stocksapp.repositories

import com.baiganov.stocksapp.data.json.StockInfo
import com.baiganov.stocksapp.data.json.StockPrice
import com.baiganov.stocksapp.data.model.Stock

interface StocksRepository {

    suspend fun getIndex(): List<Stock>

    suspend fun getStockInfo(ticker: String): StockInfo

    suspend fun getStockPrice(ticker: String): StockPrice
}