package com.baiganov.stocksapp.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.baiganov.stocksapp.api.ApiServiceFin
import com.baiganov.stocksapp.data.model.News
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.NewsDao
import com.baiganov.stocksapp.db.StocksDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class DetailRepositoryImpl(
    private val stocksDao: StocksDao,
    private val apiServiceFin: ApiServiceFin,
    private val newsDao: NewsDao,
) {

    suspend fun getStock(stockTicker: String): Stock {
        return stocksDao.getStock(stockTicker)
    }

    suspend fun loadNews(ticker: String) {
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
        newsDao.deleteAll()
        newsDao.insert(result)
    }

    fun getNews(): Flow<PagingData<News>> {
        return Pager(
            PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 2
            )
        ) {
            newsDao.news()
        }.flow
    }

    suspend fun updateStock(stock: Stock) {
        stocksDao.updateStock(stock)
    }
}
