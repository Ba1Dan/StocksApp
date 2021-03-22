package com.baiganov.stocksapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.entity.StockEntity

@Dao
interface FavouriteStockDao {

    @Query("SELECT * FROM favourite_stocks")
    suspend fun getStocks(): List<FavouriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stock: FavouriteEntity)

    @Delete
    suspend fun delete(stock: FavouriteEntity)

    @Query("DELETE FROM favourite_stocks")
    suspend fun deleteAll()

    @Query("SELECT name FROM favourite_stocks")
    suspend fun getNames(): List<String>
}