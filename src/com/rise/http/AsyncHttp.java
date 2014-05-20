package com.rise.http;

import com.base.L;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AsyncHttp {
//    private static final String BASE_URL = "http://xiiqi.com:8899/";

    private static final String TEST_URL = "http://192.168.0.103:8000/r/api/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        L.i("request " + TEST_URL + relativeUrl);
        return TEST_URL + relativeUrl;
    }
}
