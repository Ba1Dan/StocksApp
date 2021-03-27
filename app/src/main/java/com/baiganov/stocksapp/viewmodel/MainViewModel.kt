package com.baiganov.stocksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.entity.convertToStock
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.repositories.FavouriteRepository
import com.baiganov.stocksapp.repositories.StocksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val stocksRepository: StocksRepository,
    private val favouriteRepository: FavouriteRepository
) : ViewModel() {

    private val _data = MutableLiveData<List<Stock>>()
    private val _isLoading = MutableLiveData<Boolean>(true)
    private val _isNetworking = MutableLiveData<Boolean>(true)
    private var count: Int = 0

    val data: LiveData<List<Stock>> = _data
    val isLoading: LiveData<Boolean> = _isLoading
    val isNetworking: LiveData<Boolean> = _isNetworking


    fun insert(stock: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            stocksRepository.updateStock(convertToStock(stock))
            favouriteRepository.insertStock(stock)
        }
    }

    fun delete(stock: FavouriteEntity, tvStocks: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteRepository.deleteStock(stock)
            stocksRepository.updateStock(convertToStock(stock))
            if (tvStocks) {
                _data.postValue(stocksRepository.getStocks())
            } else {
                _data.postValue(favouriteRepository.getStocks().map {
                    convertToStock(it)
                })
            }
        }
    }

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = stocksRepository.updateDate(count++)
            _data.postValue(data)
        }
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = stocksRepository.updateDate(count++)
                val favourites = favouriteRepository.getNames()
                data.forEach {
                    if (favourites.contains(it.name)) {
                        it.isFavourite = true
                        stocksRepository.updateStock(it)
                    }
                }
                _isLoading.postValue(false)
                _data.postValue(data)
            } catch (e: Exception) {
                e.printStackTrace()
                _isNetworking.postValue(false)
                _isLoading.postValue(false)
                _data.postValue(stocksRepository.getStocks())
            }
        }
    }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = stocksRepository.getStocks()
            val favourites = favouriteRepository.getNames()
            data.forEach {
                if (favourites.contains(it.name)) {
                    it.isFavourite = true
                    stocksRepository.updateStock(it)
                }
            }
            _data.postValue(data)
        }
    }

    fun getDataFavourite() {
        viewModelScope.launch {
            _data.postValue(favouriteRepository.getStocks().map {
                convertToStock(it)
            })
        }
    }
}