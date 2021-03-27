package com.baiganov.stocksapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baiganov.stocksapp.data.model.News
import com.baiganov.stocksapp.repositories.DetailRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class NewsViewModel(private val detailRepositoryImpl: DetailRepositoryImpl) : ViewModel() {

    private val _news = MutableLiveData<List<News>>()

    val news: LiveData<List<News>> = _news

    fun load(ticker: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = detailRepositoryImpl.getNews(ticker)
            _news.postValue(data)
        }
    }
}