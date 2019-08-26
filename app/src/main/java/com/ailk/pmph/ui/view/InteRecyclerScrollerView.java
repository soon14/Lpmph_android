package com.ailk.pmph.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.ailk.integral.holder.InteHomeViewHolder;
import com.ailk.integral.holder.InteHomeViewPagerHolder;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.tool.GlideUtil;
import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class InteRecyclerScrollerView extends ScrollView {


    private RecyclerView.Adapter<InteHomeViewHolder> madapter;
    private LinearLayout mLayout;

    private List<ImageView> imageViews;
    private long lastRequestTime;
    private Handler mHandler;
    private Context mContext;

    public InteRecyclerScrollerView(Context context) {
        super(context);
        this.mContext = context;
        init(null, 0);
    }

    public InteRecyclerScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs, 0);
    }

    public InteRecyclerScrollerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        mLayout = new LinearLayout(getContext());
        imageViews = new ArrayList<>();
        mHandler = new Handler();
        addView(mLayout);

    }

    public void setLayoutManager(LinearLayoutManager linearLayoutManager) {
        mLayout.setOrientation(LinearLayout.VERTICAL);
    }

    private void initView() {
        mLayout.removeAllViews();
        imageViews.clear();

        if (madapter != null) {
            int count = madapter.getItemCount();
            for (int i = 0; i < count; i++) {
                InteHomeViewHolder viewHolder = madapter.onCreateViewHolder(this, madapter.getItemViewType(i));
                View itemView = viewHolder.itemView;
                mLayout.addView(itemView);
                madapter.onBindViewHolder(viewHolder, i);
                if (viewHolder instanceof InteHomeViewPagerHolder) {
                    continue;
                }
                findImageView(itemView);
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkIsVisable(true);
            }
        }, 200);
    }

    private void findImageView(View itemView) {
        if (itemView instanceof ImageView) {
            if (itemView.getTag(R.id.tag_url) == null
                    || itemView.getTag(R.id.tag_aq) == null
                    || itemView.getTag(R.id.tag_width) == null) {
                return;
            }
            imageViews.add((ImageView) itemView);
        } else if (itemView instanceof ViewGroup) {
            int childCount = ((ViewGroup) itemView).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View v = ((ViewGroup) itemView).getChildAt(i);
                findImageView(v);
            }
        }

    }

    public RecyclerView.Adapter<InteHomeViewHolder> getAdapter() {

        return madapter;
    }

    public void setAdapter(RecyclerView.Adapter<InteHomeViewHolder> adapter) {
        this.madapter = adapter;
        initView();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(Math.abs(t-oldt)<=2){//滚动将要停止时，强制刷新当前界面
            willRefresh();
        }else {
            checkIsVisable(false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                willRefresh();
            break;
        }

        return super.onTouchEvent(ev);
    }

    private boolean willRefresh;

    public synchronized  void willRefresh(){
        if(willRefresh){
            return;
        }
        willRefresh = true;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                willRefresh = false;
                checkIsVisable(true);
            }
        },300);
    }

    @Override
    public void fling(int velocityY) {
        LogUtil.e("onfling = " + velocityY);
        //不用限制主页滑动的速度
//        if(velocityY>4000){
//            velocityY = 4000;
//        }else if(velocityY<-4000){
//            velocityY = -4000;
//        }
        super.fling(velocityY);
        willRefresh();
    }

    public void checkIsVisable(boolean ismustRefresh) {
        if (System.currentTimeMillis() - lastRequestTime < 200) {
            if (!ismustRefresh) {
                return;
            }
        }
        lastRequestTime = System.currentTimeMillis();
        Rect scrollBounds = new Rect();
        getHitRect(scrollBounds);
        for (ImageView imgView : imageViews) {
            AQuery aq = (AQuery) imgView.getTag(R.id.tag_aq);
            String url = (String) imgView.getTag(R.id.tag_url);
            int targetWidth = Integer.parseInt(String.valueOf(imgView.getTag(R.id.tag_width)));
            String visable = (String) imgView.getTag(R.id.tag_visiable);
            if (imgView.getLocalVisibleRect(scrollBounds)) {
                if ("1".equals(visable)) {//已经设置过图片
                    continue;
                }
                imgView.setTag(R.id.tag_visiable, "1");
                GlideUtil.loadImg(mContext, url, imgView);
//                aq.id(imgView).image(url, true, true, targetWidth,
//                        R.drawable.default_img, AppContext.bmPreset, android.R.anim.fade_in);
            } else {
                if (!"1".equals(visable)) {//已经将图片设置成default
                    continue;
                }
                GlideUtil.loadImg(mContext, url, imgView);
                imgView.setImageBitmap(null);
                imgView.setTag(R.id.tag_visiable, "0");
            }
        }
    }

}
