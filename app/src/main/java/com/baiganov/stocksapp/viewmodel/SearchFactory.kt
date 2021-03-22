package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baiganov.stocksapp.repositories.FavouriteRepository
import com.baiganov.stocksapp.repositories.SearchRepository

class SearchFactory(
    private val searchRepository: SearchRepository,
    private val favouriteRepository: FavouriteRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        SearchViewModel::class.java -> SearchViewModel(searchRepository, favouriteRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}