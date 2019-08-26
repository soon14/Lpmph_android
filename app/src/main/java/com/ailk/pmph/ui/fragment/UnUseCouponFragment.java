package com.ailk.pmph.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ai.ecp.app.req.Coup001Req;
import com.ai.ecp.app.resp.Coup001Resp;
import com.ai.ecp.app.resp.CoupDetailResp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.adapter.CouponListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.BindView;

/**
 * 类注释:未使用的优惠券
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.fragment
 * 作者: Chrizz
 * 时间: 2016/4/8 12:44
 */
public class UnUseCouponFragment extends BaseFragment {

    @BindView(R.id.rl_empty_coupon)
    RelativeLayout rlEmpty;
    @BindView(R.id.ll_unempty_layout)
    LinearLayout llUnEmpty;
    @BindView(R.id.lv_un_use_list)
    PullToRefreshListView lvUnUseList;

    private CouponListAdapter adapter;
    private int pageNo = 1;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_unuse_coupon;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        requestCoup001(true);
    }

    public void initView(View view){
        lvUnUseList.setMode(PullToRefreshBase.Mode.BOTH);
        lvUnUseList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo=1;
                requestCoup001(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMoreData();
            }
        });
    }

    private void requestCoup001(boolean isShowProgress){
        final Coup001Req request = new Coup001Req();
        request.setOpeType(Constant.OPE_TYPE_UN_USE);
        request.setPageNo(pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestCoup001(getActivity(), request, isShowProgress, new JsonService.CallBack<Coup001Resp>() {
            @Override
            public void oncallback(Coup001Resp coup001Resp) {
                List<CoupDetailResp> resList = coup001Resp.getRespList();
                if (resList!=null && resList.size()==0) {
                    setVisible(rlEmpty);
                    setGone(llUnEmpty);
                } else {
                    setGone(rlEmpty);
                    setVisible(llUnEmpty);
                    adapter = new CouponListAdapter(getActivity(), resList, Constant.OPE_TYPE_UN_USE);
                    lvUnUseList.setAdapter(adapter);
                    lvUnUseList.onRefreshComplete();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void loadMoreData() {
        Coup001Req request = new Coup001Req();
        request.setOpeType(Constant.OPE_TYPE_UN_USE);
        request.setPageNo(++pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestCoup001(getActivity(), request, false, new JsonService.CallBack<Coup001Resp>() {
            @Override
            public void oncallback(Coup001Resp coup001Resp) {
                List<CoupDetailResp> data = coup001Resp.getRespList();
                if (data.size() == 0) {
                    ToastUtil.showCenter(getActivity(), R.string.toast_load_more_msg);
                    lvUnUseList.onRefreshComplete();
                } else {
                    adapter.addData(data);
                    lvUnUseList.onRefreshComplete();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
