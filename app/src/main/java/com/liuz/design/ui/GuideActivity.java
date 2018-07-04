package com.liuz.design.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.liuz.design.R;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.ui.fragment.GuideItemFragment;
import com.liuz.design.view.Guides;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GuideActivity extends TranslucentBarBaseActivity {

    private static List<Guides> list;
    @BindView(R.id.vp_guide) ViewPager vpGuide;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_guide_layout;
    }

    @Override
    protected void initEventAndData() {
        getSupportActionBar().hide();
        list = new ArrayList<>();
        list.add(new Guides(R.drawable.lead_bg1, R.drawable.lead_bg1_iv));
        list.add(new Guides(R.drawable.lead_bg2, R.drawable.lead_bg2_iv));
        list.add(new Guides(R.drawable.lead_bg3, R.drawable.lead_bg3_iv));
        list.add(new Guides(R.drawable.lead_bg4, R.drawable.lead_bg4_iv, R.drawable.lead_bg4_btn, R.drawable.lead_bg4_des));
        vpGuide.setAdapter(new GuideAdapter(getSupportFragmentManager()));
    }

    static class GuideAdapter extends FragmentPagerAdapter {

        public GuideAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int position) {
            GuideItemFragment fragment = GuideItemFragment.newInstance(list.get(position));
            return fragment;
        }

    }
}
