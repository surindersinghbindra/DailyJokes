package com.regrex.dailyJokes.widgets;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.regrex.dailyJokes.R;

/**
 * Created by surinder on 01-Jul-17.
 */

public class AppTextView extends android.support.v7.widget.AppCompatTextView {
    public AppTextView(Context context) {
        super(context);
        initView(context);
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context contextCompat) {

        setTextColor(ContextCompat.getColor(contextCompat, R.color.colorPrimaryDark));
        setTextSize(20f);
    }
}
