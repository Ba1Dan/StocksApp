package com.baiganov.stocksapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.adapters.StocksAdapter
import com.baiganov.stocksapp.adapters.VerticalSpaceItemDecoration
import com.baiganov.stocksapp.api.ApiFactory
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.db.StocksDatabase
import com.baiganov.stocksapp.repositories.StocksRepositoryImpl
import com.baiganov.stocksapp.viewmodel.StocksListFactory
import com.baiganov.stocksapp.viewmodel.StocksListViewModel

class FragmentStocksList : Fragment() {

    private lateinit var viewModel: StocksListViewModel
    private lateinit var adapter: StocksAdapter
    private lateinit var rvStocks: RecyclerView
    private lateinit var data: List<Int>
    private lateinit var pbStocks: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stocks_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        rvStocks = view.findViewById(R.id.rv_stocks)
        pbStocks = view.findViewById(R.id.pb_stocks)
        setupRecyclerView()
        val database = StocksDatabase.create(requireContext())
        viewModel = ViewModelProvider(this, StocksListFactory(StocksRepositoryImpl(ApiFactory.apiService, database.stockDao), database.favouriteStockDao)).get(StocksListViewModel::class.java)

        if(savedInstanceState == null) {
            viewModel.loadData()
            viewModel.data.observe(this, {
                update(it)
            })
            viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
                pbStocks.visibility = if (loading) View.VISIBLE else View.GONE
            }
            viewModel.isNetworking.observe(viewLifecycleOwner) {
                if (!it) {
                    Toast.makeText(requireContext(), "No networking", Toast.LENGTH_SHORT).show()
                }
            }
        } else {

            Toast.makeText(requireContext(), "ИЗ БД вытаскивать данные", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("ok", "Load")
    }

    private fun setupRecyclerView() {
        adapter = StocksAdapter(clickListener)
        rvStocks.layoutManager = LinearLayoutManager(requireContext())
        rvStocks.adapter = adapter
        rvStocks.addItemDecoration(VerticalSpaceItemDecoration(8))
    }

    private val clickListener = StocksAdapter.ItemClickListener { favourite, stock -> onClick(favourite = favourite, stock = stock) }

    private fun update(stocks: List<Stock>) {
        adapter.setData(stocks)
    }

    /*Если уже в избранном, то удалить, если нет то вставить*/
    private fun onClick(favourite: Boolean, stock: FavouriteEntity) {
        if (favourite) {
            stock.isFavourite = false
            viewModel.delete(stock)
        } else {
            stock.isFavourite = true
            viewModel.insert(stock)
        }
    }
}