package com.zz.fundapp.ui.view;

import static com.zz.fundapp.ui.view.SortTextView.MODE_SORT_ASC;
import static com.zz.fundapp.ui.view.SortTextView.MODE_SORT_DESC;
import static com.zz.fundapp.ui.view.SortTextView.MODE_SORT_NOT;

import android.os.Build;
import android.view.View;
import android.widget.CalendarView;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zz.fundapp.HyLog;
import com.zz.fundapp.R;
import com.zz.fundapp.base.BaseActivity;
import com.zz.fundapp.bean.FundHistoryDay;
import com.zz.fundapp.database.AppDataBase;
import com.zz.fundapp.databinding.ActivityViewTestBinding;
import com.zz.fundapp.http.DataRequestHelper;
import com.zz.fundapp.ui.adapter.BaseRecyclerAdapter;
import com.zz.fundapp.ui.adapter.BaseViewHolder;

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

public class DayHistoryActivity extends BaseActivity<ActivityViewTestBinding> implements View.OnClickListener {
    private Date queryDate = new Date();
    @Override
    protected void initView() {
        HyLog.e("initView");
        binding = ActivityViewTestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                HyLog.e("onSelectedDayChange year ： " + year + " , month : " + month + " , dayOfMonth :" + dayOfMonth);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                queryDate = calendar.getTime();
                initData();
            }
        });

        binding.btRefresh.setOnClickListener(this);
        binding.topHeader.stvGszzl.setOnClickListener(this);
        binding.topHeader.stvGszzl.setMode(MODE_SORT_NOT);
        binding.topHeader.tvDayChange.setOnClickListener(this);
        binding.topHeader.tvDayChange.setMode(MODE_SORT_NOT);
        binding.topHeader.tvDayChangeValue.setOnClickListener(this);
        binding.topHeader.tvDayChangeValue.setMode(MODE_SORT_NOT);
    }

    private BaseRecyclerAdapter<FundHistoryDay> adapter;
    private List<FundHistoryDay> fundHistoryDayArrayList = new ArrayList<>();
    private ArrayList<HorizontalScrollView> mItemScrollViewList = new ArrayList<>();
    private int lastScrollX = 0;
    private int lastScrollY = 0;
    @Override
    protected void initData() {
        String queryDay = new SimpleDateFormat("yyyy-MM-dd").format(queryDate);
        fundHistoryDayArrayList = AppDataBase.getInstance(this).getFundHistoryDao().getAllHistoryByDate(queryDay);
        HyLog.e("initData queryDay ：" + queryDay + "  size  :" + fundHistoryDayArrayList.size());

        adapter = new BaseRecyclerAdapter<FundHistoryDay>(this, R.layout.item_test_list, fundHistoryDayArrayList) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void convert(BaseViewHolder holder, FundHistoryDay fundHistoryDay, int position) {
                FundHistoryDay historyDay = fundHistoryDayArrayList.get(position);
                holder.setText(R.id.tv_name, historyDay.getName() + "(" + historyDay.getFundcode() + ")");
                holder.setText(R.id.tv_jzrq, historyDay.getJzrq());
                holder.setText(R.id.tv_dwjz, historyDay.getDwjz() + "");
                holder.setText(R.id.tv_gsz, historyDay.getGsz() + "");
                holder.setText(R.id.stv_gszzl, historyDay.getGszzl() + "");

                TextView tv_gszzl = holder.getView(R.id.stv_gszzl);
                if (historyDay.getGszzl().contains("-")) {
                    tv_gszzl.setTextColor(getResources().getColor(R.color.green));
                } else {
                    tv_gszzl.setTextColor(getResources().getColor(R.color.red));
                }
                holder.setText(R.id.tv_gztime, historyDay.getGztime() + "");
                holder.setText(R.id.tv_timestamp, new SimpleDateFormat("yyyy-MM-dd").format(new Date(historyDay.getTimestamp()))  + "");
                holder.setText(R.id.tv_dayChange, historyDay.getDayChange() + "");
                holder.setText(R.id.tv_dayChangeValue, historyDay.getDayChangeValue() + "");

                TestScrollView itemScrollView = holder.getView(R.id.testScrollView);
                if (null != mItemScrollViewList
                        && !mItemScrollViewList.contains(itemScrollView)) {
                    mItemScrollViewList.add(itemScrollView);
                }
                itemScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        lastScrollX = scrollX;
                        lastScrollY = scrollY;
                        setScroll(lastScrollX, lastScrollY);
                    }
                });
                setScroll(lastScrollX,lastScrollY);
            }
        };
        binding.rvTest.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvTest.setAdapter(adapter);

        if(fundHistoryDayArrayList.size() > 0){
            binding.tvSize.setText("数量：" + fundHistoryDayArrayList.size());
        }else{
            getCurrentDataFromService();

        }
    }

    private void setScroll(int scrollX, int scrollY) {
        int size = mItemScrollViewList.size();
        if (size > 0 && scrollX != -9999 && scrollY != -9999) {
            for (int i = 0; i < size; i++) {
                mItemScrollViewList.get(i).smoothScrollTo(scrollX, scrollY);
            }
            binding.topHeader.testScrollView.smoothScrollTo(scrollX, scrollY);
        }
    }


    @Override
    public void onClick(View v) {
        HyLog.e("onClick : " + v.getId());
        if (v.getId() == binding.btRefresh.getId()) {
            getCurrentDataFromService();
        } else if (v.getId() == binding.topHeader.stvGszzl.getId()) {
            int mode = binding.topHeader.stvGszzl.getMode();
            HyLog.e("mode : " + mode);
            switch (mode) {
                case MODE_SORT_NOT:
                    binding.topHeader.stvGszzl.setMode(MODE_SORT_DESC);
                    Comparator<FundHistoryDay> comparable = new Comparator<FundHistoryDay>() {
                        @Override
                        public int compare(FundHistoryDay o1, FundHistoryDay o2) {
                            return Double.valueOf(o2.getGszzl()).compareTo(Double.valueOf(o1.getGszzl()));
                        }
                    };
                    Collections.sort(fundHistoryDayArrayList, comparable);
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
                    Collections.sort(fundHistoryDayArrayList, comparable);
                    adapter.notifyDataSetChanged();
                    break;
                case MODE_SORT_ASC:
                    binding.topHeader.stvGszzl.setMode(MODE_SORT_NOT);
                    initData();
                    break;
            }
        } else if (v.getId() == binding.topHeader.tvName.getId()) {
            HyLog.e("onClick tvName: ");
        }
    }

    private void getCurrentDataFromService() {
        DataRequestHelper.getInstance(DayHistoryActivity.this).getHistoryList("18316021694", queryDate.getTime() / 1000)
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
                        if (fundHistoryDays.size() > 0) {
                            AppDataBase.getInstance(DayHistoryActivity.this).getFundHistoryDao().insert(fundHistoryDays);
                        }else{
                            onError(new Throwable("no data"));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        HyLog.e("onError---- : " + e);
                        binding.tvSize.setText(e.getMessage());
                        Toast.makeText(DayHistoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        HyLog.e("onComplete----");
                        Toast.makeText(DayHistoryActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                        initData();
                    }
                });
    }
}
