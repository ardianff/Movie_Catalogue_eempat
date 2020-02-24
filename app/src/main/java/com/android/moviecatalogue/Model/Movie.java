package com.android.moviecatalogue.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("overview")
    private String overview;
    @SerializedName("title")
    private String title;
    @SerializedName("release_date")
    private String movieDate;
    @SerializedName("results")
    private List<Movie> resultsMovie;

    protected Movie(Parcel in) {
        id = in.readInt();
        poster = in.readString();
        overview = in.readString();
        title = in.readString();
        resultsMovie = in.createTypedArrayList(Movie.CREATOR);
        movieDate = in.readString();
    }

    public Movie(int id, String poster, String overview, String title, List<Movie> resultsMovie, String moviedate) {
        this.id = id;
        this.poster = poster;
        this.overview = overview;
        this.title = title;
        this.resultsMovie = resultsMovie;
        this.movieDate = moviedate;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
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

    public List<Movie> getResultsMovie() {
        return resultsMovie;
    }
    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
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
        parcel.writeTypedList(resultsMovie);
        parcel.writeString(movieDate);
    }


}
