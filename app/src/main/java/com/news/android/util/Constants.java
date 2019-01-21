package com.news.android.util;

import android.view.View;

/**
 * Created by Rahul D on 1/10/19.
 */
public class Constants {
    public static String API_KEY = "717a2a66ddf44c8fbf45283fce30ce5e";


    public interface OnItemClickListener {
        void OnItemClick(View view, int position, Object value);
    }
}
