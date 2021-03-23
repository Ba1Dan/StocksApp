package com.baiganov.stocksapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.ui.*

class PagerViewAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val stock: Stock) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ChartFragment.getNewInstance(stock)
            1 -> SummaryFragment()
            2 -> NewsFragment()
            3 -> ForecastsFragment()
            else -> IdeasFragment()
        }
    }
}