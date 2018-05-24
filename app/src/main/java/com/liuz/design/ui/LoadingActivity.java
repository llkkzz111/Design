package com.liuz.design.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.liuz.design.R;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.utils.DialogUtils;
import com.liuz.design.utils.PreferencesUtils;
import com.liuz.lotus.loader.GlideApp;
import com.liuz.lotus.permission.Permission;
import com.liuz.lotus.permission.RxPermissions;
import com.vise.utils.view.DialogUtil;

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

        getPermission();
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mContext != null) {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Permission>() {
                @Override
                public void accept(Permission permission) throws Exception {
                    if (permission.granted) {
                        Timer();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 重新获取权限
                        getPermission();
                    } else {
                        //提示
                        switch (permission.name) {
                            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                                DialogUtils.showTips(mContext, R.string.permission_storage_title, R.string.permission_storage_des,
                                        R.string.permission_cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }


                                        },
                                        R.string.permission_ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Uri packageURI = Uri.parse("package:" + "com.liuz.design");
                                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                                startActivity(intent);
                                                finish();
                                            }

                                        });
                                break;
                            default:
                                DialogUtil.showTips(mContext, getString(R.string.permission_control),
                                        getString(R.string.permission_noAsk) + "\n" + permission.name);
                                break;
                        }

                    }
                }
            });
        } else {
            Timer();
        }
    }

    private void Timer() {
        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Intent intent = new Intent();
                if (PreferencesUtils.getFirstVisitState()) {
                    intent.setClass(mContext, GuideActivity.class);
                } else {
                    intent.setClass(mContext, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });
    }
}
