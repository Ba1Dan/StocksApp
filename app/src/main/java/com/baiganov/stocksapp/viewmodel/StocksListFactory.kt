package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baiganov.stocksapp.repositories.Repository

class StocksListFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T  = when(modelClass) {
        StocksListViewModel::class.java -> StocksListViewModel(repository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}