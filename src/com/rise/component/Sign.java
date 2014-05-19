package com.rise.component;

import android.content.Context;

import com.base.L;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rise.http.AsyncHttp;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by kai.wang on 5/19/14.
 */
public class Sign {

    public static void in(Context context,String username,String password) {
        RequestParams params = new RequestParams();
        params.add("username",username);
        params.add("password",password);
        AsyncHttp.post("sign_in", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                L.i(response);
            }
        });
    }

    public static void up(Context context) {
    }
}
