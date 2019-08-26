package com.ailk.pmph.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.utils
 * 作者: Chrizz
 * 时间: 2017/4/7
 */

public class UnionIdUtils {

    private static AsyncHttpClient client = new SyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(30*1000);
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(30*1000);
        client.post(url, params, responseHandler);
    }

}
