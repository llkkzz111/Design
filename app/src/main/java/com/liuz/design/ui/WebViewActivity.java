package com.liuz.design.ui;

import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
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
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress == 100) {

                }
            }


            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);


                if (title.contains("404")) {

                } else {
                    actionBar.setTitle(title);
                }
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

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //6.0以下执行

            }

            //处理网页加载失败时
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //6.0以上执行

            }


        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                if (wvContent.canGoBack()) {
                    if (!wvContent.copyBackForwardList().getCurrentItem().getUrl().equals(wvContent.copyBackForwardList().getCurrentItem().getOriginalUrl())) {
                        wvContent.goBack();
                    }
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
            if (!wvContent.copyBackForwardList().getCurrentItem().getUrl().equals(wvContent.copyBackForwardList().getCurrentItem().getOriginalUrl())) {
                wvContent.goBack();
            }
            wvContent.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
