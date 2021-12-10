package com.zz.fundapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zz.fundapp.base.BaseFragment;
import com.zz.fundapp.bean.ScoreEntity;
import com.zz.fundapp.databinding.FragmentHomeBinding;
import com.zz.fundapp.ui.adapter.BothwayListViewAdapter;
import com.zz.fundapp.ui.adapter.TableListAdapter;
import com.zz.fundapp.ui.view.BothwayListView;
import com.zz.fundapp.ui.view.HeaderHorizontalScrollView;

import java.util.ArrayList;
import java.util.Random;

public class FragmentHome extends BaseFragment {
    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initView();
        initData();
        return binding.getRoot();
    }


    private View mTopParentLinearLayout;
    private HeaderHorizontalScrollView mTopScrollView;
    private BothwayListView mBothwayListView;
    private BothwayListViewAdapter mBothwayListViewAdapter;
    @Override
    public void initView() {
        mTopParentLinearLayout = binding.topHeader.getRoot();
        mTopScrollView = binding.topHeader.myScrollview;
        mBothwayListView = binding.listView;

        ArrayList<ScoreEntity> scores = new ArrayList<>();

        Random random = new Random();

        for (int i = 0;i<5;i++){
            scores.add(new ScoreEntity("小明" + i,"1023" + i ,
                    random.nextInt(100),random.nextInt(100),random.nextInt(100),
                    random.nextInt(100),random.nextInt(100),random.nextInt(100)));
        }

        mBothwayListView.initListView(mTopParentLinearLayout, mTopScrollView);
        mBothwayListViewAdapter = new TableListAdapter(scores,
                mTopScrollView);
        mBothwayListView.setAdapter(mBothwayListViewAdapter);
    }

    @Override
    public void initData() {
    }
    @Override
    public void onDestroy() {
        if (null != mBothwayListView) {
            mBothwayListView.unRegistererReceiver();
        }
        super.onDestroy();
    }

}
