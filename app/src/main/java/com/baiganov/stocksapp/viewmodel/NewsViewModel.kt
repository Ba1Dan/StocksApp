package com.baiganov.stocksapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.baiganov.stocksapp.data.model.News
import com.baiganov.stocksapp.db.NewsDao
import com.baiganov.stocksapp.repositories.DetailRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class NewsViewModel(
    private val detailRepositoryImpl: DetailRepositoryImpl,
) : ViewModel() {

    val news = detailRepositoryImpl.getNews()

    fun load(ticker: String) {
        viewModelScope.launch(Dispatchers.IO) {
            detailRepositoryImpl.loadNews(ticker)
        }
    }

    fun clear() {
        viewModelScope.launch(Dispatchers.IO) {
            detailRepositoryImpl.clear()
        }
    }
}