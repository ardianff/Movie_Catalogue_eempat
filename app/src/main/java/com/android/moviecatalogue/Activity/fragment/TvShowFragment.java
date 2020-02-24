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
import com.android.moviecatalogue.Adapter.TvShowAdapter;
import com.android.moviecatalogue.Model.Tv;
import com.android.moviecatalogue.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private RecyclerView rv;
    private TvShowAdapter adapter;
    private String API_KEY = API.getApiKey();
    private List<Tv> tv = new ArrayList<>();

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        Call<Tv> call = service.getDataTv(API_KEY);
        call.enqueue(new Callback<Tv>() {
            @Override
            public void onResponse(Call<Tv> call, Response<Tv> response) {
                progressDialog.dismiss();
                tv = response.body().getResultTv();
                adapter = new TvShowAdapter(tv,getContext());
                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Tv> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), getResources().getString(R.string.failed),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
