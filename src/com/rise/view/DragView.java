package com.rise.view;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by kai.wang on 2/7/14.
 */
public class DragView extends TextView {
    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                ClipData data = null;
                Object tag = v.getTag();
                if (tag != null) {
                    data = ClipData.newPlainText("text", tag.toString());
                }
                v.startDrag(data, new ShadowBuilder(v), (Object) v, 0);
                return true;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * Drag and drop
     */
    @Override
    public boolean onDragEvent(DragEvent event) {
        boolean result = false;
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED: {
                result = true;
            }
            break;
            case DragEvent.ACTION_DROP: {
                result = true;
            }
            break;
            default:
                break;
        }
        return result;
    }


    class ShadowBuilder extends DragShadowBuilder {

        public ShadowBuilder(View view) {
            super(view);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            super.onDrawShadow(canvas);
        }
    }
}
