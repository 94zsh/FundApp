package com.zz.fundapp.ui.me;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.zz.fundapp.HyLog;
import com.zz.fundapp.R;
import com.zz.fundapp.base.BaseActivity;
import com.zz.fundapp.bean.Fund;
import com.zz.fundapp.database.AppDataBase;
import com.zz.fundapp.databinding.ActivityFundSearchBinding;
import com.zz.fundapp.ui.adapter.BaseRecyclerAdapter;
import com.zz.fundapp.ui.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FundSearchActivity extends BaseActivity<ActivityFundSearchBinding> implements View.OnClickListener {
    List<Fund> fundList = new ArrayList<>();
    List<Fund> searchList = new ArrayList<>();
    private BaseRecyclerAdapter<Fund> adapter;

    @Override
    protected void initView() {
        binding = ActivityFundSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.tvCancel.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        fundList.clear();
        searchList.clear();
        fundList = AppDataBase.getInstance(FundSearchActivity.this).getFundDao().getAllFunds();
        searchList.addAll(fundList);
        binding.rvList.setLayoutManager(new LinearLayoutManager(FundSearchActivity.this,LinearLayoutManager.VERTICAL,false));
        adapter = new BaseRecyclerAdapter<Fund>(FundSearchActivity.this, R.layout.item_fund_list,searchList) {
            @Override
            public void convert(BaseViewHolder holder, Fund fund, int position) {
                holder.setText(R.id.tv0,fund.getCnName())
                        .setText(R.id.tv1,fund.getCode())
                        .setText(R.id.stv_zzl,fund.getType());
            }
        };
        binding.rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Fund selectFund = searchList.get(position);
                setResult(RESULT_OK,new Intent().putExtra("code",selectFund.getCode()));
                finish();
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                HyLog.e("onTextChanged :" + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                HyLog.e("afterTextChanged :" + s);
                if(s == null || s.length() <= 0){
                    initData();
                }else{
                    searchList.clear();
                    for (Fund fund : fundList) {
                        if(s.length() > 0 && (fund.getCnName().toLowerCase().contains(s.toString().toLowerCase()) || fund.getCnName().toUpperCase().contains(s.toString().toUpperCase()))){
                            searchList.add(fund);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == binding.tvCancel.getId()){
            finish();
        }
    }
}