package com.liuz.design.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 嵌套recycleview 添加惯性实现
 * Created by liuzhao on 2017/12/18.
 */

public class InertiaScrollView extends ScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop = 100;

    public InertiaScrollView(Context context) {
        super(context);
    }

    public InertiaScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InertiaScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                int moveX = (int) e.getRawX();
                if (Math.abs(moveX - downX) < Math.abs(moveY - downY) && Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }
}
