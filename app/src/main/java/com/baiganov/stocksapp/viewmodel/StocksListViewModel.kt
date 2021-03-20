package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.entity.StockEntity
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.FavouriteStockDao
import com.baiganov.stocksapp.repositories.StocksRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class StocksListViewModel(private val repository: StocksRepositoryImpl, private val db: FavouriteStockDao) : ViewModel(){

    private val _data = MutableLiveData<List<Stock>>()
    private val _isLoading = MutableLiveData<Boolean>(true)
    private val _isNetworking = MutableLiveData<Boolean>(true)

    val data: LiveData<List<Stock>> = _data
    val isLoading: LiveData<Boolean> = _isLoading
    val isNetworking: LiveData<Boolean> = _isNetworking

    fun insert(stock: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            db.insert(stock)
        }
    }

    fun delete(stock: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            db.delete(stock)
        }
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = repository.updateDate()
                _isLoading.postValue(false)
                _data.postValue(data)
            } catch (e: Exception) {
                e.printStackTrace()
                _isNetworking.postValue(false)
                _isLoading.postValue(false)
                _data.postValue(repository.getStocks())
            }
        }
    }
}