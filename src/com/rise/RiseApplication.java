package com.rise;

import android.app.Application;
import android.os.AsyncTask;

import com.base.orm.QueryHelper;
import com.rise.db.DBHelper;
import com.rise.db.SQL;

/**
 * Created by kai.wang on 2/14/14.
 */
public class RiseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new LoadTask().execute();
    }

    private class LoadTask extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            SQL.loadSql(RiseApplication.this);
            return null;
        }
    }
}
