package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baiganov.stocksapp.db.FavouriteStockDao
import com.baiganov.stocksapp.repositories.StocksRepositoryImpl

class StocksListFactory(private val repository: StocksRepositoryImpl, private val db: FavouriteStockDao) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T  = when(modelClass) {
        StocksListViewModel::class.java -> StocksListViewModel(repository, db = db)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}