package com.zz.fundapp.http;


import com.zz.fundapp.http.builder.GetBuilder;
import com.zz.fundapp.http.builder.PostBuilder;

import okhttp3.OkHttpClient;


public class HttpUtils {

    public static String TAG = "HTTP";

    private static HttpUtils instance = new HttpUtils();

    private HttpUtils() {
    }

    public static HttpUtils getInstance() {
        return instance;
    }

    private OkHttpClient mOkHttpClient;

    public OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        }
        return mOkHttpClient;
    }

    public static PostBuilder post() {
        return new PostBuilder();
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }
}
