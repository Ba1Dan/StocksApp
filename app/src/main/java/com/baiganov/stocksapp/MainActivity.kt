package com.baiganov.stocksapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.baiganov.stocksapp.adapters.StocksAdapter
import com.baiganov.stocksapp.adapters.VerticalSpaceItemDecoration
import com.baiganov.stocksapp.api.ApiFactory
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.StocksDatabase
import com.baiganov.stocksapp.repositories.StocksRepositoryImpl
import com.baiganov.stocksapp.ui.PagerViewAdapter
import com.baiganov.stocksapp.viewmodel.MainViewFactory
import com.baiganov.stocksapp.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tablayout: TabLayout
    private lateinit var rvSearch: RecyclerView
    private lateinit var rvAdapter: StocksAdapter
    private lateinit var pagerAdapter: PagerViewAdapter
    private lateinit var searchView: SearchView
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        val database = StocksDatabase.create(applicationContext)
        mainViewModel = ViewModelProvider(this, MainViewFactory(StocksRepositoryImpl(ApiFactory.apiService, database.stockDao), database.favouriteStockDao)).get(MainViewModel::class.java)
        setupTabLayout()
        setupRecyclerView()
        setupSearchView()
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "$query%"
        mainViewModel.searchDatabase(searchQuery).observe(this, {
            rvAdapter.bindStocks(it)
        })
    }

    private fun setupRecyclerView() {
        rvAdapter = StocksAdapter(clickListener)
        rvSearch.layoutManager = LinearLayoutManager(applicationContext)
        rvSearch.adapter = rvAdapter
        rvSearch.addItemDecoration(VerticalSpaceItemDecoration(8))
    }

    private val clickListener = StocksAdapter.ItemClickListener { favourite, stock -> onClick(favourite = favourite, stock = stock) }

    private fun update(stocks: List<Stock>) {
        rvAdapter.bindStocks(stocks)
    }

    private fun initView() {
        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)
        viewPager = findViewById(R.id.view_pager)
        tablayout = findViewById(R.id.tab_layout)
        searchView = findViewById(R.id.sv_main)
        rvSearch = findViewById(R.id.rv_search)
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (p1) {
                    searchView.queryHint = ""
                } else {
                    searchView.queryHint = "Find company or ticker "
                }
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                /*if (query != null) {
                    if (query.isEmpty()) {
                        rvSearch.visibility = View.GONE
                        Log.i("QUUUEERRRYYY", query)
                        searchDatabase(query)
                    } else {
                        rvSearch.visibility = View.VISIBLE
                    }
                }*/
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isEmpty()) {
                        rvSearch.visibility = View.GONE

                    } else {
                        rvSearch.visibility = View.VISIBLE
                        Log.i("QUUUEERRRYYY", newText)
                        searchDatabase(newText)
                    }
                }

                return true
            }
        })
    }

    private fun setupTabLayout() {
        pagerAdapter = PagerViewAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tablayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Stocks"
                }
                1 -> {
                    tab.text = "Favourite"
                    tab.view.children.forEach {
                        if (it is TextView) {
                            it.post {
                                it.setTextAppearance(this, R.style.TabLayoutStyleSmall)
                            }
                        }
                    }
                }
            }
        }.attach()

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.view?.children?.forEach {
                    if (it is TextView) {
                        it.post {
                            it.setTextAppearance(applicationContext, R.style.TabLayoutStyle)

                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.view?.children?.forEach {
                    if (it is TextView) {
                        it.post {
                            it.setTextAppearance(applicationContext, R.style.TabLayoutStyleSmall)
                        }
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    /*Если уже в избранном, то удалить, если нет то вставить*/
    private fun onClick(favourite: Boolean, stock: FavouriteEntity) {
        if (favourite) {
            stock.isFavourite = false
            mainViewModel.delete(stock)
        } else {
            stock.isFavourite = true
            mainViewModel.insert(stock)
        }
    }
}