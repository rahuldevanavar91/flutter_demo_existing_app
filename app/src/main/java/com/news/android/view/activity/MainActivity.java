package com.news.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.news.android.R;
import com.news.android.model.Articles;
import com.news.android.prasenter.TopHeadlinesPresenter;
import com.news.android.util.Constants;
import com.news.android.view.adapter.NewsListAdapter;

import java.util.ArrayList;

import io.flutter.app.FlutterActivityDelegate;
import io.flutter.app.FlutterActivityEvents;
import io.flutter.facade.Flutter;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterNativeView;
import io.flutter.view.FlutterView;

/**
 * View Display the top headline list
 */
public class MainActivity extends AppCompatActivity implements TopHeadlinesPresenter.TopHeadlineListener, Constants.OnItemClickListener, FlutterActivityDelegate.ViewFactory {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TopHeadlinesPresenter mHeadlinesPresenter;
    private NewsListAdapter mNewsListAdapter;
    private int mPage;
    private static final String PLATFORM_CHANNEL = "platfrom.channel.message/info";


    private FlutterActivityDelegate delegate = new FlutterActivityDelegate(this, this);
    private FlutterActivityEvents eventDelegate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventDelegate = delegate;

        getWidget();
        FlutterView flutterView = Flutter.createView(
                MainActivity.this,
                getLifecycle(),
                "route1"
        );
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(flutterView, layout);
        layout.topMargin = 20;
        setPlatformChannel(flutterView);


       /* mHeadlinesPresenter = new TopHeadlinesPresenter(this);
        mProgressBar.setVisibility(View.VISIBLE);
        mHeadlinesPresenter.getHeadlines(++mPage);
 */
    }

    private void getWidget() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mProgressBar = findViewById(R.id.progress_bar);
    }

    private void setPlatformChannel(FlutterView view) {
        GeneratedPluginRegistrant.registerWith(view.getPluginRegistry());
        new MethodChannel(view, PLATFORM_CHANNEL).setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
                        switch (call.method) {
                            case "getMessage":
                                result.success("Hello form android");
                                break;
                            default:
                                result.notImplemented(); // INFO: not implemented
                                break;
                        }
                    }
                }
        );
    }

    @Override
    public void TopHeadlineSuccessListener(ArrayList<Articles> articles) {
        if (mNewsListAdapter != null) {
            mNewsListAdapter.updateList(articles);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mNewsListAdapter = new NewsListAdapter(this, articles, this);
            mRecyclerView.setAdapter(mNewsListAdapter);
        }
    }

    public void loadMoreNews() {
        mHeadlinesPresenter.getHeadlines(++mPage);
    }

    @Override
    public void TopHeadlinesErrorListener(String msg) {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemClick(View view, int position, Object value) {
        Intent intent = new Intent(this, NewsDetailActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, ViewCompat.getTransitionName(view));
        intent.putExtra(getString(R.string.data), (Articles) value);
        startActivity(intent, options.toBundle());

    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.eventDelegate.onConfigurationChanged(newConfig);
    }

    @Override
    public FlutterView createFlutterView(Context context) {
        return null;
    }

    @Override
    public FlutterNativeView createFlutterNativeView() {
        return null;
    }

    @Override
    public boolean retainFlutterNativeView() {
        return false;
    }
}
