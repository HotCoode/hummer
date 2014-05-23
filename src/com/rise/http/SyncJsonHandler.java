package com.rise.http;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.base.L;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rise.R;
import com.rise.common.Const;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by kai.wang on 5/19/14.
 */
public class SyncJsonHandler extends JsonHttpResponseHandler {

    private Context context;

    public SyncJsonHandler(Context context){
        this.context = context;
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        onFailure(throwable,responseString);
    }


    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        onFailure(throwable,errorResponse);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        onFailure(throwable,errorResponse);
    }

    private void onFailure(Throwable throwable,Object response){
        L.i("onFailure,response:"+response+",throwable:"+throwable);
        Toast.makeText(context, R.string.sync_fail,Toast.LENGTH_SHORT).show();
        context.sendBroadcast(new Intent(Const.ACTION_SYNC_FAIL));
    }
}
