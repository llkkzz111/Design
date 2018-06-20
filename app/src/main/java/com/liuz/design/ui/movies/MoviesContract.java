package com.liuz.design.ui.movies;

import com.liuz.design.bean.HotMoviesBean;
import com.liuz.design.mvp.IPresenter;
import com.liuz.design.mvp.IView;

/**
 * date: 2018/6/20 12:10
 * author liuzhao
 */
public interface MoviesContract {

    interface View extends IView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showView(HotMoviesBean data);
    }

    interface Presenter extends IPresenter<View> {

        void getAlDeta(int locationid);

        void takeView(MoviesContract.View view);

        void dropView();
    }
}
