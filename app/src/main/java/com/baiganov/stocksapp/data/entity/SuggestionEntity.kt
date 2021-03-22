package com.baiganov.stocksapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.baiganov.stocksapp.data.SuggestionConverter
import com.baiganov.stocksapp.data.TickersConverter
import com.baiganov.stocksapp.data.model.StockTitle

@Entity(tableName = "suggestion_table")
data class SuggestionEntity (
    @PrimaryKey
    val names: String
)