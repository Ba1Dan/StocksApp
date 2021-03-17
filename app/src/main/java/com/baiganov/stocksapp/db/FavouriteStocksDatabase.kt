package com.baiganov.stocksapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.model.Stock

@Database(entities = [FavouriteEntity::class, Stock::class], version = 1, exportSchema = true)
abstract class StocksDatabase : RoomDatabase() {

    abstract val favouriteStockDao: FavouriteStockDao
    abstract val stockDao: StocksDao

    companion object {
        fun create(context: Context): StocksDatabase =
            Room.databaseBuilder(context, StocksDatabase::class.java, "stocks_application_new.db")
                .build()
    }
}