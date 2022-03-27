package com.example.mvipattern.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvipattern.NewsIntents
import com.example.mvipattern.NewsStates
import com.example.mvipattern.NewsViewModel
import com.example.mvipattern.databinding.ActivityNewsBinding
import kotlinx.coroutines.launch

class NewsActivity : AppCompatActivity() {

    private val newsViewModel : NewsViewModel by viewModels()

    private val newsAdapter = NewsAdapter()

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        lifecycleScope.launch {
            newsViewModel.newsChannel.send(NewsIntents.TopHeadlinesIntent)
        }

        lifecycleScope.launch {
            lifecycleScope.launchWhenStarted {
                newsViewModel.newsStates.collect {
                    when(it){
                        is NewsStates.Success -> {
                            binding.progressBar.visibility = View.GONE
                            newsAdapter.addArticles(it.news.articles)
                        }
                        is NewsStates.Error -> {
                            binding.progressBar.visibility = View.GONE
                        }
                        is NewsStates.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
}