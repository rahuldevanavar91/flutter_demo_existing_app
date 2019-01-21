package com.news.android.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.android.R;
import com.news.android.model.Articles;
import com.news.android.util.Constants;
import com.news.android.view.activity.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rahul D on 1/10/19.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    public final static int VIEW_TYPE_LOAD_MORE = 1;
    private final static int VIEW_TYPE_LIST_ITEM = 0;
    private List<Articles> mArticles;
    private Context mContext;
    private int mLastMoreRequestPos;
    private Constants.OnItemClickListener mClickListener;

    public NewsListAdapter(Context context, List<Articles> articles, Constants.OnItemClickListener clickListener) {
        mArticles = articles;
        mContext = context;
        mClickListener = clickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_LOAD_MORE:
                return new ViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.loading_more_layout, viewGroup, false), viewType);
            case VIEW_TYPE_LIST_ITEM:
                return new ViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.news_list_item, viewGroup, false), viewType);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        switch (getItemViewType(viewHolder.getAdapterPosition())) {
            case VIEW_TYPE_LIST_ITEM:
                Articles item = mArticles.get(viewHolder.getAdapterPosition());
                viewHolder.headline.setText(item.getTitle());
                Picasso.with(mContext)
                        .load(item.getUrlToImage())
                        .placeholder(R.color.gray_color)
                        .into(viewHolder.imageView);
                viewHolder.imageView.setTag(viewHolder.getAdapterPosition());
                ViewCompat.setTransitionName(viewHolder.imageView, item.getUrlToImage());
                break;
            case VIEW_TYPE_LOAD_MORE:
                if (mLastMoreRequestPos != viewHolder.getAdapterPosition()) {
                    ((MainActivity) mContext).loadMoreNews();
                    mLastMoreRequestPos = viewHolder.getAdapterPosition();
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mArticles.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void updateList(List<Articles> articles) {
        mArticles = articles;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView headline;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            switch (viewType) {
                case VIEW_TYPE_LIST_ITEM:
                    headline = itemView.findViewById(R.id.headline);
                    imageView = itemView.findViewById(R.id.news_image);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mClickListener.OnItemClick(v, (int) v.getTag(), mArticles.get((int) v.getTag()));
                        }
                    });
                    break;
            }
        }
    }
}
