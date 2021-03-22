package com.baiganov.stocksapp.repositories

import com.baiganov.stocksapp.data.entity.SuggestionEntity
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.data.model.Suggestion
import com.baiganov.stocksapp.db.StocksDao
import com.baiganov.stocksapp.db.SuggestionDao
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(
    private val stocksDao: StocksDao,
    private val suggestionDao: SuggestionDao
) : SearchRepository {

    override fun searchDatabase(searchQuery: String): Flow<List<Stock>> {
        return stocksDao.searchDatabase(searchQuery)
    }

    override suspend fun updateStock(stock: Stock) {
        stocksDao.updateStock(stock)
    }

    override suspend fun insert(suggestion: SuggestionEntity) {
        suggestionDao.insert(suggestion)
    }

    override fun getSuggestions(): Flow<List<String>> {
        return suggestionDao.getData()
    }
}