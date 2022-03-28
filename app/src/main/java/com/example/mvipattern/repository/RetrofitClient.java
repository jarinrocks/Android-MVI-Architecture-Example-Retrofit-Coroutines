package com.example.mvipattern.repository;

import com.example.mvipattern.BuildConfig;
import com.example.mvipattern.Constants;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    private static final OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();
    private static RetrofitClient instance = null;
    private static NewsService service = null;
    private static final HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor();

    private RetrofitClient() {
        httpClient.interceptors().add(chain -> {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder()
                    .method(originalRequest.method(), originalRequest.body());
            return chain.proceed(builder.build());
        });

        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            // add logging as last interceptor
            httpClient.addInterceptor(logging);
        }
        Retrofit retrofit = new Retrofit.Builder().client(httpClient.build()).
                baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(NewsService.class);
    }


    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public NewsService getApiService() {
        return service;
    }


}