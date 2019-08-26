package com.ailk.pmph.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;

import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.ui.activity.LoginActivity;
import com.ailk.pmph.ui.activity.LoginPmphActivity;
import com.androidquery.callback.AjaxStatus;

import org.apache.commons.lang.StringUtils;

public class HandlerErroUtil {

	public static boolean handlerStatus(Context context,AjaxStatus status,boolean isShowToast){
		switch (status.getCode()) {
		case AjaxStatus.TRANSFORM_ERROR:
			ToastUtil.show(context, "解析错误",isShowToast);
			return true;
		case AjaxStatus.NETWORK_ERROR:
			ToastUtil.show(context, "网络连接错误",isShowToast);
			return true;
		case AjaxStatus.AUTH_ERROR:
			ToastUtil.show(context, "授权错误",isShowToast);
			return true;
		}
		
		if(status.getCode() == 200){
			return false;
		}else{
			ToastUtil.show(context, "连接错误",isShowToast);
			return true;
		}
	}
	
	public static boolean handlerHeader(final Context context,AppHeader header,boolean isShowToast){
		if (header != null) {
			if ("0000".equals(header.getRespCode())) {
				return false;
			}
			else if("0001".equals(header.getRespCode())){
//				AppUtility.getInstance().setSessionId(null);
//				DialogAnotherUtil.dismissDialog();
//				DialogUtil.dismissDialog();
//				DialogAnotherUtil.showCustomAlertDialogWithMessage(context, null,
//						"您未登录，请先登录", "登录", "取消",
//						new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								DialogUtil.dismissDialog();
//								if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
//									Intent it = new Intent(context, LoginPmphActivity.class);
//									it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//									context.startActivity(it);
//								} else {
//									Intent it = new Intent(context, LoginActivity.class);
//									it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//									context.startActivity(it);
//								}
//							}
//						},
//						new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								DialogUtil.dismissDialog();
//							}
//						});
//				return true;
			}
			else if("0005".equals(header.getRespCode())){
				AppUtility.getInstance().setSessionId(null);
				DialogAnotherUtil.dismissDialog();
				DialogUtil.dismissDialog();
				DialogAnotherUtil.showCustomAlertDialogWithMessage(context, null,
						"您未登录，请先登录", "登录", "取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								DialogUtil.dismissDialog();
								if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
									Intent it = new Intent(context, LoginPmphActivity.class);
									it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									context.startActivity(it);
								} else {
									Intent it = new Intent(context, LoginActivity.class);
									it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									context.startActivity(it);
								}
							}
						},
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								DialogUtil.dismissDialog();
							}
						});
				return true;
			}else{
				if(header.getRespMsg()!=null){
					ToastUtil.show(AppContext.getContext(),
							 header.getRespMsg(),isShowToast);
				}
				return true;
			}
		}
		return true;
	}

    public static void showError(Context context, AppHeader header, String default_error) {
        String error = default_error;
        if(header != null) {
            String msg = header.getRespMsg();

            if (!TextUtils.isEmpty(msg)) {
                error = msg;
            }
        }
        ToastUtil.show(context, error);
    }
	public static void showErrorDialog(Context context, AppHeader header, String default_error, DialogInterface.OnClickListener onOkClickListener) {
		String error = default_error;
		if(header != null) {
			String msg = header.getRespMsg();
			if (!TextUtils.isEmpty(msg)) {
				error = msg;
			}
		}
		DialogAnotherUtil.showOkAlertDialog(context, error, onOkClickListener);
	}
	
}
