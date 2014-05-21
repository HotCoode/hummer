package com.rise.component;

import android.content.Context;
import android.os.Handler;
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
import com.rise.http.SyncJsonHandler;
import com.rise.http.Urls;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by kai.wang on 5/19/14.
 */
public class Sync {

    public OnSyncListener onSyncListener;
    public OnProgressChangeListener onProgressChangeListener;

    private Context context;

    public Sync(Context context){
        this.context = context;
    }

    public interface OnSyncListener {
        void onSyncUpSuccess();

        void onSyncDownSuccess();
    }

    public interface OnProgressChangeListener {
        void onGetSyncUpCount(int count);

        void onSyncUpProgressChange(int index);
    }

    public void count(final Handler handler, final int handlerMsg){
        // count 所有未同步数据
        QueryHelper.findCount(SQL.COUNT_SYNC, new String[]{}, new QueryHelper.NumberCallBack() {
            @Override
            public void onFinish(Number num) {
                if (onProgressChangeListener != null)
                    onProgressChangeListener.onGetSyncUpCount(num.intValue());
                handler.obtainMessage(handlerMsg,num.intValue()).sendToTarget();
            }
        });
    }

    public void getItems(final Handler handler, final int handlerMsg){
        QueryHelper.findBeans(Item.class, SQL.FIND_NOT_SYNC_ITEMS, new String[]{}, new QueryHelper.FindBeansCallBack<Item>() {
            @Override
            public void onFinish(List<Item> items) {
                handler.obtainMessage(handlerMsg,items).sendToTarget();
            }
        });
    }

    public void getNotes(final Handler handler, final int handlerMsg){
        QueryHelper.findBeans(NotesItem.class, SQL.FIND_NOT_SYNC_NOTES, new String[]{}, new QueryHelper.FindBeansCallBack<NotesItem>() {
            @Override
            public void onFinish(List<NotesItem> notes) {
                handler.obtainMessage(handlerMsg,notes).sendToTarget();
            }
        });
    }

    public void down(final Handler handler, final int handlerMsg) {
        // TODO
    }


    public void upItem(final Item item, final Handler handler, final int handlerMsg) {
        RequestParams params = new RequestParams();
        params.add("user_id", Const.USER_ID+"");
        params.add("uuid",item.getId());
        params.add("content",item.getContent());
        params.add("status",item.getStatus());
        params.add("create_at",item.getTime());
        AsyncHttp.post(Urls.SYNC_UP_ITEM, params, new SyncJsonHandler(context) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // TODO 更新数据库状态
                QueryHelper.update(SQL.UPDATE_ITEM_SYNC_STATUS_BY_ID,new String[]{item.getId()},null);
                L.i("onSuccess"+response);
                handler.sendEmptyMessage(handlerMsg);
            }
        });
    }

    public void upNote(final NotesItem note, final Handler handler, final int handlerMsg) {
        RequestParams params = new RequestParams();
        params.add("user_id", Const.USER_ID + "");
        params.add("uuid", note.getId());
        params.add("item_id", note.getItemId());
        params.add("status", note.getStatus() + "");
        params.add("create_at", note.getTime() + "");
        params.add("type", note.getType() + "");
        AsyncHttp.post(Urls.SYNC_UP_NOTE, params, new SyncJsonHandler(context) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // TODO 更新数据库状态
                QueryHelper.update(SQL.UPDATE_NOTE_SYNC_STATUS_BY_ID,new String[]{note.getId()},null);
                L.i(response);
                handler.sendEmptyMessage(handlerMsg);
            }
        });
    }
}
