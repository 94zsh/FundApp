package com.zz.fundapp.ui.me;

import static com.zz.fundapp.ui.view.SortTextView.MODE_SORT_ASC;
import static com.zz.fundapp.ui.view.SortTextView.MODE_SORT_DESC;
import static com.zz.fundapp.ui.view.SortTextView.MODE_SORT_NOT;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zz.fundapp.HyLog;
import com.zz.fundapp.R;
import com.zz.fundapp.base.BaseActivity;
import com.zz.fundapp.bean.FundFocus;
import com.zz.fundapp.database.AppDataBase;
import com.zz.fundapp.databinding.ActivityFocusListBinding;
import com.zz.fundapp.http.DataRequestHelper;
import com.zz.fundapp.ui.adapter.BaseRecyclerAdapter;
import com.zz.fundapp.ui.adapter.BaseViewHolder;
import com.zz.fundapp.ui.view.SortTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FocusListActivity extends BaseActivity<ActivityFocusListBinding> implements View.OnClickListener {
    private BaseRecyclerAdapter<FundFocus> adapter;
    List<FundFocus> fundList = new ArrayList<>();
    List<FundFocus> fundListTemp = new ArrayList<>();
    private ActivityResultLauncher launcher;
    @Override
    protected void initView() {
        binding = ActivityFocusListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.btRefresh.setOnClickListener(this);
        binding.btAdd.setOnClickListener(this);
        binding.topHeader.stvZzl.setOnClickListener(this);
        binding.topHeader.stvZzl.setMode(MODE_SORT_NOT);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getData() != null && result.getData().hasExtra("code")){
                    String selectCode = result.getData().getStringExtra("code");
                    HyLog.e("select code : " + selectCode);
                    DataRequestHelper.getInstance(FocusListActivity.this).addFocus("18316021694",selectCode)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<String>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(String s) {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(FocusListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete() {
                                    binding.btRefresh.callOnClick();
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void initData() {
        fundList.clear();
        fundListTemp.clear();
        fundList = AppDataBase.getInstance(FocusListActivity.this).getFundFocusDao().getAllFocus();
        fundListTemp.addAll(fundList);
        binding.tvSize.setText("数量：" + fundList.size());
        binding.rvList.setLayoutManager(new LinearLayoutManager(FocusListActivity.this,LinearLayoutManager.VERTICAL,false));
        adapter = new BaseRecyclerAdapter<FundFocus>(FocusListActivity.this,R.layout.item_focus_list,fundList) {
            @Override
            public void convert(BaseViewHolder holder, FundFocus fund, int position) {
                SortTextView tv_zzl = holder.getView(R.id.tv_zzl);
                if(fund.getGszzl().contains("-")){
                    tv_zzl.setTextColor(getResources().getColor(R.color.green));
                }else{
                    tv_zzl.setTextColor(getResources().getColor(R.color.red));
                }

                holder.setText(R.id.tv0,fund.getName() + " \n" + fund.getCode())
                        .setText(R.id.tv1,fund.getGztime())
                        .setText(R.id.tv_zzl,fund.getGszzl());
            }
        };
        binding.rvList.setAdapter(adapter);

        adapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, int position) {
                new AlertDialog.Builder(FocusListActivity.this)
                        .setMessage("是否删除该数据")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataRequestHelper.getInstance(FocusListActivity.this).deleteFocus("18316021694",fundList.get(position).getCode())
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<String>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(String s) {

                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Toast.makeText(FocusListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onComplete() {
                                                AppDataBase.getInstance(FocusListActivity.this).getFundFocusDao().delete(fundList.get(position));
                                                binding.btRefresh.callOnClick();
                                            }
                                        });
                            }
                        }).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if(v.getId() == binding.btRefresh.getId()) {
            DataRequestHelper.getInstance(FocusListActivity.this).getFocusList("18316021694")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<FundFocus>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            HyLog.e("onSubscribe----");
                        }

                        @Override
                        public void onNext(List<FundFocus> fundFocus) {
                            HyLog.e("onNext----");
                            HyLog.e("size ----" + fundFocus.size());
                            if(fundFocus.size() > 0){
                                AppDataBase.getInstance(FocusListActivity.this).getFundFocusDao().insert(fundFocus);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            HyLog.e("onError----");
                            Toast.makeText(FocusListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            HyLog.e("onComplete----");
                            Toast.makeText(FocusListActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                            initData();
                        }
                    });
        }else if(v.getId() == binding.topHeader.stvZzl.getId()) {
            int mode = binding.topHeader.stvZzl.getMode();
            HyLog.e("mode : " + mode);
            switch (mode){
                case MODE_SORT_NOT:
                    binding.topHeader.stvZzl.setMode(MODE_SORT_DESC);
                    Comparator<FundFocus> comparable = new Comparator<FundFocus>() {
                        @Override
                        public int compare(FundFocus o1, FundFocus o2) {
                            return Double.valueOf(o2.getGszzl()).compareTo(Double.valueOf(o1.getGszzl()));
                        }
                    };
                    Collections.sort(fundList,comparable);
                    adapter.notifyDataSetChanged();
                    break;
                case MODE_SORT_DESC:
                    binding.topHeader.stvZzl.setMode(MODE_SORT_ASC);
                     comparable = new Comparator<FundFocus>() {
                        @Override
                        public int compare(FundFocus o1, FundFocus o2) {
                            return Double.valueOf(o1.getGszzl()).compareTo(Double.valueOf(o2.getGszzl()));
                        }
                    };
                    Collections.sort(fundList,comparable);
                    adapter.notifyDataSetChanged();
                    break;
                case MODE_SORT_ASC:
                    binding.topHeader.stvZzl.setMode(MODE_SORT_NOT);
                    initData();
                    break;
            }
        }else if(v.getId() == binding.btAdd.getId()) {
            Intent intent = new Intent(FocusListActivity.this,FundSearchActivity.class);
            launcher.launch(intent);
        }
    }
}