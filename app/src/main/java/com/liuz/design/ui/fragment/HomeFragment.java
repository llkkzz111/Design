package com.liuz.design.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import com.liuz.db.AreaBean;
import com.liuz.db.AreasDatabase;
import com.liuz.design.R;
import com.liuz.design.base.BaseFragment;
import com.liuz.design.ui.AreaActivity;
import com.liuz.design.view.Banners;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.support.constraint.Constraints.TAG;


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

    List<List<AreaBean>> citysList = new ArrayList<>();

    @Override
    protected void initEventAndData() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Observable thread is : " + Thread.currentThread().getName());
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + integer);
            }
        };
        observable.subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "After observeOn(mainThread), current thread is: " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "After observeOn(io), current thread is : " + Thread.currentThread().getName());
                    }
                })
                .subscribe(consumer);


    }

    @OnClick({R.id.btn_area})
    void onViewClick() {

        startActivity(new Intent(mContext, AreaActivity.class));



    }
}