package com.baiganov.stocksapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.entity.SuggestionEntity
import com.baiganov.stocksapp.data.entity.convertToStock
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.data.model.Suggestion
import com.baiganov.stocksapp.repositories.FavouriteRepository
import com.baiganov.stocksapp.repositories.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val favouriteRepository: FavouriteRepository
) : ViewModel() {

    val recentQueries:LiveData<List<String>> = searchRepository.getSuggestions().asLiveData()

    fun search(searchQuery: String): LiveData<List<Stock>> {
        return searchRepository.searchDatabase(searchQuery).asLiveData()
    }

    fun insert(stock: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteRepository.insertStock(stock)
            searchRepository.updateStock(convertToStock(stock))
        }
    }

    fun delete(stock: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteRepository.deleteStock(stock)
            searchRepository.updateStock(convertToStock(stock))
        }
    }

    fun save(suggestion: SuggestionEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            searchRepository.insert(suggestion)
        }
    }
}