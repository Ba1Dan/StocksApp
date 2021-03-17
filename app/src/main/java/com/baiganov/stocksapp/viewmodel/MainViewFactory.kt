package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baiganov.stocksapp.api.ApiService
import com.baiganov.stocksapp.db.FavouriteStockDao
import com.baiganov.stocksapp.db.StocksDatabase
import com.baiganov.stocksapp.repositories.StocksRepositoryImpl

class MainViewFactory(private val repository: StocksRepositoryImpl, private val favouriteStockDao: FavouriteStockDao) : ViewModelProvider.NewInstanceFactory() {

    /*override fun <T : ViewModel?> create(modelClass: Class<T>): T  = when(modelClass) {
        StocksListViewModel::class.java -> MainViewModel(StocksRepositoryImpl(apiService, database.stockDao), database.favouriteStockDao)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T*/

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when(modelClass) {
        MainViewModel::class.java -> MainViewModel(repository, favouriteStockDao)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}