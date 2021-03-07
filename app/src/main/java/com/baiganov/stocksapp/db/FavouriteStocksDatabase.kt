package com.baiganov.stocksapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.baiganov.stocksapp.data.entity.StockEntity

@Database(entities = [StockEntity::class], version = 1, exportSchema = true)
abstract class FavouriteStocksDatabase : RoomDatabase() {

    abstract val favouriteStockDao: FavouriteStockDao

    companion object {
        fun create(context: Context): FavouriteStocksDatabase =
            Room.databaseBuilder(context, FavouriteStocksDatabase::class.java, "aaa.db")
                .build()

    }
}