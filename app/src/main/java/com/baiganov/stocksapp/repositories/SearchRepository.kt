package com.baiganov.stocksapp.repositories

import com.baiganov.stocksapp.data.entity.SuggestionEntity
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.data.model.Suggestion
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchDatabase(searchQuery: String): List<Stock>

    suspend fun fullSearchDatabase(searchQuery: String): List<Stock>

    suspend fun updateStock(stock: Stock)

    suspend fun insert(suggestion: SuggestionEntity)

    fun getSuggestions(): Flow<List<String>>
}