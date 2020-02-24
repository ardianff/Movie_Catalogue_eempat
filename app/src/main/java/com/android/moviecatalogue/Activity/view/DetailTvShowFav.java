package com.android.moviecatalogue.Activity.view;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.moviecatalogue.Database.Helper.FavoriteHelper;
import com.android.moviecatalogue.Model.Favorite;
import com.android.moviecatalogue.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Objects;

import static com.android.moviecatalogue.BuildConfig.IMAGE_URL;
import static com.android.moviecatalogue.Database.DatabaseContract.FavoriteColumns.CATEGORY;
import static com.android.moviecatalogue.Database.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.android.moviecatalogue.Database.DatabaseContract.FavoriteColumns.ID;
import static com.android.moviecatalogue.Database.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.android.moviecatalogue.Database.DatabaseContract.FavoriteColumns.POSTER;
import static com.android.moviecatalogue.Database.DatabaseContract.FavoriteColumns.TITLE;

public class DetailTvShowFav extends AppCompatActivity {
    public final static String TV_FAV = "tv_fav";
    ImageView imgPoster;
    TextView tvName, tvOverview;
    FavoriteHelper helper;
    ImageView favorite;
    String title, overview, image;
    int id,id_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);
        imgPoster = findViewById(R.id.imgPoster);
        tvName = findViewById(R.id.tvName);
        tvOverview = findViewById(R.id.tvOverview);
        favorite = findViewById(R.id.button_favorite);
        isFavorite();

        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.TvShow));
        Favorite tv = getIntent().getParcelableExtra(TV_FAV);
        title = Objects.requireNonNull(tv).getTitle();
        overview = tv.getOverview();
        image = tv.getPoster();
        id_tv = tv.getmId();
        isFavorite();
        tvName.setText(title);
        tvOverview.setText(image);
        getSupportActionBar().setSubtitle(tv.getTitle());

        Glide.with(this)
                .load(IMAGE_URL+overview)
                .into(imgPoster);

        helper = FavoriteHelper.getInstance(getApplicationContext());
        helper.OpenDatabase();

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite()){
                    Uri uri = Uri.parse(CONTENT_URI + "/"+id);
                    getContentResolver().delete(uri, null, null);
                    favorite.setImageResource(R.drawable.love_favorite);
                    Toast.makeText(DetailTvShowFav.this, title + " " +getResources().getString(R.string.remove),Toast.LENGTH_SHORT).show();
                }else {
                    ContentValues values = new ContentValues();
                    values.put(ID,id_tv);
                    values.put(TITLE, title);
                    values.put(POSTER, image);
                    values.put(OVERVIEW, overview);
                    values.put(CATEGORY,"tv");

                    if (getContentResolver().insert(CONTENT_URI, values) != null) {
                        Toast.makeText(DetailTvShowFav.this, title + " " +getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
                        favorite.setImageResource(R.drawable.ic_is_favorite);
                    }
                }
            }
        });
    }

    public boolean isFavorite(){
        Uri uri = Uri.parse(CONTENT_URI +  "");
        boolean favorite = false;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int i;
        if (Objects.requireNonNull(cursor).moveToFirst()) {
            do {
                i = cursor.getInt(1);
                if (i == id_tv) {
                    id = cursor.getInt(0);
                    this.favorite.setImageResource(R.drawable.ic_is_favorite);
                    favorite = true;
                }
            } while (cursor.moveToNext());
        }
        return favorite;
    }
}
