package com.zz.fundapp.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.multidex.MultiDex;

import com.zz.fundapp.HyLog;


/**
 * Created by corn on 2016/11/30.
 */
public class MyApplication extends Application {
    private static MyApplication myApplication = null;
    public static MyApplication getInstance() {
        return myApplication;
    }

    public final static int DEBUG_VISION = 0;//debug local test version 内部测试服务器版本
    public final static int RELEASE_VISION = 1;//上架版本
    public static int VISION = RELEASE_VISION;


    @Override
    public void onCreate() {
        // 程序创建的时候执行
        super.onCreate();
        myApplication = this;
        HyLog.e("手机型号:" + Build.MODEL);
        try {
            String str = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            HyLog.e("应用版本号:" + str);
        } catch (Exception var3) {
        }
        MultiDex.install(this);
        registerLifecycle(this);

    }


    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        HyLog.d("MyApplication onTerminate");
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        HyLog.d("MyApplication onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        HyLog.d("MyApplication onTrimMemory 程序清理内存时执行 level = " + level);

        HyLog.e("onTrimMemory ");

        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        HyLog.d("MyApplication onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    /**
     * 活跃Activity的数量
     */
    private int mActiveActivityCount = 0;

    /**
     * 注册生命周期
     */
    private void registerLifecycle(Context context) {
        Application application = (Application) context.getApplicationContext();
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (mActiveActivityCount == 0) { //后台切换到前台
                    HyLog.e("后台切换到前台 " );
                }
                mActiveActivityCount++;
//                HyLog.e("活跃的activity数量：" + mActiveActivityCount);
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                mActiveActivityCount--;
//                HyLog.e("活跃的activity数量 =" + mActiveActivityCount);
                if (mActiveActivityCount == 0) { //前台切换到后台
                     HyLog.e("前台切换到后台 " );
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public boolean isBackground() {
        if (mActiveActivityCount <= 0) {
//            HyLog.e("启动前台服务 " );
            return true;
        } else {
//            HyLog.e("启动普通服务 " );
            return false;
        }
    }

}



