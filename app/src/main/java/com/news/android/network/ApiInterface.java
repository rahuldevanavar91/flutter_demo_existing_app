package com.news.android.network;


import com.news.android.model.NewsResponse;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("v2/top-headlines")
    retrofit2.Call<NewsResponse> getTopHeadlines(@QueryMap Map<String, String> param);
}
