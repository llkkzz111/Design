package com.liuz.design.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.liuz.design.R;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.utils.PreferencesUtils;
import com.liuz.lotus.loader.GlideApp;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class LoadingActivity extends TranslucentBarBaseActivity {
    @BindView(R.id.iv_loading) ImageView ivLoading;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_loading_layout;
    }

    @Override
    protected void initEventAndData() {

        getSupportActionBar().hide();


        GlideApp.with(mContext)
                .asBitmap()
                .load(R.drawable.welcome)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ivLoading.setImageBitmap(resource);
                    }
                });

        Timer();
    }


    private void Timer() {
        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Intent intent = new Intent();
                if (PreferencesUtils.getFirstVisitState()) {
                    intent.setClass(mContext, GuideActivity.class);
                } else {
                    intent.setClass(mContext, WanMainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });
    }
}
