package com.liuz.design.ui.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuz.design.R;
import com.liuz.design.base.BaseFragment;
import com.liuz.design.bean.BannerBean;
import com.liuz.lotus.loader.LoaderFactory;

import butterknife.BindView;
import butterknife.OnClick;


public class BannerItemFragment extends BaseFragment {

    private static final String guideStr = "guideItem";
    @BindView(R.id.iv_banner) ImageView ivBanner;
    @BindView(R.id.tv_banner_title) TextView tvTitle;
    private BannerBean bannerBean;

    public BannerItemFragment() {
        // Required empty public constructor
    }

    public static BannerItemFragment newInstance(BannerBean banner) {
        BannerItemFragment fragment = new BannerItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(guideStr, banner);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bannerBean = getArguments().getParcelable(guideStr);
        }
    }

    @OnClick(R.id.iv_banner)
    void onViewClick() {

    }


    @Override
    protected int getlayoutId() {
        return R.layout.fragment_banner_item_layout;
    }

    @Override
    protected void initEventAndData() {
        LoaderFactory.getLoader().loadNet(ivBanner, bannerBean.getImagePath(), null);
        tvTitle.setText(bannerBean.getTitle());
    }


}
