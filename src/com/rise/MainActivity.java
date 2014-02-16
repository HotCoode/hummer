package com.rise;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
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

    private Menu menu;

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
        drawerListView.setAdapter(new DrawerListAdapter(this, drawerList));
        drawerListView.setOnItemClickListener(this);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        drawerToggle = new DrawerToggle(this, drawerLayout);
        drawerLayout.setDrawerListener(drawerToggle);

        QueryHelper.init(new DBHelper(this));
    }

    private void initFragment(Menu menu) {
	    setTitle(R.string.notes);
        fragmentManager = getSupportFragmentManager();
	    FragmentUtil.setCurrentFragment(R.string.notes);
        showFragment(new MainFragment(menu));
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    private void showFragment(int id) {
	    if (FragmentUtil.getCurrentFragment() == id) return;
	    Fragment fragment = null;
        if(id == R.string.notes){
            fragment = new MainFragment(menu);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.menu_put_anim).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        initFragment(menu);
        this.menu = menu;
        return true;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    drawerLayout.closeDrawers();
	    for(int i=0;i<parent.getCount();i++){
		    parent.getChildAt(i).setBackgroundResource(R.drawable.bg_list_item);
	    }
	    view.setBackgroundResource(R.drawable.bg_list_item_focus);
        showFragment(drawerList[position]);
	    drawerToggle.setTitle(drawerList[position]);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        QueryHelper.close();
        System.exit(1);
    }
}
