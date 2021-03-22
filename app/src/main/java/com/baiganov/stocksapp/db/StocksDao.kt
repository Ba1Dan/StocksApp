package com.baiganov.stocksapp.db

import androidx.room.*
import com.baiganov.stocksapp.data.model.Stock
import kotlinx.coroutines.flow.Flow

@Dao
interface StocksDao {

    @Query("SELECT * FROM list_stocks")
    suspend fun getStocks(): List<Stock>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStocks(stocks: List<Stock>)

    @Update
    suspend fun updateStock(stock: Stock)

    @Query("DELETE FROM list_stocks")
    suspend fun deleteAll()

    @Query("SELECT * FROM list_stocks WHERE ticker LIKE :searchQuery OR name LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Stock>>
}