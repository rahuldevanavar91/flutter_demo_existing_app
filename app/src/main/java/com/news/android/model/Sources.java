package com.news.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rahul D on 1/10/19.
 */
public class Sources implements Parcelable {
    String id;
    String name;

    protected Sources(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Sources> CREATOR = new Creator<Sources>() {
        @Override
        public Sources createFromParcel(Parcel in) {
            return new Sources(in);
        }

        @Override
        public Sources[] newArray(int size) {
            return new Sources[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
