package com.rise.component;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.widget.TextView;

import com.base.L;

/**
 * Created by kai.wang on 2/7/14.
 * <p/>
 * box
 */
public class BoxView extends TextView {
    // 进入中
    private boolean entered = false;
    // 退出中
    private boolean exited = false;

    private BoxListener boxListener;

    public interface BoxListener {
        /**
         * hover
         */
        void onEnter(BoxView view);

        /**
         * puts in
         */
        void onPut(BoxView view, ClipData data);

        void onExit(BoxView view);
    }

    public BoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBoxListener(BoxListener boxListener) {
        this.boxListener = boxListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        boolean result = false;
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED: {
                L.i("box start @ " + this);
                result = true;
            }
            break;

            case DragEvent.ACTION_DRAG_ENTERED: {
                exited = false;
                L.i("box 123123 Entered dot @ " + this);
                if (boxListener != null) boxListener.onEnter(this);
            }
            break;

            case DragEvent.ACTION_DRAG_EXITED: {
                L.i("box Exited dot @ " + this);
                exited = true;
                if (boxListener != null) boxListener.onExit(this);
            }
            case DragEvent.ACTION_DROP: {
                L.i("box drop @ " + this);
                result = true;
                if(!exited){
                    ClipData data = event.getClipData();
                    if (boxListener != null) boxListener.onPut(this, data);
                }
            }
            break;

            default:
                break;
        }
        return result;
    }
}
