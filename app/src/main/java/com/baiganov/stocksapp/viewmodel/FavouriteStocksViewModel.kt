package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baiganov.stocksapp.data.entity.StockEntity
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.FavouriteStockDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteStocksViewModel(private val db: FavouriteStockDao) : ViewModel() {

    val data: LiveData<List<StockEntity>> = db.getData()

    init {
        //deleteAll()
    }
    fun insert(stock: StockEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            db.insert(stock = stock)
        }
    }

    fun delete(stock: StockEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            db.delete(stock = stock)
        }
    }

    private fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            db.deleteAll()
        }
    }
}