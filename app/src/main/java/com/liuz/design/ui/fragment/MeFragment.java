package com.liuz.design.ui.fragment;

import android.os.Bundle;

import com.liuz.design.R;
import com.liuz.design.base.BaseFragment;
import com.liuz.design.view.Guides;


/**
 * date: 2018/5/28 14:46
 * author liuzhao
 */
public class MeFragment extends BaseFragment {

    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment newInstance(Guides banner) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_me_layout;
    }

    @Override
    protected void initEventAndData() {

    }
}