package com.android.moviecatalogue.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.moviecatalogue.Activity.view.DetailMovieFav;
import com.android.moviecatalogue.Model.Favorite;
import com.android.moviecatalogue.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static com.android.moviecatalogue.BuildConfig.IMAGE_URL;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<Favorite> arrayList = new ArrayList<>();


    public MovieFavoriteAdapter(Activity context) {
        this.context = context;
    }
    public ArrayList<Favorite> getListFavorite() {
        return arrayList;
    }
    public void setListFavorite(ArrayList<Favorite> listFavorite) {

        if (listFavorite.size() > 0) {
            arrayList.clear();
        }
        arrayList.addAll(listFavorite);

        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView overview;
        ImageView image;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.parent_movie);
            image = itemView.findViewById(R.id.imgPoster);
            title = itemView.findViewById(R.id.tvName);
            overview = itemView.findViewById(R.id.tvOverview);
        }
    }

    @NonNull
    @Override
    public MovieFavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_favorite,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFavoriteAdapter.ViewHolder viewHolder, int position) {
        final Favorite movie = arrayList.get(position);
        viewHolder.title.setText(movie.getTitle());
        viewHolder.overview.setText(movie.getPoster());

        Glide.with(context)
                .load(IMAGE_URL+movie.getOverview())
                .into(viewHolder.image);

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMovieFav.class);
                intent.putExtra(DetailMovieFav.MOVIE_FAV,movie);
                context.startActivity(intent);
            }
        });
    }


}
