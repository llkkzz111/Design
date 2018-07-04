package com.liuz.design.ui.fragment;

import android.os.Bundle;

import com.liuz.design.R;
import com.liuz.design.base.BaseFragment;
import com.liuz.design.view.Guides;


/**
 * date: 2018/5/28 14:46
 * author liuzhao
 */
public class LiveFragment extends BaseFragment {

    public LiveFragment() {
        // Required empty public constructor
    }

    public static LiveFragment newInstance(Guides banner) {
        LiveFragment fragment = new LiveFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_live_layout;
    }

    @Override
    protected void initEventAndData() {

    }
}