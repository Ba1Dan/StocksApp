package com.baiganov.stocksapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.adapters.ClickListener
import com.baiganov.stocksapp.adapters.StocksAdapter
import com.baiganov.stocksapp.adapters.VerticalSpaceItemDecoration
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.entity.SuggestionEntity
import com.baiganov.stocksapp.data.model.Section
import com.baiganov.stocksapp.data.model.Suggestion
import com.baiganov.stocksapp.db.StocksDatabase
import com.baiganov.stocksapp.repositories.FavouriteRepositoryImpl
import com.baiganov.stocksapp.repositories.SearchRepositoryImpl
import com.baiganov.stocksapp.viewmodel.SearchFactory
import com.baiganov.stocksapp.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var rvSearch: RecyclerView
    private lateinit var rvAdapter: StocksAdapter
    private lateinit var searchView: SearchView
    private lateinit var btnBack: ImageView
    private lateinit var searchViewModel: SearchViewModel
    private var emptySearchView: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
        setupViewModel()
        setupRecyclerView()
        setupSearchView()
        btnBack.setOnClickListener{
            finish()
        }
    }

    private fun setupViewModel() {
        val database = StocksDatabase.create(applicationContext)
        searchViewModel = ViewModelProvider(this, SearchFactory(SearchRepositoryImpl(database.stockDao, database.suggestionDao), FavouriteRepositoryImpl(database.favouriteStockDao))).get(
            SearchViewModel::class.java)
    }

    private fun initView() {
        rvSearch = findViewById(R.id.rv_search)
        searchView = findViewById(R.id.sv_active)
        btnBack = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon)
    }

    private fun setupSearchView() {
        searchView.requestFocus()
        setupSuggestions()
        searchView.setOnQueryTextFocusChangeListener(object: View.OnFocusChangeListener {

            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (p1 && emptySearchView) {
                    setupSuggestions()
                } else if (emptySearchView){
                    finish()
                }
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(text: String?): Boolean {
                if (text != null) {
                    searchViewModel.save(SuggestionEntity(text))
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query == null) {
                    rvSearch.addItemDecoration(VerticalSpaceItemDecoration(8))
                }
                if (query != null) {
                    if (query.isNotEmpty()) {
                        emptySearchView = false
                        searchDatabase(query)
                    } else {
                        emptySearchView = true
                        setupSuggestions()
                    }
                }
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        rvAdapter = StocksAdapter(object : ClickListener {

            override fun onClickStar(stock: FavouriteEntity) {
                if (stock.isFavourite) {
                    searchViewModel.insert(stock)
                } else {
                    searchViewModel.delete(stock)
                }
            }

            override fun onClickItem(name: String) {
                Toast.makeText(applicationContext, name, Toast.LENGTH_LONG).show()
            }

            override fun onClickTitleStock(name: String) {
                searchView.setQuery(name, true)
            }

            override fun onClickShow() {
                val query = searchView.query.toString()
                val searchQuery = "$query%"
                searchView.clearFocus()
                searchViewModel.fullSearch(searchQuery)
                searchViewModel.resultSearch.observe(this@SearchActivity, {
                    rvAdapter.setData(it)
                })
            }
        })
        rvSearch.adapter = rvAdapter
        rvSearch.layoutManager = LinearLayoutManager(this)
    }

    private fun setupSuggestions() {

        val data = mutableListOf(Suggestion(POPULAR, listOf("Apple", "First Solar", "Yandex", "Alibaba", "Tesla", "Amazon", "Google", "Microsoft", "Mastercard", "Facebook")))
        searchViewModel.recentQueries.observe(this, {
            if (it.isNotEmpty()) {
                data.add(Suggestion(RECENT, it.reversed()))
            }
        })
        rvSearch.addItemDecoration(VerticalSpaceItemDecoration(8))
        rvAdapter.setData(data)
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "$query%"
        searchViewModel.search(searchQuery)
        searchViewModel.resultSearch.observe(this, {
            rvAdapter.setData(listOf(Section(STOCKS, it)))
        })
    }

    companion object {
        private const val POPULAR = "Popular requests"
        private const val RECENT = "You've searched for this"
        private const val STOCKS = "Stocks"
        private const val BONDS = "Bonds"
        private const val FOUNDATIONS = "Foundations"
    }
}