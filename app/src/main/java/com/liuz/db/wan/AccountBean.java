package com.liuz.db.wan;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.liuz.db.wan.convert.CollectConvert;

import java.util.List;

/**
 * date: 2018/7/3 11:44
 * author liuzhao
 */
@Entity(tableName = "account")
public class AccountBean {

    /**
     * collectIds : [1214,1215,1216]
     * email :
     * icon :
     * id : 3
     * password : 111111
     * type : 0
     * username : 111111
     */
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "icon")
    private String icon;
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "type")
    private int type;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "collectIds")
    @TypeConverters(CollectConvert.class)
    private List<Integer> collectIds;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }
}
