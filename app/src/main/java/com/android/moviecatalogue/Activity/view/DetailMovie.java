package com.android.moviecatalogue.Activity.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.moviecatalogue.Database.Helper.FavoriteHelper;
import com.android.moviecatalogue.Model.Movie;
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

public class DetailMovie extends AppCompatActivity {
    public final static String MOVIE = "extra_movie";
    ImageView imgPoster;
    TextView title, overview;
    ImageView favButton;
    FavoriteHelper favoriteHelper;
    String tvname, tvoverview, image;
    int id,id_movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        imgPoster = findViewById(R.id.imgPoster);
        title = findViewById(R.id.tvName);
        overview = findViewById(R.id.tvOverview);
        favButton = findViewById(R.id.button_favorite);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.movie));
        Movie movie = getIntent().getParcelableExtra(MOVIE);
        getSupportActionBar().setSubtitle(Objects.requireNonNull(movie).getTitle());
        tvname = movie.getTitle();
        tvoverview = movie.getOverview();
        image = movie.getPoster();
        id_movie = movie.getId();
        isFavorite();
        title.setText(tvname);
        overview.setText(tvoverview);

        Glide.with(this)
                .load(IMAGE_URL + movie.getPoster())
                .into(imgPoster);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.OpenDatabase();


        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite()){
                    Uri uri = Uri.parse(CONTENT_URI + "/"+id);
                    getContentResolver().delete(uri, null, null);
                    favButton.setImageResource(R.drawable.love_favorite);
                    Toast.makeText(DetailMovie.this, tvname + " " +getResources().getString(R.string.remove),Toast.LENGTH_SHORT).show();
                }else {
                    ContentValues values = new ContentValues();
                    values.put(ID,id_movie);
                    values.put(TITLE, tvname);
                    values.put(POSTER, image);
                    values.put(OVERVIEW, tvoverview);
                    values.put(CATEGORY,"movie");

                    if (getContentResolver().insert(CONTENT_URI, values) != null) {
                        Toast.makeText(DetailMovie.this, tvname + " " + getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
                        favButton.setImageResource(R.drawable.ic_is_favorite);
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
                if (i == id_movie) {
                    id = cursor.getInt(0);
                    favButton.setImageResource(R.drawable.ic_is_favorite);
                    favorite = true;
                }
            } while (cursor.moveToNext());
        }
        return favorite;
    }

}
