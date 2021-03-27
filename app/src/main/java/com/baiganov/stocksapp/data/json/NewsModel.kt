package com.baiganov.stocksapp.data.json

import kotlinx.serialization.Serializable

@Serializable
data class NewsModel (
    val category: String,
    val datetime: Long,
    val headline: String,
    val id: Int,
    val image: String,
    val related: String,
    val source: String,
    val summary: String,
    val url: String
)