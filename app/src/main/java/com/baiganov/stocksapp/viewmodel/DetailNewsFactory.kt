package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baiganov.stocksapp.repositories.DetailRepositoryImpl

@Suppress("UNCHECKED_CAST")
class DetailNewsFactory (private val detailRepositoryImpl: DetailRepositoryImpl) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        DetailNewsViewModel::class.java -> DetailNewsViewModel(detailRepositoryImpl)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}