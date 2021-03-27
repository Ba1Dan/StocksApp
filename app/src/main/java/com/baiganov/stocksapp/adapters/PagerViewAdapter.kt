package com.baiganov.stocksapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.ui.fragments.*

class PagerViewAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val ticker: String) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ChartFragment.getNewInstance(ticker)
            1 -> SummaryFragment.getNewInstance(ticker)
            2 -> NewsFragment.getNewInstance(ticker)
            3 -> ForecastsFragment()
            4 -> IdeasFragment()
            else -> EventsFragment()
        }
    }
}