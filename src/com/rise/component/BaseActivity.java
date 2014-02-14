package com.rise.component;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;

import com.base.consts.Config;

/**
 * Created by kai.wang on 2/14/14.
 */
public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Config.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }
}
