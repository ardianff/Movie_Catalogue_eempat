package com.android.moviecatalogue.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.moviecatalogue.Activity.view.DetailMovie;
import com.android.moviecatalogue.Model.Movie;
import com.android.moviecatalogue.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import static com.android.moviecatalogue.BuildConfig.IMAGE_URL;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context =  context;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,release,overview;
        ImageView image;
        LinearLayout layout;
        public ViewHolder(@NonNull View view) {
            super(view);
            layout = view.findViewById(R.id.parent_movie);
            image = view.findViewById(R.id.imgPoster);
            title = view.findViewById(R.id.tvName);
            release = view.findViewById(R.id.tvReleaseDate);
            overview = view.findViewById(R.id.tvOverview);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder viewHolder, int position) {
        final Movie movie = movieList.get(position);
        viewHolder.title.setText(movie.getTitle());
        viewHolder.overview.setText(movie.getOverview());
        viewHolder.release.setText(movie.getMovieDate());

        Glide.with(context)
                .load(IMAGE_URL+ movie.getPoster())
                .into(viewHolder.image);
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMovie.class);
                intent.putExtra(DetailMovie.MOVIE,movie);
                context.startActivity(intent);
            }
        });

    }

}
