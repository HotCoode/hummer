package com.rise.http;

import android.content.Context;
import android.widget.Toast;

import com.base.L;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rise.R;

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
        onFailure(responseString);
        Toast.makeText(context, R.string.sync_fail,Toast.LENGTH_SHORT).show();
        // TODO 发送同步失败广播
    }


    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        onFailure(errorResponse);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        onFailure(errorResponse);
    }

    private void onFailure(Object response){
        L.i("onFailure,response:"+response);
    }
}
