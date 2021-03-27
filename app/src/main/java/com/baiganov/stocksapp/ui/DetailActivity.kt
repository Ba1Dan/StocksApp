package com.baiganov.stocksapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.adapters.PagerViewAdapter
import com.baiganov.stocksapp.api.ApiFactory
import com.baiganov.stocksapp.data.entity.convertToFavourite
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.StocksDatabase
import com.baiganov.stocksapp.repositories.DetailRepositoryImpl
import com.baiganov.stocksapp.repositories.FavouriteRepositoryImpl
import com.baiganov.stocksapp.viewmodel.DetailFactory
import com.baiganov.stocksapp.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tablayout: TabLayout
    private lateinit var btnBack: ImageView
    private lateinit var ivFavourite: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvTicker: TextView
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initView()
        val database = StocksDatabase.create(applicationContext)
        detailViewModel = ViewModelProvider(this, DetailFactory(FavouriteRepositoryImpl(database.favouriteStockDao), DetailRepositoryImpl(database.stockDao, ApiFactory.apiServiceFin))).get(DetailViewModel::class.java)
        val argument = intent.extras
        if (argument != null) {
            val stock = argument.getSerializable("stock") as Stock
            detailViewModel.getStock(stock.ticker)
        }
        detailViewModel.data.observe(this, { stock ->
            if (stock != null) {
                bind(stock)
                setupTabLayout(stock)
                setupClickListener(stock)
            }
        })
    }

    private fun initView() {
        tvName = findViewById(R.id.tv_name_stock)
        tvTicker = findViewById(R.id.tv_title_stock)
        viewPager = findViewById(R.id.view_pager)
        tablayout = findViewById(R.id.tab_layout)
        btnBack = findViewById(R.id.btn_back)
        ivFavourite = findViewById(R.id.iv_favourite_detail)
    }

    private fun bind(stock: Stock) {
        tvName.text = stock.name
        tvTicker.text = stock.ticker
        if (stock.isFavourite) {
            Glide.with(applicationContext)
                .load(R.drawable.ic_star_like)
                .into(ivFavourite)
        }
    }

    private fun setupTabLayout(stock: Stock) {
        val adapter = PagerViewAdapter(supportFragmentManager, lifecycle, stock)
        viewPager.adapter = adapter
        TabLayoutMediator(tablayout, viewPager) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "Chart"

                }

                1 -> {
                    tab.text = "Summary"
                }

                2 -> {
                    tab.text = "News"
                }

                3 -> {
                    tab.text = "Forecasts"
                }

                4 -> {
                    tab.text = "Ideas"
                }

                5 -> {
                    tab.text = "Events"
                }
            }
        }.attach()
    }

    private fun setupClickListener(stock: Stock) {
        btnBack.setOnClickListener {
            finish()
        }
        ivFavourite.setOnClickListener {
            if (stock.isFavourite) {
                stock.isFavourite = false
                Glide.with(applicationContext)
                    .load(R.drawable.ic_star_detail)
                    .into(ivFavourite)
                detailViewModel.delete(convertToFavourite(stock))
            } else {
                stock.isFavourite = true
                Glide.with(applicationContext)
                    .load(R.drawable.ic_star_like)
                    .into(ivFavourite)
                detailViewModel.insert(convertToFavourite(stock))
            }

        }
    }
}