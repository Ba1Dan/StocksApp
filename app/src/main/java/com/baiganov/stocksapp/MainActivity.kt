package com.baiganov.stocksapp

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.baiganov.stocksapp.adapters.StocksAdapter
import com.baiganov.stocksapp.api.ApiFactory
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.entity.convert
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.StocksDatabase
import com.baiganov.stocksapp.repositories.StocksRepositoryImpl
import com.baiganov.stocksapp.ui.PagerViewAdapter
import com.baiganov.stocksapp.ui.SearchActivity
import com.baiganov.stocksapp.viewmodel.MainViewFactory
import com.baiganov.stocksapp.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var rvAdapter: StocksAdapter
    private lateinit var searchView: SearchView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var pbStocks: ProgressBar
    private lateinit var rvMain: RecyclerView
    private lateinit var tvStocks: TextView
    private lateinit var tvFavourite: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setupRecyclerView()
        val database = StocksDatabase.create(applicationContext)
        mainViewModel = ViewModelProvider(this, MainViewFactory(StocksRepositoryImpl(ApiFactory.apiService, database.stockDao), database.favouriteStockDao)).get(MainViewModel::class.java)
        if (savedInstanceState == null) {
            setupViewModel()
        } else {
            pbStocks.visibility = View.GONE
            mainViewModel.getData()
            mainViewModel.data.observe(this, {
                rvAdapter.setData(it)
            })
        }
        setupSearchView()
        tvStocks.setOnClickListener {
            updateStocks()

        }

        tvFavourite.setOnClickListener {
            updateFavourite()

        }
    }

    private val clickListener = StocksAdapter.ItemClickListener { favourite, stock -> onClick(favourite = favourite, stock = stock) }

    override fun onResume() {
        super.onResume()
        searchView.clearFocus()
    }

    private fun updateStocks() {
        tvFavourite.textSize = 24F
        tvStocks.textSize = 32F
        tvStocks.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
        tvFavourite.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
        mainViewModel.getData()
        mainViewModel.data.observe(this, {
            rvAdapter.setData(it)
        })
    }

    private fun updateFavourite() {
        tvFavourite.textSize = 32F
        tvFavourite.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
        tvStocks.textSize = 24F
        tvStocks.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
        val favouriteStocks= mainViewModel.getDataFavourite()
        val stocks = favouriteStocks.map {
            convert(it)
        }
        rvAdapter.setData(stocks)
    }


    private fun initView() {
        pbStocks = findViewById(R.id.pb_main)
        rvMain = findViewById(R.id.rv_main)
        searchView = findViewById(R.id.sv_main)
        tvStocks = findViewById(R.id.tv_stocks)
        tvFavourite = findViewById(R.id.tv_favourite)
    }

    private fun setupRecyclerView() {
        rvAdapter = StocksAdapter(clickListener)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewModel() {
        mainViewModel.loadData()
        mainViewModel.data.observe(this, {
            rvAdapter.setData(it)
        })
        mainViewModel.isLoading.observe(this) { loading ->
            pbStocks.visibility = if (loading) View.VISIBLE else View.GONE
        }
        mainViewModel.isNetworking.observe(this) {
            if (!it) {
                Toast.makeText(applicationContext, "No networking", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setupSearchView() {
        searchView.setOnQueryTextFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (p1) {
                    val intent = Intent(this@MainActivity, SearchActivity::class.java)
                    startActivity(intent)
                }
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