package com.rise.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.CheckBoxPreference;
import android.preference.DialogPreference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TimePicker;

import com.base.common.StringUtils;
import com.rise.R;

/**
 * Created by kai.wang on 3/11/14.
 */
public class TimePreference extends DialogPreference {
    private int lastHour = 0;
    private int lastMinute = 0;
    private TimePicker picker = null;

    public static int getHour(String time) {
        if(TextUtils.isEmpty(time)) return 0;
        String[] pieces = time.split(":");
        return StringUtils.toInt(pieces[0],0);
    }

    public static int getMinute(String time) {
        if(TextUtils.isEmpty(time)) return 0;
        String[] pieces = time.split(":");
        return StringUtils.toInt(pieces[1],0);
    }

    public TimePreference(Context ctxt, AttributeSet attrs) {
        super(ctxt, attrs);

        setPositiveButtonText(R.string.set);
        setNegativeButtonText(R.string.cancel);

    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        setSummary(getPersistedString(""));
        return super.onCreateView(parent);
    }

    @Override
    protected View onCreateDialogView() {
        picker = new TimePicker(getContext());
        picker.setIs24HourView(true);
        picker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

//        picker.setFocusable(true);
//        picker.setFocusableInTouchMode(true);
//        int childCount = picker.getChildCount();
//
//        InputMethodManager im = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        im.hideSoftInputFromWindow(picker.getWindowToken(), 0);
//        for(int i = 0;i<childCount;i++){
//            if(picker.getChildAt(i) instanceof EditText){
//
//            }
//        }
        return (picker);
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        picker.setCurrentHour(lastHour);
        picker.setCurrentMinute(lastMinute);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            lastHour = picker.getCurrentHour();
            lastMinute = picker.getCurrentMinute();

            String time = String.format("%02d",lastHour) + ":" + String.format("%02d",lastMinute);

            if (callChangeListener(time)) {
                persistString(time);
                setSummary(time);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return (a.getString(index));
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        String time = null;

        if (restoreValue) {
            if (defaultValue == null) {
                time = getPersistedString("00:00");
            } else {
                time = getPersistedString(defaultValue.toString());
            }
        } else {
            time = defaultValue.toString();
        }

        lastHour = getHour(time);
        lastMinute = getMinute(time);
    }
}