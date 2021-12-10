package com.zz.fundapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zz.fundapp.bean.FundHistoryDay;

import java.util.List;

@Dao
public interface FundHistoryDao {
    @Query("SELECT * FROM f_history_day")
    List<FundHistoryDay> getAllHistory();

    @Query("SELECT * FROM f_history_day WHERE gztime like'%' || :gztime || '%'")
    List<FundHistoryDay> getAllHistoryByDate(String gztime);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FundHistoryDay... fundHistoryDays);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<FundHistoryDay> fundHistoryDays);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FundHistoryDay fundHistoryDays);

    @Update
    void update(FundHistoryDay... funds);

    @Delete
    void delete(FundHistoryDay... funds);
}
