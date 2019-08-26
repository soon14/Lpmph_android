package com.ailk.pmph.ui.fragment;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.ai.ecp.app.req.Cms007Req;
import com.ai.ecp.app.resp.Cms007Resp;
import com.ai.ecp.app.resp.cms.CategoryRespVO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.ui.adapter.SortChildListAdapter;
import com.ailk.pmph.ui.view.CustomExpandableListView;
import com.ailk.tool.GlideUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.fragment
 * 作者: Chrizz
 * 时间: 2016/9/30 16:46
 */

public class SortChildFragment extends BaseFragment {

    @BindView(R.id.iv_banner)
    ImageView mBannerIv;
    @BindView(R.id.lv_child_sort_list)
    CustomExpandableListView mListView;

    public static final String TAG = "SortChildFragment";
    private SortChildListAdapter mAdapter;
    /**
     * 二级分类数据
     */
    private List<CategoryRespVO> mList;
    /**
     * 所有分组的所有子项的 GridView 数据集合
     */
    private Map<String, List<CategoryRespVO>> mItemList;
    /**
     * 每个分组下的每个子项的 GridView 数据集合
     */
    private List<CategoryRespVO> mItemGridList;
    private Cms007Req mRequest;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_sort_child;
    }

    @Override
    public void initView(View view) {
        mRequest = new Cms007Req();
        mList = new ArrayList<>();
        mItemList = new HashMap<>();
        mItemGridList = new ArrayList<>();
        requestCategoryList();
        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                    if (groupPosition != i) {
                        mListView.collapseGroup(i);
                    }
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    private void requestCategoryList() {
        final CategoryRespVO respVO = (CategoryRespVO) getArguments().get(TAG);
        if (respVO != null) {
            Long catgId = respVO.getId();
            if (catgId != null) {
                mRequest.setId(String.valueOf(catgId));
                getJsonService().requestCms007(getActivity(), mRequest, true, new JsonService.CallBack<Cms007Resp>() {
                    @Override
                    public void oncallback(Cms007Resp cms007Resp) {
                        mList = cms007Resp.getCatgList();
                        GlideUtil.loadImg(getActivity(),respVO.getVfsUrl(),mBannerIv);
                        if (mList != null && mList.size() != 0) {
                            for (int i = 0; i < mList.size(); i++) {
                                final CategoryRespVO bean = mList.get(i);
                                mRequest.setId(String.valueOf(bean.getId()));
                                getJsonService().requestCms007(getActivity(), mRequest, false, new JsonService.CallBack<Cms007Resp>() {
                                    @Override
                                    public void oncallback(Cms007Resp cms007Resp) {
                                        mItemGridList = cms007Resp.getCatgList();
                                        mItemList.put(String.valueOf(bean.getId()), mItemGridList);
                                        mAdapter = new SortChildListAdapter(getActivity(), mList, mItemList);
                                        mListView.setAdapter(mAdapter);
                                    }

                                    @Override
                                    public void onErro(AppHeader header) {

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
        }
    }

}
