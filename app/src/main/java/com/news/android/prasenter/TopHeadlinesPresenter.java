package com.news.android.prasenter;

import com.news.android.model.Articles;
import com.news.android.model.NewsResponse;
import com.news.android.network.ApiClient;
import com.news.android.network.ApiInterface;
import com.news.android.util.Constants;
import com.news.android.view.adapter.NewsListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul D on 1/10/19.
 * **
 * Presenter to fetches the data from server and stores in list
 */
public class TopHeadlinesPresenter {
    private final ApiInterface mApiService;
    private ArrayList<Articles> mArticles;
    private final TopHeadlineListener mListener;

    public TopHeadlinesPresenter(TopHeadlineListener topHeadlineListener) {
        mApiService = ApiClient.getClient().create(ApiInterface.class);
        mArticles = new ArrayList<>();
        mListener = topHeadlineListener;
    }

    public void getHeadlines(int page) {
        HashMap<String, String> param = new HashMap<>();
        param.put("pageSize", "10");
        param.put("apiKey", Constants.API_KEY);
        param.put("page", String.valueOf(page));
        param.put("country", "in");
        mApiService.getTopHeadlines(param).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                NewsResponse newsResponse = response.body();
                removeMoreLoading();
                if (newsResponse != null && newsResponse.getStatus() != null
                        && newsResponse.getStatus().equalsIgnoreCase("ok")) {
                    updateData(newsResponse);
                } else {
                    mListener.TopHeadlinesErrorListener(newsResponse != null ? newsResponse.getMessage() : null);
                }

            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                String error;
                if (t instanceof IOException) {
                    error = "Network failure";
                } else {
                    error = "Something went wrong";
                }
                mListener.TopHeadlinesErrorListener(error);
            }
        });
    }

    private void updateData(NewsResponse response) {
        if (response.getArticles() != null) {
            mArticles.addAll(response.getArticles());
            if (mArticles.size() != response.getTotalResults()) {
                Articles loadMore = new Articles();
                loadMore.setViewType(NewsListAdapter.VIEW_TYPE_LOAD_MORE);
                mArticles.add(loadMore);
            }
        }
        mListener.TopHeadlineSuccessListener(mArticles);
    }

    private void removeMoreLoading() {
        if (!mArticles.isEmpty() &&
                mArticles.get(mArticles.size() - 1).getViewType() ==
                        NewsListAdapter.VIEW_TYPE_LOAD_MORE) {
            mArticles.remove(mArticles.size() - 1);
        }

    }

    public interface TopHeadlineListener {
        void TopHeadlineSuccessListener(ArrayList<Articles> articles);

        void TopHeadlinesErrorListener(String msg);
    }


}
