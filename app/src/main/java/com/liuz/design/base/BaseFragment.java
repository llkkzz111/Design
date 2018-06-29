package com.liuz.design.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuz.lotus.loader.GlideApp;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.DispatchingAndroidInjector;

/**
 * date: 2018/5/28 14:46
 * author liuzhao
 */
public abstract class BaseFragment extends RxFragment {
    protected RxAppCompatActivity mContext;
    protected String title;
    @Inject DispatchingAndroidInjector<Fragment> childFragmentInjector;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = (RxAppCompatActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //绑定View
        View view = inflater.inflate(getlayoutId(), container, false);

        unbinder = ButterKnife.bind(this, view);

        //初始化事件和获取数据, 在此方法中获取数据不是懒加载模式
        initEventAndData();
        //在此方法中获取数据为懒加载模式,如不需要懒加载,请在initEventAndData获取数据,GankFragment有使用实例
        return view;
    }

    protected abstract int getlayoutId();

    protected abstract void initEventAndData();

    @Override
    public void onResume() {
        super.onResume();
        GlideApp.with(mContext).resumeRequests();
    }

    @Override
    public void onPause() {
        super.onPause();
        GlideApp.with(mContext).pauseRequests();
    }
}
