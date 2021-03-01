package com.baiganov.stocksapp.api

import com.baiganov.stocksapp.data.json.AllStocksData
import com.baiganov.stocksapp.data.json.StockInfo
import com.baiganov.stocksapp.data.json.StockPrice
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

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
    ) : StockPrice

    companion object {
        private const val QUERY_PARAM_TOKEN = "token"
        private const val QUERY_PARAM_SYMBOL = "symbol"


        private const val TOKEN = "c0oc24v48v6qah6s3vi0"
        private const val INDEX = "^GSPC"
    }
}