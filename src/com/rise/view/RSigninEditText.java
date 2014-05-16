package com.rise.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rise.R;

/**
 * Created by kai.wang on 5/16/14.
 */
public class RSignInEditText extends FrameLayout implements View.OnFocusChangeListener{
    private int iconNormal,iconFocused;
    private View containerView;
    private ImageView iconView;
    private EditText editView;
    public RSignInEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.RSignInEditText);
        iconNormal = types.getResourceId(R.styleable.RSignInEditText_icon_normal,0);
        iconFocused = types.getResourceId(R.styleable.RSignInEditText_icon_focused,0);
        int layout = types.getResourceId(R.styleable.RSignInEditText_sign_layout,0);
        if(iconNormal == 0){
            throw new RuntimeException("icon_normal must be set");
        }
        if(layout == 0){
            throw new RuntimeException("layout must be set");
        }
        if(iconFocused == 0){
            iconFocused = iconNormal;
        }
        containerView = View.inflate(context,layout,this);
        iconView = (ImageView) containerView.findViewById(R.id.sign_user_icon);
        editView = (EditText) containerView.findViewById(R.id.sign_user_text);

        iconView.setImageResource(iconNormal);

        editView.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            iconView.setImageResource(iconFocused);
        }else{
            iconView.setImageResource(iconNormal);
        }
    }
}
