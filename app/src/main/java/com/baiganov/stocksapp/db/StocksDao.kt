package com.baiganov.stocksapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.baiganov.stocksapp.data.model.Stock

@Dao
interface StocksDao {

    @Query("SELECT * FROM list_stocks")
    suspend fun getStocks(): List<Stock>

    @Insert
    suspend fun insertStocks(stocks: List<Stock>)

    @Query("DELETE FROM list_stocks")
    suspend fun deleteAll()
}