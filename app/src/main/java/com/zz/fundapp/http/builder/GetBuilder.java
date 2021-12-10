package com.zz.fundapp.http.builder;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.zz.fundapp.HyLog;
import com.zz.fundapp.http.HttpUtils;

import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * @author Administrator
 * @time 2017/3/30 0:11
 * @des ${TODO}
 */

public class GetBuilder extends BaseBuilder {

    @Override
    public GetBuilder url(String url) {
        if (!TextUtils.isEmpty(url)) {
            this.url = url;
        }
//        addSecret();
        return this;
    }

    /**
     * 给所有接口调添加secret,
     */
//    private GetBuilder addSecret() {
//        if (url.contains("config")
//                || url.equals("https://ipinfo.io/geo")) {// 获取配置的接口，不验证
//            return this;
//        }
//
//        AppConfig mConfig = CoreManager.requireConfig(MyApplication.getInstance());
//        // 所有接口都需要time与secret参数
//        String time = String.valueOf(TimeUtils.sk_time_current_time());
//        String secret;
//        String mAccessToken = CoreManager.requireSelfStatus(MyApplication.getInstance()).accessToken;
//        if (url.equals(mConfig.VX_RECHARGE)
//                || url.equals(mConfig.REDPACKET_OPEN)) {
//            // 微信支付 领红包调用的接口
//            String mLoginUserId = CoreManager.requireSelf(MyApplication.getInstance()).getUserId();
//            String step1 = Md5Util.toMD5(AppConfig.apiKey + time);
//            secret = Md5Util.toMD5(step1 + mLoginUserId + mAccessToken);
//        } else if (url.equals(mConfig.USER_LOGIN)
//                || url.equals(mConfig.USER_REGISTER) || url.equals(mConfig.USER_PASSWORD_RESET)
//                || url.equals(mConfig.VERIFY_TELEPHONE) || url.equals(mConfig.USER_GETCODE_IMAGE)
//                || url.equals(mConfig.SEND_AUTH_CODE)) {
//            // 未登录之前调用的接口
//            secret = Md5Util.toMD5(AppConfig.apiKey + time);
//        } else {
//            // 其他接口
//            String mLoginUserId = CoreManager.requireSelf(MyApplication.getInstance()).getUserId();
//            secret = Md5Util.toMD5(AppConfig.apiKey + time + mLoginUserId + mAccessToken);
//        }
//
//        params("time", time);
//        params("secret", secret);
//
//        return this;
//    }

//    /**
//     * 给需要支付密码的接口调添加secret,
//     *
//     * @param payPassword 支付密码，
//     */
//    public GetBuilder addSecret(String payPassword) {
//        AppConfig mConfig = CoreManager.requireConfig(MyApplication.getInstance());
//
//
//        // 所有接口都需要time与secret参数
//        String time = String.valueOf(TimeUtils.sk_time_current_time());
//        String secret;
//        String mAccessToken = CoreManager.requireSelfStatus(MyApplication.getInstance()).accessToken;
//        if (url.equals(mConfig.REDPACKET_SEND)) {
//            // 发红包调用的接口
//            String mLoginUserId = CoreManager.requireSelf(MyApplication.getInstance()).getUserId();
//            String step1 = Md5Util.toMD5(AppConfig.apiKey + time);
//            String step2 = Md5Util.toMD5(payPassword);
//            secret = Md5Util.toMD5(step1 + mLoginUserId + mAccessToken + step2);
//            Log.d(HttpUtils.TAG, String.format(Locale.CHINA, "addSecret: md5(md5(%s+%s)+%s+%s+md5(%s)) = %s", AppConfig.apiKey, time, mLoginUserId, mAccessToken, payPassword, secret));
//        } else {
//            // 不走这里，
//            Reporter.getInstance().unreachable();
//            String mLoginUserId = CoreManager.requireSelf(MyApplication.getInstance()).getUserId();
//            secret = Md5Util.toMD5(AppConfig.apiKey + time + mLoginUserId + mAccessToken);
//        }
//        /*
//        提现接口的secret计算在外面，
//        com.sk.weichat.wxapi.WXEntryActivity.transfer
//         */
//        params("time", time);
//        params("secret", secret);
//
//        return this;
//    }

    @Override
    public GetBuilder tag(Object tag) {
        return this;
    }

    public GetCall build(boolean isHeaderJsonType) {
        String token = "";
        try {
            url = appenParams();
            build = new Request.Builder().url(url)
                    .addHeader("vtoken",token)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new GetCall();
    }

    private String appenParams() {
        StringBuffer sb = new StringBuffer();
        sb.append(url);

        if (params != null && !params.isEmpty()) {
            sb.append("?");
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
            sb = sb.deleteCharAt(sb.length() - 1); // 去掉后面的&
            HyLog.e(HttpUtils.TAG, "网络请求参数：" + url + " , params : " + JSONObject.toJSONString(params));
        }
        return sb.toString();
    }

    @Override
    public GetBuilder params(String k, String v) {
        try {
            // url安全，部分字符不能直接放进url, 要改成百分号开头%的，
            v = URLEncoder.encode(v, "UTF-8");
        } catch (Exception e) {
            // 不可到达，UTF-8不可能不支持，
            e.printStackTrace();
        }
        // this.url = this.url+k+"="+v;
        if (params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(k, v);
        return this;
    }
    private Context mcontext;
    public GetBuilder params(Map<String, Object> params,Context context) {
        this.params = params;
        mcontext = context;
        return this;
    }

    public class GetCall extends BaseCall {
        @Override
        public void execute(Callback callback) {
            super.execute(callback);
        }
    }
}
