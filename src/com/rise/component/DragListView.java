package com.rise.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by kai.wang on 2/7/14.
 * <p/>
 * 可拖拽的ListView
 */
public class DragListView extends ListView {
    public DragListView(Context context) {
        super(context);
    }

    public DragListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
