package com.liuz.design.ui.fragment;

import android.os.Bundle;

import com.liuz.design.R;
import com.liuz.design.base.BaseFragment;
import com.liuz.design.view.Guides;


/**
 * date: 2018/5/28 14:46
 * author liuzhao
 */
public class ShopFragment extends BaseFragment {
    public ShopFragment() {
        // Required empty public constructor
    }

    public static ShopFragment newInstance(Guides banner) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_shop_layout;
    }

    @Override
    protected void initEventAndData() {

    }
}