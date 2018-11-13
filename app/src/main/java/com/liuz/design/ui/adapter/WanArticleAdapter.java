package com.liuz.design.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuz.design.R;
import com.liuz.design.base.BaseViewHolder;
import com.liuz.design.base.RecycleBaseAdapter;
import com.liuz.design.bean.ArticleBean;
import com.liuz.design.bean.BannerBean;
import com.liuz.design.ui.adapter.holder.AticleHolder;
import com.liuz.design.ui.adapter.holder.BannerHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * date: 2018/7/18 15:29
 * author liuzhao
 */
public class WanArticleAdapter extends RecycleBaseAdapter<ArticleBean, BaseViewHolder> {

    private Context mContext;
    private List<BannerBean> bannerBeans;

    public WanArticleAdapter(Context mContext, List<ArticleBean> articleBeans) {
        bannerBeans = new ArrayList<>();
        this.list = articleBeans;
        this.mContext = mContext;
    }
//

    @Override
    public int getLayoutRes(int position) {
        if (list.get(position).getId() == 0)
            return R.layout.item_home_banner_layout;
        else
            return R.layout.item_article_title_layout;
    }

    @Override
    public BaseViewHolder getHolder(View item, ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.item_home_banner_layout:
                return new BannerHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false));
            case R.layout.item_article_title_layout:
            default:
                return new AticleHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false));
        }
    }

    @Override
    public void convert(BaseViewHolder holder, ArticleBean data, int index, int type) {
        switch (type) {
            case R.layout.item_home_banner_layout:
                ((BannerHolder) holder).onBind(index, bannerBeans);
                break;
            case R.layout.item_article_title_layout:
            default:
                ((AticleHolder) holder).onBind(index, data);
                break;
        }

    }

    public void setBannerBean(List<BannerBean> bannerBeans) {
        list.clear();
        list.add(0, new ArticleBean());
        this.bannerBeans.clear();
        this.bannerBeans.addAll(bannerBeans);
        notifyDataSetChanged();
    }
}
