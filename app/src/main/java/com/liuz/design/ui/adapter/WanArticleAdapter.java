package com.liuz.design.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuz.design.R;
import com.liuz.design.base.RecycleBaseAdapter;
import com.liuz.design.bean.ArticleBean;
import com.liuz.design.ui.adapter.holder.AticleHolder;

import java.util.List;

/**
 * date: 2018/7/18 15:29
 * author liuzhao
 */
public class WanArticleAdapter extends RecycleBaseAdapter<ArticleBean, AticleHolder> {

    private Context mContext;

    public WanArticleAdapter(Context mContext, List<ArticleBean> articleBeans) {
        this.list = articleBeans;
        this.mContext = mContext;
    }
//

    @Override
    public int getLayoutRes(int position) {
        return R.layout.item_article_title_layout;
    }

    @Override
    public AticleHolder getHolder(View item, ViewGroup parent, int viewType) {
        return new AticleHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false));
    }

    @Override
    public void convert(AticleHolder holder, ArticleBean data, int index, int type) {
        holder.onBind(index, data);
    }
}
