package com.rise;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.base.orm.QueryHelper;
import com.rise.adapter.ManageItemAdapter;
import com.rise.bean.Item;
import com.rise.common.Const;
import com.rise.component.BaseActivity;
import com.rise.component.SimpleDialog;
import com.rise.db.SQL;
import com.rise.db.SqlConst;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kai.wang on 2/17/14.
 */
public class ItemsManageActivity extends BaseActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener,AbsListView.MultiChoiceModeListener{
    private ListView listView;
    private ManageItemAdapter adapter;

    private final int ITEM_LOAD_FINISH = 100;

    private List<Item> items = new ArrayList<Item>();

    private boolean deleting = false;

    private int selectedRows = 0;

    private List<Integer> selectedItems = new ArrayList<Integer>();

    private ActionMode actionMode;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == ITEM_LOAD_FINISH){
                items.addAll((List<Item>)msg.obj);
                adapter.notifyDataSetChanged();
            }
            return false;
        }
    });

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(Const.ACTION_ITEM_UPDATE.equals(action)){
                refreshList();
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setContentView(R.layout.activity_items_manage);

        listView = (ListView) findViewById(R.id.item_list_view);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setOnItemLongClickListener(this);
        listView.setOnItemClickListener(this);
        listView.setMultiChoiceModeListener(this);
        adapter = new ManageItemAdapter(ItemsManageActivity.this, items);
        listView.setAdapter(adapter);

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
                        handler.obtainMessage(ITEM_LOAD_FINISH,beans).sendToTarget();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.manage_item,menu);
        menu.findItem(R.id.menu_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.findItem(R.id.menu_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
                startActivity(new Intent(ItemsManageActivity.this,EditItemActivity.class));
                break;
            case R.id.menu_delete:
                actionMode = startActionMode(this);
                actionMode.invalidate();
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
        if(deleting) return false;
        final String content = items.get(position).getContent();
        final String itemId = items.get(position).getId();
        final SimpleDialog dialog = new SimpleDialog(ItemsManageActivity.this,content,getString(R.string.edit_event));
        dialog.setOnClickListener(new SimpleDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent intent = new Intent(ItemsManageActivity.this, EditItemActivity.class);
                intent.putExtra("itemId", itemId);
                intent.putExtra("content", content);
                startActivity(intent);
            }
        });
        return true;
    }

    private void refreshList(){
        items.clear();
        loadData();
    }


    // called when the action mode is created; startActionMode() was called
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        deleting = true;
        mode.setTitle(R.string.please_select_want_to_delete_items);
        return true;
    }

    // the following method is called each time
    // the action mode is shown. Always called after
    // onCreateActionMode, but
    // may be called multiple times if the mode is invalidated.
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // Inflate a menu resource providing context menu items
        MenuInflater inflater = mode.getMenuInflater();
        // assumes that you have "contexual.xml" menu resources
        inflater.inflate(R.menu.delete_item, menu);
        return false; // Return false if nothing is done
    }

    // called when the user selects a contextual menu item
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                deleteItems(mode);
                return true;
            default:
                return false;
        }
    }

    // called when the user exits the action mode
    public void onDestroyActionMode(ActionMode mode) {
        deleting = false;
        selectedRows = 0;
        selectedItems.clear();
        actionMode = null;
        adapter.clearSelection();
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        if(checked){
            selectedRows ++;
            adapter.selection(position);
            selectedItems.add(position);
        }else{
            selectedRows --;
            adapter.removeSelection(position);
            selectedItems.remove(selectedItems.indexOf(position));
        }
        mode.setTitle(MessageFormat.format(getString(R.string.selected_some_rows),selectedRows));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(actionMode != null){
            listView.setItemChecked(position, !adapter.isSelection(position));
        }
    }

    private void deleteItems(ActionMode mode){
        mode.finish();
        if(selectedItems.size() == 0) return;
        for (int position : selectedItems){
            Item item = items.get(position);
            String itemId = item.getId();
            QueryHelper.update(SQL.DELETE_ITEM_BY_ID,new String[]{itemId},null);
        }
        Toast.makeText(ItemsManageActivity.this,R.string.delete_success,Toast.LENGTH_SHORT).show();
        refreshList();
    }
}