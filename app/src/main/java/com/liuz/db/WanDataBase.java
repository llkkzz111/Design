package com.liuz.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.liuz.db.wan.AccountBean;
import com.liuz.db.wan.AccountDao;

/**
 * date: 2018/7/3 16:06
 * author liuzhao
 */

@Database(entities = {AccountBean.class}, version = 1)
public abstract class WanDataBase extends RoomDatabase {

    private static volatile WanDataBase INSTANCE;

    public static WanDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AreasDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WanDataBase.class, "wandroid.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract AccountDao accountDao();


}
