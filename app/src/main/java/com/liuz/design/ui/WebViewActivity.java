package com.liuz.design.ui;

import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.liuz.design.R;
import com.liuz.design.base.TranslucentBarBaseActivity;

import butterknife.BindView;

public class WebViewActivity extends TranslucentBarBaseActivity {

    @BindView(R.id.wv_content) WebView wvContent;
    private ActionBar actionBar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view_layout;
    }

    @Override
    protected void initEventAndData() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        String url = getIntent().getStringExtra("URL");
        actionBar.setTitle(getIntent().getStringExtra("TITLE"));
        WebSettings webSettings = wvContent.getSettings();
        webSettings.setDomStorageEnabled(true);//新加-->解决点餐网页打不开bug
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setJavaScriptEnabled(true);
        wvContent.loadUrl(url);

        wvContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                actionBar.setTitle(title);
            }
        });

        wvContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    actionBar.setTitle(title);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                if (wvContent.canGoBack()) {
                    wvContent.goBack();
                    return true;
                } else {
                    return super.onOptionsItemSelected(item);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (wvContent.canGoBack()) {
            wvContent.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
