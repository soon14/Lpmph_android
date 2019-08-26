package com.ailk.integral.holder;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ai.ecp.app.resp.Cms008Resp;
import com.ai.ecp.app.resp.Cms101Resp;
import com.ailk.jazzyviewpager.JazzyViewPager;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.fragment.viewholder.HomeViewHolder;
import com.ailk.pmph.ui.view.FixedSpeedScroller;
import com.ailk.pmph.ui.view.MaskableImageView;
import com.viewpagerindicator.CirclePageIndicator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Type = 0
 * Created by jiangwei on 14-12-2.
 */
@InteHomeViewType(ViewType=200)
public class InteHomeViewPagerHolder extends InteHomeViewHolder {

    private JazzyViewPager viewPager;
    private List<Cms101Resp.Item> items;
    private MyAdapter mAdapter;
    private RelativeLayout homeViewPager;
    private OnScrollListener onScrollListener;


    public InteHomeViewPagerHolder(Activity activity, LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity, inflater, viewGroup, R.layout.home_viewpager_layout, R.dimen.home_modey_body_width, R.dimen.home_model_type0_height);
        items = new ArrayList<>();
        createScroller(viewPager);
        viewPager.setTransitionEffect(JazzyViewPager.TransitionEffect.Accordion);
        mAdapter = new MyAdapter();
        viewPager.setAdapter(mAdapter);
    }

    @Override
    public void initData(Cms101Resp.Model model) {
        super.initData(model);
        //TODO-- 填充模块
        this.items = model.getItemList();
        createPageIndicator();
        mAdapter.notifyDataSetChanged();
    }

    private void createScroller(ViewPager viewPager) {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Interpolator sInterpolator = new AccelerateInterpolator();
            FixedSpeedScroller scroller = new FixedSpeedScroller(
                    viewPager.getContext(), sInterpolator);
            scroller.setFixedDuration(200);
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

    }

    @Override
    protected void initBodyView(ViewGroup viewGroup) {
        this.homeViewPager = (RelativeLayout) viewGroup.findViewById(R.id.home_pager_ads);
        this.viewPager = (JazzyViewPager) homeViewPager.findViewById(R.id.viewpager);
        this.model_body.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void createPageIndicator() {
        CirclePageIndicator indicator = (CirclePageIndicator) homeViewPager
                .findViewById(R.id.home_indicator);
        if (mAdapter.getCount()==1) {
            indicator.setVisibility(View.GONE);
        } else {
            indicator.setViewPager(viewPager);
            final float density = homeViewPager.getResources().getDisplayMetrics().density;
            indicator.setRadius(4 * density);
            indicator.setPageColor(0xFFFFFFFF);
            indicator.setFillColor(0xFFFF7700);
            indicator.setStrokeWidth(0);

            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                    if (onScrollListener != null) {

                        if (state == ViewPager.SCROLL_STATE_IDLE) {
                            onScrollListener.onScrolling(false);
                        } else if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                            onScrollListener.onScrolling(true);
                        } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
                            onScrollListener.onScrolling(true);
                        }
                    }
                }
            });
        }
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public void showNextPager() {
        int currentPager = viewPager.getCurrentItem();
        currentPager++;
        if (mAdapter != null && currentPager == mAdapter.getCount()) {
            currentPager = 0;
        }
        viewPager.setCurrentItem(currentPager, true);
    }


    public interface OnScrollListener {
        void onScrolling(boolean isScrolling);
    }

    private class MyAdapter extends PagerAdapter {


        private SparseArray<ImageView> mListViews;


        public MyAdapter() {
            this.mListViews = new SparseArray<ImageView>();//构造方法，参数是我们的页卡，这样比较方便。
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mListViews.get(position).setImageBitmap(null);
            container.removeView(mListViews.get(position));//删除页卡
            mListViews.remove(position);
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {    //这个方法用来实例化页卡
           ImageView imageView = mListViews.get(position);
            if(imageView == null) {
                imageView = new MaskableImageView(container.getContext(), null, R.style.home_img_scale_type2);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                mListViews.put(position, imageView);
            }
            setImageViewImg(items.get(position).getImgUrl(), imageView, aq, 640);
            setOnClickListener(imageView, items.get(position).getClickUrl(),items.get(position).getShareKey());
            container.addView(imageView);//添加页卡
            return imageView;
        }

        @Override
        public int getCount() {
            return items.size();//返回页卡的数量
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;//官方提示这样写
        }

    }


}

