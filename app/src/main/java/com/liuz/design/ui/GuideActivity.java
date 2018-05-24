package com.liuz.design.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.liuz.design.R;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.ui.fragment.BannerItemFragment;
import com.liuz.design.view.Banners;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GuideActivity extends TranslucentBarBaseActivity {

    private static List<Banners> list;
    @BindView(R.id.vp_banner) ViewPager vpBanner;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_guide_layout;
    }

    @Override
    protected void initEventAndData() {
        getSupportActionBar().hide();
        list = new ArrayList<>();
        list.add(new Banners(R.drawable.lead_bg1, R.drawable.lead_bg1_iv));
        list.add(new Banners(R.drawable.lead_bg2, R.drawable.lead_bg2_iv));
        list.add(new Banners(R.drawable.lead_bg3, R.drawable.lead_bg3_iv));
        list.add(new Banners(R.drawable.lead_bg4, R.drawable.lead_bg4_des, R.drawable.lead_bg4_btn));
        vpBanner.setAdapter(new BannerAdapter(getSupportFragmentManager()));
    }

    static class BannerAdapter extends FragmentPagerAdapter {

        public BannerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int position) {
            BannerItemFragment fragment = BannerItemFragment.newInstance(list.get(position));
            return fragment;
        }

    }
}
