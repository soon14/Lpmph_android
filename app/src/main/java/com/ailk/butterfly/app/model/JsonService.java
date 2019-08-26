package com.ailk.butterfly.app.model;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ai.ecp.app.req.Cms001Req;
import com.ai.ecp.app.req.Cms004Req;
import com.ai.ecp.app.req.Cms005Req;
import com.ai.ecp.app.req.Cms007Req;
import com.ai.ecp.app.req.Cms008Req;
import com.ai.ecp.app.req.Cms009Req;
import com.ai.ecp.app.req.Cms010Req;
import com.ai.ecp.app.req.Coup001Req;
import com.ai.ecp.app.req.Coup004Req;
import com.ai.ecp.app.req.Gds001Req;
import com.ai.ecp.app.req.Gds002Req;
import com.ai.ecp.app.req.Gds003Req;
import com.ai.ecp.app.req.Gds004Req;
import com.ai.ecp.app.req.Gds005Req;
import com.ai.ecp.app.req.Gds006Req;
import com.ai.ecp.app.req.Gds007Req;
import com.ai.ecp.app.req.Gds008Req;
import com.ai.ecp.app.req.Gds009Req;
import com.ai.ecp.app.req.Gds010Req;
import com.ai.ecp.app.req.Gds011Req;
import com.ai.ecp.app.req.Gds012Req;
import com.ai.ecp.app.req.Gds013Req;
import com.ai.ecp.app.req.Gds014Req;
import com.ai.ecp.app.req.Gds015Req;
import com.ai.ecp.app.req.Gds016Req;
import com.ai.ecp.app.req.Gds017Req;
import com.ai.ecp.app.req.Gds018Req;
import com.ai.ecp.app.req.Gds021Req;
import com.ai.ecp.app.req.Gds022Req;
import com.ai.ecp.app.req.Gds023Req;
import com.ai.ecp.app.req.Gds024Req;
import com.ai.ecp.app.req.Gds025Req;
import com.ai.ecp.app.req.Gds026Req;
import com.ai.ecp.app.req.Gds027Req;
import com.ai.ecp.app.req.Gds028Req;
import com.ai.ecp.app.req.IM001Req;
import com.ai.ecp.app.req.IM003Req;
import com.ai.ecp.app.req.Ord001Req;
import com.ai.ecp.app.req.Ord002Req;
import com.ai.ecp.app.req.Ord003Req;
import com.ai.ecp.app.req.Ord004Req;
import com.ai.ecp.app.req.Ord005Req;
import com.ai.ecp.app.req.Ord006Req;
import com.ai.ecp.app.req.Ord007Req;
import com.ai.ecp.app.req.Ord008Req;
import com.ai.ecp.app.req.Ord009Req;
import com.ai.ecp.app.req.Ord010Req;
import com.ai.ecp.app.req.Ord011Req;
import com.ai.ecp.app.req.Ord012Req;
import com.ai.ecp.app.req.Ord013Req;
import com.ai.ecp.app.req.Ord014Req;
import com.ai.ecp.app.req.Ord015Req;
import com.ai.ecp.app.req.Ord016Req;
import com.ai.ecp.app.req.Ord017Req;
import com.ai.ecp.app.req.Ord018Req;
import com.ai.ecp.app.req.Ord019Req;
import com.ai.ecp.app.req.Ord021Req;
import com.ai.ecp.app.req.Ord022Req;
import com.ai.ecp.app.req.Pay001Req;
import com.ai.ecp.app.req.Pay002Req;
import com.ai.ecp.app.req.Pay003Req;
import com.ai.ecp.app.req.Pay004Req;
import com.ai.ecp.app.req.Pmphstaff001Req;
import com.ai.ecp.app.req.Pmphstaff002Req;
import com.ai.ecp.app.req.Pmphstaff003Req;
import com.ai.ecp.app.req.Pmphstaff004Req;
import com.ai.ecp.app.req.Pmphstaff005Req;
import com.ai.ecp.app.req.Pmphstaff006Req;
import com.ai.ecp.app.req.Pmphstaff007Req;
import com.ai.ecp.app.req.Prom004Req;
import com.ai.ecp.app.req.Prom005Req;
import com.ai.ecp.app.req.Prom006Req;
import com.ai.ecp.app.req.Prom007Req;
import com.ai.ecp.app.req.Staff001Req;
import com.ai.ecp.app.req.Staff002Req;
import com.ai.ecp.app.req.Staff004Req;
import com.ai.ecp.app.req.Staff005Req;
import com.ai.ecp.app.req.Staff006Req;
import com.ai.ecp.app.req.Staff102Req;
import com.ai.ecp.app.req.Staff103Req;
import com.ai.ecp.app.req.Staff104Req;
import com.ai.ecp.app.req.Staff105Req;
import com.ai.ecp.app.req.Staff106Req;
import com.ai.ecp.app.req.Staff107Req;
import com.ai.ecp.app.req.Staff109Req;
import com.ai.ecp.app.req.Staff111Req;
import com.ai.ecp.app.req.Staff112Req;
import com.ai.ecp.app.req.Staff113Req;
import com.ai.ecp.app.req.Staff114Req;
import com.ai.ecp.app.req.Staff115Req;
import com.ai.ecp.app.req.Staff116Req;
import com.ai.ecp.app.req.Staff117Req;
import com.ai.ecp.app.req.Staff118Req;
import com.ai.ecp.app.req.Staff119Req;
import com.ai.ecp.app.req.Staff120Req;
import com.ai.ecp.app.req.Staff121Req;
import com.ai.ecp.app.req.Staff122Req;
import com.ai.ecp.app.resp.*;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.AppUtility;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.EncrypytUtil;
import com.ailk.pmph.utils.HandlerErroUtil;
import com.ailk.pmph.utils.JsonConvert;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.StringUtil;
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
public class JsonService {
    private static final String BASIC_NAME = "msg";
    private static final String UTF_8 = "UTF-8";
    private static final String ADID = "adid";
    private static final String LINK_TYPE = "linktype";
    private AQuery mAQuery;
    private JsonConvert mConverter = new JsonConvert();
    private AjaxCallback<String> cb;

    public JsonService(Context context) {
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

    /**
     * @param request
     * @param bizCode
     * @param adidValue     adid value
     * @param linkTypeValue linktype value 如果adidvalue为空,则请求时不添加linkTypeValue;linkTypeValue为空时,只传入adidValue
     *
     * @return
     *
     * @throws UnsupportedEncodingException
     */
    private Map<String, Object> createHttpEntiry(AppBody request, String bizCode, @NonNull String adidValue, @Nullable String linkTypeValue)
            throws UnsupportedEncodingException {
        String jsonStr = mConverter.writeObjectToJsonString(createDataPackage(
                request, bizCode));
        LogUtil.e("request : " + jsonStr);
        jsonStr = EncrypytUtil.enrypytJson(jsonStr);
//		LogUtil.e("en request: "+jsonStr);

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair(BASIC_NAME, jsonStr));
        if (StringUtil.isNotEmpty(adidValue)) {
            pairs.add(new BasicNameValuePair(ADID, adidValue));
            if (StringUtil.isNotEmpty(linkTypeValue)) {
                pairs.add(new BasicNameValuePair(LINK_TYPE, linkTypeValue));
            }
        }

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

    //    cms
    public void requestCms001(Context context, Cms001Req request,
                              boolean isShowProgress, CallBack<Cms001Resp> callback) {
        request(request, "cms001", callback, Cms001Resp.class, context,
                isShowProgress, true);
    }

    public void requestCms004(Context context, Cms004Req request,
                              boolean isShowProgress, CallBack<Cms004Resp> callback) {
        request(request, "cms004", callback, Cms004Resp.class, context,
                isShowProgress, true);
    }

    public void requestCms005(Context context, Cms005Req request,
                              boolean isShowProgress, CallBack<Cms005Resp> callback) {
        request(request, "cms005", callback, Cms005Resp.class, context,
                isShowProgress, true);
    }

    public void requestCms007(Context context, Cms007Req request,
                              boolean isShowProgress, CallBack<Cms007Resp> callback) {
        request(request, "cms007", callback, Cms007Resp.class, context,
                isShowProgress, true);
    }

    public void requestCms008(Context context, Cms008Req request,
                              boolean isShowProgress, CallBack<Cms008Resp> callback) {
        request(request, "cms008", callback, Cms008Resp.class, context,
                isShowProgress, true);
    }

    public void requestCms009(Context context, Cms009Req request, boolean isShowProgress, CallBack<Cms009Resp> callBack) {
        request(request, "cms009", callBack, Cms009Resp.class, context,
                isShowProgress, true);
    }

    public void requestCms010(Context context, Cms010Req request, boolean isShowProgress, CallBack<Cms010Resp> callBack) {
        request(request, "cms010", callBack, Cms010Resp.class, context,
                isShowProgress, true);
    }

    //  staff
    public void requestStaff001(Context context, Staff001Req request, boolean isShowProgress, CallBack<Staff001Resp> callback) {
        request(request, "staff001", callback, Staff001Resp.class, context, isShowProgress, true);
    }

    public void requestStaff002(Context context, Staff002Req request, boolean isShowProgress, CallBack<Staff002Resp> callback) {
        request(request, "staff002", callback, Staff002Resp.class, context, isShowProgress, true);
    }

    public void requestStaff003(Context context,
                                boolean isShowProgress, CallBack<Staff003Resp> callback) {
        request(null, "staff003", callback, Staff003Resp.class, context,
                isShowProgress, true);
    }

    public void requestStaff004(Context context, Staff004Req request, boolean isShowProgress, CallBack<Staff004Resp> callback) {
        request(request, "staff004", callback, Staff004Resp.class, context, isShowProgress, true);
    }

    public void requestStaff005(Context context, Staff005Req request, boolean isShowProgress, CallBack<Staff005Resp> callback) {
        request(request, "staff005", callback, Staff005Resp.class, context, isShowProgress, true);
    }

    public void requestStaff006(Context context, Staff006Req request, boolean isShowProgress, CallBack<Staff006Resp> callback) {
        request(request, "staff006", callback, Staff006Resp.class, context, isShowProgress, true);
    }

    public void requestStaff110(Context context,
                                boolean isShowProgress, CallBack<Staff110Resp> callback) {
        request(null, "staff110", callback, Staff110Resp.class, context,
                isShowProgress, true);
    }

    public void requestStaff102(Context context, Staff102Req request, boolean isShowProgress, CallBack<Staff102Resp> callback) {
        request(request, "staff102", callback, Staff102Resp.class, context, isShowProgress, true);
    }

    public void requestStaff103(Context context, Staff103Req request, boolean isShowProgress, CallBack<Staff103Resp> callback) {
        request(request, "staff103", callback, Staff103Resp.class, context, isShowProgress, true);
    }

    public void requestStaff104(Context context, Staff104Req request, boolean isShowProgress, CallBack<Staff104Resp> callback) {
        request(request, "staff104", callback, Staff104Resp.class, context, isShowProgress, true);
    }

    public void requestStaff105(Context context, Staff105Req request, boolean isShowProgress, CallBack<Staff105Resp> callback) {
        request(request, "staff105", callback, Staff105Resp.class, context, isShowProgress, true);
    }

    public void requestStaff106(Context context, Staff106Req request, boolean isShowProgress, CallBack<Staff106Resp> callback) {
        request(request, "staff106", callback, Staff106Resp.class, context, isShowProgress, true);
    }

    public void requestStaff107(Context context, Staff107Req request, boolean isShowProgress, CallBack<Staff107Resp> callback) {
        request(request, "staff107", callback, Staff107Resp.class, context, isShowProgress, true);
    }

    public void requestStaff109(Context context, Staff109Req request, boolean isShowProgress, CallBack<Staff109Resp> callback) {
        request(request, "staff109", callback, Staff109Resp.class, context, isShowProgress, true);
    }

    public void requestStaff111(Context context, Staff111Req request, boolean isShowProgress, CallBack<Staff111Resp> callback) {
        request(request, "staff111", callback, Staff111Resp.class, context, isShowProgress, true);
    }

    public void requestStaff112(Context context, Staff112Req request, boolean isShowProgress, CallBack<Staff112Resp> callback) {
        request(request, "staff112", callback, Staff112Resp.class, context, isShowProgress, true);
    }

    public void requestStaff121(Context context, Staff121Req request, boolean isShowProgress, CallBack<Staff121Resp> callBack) {
        request(request, "staff121", callBack, Staff121Resp.class, context, isShowProgress, true);
    }

    public void requestStaff122(Context context, Staff122Req request, boolean isShowProgress, CallBack<Staff122Resp> callBack) {
        request(request, "staff122", callBack, Staff122Resp.class, context, isShowProgress, true);
    }

    public void requestPmphStaff001(Context context, Pmphstaff001Req request, boolean isShowProgress, CallBack<Pmphstaff001Resp> callBack) {
        request(request, "pmphstaff001", callBack, Pmphstaff001Resp.class, context, isShowProgress, true);
    }

    public void requestPmphStaff002(Context context, Pmphstaff002Req request, boolean isShowProgress, CallBack<Pmphstaff002Resp> callBack) {
        request(request, "pmphstaff002", callBack, Pmphstaff002Resp.class, context, isShowProgress, true);
    }

    public void requestPmphStaff003(Context context, Pmphstaff003Req request, boolean isShowProgress, CallBack<Pmphstaff003Resp> callBack) {
        request(request, "pmphstaff003", callBack, Pmphstaff003Resp.class, context, isShowProgress, true);
    }

    public void requestPmphStaff004(Context context, Pmphstaff004Req request, boolean isShowProgress, CallBack<Pmphstaff004Resp> callBack) {
        request(request, "pmphstaff004", callBack, Pmphstaff004Resp.class, context, isShowProgress, true);
    }

    public void requestPmphStaff005(Context context, Pmphstaff005Req request, boolean isShowProgress, CallBack<Pmphstaff005Resp> callBack) {
        request(request, "pmphstaff005", callBack, Pmphstaff005Resp.class, context, isShowProgress, true);
    }

    public void requestPmphStaff006(Context context, Pmphstaff006Req request, boolean isShowProgress, CallBack<Pmphstaff006Resp> callBack) {
        request(request, "pmphstaff006", callBack, Pmphstaff006Resp.class, context, isShowProgress, true);
    }

    public void requestPmphStaff007(Context context, Pmphstaff007Req request, boolean isShowProgress, CallBack<Pmphstaff007Resp> callBack) {
        request(request, "pmphstaff007", callBack, Pmphstaff007Resp.class, context, isShowProgress, true);
    }

    // coup

    public void requestCoup001(Context context, Coup001Req request, boolean isShowProgress, CallBack<Coup001Resp> callback) {
        request(request, "coup001", callback, Coup001Resp.class, context, isShowProgress, true);
    }

    public void requestCoup002(Context context, Coup001Req request, boolean isShowProgress, CallBack<Coup003Resp> callback) {
        request(request, "coup002", callback, Coup003Resp.class, context, isShowProgress, true);
    }

    public void requestCoup004(Context context, Coup004Req request, boolean isShowProgress, CallBack<Coup004Resp> callBack) {
        request(request, "coup004", callBack, Coup004Resp.class, context, isShowProgress, true);
    }

    //  goods
    public void requestGds001(Context context, Gds001Req request, boolean isShowProgress, CallBack<Gds001Resp> callback) {
        request(request, "gds001", callback, Gds001Resp.class, context, isShowProgress, true);
    }

    public void requestGds002(Context context, Gds002Req request, boolean isShowProgress, CallBack<Gds002Resp> callback) {
        request(request, "gds002", callback, Gds002Resp.class, context, isShowProgress, true);
    }

    public void requestGds003(Context context, Gds003Req request, boolean isShowProgress, CallBack<Gds003Resp> callback) {
        request(request, "gds003", callback, Gds003Resp.class, context, isShowProgress, true);
    }

    public void requestGds004(Context context, Gds004Req request, boolean isShowProgress, CallBack<Gds004Resp> callback) {
        request(request, "gds004", callback, Gds004Resp.class, context, isShowProgress, true);
    }

    public void requestGds005(Context context, Gds005Req request, boolean isShowProgress, CallBack<Gds005Resp> callback) {
        request(request, "gds005", callback, Gds005Resp.class, context, isShowProgress, true);
    }

    public void requestGds006(Context context, Gds006Req request, boolean isShowProgress, CallBack<Gds006Resp> callback) {
        request(request, "gds006", callback, Gds006Resp.class, context, isShowProgress, true);
    }

    public void requestGds007(Context context, Gds007Req request, boolean isShowProgress, CallBack<Gds007Resp> callback) {
        request(request, "gds007", callback, Gds007Resp.class, context, isShowProgress, true);
    }

    public void requestGds008(Context context, Gds008Req request, boolean isShowProgress, CallBack<Gds008Resp> callback) {
        request(request, "gds008", callback, Gds008Resp.class, context, isShowProgress, true);
    }

    public void requestGds009(Context context, Gds009Req request, boolean isShowProgress, CallBack<Gds009Resp> callback) {
        request(request, "gds009", callback, Gds009Resp.class, context, isShowProgress, true);
    }

    public void requestGds010(Context context, Gds010Req request, boolean isShowProgress, CallBack<Gds010Resp> callback) {
        request(request, "gds010", callback, Gds010Resp.class, context, isShowProgress, true);
    }

    public void requestGds011(Context context, Gds011Req request, boolean isShowProgress, CallBack<Gds011Resp> callback) {
        request(request, "gds011", callback, Gds008Resp.class, context, isShowProgress, true);
    }

    public void requestGds012(Context context, Gds012Req request, boolean isShowProgress, CallBack<Gds012Resp> callback) {
        request(request, "gds012", callback, Gds012Resp.class, context, isShowProgress, true);
    }

    public void requestGds013(Context context, Gds013Req request, boolean isShowProgress, CallBack<Gds013Resp> callback) {
        request(request, "gds013", callback, Gds013Resp.class, context, isShowProgress, true);
    }

    public void requestGds014(Context context, Gds014Req request, boolean isShowProgress, CallBack<Gds014Resp> callback) {
        request(request, "gds014", callback, Gds014Resp.class, context, isShowProgress, true);
    }

    public void requestGds015(Context context, Gds015Req request, boolean isShowProgress, CallBack<Gds015Resp> callback) {
        request(request, "gds015", callback, Gds015Resp.class, context, isShowProgress, true);
    }

    public void requestGds016(Context context, Gds016Req request, boolean isShowProgress, CallBack<Gds016Resp> callback) {
        request(request, "gds016", callback, Gds016Resp.class, context, isShowProgress, true);
    }

    public void requestGds017(Context context, Gds017Req request, boolean isShowProgress, CallBack<Gds017Resp> callback) {
        request(request, "gds017", callback, Gds017Resp.class, context, isShowProgress, true);
    }

    public void requestGds018(Context context, Gds018Req request, boolean isShowProgress, CallBack<Gds018Resp> callback) {
        request(request, "gds018", callback, Gds018Resp.class, context, isShowProgress, true);
    }

    public void requestGds026(Context context, Gds026Req request, boolean isShowProgress, CallBack<Gds026Resp> callBack) {
        request(request, "gds026", callBack, Gds026Resp.class, context, isShowProgress, true);
    }

    public void requestGds027(Context context, Gds027Req request, boolean isShowProgress, CallBack<Gds027Resp> callBack) {
        request(request, "gds027", callBack, Gds027Resp.class, context, isShowProgress, true);
    }

    public void requestGds028(Context context, Gds028Req request, boolean isShowProgress, CallBack<Gds028Resp> callBack) {
        request(request, "gds028", callBack, Gds028Resp.class, context, isShowProgress, true);
    }

    //=============================订单==============================//

    /**
     * 加入购物车
     * @param context
     * @param request
     * @param isShowProgress
     * @param callback
     */
    public void requestOrd001(Context context, Ord001Req request, boolean isShowProgress, CallBack<Ord001Resp> callback) {
        request(request, "ord001", callback, Ord001Resp.class, context, isShowProgress, true);
    }

    /**
     * 购物车列表查询
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callback
     */
    public void requestOrd002(Context context, Ord002Req request, boolean isShowProgress, CallBack<Ord002Resp> callback) {
        request(request, "ord002", callback, Ord002Resp.class, context, isShowProgress, true);
    }

    /**
     * 删除购物车产品信息（无勾选）
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd003(Context context, Ord003Req request, boolean isShowProgress, CallBack<Ord003Resp> callBack) {
        request(request, "ord003", callBack, Ord003Resp.class, context, isShowProgress, true);
    }

    /**
     * 修改店铺优惠
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd004(Context context, Ord004Req request, boolean isShowProgress, CallBack<Ord004Resp> callBack) {
        request(request, "ord004", callBack, Ord004Resp.class, context, isShowProgress, true);
    }

    /**
     * 修改明细优惠
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd005(Context context, Ord005Req request, boolean isShowProgress, CallBack<Ord005Resp> callBack) {
        request(request, "ord005", callBack, Ord005Resp.class, context, isShowProgress, true);
    }

    /**
     * 删除购物车产品信息（有勾选）
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd006(Context context, Ord006Req request, boolean isShowProgress, CallBack<Ord006Resp> callBack) {
        request(request, "ord006", callBack, Ord006Resp.class, context, isShowProgress, true);
    }

    /**
     * 批量删除购物车明细
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd007(Context context, Ord007Req request, boolean isShowProgress, CallBack<Ord007Resp> callBack) {
        request(request, "ord007", callBack, Ord007Resp.class, context, isShowProgress, true);
    }

    /**
     * 购物车去结算
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd008(Context context, Ord008Req request, boolean isShowProgress, CallBack<Ord008Resp> callBack) {
        request(request, "ord008", callBack, Ord008Resp.class, context, isShowProgress, true);
    }

    /**
     * 提交订单
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd009(Context context, Ord009Req request, boolean isShowProgress, CallBack<Ord009Resp> callBack) {
        request(request, "ord009", callBack, Ord009Resp.class, context, isShowProgress, true);
    }

    /**
     * 取消订单
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd010(Context context, Ord010Req request, boolean isShowProgress, CallBack<Ord010Resp> callBack) {
        request(request, "ord010", callBack, Ord010Resp.class, context, isShowProgress, true);
    }

    /**
     * 订单详情
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd011(Context context, Ord011Req request, boolean isShowProgress, CallBack<Ord011Resp> callBack) {
        request(request, "ord011", callBack, Ord011Resp.class, context, isShowProgress, true);
    }

    /**
     * 我的订单
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd012(Context context, Ord012Req request, boolean isShowProgress, CallBack<Ord012Resp> callBack) {
        request(request, "ord012", callBack, Ord012Resp.class, context, isShowProgress, true);
    }

    /**
     * 增减购物车商品数量
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd013(Context context, Ord013Req request, boolean isShowProgress, CallBack<Ord013Resp> callBack) {
        request(request, "ord013", callBack, Ord013Resp.class, context, isShowProgress, true);
    }

    /**
     * 勾选、不选择商品（有勾选）
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd014(Context context, Ord014Req request, boolean isShowProgress, CallBack<Ord014Resp> callBack) {
        request(request, "ord014", callBack, Ord014Resp.class, context, isShowProgress, true);
    }

    /**
     * 更新组合商品数量
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd015(Context context, Ord015Req request, boolean isShowProgress, CallBack<Ord015Resp> callBack) {
        request(request, "ord015", callBack, Ord015Resp.class, context, isShowProgress, true);
    }

    /**
     * 删除组合商品
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd016(Context context, Ord016Req request, boolean isShowProgress, CallBack<Ord016Resp> callBack) {
        request(request, "ord016", callBack, Ord016Resp.class, context, isShowProgress, true);
    }

    /**
     * 确认收货
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd017(Context context, Ord017Req request, boolean isShowProgress, CallBack<Ord017Resp> callBack) {
        request(request, "ord017", callBack, Ord017Resp.class, context, isShowProgress, true);
    }

    /**
     * 获取购物商品数量
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd018(Context context, Ord018Req request, boolean isShowProgress, CallBack<Ord018Resp> callBack) {
        request(request, "ord018", callBack, Ord018Resp.class, context, isShowProgress, true);
    }

    /**
     * 检验优惠码
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd019(Context context, Ord019Req request, boolean isShowProgress, CallBack<Ord019Resp> callBack) {
        request(request, "ord019", callBack, Ord019Resp.class, context, isShowProgress, true);
    }

    /**
     * 查看物流
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd021(Context context, Ord021Req request, boolean isShowProgress, CallBack<Ord021Resp> callBack) {
        request(request, "ord021", callBack, Ord021Resp.class, context, isShowProgress, true);
    }

    /**
     * 合并支付开关
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestOrd022(Context context, Ord022Req request, boolean isShowProgress, CallBack<Ord022Resp> callBack) {
        request(request, "ord022", callBack, Ord022Resp.class, context, isShowProgress, true);
    }

    //===================================支付====================================//

    /**
     * 单店铺支付
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestPay001(Context context, Pay001Req request, boolean isShowProgress, CallBack<Pay001Resp> callBack) {
        request(request, "pay001", callBack, Pay001Resp.class, context, isShowProgress, true);
    }

    public void requestPay002(Context context, Pay002Req request, boolean isShowProgress, CallBack<Pay002Resp> callBack) {
        request(request, "pay002", callBack, Pay002Resp.class, context, isShowProgress, true);
    }

    /**
     * 多店铺支付
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestPay003(Context context, Pay003Req request, boolean isShowProgress, CallBack<Pay003Resp> callBack) {
        request(request, "pay003", callBack, Pay003Resp.class, context, isShowProgress, true);
    }

    /**
     * 支付验证
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestPay004(Context context, Pay004Req request, boolean isShowProgress, CallBack<Pay004Resp> callBack) {
        request(request, "pay004", callBack, Pay004Resp.class, context, isShowProgress, true);
    }

    /*-------------------------------------第三方店铺---------------------------------------*/

    /**
     * 店铺搜索
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestGds021(Context context, Gds021Req request, boolean isShowProgress, CallBack<Gds021Resp> callBack) {
        request(request, "gds021", callBack, Gds021Resp.class, context, isShowProgress, true);
    }

    /**
     * 店铺搜索建议
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestGds022(Context context, Gds022Req request, boolean isShowProgress, CallBack<Gds022Resp> callBack) {
        request(request, "gds022", callBack, Gds022Resp.class, context, isShowProgress, true);
    }

    /**
     * 店铺热销--及--店铺首页的重磅推荐
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestGds023(Context context, Gds023Req request, boolean isShowProgress, CallBack<Gds023Resp> callBack) {
        request(request, "gds023", callBack, Gds023Resp.class, context, isShowProgress, true);
    }

    /**
     * 上架新品和全部商品
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestGds024(Context context, Gds024Req request, boolean isShowProgress, CallBack<Gds024Resp> callBack) {
        request(request, "gds024", callBack, Gds024Resp.class, context, isShowProgress, true);
    }

    /**
     * 店铺分类
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestGds025(Context context, Gds025Req request, boolean isShowProgress, CallBack<Gds025Resp> callBack) {
        request(request, "gds025", callBack, Gds025Resp.class, context, isShowProgress, true);
    }

    /**
     * 获取店铺收藏列表
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestStaff113(Context context, Staff113Req request, boolean isShowProgress, CallBack<Staff113Resp> callBack) {
        request(request, "staff113", callBack, Staff113Resp.class, context, isShowProgress, true);
    }

    /**
     * 收藏店铺
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestStaff114(Context context, Staff114Req request, boolean isShowProgress, CallBack<Staff114Resp> callBack) {
        request(request, "staff114", callBack, Staff114Resp.class, context, isShowProgress, true);
    }

    /**
     * 取消店铺收藏
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestStaff115(Context context, Staff115Req request, boolean isShowProgress, CallBack<Staff115Resp> callBack) {
        request(request, "staff115", callBack, Staff115Resp.class, context, isShowProgress, true);
    }

    /**
     * 查询店铺的信息--（进入店铺首页时使用）
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestStaff116(Context context, Staff116Req request, boolean isShowProgress, CallBack<Staff116Resp> callBack) {
        request(request, "staff116", callBack, Staff116Resp.class, context, isShowProgress, true);
    }

    public void requestStaff117(Context context, Staff117Req request, boolean isShowProgress, CallBack<Staff117Resp> callBack) {
        request(request, "staff117", callBack, Staff117Resp.class, context, isShowProgress, true);
    }

    public void requestStaff118(Context context, Staff118Req request, boolean isShowProgress, CallBack<Staff118Resp> callBack) {
        request(request, "staff118", callBack, Staff118Resp.class, context, isShowProgress, true);
    }

    public void requestStaff119(Context context, Staff119Req request, boolean isShowProgress, CallBack<Staff119Resp> callBack) {
        request(request, "staff119", callBack, Staff119Resp.class, context, isShowProgress, true);
    }

    public void requestStaff120(Context context, Staff120Req request, boolean isShowProgress, CallBack<Staff120Resp> callBack) {
        request(request, "staff120", callBack, Staff120Resp.class, context, isShowProgress, true);
    }

    /**
     * 店铺促销商品---类型
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestProm004(Context context, Prom004Req request, boolean isShowProgress, CallBack<Prom004Resp> callBack) {
        request(request, "prom004", callBack, Prom004Resp.class, context, isShowProgress, true);
    }

    /**
     * 店铺促销商品列表
     *
     * @param context
     * @param request
     * @param isShowProgress
     * @param callBack
     */
    public void requestProm005(Context context, Prom005Req request, boolean isShowProgress, CallBack<Prom005Resp> callBack) {
        request(request, "prom005", callBack, Prom005Resp.class, context, isShowProgress, true);
    }

    public void requestProm006(Context context, Prom006Req request, boolean isShowProgess, CallBack<Prom006Resp> callBack) {
        request(request, "prom006", callBack, Prom006Resp.class, context, isShowProgess, true);
    }

    public void requestProm006Ag(Context context, Prom006Req request, boolean isShowProgess, int timeOut, CallBack<Prom006Resp> callBack) {
        request2(request, "prom006", callBack, Prom006Resp.class, context, isShowProgess, true, timeOut);
    }

    public void requestProm007(Context context, Prom007Req request, boolean isShowProgess, CallBack<Prom007Resp> callBack) {
        request(request, "prom007", callBack, Prom007Resp.class, context, isShowProgess, true);
    }

    public void requestIM001(Context context, IM001Req request, boolean isShowProgess, CallBack<IM001Resp> callBack) {
        request(request, "im001", callBack, IM001Resp.class, context, isShowProgess, true);
    }

    public void requestIM003(Context context, IM003Req request, boolean isShowProgess, CallBack<IM003Resp> callBack) {
        request(request, "im003", callBack, IM003Resp.class, context, isShowProgess, true);
    }

    public void request(final AppBody request, final String bizCode,
                        final CallBack callback, final Class class1, final Context context,
                        final boolean isShowProgress, final boolean isShowToast) {
        try {
//            temp start
//            temp end
            //当界面中有从url传的参数，但是页面没有转存到params时，自动加入
           if (context instanceof BaseActivity && request != null) {
            if (((BaseActivity) context).getParamsMap() != null) {
                request.setExpand(((BaseActivity) context).getParamsMap());
            }
        }

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
                                + RequestURL.getURL());

                        if (callback.getNetWorkError()) {
                            return;
                        }
                        if (submitIndex > 0) {
                            LogUtil.e("submitIndex = " + submitIndex + "  retrcy");
                            try {
                                if (isShowProgress) {
                                    DialogUtil.showCustomerProgressDialog(context);
                                }
                                mAQuery.ajax(RequestURL.getURL(), createHttpEntiry(request, bizCode), String.class,
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
                                            mAQuery.ajax(RequestURL.getURL(), createHttpEntiry(request, bizCode), String.class,
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

            LogUtil.e("request Url = " + RequestURL.getURL() + " sessionId=" + AppUtility.getInstance().getSessionId());
            cb.header("ECP-COOKIE", AppUtility.getInstance().getSessionId());
            mAQuery.ajax(RequestURL.getURL(), createHttpEntiry(request, bizCode), String.class,
                    cb);
        } catch (UnsupportedEncodingException e) {
            if (isShowProgress) {
                DialogUtil.dismissDialog();
            }
            LogUtil.e(e);
        }
    }

    public void request2(final AppBody request, final String bizCode,
                        final CallBack callback, final Class class1, final Context context,
                        final boolean isShowProgress, final boolean isShowToast, final int timeOut) {
        try {
//            temp start
//            temp end
            //当界面中有从url传的参数，但是页面没有转存到params时，自动加入
            if (context instanceof BaseActivity && request != null) {
                if (((BaseActivity) context).getParamsMap() != null) {
                    request.setExpand(((BaseActivity) context).getParamsMap());
                }
            }

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
                                + RequestURL.getURL());

                        if (callback.getNetWorkError()) {
                            return;
                        }
                        if (submitIndex > 0) {
                            LogUtil.e("submitIndex = " + submitIndex + "  retrcy");
                            try {
                                if (isShowProgress) {
                                    DialogUtil.showCustomerProgressDialog(context);
                                }
                                mAQuery.ajax(RequestURL.getURL(), createHttpEntiry(request, bizCode), String.class,
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
                                            mAQuery.ajax(RequestURL.getURL(), createHttpEntiry(request, bizCode), String.class,
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

            LogUtil.e("request Url = " + RequestURL.getURL() + " sessionId=" + AppUtility.getInstance().getSessionId());
            cb.header("ECP-COOKIE", AppUtility.getInstance().getSessionId());
            cb.setTimeout(timeOut);
            mAQuery.ajax(RequestURL.getURL(), createHttpEntiry(request, bizCode), String.class,
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
