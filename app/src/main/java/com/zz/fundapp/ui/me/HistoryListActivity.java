package com.zz.fundapp.ui.me;

import static com.zz.fundapp.ui.view.SortTextView.MODE_SORT_ASC;
import static com.zz.fundapp.ui.view.SortTextView.MODE_SORT_DESC;
import static com.zz.fundapp.ui.view.SortTextView.MODE_SORT_NOT;

import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.zz.fundapp.HyLog;
import com.zz.fundapp.base.BaseActivity;
import com.zz.fundapp.bean.FundFocus;
import com.zz.fundapp.bean.FundHistoryDay;
import com.zz.fundapp.database.AppDataBase;
import com.zz.fundapp.databinding.ActivityHistoryListBinding;
import com.zz.fundapp.http.DataRequestHelper;
import com.zz.fundapp.ui.adapter.BaseRecyclerAdapter;
import com.zz.fundapp.ui.adapter.BothwayListViewAdapter;
import com.zz.fundapp.ui.adapter.HistoryFundListAdapter;
import com.zz.fundapp.ui.view.BothwayListView;
import com.zz.fundapp.ui.view.HeaderHorizontalScrollView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryListActivity extends BaseActivity<ActivityHistoryListBinding> implements View.OnClickListener {
    private BaseRecyclerAdapter<FundFocus> adapter;
    List<FundHistoryDay> fundHistoryDayArrayList = new ArrayList<>();
    private String queryDay = new SimpleDateFormat ("yyyy-MM-dd").format(new Date());

    private View mTopParentLinearLayout;
    private HeaderHorizontalScrollView mTopScrollView;
    private BothwayListView mBothwayListView;
    private BothwayListViewAdapter mBothwayListViewAdapter;
    @Override
    protected void initView() {
        binding = ActivityHistoryListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btRefresh.setOnClickListener(this);

        mTopParentLinearLayout = binding.topHeader.getRoot();
        mTopScrollView = binding.topHeader.myScrollview;
        mBothwayListView = binding.listView;

        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                HyLog.e("onSelectedDayChange year ： "  + year + " , month : "+ month + " , dayOfMonth :" + dayOfMonth);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                queryDay = new SimpleDateFormat ("yyyy-MM-dd").format(calendar.getTime());
                initData();
            }
        });

        binding.topHeader.tvName.setOnClickListener(this);

        binding.topHeader.stvGszzl.setOnClickListener(this);
        binding.topHeader.stvGszzl.setMode(MODE_SORT_DESC);
        binding.topHeader.tvDayChange.setOnClickListener(this);
        binding.topHeader.tvDayChange.setMode(MODE_SORT_NOT);
        binding.topHeader.tvDayChangeValue.setOnClickListener(this);
        binding.topHeader.tvDayChangeValue.setMode(MODE_SORT_NOT);

    }

    @Override
    protected void initData() {

        fundHistoryDayArrayList = new ArrayList<>();
        fundHistoryDayArrayList =  AppDataBase.getInstance(HistoryListActivity.this).getFundHistoryDao().getAllHistoryByDate(queryDay);
        HyLog.e("数据库 size ：" + fundHistoryDayArrayList.size());
        binding.tvSize.setText("数量：" + fundHistoryDayArrayList.size());

        mBothwayListView.initListView(mTopParentLinearLayout, mTopScrollView);
        mBothwayListViewAdapter = new HistoryFundListAdapter(fundHistoryDayArrayList,
                mTopScrollView);
        mBothwayListView.setAdapter(mBothwayListViewAdapter);
    }

    @Override
    public void onClick(View v) {
        HyLog.e("onClick : " +  v.getId());
        if(v.getId() == binding.btRefresh.getId()) {
            DataRequestHelper.getInstance(HistoryListActivity.this).getHistoryList("18316021694",System.currentTimeMillis()/1000)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<FundHistoryDay>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            HyLog.e("onSubscribe----");
                        }

                        @Override
                        public void onNext(List<FundHistoryDay> fundHistoryDays) {
                            HyLog.e("onNext----");
                            HyLog.e("size ----" + fundHistoryDays.size());
                            if(fundHistoryDays.size() > 0){
                                AppDataBase.getInstance(HistoryListActivity.this).getFundHistoryDao().insert(fundHistoryDays);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            HyLog.e("onError----");
                            Toast.makeText(HistoryListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            HyLog.e("onComplete----");
                            Toast.makeText(HistoryListActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                            initData();
                        }
                    });
        }
        else if(v.getId() == binding.topHeader.stvGszzl.getId()) {
            int mode = binding.topHeader.stvGszzl.getMode();
            HyLog.e("mode : " + mode);
            switch (mode){
                case MODE_SORT_NOT:
                    binding.topHeader.stvGszzl.setMode(MODE_SORT_DESC);
                    Comparator<FundHistoryDay> comparable = new Comparator<FundHistoryDay>() {
                        @Override
                        public int compare(FundHistoryDay o1, FundHistoryDay o2) {
                            return Double.valueOf(o2.getGszzl()).compareTo(Double.valueOf(o1.getGszzl()));
                        }
                    };
                    Collections.sort(fundHistoryDayArrayList,comparable);
                    adapter.notifyDataSetChanged();
                    break;
                case MODE_SORT_DESC:
                    binding.topHeader.stvGszzl.setMode(MODE_SORT_ASC);
                    comparable = new Comparator<FundHistoryDay>() {
                        @Override
                        public int compare(FundHistoryDay o1, FundHistoryDay o2) {
                            return Double.valueOf(o1.getGszzl()).compareTo(Double.valueOf(o2.getGszzl()));
                        }
                    };
                    Collections.sort(fundHistoryDayArrayList,comparable);
                    adapter.notifyDataSetChanged();
                    break;
                case MODE_SORT_ASC:
                    binding.topHeader.stvGszzl.setMode(MODE_SORT_NOT);
                    initData();
                    break;
            }
        }
        else if(v.getId() == binding.topHeader.tvName.getId()){
            HyLog.e("onClick tvName: ");
        }
    }
}