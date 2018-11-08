package com.liuz.design.ui.adapter.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liuz.common.ApiResultTransformer;
import com.liuz.common.subscriber.ApiResultSubscriber;
import com.liuz.design.R;
import com.liuz.design.base.BaseViewHolder;
import com.liuz.design.bean.ArticleBean;
import com.liuz.design.ui.WebViewActivity;
import com.liuz.design.utils.TimeUtils;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.exception.ApiException;
import com.liuz.net.api.WanApiServices;

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
        if (bean.isCollect()) {
            ivCollector.setImageResource(R.drawable.iv_collectored);
        } else {
            ivCollector.setImageResource(R.drawable.iv_collector);
        }
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
                if (bean.isCollect()) {
                    ViseHttp.RETROFIT().create(WanApiServices.class).delCollect(bean.getId())
                            .compose(ApiResultTransformer.<String>norTransformer())
                            .subscribe(new ApiResultSubscriber<String>() {
                                @Override
                                protected void onError(ApiException e) {

                                }

                                @Override
                                public void onSuccess(String data) {
                                    Toast.makeText(itemView.getContext(), "取消收藏成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    ViseHttp.RETROFIT().create(WanApiServices.class).addCollect(bean.getId())
                            .compose(ApiResultTransformer.<String>norTransformer())
                            .subscribe(new ApiResultSubscriber<String>() {
                                @Override
                                protected void onError(ApiException e) {

                                }

                                @Override
                                public void onSuccess(String data) {
                                    Toast.makeText(itemView.getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                break;
            case R.id.iv_collector:
                break;


        }
    }
}
