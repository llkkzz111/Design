package com.liuz.design.ui.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.liuz.design.R;
import com.liuz.design.base.BaseViewHolder;
import com.liuz.design.bean.ArticleBean;

import butterknife.BindView;

/**
 * date: 2018/7/18 15:32
 * author liuzhao
 */
public class AticleHolder extends BaseViewHolder<ArticleBean> {
    @BindView(R.id.tv_article_title) TextView tvTitle;


    public AticleHolder(View view) {
        super(view);
    }

    @Override
    public void onBind(int position, ArticleBean bean) {
        tvTitle.setText(bean.getTitle());
    }
}
