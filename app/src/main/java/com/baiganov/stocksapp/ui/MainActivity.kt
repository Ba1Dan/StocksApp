package com.baiganov.stocksapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.adapters.ClickListener
import com.baiganov.stocksapp.adapters.StocksAdapter
import com.baiganov.stocksapp.adapters.VerticalSpaceItemDecoration
import com.baiganov.stocksapp.api.ApiFactory
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.StocksDatabase
import com.baiganov.stocksapp.repositories.FavouriteRepositoryImpl
import com.baiganov.stocksapp.repositories.StocksRepositoryImpl
import com.baiganov.stocksapp.viewmodel.MainFactory
import com.baiganov.stocksapp.viewmodel.MainViewModel
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var rvAdapter: StocksAdapter
    private lateinit var searchView: SearchView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var pbStocks: ProgressBar
    private lateinit var rvMain: RecyclerView
    private lateinit var tvStocks: TextView
    private lateinit var tvFavourite: TextView
    private lateinit var tvNotificationFavourite: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setupRecyclerView()
        val database = StocksDatabase.create(applicationContext)
        mainViewModel = ViewModelProvider(
            this,
            MainFactory(
                StocksRepositoryImpl(
                    ApiFactory.apiService,
                    database.stockDao,
                    applicationContext
                ), FavouriteRepositoryImpl(database.favouriteStockDao)
            )
        ).get(MainViewModel::class.java)
        /*if (savedInstanceState == null) {
            setupViewModel()
        } else {
            pbStocks.visibility = View.GONE
            mainViewModel.getData()
            mainViewModel.data.observe(this, {
                rvAdapter.setData(it)
            })
        }*/
        setupViewModel()
        setupSearchView()
        tvStocks.setOnClickListener {
            updateStocks()
        }
        tvFavourite.setOnClickListener {
            updateFavourite()
        }

    }

    override fun onResume() {
        super.onResume()
        searchView.clearFocus()
        Log.d("DEBUG", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("DEBUG", "onPause")
    }

    override fun onStart() {
        super.onStart()
        Log.d("DEBUG", "onStart сюда")
        if (tvStocks.currentTextColor == ContextCompat.getColor(applicationContext, R.color.black)) {
            mainViewModel.getData()
        } else {
            mainViewModel.getDataFavourite()
        }
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
        mainViewModel.getDataFavourite()
    }

    private fun initView() {
        pbStocks = findViewById(R.id.pb_main)
        rvMain = findViewById(R.id.rv_main)
        searchView = findViewById(R.id.sv_main)
        tvStocks = findViewById(R.id.tv_stocks)
        tvFavourite = findViewById(R.id.tv_favourite)
        tvNotificationFavourite = findViewById(R.id.tv_notification_favourite)
    }

    private fun setupRecyclerView() {
        rvAdapter = StocksAdapter(object : ClickListener {
            override fun onClickStar(stock: FavouriteEntity) {
                if (stock.isFavourite) {
                    mainViewModel.insert(stock)
                } else {
                    mainViewModel.delete(stock, tvStocks.currentTextColor == ContextCompat.getColor(applicationContext, R.color.black))
                }
            }

            override fun onClickItem(stock: Stock) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("stock", stock)
                startActivity(intent)
            }

            override fun onClickTitleStock(name: String) {
            }

            override fun onClickShow() {
            }
        })
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.addItemDecoration(VerticalSpaceItemDecoration(8))
        val isLoading = false
        val isLastPage = false
        var isScrolling = false
        rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
                val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
                val isNotAtBeginning = firstVisibleItemPosition >= 0
                val isTotalMoreThanVisible = totalItemCount >= 25
                val shouldPaginate =
                    isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                            isTotalMoreThanVisible && isScrolling
                if (shouldPaginate) {
                    mainViewModel.load()
                    isScrolling = false
                } else {
                    //rvBreakingNews.setPadding(0, 0, 0, 0)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }
        })
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
        searchView.setOnQueryTextFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (p1) {
                    val intent = Intent(this@MainActivity, SearchActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}