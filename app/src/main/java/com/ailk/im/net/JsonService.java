package com.ailk.im.net;

import android.content.Context;
import android.graphics.Bitmap;

import com.ailk.im.model.UploadResp;
import com.ailk.im.tool.IMConstant;
import com.ailk.im.tool.ImageUtil;
import com.apkfuns.logutils.LogUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * Project : XHS
 * Created by 王可 on 2016/11/25.
 */

public class JsonService {
    //图片
    public static abstract class CallBackImage<T> {

        public abstract void oncallback(T t);

        public abstract void onError(Throwable throwable);

        protected boolean getNetWorkError() {
            return false;
        }
    }

    /**
     * @param context  Context
     * @param bitmap   图片 不需要进行压缩，接口会进行压缩
     * @param callBack 回调函数
     */
    public static void requestImageByPost(final Context context, Bitmap bitmap, final CallBackImage<UploadResp> callBack) {

        RequestParams params = new RequestParams();
        params.put("image", ImageUtil.Bitmap2IS(ImageUtil.comp(bitmap)), "temp.jpg");
        params.add("filename", "temp.jpg");
        params.add("name", "image");
//        params.add("accessToken", SdkClient.getInstance().getAccessToken());
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = null;
                try {
                    response = new String(responseBody, "utf-8");
                    UploadResp uploadResp = new ObjectMapper().readValue(response,UploadResp.class);
                    LogUtils.d(uploadResp);
                    callBack.oncallback(uploadResp);

                } catch (UnsupportedEncodingException e) {
                    LogUtils.e(e);
                    callBack.onError(e);
                }  catch (IOException e) {
                    callBack.onError(e);
                    e.printStackTrace();
                    callBack.onError(e);
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                LogUtils.e(error);
                callBack.onError(error);

            }
        };
//        BasicClientCookie newCookie = new BasicClientCookie("LACE-APP-COOKIE", AppUtility.getInstance().getSessionId());
//        cookieStore.addCookie(newCookie);
        asyncHttpClient.setTimeout(120000);
        asyncHttpClient.post(context, IMConstant.UPLOAD_IMG_URL, params, handler);
    }
}
