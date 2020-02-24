package com.android.moviecatalogue.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tv implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("overview")
    private String overview;
    @SerializedName("name")
    private String title;
    @SerializedName("first_air_date")
    private String tvShowDate;
    @SerializedName("results")
    private List<Tv> resultTv;

    protected Tv(Parcel in) {
        id = in.readInt();
        poster = in.readString();
        overview = in.readString();
        title = in.readString();
        resultTv = in.createTypedArrayList(Tv.CREATOR);
    }

    public Tv(int id, String poster, String overview, String title, List<Tv> resultTv) {
        this.id = id;
        this.poster = poster;
        this.overview = overview;
        this.title = title;
        this.resultTv = resultTv;
    }

    public static final Creator<Tv> CREATOR = new Creator<Tv>() {
        @Override
        public Tv createFromParcel(Parcel in) {
            return new Tv(in);
        }

        @Override
        public Tv[] newArray(int size) {
            return new Tv[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        return title;
    }

    public List<Tv> getResultTv() {
        return resultTv;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(poster);
        parcel.writeString(overview);
        parcel.writeString(title);
        parcel.writeTypedList(resultTv);
    }

    public String getTvShowDate() {
        return tvShowDate;
    }
}
