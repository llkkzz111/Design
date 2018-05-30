package com.liuz.design.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.liuz.db.AreaBean;
import com.liuz.design.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date: 2018/5/30 12:00
 * author liuzhao
 */
public class AreasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<List<AreaBean>> areaList;
    private LayoutInflater mInflater;

    public AreasAdapter(Context mContext, List<List<AreaBean>> areaList) {
        this.areaList = areaList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.item_areas_layout, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        List<AreaBean> list = areaList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        if (position == 0) {
            viewHolder.tvLetter.setText("热门");
        } else {
            viewHolder.tvLetter.setText(list.get(0).getPinyinShort().substring(0, 1).toUpperCase());
        }

        viewHolder.rvCity.setLayoutManager(new GridLayoutManager(mContext, 4));
        viewHolder.rvCity.setAdapter(new CityAdapter(mContext, list));

    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_letter) TextView tvLetter;
        @BindView(R.id.rv_city) RecyclerView rvCity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
