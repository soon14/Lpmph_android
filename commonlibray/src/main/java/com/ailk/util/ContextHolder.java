package com.ailk.util;

import android.content.Context;
import android.text.TextUtils;

/**
 * Project : pmph_android
 * Created by 王可 on 2017/2/24.
 */

public class ContextHolder {
    static Context mContext;
    static String token;
    public static void initial(Context context) {
        mContext = context;
    }
    public static Context getContext() {
        return mContext;
    }
}