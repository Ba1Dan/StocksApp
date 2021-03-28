package com.baiganov.stocksapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.api.ApiFactory
import com.baiganov.stocksapp.data.json.StockAlpha
import com.baiganov.stocksapp.viewmodel.SummaryViewModel
import kotlinx.serialization.ExperimentalSerializationApi


class SummaryFragment : Fragment() {

    private lateinit var viewModel: SummaryViewModel
    private lateinit var tvDescription: TextView
    private lateinit var tvCountry: TextView
    private lateinit var tvSector: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    @ExperimentalSerializationApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view)
        setupViewModel()
    }

    @ExperimentalSerializationApi
    private fun setupViewModel() {
        viewModel = SummaryViewModel(ApiFactory.apiServiceAlpha)
        if (arguments != null) {
            val ticker = arguments?.getSerializable("ticker") as String
            viewModel.load(ticker)
        }
        viewModel.data.observe(viewLifecycleOwner, {
            bind(it)
        })
    }

    private fun initView(view: View) {
        tvDescription = view.findViewById(R.id.tv_stock_description)
        tvCountry = view.findViewById(R.id.tv_stock_country)
        tvSector = view.findViewById(R.id.tv_stock_sector)
    }

    private fun bind(stockAlpha: StockAlpha) {
        tvDescription.text = stockAlpha.description
        tvCountry.text = stockAlpha.country
        tvSector.text = stockAlpha.sector
    }

    companion object {

        fun getNewInstance(ticker: String): SummaryFragment {
            val bundle = Bundle()
            bundle.putString("ticker", ticker)
            val fragment = SummaryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}