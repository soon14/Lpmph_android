package com.ailk.integral.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ai.ecp.app.req.Cms101Req;
import com.ai.ecp.app.resp.Cms101Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.integral.activity.InteSearchActivity;
import com.ailk.integral.holder.InteHomeViewHolder;
import com.ailk.integral.holder.InteHomeViewPagerHolder;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.ui.activity.MainActivity;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.ui.view.InteRecyclerScrollerView;

import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.frag
 * 作者: Chrizz
 * 时间: 2016/5/11 15:17
 */
public class InteHomeFragment extends BaseFragment implements View.OnClickListener, InteHomeViewPagerHolder.OnScrollListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.home_recylerView)
    InteRecyclerScrollerView recyclerScrollerView;

    private Handler mHandler;
    private boolean isScrolling;
    private boolean taskAlive = true;
    private Set<InteHomeViewPagerHolder> viewPagers;
    private TimeTask timeTask;
    private Cms101Resp homeData;
    private boolean firstStart = true;
    private RecyclerAdapter adapter;
    public static final String REFRESH = "refresh";

    @Override
    public int getLayoutResId() {
        return R.layout.inte_fragment_home;
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equals(REFRESH, intent.getAction())) {
                getData(true);
            }
        }
    };

    public void initView(View view) {
        viewPagers = new HashSet<>();
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(false);
                swipeLayout.setRefreshing(false);
            }
        });
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

        IntentFilter filter = new IntentFilter();
        filter.addAction(REFRESH);
        getActivity().registerReceiver(receiver, filter);
    }

    public void getData(boolean isShowProgress) {
        Cms101Req req = new Cms101Req();
        getInteJsonService().requestCms101(getActivity(), req, isShowProgress, new InteJsonService.CallBack<Cms101Resp>() {
            @Override
            public void oncallback(Cms101Resp cms101Resp) {
                swipeLayout.setRefreshing(false);
                homeData = cms101Resp;
                initData();
            }

            @Override
            public void onErro(AppHeader header) {
                swipeLayout.setRefreshing(false);
            }
        });

    }

    public void initData() {
        if (homeData == null) {
            homeData = new Cms101Resp();
        }
        if (adapter == null) {
            adapter = new RecyclerAdapter(getActivity());
        }
        recyclerScrollerView.setAdapter(adapter);
        swipeLayout.setRefreshing(false);
    }

    public static InteHomeFragment newInstance() {
        InteHomeFragment fragment = new InteHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean isScrolling() {
        return isScrolling;
    }

    public void initViewPagerScroll() {
        if (viewPagers != null) {
            for (InteHomeViewPagerHolder pagerHolder : viewPagers) {
                pagerHolder.setOnScrollListener(this);
            }
        }
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
                    for (InteHomeViewPagerHolder pagerHolder : viewPagers) {
                        pagerHolder.showNextPager();
                    }
                }
            }
        }, 300);

    }

    @Override
    @OnClick({R.id.iv_back,R.id.iv_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                launch(MainActivity.class);
                getActivity().finish();
                break;
            case R.id.iv_search:
                launch(InteSearchActivity.class);
                break;
        }
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
    private class RecyclerAdapter extends RecyclerView.Adapter<InteHomeViewHolder> {

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
        public void onViewRecycled(InteHomeViewHolder holder) {
            if (holder instanceof InteHomeViewPagerHolder) {
                viewPagers.remove((InteHomeViewPagerHolder) holder);
            }
            super.onViewRecycled(holder);
        }


        @Override
        public InteHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return InteHomeViewHolder.getHolder(getActivity(), mInflater, parent, viewType);
        }

        @Override
        public void onBindViewHolder(InteHomeViewHolder holder, int position) {
            LogUtil.e(homeData.getDatas().size());
            holder.initData(homeData.getDatas().get(position));
            if (holder instanceof InteHomeViewPagerHolder) {
                viewPagers.add((InteHomeViewPagerHolder) holder);
                initViewPagerScroll();
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(receiver);
    }

}
