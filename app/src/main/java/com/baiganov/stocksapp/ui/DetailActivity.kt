package com.baiganov.stocksapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.adapters.PagerViewAdapter
import com.baiganov.stocksapp.data.model.Stock
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tablayout: TabLayout
    private lateinit var btnBack: ImageView
    private lateinit var stock: Stock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val tvName: TextView = findViewById(R.id.tv_name_stock)
        val tvTicker: TextView = findViewById(R.id.tv_ticker_stock)
        val argument = intent.extras
        if (argument != null) {
            stock = argument.getSerializable("stock") as Stock
            tvName.text = stock.name
            tvTicker.text = stock.ticker

        }
        viewPager = findViewById(R.id.view_pager)
        tablayout = findViewById(R.id.tab_layout)
        btnBack = findViewById(R.id.btn_back)


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

        btnBack.setOnClickListener {
            finish()
        }
    }
}