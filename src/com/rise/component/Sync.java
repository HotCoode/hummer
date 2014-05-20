package com.rise.component;

import android.provider.ContactsContract;

import com.base.L;
import com.base.orm.QueryHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rise.bean.Item;
import com.rise.bean.NotesItem;
import com.rise.common.Const;
import com.rise.db.SQL;
import com.rise.http.AsyncHttp;
import com.rise.http.Urls;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by kai.wang on 5/19/14.
 */
public class Sync {

    // 同步总条数
    private int SYNC_COUNT;
    // 同步位置（已经同步了多少条）
    private int SYNC_INDEX = 0;

    private List<Item> items;
    private List<NotesItem> notes;

    public OnSyncListener onSyncListener;
    public OnProgressChangeListener onProgressChangeListener;

    public interface OnSyncListener {
        void onSyncUpSuccess();

        void onSyncDownSuccess();
    }

    public interface OnProgressChangeListener {
        void onGetSyncUpCount(int count);

        void onSyncUpProgressChange(int index);
    }

    public void up() {
        // count 所有未同步数据
        QueryHelper.findCount(SQL.COUNT_SYNC, new String[]{}, new QueryHelper.NumberCallBack() {
            @Override
            public void onFinish(Number num) {
                SYNC_COUNT = num.intValue();
                if (onProgressChangeListener != null)
                    onProgressChangeListener.onGetSyncUpCount(SYNC_COUNT);
            }
        });
        // 上传ITEM
        syncUpItems();

        // TODO 上传NOTE
    }

    public void down() {
    }

    private void syncUpItems() {
        QueryHelper.findBeans(Item.class, SQL.FIND_NOT_SYNC_ITEMS, new String[]{}, new QueryHelper.FindBeansCallBack<Item>() {
            @Override
            public void onFinish(List<Item> items) {
                if (items != null && items.size() != 0){
                    Sync.this.items = items;
                    syncUpRequest(items.get(0));
                }
            }
        });
    }

    private void syncUpRequest(Item item) {
        SYNC_INDEX ++;
        RequestParams params = new RequestParams();
        params.add("user_id", Const.USER_ID+"");
        params.add("uuid",item.getId());
        params.add("content",item.getContent());
        params.add("status",item.getStatus());
        params.add("create_at",item.getTime());
        AsyncHttp.post(Urls.SYNC_UP_ITEM, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                items.remove(0);
//                if (items.size() == 0) {
//                    return;
//                }
//                syncUpRequest(items.get(0));
                if(onProgressChangeListener != null){
                    onProgressChangeListener.onSyncUpProgressChange(SYNC_INDEX);
                }
                L.i(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }
}
