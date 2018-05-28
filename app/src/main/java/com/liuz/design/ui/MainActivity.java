package com.liuz.design.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;

import com.liuz.design.R;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.utils.BottomNavigationViewHelper;

import butterknife.BindView;

public class MainActivity extends TranslucentBarBaseActivity {


    @BindView(R.id.navigation) BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_ticket:

                    return true;
                case R.id.navigation_shop:

                    return true;
                case R.id.navigation_live:

                    return true;
                case R.id.navigation_me:

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

        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
