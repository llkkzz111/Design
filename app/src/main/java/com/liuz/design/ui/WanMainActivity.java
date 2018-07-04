package com.liuz.design.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuz.common.ApiResultTransformer;
import com.liuz.common.subscriber.ApiResultSubscriber;
import com.liuz.db.WanDataBase;
import com.liuz.db.wan.AccountBean;
import com.liuz.design.R;
import com.liuz.design.api.WanApiServices;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.bean.BannerBean;
import com.liuz.design.utils.PreferencesUtils;
import com.liuz.design.view.BannerView;
import com.liuz.lotus.loader.LoaderFactory;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.exception.ApiException;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WanMainActivity extends TranslucentBarBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final int ACCOUNT_LOGIN = 100;

    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.bannerView) BannerView tabBanner;


    private ImageView ivHeader;
    private TextView tvName;
    private AccountBean account;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_wan_main_layout;
    }

    @Override
    protected void initEventAndData() {

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        ivHeader = navigationView.getHeaderView(0).findViewById(R.id.iv_header);
        tvName = navigationView.getHeaderView(0).findViewById(R.id.tv_user_name);
        ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileClick();
            }
        });

        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileClick();
            }
        });

        String userName = PreferencesUtils.getUserName();
        if (!TextUtils.isEmpty(userName)) {
            getAccount(userName);
        }

        getBannerInfo();
    }

    private void getBannerInfo() {
        ViseHttp.RETROFIT().create(WanApiServices.class).getBanner().compose(ApiResultTransformer.<List<BannerBean>>norTransformer()).subscribe(new ApiResultSubscriber<List<BannerBean>>() {
            @Override
            protected void onError(ApiException e) {

            }

            @Override
            public void onSuccess(List<BannerBean> data) {

                tabBanner.setData(data);

            }


        });
    }


    private void profileClick() {
        Intent intent = new Intent();
        if (account != null) {
            intent.setClass(mContext, LoginActivity.class);
        } else {
            intent.setClass(mContext, LoginActivity.class);
            startActivityForResult(intent, ACCOUNT_LOGIN);
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_about:
                break;
            case R.id.nav_like:
                break;
            case R.id.nav_share:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case ACCOUNT_LOGIN:
                    String userName = data.getStringExtra("username");
                    getAccount(userName);
                    break;
            }
    }

    private void getAccount(final String userName) {
        tvName.setText(userName);

        Observable.create(new ObservableOnSubscribe<AccountBean>() {
            @Override
            public void subscribe(ObservableEmitter<AccountBean> emitter) throws Exception {
                AccountBean bean = WanDataBase.getInstance(mContext).accountDao().getAccountBean(userName);
                emitter.onNext(bean);
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountBean>() {
                    @Override
                    public void accept(@NonNull AccountBean accountBean) throws Exception {
                        account = accountBean;
                        if (!TextUtils.isEmpty(accountBean.getIcon())) {
                            LoaderFactory.getLoader().loadNet(ivHeader, accountBean.getIcon(), null);
                        }
                    }
                });
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
