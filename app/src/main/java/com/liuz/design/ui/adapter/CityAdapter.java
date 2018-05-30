package com.liuz.design.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<AreaBean> areaList;
    private LayoutInflater mInflater;

    public CityAdapter(Context mContext, List<AreaBean> areaList) {
        this.areaList = areaList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.item_areas_city_layout, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AreaBean areaBean = areaList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvCity.setText(areaBean.getN());
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_city) TextView tvCity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
