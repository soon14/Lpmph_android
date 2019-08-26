package com.ailk.pmph.countdownview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.TextView;

import com.ailk.pmph.R;

/**
 * 标签制作
 * 
 * @author Linhai Gu
 * 
 */
public class GradientTextView {

	private GradientDrawable mGradientDrawable;
	private TextView mLabelTextView;
	private Context mContext;

	public GradientTextView(Context _context) {
		this.mContext = _context;
		mGradientDrawable = new GradientDrawable();
		mLabelTextView = new TextView(mContext);
		initGradientDrawable();
		initLabelTextView();
	}

	/**
	 * 初始化GradientDrawable
	 */
	private void initGradientDrawable() {
		mGradientDrawable.setColor(ContextCompat.getColor(mContext, R.color.red_f2315e));
		mGradientDrawable.setCornerRadius(dip2px(2));
		mGradientDrawable.setStroke(dip2px(1), ContextCompat.getColor(mContext, R.color.red_f2315e));
		mGradientDrawable.setAlpha(255);
	}

	/**
	 * 初始化标签
	 */
	private void initLabelTextView() {
		mLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		mLabelTextView.setPadding(dip2px(3), dip2px(3), dip2px(3), dip2px(3));
	}

	/**
	 * dp-->px
	 * 
	 * @param dipValue
	 * @return
	 */
	private int dip2px(float dipValue) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 是否为空
	 * 
	 * @param str
	 * @return
	 */
	private boolean empty(String str) {
		return TextUtils.isEmpty(str);
	}

	/**
	 * 转换成颜色值
	 * 
	 * @param color
	 * @return
	 */
	private int parseColor(String color) {
		return Color.parseColor("#" + color);
	}

	/**
	 * 设置边框圆角
	 * @param radius
	 */
	public void setCornerRadius(int radius){
		mGradientDrawable.setCornerRadius(dip2px(1));
	}
	
	/**
	 * 标签字体颜色
	 * 
	 * @param color
	 * @return
	 */
	public GradientTextView setTextColor(String color) {
		if (!empty(color)) {
			try {
				mLabelTextView.setTextColor(parseColor(color));
			} catch (Exception e) {

			}
		}
		return this;
	}

	/**
	 * 标签背景颜色
	 * 
	 * @param color
	 * @return
	 */
	public GradientTextView setBackgroundColor(String color) {
		if (!empty(color)) {
			mGradientDrawable.setColor(parseColor(color));
		}
		return this;
	}

	/**
	 * 标签外框颜色
	 * 
	 * @param color
	 * @return
	 */
	public GradientTextView setStrokeColor(String color) {
		if (!empty(color)) {
			mGradientDrawable.setStroke(dip2px(1), parseColor(color));
		}
		return this;
	}

	public GradientTextView setStrokeRadius(int radius) {
		mGradientDrawable.setCornerRadius(dip2px(radius));
		return this;
	}

	/**
	 * 设置标签内容
	 * 
	 * @param info
	 * @return
	 */
	public GradientTextView setLabelText(String info) {
		if (!empty(info)) {
			mLabelTextView.setText(info);
		}
		return this;
	}

	/**
	 * 设置标签大小 
	 * @param size
	 * @return
	 */
	public GradientTextView setTextSize(int size) {
		mLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
		return this;
	}

	/**
	 * 构造TextView
	 * 
	 * @return
	 */
	public TextView build() {
		mLabelTextView.setBackgroundDrawable(mGradientDrawable);
		return mLabelTextView;
	}

}
