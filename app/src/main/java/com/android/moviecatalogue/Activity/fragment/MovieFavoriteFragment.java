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

import com.android.moviecatalogue.Adapter.MovieFavoriteAdapter;
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
public class MovieFavoriteFragment extends Fragment {

    private static final String EXTRA_MOVIE = "EXTRA_STATE";
    private MovieFavoriteAdapter favAdapter;
    private FavoriteHelper helper;

    public MovieFavoriteFragment() {
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

        final RecyclerView viewById = view.findViewById(R.id.recyclerFav);


        LoadMovieCallback callback = new LoadMovieCallback() {
            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(Cursor cursor) {
                ArrayList<Favorite> movieData = MappingHelper.getMovieData(cursor);
                if (movieData.size() > 0) {
                    favAdapter.setListFavorite(movieData);
                    viewById.setVisibility(View.VISIBLE);
                } else {
                    favAdapter.setListFavorite(new ArrayList<Favorite>());
                    viewById.setVisibility(View.GONE);
                }
            }
        };

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver observer = new DataObserver(handler,getContext(),callback);
        Objects.requireNonNull(getActivity()).getContentResolver().registerContentObserver(CONTENT_URI,true,observer);

        helper = FavoriteHelper.getInstance(getContext());
        helper.OpenDatabase();

        viewById.setLayoutManager(new LinearLayoutManager(getContext()));
        viewById.setHasFixedSize(true);
        favAdapter = new MovieFavoriteAdapter(getActivity());
        viewById.setAdapter(favAdapter);


        if (savedInstanceState == null) {
            new LoadMovieAsync(getContext(), callback).execute();
        } else {
            ArrayList<Favorite> list = savedInstanceState.getParcelableArrayList(EXTRA_MOVIE);
            if (list != null) {
                favAdapter.setListFavorite(list);
            }
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_MOVIE, favAdapter.getListFavorite());
    }

    private static class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadMovieAsync(Context context, LoadMovieCallback callback) {
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
        final LoadMovieCallback callback;

        public DataObserver(Handler handler, Context context, LoadMovieCallback callback) {
            super(handler);
            this.context = context;
            this.callback = callback;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadMovieAsync(context,callback).execute();
        }
    }

    interface LoadMovieCallback {
        void preExecute();
        void postExecute(Cursor cursor);
    }
}
