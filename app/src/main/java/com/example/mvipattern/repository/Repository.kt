package com.example.mvipattern.repository

import com.example.mvipattern.NewsStates
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getEverything()
    suspend fun getTopHeadlines() : Flow<NewsStates>
}