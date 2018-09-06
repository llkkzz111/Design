package com.liuz.design.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * date: 2018/8/14 15:13
 * author liuzhao
 */
@Table(name = "Items")
public class Items extends Model {
    @Column(name = "Name")
    public String name;

    @Column(name = "Category")
    public Category category;
}