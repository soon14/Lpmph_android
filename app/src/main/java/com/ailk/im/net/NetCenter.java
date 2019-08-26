package com.ailk.im.net;

import android.content.Context;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.AppDatapackage;
import com.ailk.butterfly.app.model.AppHeader;

import wangke.netlib.RequestService;

/**
 * Project : pmph_android
 * Created by 王可 on 2017/2/23.
 */

public class NetCenter {
    public static RequestService<AppBody, AppDatapackage, AppHeader> build(Context context) {
        return new RequestService<AppBody, AppDatapackage, AppHeader>(new NetInterfaceImpl(), AppDatapackage.class, context);
    }

}