package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baiganov.stocksapp.data.json.StockInfo
import com.baiganov.stocksapp.data.json.StocksJsonModel
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StocksListViewModel(private val repository: Repository) : ViewModel(){

    private val _data = MutableLiveData<List<Stock>>()

    val data: LiveData<List<Stock>> = _data

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val str = repository.getIndex()
            _data.postValue(str)
        }
    }
}