package com.example.mvipattern.repository

import com.example.mvipattern.Constants
import com.example.mvipattern.NewsData
import retrofit2.Response
import retrofit2.http.GET

interface NewsService {

    @GET("top-headlines?lang=en&token=${Constants.API_KEY}")
    suspend fun getTopHeadlines() : Response<NewsData>


}