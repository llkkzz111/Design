package com.liuz.design.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liuz.common.ApiResultTransformer;
import com.liuz.common.subscriber.ApiResultSubscriber;
import com.liuz.db.WanDataBase;
import com.liuz.db.wan.AccountBean;
import com.liuz.design.R;
import com.liuz.design.api.WanApiServices;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.bean.ArticleBean;
import com.liuz.design.bean.ArticleBeans;
import com.liuz.design.bean.BannerBean;
import com.liuz.design.ui.adapter.WanArticleAdapter;
import com.liuz.design.utils.PreferencesUtils;
import com.liuz.design.view.BannerView;
import com.liuz.design.view.listener.OnBannerListener;
import com.liuz.lotus.loader.LoaderFactory;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.exception.ApiException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
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
    @BindView(R.id.rv_article) RecyclerView rvArticle;
    @BindView(R.id.rl_smart) SmartRefreshLayout rlSmart;


    private ImageView ivHeader;
    private TextView tvName;
    private AccountBean account;
    private int pageNo = 0;
    private List<ArticleBean> beanList;
    private WanArticleAdapter articleAdapter;

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
        rvArticle.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        beanList = new ArrayList<>();
        articleAdapter = new WanArticleAdapter(mContext, beanList);
        rvArticle.setAdapter(articleAdapter);
        String userName = PreferencesUtils.getUserName();
        if (!TextUtils.isEmpty(userName)) {
            getAccount(userName);
        }
        getBannerInfo();

        rlSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@android.support.annotation.NonNull RefreshLayout refreshLayout) {
                getBannerInfo();
            }
        });

        rlSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@android.support.annotation.NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getArticleList();
            }
        });
    }

    private void getBannerInfo() {
        ViseHttp.RETROFIT().create(WanApiServices.class).getBanner().compose(ApiResultTransformer.<List<BannerBean>>norTransformer()).subscribe(new ApiResultSubscriber<List<BannerBean>>() {
            @Override
            protected void onError(ApiException e) {
                rlSmart.finishRefresh();
            }

            @Override
            public void onSuccess(List<BannerBean> data) {
                rlSmart.finishRefresh();
                tabBanner.setData(data);
                tabBanner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(BannerBean bean) {
                        Toast.makeText(mContext, bean.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        getArticleList();
    }

    private void getArticleList() {
        ViseHttp.RETROFIT().create(WanApiServices.class).getArticleList(pageNo)
                .compose(ApiResultTransformer.<ArticleBeans>norTransformer())
                .subscribe(new ApiResultSubscriber<ArticleBeans>() {
                    @Override
                    protected void onError(ApiException e) {
                        rlSmart.finishLoadMore(false);
                    }

                    @Override
                    public void onSuccess(ArticleBeans data) {
                        if (pageNo == 1)
                            beanList.clear();
                        beanList.addAll(data.getDatas());
                        articleAdapter.notifyDataSetChanged();
                        rlSmart.finishLoadMore(true);
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
