package com.example.mvipattern.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.mvipattern.Articles
import com.example.mvipattern.databinding.NewsItemBinding

class NewsViewHolder(private val newsItemBinding: NewsItemBinding) : RecyclerView.ViewHolder(newsItemBinding.root) {

    fun bind(article : Articles) {
        newsItemBinding.title.text = article.title
        newsItemBinding.description.text = article.description
    }
}