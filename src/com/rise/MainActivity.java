package com.rise;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.base.common.Screen;
import com.base.orm.QueryHelper;
import com.rise.adapter.DrawerListAdapter;
import com.rise.common.Const;
import com.rise.component.BaseActivity;
import com.rise.component.DrawerToggle;
import com.rise.db.DBHelper;
import com.rise.fragment.FragmentUtil;
import com.rise.fragment.MainFragment;
import com.rise.fragment.NotesFragment;

public class MainActivity extends BaseActivity implements ListView.OnItemClickListener {

    private FragmentManager fragmentManager;

    private ListView drawerListView;
    private DrawerLayout drawerLayout;

    private DrawerToggle drawerToggle;

    // drawer list 数据
    private int[] drawerList = {R.string.notes, R.string.high_income_long_half_life, R.string.low_income_long_half_life, R.string.high_income_short_half_life, R.string.low_income_short_half_life};
    private DrawerListAdapter adapter;


	// 当前显示的fragment
	private int currentFragment = R.string.notes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerListView = (ListView) findViewById(R.id.left_drawer_list);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

	    Const.SCREEN_WIDTH = Screen.getScreenWidth(getWindowManager());
	    DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(Const.SCREEN_WIDTH * 7 / 8, ViewGroup.LayoutParams.MATCH_PARENT);
	    params.gravity = Gravity.LEFT;
        drawerListView.setLayoutParams(params);
        adapter = new DrawerListAdapter(this, drawerList);
        drawerListView.setAdapter(adapter);
        drawerListView.setOnItemClickListener(this);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        drawerToggle = new DrawerToggle(this, drawerLayout);
        drawerLayout.setDrawerListener(drawerToggle);

        QueryHelper.init(new DBHelper(this));

        initFragment();
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
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showFragment(int id) {
        if (FragmentUtil.getCurrentFragment() == id) return;
        Fragment fragment = null;
        if(id == R.string.notes){
            fragment = new MainFragment();
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
	    drawerLayout.closeDrawers();
	    for(int i=0;i<parent.getCount();i++){
		    parent.getChildAt(i).setBackgroundResource(R.drawable.bg_list_item);
	    }
	    view.setBackgroundResource(R.drawable.bg_list_item_focus);
        showFragment(drawerList[position]);
	    drawerToggle.changeTitle(drawerList[position]);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && FragmentUtil.getCurrentFragment() != R.string.notes){
            moveToHome();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        QueryHelper.close();
        System.exit(1);
    }

    /**
     * 其他頁面按返回鍵，返回到home頁面
     */
    private void moveToHome(){
        showFragment(R.string.notes);
        drawerToggle.setTitle(R.string.notes);
        adapter.notifyDataSetChanged();
    }
}
