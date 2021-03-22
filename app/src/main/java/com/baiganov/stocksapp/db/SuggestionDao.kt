package com.baiganov.stocksapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.baiganov.stocksapp.data.entity.SuggestionEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface SuggestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(suggestion: SuggestionEntity)

    @Query("SELECT names FROM suggestion_table")
    fun getData(): Flow<List<String>>
}