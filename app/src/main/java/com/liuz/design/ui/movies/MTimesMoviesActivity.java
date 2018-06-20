package com.liuz.design.ui.movies;

import com.liuz.design.R;
import com.liuz.design.base.BaseDaggerActivity;
import com.liuz.design.utils.ActivityUtils;

import javax.inject.Inject;


public class MTimesMoviesActivity extends BaseDaggerActivity {
    @Inject
    MoviesPresenter mMoviesPresenter;
    @Inject
    MoviesFragment fragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_movies_layout;
    }

    @Override
    protected void initEventAndData() {
        MoviesFragment statisticsFragment = (MoviesFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (statisticsFragment == null) {
            statisticsFragment = fragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    statisticsFragment, R.id.contentFrame);
        }
    }


}
