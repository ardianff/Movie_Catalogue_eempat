package com.android.moviecatalogue.Activity.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.android.moviecatalogue.Activity.fragment.FavoriteFragment;
import com.android.moviecatalogue.Activity.fragment.MovieFragment;
import com.android.moviecatalogue.Activity.fragment.TvShowFragment;
import com.android.moviecatalogue.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String STATE_TITLE = "state_string";
    private String title = "Movie";
    Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.recyclerFav:
                    title = getResources().getString(R.string.movie);
                    setActionBarTitle(title);
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;

                case R.id.tvshow:
                    title = getResources().getString(R.string.TvShow);
                    setActionBarTitle(title);
                    fragment = new TvShowFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.favorite:
                    title = getResources().getString(R.string.favorite_s);
                    setActionBarTitle(title);
                    fragment = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            setActionBarTitle(title);
            navView.setSelectedItemId(R.id.recyclerFav);
        } else {
            title = savedInstanceState.getString(STATE_TITLE);
            setActionBarTitle(title);
        }
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);


        if (savedInstanceState == null){
            navView.setSelectedItemId(R.id.recyclerFav);
        }
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_language, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_TITLE, String.valueOf(title));
    }

}
