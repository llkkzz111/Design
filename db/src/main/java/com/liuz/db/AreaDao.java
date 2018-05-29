/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liuz.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Data Access Object for the users table.
 */
@Dao
public interface AreaDao {

    /**
     * Get the user from the table. Since for simplicity we only have one user in the database,
     * this query gets all users from the table, but limits the result to just the 1st user.
     *
     * @return the user from the table
     */
    @Query("SELECT * FROM areas")
    List<AreaBean> getAreas();

    /**
     * Insert a user in the database. If the user already exists, replace it.
     *
     * @param area the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(AreaBean area);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AreaBean> areas);


    @Query("DELETE FROM areas")
    void deleteAllAreas();

    @Query("SELECT * FROM areas order by  count desc LIMIT 12")
    List<AreaBean> getAreas12();

    @Query("SELECT  * FROM areas  WHERE pinyinfull LIKE :letter|| '%' ORDER BY pinyinfull")
    List<AreaBean> getAreasDes(String letter);

}
