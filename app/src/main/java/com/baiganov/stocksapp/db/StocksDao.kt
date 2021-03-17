package com.baiganov.stocksapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.baiganov.stocksapp.data.model.Stock
import kotlinx.coroutines.flow.Flow

@Dao
interface StocksDao {

    @Query("SELECT * FROM list_stocks")
    suspend fun getStocks(): List<Stock>

    @Insert
    suspend fun insertStocks(stocks: List<Stock>)

    @Query("DELETE FROM list_stocks")
    suspend fun deleteAll()

    @Query("SELECT * FROM list_stocks WHERE ticker LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Stock>>
}