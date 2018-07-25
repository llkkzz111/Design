package com.liuz.design.ui.adapter.holder;

import android.view.View;

import com.liuz.design.R;
import com.liuz.design.base.BaseViewHolder;
import com.liuz.design.bean.BannerBean;
import com.liuz.design.view.BannerView;

import java.util.List;

import butterknife.BindView;

/**
 * date: 2018/7/18 15:32
 * author liuzhao
 */
public class BannerHolder extends BaseViewHolder<List<BannerBean>> {
    @BindView(R.id.bannerView) BannerView tabBanner;


    public BannerHolder(View view) {
        super(view);
    }

    @Override
    public void onBind(int position, List<BannerBean> bean) {
        tabBanner.setData(bean);
    }
}
