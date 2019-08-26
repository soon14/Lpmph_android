package com.ailk.integral.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ai.ecp.app.req.Pointmgds002Req;
import com.ai.ecp.app.resp.Pointmgds002Resp;
import com.ai.ecp.app.resp.gds.PointGdsDetailBaseInfo;
import com.ai.ecp.app.resp.gds.PointGdsScoreExtRespInfo;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.integral.activity.InteShopDetailActivity;
import com.ailk.integral.adapter.InteRankListAdapter;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.frag
 * 作者: Chrizz
 * 时间: 2016/5/11 15:18
 */
public class InteRankFragment extends BaseFragment implements InteRankListAdapter.RedirectToDetailInterface{

    @BindView(R.id.lv_rank_list)
    ListView lvRankList;

    private List<PointGdsDetailBaseInfo> rankGdsList;
    private InteRankListAdapter adapter;

    public static InteRankFragment newInstance() {
        InteRankFragment fragment = new InteRankFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.inte_fragment_rank;
    }

    @Override
    public void initData() {

    }

    public void initView(View view){
        Pointmgds002Req req = new Pointmgds002Req();
        getInteJsonService().requestPointGds002(getActivity(), req, true, new InteJsonService.CallBack<Pointmgds002Resp>() {
            @Override
            public void oncallback(Pointmgds002Resp pointmgds002Resp) {
                rankGdsList = pointmgds002Resp.getRankGdsList();
                if (rankGdsList != null) {
                    adapter = new InteRankListAdapter(getActivity(), rankGdsList);
                    adapter.setRedirectToDetailInterface(InteRankFragment.this);
                    lvRankList.setAdapter(adapter);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void redirectToDetail(PointGdsScoreExtRespInfo info) {
        Bundle bundle = new Bundle();
        Long gdsId = info.getGdsId();
        Long skuId = info.getSkuId();
        if (gdsId != null) {
            bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(gdsId));
        }
        if (skuId != null) {
            bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(skuId));
        }
        launch(InteShopDetailActivity.class, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
