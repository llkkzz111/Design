package com.liuz.design.ui;

import android.Manifest;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.liuz.design.R;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.lotus.loader.GlideApp;
import com.liuz.lotus.permission.OnPermissionCallback;
import com.liuz.lotus.permission.PermissionManager;
import com.vise.utils.view.DialogUtil;

import butterknife.BindView;

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

        PermissionManager.instance().request(this, new OnPermissionCallback() {
            @Override
            public void onRequestAllow(String permissionName) {
                DialogUtil.showTips(mContext, getString(R.string.permission_control),
                        getString(R.string.permission_allow) + "\n" + permissionName);
            }

            @Override
            public void onRequestRefuse(String permissionName) {
                DialogUtil.showTips(mContext, getString(R.string.permission_control),
                        getString(R.string.permission_refuse) + "\n" + permissionName);
            }

            @Override
            public void onRequestNoAsk(String permissionName) {
                switch (permissionName){
                    case  Manifest.permission.WRITE_EXTERNAL_STORAGE:
                        finish();
                        break;
                }
                DialogUtil.showTips(mContext, getString(R.string.permission_control),
                        getString(R.string.permission_noAsk) + "\n" + permissionName);
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
