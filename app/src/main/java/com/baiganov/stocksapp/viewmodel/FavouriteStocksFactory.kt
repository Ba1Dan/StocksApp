package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baiganov.stocksapp.db.FavouriteStockDao

class FavouriteStocksFactory(private val db: FavouriteStockDao) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        FavouriteStocksViewModel::class.java ->  FavouriteStocksViewModel(db = db)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}