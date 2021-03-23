package com.baiganov.stocksapp.ui

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.data.model.Stock


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
    private lateinit var stock: Stock

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view)
        setupClickListener()
        if (arguments != null) {
            stock = arguments?.getSerializable("stock") as Stock
            tvCurrentPrice.text = stock.currentPrice.toString()
            tvDeltaDay.text = stock.priceDelta.toString()
        }
    }

    private fun initView(view: View) {
        tvCurrentPrice = view.findViewById(R.id.tv_current_price_detail)
        tvDeltaDay = view.findViewById(R.id.tv_delta_day_detail)
        btnDay = view.findViewById(R.id.btn_day)
        btnWeek = view.findViewById(R.id.btn_week)
        btnMonth = view.findViewById(R.id.btn_month)
        btnHalfYear = view.findViewById(R.id.btn_half_year)
        btnYear = view.findViewById(R.id.btn_year)
        btnAll = view.findViewById(R.id.btn_all)
        listButtons = mutableListOf( btnDay, btnWeek, btnMonth, btnHalfYear, btnYear, btnAll)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupClickListener() {
        btnDay.setOnClickListener {
            listButtons.forEach {
                if (it.text == "D") {
                    it.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_btn_choose_active)
                    it.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                } else {
                    it.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_btn_choose)
                    it.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                }
            }
        }

        btnWeek.setOnClickListener {
            listButtons.forEach {
                if (it.text == "W") {
                    it.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_btn_choose_active)
                    it.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                } else {
                    it.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_btn_choose)
                    it.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                }
            }
        }

        btnMonth.setOnClickListener {
            listButtons.forEach {
                if (it.text == "M") {
                    it.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_btn_choose_active)
                    it.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                } else {
                    it.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_btn_choose)
                    it.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                }
            }
        }

        btnHalfYear.setOnClickListener {
            listButtons.forEach {
                if (it.text == "6M") {
                    it.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_btn_choose_active)
                    it.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                } else {
                    it.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_btn_choose)
                    it.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                }
            }
        }

        btnYear.setOnClickListener {
            listButtons.forEach {
                if (it.text == "1Y") {
                    it.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_btn_choose_active)
                    it.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                } else {
                    it.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_btn_choose)
                    it.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                }
            }
        }

        btnAll.setOnClickListener {
            listButtons.forEach {
                if (it.text == "All") {
                    it.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_btn_choose_active)
                    it.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                } else {
                    it.background = ContextCompat.getDrawable(requireContext(), R.drawable.custom_btn_choose)
                    it.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                }
            }
        }



    }

    companion object {
        fun getNewInstance(stock: Stock): ChartFragment {
            val bundle = Bundle()
            bundle.putSerializable("stock", stock)
            val fragment = ChartFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}