package com.zz.fundapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zz.fundapp.bean.FundFocus;

import java.util.List;

@Dao
public interface FundFocusDao {
    @Query("SELECT * FROM f_focus")
    List<FundFocus> getAllFocus();

    @Query("SELECT * FROM f_focus WHERE code=:code")
    FundFocus getFundFocus(int code);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FundFocus... funds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<FundFocus> fundList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FundFocus funds);

    @Update
    void update(FundFocus... funds);

    @Delete
    void delete(FundFocus... funds);
}
