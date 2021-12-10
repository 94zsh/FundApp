package com.zz.fundapp.http.callback;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.zz.fundapp.http.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author Administrator
 * @time 2017/3/30 0:45
 * @des ${TODO}
 */

public abstract class JsonCallback implements Callback {

    private Handler mDelivery;

    public JsonCallback() {
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public abstract void onResponse(String result);

    public abstract void onError(Call call, Exception e);

    @Override
    public void onFailure(Call call, IOException e) {
        Log.i(HttpUtils.TAG, "服务器请求失败");
        errorData(call, e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.code() == 200) {
            String resultString = response.body().string();
            Log.i(HttpUtils.TAG, "服务器数据包：" + resultString);
            successData(resultString);
        } else {
            Log.i(HttpUtils.TAG, "服务器请求异常");
            errorData(call, new Exception("服务器请求异常"));
        }
    }

    protected void successData(final String data) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                onResponse(data);
            }
        });
    }

    protected void errorData(final Call call, final Exception e) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                onError(call, e);
            }
        });
    }
}
