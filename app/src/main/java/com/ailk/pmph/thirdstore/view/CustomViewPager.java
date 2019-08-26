package com.ailk.pmph.thirdstore.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 类描述：
 * 项目名称： pmph
 * Package:  com.ailk.pmph.ui.view
 * 创建人：   Nzke
 * 创建时间： 2016/06/6 15:28
 * 修改人：   Nzke
 * 修改时间： 2016/06/6 15:28
 * 修改备注： 2016/06/6 15:28
 * version   v1.0
 */
public class CustomViewPager extends ViewPager {

    private boolean isCanScroll = true;//为true时禁止ViewPage滑动

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            return false;
        }
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (isCanScroll) {
            return false;
        }
        return super.onTouchEvent(arg0);
    }

}

