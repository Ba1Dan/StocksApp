package com.baiganov.stocksapp.api

import com.baiganov.stocksapp.data.json.NewsModel
import com.baiganov.stocksapp.data.json.StockAlpha
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceAlpha {

    @GET("query")
    suspend fun getInfo(
        @Query(QUERY_PARAM_FUNCTION) function: String = FUNCTION,
        @Query(QUERY_PARAM_SYMBOL) ticker: String,
        @Query(QUERY_PARAM_API_KEY) token: String = API_KEY
    ): StockAlpha
    companion object {
        private const val QUERY_PARAM_API_KEY = "apikey"
        private const val QUERY_PARAM_SYMBOL = "symbol"
        private const val QUERY_PARAM_FUNCTION = "function"

        private const val API_KEY = "F8KJ68Q1YDJ7PP7C"
        private const val FUNCTION = "OVERVIEW"
    }
}