package com.android.moviecatalogue.API;

import com.android.moviecatalogue.Model.Movie;
import com.android.moviecatalogue.Model.Tv;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("movie")
    Call<Movie> getDataMovie(@Query("api_key") String api_key);

    @GET("tv")
    Call<Tv> getDataTv(@Query("api_key") String api_key);
}
