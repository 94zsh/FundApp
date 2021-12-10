package com.zz.fundapp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;


public class TestScrollView extends HorizontalScrollView
{
    private Context mContext;
    public TestScrollView(Context context, AttributeSet attrs,
                          int defStyle)
    {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    public TestScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
    }

    public TestScrollView(Context context)
    {
        super(context);
        this.mContext = context;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
    }

}
