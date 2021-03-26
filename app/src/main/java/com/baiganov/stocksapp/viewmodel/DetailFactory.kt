package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baiganov.stocksapp.repositories.DetailRepositoryImpl
import com.baiganov.stocksapp.repositories.FavouriteRepository

class DetailFactory(private val favouriteRepository: FavouriteRepository, private val detailRepositoryImpl: DetailRepositoryImpl) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        DetailViewModel::class.java -> DetailViewModel(favouriteRepository, detailRepositoryImpl)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}