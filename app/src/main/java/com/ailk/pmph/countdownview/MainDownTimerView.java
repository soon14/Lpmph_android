package com.ailk.pmph.countdownview;

import android.content.Context;
import android.util.AttributeSet;


public class MainDownTimerView extends BaseCountDownTimerView{

	public MainDownTimerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MainDownTimerView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MainDownTimerView(Context context) {
		this(context,null);
	}

	@Override
	protected String getStrokeColor() {
		return "f83270";
	}

	@Override
	protected String getTextColor() {
		return "ffffff";
	}

	@Override
	protected int getCornerRadius() {
		return 2;
	}

	@Override
	protected int getTextSize() {
		return 12;
	}

	@Override
	protected String getBackgroundColor() {
		return "f83270";
	}

	
	
}
