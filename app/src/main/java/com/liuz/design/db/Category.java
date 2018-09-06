package com.liuz.design.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * date: 2018/8/14 15:12
 * author liuzhao
 */
@Table(name = "Categories")
public class Category extends Model {

    public Category(String name) {
        this.name = name;
    }

    @Column(name = "Name")
    public String name;
}
