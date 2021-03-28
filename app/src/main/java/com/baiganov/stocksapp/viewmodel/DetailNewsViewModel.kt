package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baiganov.stocksapp.data.model.News
import com.baiganov.stocksapp.repositories.DetailRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailNewsViewModel(private val detailRepositoryImpl: DetailRepositoryImpl) : ViewModel() {

    private var _data = MutableLiveData<News>()

    val data: LiveData<News> = _data

    fun getNews(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _data.postValue(detailRepositoryImpl.getNewsOnId(id))
        }
    }
}