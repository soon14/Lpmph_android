package com.ailk.pmph.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Cms001Req;
import com.ai.ecp.app.req.Prom006Req;
import com.ai.ecp.app.req.Prom007Req;
import com.ai.ecp.app.resp.Cms001Resp;
import com.ai.ecp.app.resp.KillGdsInfoRespVO;
import com.ai.ecp.app.resp.KillPromInfoRespVO;
import com.ai.ecp.app.resp.Prom006Resp;
import com.ai.ecp.app.resp.Prom007Resp;
import com.ai.ecp.app.resp.cms.AdvertiseRespVO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.jazzyviewpager.JazzyViewPager;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.countdownview.TimerTextView;
import com.ailk.pmph.ui.adapter.SecondKillAdapter;
import com.ailk.pmph.ui.view.FixedSpeedScroller;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DateUtils;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.PromotionParseUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.tool.GlideUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.viewpagerindicator.CirclePageIndicator;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:秒杀
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/11/1
 */

public class SecondKillActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.pic_layout)
    RelativeLayout mPicLayout;
    @BindView(R.id.pic_viewpager)
    JazzyViewPager mPicViewPager;
    @BindView(R.id.pic_indicator)
    CirclePageIndicator mPicIndicator;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.rv_prom_list)
    PullToRefreshRecyclerView mRecyclerView;
    @BindView(R.id.tv_kill_text)
    TextView mKillText;
    @BindView(R.id.tv_remain_text)
    TextView mRemainTextTv;
    @BindView(R.id.tv_remain_time)
    TimerTextView mTextDownTimerView;

    private List<AdvertiseRespVO> mImageList;
    private PicAdapter picAdapter;
    private OnScrollListener onScrollListener;
    private TimeTask timeTask;
    private boolean taskAlive = true;
    private SecondKillAdapter mAdapter;
    private int mPageNo = 1;
    private Long mPromId;
    private List<KillPromInfoRespVO> mTabList;

    public static final String REFRESH_KILL = "refresh_kill";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_second_kill;
    }

    @Override
    public void initView() {
        mImageList = new ArrayList<>();
        if (null == mTabList) {
            mTabList = new ArrayList<>();
        }
        mTabList.clear();
        createScroller(mPicViewPager);
        mPicViewPager.setTransitionEffect(JazzyViewPager.TransitionEffect.Accordion);
        picAdapter = new PicAdapter();
        mPicViewPager.setAdapter(picAdapter);

        mAdapter = new SecondKillAdapter(this, new ArrayList<KillGdsInfoRespVO>());
        mAdapter.clear();
        mRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        requestCms001();
        requestProm006();

        if (null == timeTask) {
            timeTask = new TimeTask();
        }
        startTimeTask();
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equals(intent.getAction(), REFRESH_KILL)) {
                mTabLayout.removeAllTabs();
                Prom006Req request = new Prom006Req();
                request.setSiteId(1L);
                request.setPageNo(-1);
                getJsonService().requestProm006Ag(SecondKillActivity.this, request, false, 2000, new JsonService.CallBack<Prom006Resp>() {
                    @Override
                    public void oncallback(Prom006Resp prom006Resp) {
                        if (prom006Resp != null) {
                            if (null == mTabList) {
                                mTabList = new ArrayList<>();
                            }
                            mTabList.clear();
                            mTabList = prom006Resp.getResList();
                            if (mTabList != null && mTabList.size() != 0) {
                                for (int i = 0; i < mTabList.size(); i++) {
                                    KillPromInfoRespVO respVO = mTabList.get(i);
                                    mTabLayout.addTab(mTabLayout.newTab().setText(respVO.getPromTheme()));
                                    mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                                }
                            }
                        }
                    }

                    @Override
                    public void onErro(AppHeader header) {

                    }
                });
            }
        }
    };

    @Override
    public void initData() {
        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mPageNo = 1;
                mAdapter.clear();
                requestProm007(mPromId, mPageNo);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                requestProm007(mPromId, ++mPageNo);
            }
        });

        mAdapter.setOnItemClickListener(new SecondKillAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                KillGdsInfoRespVO respVO = mAdapter.getItem(position - 1);
                Long gdsId = respVO.getGdsId();
                Long skuId = respVO.getSkuId();
                Bundle bundle = new Bundle();
                if (gdsId != null) {
                    bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(gdsId));
                }
                if (skuId != null) {
                    bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(skuId));
                }
                launch(ShopDetailActivity.class, bundle);
            }
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REFRESH_KILL);
        registerReceiver(receiver,intentFilter);
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
    public void onPause() {
        super.onPause();
        if (null != timeTask) {
            if (!timeTask.isSuspend()) {
                timeTask.setSuspend(true);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null == timeTask) {
            return;
        }
        if (timeTask.isSuspend()) {
            timeTask.setSuspend(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        if (timeTask != null) {
            taskAlive = false;
            timeTask.cancel(true);
        }
    }

    /**
     * 获取广告图
     */
    private void requestCms001() {
        Cms001Req request = new Cms001Req();
        request.setPlaceId(1309L);
        getJsonService().requestCms001(this, request, false, new JsonService.CallBack<Cms001Resp>() {
            @Override
            public void oncallback(Cms001Resp cms001Resp) {
                if (cms001Resp != null) {
                    if (null == mImageList) {
                        mImageList = new ArrayList<>();
                    }
                    mImageList.clear();
                    for (AdvertiseRespVO image : cms001Resp.getAdvertiseList()) {
                        mImageList.add(image);
                    }
                    createPageIndicator();
                    picAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void createPageIndicator() {
        CirclePageIndicator indicator = (CirclePageIndicator) mPicLayout.findViewById(R.id.pic_indicator);
        if (picAdapter.getCount() == 1) {
            setGone(indicator);
        } else {
            indicator.setViewPager(mPicViewPager);
            final float density = mPicLayout.getResources().getDisplayMetrics().density;
            indicator.setRadius(3 * density);
            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                indicator.setFillColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
            } else {
                indicator.setFillColor(ContextCompat.getColor(this, R.color.red));
            }
            indicator.setPageColor(ContextCompat.getColor(this, R.color.gray_c0c1c3));
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

    private void startTimeTask() {
        if (null == timeTask) {
            timeTask = new TimeTask();
        }
        timeTask.execute();
    }

    public interface OnScrollListener {
        void onScrolling(boolean isScrolling);
    }

    private class PicAdapter extends PagerAdapter {

        private SparseArray<ImageView> imageViews;

        public PicAdapter() {
            this.imageViews = new SparseArray<>();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            imageViews.get(position).setImageBitmap(null);
            container.removeView(imageViews.get(position));//删除页卡
            imageViews.remove(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = imageViews.get(position);
            if (imageView == null) {
                imageView = (ImageView) LayoutInflater.from(SecondKillActivity.this).inflate(R.layout.maskable_image_view, container, false);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageViews.put(position, imageView);
            }
            GlideUtil.loadImg(SecondKillActivity.this, mImageList.get(position).getVfsUrl(), imageView);

            String linkUrl = mImageList.get(position).getLinkUrl();
            if (StringUtils.isNotEmpty(linkUrl)) {
                HashMap<String,String> map = new HashMap<>();
                map.put("clickUrl", linkUrl);
                imageView.setTag(map);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if((v.getTag()!=null && v.getTag() instanceof HashMap) ){
                            HashMap<String,String> map = (HashMap<String, String>) v.getTag();
                            String url = map.get("clickUrl");
                            PromotionParseUtil.parsePromotionUrl(SecondKillActivity.this, url, true, false, null);
                        }
                    }
                });
            }
            container.addView(imageView);//添加页卡
            return imageView;
        }

        @Override
        public int getCount() {
            return (null != mImageList && 0 != mImageList.size()) ? mImageList.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    class TimeTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        private boolean suspend = false;

        private String control = "";

        public boolean isSuspend() {
            return this.suspend;
        }

        public void showNextPager() {
            int currentPager = mPicViewPager.getCurrentItem();
            currentPager++;
            if (picAdapter != null && currentPager == picAdapter.getCount()) {
                currentPager = 0;
            }
            mPicViewPager.setCurrentItem(currentPager, true);
        }

        public void setSuspend(boolean suspend) {
            if (this.suspend == suspend) {
                return;
            }
            this.suspend = suspend;
            if (!this.suspend) {
                synchronized (control) {
                    control.notifyAll();
                }
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (taskAlive) {
                synchronized (control) {
                    if (suspend) {
                        try {
                            control.wait();
                        } catch (InterruptedException e) {

                        }
                    }
                }
                try {
                    Thread.sleep(3000);
                    publishProgress();
                } catch (InterruptedException e) {

                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... params) {
            showNextPager();
        }
    }

    /**
     * 获取标签
     */
    private void requestProm006() {
        final Prom006Req request = new Prom006Req();
        request.setSiteId(1L);
        request.setPageNo(-1);
        getJsonService().requestProm006(this, request, false, new JsonService.CallBack<Prom006Resp>() {
            @Override
            public void oncallback(Prom006Resp prom006Resp) {
                if (prom006Resp != null) {
                    mTabList.clear();
                    mTabList = prom006Resp.getResList();
                    if (mTabList != null && mTabList.size() != 0) {
                        for (KillPromInfoRespVO respVO : mTabList) {
                            mTabLayout.addTab(mTabLayout.newTab().setText(respVO.getPromTheme()));
                            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                        }
                        mPromId = mTabList.get(0).getId();
                        mPageNo = 1;
                        mAdapter.clear();
                        requestProm007(mPromId, mPageNo);
                        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                mPromId = mTabList.get(tab.getPosition()).getId();
                                mPageNo = 1;
                                mAdapter.clear();
                                requestProm007(mPromId, mPageNo);
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 获取促销列表
     * @param promId
     */
    private void requestProm007(Long promId, int pageNo) {
        Prom007Req request = new Prom007Req();
        request.setPromId(promId);
        request.setPageNo(pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestProm007(this, request, true, new JsonService.CallBack<Prom007Resp>() {
            @Override
            public void oncallback(Prom007Resp prom007Resp) {
                if (prom007Resp != null) {
                    List<KillGdsInfoRespVO> list = prom007Resp.getKillGdsList();
                    String ifStart = prom007Resp.getIfStart();
                    Date startTime = prom007Resp.getStartTime();
                    Date endTime = prom007Resp.getEndTime();
                    Date nowTime = prom007Resp.getNowTime();
                    if (StringUtils.isNotEmpty(ifStart)) {
                        mAdapter.setIfStart(ifStart);
                        if (StringUtils.equals("0", ifStart)) {//未开始
                            mKillText.setText("即将开始 先抢先得");
                            mRemainTextTv.setText("距开始:");
                            mTextDownTimerView.setTimes(startTime.getTime() - nowTime.getTime());
                            if (!mTextDownTimerView.isRun()) {
                                mTextDownTimerView.beginRun();
                            }
                        } else {//抢购中
                            mKillText.setText("抢购中 先抢先得");
                            mRemainTextTv.setText("距结束:");
                            mTextDownTimerView.setTimes(endTime.getTime() - nowTime.getTime());
                            if (!mTextDownTimerView.isRun()) {
                                mTextDownTimerView.beginRun();
                            }
                        }
                    }
                    if (startTime != null) {
                        String start = DateUtils.getTimeStampString(startTime);
                        mAdapter.setStartTime(start);
                    }
                    if (list.size() != 0) {
                        mAdapter.addAll(list);
                    } else {
                        ToastUtil.show(SecondKillActivity.this, R.string.toast_load_more_msg);
                    }
                    mRecyclerView.onRefreshComplete();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    @OnClick({R.id.iv_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }


}
