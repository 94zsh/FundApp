package com.zz.fundapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.zz.fundapp.R;
import com.zz.fundapp.bean.ScoreEntity;
import com.zz.fundapp.ui.view.HeaderHorizontalScrollView;

import java.util.ArrayList;


/**
 * 双向滚动的ListView adapter
 *
 * Created by dean on 2017/8/31.
 */

public class TableListAdapter extends BothwayListViewAdapter {

    ArrayList<ScoreEntity> scores;

    public TableListAdapter(ArrayList<ScoreEntity> scores, HeaderHorizontalScrollView topScrollView) {
        super(topScrollView);
        this.scores = scores;

    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public Object getItem(int position) {
        return scores.get(position);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
            holder = new ViewHolder();

            holder.tv0 = convertView.findViewById(R.id.tv0);
            holder.tv1 = convertView.findViewById(R.id.tv1);
            holder.tv2 = convertView.findViewById(R.id.tv2);
            holder.tv3 = convertView.findViewById(R.id.tv3);
            holder.tv4 = convertView.findViewById(R.id.tv4);
            holder.tv5 = convertView.findViewById(R.id.tv5);
            holder.tv6 = convertView.findViewById(R.id.tv6);
            holder.tv7 = convertView.findViewById(R.id.tv7);
            holder.my_scrollview = convertView.findViewById(R.id.my_scrollview);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tv0.setText(scores.get(position).name);
        holder.tv1.setText(scores.get(position).code);
        holder.tv2.setText(scores.get(position).mathematics+"");
        holder.tv3.setText(scores.get(position).language+"");
        holder.tv4.setText(scores.get(position).english+"");
        holder.tv5.setText(scores.get(position).physics+"");
        holder.tv6.setText(scores.get(position).biology+"");
        holder.tv7.setText(scores.get(position).chemistry+"");

        return new ConvertViewAndScrollView(convertView, holder.my_scrollview);
    }


        private class ViewHolder {

            TextView tv0;
            TextView tv1;
            TextView tv2;
            TextView tv3;
            TextView tv4;
            TextView tv5;
            TextView tv6;
            TextView tv7;
            HorizontalScrollView my_scrollview;

    }
}
