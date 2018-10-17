package com.liuz.design;

import android.view.View;

/**
 * date: 2018/8/7 18:39
 * author liuzhao
 */
public class TabItem {
    public TabItem(String title, String hint, View layout) {
        this.hint = hint;
        this.title = title;
        this.layout = layout;
    }

    private String title;
    private String hint;
    private View layout;

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public View getLayout() {
        return layout;
    }

    public void setLayout(View layout) {
        this.layout = layout;
    }
}
