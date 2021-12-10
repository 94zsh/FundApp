package com.zz.fundapp.http;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.zz.fundapp.HyLog;
import com.zz.fundapp.base.MyApplication;
import com.zz.fundapp.bean.Fund;
import com.zz.fundapp.bean.FundFocus;
import com.zz.fundapp.bean.FundHistoryDay;
import com.zz.fundapp.http.callback.BaseCallback;
import com.zz.fundapp.http.result.ObjectResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;

public class DataRequestHelper {
    private String TAG = "DataRequestHelper";
    private Context mContext;
    private static DataRequestHelper instance;

    //http://192.168.74.2:8888/app/getData
    public static final String BASE_ADDRESS_DEBUG = "http://192.168.8.231:8888/app";//debug service
    public static final String BASE_ADDRESS_RELEASE = "http://106.52.132.186:8888/app";//正式服务器https
    public static final String GET_WEATHER = "/getData"; //测试接口
    public static final String GET_FUND_LIST = "/getFundList"; //获取列表
    public static final String GET_FOCUS_LIST = "/getFocusList"; //获取关注列表
    public static final String GET_HISTORY_LIST = "/getHistoryDayList"; //获取历史列表
    public static final String ADD_FOCUS = "/addFocus"; //加自选
    public static final String DELETE_FOCUS = "/deleteFocus"; //删除自选

    public static String getServerChannel() {
//        MyApplication.VISION = MyApplication.DEBUG_VISION;//test

        String serverUrl = BASE_ADDRESS_RELEASE;
        if (MyApplication.VISION == MyApplication.DEBUG_VISION) {
            serverUrl = BASE_ADDRESS_DEBUG;
        }
        return serverUrl;
    }

    private DataRequestHelper(){

    }
    private DataRequestHelper(Context context) {
        this.mContext = context;
    }
    public static DataRequestHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DataRequestHelper.class) {
                if (instance == null) {
                    instance = new DataRequestHelper(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void getWeather(String registerAccount){
        String url = getServerChannel() + GET_WEATHER;
        Map<String, Object> params = new HashMap<>();
        params.put("username", registerAccount);
        HttpUtils.post().url(url)
                .params(params,mContext)
                .build(false)
                .execute(new BaseCallback<String>(String.class) {
                    @Override
                    public void onResponse(ObjectResult<String> result) {
                        if(result != null){
                            HyLog.e(TAG,"onResponse register detail result :" + result.toString());
                            if(result.getResultCode() == 1){
                                //请求成功
                                return;
                            }else{
                                //注册失败
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        HyLog.e(TAG,"onResponse upLoadStep detail result :" + e.toString());
                    }
                });
    }


    public void getFundList(String registerAccount){
        String url = getServerChannel() + GET_FUND_LIST;
        Map<String, Object> params = new HashMap<>();
        params.put("account", registerAccount);
        HttpUtils.get().url(url)
                .params(params,mContext)
                .build(true)
                .execute(new BaseCallback<String>(String.class) {
                    @Override
                    public void onResponse(ObjectResult<String> result) {
                        if(result != null){
                            HyLog.e(TAG,"onResponse register detail result :" + result.toString());
                            if(result.getResultCode() == 1){
                                //请求成功
                                String data = result.getData();
                                if(data != null && data.length() > 2) {
                                    List<Fund> fundList = new ArrayList<Fund>(JSONArray.parseArray(data, Fund.class));
                                }
                            }else{
                                //请求失败
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        HyLog.e(TAG,"onResponse upLoadStep detail result :" + e.toString());
                    }
                });
    }

    public Observable<List<FundFocus>> getFocusList(String account){
        //创建被观察者
        return Observable.create(new ObservableOnSubscribe<List<FundFocus>>() {
            @Override
            public void subscribe(ObservableEmitter<List<FundFocus>> emitter) throws Exception {
                String url = getServerChannel() + GET_FOCUS_LIST;
                Map<String, Object> params = new HashMap<>();
                params.put("account", account);
                HttpUtils.post().url(url)
                        .params(params,mContext)
                        .build(false)
                        .execute(new BaseCallback<String>(String.class) {
                            @Override
                            public void onResponse(ObjectResult<String> result) {
                                if(result != null){
                                    HyLog.e(TAG,"onResponse getFocusList result :" + result.toString());
                                    if(result.getResultCode() == 1){
                                        //请求成功
                                        String data = result.getData();
                                        if(data != null && data.length() > 2) {
                                            List<FundFocus> focusList = new ArrayList<FundFocus>(JSONArray.parseArray(data, FundFocus.class));
                                            emitter.onNext(focusList);
                                        }
                                    }else{
                                        //请求失败
                                    }
                                }
                                emitter.onComplete();
                            }

                            @Override
                            public void onError(Call call, Exception e) {
                                HyLog.e(TAG,"onResponse getFocusList error :" + e.toString());
                                emitter.onError(e);
                            }
                        });
            }
        });
    }

    public Observable<List<FundHistoryDay>> getHistoryList(String account,long gztime){
        //创建被观察者
        return Observable.create(new ObservableOnSubscribe<List<FundHistoryDay>>() {
            @Override
            public void subscribe(ObservableEmitter<List<FundHistoryDay>> emitter) throws Exception {
                String url = getServerChannel() + GET_HISTORY_LIST;
                Map<String, Object> params = new HashMap<>();
                params.put("account", account);
                params.put("gztime", gztime);
                HttpUtils.post().url(url)
                        .params(params,mContext)
                        .build(false)
                        .execute(new BaseCallback<String>(String.class) {
                            @Override
                            public void onResponse(ObjectResult<String> result) {
                                if(result != null){
                                    HyLog.e(TAG,"onResponse getHistoryList result :" + result.toString());
                                    if(result.getResultCode() == 1){
                                        //请求成功
                                        String data = result.getData();
                                        if(data != null && data.length() > 2) {
                                            List<FundHistoryDay> historyDayList = new ArrayList<FundHistoryDay>(JSONArray.parseArray(data, FundHistoryDay.class));
                                            emitter.onNext(historyDayList);
                                        }else{
                                            emitter.onError(new Throwable(result.getResultMsg()));
                                            return;
                                        }
                                    }else{
                                        //请求失败
                                        emitter.onError(new Throwable(result.getResultMsg()));
                                        return;
                                    }
                                }
                                emitter.onComplete();
                            }

                            @Override
                            public void onError(Call call, Exception e) {
                                HyLog.e(TAG,"onResponse getHistoryList error :" + e.toString());
                                emitter.onError(e);
                            }
                        });
            }
        });
    }

    public Observable<String> addFocus(String account,String code){
        //创建被观察者
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String url = getServerChannel() + ADD_FOCUS;
                Map<String, Object> params = new HashMap<>();
                params.put("account", account);
                params.put("code", code);
                HttpUtils.post().url(url)
                        .params(params,mContext)
                        .build(false)
                        .execute(new BaseCallback<String>(String.class) {
                            @Override
                            public void onResponse(ObjectResult<String> result) {
                                if(result != null){
                                    HyLog.e(TAG,"onResponse addFocus result :" + result.toString());
                                    if(result.getResultCode() == 1){
                                        //请求成功
                                        String data = result.getData();
                                        emitter.onComplete();
                                    }else{
                                        //请求失败
                                        emitter.onError(new Throwable(result.getResultMsg()));
                                    }
                                }
                            }

                            @Override
                            public void onError(Call call, Exception e) {
                                HyLog.e(TAG,"onResponse addFocus error :" + e.toString());
                                emitter.onError(e);
                            }
                        });
            }
        });

    }
    public Observable<String> deleteFocus(String account,String code){

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String url = getServerChannel() + DELETE_FOCUS;
                Map<String, Object> params = new HashMap<>();
                params.put("account", account);
                params.put("code", code);
                HttpUtils.post().url(url)
                        .params(params,mContext)
                        .build(false)
                        .execute(new BaseCallback<String>(String.class) {
                            @Override
                            public void onResponse(ObjectResult<String> result) {
                                if(result != null){
                                    HyLog.e(TAG,"onResponse deleteFocus result :" + result.toString());
                                    if(result.getResultCode() == 1){
                                        //请求成功
                                        String data = result.getData();
                                        emitter.onComplete();
                                    }else{
                                        //请求失败
                                        emitter.onError(new Throwable(result.getResultMsg()));
                                    }
                                }
                            }

                            @Override
                            public void onError(Call call, Exception e) {
                                HyLog.e(TAG,"onResponse deleteFocus error :" + e.toString());
                                emitter.onError(e);
                            }
                        });
            }
        });

    }
}
