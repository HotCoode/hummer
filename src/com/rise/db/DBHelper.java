package com.rise.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.base.common.LoadSQL;
import com.rise.R;
import com.rise.common.Const;

import java.util.List;

/**
 * Created by kai.wang on 1/13/14.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static int DB_VERSION = 3;

    private static String DATABASE_PATH = Const.PATH;
    private static String DATABASE_FILENAME = "database.db";

    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_PATH + DATABASE_FILENAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
        loadPresetData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        createTables(db);
        loadPresetData(db);
    }

    private void createTables(SQLiteDatabase db){
        db.execSQL(SQL.CREATE_ITEMS);
        db.execSQL(SQL.CREATE_NOTES);
    }

    private void dropTables(SQLiteDatabase db){
        List<String> sqls = LoadSQL.readSqls(context, R.raw.drop_tables);
        for(String sql : sqls){
            db.execSQL(sql);
        }
    }

    /**
     * 加載預置數據
     * @param db
     */
    private void loadPresetData(SQLiteDatabase db){
        List<String> sqls = LoadSQL.readSqls(context, R.raw.preset_data);
        for(String sql : sqls){
            db.execSQL(sql);
        }
    }

    /**
     * 设置数据库版本
     *
     * @param version
     */
    public static void setDbVersion(int version) {
        DBHelper.DB_VERSION = version;
    }

    /**
     * 设置数据库路径
     *
     * @param path
     */
    public static void setDatabasePath(String path) {
        DBHelper.DATABASE_PATH = path;
    }

    /**
     * 设置数据库名
     *
     * @param filename
     */
    public static void setDatabaseFilename(String filename) {
        DBHelper.DATABASE_FILENAME = filename;
    }
}
