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
import com.rise.component.BaseActivity;
import com.rise.db.SQL;

/**
 * Created by kai.wang on 3/13/14.
 */
public class LoadingActivity extends BaseActivity implements SQL.OnSqlLoadFinish,Animation.AnimationListener{
    private boolean sqlLoadFinish = false;
    private boolean animationEnd = false;
    private View loadAnimView;
    private Animation animation;
    public void onCreate(Bundle savedInstanceState) {
        L.i("LoadingActivity");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loading);
        SQL.setOnSqlLoadFinish(this);
        new LoadTask().execute();

        loadAnimView = findViewById(R.id.loading_anim_view);
        animation = AnimationUtils.loadAnimation(this,R.anim.activity_loading);
        animation.setAnimationListener(this);
        loadAnimView.startAnimation(animation);
    }

    @Override
    public void onSqlLoadFinish() {
        L.i("onSqlLoadFinish");
        sqlLoadFinish = true;
        if(animationEnd){
            allFinish();
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }
    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        L.i("onAnimationEnd");
        animationEnd = true;
        if(sqlLoadFinish){
            allFinish();
        }
    }

    private void allFinish(){
        Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class LoadTask extends AsyncTask<Void,Integer,Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            SQL.loadSql(LoadingActivity.this);
            return null;
        }
    }
}