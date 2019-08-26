/**
 *
 */
package com.ailk.pmph.utils;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Spanned;

import com.ailk.pmph.R;
import com.ailk.pmph.ui.view.CustomDialog;
import com.ailk.pmph.ui.view.CustomProgressDialog;


/**
 * Dialog 工具类
 *
 * @author
 *
 */
public class DialogUtil {

	private static Dialog mAlertDialog = null;

	/**
	 * 取消所有弹出的对话框
	 */
	public static void dismissDialog() {
		if (mAlertDialog != null)
			mAlertDialog.dismiss();
		mAlertDialog = null;
	}

	/**
	 * 显示进度对话框（不可取消）
	 *
	 * @param title
	 * @param message
	 * @param onCancelListener
	 */
	public static void showProgressDialog(Context context, String title,
			String message, DialogInterface.OnCancelListener onCancelListener) {
		if (mAlertDialog != null) {
			if(mAlertDialog.isShowing())
				mAlertDialog.dismiss();
			mAlertDialog = null;
		}
		mAlertDialog = new ProgressDialog(context, R.style.CustomProgressDialog);
		if (title != null) {
			mAlertDialog.setTitle(title);
		}
		if (message != null) {
			((ProgressDialog)mAlertDialog).setMessage(message);
		}
		if (onCancelListener != null) {
			mAlertDialog.setOnCancelListener(onCancelListener);
		}
		mAlertDialog.setCancelable(false);
		mAlertDialog.show();
	}
	
	/**
	 * 显示进度对话框（不可取消）
	 *
	 */
	public static void showCustomerProgressDialog(Context context) {
		try {
			getCustomerProgressDialog(context).show();
		} catch (Exception e) {
			LogUtil.e(e);
		}
	}
	
	/**
	 * 显示进度对话框（不可取消）
	 *
	 */
	public static CustomProgressDialog getCustomerProgressDialog(Context context) {
		if (mAlertDialog != null) {
			if(mAlertDialog.isShowing())
				mAlertDialog.dismiss();
			if(mAlertDialog instanceof CustomProgressDialog){
				if(context == mAlertDialog.getContext()){
					return (CustomProgressDialog)mAlertDialog;
				}
			}
			mAlertDialog = null;
		}
		mAlertDialog = CustomProgressDialog.createDialog(context);
		mAlertDialog.setCancelable(false);
//		TODO:BaseActivity暂时不使用 1
//		if(context instanceof IMBaseActivity){
//			mAlertDialog.setOnDismissListener(((IMBaseActivity)context).getDialogcanlistener());
//		}
		
		return (CustomProgressDialog)mAlertDialog;
	}
	
	/**
	 * 显示进度对话框（不可取消）
	 *
	 */
	public static ProgressDialog getCustomerProgressBarDialog(Context context,String title) {
		if (mAlertDialog != null) {
			if(mAlertDialog.isShowing())
				mAlertDialog.dismiss();
			mAlertDialog = null;
		}
		mAlertDialog = new ProgressDialog(context);
		((ProgressDialog)mAlertDialog).setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
		mAlertDialog.setTitle(title);
		mAlertDialog.setCancelable(false);
		return (ProgressDialog)mAlertDialog;
	}
	
	
	

	public static void setMessage(String message){
		if(mAlertDialog !=null)
			if(mAlertDialog instanceof ProgressDialog){
				if (message != null) {
					((ProgressDialog)mAlertDialog).setMessage(message);
				}
			}
	}


	/**
	 * 显示”确定“ 对话框
	 *
	 */
	public static void showOkAlertDialog(Context context, String msg,
			DialogInterface.OnClickListener onOkClickListener) {
		CustomDialog.Builder alertDialogBuilder = new CustomDialog.Builder(context);
		alertDialogBuilder.setTitle("提示");
		alertDialogBuilder.setMessage(msg);
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton(
				context.getString(R.string.str_confirm), onOkClickListener);
		mAlertDialog = alertDialogBuilder.show();
	}
	/**
	 * 显示”确定“ 对话框
	 *
	 * @param title
	 * @param onOkClickListener
	 */
	public static void showOkAlertDialog(Context context, String title,String msg,
			DialogInterface.OnClickListener onOkClickListener) {
		CustomDialog.Builder alertDialogBuilder = new CustomDialog.Builder(context);
		alertDialogBuilder.setTitle(title);
		alertDialogBuilder.setMessage(msg);
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton(
				context.getString(R.string.str_confirm), onOkClickListener);
		mAlertDialog = alertDialogBuilder.show();
	}
	
	/**
	 * 显示”确定“ 对话框
	 *
	 * @param title
	 * @param onOkClickListener
	 */
	public static void showOkAlertDialog(Context context, String title,Spanned  msg,
			DialogInterface.OnClickListener onOkClickListener) {
		CustomDialog.Builder alertDialogBuilder = new CustomDialog.Builder(context);
		alertDialogBuilder.setTitle(title);
		alertDialogBuilder.setMessage(msg);
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton(
				context.getString(R.string.str_confirm), onOkClickListener);
		mAlertDialog = alertDialogBuilder.show();
	}

	/**
	 * 显示”是“ ”否“ 对话框
	 *
	 * @param onYesClickListener
	 * @param onNoClickListener
	 */
	public static void showYesNoAlertDialog(Context context, String messge,
			DialogInterface.OnClickListener onYesClickListener,
			DialogInterface.OnClickListener onNoClickListener) {
		CustomDialog.Builder alertDialogBuilder = new CustomDialog.Builder(
				context);
		alertDialogBuilder.setTitle("提示");
		if (messge != null) {
			alertDialogBuilder.setMessage(messge);
		}
		if (onYesClickListener != null) {
			alertDialogBuilder.setPositiveButton(
					context.getString(R.string.positive), onYesClickListener);
		}
		if (onNoClickListener != null) {
			alertDialogBuilder.setNegativeButton(
					context.getString(R.string.negative), onNoClickListener);
		}
		alertDialogBuilder.setCancelable(false);
		mAlertDialog = alertDialogBuilder.show();
	}
	/**
	 * 显示”是“ ”否“ 对话框
	 *
	 * @param title
	 * @param onYesClickListener
	 * @param onNoClickListener
	 */
	public static void showYesNoAlertDialog(Context context, String title,String messge,
			DialogInterface.OnClickListener onYesClickListener,
			DialogInterface.OnClickListener onNoClickListener) {
		CustomDialog.Builder alertDialogBuilder = new CustomDialog.Builder(
				context);
		if (title != null) {
			alertDialogBuilder.setTitle(title);
		}
		if (messge != null) {
			alertDialogBuilder.setMessage(messge);
		}
		if (onYesClickListener != null) {
			alertDialogBuilder.setPositiveButton(
					context.getString(R.string.positive), onYesClickListener);
		}
		if (onNoClickListener != null) {
			alertDialogBuilder.setNegativeButton(
					context.getString(R.string.negative), onNoClickListener);
		}
		alertDialogBuilder.setCancelable(false);
		mAlertDialog = alertDialogBuilder.show();
	}

	/**
	 * 显示定义对话框
	 *
	 * @param title
	 * @param onYesClickListener
	 * @param onNoClickListener
	 */
	public static void showCustomAlertDialog(
			Context context,
			String title,
			String confirmStr,
			String cancelStr,
			DialogInterface.OnClickListener onYesClickListener,
			DialogInterface.OnClickListener onNoClickListener) {
		CustomDialog.Builder alertDialogBuilder = new CustomDialog.Builder(context);
		if(title != null){
			alertDialogBuilder.setTitle(title);
		}

		if(confirmStr != null){
			alertDialogBuilder.setPositiveButton(confirmStr, onYesClickListener);
		}
		if(cancelStr != null){
			alertDialogBuilder.setNegativeButton(cancelStr, onNoClickListener);
		}
		alertDialogBuilder.setCancelable(false);
		mAlertDialog = alertDialogBuilder.show();
	}

	/**
	 * 显示定义对话框
	 *
	 * @param title
	 * @param message
	 * @param confirmStr
	 * @param cancelStr
	 * @param onYesClickListener
	 * @param onNoClickListener
	 */
	public static void showCustomAlertDialogWithMessage(
			Context context,
			String title,
			String message,
			String confirmStr,
			String cancelStr,
			DialogInterface.OnClickListener onYesClickListener,
			DialogInterface.OnClickListener onNoClickListener) {
		CustomDialog.Builder alertDialogBuilder = new CustomDialog.Builder(context);
		if(title != null){
			alertDialogBuilder.setTitle(title);
		}
		if(message != null){
			alertDialogBuilder.setMessage(message);
		}
		if(onYesClickListener != null && confirmStr != null){
			alertDialogBuilder.setPositiveButton(confirmStr, onYesClickListener);
		}
		if(onNoClickListener != null && cancelStr != null){
			alertDialogBuilder.setNegativeButton(cancelStr, onNoClickListener);
		}
		alertDialogBuilder.setCancelable(false);
		mAlertDialog = alertDialogBuilder.show();
	}

}
