package com.rise.component;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

import com.base.L;

/**
 * Created by kai.wang on 2/7/14.
 * <p/>
 * box
 */
public class BoxView extends View {
    // 进入中
    private boolean entering = false;
    // 退出中
    private boolean exiting = false;

    private BoxListener boxListener;

    public interface BoxListener {
        /** hover */
        void onEnter(BoxView view);

        /** puts in */
        void onPut(BoxView view);
    }

    public BoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBoxListener(BoxListener boxListener) {
        this.boxListener = boxListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (entering) {
        } else if (exiting) {

        }
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
                L.i("box 123123 Entered dot @ " + this);
                if (boxListener != null) boxListener.onEnter(this);
            }
            break;

            case DragEvent.ACTION_DRAG_EXITED: {
                L.i("box Exited dot @ " + this);
            }
            case DragEvent.ACTION_DROP: {
                L.i("box drop @ " + this);
                result = true;
                if (boxListener != null) boxListener.onPut(this);
            }
            break;

            default:
                break;
        }
        return result;
    }
}
