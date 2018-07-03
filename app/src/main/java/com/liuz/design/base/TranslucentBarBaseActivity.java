package com.liuz.design.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.liuz.lotus.manager.ActivityStack;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * deta: ${DETA}
 * author liuzhao
 */
public abstract class TranslucentBarBaseActivity extends RxAppCompatActivity {

    protected Context mContext;

    /**
     * 使状态栏透明
     * 适用于图片作为背景的界面，此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity
                    .findViewById(android.R.id.content)).getChildAt(0);
            if (rootView != null) {
                rootView.setFitsSystemWindows(true);
                rootView.setClipToPadding(true);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindowsFlag();
        mContext = this;
        ActivityStack.getInstance().add(this);
        if (getLayoutResId() > 0) {
            setContentView(getLayoutResId());
            ButterKnife.bind(this);
            initEventAndData();
        }
    }

    public void setColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View StatusView = createStatusView(color);
            // 添加statusView到布局中
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            if (decorView != null)
                decorView.addView(StatusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content))
                    .getChildAt(0);
            if (rootView != null) {
                rootView.setFitsSystemWindows(true);
                rootView.setClipToPadding(true);
            }
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     *
     * @param color 状态栏的颜色值
     * @return 状态栏矩形条
     */
    private View createStatusView(int color) {
        // 获得状态栏的高度
        int resourceId = getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources()
                .getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高度的矩形
        View statusView = new View(this);
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);

        return statusView;
    }


    private void initWindowsFlag() {
//        setColor(R.color.color_f97d3f);
    }

    // 设置布局
    protected abstract int getLayoutResId();

    protected abstract void initEventAndData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getInstance().remove(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
