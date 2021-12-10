package com.zz.fundapp.http.builder;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.zz.fundapp.HyLog;
import com.zz.fundapp.http.HttpUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @time 2017/3/30 0:11
 * @des ${TODO}
 */

public class PostBuilder extends BaseBuilder {

    @Override
    public PostBuilder url(String url) {
        if (!TextUtils.isEmpty(url)) {
            this.url = url;
        }
        return this;
    }

    @Override
    public PostBuilder tag(Object tag) {
        return this;
    }

    @Override
    public PostCall build(boolean isHeaderJsonType) {
        String token = "";
        if(isHeaderJsonType){
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, appenParamsJson());
            build = new Request.Builder()
                    .url(url)
                    .addHeader("vtoken",token)
                    .post(body)
                    .build();
//            HyLog.e(HttpUtils.TAG, "Content-Type：" + JSON);
        }else{
            FormBody.Builder builder = appenParams(new FormBody.Builder());
            build = new Request.Builder()
                    .url(url).post(builder.build())
                    .addHeader("vtoken",token)
                    .build();
//            HyLog.e(HttpUtils.TAG, "Content-Type：" + builder.build().contentType());

        }
        return new PostCall();
    }


    private String appenParamsJson() {
        String jsonString = "";
        jsonString = JSONObject.toJSONString(params);
        HyLog.e(HttpUtils.TAG, "网络请求参数 =" + url + " , params : " + jsonString);
//
//        RequestEntity requestEntity = new RequestEntity();
//        requestEntity.setSysdata(RequestEntity.generateSysData(mcontext));
//        requestEntity.getSysdata().setManufCode(manufCode);
//        requestEntity.setRqdata(new String(Base64.encodeToString(CompressUtil.gZip(StringEscapeUtils
//                .escapeJavaScript(jsonString).getBytes()),Base64.DEFAULT)));
//        RequestEntity req = requestEntity;
//
//
//        jsonString = JSON.toJSONString(req);

//        Log.i(HttpUtils.TAG, "网络请求参数 加密后：" + jsonString);

        return  jsonString;
    }
    private FormBody.Builder appenParams(FormBody.Builder builder) {
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        sb.append("?");
        if (params != null) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key)+"");
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        sb = sb.deleteCharAt(sb.length() - 1); // 去掉后面的&

//        HyLog.e(HttpUtils.TAG, "网络请求参数：" + sb.toString());
        HyLog.e(HttpUtils.TAG, "网络请求参数：" + url + " , params : " + JSONObject.toJSONString(params));
        // Log.d(HttpUtils.TAG, "网络请求参数：" + url + "?" + sb.toString());
        return builder;
    }

    @Override
    public PostBuilder params(String k, String v) {
        // this.url = this.url+k+"="+v;
        if (params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(k, v);
        return this;
    }

    private Context mcontext;
    public PostBuilder params(Map<String, Object> params,Context context) {
        this.params = params;
        mcontext = context;
        return this;
    }



    public PostBuilder params(Map<String, Object> params,Context context,String manufCode) {
        this.params = params;
        this.manufCode = manufCode;
        mcontext = context;
        return this;
    }

    public class PostCall extends BaseCall {
        @Override
        public void execute(Callback callback) {
            super.execute(callback);
        }
    }
}
