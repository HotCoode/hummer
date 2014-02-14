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

    public static void loadSql(Context context){
        // create table
        CREATE_ITEMS = LoadSQL.readSql(context, R.raw.items);
        CREATE_NOTES = LoadSQL.readSql(context, R.raw.notes);
        FIND_ITEMS_BY_STATUS = LoadSQL.readSql(context,R.raw.find_items_by_status);
        PUT_NEW_NOTE = LoadSQL.readSql(context,R.raw.put_new_note);
    }
}
