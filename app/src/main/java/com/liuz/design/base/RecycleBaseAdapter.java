package com.liuz.design.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * date: 2018/6/14 17:49
 * author liuzhao
 */
public abstract class RecycleBaseAdapter<T, K extends BaseViewHolder> extends RecyclerView.Adapter<K> {
    public Context mContext;
    View item;
    protected List<T> list;



    protected RecycleBaseAdapter() {
    }


    @NonNull
    @Override
    public K onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        item = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        K holder = getHolder(item, parent, viewType);
        return holder;
    }

    /**
     * 返回布局layout
     *
     * @param position 列表位置
     * @return 布局Layout ID
     */
    public abstract int getLayoutRes(int position);


    public abstract K getHolder(View item, ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull K holder, int position) {

        T data = list.get(position);
        int viewType = getLayoutRes(position);
        convert(holder, data, position, viewType);
    }


    /**
     * 在这里设置显示
     *
     * @param holder 默认的ViewHolder
     * @param data   对应的数据
     * @param index  对应的列表位置（不一定是数据在数据集合List中的位置）
     */
    public abstract void convert(K holder, T data, int index, int type);


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutRes(position);
    }

    /**
     * 设置新数据，会清除掉原有数据，并有可能重置加载更多状态
     *
     * @param data 数据集合
     */
    public void setData(@Nullable List<T> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
            notifyDataSetChanged();
        }
    }

}
