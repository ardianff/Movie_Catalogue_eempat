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

import com.android.moviecatalogue.Activity.view.DetailTvShowFav;
import com.android.moviecatalogue.Model.Favorite;
import com.android.moviecatalogue.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static com.android.moviecatalogue.BuildConfig.IMAGE_URL;

public class TvShowFavoriteAdapter extends RecyclerView.Adapter<TvShowFavoriteAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<Favorite> arrayList = new ArrayList<>();

    public TvShowFavoriteAdapter(Activity context) {
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

    @NonNull
    @Override
    public TvShowFavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_favorite,null,false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView overview;
        ImageView image;
        LinearLayout parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent_movie);
            image = itemView.findViewById(R.id.imgPoster);
            title = itemView.findViewById(R.id.tvName);
            overview = itemView.findViewById(R.id.tvOverview);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowFavoriteAdapter.ViewHolder viewHolder, int position) {
        final Favorite tv = arrayList.get(position);
        viewHolder.title.setText(tv.getTitle());
        viewHolder.overview.setText(tv.getPoster());

        Glide.with(context)
                .load(IMAGE_URL+tv.getOverview())
                .into(viewHolder.image);

        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailTvShowFav.class);
                intent.putExtra(DetailTvShowFav.TV_FAV,tv);
                context.startActivity(intent);
            }
        });
    }


}
