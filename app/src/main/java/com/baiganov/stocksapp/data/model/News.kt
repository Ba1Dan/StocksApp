package com.baiganov.stocksapp.data.model

data class News (
    val id: Int,
    val datetime: Long,
    val headline: String,
    val source: String,
    val summary: String
)