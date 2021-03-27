package com.baiganov.stocksapp.db

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.baiganov.stocksapp.data.model.News
import retrofit2.http.DELETE


@Dao
interface NewsDao {

    @Insert
    suspend fun insert(news: List<News>)

    @Query("SELECT * FROM news_table ORDER BY datetime DESC")
    fun news(): PagingSource<Int, News>

    @Query("DELETE FROM news_table")
    suspend fun deleteAll()
}