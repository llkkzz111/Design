package com.liuz.design.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.liuz.design.R;
import com.liuz.design.api.MTimeApiService;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.bean.HotMoviesBean;
import com.liuz.design.ui.adapter.HotMovAdapter;
import com.liuz.design.utils.PreferencesUtils;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.callback.ACallback;
import com.liuz.lotus.net.core.ApiTransformer;
import com.liuz.lotus.net.subscriber.ApiCallbackSubscriber;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MTimesMoviesActivity extends TranslucentBarBaseActivity {

    @BindView(R.id.rv_hot_movies) RecyclerView rvHotMovies;
    private int locationid = PreferencesUtils.getLocationID();

    @Override
    protected int getLayoutResId() {
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
                .getAreas(params)
                .compose(ApiTransformer.<HotMoviesBean>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<HotMoviesBean>() {
                    @Override
                    public void onSuccess(HotMoviesBean data) {
                        rvHotMovies.setAdapter(new HotMovAdapter(mContext,data.getMovies()));
                        Toast.makeText(mContext, data.getCount() + "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                }));
    }


}
