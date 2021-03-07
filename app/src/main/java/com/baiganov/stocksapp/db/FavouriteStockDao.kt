package com.baiganov.stocksapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.baiganov.stocksapp.data.entity.StockEntity

@Dao
interface FavouriteStockDao {

    @Query("SELECT * FROM favourite_stocks")
    fun getData(): LiveData<List<StockEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stock: StockEntity)

    @Delete
    suspend fun delete(stock: StockEntity)

    @Query("DELETE FROM favourite_stocks")
    suspend fun deleteAll()
}