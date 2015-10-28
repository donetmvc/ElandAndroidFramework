package com.eland.elandandroidframework.Util;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by elandmac on 15/10/8.
 */
public class HttpRequest {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, JsonHttpResponseHandler response) {
        client.get(url, params, response);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }
}


