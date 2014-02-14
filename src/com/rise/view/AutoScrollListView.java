package com.rise.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import com.base.L;

/**
 * Created by kai.wang on 2/13/14.
 * 如果滑到第一个item，自动滑倒顶部
 */
public class AutoScrollListView extends ListView {

    public AutoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_UP && getFirstVisiblePosition() == 0){
            L.i("first");
            setSelection(0);
        }
        return super.onTouchEvent(ev);
    }
}
