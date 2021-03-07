package com.baiganov.stocksapp.repositories

import com.baiganov.stocksapp.api.ApiService
import com.baiganov.stocksapp.data.json.AllStocksData
import com.baiganov.stocksapp.data.json.StockInfo
import com.baiganov.stocksapp.data.json.StockPrice
import com.baiganov.stocksapp.data.json.StocksJsonModel
import com.baiganov.stocksapp.data.model.Stock

class StocksRepositoryImpl(
    private val apiService: ApiService
) : StocksRepository {

    override suspend fun getIndex(): List<Stock> {
        val tickers = apiService.getIndex().constituents
        val data = mutableListOf<Stock>()
        for (i in 0..10) {
            val infoCompany = getStockInfo(tickers[i])
            val infoPrice = getStockPrice(tickers[i])
            data.add(
                Stock(
                    currency = infoCompany.currency,
                    name = infoCompany.name,
                    ticker = infoCompany.ticker,
                    logo = infoCompany.logo,
                    currentPrice = infoPrice.currentPrice
            ))
        }
        return data
    }

    override suspend fun getStockInfo(ticker: String): StockInfo {
        return apiService.getInfoStock(ticker = ticker)
    }

    override suspend fun getStockPrice(ticker: String): StockPrice {
        return apiService.getInfoPriceStock(ticker = ticker)
    }
}