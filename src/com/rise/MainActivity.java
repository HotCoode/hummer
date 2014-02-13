package com.rise;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

import com.base.common.Screen;
import com.rise.adapter.DrawerListAdapter;
import com.rise.component.DrawerToggle;
import com.rise.fragment.MainFragment;

public class MainActivity extends ActionBarActivity {

    private FragmentTransaction fragmentTransaction;

    private ListView drawerListView;
    private DrawerLayout drawerLayout;

    private DrawerToggle drawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerListView = (ListView) findViewById(R.id.left_drawer_list);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(Screen.getScreenWidth(getWindowManager()) * 3 / 4, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.START;
        drawerListView.setLayoutParams(params);
        drawerListView.setAdapter(new DrawerListAdapter(this, new String[]{"aaaa", "bbbb", "cccc"}));
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        drawerToggle = new DrawerToggle(this, drawerLayout);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void initFragment(Menu menu) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        showFragment(new MainFragment(menu));
    }

    private void showFragment(Fragment fragment) {
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        initFragment(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }

}
