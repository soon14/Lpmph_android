package com.ailk.pmph.ui.view;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.view
 * 作者: Chrizz
 * 时间: 2016/11/1
 */

public class MyViewPager extends ViewPager {

    private float mLastX = 0;
    private float mLastY = 0;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // /** 触摸时按下的点 **/
    PointF downP = new PointF();
    //    　　 /** 触摸时当前的点 **/
    PointF curP = new PointF();
    OnSingleTouchListener onSingleTouchListener;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        float x = arg0.getX();
        float y = arg0.getY();
        int action = arg0.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            mLastX = x;
            mLastY = y;
            return false;
        } else {
            return true;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        //每次进行onTouch事件都记录当前的按下的坐标
        curP.x = arg0.getX();
        curP.y = arg0.getY();
        if (arg0.getAction() == MotionEvent.ACTION_DOWN) {//记录按下时候的坐标
            // 切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
            downP.x = arg0.getX();
            downP.y = arg0.getY();
            //此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        if (arg0.getAction() == MotionEvent.ACTION_MOVE) {
            float deltaX = curP.x - mLastX;
            float deltaY = curP.y - mLastY;
            if (parentNeed(deltaX, deltaY)) {

                getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                getParent().requestDisallowInterceptTouchEvent(true);

            }
        }
        if (arg0.getAction() == MotionEvent.ACTION_UP) {
            //在up时判断是否按下和松手的坐标为一个点
            //如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick
            if (downP.x == curP.x && downP.y == curP.y) {
                onSingleTouch();
                return true;
            }
        }
        mLastX = curP.x;
        mLastY = curP.y;
        return super.onTouchEvent(arg0);
    }

    private boolean parentNeed(float deltaX, float deltaY) {
        if (Math.abs(deltaX) > Math.abs(deltaX)) {
            return false;
        } else {
            return true;
        }

    }

    public void onSingleTouch() {
        if (onSingleTouchListener != null) {
            onSingleTouchListener.onSingleTouch();
        }
    }

    public interface OnSingleTouchListener {
        public void onSingleTouch();

    }

    public void setOnSingleTouchListener(OnSingleTouchListener
                                                 onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }

}
