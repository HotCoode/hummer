package com.rise;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.base.L;
import com.base.orm.QueryHelper;
import com.rise.component.BaseActivity;
import com.rise.db.DBHelper;
import com.rise.db.SQL;

/**
 * Created by kai.wang on 3/13/14.
 */
public class LoadingActivity extends BaseActivity implements SQL.OnSqlLoadFinish{
    public void onCreate(Bundle savedInstanceState) {
        L.i("LoadingActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        SQL.setOnSqlLoadFinish(this);
        new LoadTask().execute();
    }

    @Override
    public void onSqlLoadFinish() {
        L.i("onSqlLoadFinish");
        QueryHelper.init(new DBHelper(LoadingActivity.this));
        allFinish();
    }

    private void allFinish(){
        Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);
    }

    private class LoadTask extends AsyncTask<Void,Integer,Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            SQL.loadSql(LoadingActivity.this);
            return null;
        }
    }

    @Override
    protected void onRestart() {
        L.i("onRestart");
        super.onRestart();
    }
}