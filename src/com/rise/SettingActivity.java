package com.rise;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by kai.wang on 3/11/14.
 */
public class SettingActivity extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.activity_setting);
    }
}