package com.zz.fundapp.ui.me;

import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONArray;
import com.zz.fundapp.HyLog;
import com.zz.fundapp.R;
import com.zz.fundapp.base.BaseActivity;
import com.zz.fundapp.bean.Fund;
import com.zz.fundapp.database.AppDataBase;
import com.zz.fundapp.databinding.ActivityFundListBinding;
import com.zz.fundapp.http.DataRequestHelper;
import com.zz.fundapp.http.HttpUtils;
import com.zz.fundapp.http.callback.BaseCallback;
import com.zz.fundapp.http.result.ObjectResult;
import com.zz.fundapp.ui.adapter.BaseRecyclerAdapter;
import com.zz.fundapp.ui.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;

public class FundListActivity extends BaseActivity<ActivityFundListBinding> implements View.OnClickListener {
    private BaseRecyclerAdapter<Fund> adapter;

    @Override
    protected void initView() {
        binding = ActivityFundListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btRefresh.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        List<Fund> fundList = AppDataBase.getInstance(FundListActivity.this).getFundDao().getAllFunds();
        HyLog.e("数据库 size ：" + fundList.size());

        binding.tvSize.setText("数量：" + fundList.size());
        binding.rvList.setLayoutManager(new LinearLayoutManager(FundListActivity.this,LinearLayoutManager.VERTICAL,false));
        adapter = new BaseRecyclerAdapter<Fund>(FundListActivity.this,R.layout.item_fund_list,fundList) {
            @Override
            public void convert(BaseViewHolder holder, Fund fund, int position) {
                    holder.setText(R.id.tv0,fund.getCnName())
                            .setText(R.id.tv1,fund.getCode())
                            .setText(R.id.stv_zzl,fund.getType());
            }
        };
        binding.rvList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == binding.btRefresh.getId()){
            Observable.create(new ObservableOnSubscribe<List<Fund>>() {
                @Override
                public void subscribe(ObservableEmitter<List<Fund>> emitter) throws Exception {
                    String url = DataRequestHelper.getServerChannel() + DataRequestHelper.GET_FUND_LIST;
                    Map<String, Object> params = new HashMap<>();
                    params.put("username", "");
                    HttpUtils.get().url(url)
                            .params(params,FundListActivity.this)
                            .build(true)
                            .execute(new BaseCallback<String>(String.class) {
                                @Override
                                public void onResponse(ObjectResult<String> result) {
                                    if(result != null){
                                        HyLog.e("onResponse register detail result :" + result.toString());
                                        if(result.getResultCode() == 1){
                                            //请求成功
                                            String data = result.getData();
                                            if(data != null && data.length() > 2) {
                                                List<Fund> fundList = new ArrayList<Fund>(JSONArray.parseArray(data, Fund.class));
                                                emitter.onNext(fundList);
                                                emitter.onComplete();
                                                return;
                                            }
                                        }else{
                                            //请求失败
                                        }
                                    }
                                    emitter.onComplete();
                                }

                                @Override
                                public void onError(Call call, Exception e) {
                                    HyLog.e("onResponse upLoadStep detail result :" + e.toString());
                                    emitter.onComplete();
                                }
                            });
                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Fund>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            HyLog.e("onSubscribe----");
                        }

                        @Override
                        public void onNext(List<Fund> fundList) {
                            HyLog.e("onNext---- : " + fundList);
                            HyLog.e("数据 size ：" + fundList.size());
//                        for (int i = 0; i < fundList.size(); i++) {
//                            HyLog.e("fund detail : " + fundList.get(i).toString());
//                        }
                            AppDataBase.getInstance(FundListActivity.this).getFundDao().insert(fundList);
                            HyLog.e("TAG", "插入数据");
                        }

                        @Override
                        public void onError(Throwable e) {
                            HyLog.e("onError----" + e);
                            Toast.makeText(FundListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            HyLog.e("onComplete----  ");
                            initData();
                        }
                    });
        }
    }
}