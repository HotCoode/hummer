package com.rise;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.ListView;

import com.base.orm.QueryHelper;
import com.rise.adapter.MainListAdapter;
import com.rise.adapter.ManageItemAdapter;
import com.rise.bean.Item;
import com.rise.component.BaseActivity;
import com.rise.db.SQL;
import com.rise.db.SqlConst;

import java.util.List;

/**
 * Created by kai.wang on 2/17/14.
 */
public class ItemsActivity extends BaseActivity {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_manage);

        listView = (ListView) findViewById(R.id.item_list_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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
                        adapter = new ManageItemAdapter(ItemsActivity.this, beans);
                        handler.sendEmptyMessage(ITEM_LOAD_FINISH);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}