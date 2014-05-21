package com.rise.http;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.rise.R;

import org.apache.http.Header;

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
        Toast.makeText(context, R.string.sync_fail,Toast.LENGTH_SHORT).show();
        // TODO 发送同步失败广播
    }
}
