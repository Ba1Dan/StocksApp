package com.baiganov.stocksapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.adapters.StocksAdapter
import com.baiganov.stocksapp.adapters.VerticalSpaceItemDecoration
import com.baiganov.stocksapp.api.ApiFactory
import com.baiganov.stocksapp.data.entity.StockEntity
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.FavouriteStocksDatabase
import com.baiganov.stocksapp.repositories.StocksRepositoryImpl
import com.baiganov.stocksapp.viewmodel.StocksListFactory
import com.baiganov.stocksapp.viewmodel.StocksListViewModel

class FragmentStocksList : Fragment() {

    private lateinit var viewModel: StocksListViewModel
    private lateinit var adapter: StocksAdapter
    private lateinit var rvStocks: RecyclerView
    private lateinit var data: List<Int>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stocks_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvStocks = view.findViewById(R.id.rv_stocks)
        setupRecyclerView()
        val database = FavouriteStocksDatabase.create(requireContext())
        viewModel = ViewModelProvider(this, StocksListFactory(StocksRepositoryImpl(ApiFactory.apiService), database.favouriteStockDao)).get(StocksListViewModel::class.java)
        viewModel.data.observe(this, {
            update(it)
        })
    }

    private fun setupRecyclerView() {
        adapter = StocksAdapter(clickListener)
        rvStocks.layoutManager = LinearLayoutManager(requireContext())
        rvStocks.adapter = adapter
        rvStocks.addItemDecoration(VerticalSpaceItemDecoration(8))
    }

    private val clickListener = StocksAdapter.ItemClickListener { favourite, stock -> onClick(favourite = favourite, stock = stock) }

    private fun update(stocks: List<Stock>) {
        adapter.bindMovies(stocks)
    }

    /*Если уже в избранном, то удалить, если нет то вставить*/
    private fun onClick(favourite: Boolean, stock: StockEntity) {
        if (favourite) {
            stock.isFavourite = false
            viewModel.delete(stock)
        } else {
            stock.isFavourite = true
            viewModel.insert(stock)
        }
    }
}