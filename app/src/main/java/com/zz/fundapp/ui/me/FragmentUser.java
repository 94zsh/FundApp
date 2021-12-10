package com.zz.fundapp.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zz.fundapp.base.BaseFragment;
import com.zz.fundapp.databinding.FragmentUserBinding;
import com.zz.fundapp.http.DataRequestHelper;
import com.zz.fundapp.ui.view.DayHistoryActivity;

public class FragmentUser extends BaseFragment implements View.OnClickListener {
    private FragmentUserBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        initView();
        initData();
        return binding.getRoot();
    }


    @Override
    public void initView() {
        binding.btList.setOnClickListener(this);
        binding.btFocusList.setOnClickListener(this);
        binding.btHistoryList.setOnClickListener(this);
        binding.btTest.setOnClickListener(this);
        binding.btDayHistory.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == binding.btTest.getId()){
            DataRequestHelper.getInstance(getActivity()).getWeather("18316021694");
        }
        else if(v.getId() == binding.btList.getId()){
            startActivity(new Intent(getActivity(),FundListActivity.class));
        }else if(v.getId() == binding.btFocusList.getId()){
            startActivity(new Intent(getActivity(),FocusListActivity.class));
        }else if(v.getId() == binding.btHistoryList.getId()){
            startActivity(new Intent(getActivity(),HistoryListActivity.class));
        }else if(v.getId() == binding.btDayHistory.getId()){
            startActivity(new Intent(getActivity(), DayHistoryActivity.class));
        }
    }
}
