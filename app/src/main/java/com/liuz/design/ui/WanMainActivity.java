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

import com.liuz.db.wan.AccountBean;
import com.liuz.design.R;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.bean.ArticleBean;
import com.liuz.design.bean.ArticleBeans;
import com.liuz.design.bean.BannerBean;
import com.liuz.design.ui.adapter.WanArticleAdapter;
import com.liuz.design.utils.PreferencesUtils;
import com.liuz.design.view.DividerItemDecoration;
import com.liuz.lotus.loader.LoaderFactory;
import com.liuz.mvvm.vm.WanViewModel;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


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
        initView();

        viewModel = ViewModelProviders.of(this).get(WanViewModel.class);
        viewModel.getLiveData().observe(this, apiResult -> {
            if (apiResult != null && apiResult.getData() != null) {
                if (apiResult.getData() instanceof AccountBean) {
                    account = (AccountBean) apiResult.getData();
                    if (!TextUtils.isEmpty(account.getIcon())) {
                        LoaderFactory.getLoader().loadNet(ivHeader, account.getIcon(), null);
                    }
                } else if (apiResult.getData() instanceof ArticleBeans) {
                    ArticleBeans articleBeans = (ArticleBeans) apiResult.getData();
                    beanList.addAll(articleBeans.getDatas());
                    articleAdapter.notifyDataSetChanged();
                    rvArticle.setPullLoadMoreCompleted();
                } else {
                    List<BannerBean> list = (List<BannerBean>) apiResult.getData();
                    beanList.add(new ArticleBean());
                    articleAdapter.setBannerBean(list);
                }
            }
        });
        String userName = PreferencesUtils.getUserName();
        if (!TextUtils.isEmpty(userName)) {
            getAccount(userName);
        }
        viewModel.loadData(pageNo);
    }

    private void initView() {
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


        rvArticle.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                pageNo = 0;
                viewModel.loadData(pageNo);
            }

            @Override
            public void onLoadMore() {
                pageNo++;
                viewModel.loadMore(pageNo);
            }
        });

        toggle.setToolbarNavigationClickListener(v -> {
            if (TextUtils.isEmpty(tvName.getText())) {
                tvName.setText(PreferencesUtils.getUserName());
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
        viewModel.getAccount(userName);
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
