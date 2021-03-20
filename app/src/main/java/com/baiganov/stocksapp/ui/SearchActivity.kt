package com.baiganov.stocksapp.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baiganov.stocksapp.MainActivity
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.adapters.StocksAdapter
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.data.model.StockTitle
import com.baiganov.stocksapp.data.model.Suggestion

class SearchActivity : AppCompatActivity() {

    private lateinit var rvSearch: RecyclerView
    private lateinit var adapter: StocksAdapter
    private lateinit var searchView: SearchView
    private var flag: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        rvSearch = findViewById(R.id.rv_search)
        searchView = findViewById(R.id.sv_active)
        setupRecyclerView()
        searchView.requestFocus()
        setupSuggestions()
        searchView.setOnQueryTextFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (p1) {
                    searchView.queryHint = ""
                    setupSuggestions()
                } else if (flag){
                    finish()
                }
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    if (query.isNotEmpty()) {
                        flag = false
                        changeViewType()
                    } else {
                        flag = true
                        setupSuggestions()
                    }
                }
                return true
            }
        })

        val btnBack = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        btnBack.setOnClickListener{
            finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = StocksAdapter(clickListener)
        rvSearch.adapter = adapter
        rvSearch.layoutManager = LinearLayoutManager(this)
    }

    private fun setupSuggestions() {
        val popular: MutableList<StockTitle> = ArrayList()
        popular.add(StockTitle("Apple"))
        popular.add(StockTitle("Тинькофф"))
        popular.add(StockTitle("Яндекс"))
        popular.add(StockTitle("Mail.ru"))
        popular.add(StockTitle("Bank of America"))
        popular.add(StockTitle("Tesla"))
        popular.add(StockTitle("Virgin"))
        popular.add(StockTitle("Amazon"))
        popular.add(StockTitle("Google"))
        popular.add(StockTitle("Microsoft"))
        popular.add(StockTitle("Mastercard"))
        popular.add(StockTitle("Facebook"))

        val searched: MutableList<StockTitle> = ArrayList()
        searched.add(StockTitle("Amd"))
        searched.add(StockTitle("NVidia"))
        searched.add(StockTitle("Intel"))
        searched.add(StockTitle("Baidu"))
        searched.add(StockTitle("Alibaba"))
        searched.add(StockTitle("Polo"))
        searched.add(StockTitle("Logan"))
        searched.add(StockTitle("Vesta"))
        searched.add(StockTitle("Lada"))
        searched.add(StockTitle("BMW"))
        searched.add(StockTitle("Яндекс"))
        searched.add(StockTitle("Сбербанк"))
        searched.add(StockTitle("ВТБ"))
        searched.add(StockTitle("Магнит"))
        searched.add(StockTitle("Пятерочка"))


        val data: MutableList<Suggestion> = ArrayList()
        data.add(Suggestion("Popular requests", popular))
        data.add(Suggestion("You've searched for this", searched))
        //setupRecyclerView()
        adapter.setData(data)
    }

    private fun changeViewType() {
        val stocks: MutableList<Stock> = ArrayList()
        stocks.add(Stock("235", "234.5f", "ТФТ", logo = "https://static.finnhub.io/logo/87cb30d8-80df-11ea-8951-00000000092a.png", priceDelta = 24.4, currentPrice = 235.4f))

        adapter.setData(stocks)
    }

    private val clickListener = StocksAdapter.ItemClickListener { favourite, stock -> onClick(favourite = favourite, stock = stock) }

    private fun onClick(favourite: Boolean, stock: FavouriteEntity) {
        if (favourite) {
            stock.isFavourite = false
            //mainViewModel.delete(stock)
        } else {
            stock.isFavourite = true
            //mainViewModel.insert(stock)
        }
    }

    /*private fun searchDatabase(query: String) {
        val searchQuery = "$query%"
        mainViewModel.searchDatabase(searchQuery).observe(this, {
            rvAdapter.setData(it)
        })
    }*/
}