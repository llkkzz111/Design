package com.liuz.design.ui.movies;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.liuz.common.subscriber.ApiCallbackSubscriber;
import com.liuz.design.R;
import com.liuz.design.base.BaseDaggerFragment;
import com.liuz.design.bean.HotMoviesBean;
import com.liuz.design.di.ActivityScoped;
import com.liuz.design.ui.adapter.HotMovAdapter;
import com.liuz.design.utils.PreferencesUtils;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.callback.ACallback;
import com.liuz.net.api.MTimeApiService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * date: 2018/6/20 18:20
 * author liuzhao
 */
@ActivityScoped
public class MoviesFragment extends BaseDaggerFragment {

    @BindView(R.id.rv_hot_movies) RecyclerView rvHotMovies;

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
        Map<String, Object> params = new HashMap<>();
        if (locationid > 0) {
            params.put("locationId", locationid);
        }

        ViseHttp.RETROFIT()
                .create(MTimeApiService.class)
                .getHotPlayMovies(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<HotMoviesBean>() {
                    @Override
                    public void onSuccess(HotMoviesBean data) {
                        showView(data);
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                }));
    }

    public void showView(HotMoviesBean data) {
        rvHotMovies.setAdapter(new HotMovAdapter(mContext, data.getMovies()));
        Toast.makeText(mContext, data.getCount() + "", Toast.LENGTH_SHORT).show();
    }

}
