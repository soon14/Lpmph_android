package com.ailk.pmph.utils;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ailk.pmph.R;

public class ToastUtil {

	private static Handler mHandler = new Handler(Looper.getMainLooper());
	private static Toast mToast = null;

	private static Object mSynObj = new Object();

	public static void show(Context context,String msg) {
		showToast(context, msg, Toast.LENGTH_SHORT, Gravity.BOTTOM);
	}

	public static void show(Context context,String msg,boolean isShowToast) {
		if(isShowToast)
			showToast(context, msg, Toast.LENGTH_SHORT, Gravity.BOTTOM);
	}
	public static void show(Context context,int msg) {
		showToast(context, msg, Toast.LENGTH_SHORT, Gravity.BOTTOM);
	}

	public static void showCenter(Context context,String msg) {
		showToast(context, msg, Toast.LENGTH_SHORT, Gravity.CENTER);
	}

	public static void showCenter(Context context,int msg) {
		showToast(context, msg, Toast.LENGTH_SHORT, Gravity.CENTER);
	}

	public static void dismiss() {
		if (mToast != null) {
			if (Integer.valueOf(android.os.Build.VERSION.SDK) < 14)
				mToast.cancel();
		}
	}

	private static void showToast(final Context context,final int msg, final int len,
			final int gravity) {
		new Thread(new Runnable() {
			public void run() {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (mSynObj) {
							if (mToast != null) {
								if (Integer
										.valueOf(android.os.Build.VERSION.SDK) < 12)
									mToast.cancel();
								mToast.setText(msg);
								mToast.setDuration(len);
							} else {
								mToast = Toast.makeText(
										context, msg, len);
							}
							mToast.setGravity(gravity, 0, 100);
							mToast.show();
						}
					}
				});
			}
		}).start();
	}

	private static void showToast(final Context context,final String msg, final int len,
			final int gravity) {
		new Thread(new Runnable() {
			public void run() {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (mSynObj) {
							if (mToast != null) {
								if (Integer
										.valueOf(android.os.Build.VERSION.SDK) < 12)
									mToast.cancel();
								mToast.setText(msg);
								mToast.setDuration(len);
							} else {
								mToast = Toast.makeText(
										context, msg, len);
							}
							mToast.setGravity(gravity, 0, 100);
							mToast.show();
						}
					}
				});
			}
		}).start();
	}

	public static void showIconToast(Context context, String message) {
		if (message != null && !message.equalsIgnoreCase("")) {
			View view = LayoutInflater.from(context).inflate(R.layout.toast_add_shop_cart, null);
			((TextView) view.findViewById(R.id.tv_toast_msg)).setText(message);
			Toast toast = new Toast(context);
			toast.setView(view);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

}
