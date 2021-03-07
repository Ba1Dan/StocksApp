package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baiganov.stocksapp.data.entity.StockEntity
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.FavouriteStockDao
import com.baiganov.stocksapp.repositories.StocksRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StocksListViewModel(private val repository: StocksRepositoryImpl, private val db: FavouriteStockDao) : ViewModel(){

    private val _data = MutableLiveData<List<Stock>>()

    val data: LiveData<List<Stock>> = _data

    init {
        loadData()
    }

    fun insert(stock: StockEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            db.insert(stock)
        }
    }

    fun delete(stock: StockEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            db.delete(stock)
        }
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val str = repository.getIndex()
            _data.postValue(str)
        }
    }
}