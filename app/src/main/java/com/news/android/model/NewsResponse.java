package com.news.android.model;

import java.util.List;

/**
 * Created by Rahul D on 1/10/19.
 */
public class NewsResponse {


    private List<Articles> articles;

    private int totalResults;

    private String status;

    private String message;


    public String getMessage() {
        return message;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
