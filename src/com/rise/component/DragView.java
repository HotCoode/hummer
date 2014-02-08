package com.rise.component;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

import com.base.L;

/**
 * Created by kai.wang on 2/7/14.
 */
public class DragView extends TextView {
    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setFocusable(true);
//        setClickable(true);
        setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("text", ((TextView) v).getText());
                data.addItem(new ClipData.Item(v.getTag().toString()));
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
                L.i("ACTION_DRAG_STARTED, event=" + event);
                result = true;
            }
            break;

            case DragEvent.ACTION_DRAG_ENDED: {
                L.i("ACTION_DRAG_ENDED.");
            }
            break;

            case DragEvent.ACTION_DRAG_LOCATION: {
                // we returned true to DRAG_STARTED, so return true here
                L.i("ACTION_DRAG_LOCATION");
            }
            break;

            case DragEvent.ACTION_DROP: {
                L.i("ACTION_DROP! dot=" + this + " event=" + event);
                result = true;
            }
            break;

            case DragEvent.ACTION_DRAG_ENTERED: {
                L.i("ACTION_DRAG_ENTERED,Entered dot @ " + this);
            }
            break;

            default:
                break;
        }

        return result;
    }


    // Shadow builder that can ANR if desired
    class ShadowBuilder extends DragShadowBuilder {

        public ShadowBuilder(View view) {
            super(view);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            canvas.drawColor(Color.LTGRAY);
            super.onDrawShadow(canvas);
        }
    }
}
