package com.baiganov.stocksapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baiganov.stocksapp.R

class StockTitleAdapter(
    private val clickListener: ClickListener
) : RecyclerView.Adapter<StockTitleAdapter.StockTitleViewHolder>() {

    private var stocks: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockTitleViewHolder {
        return StockTitleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_stock_suggestion, parent, false),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: StockTitleViewHolder, position: Int) {
        holder.bind(stocks[position])
    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    fun setData(newData: List<String>) {
        stocks = newData
        notifyDataSetChanged()
    }

    class StockTitleViewHolder(itemView: View, private val clickListener: ClickListener) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title_stock_suggestion)

        fun bind(name: String) {
            tvTitle.text = name
            tvTitle.setOnClickListener {
                clickListener.onClickTitleStock(name)
            }
        }
    }
}