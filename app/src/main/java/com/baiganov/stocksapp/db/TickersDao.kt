package com.baiganov.stocksapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.baiganov.stocksapp.data.entity.TickersEntity
import com.baiganov.stocksapp.data.model.Stock

@Dao
interface TickersDao {

    @Query("SELECT * FROM tickers_table")
    suspend fun getStocks(): TickersEntity

    @Insert
    suspend fun insertStocks(tickersEntity: TickersEntity)

    @Query("DELETE FROM tickers_table")
    suspend fun deleteAll()
}