package com.ailk.tool;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;

import com.pongo.commonlibray.R;
import com.syd.oden.circleprogressdialog.core.CircleProgressDialog;

/**
 * Project : XHS
 * Created by 王可 on 2016/10/14.
 */

public class ProgressUtil {
    public static CircleProgressDialog circleProgressDialog = null;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Object mSynObj = new Object();

    public static void defaultShow(final Context context) {
        show(context, "加载中...");
    }

    public static void show(final Context context, final String msg) {
        new Thread(new Runnable() {
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (mSynObj) {
                            if (circleProgressDialog != null) {

                                circleProgressDialog.setText(msg);
                                circleProgressDialog.setTextColor(ContextCompat.getColor(context, R.color.white));
                                circleProgressDialog.dismiss();
                            } else {
                                circleProgressDialog = buildProgress(context);
                                circleProgressDialog.setText(msg);
                                circleProgressDialog.setTextColor(ContextCompat.getColor(context, R.color.white));
                            }
                            try {
                                circleProgressDialog.showDialog();
                            }catch (Exception e){

                                if (null != circleProgressDialog){
                                    circleProgressDialog.dismiss();
                                }
                            }
                        }
                    }
                });
            }
        }).start();
    }

    public static void dismiss() {
        if (null == circleProgressDialog) {
            return;
        }
        if (circleProgressDialog.isShowing()) {
            circleProgressDialog.dismiss();
        }
    }

    private static CircleProgressDialog buildProgress(Context context) {
        return new CircleProgressDialog(context);
    }


}
