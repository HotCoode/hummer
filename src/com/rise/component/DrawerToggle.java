package com.rise.component;

import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.rise.R;

/**
 * Created by kai.wang on 2/11/14.
 */
public class DrawerToggle extends ActionBarDrawerToggle {

    private Activity activity;

    private int currentTitle = R.string.main_frame;

    public DrawerToggle(Activity activity, DrawerLayout drawerLayout){
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
    public void setTitle(int title){
        currentTitle = title;
    }

    public void onDrawerClosed(View view) {
        activity.getActionBar().setTitle(currentTitle);
        activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }

    public void onDrawerOpened(View drawerView) {
        activity.getActionBar().setTitle(R.string.app_name);
        activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }
}
