package com.ailk.pmph.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Gds006Req;
import com.ai.ecp.app.req.Gds010Req;
import com.ai.ecp.app.req.Gds014Req;
import com.ai.ecp.app.req.Gds027Req;
import com.ai.ecp.app.req.Gds028Req;
import com.ai.ecp.app.req.gds.SearchPropReqInfo;
import com.ai.ecp.app.resp.Gds006Resp;
import com.ai.ecp.app.resp.Gds010Resp;
import com.ai.ecp.app.resp.Gds014Resp;
import com.ai.ecp.app.resp.Gds027Resp;
import com.ai.ecp.app.resp.Gds028Resp;
import com.ai.ecp.app.resp.gds.CategoryTree;
import com.ai.ecp.app.resp.gds.GdsPropBaseInfo;
import com.ai.ecp.app.resp.gds.GdsPropValueBaseInfo;
import com.ai.ecp.app.resp.gds.GoodSearchResultVO;
import com.ai.ecp.goods.dubbo.dto.GdsPropRespDTO;
import com.ai.ecp.goods.dubbo.dto.GdsPropValueRespDTO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.treeview.Node;
import com.ailk.treeview.TreeListViewAdapter;
import com.ailk.pmph.ui.adapter.SearchBySortPropsAdapter;
import com.ailk.pmph.ui.adapter.SimpleTreeAdapter;
import com.ailk.pmph.ui.view.CustomListView;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.adapter.SearchByPropsAdapter;
import com.ailk.pmph.ui.adapter.SearchResultAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;


import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释: 搜索结果列表（商品列表）
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/7/20 14:41
 */
public class SearchResultActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title_layout_search)
    RelativeLayout searchLayout;
    @BindView(R.id.title_goodlist_content)
    EditText searchContent;
    @BindView(R.id.tv_search)
    TextView tvSearch;

    @BindView(R.id.sort1)
    View mSort1View;
    @BindView(R.id.sort2)
    View mSort2View;
    @BindView(R.id.sort3)
    View mSort3View;
    @BindView(R.id.filter)
    View mFilterView;
    @BindView(R.id.sort_style)
    ImageView ivSortStyle;

    @BindView(R.id.recycler_search_result)
    PullToRefreshRecyclerView searchResultView;
    @BindView(R.id.iv_none_search)
    ImageView ivNoSearch;

    private SearchResultAdapter mAdapter;
    private GridLayoutManager mGridLayoutManager;
    private boolean isGridList = true; //true表示当前为grid布局
    private List<SearchPropReqInfo> searchProps;

    private int pageNumber = 1;
    private int searchPosition = -1; //初始化为-1默认为不选择
    private int searchPosition2 = -1; //初始化为-1默认为不选择
    private int selectPosition = -1;
    private int selectPosition1 = -1;
    private int selectPosition2 = -1;
    private int selectPosition3 = -1;

    private int selectPricePosition1 = -1;
    private int selectPricePosition2 = -1;
    private int selectPricePosition3 = -1;
    private int selectPricePosition4 = -1;
    private int selectPricePosition5 = -1;

    private String currentSortField = "";
    private String currentSortType = "";
    private String currentKeyWord = "";
    private String currentCatgCode = "";
    private String currentCatgName = "";
    private boolean isPriceUp = false;
    private static final String SORT_FIELD_SALE = "sales";
    private static final String SORT_FIELD_PRICE = "discountPrice";
    private static final String ASC = "asc";//顺序
    private static final String DESC = "desc";//逆序
    private Gds006Req gds006Req;
    private String mAdid;
    private String mLinkType;
    private String mCatgCode = "";
    private String mCatgName = "";
    private List<GdsPropRespDTO> mGdsPropList;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search_result;
    }

    @Override
    public void initView() {
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            searchContent.setHint(R.string.str_searc_books);
        } else {
            searchContent.setHint(R.string.str_searc_goods);
        }
        initSortView();
        initSearchView();
    }

    @Override
    public void initData() {

    }

    private void initSortView() {
        //当切换有排序时清除已选排序的条件
        if (searchProps == null) {
            searchProps = new ArrayList<>();
        }
        if (mGdsPropList == null) {
            mGdsPropList = new ArrayList<>();
        }
        mGdsPropList.clear();
        selectPosition = -1;
        searchPosition = -1;
        searchProps.clear();
        searchProps = (List<SearchPropReqInfo>) getIntent().getExtras().get(Constant.AUTHOR_BOOKS);

        TextView tv1 = (TextView) mSort1View.findViewById(R.id.text);
        tv1.setText("综合排序");
        TextView tv3 = (TextView) mSort3View.findViewById(R.id.text);
        tv3.setText("销量");
        TextView tv2 = (TextView) mSort2View.findViewById(R.id.text);
        tv2.setText("价格");
        TextView tv4 = (TextView) mFilterView.findViewById(R.id.text);
        tv4.setText("筛选");
        ImageView icon = (ImageView) mSort1View.findViewById(R.id.icon);
        icon.setVisibility(View.GONE);

        mSort1View.setSelected(true);
        mSort2View.setSelected(false);
        mSort3View.setSelected(false);
        mFilterView.setSelected(false);

        gds006Req = new Gds006Req();
        mAdapter = new SearchResultAdapter(this, new ArrayList<GoodSearchResultVO>());
    }

    private void initSearchView() {
        searchContent.setFocusableInTouchMode(false);
        searchContent.setFocusable(false);
        searchContent.setText(getIntent().getStringExtra(Constant.SHOP_KEY_WORD));
        try {
            currentCatgCode = getIntent().getStringExtra(Constant.SHOP_CATG_CODE);
            currentCatgName = getIntent().getStringExtra(Constant.SHOP_CATG_NAME);
            currentKeyWord = searchContent.getText().toString();
            mAdid = getIntent().getStringExtra(Constant.SHOP_ADID);
            mLinkType = getIntent().getStringExtra(Constant.SHOP_LINK_TYPE);
        } catch (Exception e) {
            currentKeyWord = "";
        }
        mAdapter.clear();
        mAdapter.updateStyle(true);//默认为Grid布局
        searchResultView.setMode(PullToRefreshBase.Mode.BOTH);
        mGridLayoutManager = new GridLayoutManager(this, 2);
        mGridLayoutManager.setSmoothScrollbarEnabled(true);
        searchResultView.setLayoutManager(mGridLayoutManager);
        searchResultView.setAdapter(mAdapter);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (mAdapter.isHeaderView(position) || mAdapter.isBottomView(position)) ? mGridLayoutManager.getSpanCount() : 1;
            }
        });
        isGridList = true;
        getSearchResult(pageNumber, currentSortField, currentSortType);

        searchResultView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNumber = 1;
                mAdapter.clear();
                getSearchResult(pageNumber, currentSortField, currentSortType);
                searchResultView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                loadMoreData();
                searchResultView.onRefreshComplete();
            }
        });

        mAdapter.setOnItemClickListener(new SearchResultAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GoodSearchResultVO goodSearchResultVO = mAdapter.getItem(position - 1);
                Bundle bundle = new Bundle();
                if (null != goodSearchResultVO.getId()) {
                    bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(goodSearchResultVO.getId()));
                }
                if (0 != goodSearchResultVO.getFirstSkuId()) {
                    bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(goodSearchResultVO.getFirstSkuId()));
                }
                if (StringUtils.isNotEmpty(mAdid)) {
                    bundle.putString(Constant.SHOP_ADID, mAdid);
                }
                bundle.putString(Constant.SHOP_LINK_TYPE, mLinkType);
                launch(ShopDetailActivity.class, bundle);
            }
        });

        mAdapter.setCollectListener(new SearchResultAdapter.CollectListener() {
            @Override
            public void collect(GoodSearchResultVO resultVO, final ImageView imageView) {
                Long collectId = resultVO.getCollectId();
                Long skuId = resultVO.getFirstSkuId();
                Gds010Req request = new Gds010Req();
                request.setCollectId(collectId);
                request.setSkuId(skuId);
                if (!imageView.isSelected()) {
                    request.setIfCancel(true);
                } else {
                    request.setIfCancel(false);
                }
                getJsonService().requestGds010(SearchResultActivity.this, request, true, new JsonService.CallBack<Gds010Resp>() {
                    @Override
                    public void oncallback(Gds010Resp gds010Resp) {
                        if (imageView.isSelected()) {
                            imageView.setSelected(false);
                            imageView.setImageDrawable(ContextCompat.getDrawable(SearchResultActivity.this, R.drawable.icon_search_collect));
                            ToastUtil.show(SearchResultActivity.this, gds010Resp.getMsg());
                        } else {
                            imageView.setImageDrawable(ContextCompat.getDrawable(SearchResultActivity.this, R.drawable.icon_search_collected));
                            imageView.setSelected(true);
                            ToastUtil.show(SearchResultActivity.this, gds010Resp.getMsg());
                        }
                        pageNumber = 1;
                        mAdapter.clear();
                        getSearchResult(pageNumber, currentSortField, currentSortType);
                        searchResultView.onRefreshComplete();
                    }

                    @Override
                    public void onErro(AppHeader header) {

                    }
                });
            }
        });
    }

    /**
     * 获取搜索结果
     * @param pageNo
     * @param sortField
     * @param sortType
     */
    private void getSearchResult(int pageNo, String sortField, String sortType) {
        gds006Req.setField(sortField);
        gds006Req.setSort(sortType);
        gds006Req.setCategory(currentCatgCode);
        gds006Req.setKeyword(currentKeyWord);
        gds006Req.setPropertyGroup(searchProps);
        gds006Req.setPageSize(Constant.PAGE_SIZE);
        gds006Req.setPageNumber(pageNo);
        getJsonService().requestGds006(this, gds006Req, true, new JsonService.CallBack<Gds006Resp>() {
            @Override
            public void oncallback(Gds006Resp gds006Resp) {
                if (gds006Resp.getPageRespVO()!=null && gds006Resp.getPageRespVO().size()!=0) {
                    setGone(ivNoSearch);
                    setVisible(searchResultView);
                    mAdapter.addAll(gds006Resp.getPageRespVO());
                } else {
                    setGone(searchResultView);
                    setVisible(ivNoSearch);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 加载更多
     */
    private void loadMoreData() {
        gds006Req.setField(currentSortField);
        gds006Req.setSort(currentSortType);
        gds006Req.setCategory(currentCatgCode);
        gds006Req.setKeyword(currentKeyWord);
        gds006Req.setPageSize(Constant.PAGE_SIZE);
        gds006Req.setPageNumber(++pageNumber);
        getJsonService().requestGds006(this, gds006Req, true, new JsonService.CallBack<Gds006Resp>() {
            @Override
            public void oncallback(Gds006Resp gds006Resp) {
                List<GoodSearchResultVO> data = gds006Resp.getPageRespVO();
                if (data.size() == 0) {
                    ToastUtil.showCenter(SearchResultActivity.this, R.string.toast_load_more_msg);
                } else {
                    mAdapter.addData(data);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    @OnClick({R.id.iv_back, R.id.title_layout_search, R.id.tv_search, R.id.sort1, R.id.sort2, R.id.sort3, R.id.filter, R.id.sort_style})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.title_layout_search:
                Intent intent = new Intent(this, SearchActivity.class);
                if (StringUtils.isNotEmpty(currentCatgCode)) {
                    intent.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
                }
                launch(intent);
                onBackPressed();
                break;

            case R.id.tv_search:
                Intent it = new Intent(this, SearchActivity.class);
                if (StringUtils.isNotEmpty(currentCatgCode)) {
                    it.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
                }
                launch(it);
                onBackPressed();
                break;

            case R.id.sort1:
                //综合排序
                sortDefault();
                break;

            case R.id.sort2:
                //按价格排序
                sortByPrice();
                break;

            case R.id.sort3:
                //按销量排序
                sortByCounts();
                break;

            case R.id.filter:
                //筛选条件
                sortByProps(v);
                break;

            case R.id.sort_style:
                if (isGridList)
                {
                    mAdapter.updateStyle(false);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                    layoutManager.setSmoothScrollbarEnabled(true);
                    searchResultView.setLayoutManager(layoutManager);
                    searchResultView.setAdapter(mAdapter);
                }
                else
                {
                    mAdapter.updateStyle(true);
                    final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
                    gridLayoutManager.setSmoothScrollbarEnabled(true);
                    searchResultView.setLayoutManager(gridLayoutManager);
                    searchResultView.setAdapter(mAdapter);
                    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return (mAdapter.isHeaderView(position) || mAdapter.isBottomView(position)) ? gridLayoutManager.getSpanCount() : 1;
                        }
                    });
                }
                ivSortStyle.setSelected(!ivSortStyle.isSelected());
                isGridList = !isGridList;
                break;
        }
    }

    /**
     * 默认排序
     */
    private void sortDefault() {
        isPriceUp = false;
        mSort2View.setSelected(false);
        mSort3View.setSelected(false);
        mFilterView.setSelected(false);
        ivSortStyle.setSelected(false);
        mAdapter.clear();
        pageNumber = 1;
        getSearchResult(pageNumber, "", "");
        mSort1View.setSelected(true);
    }

    /**
     * 按价格排序
     */
    private void sortByPrice() {
        ImageView img = (ImageView) mSort2View.findViewById(R.id.icon);
        if (mSort2View.isSelected())
        {
            if (!isPriceUp) {
                img.setImageResource(R.drawable.icon_triangle_up);
                isPriceUp = true;
                sortByPriceAsc();
            } else {
                img.setImageResource(R.drawable.icon_triangle_down);
                isPriceUp = false;
                sortByPriceDesc();
            }
        }
        else
        {
            isPriceUp = false;
            img.setImageResource(R.drawable.icon_triangle_down);
            sortByPriceDesc();
            mSort1View.setSelected(false);
            mSort3View.setSelected(false);
            mFilterView.setSelected(false);
            ivSortStyle.setSelected(false);
        }
        mSort2View.setSelected(true);
    }

    /**
     * 按价格从高到低
     */
    private void sortByPriceAsc() {
        mAdapter.clear();
        pageNumber = 1;
        getSearchResult(pageNumber, SORT_FIELD_PRICE, ASC);
    }

    /**
     * 按价格从低到高
     */
    private void sortByPriceDesc() {
        mAdapter.clear();
        pageNumber = 1;
        getSearchResult(pageNumber, SORT_FIELD_PRICE, DESC);
    }

    /**
     * 按销量排序
     */
    private void sortByCounts() {
        ImageView img = (ImageView) mSort3View.findViewById(R.id.icon);
        if (mSort3View.isSelected())
        {
            if (isPriceUp) {
                img.setImageResource(R.drawable.icon_triangle_down);
                isPriceUp = false;
                mAdapter.clear();
                pageNumber = 1;
                getSearchResult(pageNumber, SORT_FIELD_SALE, DESC);
            } else {
                img.setImageResource(R.drawable.icon_triangle_up);
                isPriceUp = true;
                mAdapter.clear();
                pageNumber = 1;
                getSearchResult(pageNumber, SORT_FIELD_SALE, ASC);
            }
        }
        else
        {
            isPriceUp = false;
            mSort1View.setSelected(false);
            mSort2View.setSelected(false);
            mFilterView.setSelected(false);
            img.setImageResource(R.drawable.icon_triangle_down);
            mAdapter.clear();
            pageNumber = 1;
            getSearchResult(pageNumber, SORT_FIELD_SALE, DESC);
        }
        mSort3View.setSelected(true);
    }

    /**
     * 筛选
     */
    private void sortByProps(View asView) {
        mSort2View.setSelected(false);
        mSort3View.setSelected(false);
        mFilterView.setSelected(true);
        mSort1View.setSelected(false);
        ivSortStyle.setSelected(false);
        showPropPopUpWindow(asView);
    }

    /**
     * 显示筛选的弹窗
     * @param asView
     */
    private void showPropPopUpWindow(final View asView) {
        searchProps = new ArrayList<>();

        View searchByPropsView = LayoutInflater.from(this).inflate(R.layout.popupwindow_searchbyprops, null, false);
        final TextView phonePrice = (TextView) searchByPropsView.findViewById(R.id.tv_phone) ;
        final TextView hasGood = (TextView) searchByPropsView.findViewById(R.id.tv_has_good);
        final TextView promotion = (TextView) searchByPropsView.findViewById(R.id.tv_promotion);

        final TextView price1 = (TextView) searchByPropsView.findViewById(R.id.tv_price1);
        final TextView price2 = (TextView) searchByPropsView.findViewById(R.id.tv_price2);
        final TextView price3 = (TextView) searchByPropsView.findViewById(R.id.tv_price3);
        final TextView price4 = (TextView) searchByPropsView.findViewById(R.id.tv_price4);
        final TextView price5 = (TextView) searchByPropsView.findViewById(R.id.tv_price5);

//        final CustomListView propListView = (CustomListView) searchByPropsView.findViewById(R.id.searchbyprops_listview);
        RelativeLayout sortLayout = (RelativeLayout) searchByPropsView.findViewById(R.id.sort_layout);
        final TextView catgNameTv = (TextView) searchByPropsView.findViewById(R.id.tv_catg_name);
        final CustomListView sortPropListView = (CustomListView) searchByPropsView.findViewById(R.id.props_list);
        Button confirmSearch = (Button) searchByPropsView.findViewById(R.id.searchbyprops_confirm);
        Button resetButton = (Button) searchByPropsView.findViewById(R.id.searchbyprops_reset);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            catgNameTv.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
            confirmSearch.setBackgroundColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            catgNameTv.setTextColor(ContextCompat.getColor(this, R.color.red));
            confirmSearch.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        }

//        final SearchByPropsAdapter adapter = new SearchByPropsAdapter(this, searchPosition, R.layout.item_choosegood_vertical);
//        propListView.setAdapter(adapter);
//        Gds014Req req = new Gds014Req();
//        getJsonService().requestGds014(this, req, false, new JsonService.CallBack<Gds014Resp>() {
//            @Override
//            public void oncallback(Gds014Resp gds014Resp) {
//                if (gds014Resp != null) {
//                    List<GdsPropBaseInfo> propList = gds014Resp.getPropList();
//                    if (propList != null && propList.size() != 0) {
//                        for (int i = 0; i < propList.size(); i++) {
//                            GdsPropBaseInfo info = gds014Resp.getPropList().get(i);
//                            adapter.add(info);
//                        }
//                        adapter.notifyDataSetChanged();
//                    } else {
//                        setGone(propListView);
//                    }
//                }
//            }
//
//            @Override
//            public void onErro(AppHeader header) {
//
//            }
//        });

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

//        adapter.setListener(new SearchByPropsAdapter.GetPropValueListener() {
//            @Override
//            public void onChildClick(int position, GdsPropValueBaseInfo propInfo) {
//                searchPosition2 = position;
//                mSearchPropReqInfo = new SearchPropReqInfo();
//                List<String> propValues = new ArrayList<>();
//                propValues.clear();
//                propValues.add(String.valueOf(propInfo.getId()));
//                mSearchPropReqInfo.setPropertyValues(propValues);
//                mSearchPropReqInfo.setPropertyId(String.valueOf(propInfo.getPropId()));
//            }
//        });

        //===================人卫服务===================//
        if (selectPosition1 != -1) {
            selectPhonePriceService(phonePrice);
        } else {
            clearPhonePriceService(phonePrice);
        }

        if (selectPosition2 != -1) {
            selectHasGoodService(hasGood);
        } else {
            clearHasGoodService(hasGood);
        }

        if (selectPosition3 != -1) {
            selectPromotionService(promotion);
        } else {
            clearPromotionService(promotion);
        }

        phonePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.equals("手机专享", phonePrice.getText().toString())) {
                    selectPosition1 = 1;
                    selectPhonePriceService(phonePrice);
                } else {
                    selectPosition1 = -1;
                    clearPhonePriceService(phonePrice);
                }
            }
        });

        hasGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.equals("仅看有货", hasGood.getText().toString())) {
                    selectPosition2 = 2;
                    selectHasGoodService(hasGood);
                } else {
                    selectPosition2 = -1;
                    clearHasGoodService(hasGood);
                }
            }
        });

        promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.equals("促销", promotion.getText().toString())) {
                    selectPosition3 = 3;
                    selectPromotionService(promotion);
                } else {
                    selectPosition3 = -1;
                    clearPromotionService(promotion);
                }
            }
        });

        //====================价格区间==================//
        if (selectPricePosition1 != -1) {
            selectPrice(price1);
        } else {
            clearPriceBackground(price1);
        }

        if (selectPricePosition2 != -1) {
            selectPrice(price2);
        } else {
            clearPriceBackground(price2);
        }

        if (selectPricePosition3 != -1) {
            selectPrice(price3);
        } else {
            clearPriceBackground(price3);
        }

        if (selectPricePosition4 != -1) {
            selectPrice(price4);
        } else {
            clearPriceBackground(price4);
        }

        if (selectPricePosition5 != -1) {
            selectPrice(price5);
        } else {
            clearPriceBackground(price5);
        }

        price1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPricePosition1 = 1;
                selectPricePosition2 = -1;
                selectPricePosition3 = -1;
                selectPricePosition4 = -1;
                selectPricePosition5 = -1;
                selectPrice(price1);
                clearPriceBackground(price2);
                clearPriceBackground(price3);
                clearPriceBackground(price4);
                clearPriceBackground(price5);
            }
        });

        price2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPricePosition2 = 2;
                selectPricePosition1 = -1;
                selectPricePosition3 = -1;
                selectPricePosition4 = -1;
                selectPricePosition5 = -1;
                selectPrice(price2);
                clearPriceBackground(price1);
                clearPriceBackground(price3);
                clearPriceBackground(price4);
                clearPriceBackground(price5);
            }
        });

        price3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPricePosition3 = 3;
                selectPricePosition1 = -1;
                selectPricePosition2 = -1;
                selectPricePosition4 = -1;
                selectPricePosition5 = -1;
                selectPrice(price3);
                clearPriceBackground(price1);
                clearPriceBackground(price2);
                clearPriceBackground(price4);
                clearPriceBackground(price5);
            }
        });

        price4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPricePosition4 = 4;
                selectPricePosition1 = -1;
                selectPricePosition2 = -1;
                selectPricePosition3 = -1;
                selectPricePosition5 = -1;
                selectPrice(price4);
                clearPriceBackground(price1);
                clearPriceBackground(price2);
                clearPriceBackground(price3);
                clearPriceBackground(price5);
            }
        });

        price5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPricePosition5 = 5;
                selectPricePosition1 = -1;
                selectPricePosition2 = -1;
                selectPricePosition3 = -1;
                selectPricePosition4 = -1;
                selectPrice(price5);
                clearPriceBackground(price1);
                clearPriceBackground(price2);
                clearPriceBackground(price3);
                clearPriceBackground(price4);
            }
        });

        //=====================分类=====================//
        final SearchBySortPropsAdapter propAdapter = new SearchBySortPropsAdapter(this, R.layout.item_choose_sort_prop);
        sortPropListView.setAdapter(propAdapter);
        if (StringUtils.isNotEmpty(currentCatgName)) {
            catgNameTv.setText(currentCatgName);
            setVisible(sortPropListView);
            showSortProp(currentCatgCode, propAdapter);
        } else if (StringUtils.isNotEmpty(mCatgName)) {
            catgNameTv.setText(mCatgName);
            setVisible(sortPropListView);
            propAdapter.clear();
            propAdapter.addAll(mGdsPropList);
        } else {
            catgNameTv.setText("全部");
            setGone(sortPropListView);
        }

        sortLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSortPopupWindow(asView, catgNameTv, sortPropListView, propAdapter);
            }
        });

        confirmSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition = selectPosition1;
                selectPosition = selectPosition2;
                selectPosition = selectPosition3;

                if (selectPosition1 == 1) {
                    SearchPropReqInfo ifAppSpecPriceInfo = new SearchPropReqInfo();
                    List<String> propValues = new ArrayList<>();
                    propValues.clear();
                    propValues.add("ifAppSpecPrice");
                    ifAppSpecPriceInfo.setPropertyValues(propValues);
                    ifAppSpecPriceInfo.setPropertyId("pmphService");
                    searchProps.add(ifAppSpecPriceInfo);
                }
                if (selectPosition2 == 2) {
                    SearchPropReqInfo ifStorageInfo = new SearchPropReqInfo();
                    List<String> propValues = new ArrayList<>();
                    propValues.clear();
                    propValues.add("ifStorage");
                    ifStorageInfo.setPropertyValues(propValues);
                    ifStorageInfo.setPropertyId("pmphService");
                    searchProps.add(ifStorageInfo);
                }
                if (selectPosition3 == 3) {
                    SearchPropReqInfo ifPromotionInfo = new SearchPropReqInfo();
                    List<String> propValues = new ArrayList<>();
                    propValues.clear();
                    propValues.add("ifPromotion");
                    ifPromotionInfo.setPropertyValues(propValues);
                    ifPromotionInfo.setPropertyId("pmphService");
                    searchProps.add(ifPromotionInfo);
                }

                if (selectPricePosition1 == 1) {
                    SearchPropReqInfo priceInfo1 = new SearchPropReqInfo();
                    List<String> propValues = new ArrayList<>();
                    propValues.clear();
                    propValues.add("1");
                    priceInfo1.setPropertyValues(propValues);
                    priceInfo1.setPropertyId("-1");
                    searchProps.add(priceInfo1);
                }
                if (selectPricePosition2 == 2) {
                    SearchPropReqInfo priceInfo2 = new SearchPropReqInfo();
                    List<String> propValues = new ArrayList<>();
                    propValues.clear();
                    propValues.add("2");
                    priceInfo2.setPropertyValues(propValues);
                    priceInfo2.setPropertyId("-1");
                    searchProps.add(priceInfo2);
                }
                if (selectPricePosition3 == 3) {
                    SearchPropReqInfo priceInfo3 = new SearchPropReqInfo();
                    List<String> propValues = new ArrayList<>();
                    propValues.clear();
                    propValues.add("3");
                    priceInfo3.setPropertyValues(propValues);
                    priceInfo3.setPropertyId("-1");
                    searchProps.add(priceInfo3);
                }
                if (selectPricePosition4 == 4) {
                    SearchPropReqInfo priceInfo4 = new SearchPropReqInfo();
                    List<String> propValues = new ArrayList<>();
                    propValues.clear();
                    propValues.add("4");
                    priceInfo4.setPropertyValues(propValues);
                    priceInfo4.setPropertyId("-1");
                    searchProps.add(priceInfo4);
                }
                if (selectPricePosition5 == 5) {
                    SearchPropReqInfo priceInfo5 = new SearchPropReqInfo();
                    List<String> propValues = new ArrayList<>();
                    propValues.clear();
                    propValues.add("5");
                    priceInfo5.setPropertyValues(propValues);
                    priceInfo5.setPropertyId("-1");
                    searchProps.add(priceInfo5);
                }
                if (StringUtils.isNotEmpty(currentCatgName)) {
                    catgNameTv.setText(currentCatgName);
                } else {
                    catgNameTv.setText(mCatgName);
                }
                SearchPropReqInfo sortPropReqInfo = new SearchPropReqInfo();
                List<String> propValues = new ArrayList<>();
                propValues.clear();
                propValues.add(mCatgCode);
                sortPropReqInfo.setPropertyValues(propValues);
                sortPropReqInfo.setPropertyId("categories");
                searchProps.add(sortPropReqInfo);

                if (mGdsPropList != null) {
                    for (int i = 0; i < mGdsPropList.size(); i++) {
                        List<GdsPropValueRespDTO> values = mGdsPropList.get(i).getValues();
                        for (int j = 0; j < values.size(); j++) {
                            if (values.get(j).isChecked()) {
                                SearchPropReqInfo info = new SearchPropReqInfo();
                                List<String> list = new ArrayList<>();
                                list.clear();
                                list.add(String.valueOf(values.get(j).getId()));
                                info.setPropertyValues(list);
                                info.setPropertyId(String.valueOf(values.get(j).getPropId()));
                                searchProps.add(info);
                            }
                        }
                    }
                }

                mAdapter.clear();
                pageNumber = 1;
                getSearchResult(pageNumber, "", "");
                popupWindow.dismiss();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPosition2 = -1;
                selectPosition1 = -1;
                selectPosition2 = -1;
                selectPosition3 = -1;
                selectPricePosition1 = -1;
                selectPricePosition2 = -1;
                selectPricePosition3 = -1;
                selectPricePosition4 = -1;
                selectPricePosition5 = -1;
                clearPhonePriceService(phonePrice);
                clearHasGoodService(hasGood);
                clearPromotionService(promotion);
                clearPriceBackground(price1);
                clearPriceBackground(price2);
                clearPriceBackground(price3);
                clearPriceBackground(price4);
                clearPriceBackground(price5);
                mCatgName = "";
                mCatgCode = "";
                currentCatgCode = "";
                catgNameTv.setText("全部");
                if (mGdsPropList != null) {
                    for (int i = 0; i < mGdsPropList.size(); i++) {
                        for (int j = 0; j < mGdsPropList.get(i).getValues().size(); j++) {
                            mGdsPropList.get(i).getValues().get(j).setChecked(false);
                        }
                    }
                    propAdapter.notifyDataSetChanged();
                }
                setGone(sortPropListView);
                searchProps.clear();
            }
        });

        popupWindow.showAtLocation(asView, Gravity.END | Gravity.TOP, 0, 0);
    }

    private void selectPhonePriceService(TextView phonePrice) {
        phonePrice.setText("手机专享√");
        phonePrice.setTextColor(ContextCompat.getColor(SearchResultActivity.this, R.color.orange_ff6a00));
        phonePrice.setBackground(ContextCompat.getDrawable(SearchResultActivity.this, R.drawable.shape_round_delbutton));
    }

    private void selectHasGoodService(TextView hasGood) {
        hasGood.setText("仅看有货√");
        hasGood.setTextColor(ContextCompat.getColor(SearchResultActivity.this, R.color.orange_ff6a00));
        hasGood.setBackground(ContextCompat.getDrawable(SearchResultActivity.this, R.drawable.shape_round_delbutton));
    }

    private void selectPromotionService(TextView promotion) {
        promotion.setText("促销√");
        promotion.setTextColor(ContextCompat.getColor(SearchResultActivity.this, R.color.orange_ff6a00));
        promotion.setBackground(ContextCompat.getDrawable(SearchResultActivity.this, R.drawable.shape_round_delbutton));
    }

    private void clearPhonePriceService(TextView phonePrice) {
        phonePrice.setText("手机专享");
        phonePrice.setTextColor(ContextCompat.getColor(SearchResultActivity.this, R.color.black));
        phonePrice.setBackground(ContextCompat.getDrawable(SearchResultActivity.this, R.drawable.shape_round_textview));
    }

    private void clearHasGoodService(TextView hasGood) {
        hasGood.setText("仅看有货");
        hasGood.setTextColor(ContextCompat.getColor(SearchResultActivity.this, R.color.black));
        hasGood.setBackground(ContextCompat.getDrawable(SearchResultActivity.this, R.drawable.shape_round_textview));
    }

    private void clearPromotionService(TextView promotion) {
        promotion.setText("促销");
        promotion.setTextColor(ContextCompat.getColor(SearchResultActivity.this, R.color.black));
        promotion.setBackground(ContextCompat.getDrawable(SearchResultActivity.this, R.drawable.shape_round_textview));
    }

    private void selectPrice(TextView textView) {
        textView.setBackground(ContextCompat.getDrawable(SearchResultActivity.this, R.drawable.store_prop_bg));
    }

    private void clearPriceBackground(TextView textView) {
        textView.setBackground(ContextCompat.getDrawable(SearchResultActivity.this, R.drawable.shape_round_textview));
    }

    /**
     * 显示分类弹窗
     * @param asView
     * @param catgNameTv 具体分类
     * @param sortPropListView 具体分类属性
     */
    private void showSortPopupWindow(final View asView, final TextView catgNameTv, final CustomListView sortPropListView, final SearchBySortPropsAdapter propAdapter) {
        View sortView = LayoutInflater.from(this).inflate(R.layout.popupwindow_searchbysort, null, false);
        ImageView back = (ImageView) sortView.findViewById(R.id.iv_back);
        TextView confirm = (TextView) sortView.findViewById(R.id.tv_confirm);
        LinearLayout allCatgLayout = (LinearLayout) sortView.findViewById(R.id.all_catg_layout);
        final TextView allCatgTv = (TextView) sortView.findViewById(R.id.tv_all_catg);
        final ImageView allCatgIv = (ImageView) sortView.findViewById(R.id.iv_all_catg);
        final LinearLayout checkCatgLayout = (LinearLayout) sortView.findViewById(R.id.check_catg_layout);
        final TextView checkCatgTv = (TextView) sortView.findViewById(R.id.tv_check_catg);
        final ListView baseSortList = (ListView) sortView.findViewById(R.id.lv_base_sort);

        if (StringUtils.equals("全部", catgNameTv.getText().toString())) {
            setGone(checkCatgLayout);
            getAllCatg(baseSortList, allCatgTv, allCatgIv, catgNameTv, sortPropListView, propAdapter, checkCatgLayout, checkCatgTv);
        } else {
            allCatgTv.setTextColor(ContextCompat.getColor(SearchResultActivity.this, R.color.black));
            allCatgIv.setVisibility(View.GONE);
            setVisible(checkCatgLayout);
            checkCatgTv.setText(catgNameTv.getText().toString());
            setGone(baseSortList);
        }
        final PopupWindow popupWindow = new PopupWindow(sortView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        setBackgroundAlpha(0.2f);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.transparent)));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(0.5f);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(mCatgName) && StringUtils.isEmpty(currentCatgName)) {
                    mCatgName = "全部";
                    mCatgCode = "";
                }
                if (StringUtils.isNotEmpty(currentCatgName)) {
                    catgNameTv.setText(currentCatgName);
                } else {
                    catgNameTv.setText(mCatgName);
                }
                popupWindow.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        allCatgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allCatgTv.setTextColor(ContextCompat.getColor(SearchResultActivity.this, R.color.red));
                allCatgIv.setVisibility(View.VISIBLE);
                mCatgName = "全部";
                mCatgCode = "";
                currentCatgName = "";
                currentCatgCode = "";
                setGone(sortPropListView);
                catgNameTv.setText(mCatgName);
                setGone(checkCatgLayout);
                setVisible(baseSortList);
                getAllCatg(baseSortList, allCatgTv, allCatgIv, catgNameTv, sortPropListView, propAdapter, checkCatgLayout, checkCatgTv);
            }
        });
        popupWindow.showAtLocation(asView, Gravity.END | Gravity.TOP, 0, 0);
    }

    /**
     * 获取分类
     * @param listView
     * @param allCatgTv
     * @param allCatgIv
     * @param catgNameTv
     * @param sortPropListView
     * @param propAdapter
     */
    private void getAllCatg(final ListView listView, final TextView allCatgTv, final ImageView allCatgIv, final TextView catgNameTv,
                            final CustomListView sortPropListView, final SearchBySortPropsAdapter propAdapter, final LinearLayout checkCatgLayout,
                            final TextView checkCatgTv) {
        Gds027Req req = new Gds027Req();
        req.setId("all");
        getJsonService().requestGds027(SearchResultActivity.this, req, true, new JsonService.CallBack<Gds027Resp>() {
            @Override
            public void oncallback(Gds027Resp gds027Resp) {
                if (gds027Resp != null) {
                    List<CategoryTree> categoryTreeList = gds027Resp.getGdsCategoryList();
                    if (categoryTreeList != null && categoryTreeList.size() != 0) {
                        try {
                            final SimpleTreeAdapter adapter = new SimpleTreeAdapter<>(listView, SearchResultActivity.this,
                                    categoryTreeList, 0);
                            listView.setAdapter(adapter);

                            adapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                                @Override
                                public void onClick(Node node, View view, int position) {
                                    allCatgTv.setTextColor(ContextCompat.getColor(SearchResultActivity.this, R.color.black));
                                    allCatgIv.setVisibility(View.GONE);
                                    currentCatgName = "";
                                    currentCatgCode = "";
                                    if (!StringUtils.equals("全部", node.getName())
                                            && StringUtils.isEmpty(currentCatgName)) {
                                        mCatgName = node.getName();
                                        mCatgCode = node.getId();
                                        setVisible(checkCatgLayout);
                                        checkCatgTv.setText(mCatgName);
                                        catgNameTv.setText(mCatgName);
                                        setVisible(sortPropListView);
                                        showSortProp(mCatgCode,propAdapter);
                                        propAdapter.setListener(new SearchBySortPropsAdapter.GdsPropValueListener() {
                                            @Override
                                            public void onChildClick(int position, GdsPropValueRespDTO bean) {
                                                SearchPropReqInfo reqInfo = new SearchPropReqInfo();
                                                List<String> values = new ArrayList<>();
                                                values.clear();
                                                values.add(String.valueOf(bean.getId()));
                                                reqInfo.setPropertyValues(values);
                                                reqInfo.setPropertyId(String.valueOf(bean.getPropId()));
                                                searchProps.add(reqInfo);
                                            }
                                        });
                                    } else {
                                        mCatgName = "全部";
                                        mCatgCode = "";
                                        catgNameTv.setText(mCatgName);
                                        setGone(sortPropListView);
                                    }
                                }
                            });
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        setGone(listView);
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 显示对应分类属性
     * @param catgCode
     * @param adapter
     */
    private void showSortProp(String catgCode, final SearchBySortPropsAdapter adapter) {
        Gds028Req req = new Gds028Req();
        req.setCatgCode(catgCode);
        getJsonService().requestGds028(this, req, false, new JsonService.CallBack<Gds028Resp>() {
            @Override
            public void oncallback(Gds028Resp gds028Resp) {
                if (gds028Resp != null) {
                    mGdsPropList = gds028Resp.getGdsPropList();
                    if (mGdsPropList != null && mGdsPropList.size() != 0) {
                        adapter.clear();
                        adapter.addAll(mGdsPropList);
                    } else {
                        adapter.clear();
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
