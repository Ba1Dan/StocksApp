package com.baiganov.stocksapp.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.entity.StockEntity
import com.baiganov.stocksapp.data.entity.convert
import com.baiganov.stocksapp.data.model.Stock
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_stock.view.*

class StocksAdapter(
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<StocksAdapter.StocksViewHolder>() {

    private var stocksList = listOf<Stock>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksViewHolder {
        return StocksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StocksViewHolder, position: Int) {
        holder.bind(stocksList[position], position)
        holder.itemView.iv_favourite.setOnClickListener {
            if (stocksList[position].isFavourite) {
                holder.itemView.iv_favourite.setImageResource(R.drawable.ic_default_star)
                clickListener.onStarClick(true, convert(stocksList[position]))
                stocksList[position].isFavourite = false
            } else {
                holder.itemView.iv_favourite.setImageResource(R.drawable.ic_like)
                clickListener.onStarClick(false, convert(stocksList[position]))
                stocksList[position].isFavourite = true
            }
        }
    }

    override fun getItemCount(): Int {
        return stocksList.size
    }

    fun bindStocks(newStocks: List<Stock>) {
        stocksList = newStocks
        notifyDataSetChanged()
    }

    fun interface ItemClickListener {
        fun onStarClick(favourite: Boolean, stock: FavouriteEntity)
    }

    inner class StocksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cvViewHolder: CardView = itemView.findViewById(R.id.cv_view_holder)
        private val tvTitleTicker: TextView = itemView.findViewById(R.id.tv_title_ticket)
        private val tvTitleStock: TextView = itemView.findViewById(R.id.tv_title_stock)
        private val tvDeltaPercent: TextView = itemView.findViewById(R.id.tv_day_delta)
        private val ivLogoStock: ImageView = itemView.findViewById(R.id.iv_logo_stock)
        private val tvCurrentPrice: TextView = itemView.findViewById(R.id.tv_current_price)
        private val ivFavourite: ImageView = itemView.findViewById(R.id.iv_favourite)

        @SuppressLint("SetTextI18n")
        fun bind(data: Stock, position: Int) {
            if (position % 2 == 0) {
                cvViewHolder.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.light_item
                    )
                )
            } else {
                cvViewHolder.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
            }
            if (data.priceDelta < 0) {
                tvDeltaPercent.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
            }

            tvDeltaPercent.text =
                String.format("%.2f", data.priceDelta) + sign + "(" + String.format(
                    "%.2f",
                    data.percentDelta
                ) + percent + ")"
            tvTitleTicker.text = data.ticker
            tvTitleStock.text = data.name
            tvCurrentPrice.text = sign.plus(data.currentPrice)
            Glide.with(itemView.context)
                .load(data.logo)
                .into(ivLogoStock)
            if (data.isFavourite) {
                Glide.with(itemView.context)
                    .load(R.drawable.ic_like)
                    .into(ivFavourite)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.ic_default_star)
                    .into(ivFavourite)
            }
        }
    }

    companion object {
        private const val sign = "$"
        private const val percent = "%"
    }
}

