package com.ailk.pmph.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ai.ecp.app.req.Gds012Req;
import com.ai.ecp.app.resp.Gds012Resp;
import com.ai.ecp.app.resp.gds.GdsEvalBaseInfo;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.activity.CommitCommentActivity;
import com.ailk.pmph.ui.activity.ShopDetailActivity;
import com.ailk.pmph.ui.adapter.CommentListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:未评价
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.fragment
 * 作者: Chrizz
 * 时间: 2016/4/15 15:13
 */
public class UnCommentCenterFragment extends BaseFragment implements CommentListAdapter.GoCommentInterface,
    CommentListAdapter.RedirectToShopDetailInterface{

    @BindView(R.id.rl_empty_layout)
    RelativeLayout rlEmpty;
    @BindView(R.id.ll_unempty_layout)
    LinearLayout llUnEmpty;
    @BindView(R.id.lv_un_comment_list)
    PullToRefreshListView lvUnCommentList;

    private CommentListAdapter adapter;
    private int pageNo = 1;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comment_center_uncomment;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        requestGds012(true);
    }

    public void initView(View view) {
        lvUnCommentList.setMode(PullToRefreshBase.Mode.BOTH);
        lvUnCommentList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo=1;
                requestGds012(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMoreData();
            }
        });
    }

    private void requestGds012(boolean isShowProgress){
        Gds012Req request = new Gds012Req();
        request.setPageNumber(pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        request.setStatus("0");
        getJsonService().requestGds012(getActivity(), request, isShowProgress, new JsonService.CallBack<Gds012Resp>() {
            @Override
            public void oncallback(Gds012Resp gds012Resp) {
                List<GdsEvalBaseInfo> gdsEvalRespList = gds012Resp.getGdsEvalRespList();
                if (gdsEvalRespList != null && gdsEvalRespList.size() == 0) {
                    setVisible(rlEmpty);
                    setGone(llUnEmpty);
                } else {
                    setGone(rlEmpty);
                    setVisible(llUnEmpty);
                    adapter = new CommentListAdapter(getActivity(), gdsEvalRespList);
                    adapter.setStatus("0");
                    adapter.setGoCommentInterface(UnCommentCenterFragment.this);
                    adapter.setRedirectToShopDetailInterface(UnCommentCenterFragment.this);
                    lvUnCommentList.setAdapter(adapter);
                    lvUnCommentList.onRefreshComplete();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void loadMoreData(){
        Gds012Req request = new Gds012Req();
        request.setPageNumber(++pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        request.setStatus("0");
        getJsonService().requestGds012(getActivity(), request, true, new JsonService.CallBack<Gds012Resp>() {
            @Override
            public void oncallback(Gds012Resp gds012Resp) {
                List<GdsEvalBaseInfo> data = gds012Resp.getGdsEvalRespList();
                if (data.size() == 0) {
                    ToastUtil.showCenter(getActivity(), R.string.toast_load_more_msg);
                    lvUnCommentList.onRefreshComplete();
                } else {
                    adapter.addData(data);
                    lvUnCommentList.onRefreshComplete();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void goComment(GdsEvalBaseInfo bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("gdsEvalBaseInfo", bean);
        launch(CommitCommentActivity.class, bundle);
        getActivity().finish();
    }

    @Override
    public void redirectToShopDetail(Long skuId, Long gdsId) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(gdsId));
        bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(skuId));
        launch(ShopDetailActivity.class, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
