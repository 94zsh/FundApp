package com.zz.fundapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zz.fundapp.bean.Fund;

import java.util.List;

@Dao
public interface FundDao {
    @Query("SELECT * FROM f_list")
    List<Fund> getAllFunds();

    @Query("SELECT * FROM f_list WHERE code=:code")
    Fund getFund(int code);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Fund... funds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Fund> fundList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Fund funds);

    @Update
    void update(Fund... funds);

    @Delete
    void delete(Fund... funds);
}
