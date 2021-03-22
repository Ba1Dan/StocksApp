package com.baiganov.stocksapp.data.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class StockInfo(
        @SerialName("country")
        val country: String = "",
        @SerialName("currency")
        val currency: String = "",
        @SerialName("exchange")
        val exchange: String = "",
        @SerialName("ipo")
        val ipo: String = "",
        @SerialName("marketCapitalization")
        val marketCapitalization: Float = 0F,
        @SerialName("name")
        val name: String = "",
        @SerialName("phone")
        val phone: String = "",
        @SerialName("shareOutstanding")
        val shareOutstanding: Float = 0F,
        @SerialName("ticker")
        val ticker: String = "",
        @SerialName("weburl")
        val webUrl: String = "",
        @SerialName("logo")
        val logo: String = "",
        @SerialName("finnhubIndustry")
        val industry: String = ""
)