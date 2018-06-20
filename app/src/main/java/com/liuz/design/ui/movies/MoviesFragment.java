package com.liuz.design.ui.movies;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.liuz.design.R;
import com.liuz.design.base.BaseFragment;
import com.liuz.design.bean.HotMoviesBean;
import com.liuz.design.di.ActivityScoped;
import com.liuz.design.ui.adapter.HotMovAdapter;
import com.liuz.design.utils.PreferencesUtils;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * date: 2018/6/20 18:20
 * author liuzhao
 */
@ActivityScoped
public class MoviesFragment extends BaseFragment implements MoviesContract.View {

    @BindView(R.id.rv_hot_movies) RecyclerView rvHotMovies;
    @Inject
    MoviesContract.Presenter mPresenter;
    private int locationid = PreferencesUtils.getLocationID();

    @Inject
    public MoviesFragment() {
    }

    @Override
    protected int getlayoutId() {
        return R.layout.activity_mtimes_movies_layout;
    }

    @Override
    protected void initEventAndData() {
        mPresenter.getAlDeta(locationid);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }


    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showView(HotMoviesBean data) {
        rvHotMovies.setAdapter(new HotMovAdapter(mContext, data.getMovies()));
        Toast.makeText(mContext, data.getCount() + "", Toast.LENGTH_SHORT).show();
    }

}
