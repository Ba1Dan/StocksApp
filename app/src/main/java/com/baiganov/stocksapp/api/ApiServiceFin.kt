package com.baiganov.stocksapp.api

import com.baiganov.stocksapp.data.json.AllStocksData
import com.baiganov.stocksapp.data.json.NewsModel
import com.baiganov.stocksapp.data.json.StockInfo
import com.baiganov.stocksapp.data.json.StockPrice
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceFin {

    @GET("index/constituents")
    suspend fun getIndex(
        @Query(QUERY_PARAM_SYMBOL) index: String = INDEX,
        @Query(QUERY_PARAM_TOKEN) token: String = TOKEN
    ): AllStocksData

    @GET("stock/profile2")
    suspend fun getInfoStock(
        @Query(QUERY_PARAM_SYMBOL) ticker: String,
        @Query(QUERY_PARAM_TOKEN) token: String = TOKEN
    ): StockInfo

    @GET("quote")
    suspend fun getInfoPriceStock(
        @Query(QUERY_PARAM_SYMBOL) ticker: String,
        @Query(QUERY_PARAM_TOKEN) token: String = TOKEN
    ): StockPrice

    @GET("company-news")
    suspend fun getNews(
        @Query(QUERY_PARAM_SYMBOL) ticker: String,
        @Query(QUERY_PARAM_FROM) from: String = FROM,
        @Query(QUERY_PARAM_TO) to: String = TO,
        @Query(QUERY_PARAM_TOKEN) token: String = TOKEN
    ): List<NewsModel>
    companion object {
        private const val QUERY_PARAM_TOKEN = "token"
        private const val QUERY_PARAM_SYMBOL = "symbol"
        private const val QUERY_PARAM_FROM = "from"
        private const val QUERY_PARAM_TO = "to"


        private const val TOKEN = "c0oc24v48v6qah6s3vi0"
        private const val INDEX = "^GSPC"
        private const val FROM = "2021-03-01"
        private const val TO = "2021-03-27"
    }
}