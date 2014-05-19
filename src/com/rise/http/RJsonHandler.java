package com.rise.http;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by kai.wang on 5/19/14.
 */
public class RJsonHandler extends JsonHttpResponseHandler {

    private Context context;

    public RJsonHandler(Context context){
        this.context = context;
    }

//    @Override
//    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//        Toast
//    }
}
