package com.zz.fundapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.zz.fundapp.bean.Fund;
import com.zz.fundapp.bean.FundFocus;
import com.zz.fundapp.bean.FundHistoryDay;

@Database(entities = {Fund.class, FundFocus.class, FundHistoryDay.class},version = 4,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase INSTANCE;

    // 因为需要一个Context，所有我们得传来一个Context
    public static synchronized AppDataBase getInstance(Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "app_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()////数据库更新时删除数据重新创建
//                    .addMigrations(MIGRATION_1_2)//指定版本1-2升级时的升级策略
                    .build();
        }
        return INSTANCE;
    }

    public abstract FundDao getFundDao();
    public abstract FundFocusDao getFundFocusDao();
    public abstract FundHistoryDao getFundHistoryDao();

    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //将数据表device创建出来
//            database.execSQL("CREATE TABLE 'device' ('id'  TEXT NOT NULL,'location' TEXT,'deviceName' TEXT,'deviceType' TEXT,PRIMARY KEY ('id')) ");
        }
    };
}
