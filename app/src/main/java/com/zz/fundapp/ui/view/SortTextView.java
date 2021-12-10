package com.zz.fundapp.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zz.fundapp.R;

public class SortTextView extends androidx.appcompat.widget.AppCompatTextView {
    public static final int MODE_SORT_NONE = -1;
    public static final int MODE_SORT_NOT = 0;
    public static final int MODE_SORT_DESC = 1;
    public static final int MODE_SORT_ASC = 2;
    private int mode = MODE_SORT_NONE;
    public SortTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = getResources().getDrawable(R.drawable.sort);
        switch (mode){
            case MODE_SORT_NONE:
                break;
            case MODE_SORT_NOT:
                drawable = getResources().getDrawable(R.drawable.sort);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                setCompoundDrawables(null, null, drawable, null);
                break;
            case MODE_SORT_DESC:
                drawable = getResources().getDrawable(R.drawable.sort_desc);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                setCompoundDrawables(null, null, drawable, null);
                break;
            case MODE_SORT_ASC:
                drawable = getResources().getDrawable(R.drawable.sort_asc);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                setCompoundDrawables(null, null, drawable, null);
                break;
        }

    }
    public void setMode(int mode){
        this.mode = mode;
        postInvalidate();
    }
    public int getMode(){
        return mode;
    }
}
