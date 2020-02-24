package com.android.moviecatalogue.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {
    private int id;
    private int mId;
    private String category;
    private String title;
    private String poster;
    private String overview;

    public Favorite(Parcel in) {
        id = in.readInt();
        mId = in.readInt();
        category = in.readString();
        title = in.readString();
        poster = in.readString();
        overview = in.readString();
    }

    public Favorite(int id, int mId, String category, String title, String poster, String overview) {
        this.id = id;
        this.mId = mId;
        this.category = category;
        this.title = title;
        this.poster = poster;
        this.overview = overview;
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getmId() {
        return mId;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(mId);
        parcel.writeString(category);
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(overview);
    }
}
