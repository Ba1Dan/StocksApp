package com.baiganov.stocksapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baiganov.stocksapp.R
import com.baiganov.stocksapp.data.model.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var data = listOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newData: List<News>) {
        data = newData
        notifyDataSetChanged()
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val headline: TextView = itemView.findViewById(R.id.news_headline)
        private val date: TextView = itemView.findViewById(R.id.news_date)
        private val source: TextView = itemView.findViewById(R.id.news_source)

        fun bind(news: News) {
            Log.d("DEBUG in adapter", news.headline)
            headline.text = news.headline
            date.text = news.datetime.toString()
            source.text = news.source
        }
    }
}