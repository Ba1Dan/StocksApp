package com.baiganov.stocksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.baiganov.stocksapp.viewmodel.FavouriteStocksFactory
import com.baiganov.stocksapp.viewmodel.FavouriteStocksViewModel
import com.baiganov.stocksapp.viewmodel.StocksListFactory
import com.baiganov.stocksapp.viewmodel.StocksListViewModel

class FavouriteFragment : Fragment() {

    private lateinit var adapter: StocksAdapter
    private lateinit var rvStocks: RecyclerView
    private lateinit var viewModel: FavouriteStocksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvStocks = view.findViewById(R.id.rv_stocks)
        setupRecyclerView()
        val database = FavouriteStocksDatabase.create(requireContext())
        viewModel = ViewModelProvider(this, FavouriteStocksFactory(database.favouriteStockDao)).get(
            FavouriteStocksViewModel::class.java)

        viewModel.data.observe(this, {
            setData(it)
        })
    }

    private fun setData(stocks: List<StockEntity>) {
        val data = mutableListOf<Stock>()
        stocks.map {
            data.add(
                Stock(
                    currency = it.currency,
                    name = it.name,
                    ticker = it.ticker,
                    logo = it.logo,
                    currentPrice = it.currentPrice,
                    percentDelta = it.percentDelta,
                    isFavourite = it.isFavourite)
            )
        }
        adapter.bindMovies(data)
    }

    private fun setupRecyclerView() {
        adapter = StocksAdapter(clickListener)
        rvStocks.layoutManager = LinearLayoutManager(requireContext())
        rvStocks.adapter = adapter
        rvStocks.addItemDecoration(VerticalSpaceItemDecoration(8))
    }

    private val clickListener = StocksAdapter.ItemClickListener { favourite, stock -> onClick(favourite = favourite, stock = stock) }

    private fun onClick(favourite: Boolean, stock: StockEntity) {
        if (favourite) {
            stock.isFavourite = false
            viewModel.delete(stock)
            Toast.makeText(requireContext(), "Удалить", Toast.LENGTH_SHORT).show()
        } else {
            stock.isFavourite = true
            viewModel.insert(stock)
            Toast.makeText(requireContext(), "Вставить", Toast.LENGTH_SHORT).show()
        }
    }
}