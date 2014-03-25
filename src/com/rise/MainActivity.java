package com.rise;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.base.L;
import com.base.common.Screen;
import com.base.orm.QueryHelper;
import com.rise.adapter.DrawerListAdapter;
import com.rise.common.AppUtils;
import com.rise.common.Const;
import com.rise.component.BaseActivity;
import com.rise.component.DrawerToggle;
import com.rise.db.DBHelper;
import com.rise.fragment.FragmentUtil;
import com.rise.fragment.MainFragment;
import com.rise.fragment.NotesFragment;
import com.rise.fragment.ReportFragment;

public class MainActivity extends BaseActivity implements ListView.OnItemClickListener {

    private FragmentManager fragmentManager;

    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private View drawerLayoutContainer;

    private DrawerToggle drawerToggle;

    // drawer list 数据
    private int[] drawerList = {R.string.notes,
            R.string.high_income_long_half_life,
            R.string.low_income_long_half_life,
            R.string.high_income_short_half_life,
            R.string.low_income_short_half_life,
            R.string.report
    };
    private DrawerListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        L.i("main activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        injectViews();

        initFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        L.i("new intent");
        moveToHome();
        super.onNewIntent(intent);
    }

    private void injectViews(){
        drawerListView = (ListView) findViewById(R.id.left_drawer_list);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayoutContainer = findViewById(R.id.drawer_layout_container);

        Const.SCREEN_WIDTH = Screen.getScreenWidth(getWindowManager());
        DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(Const.SCREEN_WIDTH * 7 / 8, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.LEFT;
        drawerLayoutContainer.setLayoutParams(params);

        adapter = new DrawerListAdapter(this, drawerList);
        drawerListView.setAdapter(adapter);
        drawerListView.setOnItemClickListener(this);
//        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        drawerToggle = new DrawerToggle(this, drawerLayout);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void initFragment() {
	    setTitle(R.string.notes);
        fragmentManager = getSupportFragmentManager();
	    FragmentUtil.setCurrentFragment(R.string.notes);
        showFragment(new MainFragment());
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    private void showFragment(int id) {
        if (FragmentUtil.getCurrentFragment() == id) return;
        Fragment fragment;
        if(id == R.string.notes){
            fragment = new MainFragment();
        }else if(id == R.string.report){
            fragment = new ReportFragment();
        }else{
            Bundle bundle = new Bundle();
            bundle.putInt("id",id);
            fragment = new NotesFragment();
            fragment.setArguments(bundle);
        }
        FragmentUtil.setCurrentFragment(id);
        showFragment(fragment);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    resetDrawerLayoutItemFocus();
	    view.setBackgroundResource(R.drawable.bg_list_item_focus);
        showFragment(drawerList[position]);
	    drawerToggle.changeTitle(drawerList[position]);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && FragmentUtil.getCurrentFragment() != R.string.notes){
            moveToHome();
            return true;
        }else if(keyCode != KeyEvent.KEYCODE_MENU){
            finish();
            AppUtils.exit();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    protected void onDestroy() {
        L.i("MainActivity : onDestroy");
        super.onDestroy();
    }

    /**
     * 其他頁面按返回鍵，返回到home頁面
     */
    private void moveToHome(){
        showFragment(R.string.notes);
        drawerToggle.setTitle(R.string.notes);
        adapter.notifyDataSetChanged();
    }

    private void resetDrawerLayoutItemFocus(){
        drawerLayout.closeDrawers();
        for(int i=0;i<drawerListView.getCount();i++){
            drawerListView.getChildAt(i).setBackgroundResource(R.drawable.bg_pressable_normal);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }else{
            switch (item.getItemId()){
                case R.id.menu_setting:
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
