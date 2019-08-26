/**
 * Project Name: Ailk_fs_droid
 * Package Name: com.ailk.cucinfo.sys
 * File Name: L.java
 * Create Time: 2013-4-25 上午11:22:26
 * Copyright: Copyright(c) 2012 ailk cuc app group.
 */
package com.ailk.pmph.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;


import com.ailk.pmph.AppContext;

import java.io.PrintWriter;
import java.io.StringWriter;


public class LogUtil {

	private static final String TAG = "AI";

	public static Boolean _debug = true;

	private static int _logLevel;
	private static String _tag;

	private static final int VERBOSE = Log.VERBOSE;
	private static final int DEBUG = Log.DEBUG;
	private static final int INFO = Log.INFO;
	private static final int WARN = Log.WARN;
	private static final int ERROR = Log.ERROR;
	private static final int ASSERT = Log.ASSERT;
	private static final int DISABLE = 1024;

	public static void v(String tag, String msg) {
		log(VERBOSE, tag, msg);
	}

	public static void v(Object obj) {
		log(VERBOSE, obj);
	}

	public static void d(Object obj) {
		log(DEBUG, obj);
	}

	public static void i(Object obj) {
		log(INFO, obj);
	}

	public static void w(Object obj) {
		log(WARN, obj);
	}

	public static void e(Object obj) {
		log(ERROR, obj);
	}

	public static void wtf(Object obj) {
		log(ASSERT, obj);
	}

	public static void wtf() {
		log(ASSERT, "WTF");
	}

	private static void log(int priority, Object obj) {
		log(priority, getTag(isDebug()), obj);
		
//		{"messagetype":"01","loadurl":"http://fir.im/ejos"}
//		{"messagetype":"01","loadurl":"http://fir.im/m99j"}
	}

	private static void log(int priority, String tag, Object obj) {
		boolean debug = isDebug();
		if (debug || (!debug && priority >= getLogLevel())) {
			String msg;
			if (obj instanceof Throwable) {
				Throwable t = (Throwable) obj;
				StringWriter sw = new StringWriter();
				t.printStackTrace(new PrintWriter(sw));
				msg = sw.toString();
			} else {
				msg = String.valueOf(obj);
				if (msg == null || msg.length()==0) {
					msg = "\"\"";
				}
			}
			Log.println(priority, tag, msg);
			//LogFile.log2File(DateUtil.getCurrentDateTime() + " " + priority + " " + getTag(debug) + " " + msg);
		}
	}

	private static boolean isDebug() {
		if (_debug == null) {
			Context ctx = AppContext.getContext();
			if (ctx != null) {
				ApplicationInfo appInfo = ctx.getApplicationInfo();
				_debug = (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
			}
		}
		boolean debug = (_debug == null) ? true : _debug;
		return debug;
	}

	private static int getLogLevel() {
		if (_logLevel == 0) {
			Context ctx = AppContext.getContext();
			if (ctx != null) {
				PackageManager pm = ctx.getPackageManager();
				String logLevelStr = null;
				try {
					Bundle metaData = pm.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA).metaData;
					logLevelStr = metaData.getString(ManifestMeta.LOG_LEVEL);
				} catch (Exception e) {
					Log.d(TAG, "", e);
				}
				if (ManifestMeta.DISABLE.equalsIgnoreCase(logLevelStr)) {
					_logLevel = DISABLE;
				} else if (ManifestMeta.VERBOSE.equalsIgnoreCase(logLevelStr)) {
					_logLevel = VERBOSE;
				} else if (ManifestMeta.DEBUG.equalsIgnoreCase(logLevelStr)) {
					_logLevel = DEBUG;
				} else if (ManifestMeta.INFO.equalsIgnoreCase(logLevelStr)) {
					_logLevel = INFO;
				} else if (ManifestMeta.WARN.equalsIgnoreCase(logLevelStr)) {
					_logLevel = WARN;
				} else if (ManifestMeta.ERROR.equalsIgnoreCase(logLevelStr)) {
					_logLevel = ERROR;
				} else if (ManifestMeta.ASSERT.equalsIgnoreCase(logLevelStr)) {
					_logLevel = ASSERT;
				} else {
					_logLevel = DISABLE;
					Log.i("--->>",
							"No <meta-data android:name=\"ailk_log_level\" android:value=\"...\"/> in AndroidManifest.xml. Logging disabled.");
				}
			}
		}
		return (_logLevel != 0) ? _logLevel : DISABLE;
	}

	private static String getTag(boolean debug) {
		if (debug) {
			StackTraceElement caller = Thread.currentThread().getStackTrace()[5];
			String c = caller.getClassName();
			String className = c.substring(c.lastIndexOf(".") + 1, c.length());
			StringBuilder sb = new StringBuilder(5);
			sb.append(className);
			sb.append(".");
			sb.append(caller.getMethodName());
			sb.append("():");
			sb.append(caller.getLineNumber());
			return sb.toString();
		} else {
			if (_tag == null) {
				Context ctx = AppContext.getContext();
				if (ctx != null) {
					_tag = ctx.getPackageName();
				}
			}
			return (_tag != null) ? _tag : "";
		}
	}

	protected LogUtil() {}

	public static void set_debug(Boolean _debug) {
		LogUtil._debug = _debug;
	}

	static interface ManifestMeta {

		String DEPENDENCY_PROVIDER = "ailk_dependency_provider";

		String LOG_LEVEL = "ailk_log_level";

		String DISABLE = "disable";
		String VERBOSE = "verbose";
		String DEBUG = "debug";
		String INFO = "info";
		String WARN = "warn";
		String ERROR = "error";
		String ASSERT = "assert";

	}

}
