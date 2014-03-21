package com.rise;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.base.orm.QueryHelper;
import com.rise.adapter.ManageItemAdapter;
import com.rise.bean.Item;
import com.rise.common.Const;
import com.rise.component.BaseActivity;
import com.rise.db.SQL;
import com.rise.db.SqlConst;

import java.util.List;

/**
 * Created by kai.wang on 2/17/14.
 */
public class ItemsManageActivity extends BaseActivity implements AdapterView.OnItemLongClickListener{
    private ListView listView;
    private ManageItemAdapter adapter;

    private final int ITEM_LOAD_FINISH = 100;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == ITEM_LOAD_FINISH){
                listView.setAdapter(adapter);
            }
            return false;
        }
    });

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(Const.ACTION_ITEM_UPDATE.equals(action)){
                loadData();
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setContentView(R.layout.activity_items_manage);

        listView = (ListView) findViewById(R.id.item_list_view);
        listView.setOnItemLongClickListener(this);


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Const.ACTION_ITEM_UPDATE);
        registerReceiver(broadcastReceiver, intentFilter);

        loadData();
    }

    private void loadData(){
        QueryHelper.findBeans(
                Item.class,
                SQL.FIND_ITEMS_BY_STATUS,
                new String[]{SqlConst.ITEM_STATUS_AVAILABLE},
                new QueryHelper.FindBeansCallBack<Item>() {
                    @Override
                    public void onFinish(List<Item> beans) {
                        adapter = new ManageItemAdapter(ItemsManageActivity.this, beans);
                        handler.sendEmptyMessage(ITEM_LOAD_FINISH);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add,menu);
        menu.findItem(R.id.menu_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_add:
                startActivity(new Intent(ItemsManageActivity.this,NewItemActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        startSupportActionMode(mActionModeCallback);
        return false;
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // called when the action mode is created; startActionMode() was called
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            // assumes that you have "contexual.xml" menu resources
            inflater.inflate(R.menu.manage_item, menu);
            return true;
        }

        // the following method is called each time
        // the action mode is shown. Always called after
        // onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // called when the user selects a contextual menu item
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_edit:
                    // the Action was executed, close the CAB
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        // called when the user exits the action mode
        public void onDestroyActionMode(ActionMode mode) {
//            mActionMode = null;
//            selectedItem = -1;
        }
    };

}