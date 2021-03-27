package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.entity.convertToStock
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.repositories.DetailRepositoryImpl
import com.baiganov.stocksapp.repositories.FavouriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val favouriteRepository: FavouriteRepository, private val detailRepositoryImpl: DetailRepositoryImpl) : ViewModel() {

    private val _data = MutableLiveData<Stock>()

    val data: LiveData<Stock> = _data

    fun insert(stock: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteRepository.insertStock(stock)
            detailRepositoryImpl.updateStock(convertToStock(stock))
        }
    }

    fun delete(stock: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteRepository.deleteStock(stock)
            detailRepositoryImpl.updateStock(convertToStock(stock))
        }
    }

    fun getStock(stockTicker: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _data.postValue(detailRepositoryImpl.getStock(stockTicker))
        }
    }


}