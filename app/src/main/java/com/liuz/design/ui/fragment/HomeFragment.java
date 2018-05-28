package com.liuz.design.ui.fragment;

import android.os.Bundle;
import android.widget.Button;

import com.google.gson.Gson;
import com.liuz.design.R;
import com.liuz.design.api.MTimeApiService;
import com.liuz.design.base.BaseFragment;
import com.liuz.design.bean.AreasBean;
import com.liuz.design.utils.AssetsUtils;
import com.liuz.design.view.Banners;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.callback.ACallback;
import com.liuz.lotus.net.core.ApiTransformer;
import com.liuz.lotus.net.subscriber.ApiCallbackSubscriber;
import com.vise.log.ViseLog;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;


/**
 * date: 2018/5/28 14:46
 * author liuzhao
 */
public class HomeFragment extends BaseFragment {

    String areaJson = "file:///android_asset/area.json";
    @BindView(R.id.btn_area) Button btnArea;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(Banners banner) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    protected void initEventAndData() {
        AreasBean areasBean = new Gson().fromJson(AssetsUtils.getJson(mContext, areaJson), AreasBean.class);
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
            public boolean verify(String string,SSLSession ssls) {
                return true;
            }
        });
    }

    @OnClick({R.id.btn_area})
    void onViewClick() {
        ViseHttp.RETROFIT()
                .addHeader("Host", "api-m.mtime.cn")
                .create(MTimeApiService.class)
                .getAreas()
                .compose(ApiTransformer.<AreasBean>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<AreasBean>() {
                    @Override
                    public void onSuccess(AreasBean authorModel) {
                        ViseLog.i("request onSuccess!");
                        if (authorModel == null) {
                            return;
                        }

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                }));

    }
}