package com.liuz.design.ui.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuz.design.R;
import com.liuz.design.base.BaseViewHolder;
import com.liuz.design.bean.MoviesBean;
import com.liuz.lotus.loader.LoaderFactory;

import butterknife.BindView;

/**
 * date: 2018/6/14 18:09
 * author liuzhao
 */
public class HotHolder extends BaseViewHolder<MoviesBean> {
    @BindView(R.id.tv_movie_title) TextView tvMovieTitle;
    @BindView(R.id.iv_movies_banner) ImageView ivBanner;

    public HotHolder(View view) {
        super(view);
    }

    @Override
    public void onBind(int position, MoviesBean bean) {
        tvMovieTitle.setText(bean.getTitleCn());
        LoaderFactory.getLoader().loadNet(ivBanner, bean.getImg(), null);
    }
}
