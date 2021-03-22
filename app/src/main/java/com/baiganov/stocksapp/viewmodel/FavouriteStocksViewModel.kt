package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.db.FavouriteStockDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteStocksViewModel(private val db: FavouriteStockDao) : ViewModel() {

    private val _data = MutableLiveData<List<FavouriteEntity>>()

    val data: LiveData<List<FavouriteEntity>> = _data

    init {
        //deleteAll()
        getData()
    }
    fun insert(stock: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            db.insert(stock = stock)
        }
    }

    fun delete(stock: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            db.delete(stock = stock)
        }
    }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO){
            _data.postValue(db.getStocks())
        }
    }
    private fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            db.deleteAll()
        }
    }
}