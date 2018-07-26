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
public class BannerView extends FrameLayout {

    BannerPagerAdapter adapter = null;
    private List<BannerBean> bannerList = null;
    private Context mContext;
    private OnBannerListener listener;
    private List<View> views;
    private List<View> listDots;

    private int count;
    private ViewPager tabBanner;

    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int currentItem = -1;
    private LinearLayout llDots;

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

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    void init() {
        bannerList = new ArrayList<>();
        views = new ArrayList<>();
        listDots = new ArrayList<>();
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_banner_layout, this, true);
        tabBanner = view.findViewById(R.id.tab_banner);
        llDots = view.findViewById(R.id.ll_dots);
//        tabBanner.setAdapter(new BannerPagerAdapter());
    }

    public void setData(List<BannerBean> beanList) {
        this.bannerList.addAll(beanList);
        this.count = beanList.size();
        if (adapter == null) {
            adapter = new BannerPagerAdapter(beanList);
            tabBanner.setAdapter(adapter);
            tabBanner.setCurrentItem(1);
            tabBanner.addOnPageChangeListener(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

    }

    public void setOnBannerListener(OnBannerListener listener) {
        this.listener = listener;
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

    class BannerPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {


        List<BannerBean> beanList;

        public BannerPagerAdapter(List<BannerBean> beanList) {
            this.beanList = beanList;
            //        如果数据大于一条
            if (beanList.size() > 1) {
//            添加最后一页到第一页
                beanList.add(0, beanList.get(beanList.size() - 1));
//            添加第一页(经过上行的添加已经是第二页了)到最后一页
                beanList.add(beanList.get(1));
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
                views.add(view);
            }

            for (BannerBean bean : bannerList) {
                ImageView ivDot = new ImageView(mContext);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                ivDot.setPadding(5, 0, 5, 0);
                ivDot.setImageResource(R.drawable.img_dots_bg);
                layoutParams.gravity = Gravity.CENTER;
                ivDot.setLayoutParams(layoutParams);
                llDots.addView(ivDot);
                if (listDots.size() == 0)
                    ivDot.setSelected(true);
                listDots.add(ivDot);
            }
        }

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
            container.addView(views.get(position));
            View view = views.get(position);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrollStateChanged(state);
            }

            //        若viewpager滑动未停止，直接返回
            if (state != ViewPager.SCROLL_STATE_IDLE) return;
//        若当前为第一张，设置页面为倒数第二张
            if (currentItem == 0) {
                tabBanner.setCurrentItem(views.size() - 2, false);
            } else if (currentItem == views.size() - 1) {
//        若当前为倒数第一张，设置页面为第二张
                tabBanner.setCurrentItem(1, false);
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
            for (int i = 0; i < listDots.size(); i++) {
                if (i == toRealPosition(position)) {
                    listDots.get(i).setSelected(true);
                } else {
                    listDots.get(i).setSelected(false);
                }
            }
        }
    }

}
