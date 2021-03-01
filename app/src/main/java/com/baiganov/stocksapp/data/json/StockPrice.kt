package com.baiganov.stocksapp.data.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockPrice (
        @SerialName("c")
        val currentPrice: Float,
        @SerialName("h")
        val highPrice: Float,
        @SerialName("l")
        val lowPrice: Float,
        @SerialName("pc")
        val previousClosePrice: Float,
        @SerialName("o")
        val openPrice: Float
        )