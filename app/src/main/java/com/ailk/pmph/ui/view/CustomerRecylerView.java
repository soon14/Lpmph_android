package com.ailk.pmph.ui.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ailk.pmph.R;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.tool.GlideUtil;
import com.androidquery.AQuery;
import com.bumptech.glide.Glide;

/**
 * Created by jiangwei on 14-12-4.
 */
public class CustomerRecylerView extends RecyclerView {

    public static final int Fling = 1;
    public static final int UNFLY = 0;
    private GestureDetector mGestureDetector;
    private Context mContext;

    public CustomerRecylerView(Context context) {
        super(context);
        this.mContext = context;
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
    }

    public CustomerRecylerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
    }


    public CustomerRecylerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev)
                && mGestureDetector.onTouchEvent(ev);
    }


    public void refreshVisiableItem() {
        int firstPos = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        int lastPos = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        LogUtil.e("====firstpos = "+firstPos+ " lastPos = "+lastPos);

        for(int position = firstPos;position<lastPos;position++){
            View view = getChildAt(position);
            findImageView(view);
        }
    }

    public void findImageView(View view) {
        if (view == null) {
            return;
        }
        if (view instanceof ImageView) {
            LogUtil.e("find imageView set img");
            AQuery aq = (AQuery) view.getTag(R.id.tag_aq);
            String url = (String) view.getTag(R.id.tag_url);
            if (aq == null || url == null) {
                return;
            }
            GlideUtil.loadImg(mContext, url, (ImageView) view);
//            aq.id(view).image(url, true, true, 150,
//                    R.drawable.default_img, aq.getCachedImage(R.drawable.default_img), 0);
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View itemView = viewGroup.getChildAt(i);
                findImageView(itemView);
            }
        }

    }

    /**
     * 在ViewGroup中根据id进行查找
     *
     * @param vg
     * @param id 如：R.id.tv_name
     * @return
     */
    private View findViewInViewGroupById(ViewGroup vg, int id) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if (v.getId() == id) {
                return v;
            } else {
                if (v instanceof ViewGroup) {
                    return findViewInViewGroupById((ViewGroup) v, id);
                }
            }
        }
        return null;
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            /**
             * 如果我们滚动更接近水平方向,返回false,让子视图来处理它
             */
            return (Math.abs(distanceY) > (Math.abs(distanceX)));
//            return true;
        }

    }

}
