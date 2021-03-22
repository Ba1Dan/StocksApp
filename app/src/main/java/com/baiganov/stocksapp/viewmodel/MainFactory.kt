package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baiganov.stocksapp.repositories.FavouriteRepository
import com.baiganov.stocksapp.repositories.StocksRepository

class MainFactory(
    private val stocksRepository: StocksRepository,
    private val favouriteRepository: FavouriteRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MainViewModel::class.java -> MainViewModel(stocksRepository, favouriteRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}