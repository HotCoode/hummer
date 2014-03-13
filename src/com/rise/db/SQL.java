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

    public interface OnSqlLoadFinish{
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
        PUT_NEW_NOTE = LoadSQL.readSql(context,R.raw.put_new_note);
	    FIND_NOTES_BY_TYPE = LoadSQL.readSql(context,R.raw.find_notes_by_type);
        ADD_ITEM = LoadSQL.readSql(context,R.raw.add_item);
        DELETE_NOTE_BY_ID = LoadSQL.readSql(context,R.raw.delete_note_by_id);
        COUNT_NOTES_BY_TYPE_AND_MONTH = LoadSQL.readSql(context,R.raw.count_notes_by_type_and_month);

        if(onSqlLoadFinish != null) onSqlLoadFinish.onSqlLoadFinish();
    }
}
