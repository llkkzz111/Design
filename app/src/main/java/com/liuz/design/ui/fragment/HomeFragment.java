package com.liuz.design.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.liuz.design.R;
import com.liuz.design.base.BaseFragment;
import com.liuz.design.ui.movies.MTimesMoviesActivity;
import com.liuz.design.view.Guides;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.support.constraint.Constraints.TAG;


/**
 * date: 2018/5/28 14:46
 * author liuzhao
 */
public class HomeFragment extends BaseFragment {


    String areaJson = "file:///android_asset/area.json";
    @BindView(R.id.btn_area)
    Button btnArea;
    @BindView(R.id.btn_hot)
    Button btnHot;
    private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};

    public HomeFragment() {

    }

    public static HomeFragment newInstance(Guides banner) {
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


    }

    private void rxThread() {
        Observable<Integer> observable = Observable.create(emitter -> {
            Log.d(TAG, "Observable thread is : " + Thread.currentThread().getName());
            Log.d(TAG, "emit 1");
            emitter.onNext(1);
        });

        Consumer<Integer> consumer = integer -> {
            Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
            Log.d(TAG, "onNext: " + integer);
        };
        observable.subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(integer -> Log.d(TAG, "After observeOn(mainThread), current thread is: " + Thread.currentThread().getName()))
                .observeOn(Schedulers.io())
                .doOnNext(integer -> Log.d(TAG, "After observeOn(io), current thread is : " + Thread.currentThread().getName()))
                .subscribe(consumer);
    }

    @OnClick({R.id.btn_area, R.id.btn_hot, R.id.btn_rxzip, R.id.btn_rxmap, R.id.btn_rxflatmap})
    void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.btn_area:
                rxThread();
//                gotoArea();
                break;
            case R.id.btn_hot:
                startActivity(new Intent(mContext, MTimesMoviesActivity.class));
                break;
            case R.id.btn_rxzip:
                rxZip();
                break;
            case R.id.btn_rxmap:
                rxMap();
                break;
            case R.id.btn_rxflatmap:
                rxFlatMap();
                break;
        }

    }

    private void gotoArea() {
        //                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity() != null) {
//                    RxPermissions rxPermissions = new RxPermissions(getActivity());
//                    rxPermissions.requestEach(permissions).subscribe(new Consumer<Permission>() {
//                        @Override
//                        public void accept(Permission permission) throws Exception {
//                            if (permission.granted) {
//                                startActivity(new Intent(mContext, AreaActivity.class));
//                            } else if (permission.shouldShowRequestPermissionRationale) {
//                                // 重新获取权限
//                                startActivity(new Intent(mContext, AreaActivity.class));
//                            } else {
//                                //提示
//                                DialogUtils.showTips(getActivity(), R.string.permission_storage_title, R.string.permission_storage_des,
//                                        R.string.permission_cancel, (dialog, which) -> getActivity().finish(),
//                                        R.string.permission_ok, (dialog, which) -> {
//                                            Uri packageURI = Uri.parse("package:" + "com.liuz.design");
//                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
//                                            startActivity(intent);
//                                            getActivity().finish();
//                                        });
//
//                            }
//                        }
//                    });
//                } else {
//                    startActivity(new Intent(mContext, AreaActivity.class));
//                }
    }


    private void rxMap() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            Log.d(TAG, "emit 1");
            emitter.onNext(1);
            Log.d(TAG, "emit 2");
            emitter.onNext(2);
        }).map(integer -> "This is result  " + integer).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "emit " + s);
            }
        });
    }

    private void rxFlatMap() {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            Log.d(TAG, "current thread is: " + Thread.currentThread().getName());
            Log.d(TAG, "emit 1");
            emitter.onNext("emit 1");
//            Thread.sleep(10000);
//            Log.d(TAG, "emit 2");
//            emitter.onNext("emit 2");
//            Thread.sleep(10000);
//            Log.d(TAG, "emit 3");
//            emitter.onNext("emit 3");
        }).flatMap((Function<String, ObservableSource<String>>) str -> {
            Log.d(TAG, "current thread is: " + Thread.currentThread().getName());
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                list.add("I am value " + str + "---- i = " + 0);
                Log.d(TAG, "I am value " + str + "---- i = " + 0);
            }
            Log.d(TAG, "list.size() = " + list.size());

            return Observable.fromIterable(list);
        }).subscribe(s -> {
            Log.d(TAG, "current thread is: " + Thread.currentThread().getName());
            Log.d(TAG, "emit " + s);

        });
    }

    private void rxZip() {
        Observable<Integer> observable1 = Observable.create(emitter -> {
            Log.d(TAG, "emit 1");
            emitter.onNext(1);
            Log.d(TAG, "emit 2");
            emitter.onNext(2);
            Log.d(TAG, "emit 3");
            emitter.onNext(3);
            Log.d(TAG, "emit 4");
            emitter.onNext(4);
            Log.d(TAG, "emit complete1");
            emitter.onComplete();
        });

        Observable<String> observable2 = Observable.create(emitter -> {
            Log.d(TAG, "emit A");
            emitter.onNext("A");
            Log.d(TAG, "emit B");
            emitter.onNext("B");
            Log.d(TAG, "emit C");
            emitter.onNext("C");

            Log.d(TAG, "emit complete2");
            emitter.onComplete();
        });

        Observable.zip(observable1, observable2, (integer, s) -> integer + s).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });


    }

}