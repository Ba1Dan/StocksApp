package com.baiganov.stocksapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.entity.SuggestionEntity
import com.baiganov.stocksapp.data.entity.TickersEntity
import com.baiganov.stocksapp.data.model.News
import com.baiganov.stocksapp.data.model.Stock

@Database(entities = [FavouriteEntity::class, Stock::class, SuggestionEntity::class, News::class], version = 1, exportSchema = false)
abstract class StocksDatabase : RoomDatabase() {

    abstract val favouriteStockDao: FavouriteStockDao
    abstract val stockDao: StocksDao
    abstract val newsDao: NewsDao
    abstract val suggestionDao: SuggestionDao

    companion object {
        @Volatile
        private var INSTANCE: StocksDatabase? = null
        private const val DB_NAME = "main_application.db"

        fun create(context: Context): StocksDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StocksDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}