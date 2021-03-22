package com.baiganov.stocksapp.repositories

import com.baiganov.stocksapp.data.entity.SuggestionEntity
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.data.model.Suggestion
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchDatabase(searchQuery: String): Flow<List<Stock>>

    suspend fun updateStock(stock: Stock)

    suspend fun insert(suggestion: SuggestionEntity)

    fun getSuggestions(): Flow<List<String>>
}