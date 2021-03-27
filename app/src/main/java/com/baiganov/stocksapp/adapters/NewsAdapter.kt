package com.baiganov.stocksapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.data.model.News
import com.baiganov.stocksapp.data.model.Stock
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(private val clickListener: ClickListenerNews) : PagingDataAdapter<News, NewsAdapter.NewsViewHolder>(DIF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false), clickListener)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class NewsViewHolder(itemView: View, private val clickListener: ClickListenerNews) : RecyclerView.ViewHolder(itemView) {

        private val headline: TextView = itemView.findViewById(R.id.news_headline)
        private val date: TextView = itemView.findViewById(R.id.news_date)
        private val source: TextView = itemView.findViewById(R.id.news_source)

        fun bind(news: News) {
            headline.text = news.headline
            date.text = fromUnixToData(news.datetime)
            source.text = news.source

            itemView.setOnClickListener {
                clickListener.onClickItem(news)
            }
        }

        private fun fromUnixToData(datetime: Long): String {
            val sdf = SimpleDateFormat(FORMAT)
            val netDate = Date(datetime * 1000)
            return sdf.format(netDate)
        }
    }

    companion object {

        private const val FORMAT = "yyyy-MMMM-dd hh:mm a"

        private val DIF_CALLBACK = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
               return oldItem == newItem
            }
        }
    }
}

interface ClickListenerNews {
    fun onClickItem(news: News)
}