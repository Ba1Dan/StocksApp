package com.baiganov.stocksapp.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.data.model.StockTitle

class StockTitleAdapter : RecyclerView.Adapter<StockTitleAdapter.StockTitleViewHolder>() {

    private var stocks: List<StockTitle> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockTitleViewHolder {
        return StockTitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_stock_suggestion, parent, false))
    }

    override fun onBindViewHolder(holder: StockTitleViewHolder, position: Int) {
        holder.bind(stocks[position])
    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    fun setData(newData: List<StockTitle>) {
        stocks = newData
        notifyDataSetChanged()
    }

    class StockTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title_stock_suggestion)

        fun bind(data: StockTitle) {
            tvTitle.text = data.title
        }
    }
}