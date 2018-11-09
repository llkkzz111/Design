package com.liuz.design.ui;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuz.common.ApiResultTransformer;
import com.liuz.common.subscriber.ApiResultSubscriber;
import com.liuz.db.WanDataBase;
import com.liuz.db.wan.AccountBean;
import com.liuz.design.R;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.bean.ArticleBean;
import com.liuz.design.bean.ArticleBeans;
import com.liuz.design.ui.adapter.WanArticleAdapter;
import com.liuz.design.utils.PreferencesUtils;
import com.liuz.design.view.DividerItemDecoration;
import com.liuz.jetpack.lifecycle.MyObserver;
import com.liuz.lotus.loader.LoaderFactory;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.exception.ApiException;
import com.liuz.mvvm.vm.WanViewModel;
import com.liuz.net.api.WanApiServices;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class WanMainActivity extends TranslucentBarBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, LifecycleOwner {
    private static final String TAG = "WanMainActivity";
    private final int ACCOUNT_LOGIN = 100;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rv_article) PullLoadMoreRecyclerView rvArticle;

    private ImageView ivHeader;
    private TextView tvName;
    private AccountBean account;
    private int pageNo = 0;
    private List<ArticleBean> beanList;
    private WanArticleAdapter articleAdapter;
    private WanViewModel viewModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_wan_main_layout;
    }

    @Override
    protected void initEventAndData() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("Wan");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        ivHeader = navigationView.getHeaderView(0).findViewById(R.id.iv_header);
        tvName = navigationView.getHeaderView(0).findViewById(R.id.tv_user_name);
        ivHeader.setOnClickListener(v -> profileClick());
        tvName.setOnClickListener(v -> profileClick());
        rvArticle.setLinearLayout();
        rvArticle.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.HORIZONTAL, 15,
                getResources().getColor(R.color.color_F0F0F0), true));
        beanList = new ArrayList<>();
        articleAdapter = new WanArticleAdapter(mContext, beanList);
        rvArticle.setAdapter(articleAdapter);
        rvArticle.setPushRefreshEnable(true);
        rvArticle.setFooterViewText("加载中。。");
        String userName = PreferencesUtils.getUserName();
        if (!TextUtils.isEmpty(userName)) {
            getAccount(userName);
        }

        rvArticle.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                pageNo++;
                getArticleList();
            }
        });

        toggle.setToolbarNavigationClickListener(v -> {
            if (TextUtils.isEmpty(tvName.getText())) {
                tvName.setText(PreferencesUtils.getUserName());
            }
        });

        getLifecycle().addObserver(new MyObserver());

        viewModel = ViewModelProviders.of(this).get(WanViewModel.class);
        viewModel.getLiveData().observe(this, articleBeans -> {
            beanList.addAll(articleBeans.getDatas());
            articleAdapter.notifyDataSetChanged();
        });

        viewModel.getLiveData().observe(this, articleBeans -> {

        });
    }


    private void getArticleList() {
        ViseHttp.RETROFIT().create(WanApiServices.class).getArticleList(pageNo)
                .compose(ApiResultTransformer.norTransformer())
                .subscribe(new ApiResultSubscriber<ArticleBeans>() {
                    @Override
                    protected void onError(ApiException e) {
                        rvArticle.setPullLoadMoreCompleted();
                    }

                    @Override
                    public void onSuccess(ArticleBeans data) {
                        if (pageNo == 1)
                            beanList.clear();
                        beanList.addAll(data.getDatas());
                        articleAdapter.notifyDataSetChanged();
                        rvArticle.setPullLoadMoreCompleted();
                    }


                });
    }

    private void profileClick() {
        Intent intent = new Intent();
        if (account != null) {
            intent.setClass(mContext, LoginActivity.class);
        } else {
            intent.setClass(mContext, LoginActivity.class);

        }
        startActivityForResult(intent, ACCOUNT_LOGIN);
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
                startActivity(new Intent(this, LikeActivity.class));
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

        Observable.create((ObservableOnSubscribe<AccountBean>) emitter -> {
            AccountBean bean = WanDataBase.getInstance(mContext).accountDao().getAccountBean(userName);
            if (bean != null)
                emitter.onNext(bean);
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accountBean -> {
                    account = accountBean;
                    if (!TextUtils.isEmpty(accountBean.getIcon())) {
                        LoaderFactory.getLoader().loadNet(ivHeader, accountBean.getIcon(), null);
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
