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

import com.android.moviecatalogue.Activity.view.DetailTvShow;
import com.android.moviecatalogue.Model.Tv;
import com.android.moviecatalogue.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import static com.android.moviecatalogue.BuildConfig.IMAGE_URL;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private List<Tv> tvList;
    private Context context;

    public TvShowAdapter(List<Tv> tvList, Context context) {
        this.tvList = tvList;
        this.context = context;
    }

    @NonNull
    @Override
    public TvShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return tvList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,release,overview;
        ImageView image;
        LinearLayout parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent_movie);
            image = itemView.findViewById(R.id.imgPoster);
            title = itemView.findViewById(R.id.tvName);
            release = itemView.findViewById(R.id.tvReleaseDate);
            overview = itemView.findViewById(R.id.tvOverview);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.ViewHolder viewHolder, int position) {
        final Tv tv = tvList.get(position);
        viewHolder.title.setText(tv.getTitle());
        viewHolder.overview.setText(tv.getOverview());
        viewHolder.release.setText(tv.getTvShowDate());

        Glide.with(context)
                .load(IMAGE_URL+ tv.getPoster())
                .into(viewHolder.image);

        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailTvShow.class);
                intent.putExtra(DetailTvShow.TV,tv);
                context.startActivity(intent);
            }
        });

    }

}
