package com.baiganov.stocksapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.entity.convertToFavourite
import com.baiganov.stocksapp.data.model.Stock
import com.baiganov.stocksapp.data.model.StockResponse
import com.baiganov.stocksapp.data.model.Suggestion
import com.bumptech.glide.Glide

class StocksAdapter(
    private var clickListener: ClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mData = listOf<StockResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            0-> StocksViewHolder(inflater.inflate(R.layout.item_stock, parent, false), clickListener)
            else -> SuggestionViewHolder(inflater.inflate(R.layout.item_suggestion, parent, false), clickListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StocksViewHolder -> holder.bind(mData[position] as Stock, position)
            is SuggestionViewHolder -> holder.bind(mData[position] as Suggestion)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setData(newStocks: List<StockResponse>) {
        mData = newStocks
        notifyDataSetChanged()
    }

    fun attach(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    class StocksViewHolder(itemView: View, private val clickListener: ClickListener) : RecyclerView.ViewHolder(itemView) {

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

            ivFavourite.setOnClickListener {
                if (data.isFavourite) {
                    ivFavourite.setImageResource(R.drawable.ic_default_star)
                    data.isFavourite = false
                    clickListener.onClickStar(convertToFavourite(data))
                } else {
                    ivFavourite.setImageResource(R.drawable.ic_like)
                    data.isFavourite = true
                    clickListener.onClickStar(convertToFavourite(data))
                }
            }

            itemView.setOnClickListener {
                clickListener.onClickItem(data.name)
            }
        }
    }

    class SuggestionViewHolder(itemView: View, private val clickListener: ClickListener) : RecyclerView.ViewHolder(itemView) {

        private val rvTitleStock: RecyclerView = itemView.findViewById(R.id.rv_title_stock)
        private val tvNameSuggestion: TextView = itemView.findViewById(R.id.tv_name_suggestion)

        fun bind(data: Suggestion) {
            tvNameSuggestion.text = data.name
            val adapter = StockTitleAdapter(clickListener)
            adapter.setData(data.stocks)
            rvTitleStock.adapter = adapter
            rvTitleStock.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.HORIZONTAL)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(mData[position]) {
            is Stock -> 0
            is Suggestion -> 1
        }
    }

    companion object {
        private const val sign = "$"
        private const val percent = "%"
    }
}

interface ClickListener {
    fun onClickStar(stock: FavouriteEntity)
    fun onClickItem(name: String)
    fun onClickTitleStock(name: String)
}

