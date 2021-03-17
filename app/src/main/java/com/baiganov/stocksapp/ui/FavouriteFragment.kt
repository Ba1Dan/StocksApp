package com.baiganov.stocksapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.adapters.StocksAdapter
import com.baiganov.stocksapp.adapters.VerticalSpaceItemDecoration
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.StocksDatabase
import com.baiganov.stocksapp.viewmodel.FavouriteStocksFactory
import com.baiganov.stocksapp.viewmodel.FavouriteStocksViewModel

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

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvStocks = view.findViewById(R.id.rv_stocks)
        val database = StocksDatabase.create(requireContext())
        viewModel = ViewModelProvider(this, FavouriteStocksFactory(database.favouriteStockDao)).get(
            FavouriteStocksViewModel::class.java)
        viewModel.data.observe(this, {
            setData(it)
        })
        setupRecyclerView()
    }

    private fun setData(stocks: List<FavouriteEntity>) {
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
                    isFavourite = it.isFavourite,
                    priceDelta = it.priceDelta
                    )
            )
        }
        adapter.bindStocks(data)
    }

    private fun setupRecyclerView() {
        adapter = StocksAdapter(clickListener)
        rvStocks.layoutManager = LinearLayoutManager(requireContext())
        rvStocks.adapter = adapter
        rvStocks.addItemDecoration(VerticalSpaceItemDecoration(8))
    }

    private fun refreshData() {
        viewModel.getData()
        viewModel.data.observe(this, {
            setData(it)
        })
    }

    private val clickListener = StocksAdapter.ItemClickListener { favourite, stock -> onClick(favourite = favourite, stock = stock) }

    private fun onClick(favourite: Boolean, stock: FavouriteEntity) {
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