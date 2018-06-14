package com.liuz.design.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liuz.design.R;
import com.liuz.design.base.RecycleBaseAdapter;
import com.liuz.design.bean.MoviesBean;
import com.liuz.design.ui.adapter.holder.HotHolder;

import java.util.List;

import butterknife.BindView;

/**
 * date: 2018/6/14 18:09
 * author liuzhao
 */
public class HotMovAdapter extends RecycleBaseAdapter<MoviesBean, HotHolder> {



    public HotMovAdapter(Context mContext, List<MoviesBean> list) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getLayoutRes(int position) {
        return R.layout.item_hot_movies_layout;
    }

    @Override
    public HotHolder getHolder(View item, ViewGroup parent, int viewType) {
        return new HotHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false));
    }

    @Override
    public void convert(HotHolder holder, MoviesBean data, int index, int type) {
        holder.onBind(index, data);
    }

}
