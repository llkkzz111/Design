package com.liuz.design.ui.adapter.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuz.design.R;
import com.liuz.design.base.BaseViewHolder;
import com.liuz.design.bean.ArticleBean;
import com.liuz.design.ui.WebViewActivity;
import com.liuz.design.utils.TimeUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date: 2018/7/18 15:32
 * author liuzhao
 */
public class AticleHolder extends BaseViewHolder<ArticleBean> {
    @BindView(R.id.tv_article_title) TextView tvTitle;
    @BindView(R.id.tv_author) TextView tvAuthor;
    @BindView(R.id.iv_collector) ImageView ivCollector;
    @BindView(R.id.tv_create_time) TextView tvTime;
    @BindView(R.id.ll_article_content) LinearLayout llContent;

    private ArticleBean bean;
    public AticleHolder(View view) {
        super(view);
    }

    @Override
    public void onBind(int position, ArticleBean bean) {
        this.bean = bean;
        tvTitle.setText(bean.getTitle());
        tvAuthor.setText(bean.getAuthor());
        tvTime.setText(TimeUtils.friednlyTime(bean.getPublishTime()));
    }

    @OnClick({R.id.ll_article_content, R.id.tv_article_title, R.id.iv_collector})
    void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.ll_article_content:
                Intent intent = new Intent(itemView.getContext(), WebViewActivity.class);
                intent.putExtra("URL", bean.getLink());
                intent.putExtra("TITLE", bean.getTitle());
                itemView.getContext().startActivity(intent);
                break;
            case R.id.tv_article_title:

                break;
            case R.id.iv_collector:
                break;


        }
    }
}
