package com.zz.fundapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.zz.fundapp.R;
import com.zz.fundapp.bean.FundHistoryDay;
import com.zz.fundapp.ui.view.HeaderHorizontalScrollView;

import java.util.List;


/**
 * 双向滚动的ListView adapter
 *
 * Created by dean on 2017/8/31.
 */

public class HistoryFundListAdapter extends BothwayListViewAdapter {

    List<FundHistoryDay> fundHistoryDayArrayList;

    public HistoryFundListAdapter(List<FundHistoryDay> fundHistoryDays, HeaderHorizontalScrollView topScrollView) {
        super(topScrollView);
        this.fundHistoryDayArrayList = fundHistoryDays;

    }

    @Override
    public int getCount() {
        return fundHistoryDayArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return fundHistoryDayArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ConvertViewAndScrollView getFullView(int position, View convertView,
                                                ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_list, null, false);
            holder = new ViewHolder();

            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_jzrq = convertView.findViewById(R.id.tv_jzrq);
            holder.tv_dwjz = convertView.findViewById(R.id.tv_dwjz);
            holder.tv_gsz = convertView.findViewById(R.id.tv_gsz);
            holder.tv_gszzl = convertView.findViewById(R.id.stv_gszzl);
            holder.tv_gztime = convertView.findViewById(R.id.tv_gztime);
            holder.tv_timestamp = convertView.findViewById(R.id.tv_timestamp);
            holder.tv_dayChange = convertView.findViewById(R.id.tv_dayChange);
            holder.tv_dayChangeValue = convertView.findViewById(R.id.tv_dayChangeValue);
            holder.my_scrollview = convertView.findViewById(R.id.my_scrollview);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FundHistoryDay historyDay = fundHistoryDayArrayList.get(position);
        holder.tv_name.setText(historyDay.getName() + "(" + historyDay.getFundcode()+")");
        holder.tv_jzrq.setText(historyDay.getJzrq());
        holder.tv_dwjz.setText(historyDay.getDwjz()+"");
        holder.tv_gsz.setText(historyDay.getGsz()+"");
        holder.tv_gszzl.setText(historyDay.getGszzl()+"");
        if(historyDay.getGszzl().contains("-")){
            holder.tv_gszzl.setTextColor(convertView.getContext().getResources().getColor(R.color.green));
        }else{
            holder.tv_gszzl.setTextColor(convertView.getContext().getResources().getColor(R.color.red));
        }
        holder.tv_gztime.setText(historyDay.getGztime()+"");
        holder.tv_timestamp.setText(historyDay.getTimestamp()+"");
        holder.tv_dayChange.setText(historyDay.getDayChange()+"");
        holder.tv_dayChangeValue.setText(historyDay.getDayChangeValue()+"");

        return new ConvertViewAndScrollView(convertView, holder.my_scrollview);
    }


        private class ViewHolder {

            TextView tv_name;
            TextView tv_jzrq;
            TextView tv_dwjz;
            TextView tv_gsz;
            TextView tv_gszzl;
            TextView tv_gztime;
            TextView tv_timestamp;
            TextView tv_dayChange;
            TextView tv_dayChangeValue;
            HorizontalScrollView my_scrollview;

    }
}
