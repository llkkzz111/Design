package com.liuz.design.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.liuz.design.R;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.ui.adapter.PageTableFragmentAdapter;
import com.liuz.design.utils.BottomNavigationViewHelper;
import com.liuz.design.utils.Utils;
import com.liuz.design.view.CustomViewPager;
import com.vise.log.ViseLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends TranslucentBarBaseActivity {


    @BindView(R.id.navigation) BottomNavigationView navigation;

    @BindView(R.id.vp_main) CustomViewPager vpMain;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    vpMain.setCurrentItem(0, false);
                    return true;
                case R.id.navigation_ticket:
                    vpMain.setCurrentItem(1, false);
                    return true;
                case R.id.navigation_shop:
                    vpMain.setCurrentItem(2, false);
                    return true;
                case R.id.navigation_live:
                    vpMain.setCurrentItem(3, false);
                    return true;
                case R.id.navigation_me:
                    vpMain.setCurrentItem(4, false);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        getSupportActionBar().hide();
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        List<String> listMain = new ArrayList<>();
        listMain.add("首页");
        listMain.add("购票");
        listMain.add("商城");
        listMain.add("直播");
        listMain.add("我的");
        PageTableFragmentAdapter adapter = new PageTableFragmentAdapter(getSupportFragmentManager(), listMain);
        vpMain.setAdapter(adapter);
        vpMain.setScanScroll(false);
        ViseLog.e(Utils.sHA1(mContext));
    }

}
