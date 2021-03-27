package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baiganov.stocksapp.repositories.DetailRepositoryImpl
import com.baiganov.stocksapp.repositories.FavouriteRepository

class ChartFactory(private val detailRepositoryImpl: DetailRepositoryImpl) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        ChartViewModel::class.java -> ChartViewModel(detailRepositoryImpl)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}