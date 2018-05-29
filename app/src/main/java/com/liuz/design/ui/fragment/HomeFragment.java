package com.liuz.design.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liuz.db.AreaBean;
import com.liuz.db.AreasDatabase;
import com.liuz.design.R;
import com.liuz.design.base.BaseFragment;
import com.liuz.design.bean.AreasBean;
import com.liuz.design.ui.AreaActivity;
import com.liuz.design.utils.AssetsUtils;
import com.liuz.design.view.Banners;

import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


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

        startActivity(new Intent(mContext, AreaActivity.class));

        Observable.create(new ObservableOnSubscribe<List<AreaBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AreaBean>> emitter) throws Exception {
                List<AreaBean> list = AreasDatabase.getInstance(mContext).areaDao().getAreasDes("A");
                emitter.onNext(list);
            }


        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AreaBean>>() {
                    @Override
                    public void accept(List<AreaBean> areaBeans) throws Exception {
                        Toast.makeText(mContext,areaBeans.toString(),Toast.LENGTH_LONG).show();
                    }
                });

    }
}