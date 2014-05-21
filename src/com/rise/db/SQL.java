package com.rise.db;

import android.content.Context;

import com.base.common.LoadSQL;
import com.rise.R;

/**
 * Created by kai.wang on 2/13/14.
 */
public class SQL {
    // create table
    public static String CREATE_ITEMS;
    public static String CREATE_NOTES;
    public static String FIND_ITEMS_BY_STATUS;
    public static String PUT_NEW_NOTE;
	public static String FIND_NOTES_BY_TYPE;
    public static String ADD_ITEM;
    public static String DELETE_NOTE_BY_ID;
    public static String COUNT_NOTES_BY_TYPE_AND_MONTH;
    public static String ADD_NOTE_ONLY_ITEM;
    public static String UPDATE_ITEM_BY_ID;
    public static String DELETE_ITEM_BY_ID;
    public static String COUNT_SYNC;
    public static String FIND_NOT_SYNC_ITEMS;
    public static String FIND_NOT_SYNC_NOTES;

    public interface OnSqlLoadFinish {
        void onSqlLoadFinish();
    }

    private static OnSqlLoadFinish onSqlLoadFinish;

    public static void setOnSqlLoadFinish(OnSqlLoadFinish onSqlLoadFinish){
        SQL.onSqlLoadFinish = onSqlLoadFinish;
    }

    public static void loadSql(Context context){
        // create table
        CREATE_ITEMS = LoadSQL.readSql(context, R.raw.items);
        CREATE_NOTES = LoadSQL.readSql(context, R.raw.notes);
        FIND_ITEMS_BY_STATUS = LoadSQL.readSql(context,R.raw.find_items_by_status);
        PUT_NEW_NOTE = LoadSQL.readSql(context,R.raw.add_new_note);
	    FIND_NOTES_BY_TYPE = LoadSQL.readSql(context,R.raw.find_notes_by_type);
        ADD_ITEM = LoadSQL.readSql(context,R.raw.add_item);
        DELETE_NOTE_BY_ID = LoadSQL.readSql(context,R.raw.delete_note_by_id);
        COUNT_NOTES_BY_TYPE_AND_MONTH = LoadSQL.readSql(context,R.raw.count_notes_by_type_and_month);
        ADD_NOTE_ONLY_ITEM = LoadSQL.readSql(context,R.raw.add_note_only_item);
        UPDATE_ITEM_BY_ID = LoadSQL.readSql(context,R.raw.update_item_by_id);
        DELETE_ITEM_BY_ID = LoadSQL.readSql(context,R.raw.delete_item_by_id);
        COUNT_SYNC = LoadSQL.readSql(context,R.raw.count_sync);
        FIND_NOT_SYNC_ITEMS = LoadSQL.readSql(context,R.raw.find_not_sync_items);
        FIND_NOT_SYNC_NOTES = LoadSQL.readSql(context,R.raw.find_not_sync_notes);

        if(onSqlLoadFinish != null) onSqlLoadFinish.onSqlLoadFinish();
    }
}
