package com.rise.component;

import android.content.Context;
import android.content.Intent;

import com.base.L;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rise.common.Const;
import com.rise.common.RiseUtil;
import com.rise.http.AsyncHttp;
import com.rise.http.Urls;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kai.wang on 5/19/14.
 */
public class Sign {

    public static void in(final Context context,String username,String password) {
        RequestParams params = new RequestParams();
        params.add("username",username);
        params.add("password",password);
        AsyncHttp.post(Urls.SIGN_IN, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int code = response.getInt("code");
                    Intent intent;
                    if(code == 200){
                        intent = new Intent(Const.ACTION_SIGN_SUCCESS);
                        intent.putExtra("user_id",response.getInt("user_id"));
                    }else{
                        intent = new Intent(Const.ACTION_SIGN_FAIL);
                    }
                    context.sendBroadcast(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                L.i(responseString);
                RiseUtil.requestFailToast(context);
                Intent intent = new Intent(Const.ACTION_SIGN_FAIL);
                context.sendBroadcast(intent);
            }
        });
    }

    public static void up(Context context) {
    }
}
