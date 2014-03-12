package com.rise.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rise.R;

/**
 * Created by kai.wang on 3/10/14.
 */
public class SettingFragment extends Fragment implements BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container = (ViewGroup) inflater.inflate(R.layout.activity_setting, null);
        injectViews(container);
        return container;
    }

    @Override
    public void injectViews(View parentView) {

    }
}