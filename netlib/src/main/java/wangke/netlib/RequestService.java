package wangke.netlib;

import android.content.Context;

import com.apkfuns.logutils.LogUtils;

import java.io.IOException;

import exception.ExceptionEngine;
import exception.XhsError;
import exception.XhsException;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import wangke.netlib.interfaces.NetInterface;
import wangke.netlib.utils.EncrypytUtil;
import wangke.netlib.utils.JsonConvert;

/**
 * Project : Net Lib
 * Created by 王可 on 16/8/31.
 * Use for request data to server, and get response data.
 * The request object will be created as json package, then json package will be serialized as json String.
 * The json String will be encrypted and sent to server. When response data has been decrypted and deserialized ,
 * it returns json package object which will be handled.
 *
 * @param <B> Json Body
 * @param <P> Json package
 * @param <H> Json header
 */
public class RequestService<B, P, H> {
    private NetInterface<P, B, H> netInterface;
    private Class packageClass;
    private Context context;

    /**
     * Instance of RequestService.
     */
    public RequestService(NetInterface<P, B, H> netInterface, Class packageClass, Context context) {

        this.packageClass = packageClass;
        this.netInterface = netInterface;
        this.context = context;
    }


    /**
     * Default request function.
     *
     * @param bodyObject
     * @param code
     *
     * @return
     */
    public Observable<B> requestDefault(B bodyObject, String code) {

        final P p = netInterface.createPackage(bodyObject, netInterface.createHeader(code), code);
        String jsonStr = new JsonConvert(netInterface).writeObjectToJsonString(p);
        LogUtils.d("request报文:" + jsonStr);

        jsonStr = EncrypytUtil.enrypytJson(jsonStr);
        return NetRetrofit
                .getInstance(netInterface.getUrl(), netInterface.getToken())
                .create(NetService.class)
                .getResult(jsonStr)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ResponseBody>>() {
                    @Override
                    public Observable<? extends ResponseBody> call(Throwable throwable) {
                        LogUtils.e(throwable);
                        return Observable.error(ExceptionEngine.handleException(throwable));
                    }
                })
                .map(new Func1<ResponseBody, P>() {
                    @Override
                    public P call(ResponseBody responseBody) {
                        try {
                            String resultJsonStr = responseBody.string();
                            LogUtils.d("返回报文:" + resultJsonStr);
                            resultJsonStr = EncrypytUtil.decryptJson(resultJsonStr);
                            P resultPackage = (P) new JsonConvert(netInterface).readJsonStringToObject(resultJsonStr, packageClass);
                            return resultPackage;
                        } catch (IOException e) {
                            LogUtils.e("can not read json String to Object: " + e);
                            throw Exceptions.propagate(new RuntimeException(XhsException.ERROR_DESERIALIZE.getMessage()));
                        }
                    }
                }).map(new Func1<P, B>() {
                    @Override
                    public B call(P p) {
                        try {
                            if (netInterface.handleHeader(netInterface.obtainHeader(p), context)) {

                                return null;
                            }
                        } catch (XhsException e) {

                            throw Exceptions.propagate(new RuntimeException(e.getDisplayMessage()));
                        }
                        return netInterface.obtainBody(p);
                    }
                }).filter(new Func1<B, Boolean>() {
                    @Override
                    public Boolean call(B b) {
                        if (null == b){
                            throw Exceptions.propagate(new RuntimeException(XhsException.ERROR_MSG_RESPONSE));

                        }else {
                            return true;
                        }
                    }
                });

    }

}
