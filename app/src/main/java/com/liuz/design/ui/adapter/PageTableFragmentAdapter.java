package com.liuz.design.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.liuz.design.TabItem;
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
    List<TabItem> tabs = null;

    public PageTableFragmentAdapter(FragmentManager fm, List<TabItem> listMain) {
        super(fm);
        this.tabs = listMain;
    }


    @Override
    public Fragment getItem(int position) {

        BaseFragment fragment = null;
        if (tabs != null) {
            switch (tabs.get(position).getTitle()) {
                case "消息":
                    HomeFragment homeFragment = new HomeFragment();
                    fragment = homeFragment;
                    break;
                case "发现":
                    TicketFragment ticketFragment = new TicketFragment();
                    fragment = ticketFragment;
                    break;
                case "联系人":
                    ShopFragment shopFragment = new ShopFragment();
                    fragment = shopFragment;
                    break;
                case "任务":
                    LiveFragment liveFragment = new LiveFragment();
                    fragment = liveFragment;
                    break;
                case "我":
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
