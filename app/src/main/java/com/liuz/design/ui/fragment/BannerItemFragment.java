package com.liuz.design.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liuz.design.R;
import com.liuz.design.view.Banners;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class BannerItemFragment extends Fragment {

    private static final String bannerStr = "bannerItem";
    @BindView(R.id.iv_banner) ImageView ivBanner;
    @BindView(R.id.iv_btn) ImageView ivBtn;
    @BindView(R.id.iv_logo) ImageView ivLogo;
    @BindView(R.id.iv_tips) ImageView ivTips;
    private Banners banner;
    private Unbinder unbinder;

    public BannerItemFragment() {
        // Required empty public constructor
    }

    public static BannerItemFragment newInstance(Banners banner) {
        BannerItemFragment fragment = new BannerItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(bannerStr, banner);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            banner = getArguments().getParcelable(bannerStr);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banner_item_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        ivBanner.setBackgroundResource(banner.getBannerRes());
        ivTips.setBackgroundResource(banner.getTipsRes());
        if (banner.getTipHintRes() > 0) {
            ivLogo.setVisibility(View.GONE);
            ivBtn.setBackgroundResource(banner.getTipHintRes());
        }else{

        }


        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }


}
