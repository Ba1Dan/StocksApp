package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baiganov.stocksapp.repositories.DetailRepositoryImpl

class NewsFactory(private val detailRepositoryImpl: DetailRepositoryImpl) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        NewsViewModel::class.java -> NewsViewModel(detailRepositoryImpl)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}