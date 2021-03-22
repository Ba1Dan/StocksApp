package com.baiganov.stocksapp.data

import android.content.Context
import com.baiganov.stocksapp.data.model.Stock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class JsonLoad {
}

internal suspend fun loadStocks(context: Context): List<Stock> {
    val stream = context.assets.open("data.json")
    val data = stream.bufferedReader().readText()
    return parseStocks(data)
}

private fun parseStocks(data: String): List<Stock> {
    return Json.decodeFromString(data)
}