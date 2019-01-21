package com.news.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Articles implements Parcelable {
    int viewType;

    private String content;

    private String publishedAt;

    private String author;

    private String urlToImage;

    private String title;

    private Sources source;

    private String description;

    private String url;

    public Articles() {

    }


    protected Articles(Parcel in) {
        viewType = in.readInt();
        content = in.readString();
        publishedAt = in.readString();
        author = in.readString();
        urlToImage = in.readString();
        title = in.readString();
        source = in.readParcelable(Sources.class.getClassLoader());
        description = in.readString();
        url = in.readString();
    }

    public static final Creator<Articles> CREATOR = new Creator<Articles>() {
        @Override
        public Articles createFromParcel(Parcel in) {
            return new Articles(in);
        }

        @Override
        public Articles[] newArray(int size) {
            return new Articles[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(viewType);
        dest.writeString(content);
        dest.writeString(publishedAt);
        dest.writeString(author);
        dest.writeString(urlToImage);
        dest.writeString(title);
        dest.writeParcelable(source, flags);
        dest.writeString(description);
        dest.writeString(url);
    }

    public int getViewType() {
        return viewType;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public Sources getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}