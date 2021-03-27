package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.repositories.DetailRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChartViewModel(private val detailRepositoryImpl: DetailRepositoryImpl) : ViewModel() {

    private val _data = MutableLiveData<Stock>()

    val data: LiveData<Stock> = _data

    fun getStock(stockTicker: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _data.postValue(detailRepositoryImpl.getStock(stockTicker))
        }
    }
}