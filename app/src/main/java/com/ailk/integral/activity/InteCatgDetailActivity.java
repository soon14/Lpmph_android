package com.ailk.integral.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Pointmgds006Req;
import com.ai.ecp.app.req.Pointmgds014Req;
import com.ai.ecp.app.resp.Pointmgds006Resp;
import com.ai.ecp.app.resp.Pointmgds014Resp;
import com.ai.ecp.app.resp.cms.CategoryRespVO;
import com.ai.ecp.app.resp.gds.InteGdsSort;
import com.ai.ecp.app.resp.gds.PointGdsPropBaseInfo;
import com.ai.ecp.app.resp.gds.PointGdsPropValueBaseInfo;
import com.ai.ecp.app.resp.gds.PointGoodSearchResultVO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.integral.adapter.InteRangeListAdapter;
import com.ailk.integral.adapter.InteSearchResultAdapter;
import com.ailk.integral.adapter.InteSortListAdapter;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.base.BaseActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.act
 * 作者: Chrizz
 * 时间: 2016/5/17 15:55
 */
public class InteCatgDetailActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.sort_layout)
    RelativeLayout sortLayout;
    @BindView(R.id.range_layout)
    RelativeLayout rangeLayout;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.tv_range)
    TextView tvRange;
    @BindView(R.id.iv_sort_down)
    ImageView ivSortDown;
    @BindView(R.id.iv_range_down)
    ImageView ivRangeDown;
    @BindView(R.id.recycler_search_result)
    PullToRefreshRecyclerView searchResultView;
    @BindView(R.id.iv_none_search)
    ImageView ivNoSearch;

    private PopupWindow popupWindow;
    private InteSearchResultAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private String curSortType = "";
    private String curRangeType = "";
    private String currentKeyWord = "";
    private String currentCatgCode = "";
    private String field = "score";
    private int pageNumber = 1;
    private Pointmgds006Req request;
    private boolean isClick = true;

    @Override
    protected int getContentViewId() {
        return R.layout.inte_activity_sort_detail;
    }

    @Override
    public void initView() {

    }

    public void initData() {
        CategoryRespVO sortDetail = (CategoryRespVO) getIntent().getExtras().get("sortDetail");
        if (sortDetail!=null) {
            currentCatgCode = sortDetail.getCatgCode();
            if (StringUtils.isNotEmpty(currentCatgCode)) {
                currentKeyWord = "";
            } else {
                currentCatgCode = "";
                currentKeyWord = sortDetail.getCatgName();
            }
            tvTitle.setText(sortDetail.getCatgName());
        }
        request = new Pointmgds006Req();
        adapter = new InteSearchResultAdapter(this, new ArrayList<PointGoodSearchResultVO>());
        adapter.clear();
        searchResultView.setMode(PullToRefreshBase.Mode.BOTH);
        gridLayoutManager = new GridLayoutManager(this, 2);
        searchResultView.setLayoutManager(gridLayoutManager);
        searchResultView.setAdapter(adapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (adapter.isHeaderView(position) || adapter.isBottomView(position)) ? gridLayoutManager.getSpanCount() : 1;
            }
        });
        getCatgGoodList(pageNumber, curSortType, curRangeType);

        searchResultView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNumber = 1;
                adapter.clear();
                getCatgGoodList(pageNumber, curSortType, curRangeType);
                searchResultView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                loadMoreData();
                searchResultView.onRefreshComplete();
            }
        });

        adapter.setOnItemClickListener(new InteSearchResultAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PointGoodSearchResultVO resultVO = adapter.getItem(position - 1);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.SHOP_GDS_ID, resultVO.getId());
                bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(resultVO.getFirstSkuId()));
                launch(InteShopDetailActivity.class, bundle);
            }
        });
    }

    @Override
    @OnClick({R.id.iv_back, R.id.iv_search, R.id.sort_layout, R.id.range_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_search:
                Intent intent = new Intent(this, InteSearchActivity.class);
                intent.putExtra("catgCode", currentCatgCode);
                launch(intent);
                break;
            case R.id.sort_layout:
                List<InteGdsSort> sortList = new ArrayList<>();
                InteGdsSort gdsSort1 = new InteGdsSort();
                gdsSort1.setSort("综合排序");
                gdsSort1.setSortType("");
                gdsSort1.setSortId(1L);

                InteGdsSort gdsSort2 = new InteGdsSort();
                gdsSort2.setSort("积分从低到高");
                gdsSort2.setSortType("asc");
                gdsSort2.setSortId(2L);

                InteGdsSort gdsSort3 = new InteGdsSort();
                gdsSort3.setSort("积分从高到低");
                gdsSort3.setSortType("desc");
                gdsSort3.setSortId(3L);
                sortList.add(gdsSort1);
                sortList.add(gdsSort2);
                sortList.add(gdsSort3);

                clickSort(tvSort, ivSortDown, tvRange, ivRangeDown);
                showSearchSort(sortList, v);
                break;
            case R.id.range_layout:
                clickRange(tvSort,ivSortDown, tvRange, ivRangeDown);
                if (isClick) {
                    clickRange(tvSort, ivSortDown, tvRange, ivRangeDown);
                    requestRange(v);
                    isClick = false;
                } else {
                    isClick = true;
                }
                break;
        }
    }

    private void clickSort(TextView tvSort, ImageView ivSortDown, TextView tvRange, ImageView ivRangeDown) {
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvSort.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            tvSort.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
        tvRange.setTextColor(ContextCompat.getColor(this, R.color.gray_969696));
        setVisible(ivSortDown);
        setGone(ivRangeDown);
    }

    private void clickRange(TextView tvSort, ImageView ivSortDown, TextView tvRange, ImageView ivRangeDown) {
        tvSort.setTextColor(ContextCompat.getColor(this, R.color.gray_969696));
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvRange.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            tvRange.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
        setVisible(ivRangeDown);
        setGone(ivSortDown);
    }

    private void showSearchSort(final List<InteGdsSort> sortList, View asView) {
        if (popupWindow!=null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow=null;
        } else {
            View popupView = LayoutInflater.from(this).inflate(R.layout.inte_dialog_check_score, null);
            ListView listView = (ListView) popupView.findViewById(R.id.lv_score);
            final InteSortListAdapter sortListAdapter = new InteSortListAdapter(this);
            sortListAdapter.setList(sortList);
            if (StringUtils.equals("综合排序",tvSort.getText().toString())) {
                sortListAdapter.setRangType(1L);
            } else if (StringUtils.equals("积分从低到高",tvSort.getText().toString())) {
                sortListAdapter.setRangType(2L);
            } else if (StringUtils.equals("积分从高到低",tvSort.getText().toString())) {
                sortListAdapter.setRangType(3L);
            } else {
                sortListAdapter.setRangType(1L);
            }
            listView.setAdapter(sortListAdapter);
            popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.popup_window_animation);
            setBackgroundAlpha(0.5f);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.showAsDropDown(asView, 0, 0);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1.0f);
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tvSort.setText(sortList.get(position).getSort());
                    curSortType = sortList.get(position).getSortType();
                    if (popupWindow!=null) {
                        popupWindow.dismiss();
                    }
                    pageNumber=1;
                    adapter.clear();
                    getCatgGoodList(pageNumber, curSortType, curRangeType);
                }
            });
        }
    }

    private void requestRange(final View asView){
        Pointmgds014Req req = new Pointmgds014Req();
        getInteJsonService().requestPointGds014(this, req, false, new InteJsonService.CallBack<Pointmgds014Resp>() {
            @Override
            public void oncallback(Pointmgds014Resp pointmgds014Resp) {
                List<PointGdsPropBaseInfo> propList = pointmgds014Resp.getPropList();
                if (propList!=null && propList.size()>0) {
                    for (int i = 0; i < propList.size(); i++) {
                        PointGdsPropBaseInfo value = propList.get(i);
                        if (value.getValues()!=null && value.getValues().size()>0) {
                            showSearchRange(asView, value.getValues());
                        }
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void showSearchRange(View asView, final List<PointGdsPropValueBaseInfo> values) {
        if (popupWindow!=null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow=null;
        } else {
            View popupView = LayoutInflater.from(this).inflate(R.layout.inte_dialog_check_score, null);
            ListView listView = (ListView) popupView.findViewById(R.id.lv_score);
            InteRangeListAdapter rangeListAdapter = new InteRangeListAdapter(this);
            rangeListAdapter.setValues(values);
            if (StringUtils.equals("积分范围",tvRange.getText().toString())) {
                rangeListAdapter.setRangType(0L);
            }
            for (int i = 0; i < values.size(); i++) {
                PointGdsPropValueBaseInfo bean = values.get(i);
                if (StringUtils.equals(bean.getPropValue(),tvRange.getText().toString())) {
                    rangeListAdapter.setRangType(bean.getId());
                }
            }
            listView.setAdapter(rangeListAdapter);
            popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.popup_window_animation);
            setBackgroundAlpha(0.5f);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.showAsDropDown(asView, 0, 0);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1.0f);
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tvRange.setText(values.get(position).getPropValue());
                    curRangeType = String.valueOf(values.get(position).getId());
                    if (popupWindow!=null) {
                        popupWindow.dismiss();
                    }
                    pageNumber=1;
                    adapter.clear();
                    getCatgGoodList(pageNumber, curSortType, curRangeType);
                }
            });
        }
    }

    /**
     * 获取搜索结果列表
     * @param sort
     * @param rangeType
     */
    private void getCatgGoodList(int pageNo, String sort, String rangeType) {
        curSortType = sort;
        curRangeType = rangeType;
        request.setField(field);
        request.setCategory(currentCatgCode);
        request.setSort(curSortType);
        request.setRangeType(curRangeType);
        request.setKeyword(currentKeyWord);
        request.setPageNumber(pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        getInteJsonService().requestPointGds006(this, request, true, new InteJsonService.CallBack<Pointmgds006Resp>() {
            @Override
            public void oncallback(Pointmgds006Resp pointmgds006Resp) {
                if (pointmgds006Resp.getPageRespVO()!=null && pointmgds006Resp.getPageRespVO().size()!=0) {
                    setGone(ivNoSearch);
                    setVisible(searchResultView);
                    adapter.addAll(pointmgds006Resp.getPageRespVO());
                } else {
                    setVisible(ivNoSearch);
                    setGone(searchResultView);
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
        request.setField(field);
        request.setSort(curSortType);
        request.setRangeType(curRangeType);
        request.setKeyword(currentKeyWord);
        request.setCategory(currentCatgCode);
        request.setPageSize(Constant.PAGE_SIZE);
        request.setPageNumber(++pageNumber);
        getInteJsonService().requestPointGds006(this, request, true, new InteJsonService.CallBack<Pointmgds006Resp>() {
            @Override
            public void oncallback(Pointmgds006Resp pointmgds006Resp) {
                List<PointGoodSearchResultVO> data = pointmgds006Resp.getPageRespVO();
                if (data.size()==0) {
                    ToastUtil.showCenter(InteCatgDetailActivity.this, R.string.toast_load_more_msg);
                } else {
                    adapter.addData(data);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
