package com.ailk.im.net;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.AppDatapackage;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.im.tool.IMConstant;
import com.ailk.util.PrefUtility;
import com.apkfuns.logutils.LogUtils;

import exception.XhsException;
import wangke.netlib.interfaces.NetInterface;

/**
 * Project : pmph_android
 * Created by 王可 on 2017/2/23.
 */

public class NetInterfaceImpl implements NetInterface<AppDatapackage, AppBody, AppHeader> {
    /**
     * @return 服务器url
     */
    @NonNull
    @Override
    public String getUrl() {
        return IMConstant.URL_HEADER;
    }

    /**
     * 反序列化异常抛出后的处理 如界面弹窗提示等
     *
     * @param t Throwable IOException when Deserialize error.
     */
    @Override
    public void onJsonConvertError(Throwable t) {
        LogUtils.e("json转换失败:" + t.getMessage());
    }

    /**
     * 创建json报文
     *
     * @param bodyObject
     * @param header
     * @param code
     *
     * @return
     */
    @Override
    public AppDatapackage createPackage(AppBody bodyObject, AppHeader header, String code) {
        AppDatapackage appDatapackage = new AppDatapackage();
        appDatapackage.setBody(bodyObject);
        appDatapackage.setHeader(header);
        LogUtils.d("create data package, bizcode = " + code);
        return appDatapackage;
    }

    /**
     * 创建json报头
     *
     * @param requestCode
     *
     * @return
     */
    @Override
    public AppHeader createHeader(String requestCode) {
        AppHeader appHeader = new AppHeader();
        appHeader.setBizCode(requestCode);
        return appHeader;
    }

    /**
     * @param header 根据报文报头的返回状态码进行处理
     *               "0000"为返回状态正常
     *
     * @return 如果失败则返回true 正常则返回false
     */
    @Override
    public boolean handleHeader(final AppHeader header, final Context context) throws XhsException {
        if (null == header) {
            return true;
        }
        if ("0000".equals(header.getRespCode())) {
            return false;
        } else if ("0001".equals(header.getRespCode())) {
            //登录超时
            return true;
        } else if ("0005".equals(header.getRespCode())) {
            //未登录
            return true;
        }
        else if ("0101".equals(header.getRespCode())) {
            throw new XhsException(new Throwable(header.getRespCode() + ":" + header.getRespMsg()),XhsException.ERROR_ACCOUNT);
        }
        else if ("0102".equals(header.getRespCode())) {
            throw new XhsException(new Throwable(header.getRespCode() + ":" + header.getRespMsg()),XhsException.ERROR_PASSWORD);
        }
        else {
            throw new XhsException(new Throwable(header.getRespCode() + ":" + header.getRespMsg()),XhsException.ERROR_RESPONSE);
//            onShowHeaderMessage(header, context);
//            return true;
        }

    }



    @Override
    public AppHeader obtainHeader(AppDatapackage appDatapackage) {
        return null == appDatapackage ? null : (AppHeader) appDatapackage.getHeader();
    }

    @Override
    public AppBody obtainBody(AppDatapackage appDatapackage) {
        return null == appDatapackage ? null : (AppBody) appDatapackage.getBody();
    }

    @Override
    public String getToken() {

        return PrefUtility.get("token","");
    }


}