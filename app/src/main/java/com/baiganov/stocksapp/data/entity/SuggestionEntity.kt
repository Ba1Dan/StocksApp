package com.baiganov.stocksapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suggestion_table")
data class SuggestionEntity (
    @PrimaryKey
    val names: String
)