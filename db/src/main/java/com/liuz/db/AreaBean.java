package com.liuz.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * date: 2018/5/29 12:18
 * author liuzhao
 */
@Entity(tableName = "areas")
public class AreaBean {
    /**
     * id : 290
     * n : 北京
     * count : 120
     * pinyinShort : bj
     * pinyinFull : Beijing
     */
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "n")
    private String n;

    @ColumnInfo(name = "count")
    private int count;

    @ColumnInfo(name = "pinyinShort")
    private String pinyinShort;

    @ColumnInfo(name = "pinyinFull")
    private String pinyinFull;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPinyinShort() {
        return pinyinShort;
    }

    public void setPinyinShort(String pinyinShort) {
        this.pinyinShort = pinyinShort;
    }

    public String getPinyinFull() {
        return pinyinFull;
    }

    public void setPinyinFull(String pinyinFull) {
        this.pinyinFull = pinyinFull;
    }
}
