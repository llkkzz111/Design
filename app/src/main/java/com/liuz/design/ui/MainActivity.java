package com.liuz.design.ui;

import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import com.liuz.design.R;
import com.liuz.design.TabItem;
import com.liuz.design.base.TranslucentBarBaseActivity;
import com.liuz.design.ui.adapter.PageTableFragmentAdapter;
import com.liuz.design.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends TranslucentBarBaseActivity {


    @BindView(R.id.vp_main) CustomViewPager vpMain;
    @BindView(R.id.tab_home) RelativeLayout tabHome;
    @BindView(R.id.tab_member) RelativeLayout tabMember;
    @BindView(R.id.tab_find) RelativeLayout tabFind;
    @BindView(R.id.tab_task) RelativeLayout tabTask;
    @BindView(R.id.tab_mine) RelativeLayout tabMine;
    private List<TabItem> listTitle = new ArrayList<>();
    private View clickView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        initTab();
    }

    private void initTab() {
        listTitle.add(new TabItem("消息", "消息", tabHome));
        listTitle.add(new TabItem("联系人", "联系人", tabMember));
        listTitle.add(new TabItem("发现", "发现", tabFind));
        listTitle.add(new TabItem("任务", "任务", tabTask));
        listTitle.add(new TabItem("我", "我", tabMine));

        PageTableFragmentAdapter adapter = new PageTableFragmentAdapter(getSupportFragmentManager(), listTitle);
        vpMain.setAdapter(adapter);
        vpMain.setOffscreenPageLimit(4);
        tabHome.performClick();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (clickView != null)
            switch (clickView.getId()) {
                case R.id.tab_home:
                    getMenuInflater().inflate(R.menu.frag_home_menu, menu);
                    break;
                case R.id.tab_member:
                    getMenuInflater().inflate(R.menu.frag_member_menu, menu);
                    break;
                case R.id.tab_find:
                    getMenuInflater().inflate(R.menu.frag_find_menu, menu);
                    break;
                case R.id.tab_task:
                    getMenuInflater().inflate(R.menu.frag_task_menu, menu);
                    break;
                case R.id.tab_mine:
                    getMenuInflater().inflate(R.menu.frag_me_menu, menu);
                    break;
            }
        return super.onPrepareOptionsMenu(menu);
    }


    @OnClick({R.id.tab_home, R.id.tab_member, R.id.tab_find, R.id.tab_task, R.id.tab_mine})
    public void onViewClick(View v) {
        clickView = v;
        Checked(v.getId());
        switch (v.getId()) {
            case R.id.tab_home:
                vpMain.setCurrentItem(0);
                break;
            case R.id.tab_member:
                vpMain.setCurrentItem(1);
                break;
            case R.id.tab_find:
                vpMain.setCurrentItem(2);

                break;
            case R.id.tab_task:
                vpMain.setCurrentItem(3);
                break;
            case R.id.tab_mine:
                vpMain.setCurrentItem(4);
                break;
        }
        supportInvalidateOptionsMenu();
    }

    /**
     * 切换Layout选中状态
     *
     * @param id 选中的layout的id
     */
    private void Checked(int id) {
        for (TabItem entry : listTitle) {
            View layout = entry.getLayout();
            if (layout.isSelected()) {
                layout.setSelected(false);
            }
            if (layout.getId() == id) {
                layout.setSelected(true);
            }

        }
    }

}
