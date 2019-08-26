package wangke.netlib;

import android.os.Build;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;
import com.pongo.commonlibray.*;
import com.pongo.commonlibray.BuildConfig;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Project : Net Lib
 * Created by 王可 on 16/8/31.
 */
public class NetRetrofit {
    /**
     * Instance of Retrofit
     *
     * @param URL_STRING URL header. Must end with "/"
     *
     * @return
     */
    public static Retrofit getInstance(final String URL_STRING, final String token) {
        if (TextUtils.isEmpty(URL_STRING)) {
            LogUtils.e("url为空");
            return null;
        }
        return new Retrofit.Builder()
                .baseUrl(URL_STRING)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getDefaultClient(token))
                .build();

    }

    public static OkHttpClient getDefaultClient(final String token) {
      if (TextUtils.isEmpty(token)){
          return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
              @Override
              public Response intercept(Chain chain) throws IOException {
                  Request request = chain.request()
                          .newBuilder()
                          .build();
                  return chain.proceed(request);
              }
          }).connectTimeout(30, TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS).writeTimeout(30,TimeUnit.SECONDS).build();
      }else {
        final String info = "XHNews/" + BuildConfig.VERSION_NAME +" (" + Build.MODEL + "; " + "Android " +
                Build.VERSION.RELEASE +"; " + "Scale/2.00)";
          return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
              @Override
              public Response intercept(Chain chain) throws IOException {
                  Request request = chain.request()
                          .newBuilder()
                          .addHeader("ECP-COOKIE", token)
                          .addHeader("User-Agent", info)
                          .build();
                  return chain.proceed(request);
              }
          }).connectTimeout(30, TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS).writeTimeout(30,TimeUnit.SECONDS).build();
      }



    }
}
