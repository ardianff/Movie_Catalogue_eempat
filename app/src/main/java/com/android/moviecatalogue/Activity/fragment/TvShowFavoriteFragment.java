package com.android.moviecatalogue.Activity.fragment;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.moviecatalogue.Adapter.TvShowFavoriteAdapter;
import com.android.moviecatalogue.Database.Helper.FavoriteHelper;
import com.android.moviecatalogue.Database.Helper.MappingHelper;
import com.android.moviecatalogue.Model.Favorite;
import com.android.moviecatalogue.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.android.moviecatalogue.Database.DatabaseContract.FavoriteColumns.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment {

    private static final String TV = "EXTRA_STATE";
    private TvShowFavoriteAdapter adapter;

    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerFav);


        MovieFavoriteFragment.LoadMovieCallback callback = new MovieFavoriteFragment.LoadMovieCallback() {
            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(Cursor cursor) {
                ArrayList<Favorite> tvData = MappingHelper.getTvData(cursor);
                if (tvData.size() > 0) {
                    adapter.setListFavorite(tvData);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    adapter.setListFavorite(new ArrayList<Favorite>());
                    recyclerView.setVisibility(View.GONE);
                }
            }
        };


        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        MovieFavoriteFragment.DataObserver observer = new MovieFavoriteFragment.DataObserver(handler,getContext(),callback);
        Objects.requireNonNull(getActivity()).getContentResolver().registerContentObserver(CONTENT_URI,true,observer);

        FavoriteHelper favoriteHelper = FavoriteHelper.getInstance(getContext());
        favoriteHelper.OpenDatabase();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new TvShowFavoriteAdapter(getActivity());
        recyclerView.setAdapter(adapter);


        if (savedInstanceState == null) {
            new LoadTvAsync(getContext(), callback).execute();
        } else {
            ArrayList<Favorite> list = savedInstanceState.getParcelableArrayList(TV);
            if (list != null) {
                adapter.setListFavorite(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TV, adapter.getListFavorite());
    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<MovieFavoriteFragment.LoadMovieCallback> weakCallback;

        private LoadTvAsync(Context context, MovieFavoriteFragment.LoadMovieCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        final MovieFavoriteFragment.LoadMovieCallback callback;
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new TvShowFavoriteFragment.LoadTvAsync(context,callback).execute();
        }

        public DataObserver(Handler handler, Context context, MovieFavoriteFragment.LoadMovieCallback callback) {
            super(handler);
            this.context = context;
            this.callback = callback;
        }


    }

}
