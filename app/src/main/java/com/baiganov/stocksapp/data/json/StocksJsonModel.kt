package com.baiganov.stocksapp.data.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StocksJsonModel (val name: String)


@Serializable
data class AllStocksData(
        @SerialName("constituents")
        val constituents: List<String>,
        @SerialName("symbol")
        val symbol: String
        )