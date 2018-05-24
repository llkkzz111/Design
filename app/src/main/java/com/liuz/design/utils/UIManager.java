package com.liuz.design.utils;

import android.content.Context;

/**
 * date: 2018/5/24 16:18
 * author liuzhao
 */
public class UIManager {
    private static UIManager INSTANCE;
    private Context mBaseContext;

    public synchronized static UIManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UIManager();
        }
        return INSTANCE;
    }

    public Context getBaseContext() {
        return mBaseContext;
    }

    public void setBaseContext(Context context) {
        mBaseContext = context;
    }

}
