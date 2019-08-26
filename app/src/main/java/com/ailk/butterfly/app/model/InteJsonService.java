package com.ailk.butterfly.app.model;

import android.content.Context;
import android.content.DialogInterface;

import com.ai.ecp.app.req.Cms101Req;
import com.ai.ecp.app.req.Cms102Req;
import com.ai.ecp.app.req.Ord101Req;
import com.ai.ecp.app.req.Ord102Req;
import com.ai.ecp.app.req.Ord103Req;
import com.ai.ecp.app.req.Ord104Req;
import com.ai.ecp.app.req.Ord105Req;
import com.ai.ecp.app.req.Ord106Req;
import com.ai.ecp.app.req.Ord107Req;
import com.ai.ecp.app.req.Ord108Req;
import com.ai.ecp.app.req.Ord109Req;
import com.ai.ecp.app.req.Ord110Req;
import com.ai.ecp.app.req.Ord111Req;
import com.ai.ecp.app.req.Ord112Req;
import com.ai.ecp.app.req.Pay101Req;
import com.ai.ecp.app.req.Pay102Req;
import com.ai.ecp.app.req.Pay103Req;
import com.ai.ecp.app.req.Pay104Req;
import com.ai.ecp.app.req.Pointmgds001Req;
import com.ai.ecp.app.req.Pointmgds002Req;
import com.ai.ecp.app.req.Pointmgds006Req;
import com.ai.ecp.app.req.Pointmgds007Req;
import com.ai.ecp.app.req.Pointmgds011Req;
import com.ai.ecp.app.req.Pointmgds014Req;
import com.ai.ecp.app.resp.Cms101Resp;
import com.ai.ecp.app.resp.Cms102Resp;
import com.ai.ecp.app.resp.Ord101Resp;
import com.ai.ecp.app.resp.Ord102Resp;
import com.ai.ecp.app.resp.Ord103Resp;
import com.ai.ecp.app.resp.Ord104Resp;
import com.ai.ecp.app.resp.Ord105Resp;
import com.ai.ecp.app.resp.Ord106Resp;
import com.ai.ecp.app.resp.Ord107Resp;
import com.ai.ecp.app.resp.Ord108Resp;
import com.ai.ecp.app.resp.Ord109Resp;
import com.ai.ecp.app.resp.Ord110Resp;
import com.ai.ecp.app.resp.Ord111Resp;
import com.ai.ecp.app.resp.Ord112Resp;
import com.ai.ecp.app.resp.Pay101Resp;
import com.ai.ecp.app.resp.Pay102Resp;
import com.ai.ecp.app.resp.Pay103Resp;
import com.ai.ecp.app.resp.Pay104Resp;
import com.ai.ecp.app.resp.Pointmgds001Resp;
import com.ai.ecp.app.resp.Pointmgds002Resp;
import com.ai.ecp.app.resp.Pointmgds006Resp;
import com.ai.ecp.app.resp.Pointmgds007Resp;
import com.ai.ecp.app.resp.Pointmgds011Resp;
import com.ai.ecp.app.resp.Pointmgds014Resp;
import com.ailk.pmph.utils.AppUtility;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.EncrypytUtil;
import com.ailk.pmph.utils.HandlerErroUtil;
import com.ailk.pmph.utils.JsonConvert;
import com.ailk.pmph.utils.LogUtil;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/7.
 */
@Deprecated
public class InteJsonService {
    private static final String BASIC_NAME = "msg";
    private static final String UTF_8 = "UTF-8";
    private AQuery mAQuery;
    private JsonConvert mConverter = new JsonConvert();
    private AjaxCallback<String> cb;

    public InteJsonService(Context context) {
        this.mAQuery = new AQuery(context);
    }

    public AQuery getAquery() {
        return mAQuery;
    }

    public void cancel() {
        mAQuery.ajaxCancel();
    }

    private Map<String, Object> createHttpEntiry(AppBody request, String bizCode)
            throws UnsupportedEncodingException {
        String jsonStr = mConverter.writeObjectToJsonString(createDataPackage(
                request, bizCode));
        LogUtil.e("request : " + jsonStr);
        jsonStr = EncrypytUtil.enrypytJson(jsonStr);
//		LogUtil.e("en request: "+jsonStr);

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair(BASIC_NAME, jsonStr));

        HttpEntity entity = new UrlEncodedFormEntity(pairs, UTF_8);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AQuery.POST_ENTITY, entity);
        return params;
    }

    private AppDatapackage createDataPackage(AppBody request, String bizCode) {
        AppDatapackage dataPackage = new AppDatapackage();

        AppHeader requestHeader = new AppHeader();
        requestHeader.setBizCode(bizCode);
        requestHeader.setIdentityId("");
        requestHeader.setMode("");
        requestHeader.setRespCode("");
        requestHeader.setSign("");
        requestHeader.setRespMsg("");
//        requestHeader.setMode("1");
        dataPackage.setHeader(requestHeader);
        dataPackage.setBody(request);
        return dataPackage;
    }

    public void requestCms101(Context context, Cms101Req request, boolean isShowProgress, CallBack<Cms101Resp> callBack){
        request(request,"cms101",callBack,Cms101Resp.class,context,isShowProgress,true);
    }

    public void requestCms102(Context context, Cms102Req request, boolean isShowProgress, CallBack<Cms102Resp> callBack){
        request(request,"cms102",callBack,Cms102Resp.class,context,isShowProgress,true);
    }

    public void requestPointGds001(Context context, Pointmgds001Req request, boolean isShowProgress, CallBack<Pointmgds001Resp> callBack){
        request(request,"pointmgds001",callBack,Pointmgds001Resp.class,context,isShowProgress,true);
    }

    public void requestPointGds002(Context context, Pointmgds002Req request, boolean isShowProgress, CallBack<Pointmgds002Resp> callBack){
        request(request,"pointmgds002",callBack,Pointmgds002Resp.class,context,isShowProgress,true);
    }

    public void requestPointGds006(Context context, Pointmgds006Req request, boolean isShowProgress, CallBack<Pointmgds006Resp> callBack){
        request(request,"pointmgds006",callBack,Pointmgds006Resp.class,context,isShowProgress,true);
    }

    public void requestPointGds007(Context context, Pointmgds007Req request, boolean isShowProgress, CallBack<Pointmgds007Resp> callBack){
        request(request,"pointmgds007",callBack,Pointmgds007Resp.class,context,isShowProgress,true);
    }

    public void requestPointGds011(Context context, Pointmgds011Req request, boolean isShowProgress, CallBack<Pointmgds011Resp> callBack){
        request(request,"pointmgds011",callBack,Pointmgds011Resp.class,context,isShowProgress,true);
    }

    public void requestPointGds014(Context context, Pointmgds014Req request, boolean isShowProgress, CallBack<Pointmgds014Resp> callBack){
        request(request,"pointmgds014",callBack,Pointmgds014Resp.class,context,isShowProgress,true);
    }

    public void requestOrd101(Context context, Ord101Req request, boolean isShowProgress, CallBack<Ord101Resp> callBack){
        request(request,"ord101",callBack,Ord101Resp.class,context,isShowProgress,true);
    }

    public void requestOrd102(Context context, Ord102Req request, boolean isShowProgress, CallBack<Ord102Resp> callBack){
        request(request,"ord102",callBack,Ord102Resp.class,context,isShowProgress,true);
    }

    public void requestOrd103(Context context, Ord103Req request, boolean isShowProgress, CallBack<Ord103Resp> callBack){
        request(request,"ord103",callBack,Ord103Resp.class,context,isShowProgress,true);
    }

    public void requestOrd104(Context context, Ord104Req request, boolean isShowProgress, CallBack<Ord104Resp> callBack){
        request(request,"ord104",callBack,Ord104Resp.class,context,isShowProgress,true);
    }

    public void requestOrd105(Context context, Ord105Req request, boolean isShowProgress, CallBack<Ord105Resp> callBack){
        request(request,"ord105",callBack,Ord105Resp.class,context,isShowProgress,true);
    }

    public void requestOrd106(Context context, Ord106Req request, boolean isShowProgress, CallBack<Ord106Resp> callBack){
        request(request,"ord106",callBack,Ord106Resp.class,context,isShowProgress,true);
    }

    public void requestOrd107(Context context, Ord107Req request, boolean isShowProgress, CallBack<Ord107Resp> callBack){
        request(request,"ord107",callBack,Ord107Resp.class,context,isShowProgress,true);
    }

    public void requestOrd108(Context context, Ord108Req request, boolean isShowProgress, CallBack<Ord108Resp> callBack){
        request(request,"ord108",callBack,Ord108Resp.class,context,isShowProgress,true);
    }

    public void requestOrd109(Context context, Ord109Req request, boolean isShowProgress, CallBack<Ord109Resp> callBack){
        request(request,"ord109",callBack,Ord109Resp.class,context,isShowProgress,true);
    }

    public void requestOrd110(Context context, Ord110Req request, boolean isShowProgress, CallBack<Ord110Resp> callBack){
        request(request,"ord110",callBack,Ord110Resp.class,context,isShowProgress,true);
    }

    public void requestOrd111(Context context, Ord111Req request, boolean isShowProgress, CallBack<Ord111Resp> callBack){
        request(request,"ord111",callBack,Ord111Resp.class,context,isShowProgress,true);
    }

    public void requestOrd112(Context context, Ord112Req request, boolean isShowProgress, CallBack<Ord112Resp> callBack){
        request(request,"ord112",callBack,Ord112Resp.class,context,isShowProgress,true);
    }

    public void requestPay101(Context context, Pay101Req request, boolean isShowProgress, CallBack<Pay101Resp> callBack){
        request(request,"pay101",callBack,Pay101Resp.class,context,isShowProgress,true);
    }

    public void requestPay102(Context context, Pay102Req request, boolean isShowProgress, CallBack<Pay102Resp> callBack){
        request(request,"pay102",callBack,Pay102Resp.class,context,isShowProgress,true);
    }

    public void requestPay103(Context context, Pay103Req request, boolean isShowProgress, CallBack<Pay103Resp> callBack){
        request(request,"pay103",callBack,Pay103Resp.class,context,isShowProgress,true);
    }

    public void requestPay104(Context context, Pay104Req request, boolean isShowProgress, CallBack<Pay104Resp> callBack){
        request(request,"pay104",callBack,Pay104Resp.class,context,isShowProgress,true);
    }

    public void request(final AppBody request, final String bizCode,
                        final CallBack callback, final Class class1, final Context context,
                        final boolean isShowProgress, final boolean isShowToast) {
        try {
//            temp start
//            temp end
            //当界面中有从url传的参数，但是页面没有转存到params时，自动加入
//            if (context instanceof IMBaseActivity && request != null) {
//                if (((IMBaseActivity) context).getParamsMap() != null) {
//                    if (request.getExpand() == null) {
//                        Map<String, Object> expand = new HashMap<String, Object>();
//                        request.setExpand(expand);
//                    }
//                    if (request.getExpand().get("params") == null) {
//                        request.getExpand().put("params", ((IMBaseActivity) context).getParamsMap());
//                    }
//                }
//            }

            if (isShowProgress) {
                DialogUtil.showCustomerProgressDialog(context);
            }

            cb = new AjaxCallback<String>() {
                private int submitIndex = 3;

                @Override
                public void callback(String url, String object,
                                     AjaxStatus status) {

                    LogUtil.e(" statusCode = " + status.getCode());
                    if (isShowProgress) {
                        DialogUtil.dismissDialog();
                    }
                    submitIndex--;

                    if (HandlerErroUtil.handlerStatus(context, status,
                            isShowToast && (submitIndex == 0))) {
                        if (isShowProgress) {
                            DialogUtil.dismissDialog();
                        }
                        LogUtil.e("erro code = " + status.getCode() + " url = "
                                + InteRequestURL.getURL());

                        if (callback.getNetWorkError()) {
                            return;
                        }
                        if (submitIndex > 0) {
                            LogUtil.e("submitIndex = " + submitIndex + "  retrcy");
                            try {
                                if (isShowProgress) {
                                    DialogUtil.showCustomerProgressDialog(context);
                                }
                                mAQuery.ajax(InteRequestURL.getURL(), createHttpEntiry(request, bizCode), String.class,
                                        cb);
                                return;
                            } catch (UnsupportedEncodingException e) {
                                if (isShowProgress) {
                                    DialogUtil.dismissDialog();
                                }
                                LogUtil.e(e);
                            }
                        }

                        DialogAnotherUtil.dismissDialog();
                        DialogAnotherUtil.showCustomAlertDialogWithMessage(context, null,
                                "网络连接错误", "重试", "取消", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            if (isShowProgress) {
                                                DialogUtil.showCustomerProgressDialog(context);
                                            }
                                            mAQuery.ajax(InteRequestURL.getURL(), createHttpEntiry(request, bizCode), String.class,
                                                    cb);
                                        } catch (UnsupportedEncodingException e) {
                                            if (isShowProgress) {
                                                DialogUtil.dismissDialog();
                                            }
                                            LogUtil.e(e);
                                        }
                                    }
                                }, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (isShowProgress) {
                                            DialogUtil.dismissDialog();
                                        }
                                        callback.onErro(null);
                                    }
                                });
                    } else {
                        onCallBack(context, callback, class1, object,
                                isShowToast);
                    }
                }
            };

            LogUtil.e("request Url = " + InteRequestURL.getURL() + " sessionId=" + AppUtility.getInstance().getSessionId());
            cb.header("ECP-COOKIE", AppUtility.getInstance().getSessionId());
            mAQuery.ajax(InteRequestURL.getURL(), createHttpEntiry(request, bizCode), String.class,
                    cb);
        } catch (UnsupportedEncodingException e) {
            if (isShowProgress) {
                DialogUtil.dismissDialog();
            }
            LogUtil.e(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void onCallBack(Context context, final CallBack callback,
                            final Class class1, String object, boolean isShowToast) {
//		 LogUtil.e(url);
        if (object != null) {
//			 LogUtil.e("unjson : " + object);
            object = EncrypytUtil.decryptJson(object);
            LogUtil.e("json: " + object);
            if (object == null) {
                callback.onErro(null);
            }
            AppDatapackage data = null;
            try {
                ClassLoader loader = context.getClassLoader();
                Class<?> cls = loader.loadClass(AppDatapackage.class.getName());
                data = (AppDatapackage) new JsonConvert().readJsonStringToObject(
                        object, cls);
            } catch (ClassNotFoundException e) {
                LogUtil.e(e);
            }

//			 LogUtil.e(data);
            if (data == null) {
                DialogUtil.dismissDialog();
                callback.onErro(null);
                return;
            }
            AppHeader header = (AppHeader) data.getHeader();
            if (HandlerErroUtil.handlerHeader(context, header, isShowToast)) {
                DialogUtil.dismissDialog();
                callback.onErro(header);
            } else {
                if (header != null && "0001".equals(header.getRespCode())) {
                    return;
                }
//                if(!TextUtils.isEmpty(header.getIdentityId())) {
//                    AppUtility.getInstance().setSessionId(header.getIdentityId());
//                }
                AppBody body = null;
                body = (AppBody) data.getBody();
                if (body != null) {
//					LogUtil.e("Object: " + body);
                }
                callback.oncallback(body);
            }
        } else {
            callback.onErro(null);
        }
    }

    public static abstract class CallBack<T extends AppBody> {

        public abstract void oncallback(T t);

        public abstract void onErro(AppHeader header);

        protected boolean getNetWorkError() {
            return false;
        }
    }
}
