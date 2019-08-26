package com.ailk.pmph.ui.view;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ailk.pmph.R;
import com.ailk.pmph.utils.AnimationShowUtil;
import com.ailk.pmph.utils.Helper;


public class ExpandableLayout extends LinearLayout implements View.OnClickListener{
	private View mHandle;
	private FrameLayout mHeaderContainer;
	private TextView mTitle;
	private ImageView mIndicator;
	private FrameLayout mContainer;
	private boolean mExpanded;
	private LayoutInflater mInflater;
	private LayoutTransition mTransitioner;
	private View mDivider;

	public ExpandableLayout(Context context) {
		super(context);
	}
	
	public ExpandableLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	@SuppressLint("NewApi")
	public ExpandableLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}
	
	private void init(Context context, AttributeSet attrs, int defStyle) {
		mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.expandable_layout, this, true);
        
        setOrientation(LinearLayout.VERTICAL);
	}

	@Override
    protected void onFinishInflate() {
		super.onFinishInflate();
		
		mHandle = findViewById(R.id.handle);
		mHeaderContainer = (FrameLayout) findViewById(R.id.header_container);
		mTitle = (TextView)findViewById(R.id.title);
		mIndicator = (ImageView)findViewById(R.id.indicator);
		mContainer = (FrameLayout)findViewById(R.id.content_container);
		mDivider = findViewById(R.id.divider);

		mHandle.setOnClickListener(this);
		
		showContent(mExpanded, false);
		//initLayoutAnimation(this);
	}
	
	@Override
	public void onClick(View v) {
		mExpanded = !mExpanded;
		
		showContent(mExpanded, true);
	}

    public void expand(boolean expand) {
        mExpanded = expand;
        showContent(mExpanded, false);
    }
	
	private void showContent(boolean show, boolean animation) {
		if(show) {
            if(animation) {
                AnimationShowUtil.animateShow(mContainer);
            } else {
                mContainer.setVisibility(View.VISIBLE);
            }
			mIndicator.setImageResource(R.drawable.btn_content_up_n);
			if(mDivider != null) {
                if(animation) {
                    AnimationShowUtil.animateShow(mDivider);
                } else {
                    mDivider.setVisibility(View.VISIBLE);
                }
			}
		} else {
            if(animation) {
                AnimationShowUtil.animateGone(mContainer);
            } else {
                mContainer.setVisibility(View.GONE);
            }
			mIndicator.setImageResource(R.drawable.btn_content_down_n);
			if(mDivider != null) {
                if(animation) {
                    AnimationShowUtil.animateGone(mDivider);
                } else {
                    mDivider.setVisibility(View.GONE);
                }
			}
		}
	}
	
	@SuppressLint("NewApi")
	private void initLayoutAnimation(ViewGroup view){
		if(Helper.getSDKVersion()>11){
			if(mTransitioner == null){
				mTransitioner = new LayoutTransition();
				//mTransitioner.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 10);
				mTransitioner.setDuration(300);
			}
			view.setLayoutTransition(mTransitioner);
		}
	}

	public void setCustomHeader(View view) {
		mTitle.setVisibility(GONE);
		mHeaderContainer.addView(view);
	}

	public void setCustomHeader(int layoutId) {
		mTitle.setVisibility(GONE);
		mInflater.inflate(layoutId, mHeaderContainer, true);
	}
	
	public void setTitle(int resId) {
		mTitle.setText(resId);
	}
	
	public void setTitle(CharSequence title) {
		mTitle.setText(title);
	}
	
	public void setContent(View view) {
		mContainer.removeAllViews();
		mContainer.addView(view);
	}
	
	public void setContent(int layoutId) {
		mContainer.removeAllViews();
		mInflater.inflate(layoutId, mContainer, true);
	}
	
	public View getContentView() {
		if(mContainer.getChildCount() > 0) {
			return mContainer.getChildAt(0);
		}
		
		return null;
	}
}
