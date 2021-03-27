package com.baiganov.stocksapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baiganov.stocksapp.api.ApiServiceAlpha
import com.baiganov.stocksapp.data.json.StockAlpha
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SummaryViewModel(private val apiServiceAlpha: ApiServiceAlpha) : ViewModel() {

    private val _data = MutableLiveData<StockAlpha>()

    val data: LiveData<StockAlpha> = _data

    fun load(ticker: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val s = apiServiceAlpha.getInfo(ticker = ticker)
            Log.d("DEBUG", "viewmodel $s")
            _data.postValue(s)
        }
    }
}