package com.liuz.design.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.liuz.design.bean.BannerBean;
import com.liuz.design.ui.fragment.BannerItemFragment;

import java.util.List;

/**
 * date: 2018/5/28 14:44
 * author liuzhao
 */
public class BannerFragmentAdapter extends FragmentStatePagerAdapter {
    List<BannerBean> tabs = null;

    public BannerFragmentAdapter(FragmentManager fm, List<BannerBean> listMain) {
        super(fm);
        this.tabs = listMain;
    }

    @Override
    public Fragment getItem(int position) {
        BannerItemFragment bannerItemFragment = BannerItemFragment.newInstance(tabs.get(position));
        return bannerItemFragment;
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
