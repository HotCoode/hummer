package com.rise.component;

import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.rise.R;
import com.rise.fragment.FragmentUtil;

/**
 * Created by kai.wang on 2/11/14.
 */
public class DrawerToggle extends ActionBarDrawerToggle {

    private ActionBarActivity activity;

    private int currentTitle = R.string.main_frame;

    public DrawerToggle(ActionBarActivity activity, DrawerLayout drawerLayout){
        super(activity, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open,R.string.drawer_close);
        this.activity = activity;
    }

    public DrawerToggle(Activity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    /**
     * 设置当前ActionBar上显示的title,drawer close 时显示
     * @param title
     */
    public void changeTitle(int title){
        currentTitle = title;
    }

    public void setTitle(int title){
        activity.getSupportActionBar().setTitle(title);
    }



	@Override
    public void onDrawerClosed(View view) {
        activity.getSupportActionBar().setTitle(FragmentUtil.getCurrentFragment());
    }

	@Override
    public void onDrawerOpened(View drawerView) {
        activity.getSupportActionBar().setTitle(R.string.app_name);
    }
}
