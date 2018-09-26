package com.liuz.design.ui;

import android.text.Html;
import android.widget.TextView;

import com.liuz.design.R;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.view.ExpandTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class LikeActivity extends TranslucentBarBaseActivity {


    @BindView(R.id.txtShareNewsContent) ExpandTextView txtShareNewsContent;
    @BindView(R.id.txt) TextView txt;
    String htmlStr = "1、据法制日报报道，同济大学同济区块链研究院(苏州)院长马小峰认为，区块链技术通过其防篡改、防丢失。1、据法制日报报道，同济大学同济区块链研究院(苏州)院长马小峰认为，区块链技术通过其防篡改、防丢失。";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_like;
    }

    @Override
    protected void initEventAndData() {
        txtShareNewsContent.setText(htmlStr, false);
        txt.setText(Html.fromHtml(htmlStr));

    }

    @OnClick(R.id.txtShareNewsContent)
    void onViewClick() {
        if (txtShareNewsContent.isExpandState()) {
            txtShareNewsContent.setText(htmlStr, false);
        } else {
            txtShareNewsContent.setText(htmlStr, true);
        }
    }

}
