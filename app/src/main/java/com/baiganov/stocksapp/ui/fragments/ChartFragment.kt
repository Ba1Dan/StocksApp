package com.baiganov.stocksapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.data.model.Stock
import kotlin.math.abs


class ChartFragment : Fragment() {

    private lateinit var btnDay: Button
    private lateinit var btnWeek: Button
    private lateinit var btnMonth: Button
    private lateinit var btnHalfYear: Button
    private lateinit var btnYear: Button
    private lateinit var btnAll: Button
    private lateinit var listButtons: MutableList<Button>
    private lateinit var tvCurrentPrice: TextView
    private lateinit var tvDeltaDay: TextView
    private lateinit var btnBuy: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view)
        setupClickListener()
        if (arguments != null) {
            val stock = arguments?.getSerializable("stock") as Stock
            bind(stock)
        }
    }

    private fun initView(view: View) {
        tvCurrentPrice = view.findViewById(R.id.tv_current_price_detail)
        btnBuy = view.findViewById(R.id.btn_buy)
        tvDeltaDay = view.findViewById(R.id.tv_delta_day_detail)
        btnDay = view.findViewById(R.id.btn_day)
        btnWeek = view.findViewById(R.id.btn_week)
        btnMonth = view.findViewById(R.id.btn_month)
        btnHalfYear = view.findViewById(R.id.btn_half_year)
        btnYear = view.findViewById(R.id.btn_year)
        btnAll = view.findViewById(R.id.btn_all)
        listButtons = mutableListOf( btnDay, btnWeek, btnMonth, btnHalfYear, btnYear, btnAll)
    }

    @SuppressLint("SetTextI18n")
    private fun bind(stock: Stock) {
        tvCurrentPrice.text = SIGN + stock.currentPrice.toString()
        if (stock.priceDelta >= 0) {
            tvDeltaDay.text = PLUS + SIGN +
                    String.format("%.2f", abs(stock.priceDelta)) +
                    "(" + String.format("%.2f", stock.percentDelta) +
                    PERCENT + ")"
        } else {
            tvDeltaDay.text = MINUS + SIGN + String.format("%.2f", abs(stock.priceDelta)) +
                    "(" + String.format("%.2f", stock.percentDelta) + PERCENT + ")"
            tvDeltaDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }
        btnBuy.text = TEXT_BUTTON + stock.currentPrice.toString()
    }

    private fun setupClickListener() {
        btnDay.setOnClickListener {
            changeStyleButton(btnDay.text.toString())
        }

        btnWeek.setOnClickListener {
            changeStyleButton(btnWeek.text.toString())
        }

        btnMonth.setOnClickListener {
            changeStyleButton(btnMonth.text.toString())
        }

        btnHalfYear.setOnClickListener {
            changeStyleButton(btnHalfYear.text.toString())
        }

        btnYear.setOnClickListener {
            changeStyleButton(btnYear.text.toString())
        }

        btnAll.setOnClickListener {
            changeStyleButton(btnAll.text.toString())
        }
    }

    private fun changeStyleButton(buttonName: String) {
        listButtons.forEach {
            if (it.text == buttonName) {
                it.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_btn_choose_active)
                it.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                it.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_btn_choose)
                it.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    companion object {
        private const val SIGN = "$"
        private const val PERCENT = "%"
        private const val PLUS = "+"
        private const val MINUS = "-"
        private const val TEXT_BUTTON = "Buy for"

        fun getNewInstance(stock: Stock): ChartFragment {
            val bundle = Bundle()
            bundle.putSerializable("stock", stock)
            val fragment = ChartFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}