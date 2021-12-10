package com.zz.fundapp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.zz.fundapp.HyLog;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {
    public T binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        HyLog.e("BaseActivity onCreate");
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected abstract void initView();
    protected abstract void initData();

}
