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
        setFocusable(true);
        setClickable(true);
        setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("dot", "Dot : " + v.toString());
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
                // claim to accept any dragged content
                L.i("ACTION_DRAG_STARTED, event=" + event);
//                // cache whether we accept the drag to return for LOCATION events
//                mDragInProgress = true;
//                mAcceptsDrag = result = true;
//                // Redraw in the new visual state if we are a potential drop target
//                if (mAcceptsDrag) {
//                    invalidate();
//                }
            }
            break;

            case DragEvent.ACTION_DRAG_ENDED: {
                L.i("ACTION_DRAG_ENDED.");
//                if (mAcceptsDrag) {
//                    invalidate();
//                }
//                mDragInProgress = false;
//                mHovering = false;
            }
            break;

            case DragEvent.ACTION_DRAG_LOCATION: {
                // we returned true to DRAG_STARTED, so return true here
                L.i("ACTION_DRAG_LOCATION");
//                result = mAcceptsDrag;
            }
            break;

            case DragEvent.ACTION_DROP: {
                L.i("ACTION_DROP! dot=" + this + " event=" + event);
//                processDrop(event);
//                result = true;
            }
            break;

            case DragEvent.ACTION_DRAG_ENTERED: {
                L.i("ACTION_DRAG_ENTERED,Entered dot @ " + this);
//                mHovering = true;
//                invalidate();
            }
            break;

            default:
                L.i("other drag event: " + event);
//                result = mAcceptsDrag;
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
