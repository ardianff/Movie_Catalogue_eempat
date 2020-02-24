package com.android.moviecatalogue.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.moviecatalogue.Activity.fragment.MovieFavoriteFragment;
import com.android.moviecatalogue.R;
import com.android.moviecatalogue.Activity.fragment.TvShowFavoriteFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] INTS = new int[]{R.string.movie, R.string.TvShow};
    private final Context context;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        @NonNull
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MovieFavoriteFragment();
                break;
            case 1:
                fragment = new TvShowFavoriteFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(INTS[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
