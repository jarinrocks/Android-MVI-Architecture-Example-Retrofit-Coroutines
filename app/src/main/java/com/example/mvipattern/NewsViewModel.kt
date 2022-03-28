package com.example.mvipattern

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvipattern.repository.RepositoryImpl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    val newsChannel = Channel<NewsIntents>()

    private val _newsState = MutableStateFlow<NewsStates>(NewsStates.Loading)
    val newsStates : StateFlow<NewsStates> get() = _newsState

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            newsChannel.consumeAsFlow().collect{
                when(it){
                    NewsIntents.TopHeadlinesIntent -> getTopHeadlines()
                }
            }
        }
    }

    private suspend fun getTopHeadlines() {
       RepositoryImpl().getTopHeadlines().collect {
           _newsState.value = it
       }
    }
}