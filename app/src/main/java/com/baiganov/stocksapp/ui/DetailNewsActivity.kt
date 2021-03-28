package com.baiganov.stocksapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.api.ApiFactory
import com.baiganov.stocksapp.data.model.News
import com.baiganov.stocksapp.db.StocksDatabase
import com.baiganov.stocksapp.repositories.DetailRepositoryImpl
import com.baiganov.stocksapp.viewmodel.DetailNewsFactory
import com.baiganov.stocksapp.viewmodel.DetailNewsViewModel
import kotlinx.serialization.ExperimentalSerializationApi

class DetailNewsActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var tvHeadline: TextView
    private lateinit var tvData: TextView
    private lateinit var tvSource: TextView
    private lateinit var tvDescription: TextView
    private lateinit var detailNewsViewModel: DetailNewsViewModel

    @ExperimentalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)
        initView()
        val database = StocksDatabase.create(applicationContext)
        detailNewsViewModel = ViewModelProvider(
            this,
            DetailNewsFactory(
                DetailRepositoryImpl(
                    stocksDao = database.stockDao,
                    apiServiceFin = ApiFactory.apiServiceFin,
                    newsDao = database.newsDao
                )
            )
        ).get(DetailNewsViewModel::class.java)
        val argument = intent.extras
        if (argument != null) {
            val id = argument.getInt("id")
            detailNewsViewModel.getNews(id)
        }
        detailNewsViewModel.data.observe(this, {
            bind(it)
        })
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun initView() {
        btnBack = findViewById(R.id.btn_detail_news_back)
        tvData = findViewById(R.id.tv_detail_news_data)
        tvHeadline = findViewById(R.id.tv_detail_news_headline)
        tvSource = findViewById(R.id.tv_detail_news_source)
        tvDescription = findViewById(R.id.tv_detail_news_description)
    }

    private fun bind(news: News) {
        tvData.text = news.datetime
        tvSource.text = news.source
        tvDescription.text = news.summary
        tvHeadline.text = news.headline
    }
}