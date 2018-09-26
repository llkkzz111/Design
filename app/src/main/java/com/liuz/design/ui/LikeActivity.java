package com.liuz.design.ui;

import android.text.Html;
import android.widget.TextView;

import com.liuz.design.R;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.utils.CustomMovementMethod;
import com.liuz.design.view.ExpandTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class LikeActivity extends TranslucentBarBaseActivity {


    @BindView(R.id.txtShareNewsContent) ExpandTextView txtShareNewsContent;
    @BindView(R.id.txt) TextView txt;
    @BindView(R.id.txt1) TextView txt1;
    String htmlStr = "<p>火币最新<a href= 'http://baidu.com/' rel='nofollow' >公告</a>，火币自主数字资产交易所（HADAX）现支持GVE换链，并将于新加坡时间2018年9月8日11:00开放GVE新币和旧币的充币业务。</p>";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_like;
    }

    @Override
    protected void initEventAndData() {

        htmlStr = htmlStr.replaceAll("\\<p>|</p>", "");

        txtShareNewsContent.setText(htmlStr, false);
        txt.setText(Html.fromHtml(htmlStr));
        txt.setMovementMethod(CustomMovementMethod.getInstance());

        txt1.setText(Html.fromHtml(htmlStr));
        txt1.setMovementMethod(CustomMovementMethod.getInstance());
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
