package com.ailk.pmph.thirdstore.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ai.ecp.app.req.Cms001Req;
import com.ai.ecp.app.req.Gds006Req;
import com.ai.ecp.app.req.Gds014Req;
import com.ai.ecp.app.req.Gds023Req;
import com.ai.ecp.app.req.Gds024Req;
import com.ai.ecp.app.req.Prom004Req;
import com.ai.ecp.app.req.Prom005Req;
import com.ai.ecp.app.req.Staff114Req;
import com.ai.ecp.app.req.Staff115Req;
import com.ai.ecp.app.req.Staff116Req;
import com.ai.ecp.app.req.gds.SearchPropReqInfo;
import com.ai.ecp.app.resp.Cms001Resp;
import com.ai.ecp.app.resp.Gds006Resp;
import com.ai.ecp.app.resp.Gds014Resp;
import com.ai.ecp.app.resp.Gds023Resp;
import com.ai.ecp.app.resp.Gds024Resp;
import com.ai.ecp.app.resp.Prom004Resp;
import com.ai.ecp.app.resp.Prom005Resp;
import com.ai.ecp.app.resp.PromSkuRespVO;
import com.ai.ecp.app.resp.Staff114Resp;
import com.ai.ecp.app.resp.Staff115Resp;
import com.ai.ecp.app.resp.Staff116Resp;
import com.ai.ecp.app.resp.gds.GdsPropBaseInfo;
import com.ai.ecp.app.resp.gds.GdsPropValueBaseInfo;
import com.ai.ecp.app.resp.gds.GoodSearchResultVO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.StringUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.activity.LoginActivity;
import com.ailk.pmph.ui.activity.ShopDetailActivity;
import com.ailk.pmph.ui.adapter.SearchByPropsAdapter;
import com.ailk.pmph.thirdstore.adapter.StoreRecyclerAdapter;
import com.ailk.pmph.thirdstore.adapter.StoreRecyclerAllAdapter;
import com.ailk.pmph.thirdstore.adapter.StoreRecyclerSalesAdapter;
import com.ailk.pmph.ui.fragment.StoreCollectFragment;
import com.ailk.tool.GlideUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StoreActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rab_tab)
    RadioGroup rabTab;
    @BindView(R.id.line_layout)
    LinearLayout lineLayout;
    @BindView(R.id.store_img)
    ImageView storeImg;
    @BindView(R.id.store_name_tv)
    TextView storeNameTv;
    @BindView(R.id.collect_store_tv)
    TextView storeCollectTv;

    @BindView(R.id.sort_style)
    ImageView sortStyle;
    private View mSort1View;
    private View mSort2View;
    private View mSort3View;
    private View mFilterView;

    @BindView(R.id.sort_layout)
    LinearLayout sortLayout;
    @BindView(R.id.sales_layout)
    LinearLayout salesLayout;
    @BindView(R.id.store_no_content)
    ImageView storeNoContent;


    @BindView(R.id.recyclerViewId)
    PullToRefreshRecyclerView recyclerViewGds023;
    @BindView(R.id.recyclerViewId2)
    PullToRefreshRecyclerView salesRecyclerView;
    @BindView(R.id.recyclerViewId3)
    PullToRefreshRecyclerView allNewRecyclerView;

    private StoreRecyclerAdapter gds023Adapter;
    private StoreRecyclerSalesAdapter salesAdapter;
    private StoreRecyclerAllAdapter allAdapter;
    private GridLayoutManager gridLayoutManager;

    //店铺首页
    private Gds023Req gds023Req;
    private int pageNumberGds023 = 1;
    private boolean isExistGds023 = true; //是否有数据

    //店铺促销
    private Prom004Req prom004Req;
    private Prom005Req prom005Req;
    private int pageNumberSales = 1;
    private boolean isExistSales = false; //是否加载过
    private String promTypeCode = null;//查询全部时 为空，否则需要传入值---促销类型编码
    private int index = 0;//点击促销类型的第几个

    //上架新品
    private Gds024Req gds024Req;
    private int pageNumberAN = 1;
    private boolean isGridOrList = true; //true表示当前为grid布局

    //排序
    private static final String SORT_FIELD_SALE = "sales";
    private static final String SORT_FIELD_PRICE = "discountPrice";
    private static final String ASC = "asc";//顺序
    private static final String DESC = "desc";//逆序
    private boolean isPriceUp = false;

    //排序--筛选
    private List<SearchPropReqInfo> searchProps;
    private int searchPosition = -1;//初始化为-1默认为不选择
    private int searchPosition2 = -1;//初始化为-1默认为不选择

    private int noStoreContent = 0;//0标识无商品,1为促销页面无商品
    private String storeId = null;//店铺id
    private Gds006Req gds006Req;//排序

    public static final String REFRESH_STORE = "REFRESH_STORE";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_store;
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(REFRESH_STORE)) {
                requestStaff116();
            }
        }
    };

    @Override
    public void initView() {

    }

    public void initData() {
        rabTab.setOnCheckedChangeListener(this);
        ((RadioButton) rabTab.getChildAt(0)).setChecked(true);

        storeId = getIntent().getStringExtra(Constant.STORE_ID);

        gds023Req = new Gds023Req();
        gds024Req = new Gds024Req();
        prom004Req = new Prom004Req();
        prom005Req = new Prom005Req();
        gds006Req = new Gds006Req();
        gds023Adapter = new StoreRecyclerAdapter(StoreActivity.this, new ArrayList<GoodSearchResultVO>());
        salesAdapter = new StoreRecyclerSalesAdapter(StoreActivity.this, new ArrayList<PromSkuRespVO>());
        allAdapter = new StoreRecyclerAllAdapter(StoreActivity.this, new ArrayList<GoodSearchResultVO>());

        //Grid布局
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewGds023.setLayoutManager(gridLayoutManager);//这里用线性宫格显示 类似于grid view
        recyclerViewGds023.setAdapter(gds023Adapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (gds023Adapter.isHeaderView(position) || gds023Adapter.isBottomView(position)) ? gridLayoutManager.getSpanCount() : 1;
            }
        });

        final GridLayoutManager gManager = new GridLayoutManager(this, 2);
        salesRecyclerView.setLayoutManager(gManager);//这里用线性宫格显示 类似于grid view
        salesRecyclerView.setAdapter(salesAdapter);
        gManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (salesAdapter.isHeaderView(position) || salesAdapter.isBottomView(position)) ? gManager.getSpanCount() : 1;
            }
        });

        requestCms001();//店铺首页广告轮播
        requestStaff116();
        requestGds023(true);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REFRESH_STORE);
        registerReceiver(receiver, intentFilter);
    }

    private void requestCms001() {
        Cms001Req cms001Req = new Cms001Req();
        cms001Req.setPlaceId(1501L);//目前固定
        cms001Req.setShopId(Long.parseLong(storeId));
        getJsonService().requestCms001(StoreActivity.this, cms001Req, false, new JsonService.CallBack<Cms001Resp>() {
            @Override
            public void oncallback(Cms001Resp cms001Resp) {
                gds023Adapter.setAdvertiseRespVO(cms001Resp.getAdvertiseList());
            }
            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    //查询店铺信息
    private void requestStaff116() {
        Staff116Req staff116Req = new Staff116Req();
        staff116Req.setShopId(Long.parseLong(storeId));
        getJsonService().requestStaff116(StoreActivity.this, staff116Req, true, new JsonService.CallBack<Staff116Resp>() {
            @Override
            public void oncallback(Staff116Resp staff116Resp) {
                if (null != staff116Resp) {
                    storeNameTv.setText(staff116Resp.getShopName());
                    if (StringUtil.isNotEmpty(staff116Resp.getLogoPathURL())) {
                        GlideUtil.loadImg(StoreActivity.this,staff116Resp.getLogoPathURL(),storeImg);
                    } else {
                        storeImg.setBackgroundResource(R.drawable.default_img);
                    }
                    if (AppContext.isLogin) {
                        if ("1".equals(staff116Resp.getCollectFlag())) {
                            collectStatus("已关注");
                        } else {
                            collectStatus("关注");
                        }
                    } else {
                        collectStatus("关注");
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void collectStatus(String status) {
        storeCollectTv.setText(status);
        if ("关注".equals(status)) {
            storeCollectTv.setTextColor(ContextCompat.getColor(this, R.color.white));
            storeCollectTv.setBackground(ContextCompat.getDrawable(this, R.drawable.tv_store_collect_bg));
            Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.store_collect_w);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                storeCollectTv.setCompoundDrawables(drawable, null, null, null);
            }
        }else if ("已关注".equals(status)) {
            storeCollectTv.setTextColor(ContextCompat.getColor(this, R.color.red));
            storeCollectTv.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_round_hot_word_textview));
            Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.store_collect_r);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                storeCollectTv.setCompoundDrawables(drawable, null, null, null);
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < rabTab.getChildCount(); i++) {
            if (rabTab.getChildAt(i).getId() == checkedId) {
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    switch (i) {
                        case 0:
                            //店铺首页
                            initGds023();
                            break;
                        case 1:
                            //全部商品
                            newRequest(true);
                            break;
                        case 2:
                            //上架新品
                            newRequest(false);
                            break;
                    }
                    showLine(i);
                }
                else {
                    switch (i) {
                        case 0:
                            //店铺首页
                            initGds023();
                            break;
                        case 1:
                            //全部商品
                            newRequest(true);
                            break;
                        case 2:
                            //促销商品
                            salesRequest();
                            break;
                        case 3:
                            newRequest(false);
                            break;
                    }
                    showLine(i);
                }
            }
        }
    }

    //店铺首页内容---重磅推荐
    private void initGds023() {
        if (isExistGds023) {
            recyclerViewGds023.setVisibility(View.VISIBLE);
            storeNoContent.setVisibility(View.GONE);
        } else {
            recyclerViewGds023.setVisibility(View.GONE);
            storeNoContent.setVisibility(View.VISIBLE);
        }
        salesRecyclerView.setVisibility(View.GONE);
        salesLayout.setVisibility(View.GONE);
        allNewRecyclerView.setVisibility(View.GONE);
        sortLayout.setVisibility(View.GONE);
        recyclerViewGds023.setMode(PullToRefreshBase.Mode.BOTH);
        recyclerViewGds023.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(final PullToRefreshBase<RecyclerView> refreshView) {
                requestCms001();//重新加载广播
                pageNumberGds023 = 1;
                gds023Adapter.clear();
                requestGds023(false);
                refreshView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(final PullToRefreshBase<RecyclerView> refreshView) {
                requestGds023(false);
                refreshView.onRefreshComplete();

            }
        });
    }

    //重磅推荐
    private void requestGds023(boolean flag) {
        if (View.VISIBLE != storeNoContent.getVisibility()) {
            recyclerViewGds023.setVisibility(View.VISIBLE);
            gds023Req.setId(storeId);
            gds023Req.setPageNumber(pageNumberGds023++);
            gds023Req.setPageSize(Constant.PAGE_SIZE);
            getJsonService().requestGds023(this, gds023Req, flag, new JsonService.CallBack<Gds023Resp>() {
                @Override
                public void oncallback(Gds023Resp gds023Resp) {
                    if (null != gds023Resp && 0 != gds023Resp.getPageRespVO().size()) {
                        recyclerViewGds023.setVisibility(View.VISIBLE);
                        storeNoContent.setVisibility(View.GONE);
                        gds023Adapter.addAll(gds023Resp.getPageRespVO());
                        isExistGds023 = true;
                    } else {
                        if (2 == pageNumberGds023) {
                            storeNoContent.setVisibility(View.VISIBLE);
                            noStoreContent = 0;
                            recyclerViewGds023.setVisibility(View.GONE);
                            isExistGds023 = false;
                        } else {
                            ToastUtil.show(StoreActivity.this, R.string.toast_load_more_msg);
                            isExistGds023 = true;
                        }
                    }
                    recyclerViewGds023.onRefreshComplete();
                }

                @Override
                public void onErro(AppHeader header) {
                    recyclerViewGds023.onRefreshComplete();
                }
            });
        } else {
            recyclerViewGds023.setVisibility(View.GONE);
        }

        gds023Adapter.setOnItemClickListener(new StoreRecyclerAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GoodSearchResultVO goodSearchResultVO = gds023Adapter.getItem(position - 1);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(goodSearchResultVO.getId()));
                bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(goodSearchResultVO.getFirstSkuId()));
                launch(ShopDetailActivity.class, bundle);
            }
        });

    }

    //促销商品
    private void salesRequest() {
        //先判断有无商品
        if (View.VISIBLE == storeNoContent.getVisibility() && 0 == noStoreContent) {
            return;
        }else if (1 == noStoreContent){
            storeNoContent.setVisibility(View.VISIBLE);
            salesRecyclerView.setVisibility(View.GONE);
            salesLayout.setVisibility(View.GONE);
            recyclerViewGds023.setVisibility(View.GONE);
            allNewRecyclerView.setVisibility(View.GONE);
            sortLayout.setVisibility(View.GONE);
            return;
        }

        salesRecyclerView.setVisibility(View.VISIBLE);
        salesLayout.setVisibility(View.VISIBLE);
        recyclerViewGds023.setVisibility(View.GONE);
        storeNoContent.setVisibility(View.GONE);
        allNewRecyclerView.setVisibility(View.GONE);
        sortLayout.setVisibility(View.GONE);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            ((TextView) salesLayout.getChildAt(index)).setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            ((TextView) salesLayout.getChildAt(index)).setTextColor(ContextCompat.getColor(this, R.color.red));
        }

        salesLayout.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < salesLayout.getChildCount(); i++) {
                    ((TextView) salesLayout.getChildAt(i)).setTextColor(ContextCompat.getColor(StoreActivity.this, R.color.black));
                }
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    ((TextView) salesLayout.getChildAt(0)).setTextColor(ContextCompat.getColor(StoreActivity.this, R.color.orange_ff6a00));
                } else {
                    ((TextView) salesLayout.getChildAt(0)).setTextColor(ContextCompat.getColor(StoreActivity.this, R.color.red));
                }
                salesAdapter.clear();
                requestProm005(1, 0, null, true);
                index = 0;
            }
        });

        if (!isExistSales) {
            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                ((TextView) salesLayout.getChildAt(0)).setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
            } else {
                ((TextView) salesLayout.getChildAt(0)).setTextColor(ContextCompat.getColor(this, R.color.red));
            }

            prom004Req.setShopId(Long.parseLong(storeId));
            getJsonService().requestProm004(StoreActivity.this, prom004Req, false, new JsonService.CallBack<Prom004Resp>() {
                @Override
                public void oncallback(final Prom004Resp prom004Resp) {
                    isExistSales = true;
                    if (null != prom004Resp && 0 != prom004Resp.getResList().size()) {
                        for (int i = 0; i < prom004Resp.getResList().size(); i++) {
                            final TextView textView = new TextView(StoreActivity.this);
                            textView.setTextSize(14);
                            textView.setGravity(Gravity.CENTER);
                            textView.setTextColor(ContextCompat.getColor(StoreActivity.this, R.color.black));
                            textView.setPadding(15, 15, 15, 15);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            textView.setText(prom004Resp.getResList().get(i).getPromTypeName());
                            salesLayout.addView(textView, params);

                            final int j = i;
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for (int i = 0; i < salesLayout.getChildCount(); i++) {
                                        ((TextView) salesLayout.getChildAt(i)).setTextColor(ContextCompat.getColor(StoreActivity.this, R.color.black));
                                    }
                                    if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                                        textView.setTextColor(ContextCompat.getColor(StoreActivity.this, R.color.orange_ff6a00));
                                    } else {
                                        textView.setTextColor(ContextCompat.getColor(StoreActivity.this, R.color.red));
                                    }
                                    index = j + 1;//标记点击了哪个(因为前面还有“全部”)
                                    promTypeCode = prom004Resp.getResList().get(j).getPromTypeCode();
                                    salesAdapter.clear();
                                    requestProm005(1, index, promTypeCode, true);
                                }
                            });
                        }

                        //默认加载全部类型
                        requestProm005(1, 0, null, true);
                    }else {
                        noStoreContent = 1;
                        storeNoContent.setVisibility(View.VISIBLE);
                        salesRecyclerView.setVisibility(View.GONE);
                        salesLayout.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onErro(AppHeader header) {

                }
            });
        }

        loadMoreProm005();//上拉刷新和加载更多

        salesAdapter.setOnItemClickListener(new StoreRecyclerSalesAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PromSkuRespVO promSkuRespVO = salesAdapter.getItem(position - 1);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(promSkuRespVO.getGdsId()));
                bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(promSkuRespVO.getSkuId()));
                launch(ShopDetailActivity.class, bundle);
            }
        });
    }

    private void loadMoreProm005() {
        salesRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        salesRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNumberSales = 1;
                salesAdapter.clear();
                requestProm005(pageNumberSales, index, promTypeCode, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                requestProm005(++pageNumberSales, index, promTypeCode, false);
            }
        });
    }

    //店铺促销单品列表
    private void requestProm005(final int pageNo, final int i, final String typeCode, boolean flag) {
        prom005Req.setShopId(Long.parseLong(storeId));
        prom005Req.setPromTypeCode(typeCode);
        prom005Req.setPageNo(pageNo);
        prom005Req.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestProm005(StoreActivity.this, prom005Req, flag, new JsonService.CallBack<Prom005Resp>() {
            @Override
            public void oncallback(Prom005Resp prom005Resp) {
                salesRecyclerView.onRefreshComplete();
                if (null != prom005Resp && 0 != prom005Resp.getResList().size()) {
                    salesAdapter.addAll(prom005Resp.getResList());
                } else {
                    ToastUtil.show(StoreActivity.this, R.string.toast_load_more_msg);
                }
                index = i;
                promTypeCode = typeCode;
            }

            @Override
            public void onErro(AppHeader header) {
                salesRecyclerView.onRefreshComplete();
            }
        });
    }

    //全部商品和上架新品（共用）
    //flag为true时---为加载全部商品的内容，false为上架新品
    private void newRequest(boolean flag) {
        //先判断有无商品
        if (View.VISIBLE == storeNoContent.getVisibility() && 0 == noStoreContent) {
            return;
        }

        allAdapter.clear();
        allAdapter.updateStyle(true);//默认为Grid布局
        final GridLayoutManager gManager = new GridLayoutManager(this, 2);
        allNewRecyclerView.setLayoutManager(gManager);//这里用线性宫格显示 类似于grid view
        allNewRecyclerView.setAdapter(allAdapter);
        gManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (allAdapter.isHeaderView(position) || allAdapter.isBottomView(position)) ? gManager.getSpanCount() : 1;
            }
        });
        isGridOrList = true;

        allNewRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        allNewRecyclerView.setVisibility(View.VISIBLE);
        sortLayout.setVisibility(View.VISIBLE);
        salesRecyclerView.setVisibility(View.GONE);
        salesLayout.setVisibility(View.GONE);
        recyclerViewGds023.setVisibility(View.GONE);
        storeNoContent.setVisibility(View.GONE);

        initSortView(flag);
        if (!flag) {//上架新品默认加载后台返回的数据
            pageNumberAN = 1;
            requestGds024(pageNumberAN, true);

            loadMoreGds024();
        }

        allAdapter.setOnItemClickListener(new StoreRecyclerAllAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GoodSearchResultVO goodSearchResultVO = allAdapter.getItem(position - 1);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(goodSearchResultVO.getId()));
                bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(goodSearchResultVO.getFirstSkuId()));
                launch(ShopDetailActivity.class, bundle);
            }
        });
    }

    private void requestGds024(int pageNumber, boolean flag) {
        gds024Req.setId(storeId);
        gds024Req.setPageNumber(pageNumber);
        gds024Req.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestGds024(StoreActivity.this, gds024Req, flag, new JsonService.CallBack<Gds024Resp>() {
            @Override
            public void oncallback(Gds024Resp gds024Resp) {
                if (null != gds024Resp && 0 != gds024Resp.getPageRespVO().size()) {
                    allAdapter.addAll(gds024Resp.getPageRespVO());
                } else {
                    ToastUtil.show(StoreActivity.this, R.string.toast_load_more_msg);
                }
                allNewRecyclerView.onRefreshComplete();
            }

            @Override
            public void onErro(AppHeader header) {
                allNewRecyclerView.onRefreshComplete();
            }
        });
    }

    private void loadMoreGds024() {
        allNewRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNumberAN = 1;
                allAdapter.clear();
                requestGds024(pageNumberAN, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                requestGds024(++pageNumberAN, false);
            }
        });
    }

    private void initSortView(boolean flag) {
        if (searchProps==null) {
            searchProps = new ArrayList<>();
        }
        //当切换有排序时清除已选排序的条件
        searchPosition = -1;
        searchProps.clear();
        mSort1View = findViewById(R.id.sort1);
        mSort2View = findViewById(R.id.sort2);
        mSort3View = findViewById(R.id.sort3);
        mFilterView = findViewById(R.id.filter);
        mSort1View.setOnClickListener(this);
        mSort2View.setOnClickListener(this);
        mSort3View.setOnClickListener(this);
        mFilterView.setOnClickListener(this);


        TextView tv1 = (TextView) mSort1View.findViewById(R.id.text);
        tv1.setText("综合排序");
        TextView tv3 = (TextView) mSort3View.findViewById(R.id.text);
        tv3.setText("销量");
        TextView tv2 = (TextView) mSort2View.findViewById(R.id.text);
        tv2.setText("价格");
        TextView tv4 = (TextView) mFilterView.findViewById(R.id.text);
        tv4.setText("筛选");
        ImageView icon1 = (ImageView) mSort1View.findViewById(R.id.icon);
        icon1.setVisibility(View.GONE);

        if (flag) {
            sortDefault();
        } else {
            isPriceUp = false;
            mSort2View.setSelected(false);
            mSort3View.setSelected(false);
            mFilterView.setSelected(false);
            mSort1View.setSelected(false);
            sortStyle.setSelected(false);
        }
    }

    @OnClick({R.id.iv_back, R.id.classify_img_btn, R.id.collect_store_tv, R.id.sort1, R.id.sort2,
            R.id.sort3, R.id.filter, R.id.sort_style, R.id.tv_search})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            //搜索店内商品
            case R.id.tv_search:
                Intent intent = new Intent(StoreActivity.this, SearchInStoreActivity.class);
                intent.putExtra(Constant.STORE_ID, storeId);
                launch(intent);
                break;

            //分类
            case R.id.classify_img_btn:
                classify();
                break;

            //店铺收藏
            case R.id.collect_store_tv:
                collectStore();
                break;

            case R.id.sort1:
                //综合排序
                sortDefault();
                break;

            case R.id.sort2:
                //价格
                sortByPrice();
                break;

            case R.id.sort3:
                //销量
                sortByCounts();
                break;

            case R.id.filter:
                //筛选条件
                sortByProps(v);
                break;

            case R.id.sort_style:
                if (isGridOrList) {
                    allAdapter.updateStyle(false);
                    //List布局
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                    allNewRecyclerView.setLayoutManager(layoutManager);
                    allNewRecyclerView.setAdapter(allAdapter);
                } else {
                    allAdapter.updateStyle(true);
                    final GridLayoutManager gManager = new GridLayoutManager(this, 2);
                    allNewRecyclerView.setLayoutManager(gManager);//这里用线性宫格显示 类似于grid view
                    allNewRecyclerView.setAdapter(allAdapter);
                    gManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return (allAdapter.isHeaderView(position) || allAdapter.isBottomView(position)) ? gManager.getSpanCount() : 1;
                        }
                    });
                }
                sortStyle.setSelected(!sortStyle.isSelected());
                isGridOrList = !isGridOrList;
                break;
        }

    }

    //按价格排序
    private void sortByPrice() {
        ImageView img = (ImageView) mSort2View.findViewById(R.id.icon);
        if (mSort2View.isSelected()) {
            if (!isPriceUp) {
                img.setImageResource(R.drawable.icon_triangle_up);
                isPriceUp = true;
                sortByPriceAsc();
            } else {
                img.setImageResource(R.drawable.icon_triangle_down);
                isPriceUp = false;
                sortByPriceDesc();
            }
        } else {
            isPriceUp = false;
            img.setImageResource(R.drawable.icon_triangle_down);
            sortByPriceDesc();
            mSort1View.setSelected(false);
            mSort3View.setSelected(false);
            mFilterView.setSelected(false);
            sortStyle.setSelected(false);
        }
        mSort2View.setSelected(true);
    }

    //按价格从高到低
    private void sortByPriceAsc() {
        allAdapter.clear();
        sort(1, SORT_FIELD_PRICE, ASC, true);
        loadMoreGds006(SORT_FIELD_PRICE, ASC);
    }

    //按价格从低到高
    private void sortByPriceDesc() {
        allAdapter.clear();
        sort(1, SORT_FIELD_PRICE, DESC, true);
        loadMoreGds006(SORT_FIELD_PRICE, DESC);
    }

    //根据销量排序 只提供逆序
    private void sortByCounts() {
        ImageView img = (ImageView) mSort3View.findViewById(R.id.icon);
        if (mSort3View.isSelected()) {
            if (isPriceUp) {
                img.setImageResource(R.drawable.icon_triangle_down);
                isPriceUp = false;
                allAdapter.clear();
                sort(1, SORT_FIELD_SALE, DESC, true);
                loadMoreGds006(SORT_FIELD_SALE, DESC);
            } else {
                img.setImageResource(R.drawable.icon_triangle_up);
                isPriceUp = true;
                allAdapter.clear();
                sort(1, SORT_FIELD_SALE, ASC, true);
                loadMoreGds006(SORT_FIELD_SALE, ASC);
            }
        } else {
            isPriceUp = false;
            mSort1View.setSelected(false);
            mSort2View.setSelected(false);
            mFilterView.setSelected(false);
            img.setImageResource(R.drawable.icon_triangle_down);
            allAdapter.clear();
            sort(1, SORT_FIELD_SALE, DESC, true);
            loadMoreGds006(SORT_FIELD_SALE, DESC);
        }
        mSort3View.setSelected(true);
    }

    //综合排序
    private void sortDefault() {
        isPriceUp = false;
        mSort2View.setSelected(false);
        mSort3View.setSelected(false);
        mFilterView.setSelected(false);
        sortStyle.setSelected(false);
        allAdapter.clear();
        sort(1, "", "", true);
        loadMoreGds006("", "");
        mSort1View.setSelected(true);
    }

    //筛选
    private void sortByProps(View v) {
        mSort2View.setSelected(false);
        mSort3View.setSelected(false);
        mFilterView.setSelected(true);
        mSort1View.setSelected(false);
        sortStyle.setSelected(false);
        initPopUpWindow(v);
    }

    // 用于筛选条件进行搜索的弹窗
    private void initPopUpWindow(View v) {
        Gds014Req req = new Gds014Req();
        req.setCatgCode("");
        View searchByPropsView = LayoutInflater.from(StoreActivity.this).inflate(R.layout.popupwindow_searchbyprops, null, false);
        ListView propList = (ListView) searchByPropsView.findViewById(R.id.searchbyprops_listview);
        Button confirmSearch = (Button) searchByPropsView.findViewById(R.id.searchbyprops_confirm);
        Button resetButton = (Button) searchByPropsView.findViewById(R.id.searchbyprops_reset);
        propList.setDivider(null);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            confirmSearch.setBackgroundColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            confirmSearch.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        }
        final SearchByPropsAdapter adapter = new SearchByPropsAdapter(StoreActivity.this, searchPosition, R.layout.item_choosegood_vertical);
        propList.setAdapter(adapter);
        getJsonService().requestGds014(StoreActivity.this, req, false, new JsonService.CallBack<Gds014Resp>() {
            @Override
            public void oncallback(Gds014Resp gds014Resp) {
                if ((null == gds014Resp) || (null == gds014Resp.getPropList()) || (0 == gds014Resp.getPropList().size())) {
                    ToastUtil.show(StoreActivity.this, "获取筛选条件失败，请稍后重试");
                    return;
                }
                for (int i = 0; i < gds014Resp.getPropList().size(); i++) {
                    GdsPropBaseInfo info = gds014Resp.getPropList().get(i);
                    adapter.add(info);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
        final PopupWindow popupWindow = new PopupWindow(searchByPropsView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        setBackgroundAlpha(0.5f);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.transparent)));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
               setBackgroundAlpha(1.0f);
            }
        });

        adapter.setListener(new SearchByPropsAdapter.GetPropValueListener() {
            @Override
            public void onChildClick(int position, GdsPropValueBaseInfo propInfo) {
                searchPosition2 = position;
                searchProps.clear();
                SearchPropReqInfo searchPropReqInfo = new SearchPropReqInfo();
                List<String> propValues = new ArrayList<>();
                propValues.clear();
                propValues.add(String.valueOf(propInfo.getId()));
                searchPropReqInfo.setPropertyValues(propValues);
                searchPropReqInfo.setPropertyId(String.valueOf(propInfo.getPropId()));
                searchProps.add(searchPropReqInfo);
            }
        });

        confirmSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPosition = searchPosition2;
                allAdapter.clear();
                sort(1, "", "", true);
                loadMoreGds006("", "");
                popupWindow.dismiss();

            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPosition = -1;
                adapter.clearPositionBg();
                searchProps.clear();
            }
        });
        popupWindow.showAtLocation(v, Gravity.END | Gravity.TOP, 0, 0);
    }

    //排序
    private void sort(final int pageNumber, String sortField, String sortType, boolean flag) {
        pageNumberAN = pageNumber;
        gds006Req.setShopId(storeId);
        gds006Req.setPageNumber(pageNumberAN);
        gds006Req.setPageSize(Constant.PAGE_SIZE);
        gds006Req.setField(sortField);
        gds006Req.setSort(sortType);
        gds006Req.setPropertyGroup(searchProps);
        getJsonService().requestGds006(StoreActivity.this, gds006Req, flag, new JsonService.CallBack<Gds006Resp>() {
            @Override
            public void oncallback(Gds006Resp gds006Resp) {
                if (null != gds006Resp && 0 != gds006Resp.getPageRespVO().size()) {
                    allAdapter.addAll(gds006Resp.getPageRespVO());
                } else {
                    ToastUtil.show(StoreActivity.this, R.string.toast_load_more_msg);
                }
                allNewRecyclerView.onRefreshComplete();
            }

            @Override
            public void onErro(AppHeader header) {
                allNewRecyclerView.onRefreshComplete();
            }
        });
    }

    private void loadMoreGds006(final String sortField, final String sortType) {
        allNewRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNumberAN = 1;
                allAdapter.clear();
                sort(pageNumberAN, sortField, sortType, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                sort(++pageNumberAN, sortField, sortType, false);
            }
        });
    }

    //店铺收藏
    private void collectStore() {
        if (AppContext.isLogin) {
            if ("关注".equals(storeCollectTv.getText().toString())) {
                Staff114Req staff114Req = new Staff114Req();
                staff114Req.setShopId(Long.parseLong(storeId));
                getJsonService().requestStaff114(StoreActivity.this, staff114Req, true, new JsonService.CallBack<Staff114Resp>() {
                    @Override
                    public void oncallback(Staff114Resp staff114Resp) {
                        if (staff114Resp.isFlag()) {
                            collectStatus("已关注");
                            ToastUtil.showIconToast(StoreActivity.this, "关注成功");
                            Intent intent = new Intent(StoreCollectFragment.REFRESH);
                            sendBroadcast(intent);
                        }
                    }

                    @Override
                    public void onErro(AppHeader header) {

                    }
                });
            } else if ("已关注".equals(storeCollectTv.getText().toString())) {
                Staff115Req staff115Req = new Staff115Req();
                staff115Req.setShopId(Long.parseLong(storeId));
                getJsonService().requestStaff115(StoreActivity.this, staff115Req, true, new JsonService.CallBack<Staff115Resp>() {
                    @Override
                    public void oncallback(Staff115Resp staff115Resp) {
                        if (staff115Resp.isFlag()) {
                            collectStatus("关注");
                            ToastUtil.showIconToast(StoreActivity.this, "取消成功");
                            Intent intent = new Intent(StoreCollectFragment.REFRESH);
                            sendBroadcast(intent);
                        }
                    }

                    @Override
                    public void onErro(AppHeader header) {

                    }
                });
            }
        } else {
            DialogAnotherUtil.showCustomAlertDialogWithMessage(StoreActivity.this, null,
                    "您未登录，请先登录", "登录", "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogUtil.dismissDialog();
                            launch(LoginActivity.class);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogUtil.dismissDialog();
                        }
                    });
        }
    }

    //店铺分类
    private void classify() {
        Intent intent = new Intent(StoreActivity.this, StoreClassifyActivity.class);
        intent.putExtra(Constant.STORE_ID, storeId);
        launch(intent);
    }

    /**
     * 显示或隐藏下划线
     *
     * @param index
     */
    public void showLine(int index) {
        for (int i = 0; i < lineLayout.getChildCount(); i++) {
            if (index == i) {
                lineLayout.getChildAt(index).setVisibility(View.VISIBLE);
            } else {
                lineLayout.getChildAt(i).setVisibility(View.INVISIBLE);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
