package com.ailk.pmph.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ai.ecp.app.req.Cms007Req;
import com.ai.ecp.app.resp.Cms007Resp;
import com.ai.ecp.app.resp.cms.CategoryRespVO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.ui.activity.MessageActivity;
import com.ailk.pmph.utils.ListViewAdapter;
import com.ailk.pmph.utils.PromotionParseUtil;
import com.ailk.tool.GlideUtil;

import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.fragment
 * 作者: Chrizz
 * 时间: 2016/10/5 10:36
 */

public class ClassificationFragment extends BaseFragment {

    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.classfication_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.classification_listview)
    ListView listView;
    private Cms007Resp cms007Resp;
    private ClassficationListViewAdapter adapter;

    public ClassificationFragment() {

    }

    public static ClassificationFragment newInstance() {
        ClassificationFragment fragment = new ClassificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_classification;
    }

    public void initData() {
        cms007Resp = (Cms007Resp) getArguments().get("cm007");
        if (null != cms007Resp) {
            if ((null == cms007Resp.getCatgList())
                    || (0 == cms007Resp.getCatgList().size())) {
                return;
            }
            for (int i = 0; i < cms007Resp.getCatgList().size(); i++) {
                CategoryRespVO kind = cms007Resp.getCatgList().get(i);
                adapter.add(kind);
            }
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CategoryRespVO kind = (CategoryRespVO) adapter.getItem(position);
                    PromotionParseUtil.parsePromotionUrl(getActivity(), kind.getCatgUrl());
                }
            });
        }
    }

    public void initView(View view) {
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch(MessageActivity.class);
            }
        });
        adapter = new ClassficationListViewAdapter(getActivity(), R.layout.classification_list_item);
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getIntArray(R.array.swipe_colors));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Cms007Req cms007Req = new Cms007Req();
                cms007Req.setPlaceId(Long.valueOf("13"));
                new JsonService(getActivity()).requestCms007(getActivity(), cms007Req, false, new JsonService.CallBack<Cms007Resp>() {
                    @Override
                    public void oncallback(Cms007Resp cms007Resp2) {
                        cms007Resp = cms007Resp2;
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.clear();
                        for (int i = 0; i < cms007Resp.getCatgList().size(); i++) {
                            CategoryRespVO kind = cms007Resp.getCatgList().get(i);
                            adapter.add(kind);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onErro(AppHeader header) {

                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private class ClassficationListViewAdapter extends ListViewAdapter {

        public ClassficationListViewAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public void initView(View view, int position, View convertView) {
            ImageView imageView = (ImageView) view.findViewById(R.id.classification_item_img);
            TextView titleView = (TextView) view.findViewById(R.id.item_title);
            TextView detailView = (TextView) view.findViewById(R.id.sold_price);
            CategoryRespVO kind = (CategoryRespVO) getItem(position);
            if (kind != null) {
                titleView.setText(kind.getCatgName());
                detailView.setText(kind.getChildStr());
                GlideUtil.loadImg(getActivity(), kind.getVfsUrl(), imageView);
            }
        }
    }

}
