package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.FavouriteStockDao
import com.baiganov.stocksapp.repositories.StocksRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: StocksRepositoryImpl, private val favouriteStockDao: FavouriteStockDao) : ViewModel() {

    fun searchDatabase(searchQuery: String): LiveData<List<Stock>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }

    fun insert(stock: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteStockDao.insert(stock)
        }
    }

    fun delete(stock: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteStockDao.delete(stock)
        }
    }
}