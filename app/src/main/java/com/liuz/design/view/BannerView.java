package com.liuz.design.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuz.design.R;
import com.liuz.design.bean.BannerBean;
import com.liuz.design.view.listener.OnBannerListener;
import com.liuz.lotus.loader.LoaderFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * date: 2018/7/4 17:15
 * author liuzhao
 */
public class BannerView extends FrameLayout implements ViewPager.OnPageChangeListener {

    BannerPagerAdapter adapter = null;
    private List<BannerBean> beanList = null;
    private Context mContext;
    private OnBannerListener listener;
    private List<View> imageViews;
    private int count;
    private ViewPager tabBanner;
    private LinearLayout llDots;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int currentItem = -1;

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public BannerView(@NonNull Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
    }

    void init() {
        beanList = new ArrayList<>();
        imageViews = new ArrayList<>();
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_banner_layout, this, true);
        tabBanner = view.findViewById(R.id.tab_banner);
        llDots = view.findViewById(R.id.ll_dots);
//        tabBanner.setAdapter(new BannerPagerAdapter());
    }

    public void setData(List<BannerBean> beanList) {
        this.beanList = beanList;
        this.count = beanList.size();
        if (adapter == null) {
            adapter = new BannerPagerAdapter();
            tabBanner.addOnPageChangeListener(this);

        }

        for (int i = 0; i < beanList.size(); i++) {
            final BannerBean bean = beanList.get(i);
            View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_banner_item_layout, null, true);
            ImageView ivBanner = view.findViewById(R.id.iv_banner);
            LoaderFactory.getLoader().loadNet(ivBanner, bean.getImagePath(), null);
            TextView tvTitle = view.findViewById(R.id.tv_banner_title);
            tvTitle.setText(bean.getTitle());
            ivBanner.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnBannerClick(bean);
                }
            });
            imageViews.add(view);
            ImageView ivDot = new ImageView(mContext);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            ivDot.setPadding(5, 0, 5, 0);
            ivDot.setImageResource(R.drawable.img_dots_bg);
            layoutParams.gravity = Gravity.CENTER;
            ivDot.setLayoutParams(layoutParams);
            llDots.addView(ivDot);

        }

        tabBanner.setAdapter(adapter);
    }

    public void setOnBannerListener(OnBannerListener listener) {
        this.listener = listener;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
        switch (state) {
            case 0://No operation
                if (currentItem == 0) {
                    tabBanner.setCurrentItem(count, false);
                } else if (currentItem == count + 1) {
                    tabBanner.setCurrentItem(1, false);
                }
                break;
            case 1://start Sliding
                if (currentItem == count + 1) {
                    tabBanner.setCurrentItem(1, false);
                } else if (currentItem == 0) {
                    tabBanner.setCurrentItem(count, false);
                }
                break;
            case 2://end Sliding
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(toRealPosition(position), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        currentItem = position;
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(toRealPosition(position));
        }
    }

    /**
     * 返回真实的位置
     *
     * @param position
     * @return 下标从0开始
     */
    public int toRealPosition(int position) {
        int realPosition = (position - 1) % count;
        if (realPosition < 0)
            realPosition += count;
        return realPosition;
    }

    class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return beanList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViews.get(position));
            View view = imageViews.get(position);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

}
