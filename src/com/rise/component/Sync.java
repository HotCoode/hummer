package com.rise.component;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.base.L;
import com.base.common.StringUtils;
import com.base.orm.QueryHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rise.bean.Item;
import com.rise.bean.NotesItem;
import com.rise.common.Const;
import com.rise.db.SQL;
import com.rise.db.SqlConst;
import com.rise.http.AsyncHttp;
import com.rise.http.SyncJsonHandler;
import com.rise.http.Urls;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

    // TODO
    public void down(final Handler handler, final int handlerMsg) {
        AsyncHttp.post(Urls.SYNC_DOWN+Const.USER_ID+"/", null, new SyncJsonHandler(context) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    L.i(response);
                    int code = response.getInt("code");
                    if(code == 200){
                        List<Item> items = new ArrayList<Item>();
                        List<NotesItem> notes = new ArrayList<NotesItem>();
                        JSONArray itemArray = response.getJSONArray("items");
                        JSONArray noteArray = response.getJSONArray("notes");
                        if(itemArray != null){
                            for(int i = 0; i< itemArray.length();i++){
                                JSONObject itemJson = itemArray.getJSONObject(i);
                                Item item = new Item();
                                item.setContent(itemJson.getString("content"));
                                item.setId(itemJson.getString("uuid"));
                                item.setStatus(itemJson.getString("status"));
                                item.setTime(itemJson.getString("time"));
                                items.add(item);
                            }
                        }

                        if(noteArray != null){
                            for(int i = 0; i< noteArray.length();i++){
                                JSONObject noteJson = noteArray.getJSONObject(i);
                                NotesItem note = new NotesItem();
                                note.setItemId(noteJson.getString("item_id"));
                                note.setId(noteJson.getString("uuid"));
                                note.setStatus(noteJson.getLong("status"));
                                note.setType(noteJson.getLong("type"));
                                note.setTime(noteJson.getLong("time"));
                                notes.add(note);
                            }
                        }
                        new SaveSyncDownDataThread(items,notes,handler,handlerMsg).start();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void upItem(final Item item, final Handler handler, final int handlerMsg) {
        RequestParams params = new RequestParams();
        params.add("user_id", Const.USER_ID+"");
        params.add("uuid",item.getId());
        params.add("content",item.getContent());
        params.add("status",item.getStatus());
        params.add("time",item.getTime());
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
        params.add("time", note.getTime() + "");
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

    private class SaveSyncDownDataThread extends Thread{
        private List<Item> items = new ArrayList<Item>();
        private List<NotesItem> notes = new ArrayList<NotesItem>();
        private Handler handler;
        private int handlerMsg;

        public SaveSyncDownDataThread(List<Item> items, List<NotesItem> notes,Handler handler, final int handlerMsg){
            this.items = items;
            this.notes = notes;
            this.handler = handler;
            this.handlerMsg = handlerMsg;
        }

        @Override
        public void run() {
            if(items.size() != 0){
                for(Item item : items){
                    Cursor cursor = QueryHelper.getDb().rawQuery("select id,time from items where id=?", new String[]{item.getId()});
                    if(cursor.moveToNext()){
                        String id = cursor.getString(0);
                        String time = cursor.getString(1);
                        if(!TextUtils.isEmpty(id)){
                            // 服务器的数据比本地的新
                            if(StringUtils.toLong(item.getTime()) > StringUtils.toLong(time)){
                                ContentValues values = new ContentValues();
                                values.put("content",item.getContent());
                                values.put("time",item.getTime());
                                values.put("status",item.getStatus());
                                QueryHelper.getDb().update("items",values,"id=?",new String[]{id});
                            }
                        }
                    }else{
                        // 本地没有服务器的数据
                        ContentValues values = new ContentValues();
                        values.put("id",item.getId());
                        values.put("content",item.getContent());
                        values.put("time",item.getTime());
                        values.put("status",item.getStatus());
                        values.put("sync", SqlConst.SYNC_OK);
                        QueryHelper.getDb().insert("items",null,values);
                    }
                }
            }

            if(notes.size() != 0){
                for(NotesItem note : notes){
                    Cursor cursor = QueryHelper.getDb().rawQuery("select id,time from notes where id=?", new String[]{note.getId()});
                    if(cursor.moveToNext()){
                        String id = cursor.getString(0);
                        String time = cursor.getString(1);
                        if(!TextUtils.isEmpty(id)){
                            // 服务器的数据比本地的新
                            if(note.getTime() > StringUtils.toLong(time)){
                                ContentValues values = new ContentValues();
                                values.put("status",note.getStatus());
                                values.put("type",note.getType());
                                values.put("item_id",note.getItemId());
                                values.put("time",note.getTime());
                                QueryHelper.getDb().update("notes",values,"id=?",new String[]{id});
                            }
                        }
                    }else{
                        ContentValues values = new ContentValues();
                        values.put("id",note.getId());
                        values.put("status",note.getStatus());
                        values.put("type",note.getType());
                        values.put("item_id",note.getItemId());
                        values.put("time",note.getTime());
                        values.put("sync", SqlConst.SYNC_OK);
                        QueryHelper.getDb().insert("notes",null,values);
                    }
                }
            }
            handler.sendEmptyMessage(handlerMsg);
        }
    }

}
