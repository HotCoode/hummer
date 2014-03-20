package com.rise.common;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.base.orm.QueryHelper;
import com.rise.R;

/**
 * Created by kai.wang on 3/20/14.
 */
public class AppUtils {

    /**
     * 退出程序
     */
    public static void exit(){
        QueryHelper.close();
        System.exit(1);
    }

    /**
     * DoneDiscardActivity关闭动画
     * @param focusView
     * @param activity
     */
    public static void doneDiscardActivityClose(View focusView,Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.finish();
        activity.overridePendingTransition(0,R.anim.cancel_item);
    }
}
