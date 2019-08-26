package com.ailk.pmph.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ai.ecp.app.resp.Cms010Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.ui.activity.GuessLikeActivity;
import com.ailk.pmph.ui.activity.ScanActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.ui.activity.SearchActivity;
import com.ailk.pmph.ui.activity.MessageActivity;
import com.ailk.pmph.ui.fragment.viewholder.HomeViewHolder;
import com.ailk.pmph.ui.fragment.viewholder.HomeViewPagerHolder;
import com.ailk.pmph.ui.view.RecyclerScrollerView;
import com.ailk.pmph.utils.ToastUtil;

import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/16.
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, HomeViewPagerHolder.OnScrollListener {

    @BindView(R.id.title_layout)
    LinearLayout titleLayout;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.home_recylerView)
    RecyclerScrollerView recyclerScrollerView;

    private Handler mHandler;
    private boolean isScrolling;
    private boolean taskAlive = true;
    private Set<HomeViewPagerHolder> viewPagers;
    private TimeTask timeTask;
    private Cms010Resp homeData;
    private boolean firstStart = true;
    private RecyclerAdapter adapter;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    public void initView(View view) {
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvSearch.setText(getActivity().getResources().getString(R.string.str_searc_books));
        } else {
            tvSearch.setText(getActivity().getResources().getString(R.string.str_searc_goods));
        }
        viewPagers = new HashSet<>();
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getActivity().getResources().getIntArray(R.array.swipe_colors));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerScrollerView.setLayoutManager(linearLayoutManager);
        if(homeData == null){
            initData();
            getData(true);
        }else{
            initData();
        }
        timeTask = new TimeTask();
        timeTask.execute();

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("home", true);
                launch(SearchActivity.class, bundle);
            }
        });

        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppContext.isLogin) {
                    launch(MessageActivity.class);
                } else {
                    showLoginDialog(getActivity());
                }
            }
        });

        if (!StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            recyclerScrollerView.setOnScrollChangedListener(scrollChangedListener);
        }
        recyclerScrollerView.setOnTouchListener(touchListener);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else
        {
            launch(ScanActivity.class);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launch(ScanActivity.class);
            } else {
                ToastUtil.showCenter(getActivity(), "permission denied");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void initData() {
        if (homeData == null) {
            homeData = new Cms010Resp();
        }
        if (adapter == null) {
            adapter = new RecyclerAdapter(getActivity());
        }
        recyclerScrollerView.setAdapter(adapter);
        swipeLayout.setRefreshing(false);
    }

    private RecyclerScrollerView.OnScrollChangedListener scrollChangedListener = new RecyclerScrollerView.OnScrollChangedListener() {
        @Override
        public void onScrollChanged(ScrollView who, int x, int y, int oldx, int oldy) {
            int fadingHeight = 255;
            if (y <= titleLayout.getHeight())
            {
                titleLayout.setBackgroundColor(Color.TRANSPARENT);
                searchLayout.setBackgroundResource(R.drawable.shape_round_white_dp3);
                ivSearch.setImageResource(R.drawable.search_white);
                ivSearch.setImageAlpha(255);
                tvSearch.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                ivScan.setImageResource(R.drawable.icon_scan_white);
                ivScan.setImageAlpha(255);
                ivMessage.setImageResource(R.drawable.message);
                ivMessage.setImageAlpha(255);
            }
            else if (y > titleLayout.getHeight() && y < fadingHeight)
            {
                float scale = (float) y / fadingHeight;
                float alpha = 255 * scale;
                titleLayout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                searchLayout.setBackgroundResource(R.drawable.shape_round_dp3);
                searchLayout.setBackgroundColor(Color.argb((int) alpha, 235, 235, 235));
                ivSearch.setImageResource(R.drawable.search);
                ivSearch.setImageAlpha((int) alpha);
                tvSearch.setTextColor(Color.argb((int) alpha, 150, 150, 150));
                ivScan.setImageResource(R.drawable.icon_scan);
                ivScan.setImageAlpha((int) alpha);
                ivMessage.setImageResource(R.drawable.message_black);
                ivMessage.setImageAlpha((int) alpha);
            }
            else
            {
                titleLayout.setBackgroundColor(Color.WHITE);
                searchLayout.setBackgroundResource(R.drawable.shape_round_dp3);
                ivSearch.setImageResource(R.drawable.search);
                tvSearch.setTextColor(Color.WHITE);
                ivScan.setImageResource(R.drawable.icon_scan);
                ivMessage.setImageResource(R.drawable.message_black);
            }
        }
    };

    private RecyclerScrollerView.OnTouchListener touchListener = new RecyclerScrollerView.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
                case MotionEvent.ACTION_MOVE:{
                    break;
                }
                case MotionEvent.ACTION_DOWN:{
                    break;
                }
                case MotionEvent.ACTION_UP:{
                    if(recyclerScrollerView.getChildAt(0).getMeasuredHeight()<=recyclerScrollerView.getScrollY()+recyclerScrollerView.getHeight()){
                        if (homeData != null) {
                            if (homeData.getDatas() != null && homeData.getDatas().size() != 0) {
                                int size = homeData.getDatas().size() - 1;
                                int type = homeData.getDatas().get(size).getType();
                                if (type == 410) {
                                    launch(GuessLikeActivity.class);
                                }
                            }
                        }
                    }
                    break;
                }
            }
            return false;
        }
    };

    public void getData(boolean isShowProgress) {
        getJsonService().requestCms010(getActivity(), null, isShowProgress, new JsonService.CallBack<Cms010Resp>() {
            @Override
            public void oncallback(Cms010Resp cms010Resp) {
                swipeLayout.setRefreshing(false);
                homeData = cms010Resp;
                initData();
            }

            @Override
            public void onErro(AppHeader header) {
                swipeLayout.setRefreshing(false);
            }
        });

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean isScrolling() {
        return isScrolling;
    }
    public void initViewPagerScroll() {
        if (viewPagers != null) {
            for (HomeViewPagerHolder pagerHolder : viewPagers) {
                pagerHolder.setOnScrollListener(this);
            }
        }
    }

    @Override
    public void onRefresh() {
        getData(false);
    }

    public void refreshData(){
        recyclerScrollerView.willRefresh();
    }

    @Override
    public void onScrolling(boolean isScrolling) {
        this.isScrolling = isScrolling;
        swipeLayout.setEnabled(!isScrolling);
    }
    private void showNextpage() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        if (swipeLayout.isRefreshing()) {
            return;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewPagers != null) {
                    for (HomeViewPagerHolder pagerHolder : viewPagers) {
                        pagerHolder.showNextPager();
                    }
                }
            }
        }, 300);

    }

    @Override
    public void onStop() {
        super.onStop();
        setSuspend(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timeTask!=null) {
            taskAlive = false;
            timeTask.cancel(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!firstStart) {
            showNextpage();
            setSuspend(false);
        } else {
            firstStart = false;
        }
    }

    public void setSuspend(boolean b) {
        if(timeTask!=null) {
            timeTask.setSuspend(b);
        }
    }

    class TimeTask extends AsyncTask<Void, Void, Void> {

        private boolean suspend = false;

        private String control = ""; // 只是需要一个对象而已，这个对象没有实际意义

        public boolean isSuspend() {
            return this.suspend;
        }

        public void setSuspend(boolean suspend) {
            LogUtil.e("suspend = " + this.suspend + " --> params = " + suspend);
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
                            LogUtil.e(e);
                        }
                    }
                }
                try {
                    Thread.sleep(3000);
                    publishProgress();
                } catch (InterruptedException e) {
                    LogUtil.e(e);
                }

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... params) {
            if (isScrolling()) {
                return;
            }
            showNextpage();
        }

    }
    private class RecyclerAdapter extends RecyclerView.Adapter<HomeViewHolder> {

        private LayoutInflater mInflater;

        public RecyclerAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getItemCount() {
            return (homeData == null || homeData.getDatas() == null) ? 0 :homeData.getDatas().size();
        }

        @Override
        public int getItemViewType(int position) {
            return homeData.getDatas().get(position).getType();
        }

        @Override
        public void onViewRecycled(HomeViewHolder holder) {
            if (holder instanceof HomeViewPagerHolder) {
                viewPagers.remove(holder);
            }
            super.onViewRecycled(holder);
        }

        @Override
        public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return HomeViewHolder.getHolder(getActivity(), mInflater, parent, viewType);
        }

        @Override
        public void onBindViewHolder(HomeViewHolder holder, int position) {
            holder.initData(homeData.getDatas().get(position));
            if (holder instanceof HomeViewPagerHolder) {
                viewPagers.add((HomeViewPagerHolder) holder);
                initViewPagerScroll();
            }
        }
    }
}
