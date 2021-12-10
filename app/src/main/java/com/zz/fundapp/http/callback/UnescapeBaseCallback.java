package com.zz.fundapp.http.callback;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zz.fundapp.HyLog;
import com.zz.fundapp.http.HttpUtils;
import com.zz.fundapp.http.result.ObjectResult;
import com.zz.fundapp.http.result.Result;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * @author Administrator
 * @time 2017/3/30 0:45
 * @des ${TODO}
 */

public abstract class UnescapeBaseCallback<T> implements Callback {
    // private Gson mGson;
    private Class<T> mClazz;
    private Handler mDelivery;

    public UnescapeBaseCallback(Class<T> clazz) {
       /* mType = getSuperclassTypeParameter(getClass());
        mGson = new Gson();*/
        mClazz = clazz;
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public abstract void onResponse(ObjectResult<T> result);

    public abstract void onError(Call call, Exception e);

    @Override
    public void onFailure(Call call, IOException e) {
        HyLog.e(HttpUtils.TAG, "服务器请求失败：" + e.toString());
        errorData(call, e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.code() == 200) {
            try {
                ObjectResult<T> result = new ObjectResult<T>();
                String body = response.body().string();
                HyLog.e(HttpUtils.TAG, "服务器数据包：" + body);

//                byte[] bytes = body.getBytes();
//                byte[] decodeBytes = Base64.decodeBase64(bytes);
//                byte[] unGizpBytes = CompressUtil.unGZip(decodeBytes);
//                String result_app = StringEscapeUtils.unescapeJavaScript(new String(unGizpBytes));
//                Log.i(HttpUtils.TAG, "服务器数据包 解密后：" + result_app);
//
//                JSONObject jsonObject = JSON.parseObject(result_app);
                //不解压的服务器返回数据
                JSONObject jsonObject = JSON.parseObject(body);
                Log.i(HttpUtils.TAG, "服务器数据包 解密后：" + body);

                result.setResultCode(jsonObject.getIntValue(Result.RESULT_CODE));
                result.setResultMsg(jsonObject.getString(Result.RESULT_MSG));

                if (!mClazz.equals(Void.class)) {
                    String data = jsonObject.getString(Result.DATA);
                    if (!TextUtils.isEmpty(data)) {
                        if (mClazz.equals(String.class) || mClazz.getSuperclass().equals(Number.class)) {// String
                            // 类型或者基本数据类型（Integer）
                            result.setData(castValue(mClazz, data));
                        } else {
                            result.setData(JSON.parseObject(data, mClazz));
                        }
                    }
                }
                successData(result);
            } catch (Exception e) {
                HyLog.e(HttpUtils.TAG, "数据解析异常:" + e.getMessage());
                errorData(call, new Exception("数据解析异常"));
            }
        } else {
            HyLog.e(HttpUtils.TAG, "服务器请求异常" + response.toString());
            errorData(call, new Exception("服务器请求异常" + response.code()));
        }
    }

    protected void successData(final ObjectResult<T> data) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                /*if (data.getResultCode() == 1030101 || data.getResultCode() == 1030102) {
                    // 缺少访问令牌 || 访问令牌过期或无效
                    // com.sk.weichat.ui.SplashActivity.jump()方法里会判断该变量然后跳到登录，
                    // TODO: 使用过程中accessToken突然失效的情况无法解决，
//                    MyApplication.getInstance().mUserStatus = STATUS_USER_TOKEN_OVERDUE;
                }*/
                try {
                    onResponse(data);
                }catch (Exception e){
                    e.printStackTrace();
                    HyLog.e(HttpUtils.TAG," onResponse ERROR : " + e);
                }

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

    private T castValue(Class<T> clazz, String data) {
        try {
            Constructor<T> constructor = clazz.getConstructor(String.class);
            return constructor.newInstance(data);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
