package com.baiganov.stocksapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.baiganov.stocksapp.data.TickersConverter

@TypeConverters(TickersConverter::class)
@Entity(tableName = "tickers_table")
data class TickersEntity(
    @PrimaryKey
    val tickers: List<String>
)

