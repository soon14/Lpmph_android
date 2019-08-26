package com.ailk.pmph.utils;

import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;

public class AnimationShowUtil {

	public static void animateGone(final View view) {
		if (view.getVisibility() == View.GONE) {
			return;
		}
		int origHeight = view.getHeight();

		ValueAnimator animator = createHeightAnimator(view, origHeight, 0);
		animator.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(final Animator animation) {
				view.setVisibility(View.GONE);
			}
		});
		animator.start();
	}

	public static void animateShow(final View view) {
		if (view.getVisibility() == View.VISIBLE) {
			return;
		}
		view.setVisibility(View.VISIBLE);

		View parent = (View) view.getParent();
		final int widthSpec = View.MeasureSpec.makeMeasureSpec(
				parent.getMeasuredWidth() - parent.getPaddingLeft()
						- parent.getPaddingRight(), View.MeasureSpec.AT_MOST);
		final int heightSpec = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(widthSpec, heightSpec);

		ValueAnimator animator = createHeightAnimator(view, 0,
				view.getMeasuredHeight());
		animator.setDuration(200);
		animator.start();
	}
	

	public static ValueAnimator createHeightAnimator(final View view,
			final int start, final int end) {
		ValueAnimator animator = ValueAnimator.ofInt(start, end);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(final ValueAnimator animation) {
				int value = (Integer) animation.getAnimatedValue();
				LayoutParams layoutParams = view.getLayoutParams();
				layoutParams.height = value;
				view.setLayoutParams(layoutParams);
			}
		});
		animator.setDuration(200);
		return animator;
	}
}
