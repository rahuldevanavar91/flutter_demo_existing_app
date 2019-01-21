package com.news.android.view.activity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.android.R;
import com.news.android.model.Articles;
import com.squareup.picasso.Picasso;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Rahul D on 1/11/19.
 * **
 */
public class NewsDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        getWidget();
    }

    private void getWidget() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView = findViewById(R.id.image);
        TextView headline = findViewById(R.id.headline);
        TextView desc = findViewById(R.id.description);
        desc.setMovementMethod(LinkMovementMethod.getInstance());

        final Articles articles = getIntent().getParcelableExtra(getString(R.string.data));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(articles.getUrlToImage());
        }

        String source = "News";
        if (articles.getSource() != null) {
            source = articles.getSource().getName();
            getSupportActionBar().setTitle(source);
        }


        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(source);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));


        Picasso.with(getApplicationContext())
                .load(articles.getUrlToImage())
                .placeholder(R.color.gray_color)
                .into(imageView);

        headline.setText(articles.getTitle());
        SpannableStringBuilder descSpannable = new SpannableStringBuilder(articles.getDescription()
                + " Read more");

        descSpannable.setSpan(new ClickableSpan() {
                                  @Override
                                  public void onClick(View widget) {
                                      CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                      CustomTabsIntent customTabsIntent = builder.build();
                                      customTabsIntent.intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                      customTabsIntent.launchUrl(getBaseContext(), Uri.parse(articles.getUrl()));
                                  }
                              }, articles.getDescription().length()
                , descSpannable.length()
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        desc.setText(descSpannable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }
}
