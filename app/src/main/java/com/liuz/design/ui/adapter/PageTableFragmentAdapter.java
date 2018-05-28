package com.liuz.design.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.liuz.design.base.BaseFragment;
import com.liuz.design.ui.fragment.ErrorFragment;
import com.liuz.design.ui.fragment.HomeFragment;
import com.liuz.design.ui.fragment.LiveFragment;
import com.liuz.design.ui.fragment.MeFragment;
import com.liuz.design.ui.fragment.ShopFragment;
import com.liuz.design.ui.fragment.TicketFragment;

import java.util.List;

/**
 * date: 2018/5/28 14:44
 * author liuzhao
 */
public class PageTableFragmentAdapter extends FragmentStatePagerAdapter {
    List<String> tabs = null;

    public PageTableFragmentAdapter(FragmentManager fm, List<String> listMain) {
        super(fm);
        this.tabs = listMain;
    }


    @Override
    public Fragment getItem(int position) {

        BaseFragment fragment = null;
        if (tabs != null) {
            switch (tabs.get(position)) {
                case "首页":
                    HomeFragment homeFragment = new HomeFragment();
                    fragment = homeFragment;
                    break;
                case "购票":
                    TicketFragment ticketFragment = new TicketFragment();
                    fragment = ticketFragment;
                    break;
                case "商城":
                    ShopFragment shopFragment = new ShopFragment();
                    fragment = shopFragment;
                    break;
                case "直播":
                    LiveFragment liveFragment = new LiveFragment();
                    fragment = liveFragment;
                    break;
                case "我的":
                    MeFragment meFragment = new MeFragment();
                    fragment = meFragment;
                    break;
                default:
                    ErrorFragment errorFragment = new ErrorFragment();
                    fragment = errorFragment;
                    break;
            }


        }

        return fragment;
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
