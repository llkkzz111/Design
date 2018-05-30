package com.liuz.design.ui.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.liuz.design.R;
import com.liuz.design.base.BaseFragment;
import com.liuz.design.ui.AreaActivity;
import com.liuz.design.utils.DialogUtils;
import com.liuz.design.view.Banners;
import com.liuz.lotus.permission.Permission;
import com.liuz.lotus.permission.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
    @BindView(R.id.btn_hot) Button btnHot;


    public HomeFragment() {

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

    private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};

    @OnClick({R.id.btn_area})
    void onViewClick() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity() != null) {
            RxPermissions rxPermissions = new RxPermissions(getActivity());
            rxPermissions.requestEach(permissions).subscribe(new Consumer<Permission>() {
                @Override
                public void accept(Permission permission) throws Exception {
                    if (permission.granted) {
                        startActivity(new Intent(mContext, AreaActivity.class));
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 重新获取权限
                        startActivity(new Intent(mContext, AreaActivity.class));
                    } else {
                        //提示
                        DialogUtils.showTips(getActivity(), R.string.permission_storage_title, R.string.permission_storage_des,
                                R.string.permission_cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getActivity().finish();
                                    }


                                },
                                R.string.permission_ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri packageURI = Uri.parse("package:" + "com.liuz.design");
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }

                                });


                    }
                }
            });
        } else {
            startActivity(new Intent(mContext, AreaActivity.class));
        }

    }
}