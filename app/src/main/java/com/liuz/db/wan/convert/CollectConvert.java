package com.liuz.db.wan.convert;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * date: 2018/7/3 16:30
 * author liuzhao
 */
public class CollectConvert {

    @TypeConverter
    public List<Integer> fromString(String value) {
        List<Integer> mList = new Gson().fromJson(value, new TypeToken<List<Integer>>() {
        }.getType());
        return mList;
    }

    @TypeConverter
    public String fromList(List<Integer> date) {
        if (date == null) {
            return null;
        } else {
            return new Gson().toJson(date);
        }
    }
}
