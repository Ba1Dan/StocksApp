package com.baiganov.stocksapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.adapters.ClickListenerNews
import com.baiganov.stocksapp.adapters.NewsAdapter
import com.baiganov.stocksapp.api.ApiFactory
import com.baiganov.stocksapp.data.model.News
import com.baiganov.stocksapp.db.StocksDatabase
import com.baiganov.stocksapp.repositories.DetailRepositoryImpl
import com.baiganov.stocksapp.viewmodel.NewsFactory
import com.baiganov.stocksapp.viewmodel.NewsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi


class NewsFragment : Fragment() {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var rvNews: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    @ExperimentalSerializationApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view)
        val adapter =  NewsAdapter(object : ClickListenerNews {
            override fun onClickItem(news: News) {

            }
        })
        rvNews.adapter = adapter
        rvNews.layoutManager = LinearLayoutManager(requireContext())
        val database = StocksDatabase.create(requireContext())
        newsViewModel = ViewModelProvider(this, NewsFactory(DetailRepositoryImpl(database.stockDao, ApiFactory.apiServiceFin, database.newsDao))).get(NewsViewModel::class.java)
        if (arguments != null) {
            val ticker = arguments?.getString("ticker") as String
            newsViewModel.load(ticker)
        }

        lifecycleScope.launch {
            newsViewModel.news.collectLatest {
                adapter.submitData(it)
            }
        }
    }


    private fun initView(view: View) {
        rvNews = view.findViewById(R.id.rv_news)
    }

    companion object {

        fun getNewInstance(ticker: String): NewsFragment {
            val bundle = Bundle()
            bundle.putString("ticker", ticker)
            val fragment = NewsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}