package com.ailk.pmph;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.ailk.butterfly.app.model.BaseConstant;
import com.ailk.pmph.utils.AppUtility;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.pmph.utils.TDevice;
import com.ailk.util.ContextHolder;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;
import com.pgyersdk.crash.PgyCrashManager;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;



public class AppContext extends Application {

    public static final String CATCHE_DIR_PATH = "/emall/cache/aquery";
    public static String FilePath = "";
    public static Bitmap bmPreset;
    public static String homeUrl = "";
    public static boolean isLogin = false;
    public static Bitmap avator;

    @Override
    public void onCreate() {
        CrashReport.initCrashReport(getApplicationContext(), "dd92ed869f", BaseConstant.isDebuggable);
        AQUtility.setContext(this);
        ContextHolder.initial(this);
        configDebug();

        configCacheDir();

        configLimits();
        bmPreset = BitmapFactory.decodeResource(getResources(), R.drawable.default_img);
        avator = BitmapFactory.decodeResource(getResources(), R.drawable.avtar);
        if (TDevice.hasSDcard()) {
            FilePath = Environment.getExternalStorageDirectory().getPath();
        } else {
            FilePath = getCacheDir().getPath();
        }
        AppUtility.getInstance();
        if (PrefUtility.contains("token")){
            isLogin = true;
        }else {
            isLogin = false;
        }
        PgyCrashManager.register(this);
        super.onCreate();
//        FIR.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static String getFilePath() {
        return FilePath;
    }

    private void configDebug() {
        if (BuildConfig.DEBUG) {
            AQUtility.setDebug(true);
        }
    }

    private void configCacheDir() {
        if (TDevice.hasSDcard()) {
            File ext = Environment.getExternalStorageDirectory();
            File cacheDir = new File(ext, CATCHE_DIR_PATH);
            AQUtility.setCacheDir(cacheDir);
        } else {
            AQUtility.setCacheDir(null);
        }
    }

    private void configLimits() {
        AjaxCallback.setNetworkLimit(8);
        BitmapAjaxCallback.setIconCacheLimit(200);
        BitmapAjaxCallback.setCacheLimit(200);
        BitmapAjaxCallback.setPixelLimit(400 * 400);
        BitmapAjaxCallback.setMaxPixelLimit(2000000);
    }

    /**
     * 判断是否是debug模式，如果是，返回true，如果不是，返回false
     * @return
     */
    public static boolean isDebugAble(){
        try {
            ApplicationInfo info = getContext().getApplicationInfo();
            return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        }catch (Exception e){
            LogUtil.e(e);
        }
        return false;
    }

    @Override
    public void onLowMemory() {
        BitmapAjaxCallback.clearCache();
        super.onLowMemory();
    }

    public static Context getContext() {
        return AQUtility.getContext();
    }

}
