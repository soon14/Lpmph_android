package com.ailk.pmph.thirdstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ai.ecp.app.req.Gds006Req;
import com.ai.ecp.app.req.Gds014Req;
import com.ai.ecp.app.req.gds.SearchPropReqInfo;
import com.ai.ecp.app.resp.Gds006Resp;
import com.ai.ecp.app.resp.Gds014Resp;
import com.ai.ecp.app.resp.gds.GdsPropBaseInfo;
import com.ai.ecp.app.resp.gds.GdsPropValueBaseInfo;
import com.ai.ecp.app.resp.gds.GoodSearchResultVO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.activity.ShopDetailActivity;
import com.ailk.pmph.ui.adapter.SearchByPropsAdapter;
import com.ailk.pmph.thirdstore.adapter.StoreRecyclerAllAdapter;
import com.ailk.pmph.utils.Constant;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchResultInStoreActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.sort_layout)
    LinearLayout sortLayout;
    @BindView(R.id.content_layout)
    LinearLayout contentLayout;
    @BindView(R.id.no_content_layout)
    LinearLayout noContentLayout;
    @BindView(R.id.recyclerViewId)
    PullToRefreshRecyclerView mRecyclerView;

    @BindView(R.id.sort_style)
    ImageView sortStyle;
    private View mSort1View;
    private View mSort2View;
    private View mSort3View;
    private View mFilterView;

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

    private String storeId = null;//店铺id
    private String keyword = null;//关键字
    private Gds006Req gds006Req;
    private StoreRecyclerAllAdapter adapter;
    private boolean isGridOrList = true; //true表示当前为grid布局
    private int pageNumber = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_store_in_search2;
    }

    @Override
    public void initView() {

    }

    public void initData() {
        storeId = getIntent().getStringExtra(Constant.STORE_ID);
        keyword = getIntent().getStringExtra(Constant.STORE_KEY_WORDS);
        tvSearch.setText(keyword);
        gds006Req = new Gds006Req();
        adapter = new StoreRecyclerAllAdapter(SearchResultInStoreActivity.this, new ArrayList<GoodSearchResultVO>());
        adapter.updateStyle(true);//默认为Grid布局
        final GridLayoutManager gManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gManager);//这里用线性宫格显示 类似于grid view
        mRecyclerView.setAdapter(adapter);
        gManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (adapter.isHeaderView(position) || adapter.isBottomView(position)) ? gManager.getSpanCount() : 1;
            }
        });
        mRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);

        initSortView();
        sortDefault();//默认为综合排序
    }

    private void initSortView() {
        searchProps = new ArrayList<>();
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
    }

    @Override
    @OnClick({R.id.iv_back, R.id.tv_search, R.id.classify_img_btn, R.id.to_classify_tv, R.id.sort1, R.id.sort2,
            R.id.sort3, R.id.filter, R.id.sort_style})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.tv_search:
                if (Constant.STORE_CLASSIFY.equals(getIntent().getStringExtra(Constant.STORE_SOURCE))) {
                    Intent intent = new Intent(SearchResultInStoreActivity.this, SearchInStoreActivity.class);
                    intent.putExtra(Constant.STORE_ID, storeId);
                    launch(intent);
                }else if (Constant.STORE_SEARCH.equals(getIntent().getStringExtra(Constant.STORE_SOURCE))) {
                    onBackPressed();
                }
                break;

            //分类
            case R.id.classify_img_btn:
                if (Constant.STORE_CLASSIFY.equals(getIntent().getStringExtra(Constant.STORE_SOURCE))) {
                    onBackPressed();
                }else if (Constant.STORE_SEARCH.equals(getIntent().getStringExtra(Constant.STORE_SOURCE))) {
                    classify();
                }
                break;

            case R.id.to_classify_tv:
                if (Constant.STORE_CLASSIFY.equals(getIntent().getStringExtra(Constant.STORE_SOURCE))) {
                    onBackPressed();
                } else if (Constant.STORE_SEARCH.equals(getIntent().getStringExtra(Constant.STORE_SOURCE))) {
                    classify();
                }
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
                    adapter.updateStyle(false);
                    //List布局
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setAdapter(adapter);
                } else {
                    adapter.updateStyle(true);
                    final GridLayoutManager gManager = new GridLayoutManager(this, 2);
                    mRecyclerView.setLayoutManager(gManager);//这里用线性宫格显示 类似于grid view
                    mRecyclerView.setAdapter(adapter);
                    gManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return (adapter.isHeaderView(position) || adapter.isBottomView(position)) ? gManager.getSpanCount() : 1;
                        }
                    });
                }
                sortStyle.setSelected(!sortStyle.isSelected());
                isGridOrList = !isGridOrList;
                break;
        }
    }

    /**
     * 查看分类
     */
    private void classify() {
        Intent intent = new Intent(SearchResultInStoreActivity.this, StoreClassifyActivity.class);
        intent.putExtra(Constant.STORE_ID, storeId);
        launch(intent);
    }

    /**
     * 排序
     */
    private void sort(String sortField, String sortType, boolean flag) {
        gds006Req.setShopId(storeId);
        gds006Req.setKeyword(keyword);
        gds006Req.setPageNumber(pageNumber++);
        gds006Req.setPageSize(Constant.PAGE_SIZE);
        gds006Req.setField(sortField);
        gds006Req.setSort(sortType);
        gds006Req.setPropertyGroup(searchProps);
        getJsonService().requestGds006(SearchResultInStoreActivity.this, gds006Req, flag, new JsonService.CallBack<Gds006Resp>() {
            @Override
            public void oncallback(Gds006Resp gds006Resp) {
                if (null != gds006Resp && 0 != gds006Resp.getPageRespVO().size()) {
                    adapter.addAll(gds006Resp.getPageRespVO());
                    setGone(noContentLayout);
                    setVisible(sortLayout,contentLayout);
                } else {
                    if (2 == pageNumber) {
                        setGone(sortLayout,contentLayout);
                        setVisible(noContentLayout);
                    }else {
                        ToastUtil.show(SearchResultInStoreActivity.this, R.string.toast_load_more_msg);
                    }
                }
                mRecyclerView.onRefreshComplete();
            }

            @Override
            public void onErro(AppHeader header) {
                mRecyclerView.onRefreshComplete();
            }
        });

        adapter.setOnItemClickListener(new StoreRecyclerAllAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GoodSearchResultVO goodSearchResultVO = adapter.getItem(position - 1);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(goodSearchResultVO.getId()));
                bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(goodSearchResultVO.getFirstSkuId()));
                launch(ShopDetailActivity.class, bundle);
            }
        });
    }

    /**
     * 综合排序
     */
    private void sortDefault() {
        isPriceUp = false;
        mSort2View.setSelected(false);
        mSort3View.setSelected(false);
        mFilterView.setSelected(false);
        sortStyle.setSelected(false);
        adapter.clear();
        pageNumber = 1;
        sort("", "", true);
        loadMoreGds006("", "");
        mSort1View.setSelected(true);
    }

    /**
     * 按价格排序
     */
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

    /**
     * 按价格从高到低
     */
    private void sortByPriceAsc() {
        adapter.clear();
        pageNumber = 1;
        sort(SORT_FIELD_PRICE, ASC, true);
        loadMoreGds006(SORT_FIELD_PRICE, ASC);
    }

    /**
     * 按价格从低到高
     */
    private void sortByPriceDesc() {
        adapter.clear();
        pageNumber = 1;
        sort(SORT_FIELD_PRICE, DESC, true);
        loadMoreGds006(SORT_FIELD_PRICE, DESC);
    }

    /**
     * 根据销量排序
     */
    private void sortByCounts() {
        ImageView img = (ImageView) mSort3View.findViewById(R.id.icon);
        if (mSort3View.isSelected()) {
            if (isPriceUp) {
                img.setImageResource(R.drawable.icon_triangle_down);
                isPriceUp = false;
                sortByCountsDesc();
            } else {
                img.setImageResource(R.drawable.icon_triangle_up);
                isPriceUp = true;
                sortByCountsAsc();
            }
        } else {
            isPriceUp = false;
            mSort1View.setSelected(false);
            mSort2View.setSelected(false);
            mFilterView.setSelected(false);
            img.setImageResource(R.drawable.icon_triangle_down);
            sortByCountsDesc();
        }
        mSort3View.setSelected(true);
    }

    /**
     * 按销量从高到低
     */
    private void sortByCountsAsc() {
        adapter.clear();
        pageNumber = 1;
        sort(SORT_FIELD_SALE, ASC, true);
        loadMoreGds006(SORT_FIELD_SALE, ASC);
    }

    /**
     * 按销量从低到高
     */
    private void sortByCountsDesc() {
        adapter.clear();
        pageNumber = 1;
        sort(SORT_FIELD_SALE, DESC, true);
        loadMoreGds006(SORT_FIELD_SALE, DESC);
    }

    /**
     * 筛选
     */
    private void sortByProps(View v) {
        mSort2View.setSelected(false);
        mSort3View.setSelected(false);
        mFilterView.setSelected(true);
        mSort1View.setSelected(false);
        sortStyle.setSelected(false);
        initPopUpWindow(v);
    }

    /**
     * 用于筛选条件进行搜索的弹窗
     */
    private void initPopUpWindow(View v) {
        Gds014Req req = new Gds014Req();
        req.setCatgCode("");
        View searchByPropsView = LayoutInflater.from(SearchResultInStoreActivity.this).inflate(R.layout.popupwindow_searchbyprops, null, false);
        ListView propList = (ListView) searchByPropsView.findViewById(R.id.searchbyprops_listview);
        Button confirmSearch = (Button) searchByPropsView.findViewById(R.id.searchbyprops_confirm);
        Button resetButton = (Button) searchByPropsView.findViewById(R.id.searchbyprops_reset);
        propList.setDivider(null);
        final SearchByPropsAdapter sPAdapter = new SearchByPropsAdapter(SearchResultInStoreActivity.this, searchPosition, R.layout.item_choosegood_vertical);
        propList.setAdapter(sPAdapter);
        getJsonService().requestGds014(SearchResultInStoreActivity.this, req, false, new JsonService.CallBack<Gds014Resp>() {
            @Override
            public void oncallback(Gds014Resp gds014Resp) {
                if ((null == gds014Resp) || (null == gds014Resp.getPropList()) || (0 == gds014Resp.getPropList().size())) {
                    ToastUtil.show(SearchResultInStoreActivity.this, "获取筛选条件失败，请稍后重试");
                    return;
                }
                for (int i = 0; i < gds014Resp.getPropList().size(); i++) {
                    GdsPropBaseInfo info = gds014Resp.getPropList().get(i);
                    sPAdapter.add(info);
                }
                sPAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
        final PopupWindow popupWindow = new PopupWindow(searchByPropsView,
                WindowManager.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT, true);

        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.background_login));
        setBackgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });

        sPAdapter.setListener(new SearchByPropsAdapter.GetPropValueListener() {
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
                adapter.clear();
                pageNumber = 1;
                sort("", "", true);
                loadMoreGds006("", "");
                popupWindow.dismiss();

            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPosition = -1;
                sPAdapter.clearPositionBg();
                searchProps.clear();
            }
        });
        popupWindow.showAtLocation(v, Gravity.END | Gravity.TOP, 0, 0);
    }

    private void loadMoreGds006(final String sortField, final String sortType) {
        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNumber = 1;
                adapter.clear();
                sort(sortField, sortType, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                sort(sortField, sortType, false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
