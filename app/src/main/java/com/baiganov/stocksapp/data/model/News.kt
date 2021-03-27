package com.baiganov.stocksapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class News (
    @PrimaryKey
    val id: Int,
    val datetime: Long,
    val headline: String,
    val source: String,
    val summary: String
)