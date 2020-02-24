package com.android.moviecatalogue.Activity.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.moviecatalogue.API.API;
import com.android.moviecatalogue.API.Service;
import com.android.moviecatalogue.Adapter.MovieAdapter;
import com.android.moviecatalogue.Model.Movie;
import com.android.moviecatalogue.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private Context context;

    private RecyclerView rv;
    private MovieAdapter adapter;
    private String API_KEY = API.getApiKey();
    private List<Movie> movie = new ArrayList<>();

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);


    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading .... ");
        progressDialog.show();

        rv = view.findViewById(R.id.recycler);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        Service service = API.getClient().create(Service.class);
        Call<Movie> call = service.getDataMovie(API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, retrofit2.Response<Movie> response) {
                progressDialog.dismiss();
                assert response.body() != null;
                movie = response.body().getResultsMovie();
                adapter = new MovieAdapter(movie,getContext());
                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Failed to load data",Toast.LENGTH_SHORT).show();

            }
        });

    }

}
