package com.ailk.pmph.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ai.ecp.app.req.Gds009Req;
import com.ai.ecp.app.req.Gds010Req;
import com.ai.ecp.app.req.Ord00101Req;
import com.ai.ecp.app.req.Ord001Req;
import com.ai.ecp.app.req.Ord018Req;
import com.ai.ecp.app.resp.Gds009Resp;
import com.ai.ecp.app.resp.Gds010Resp;
import com.ai.ecp.app.resp.Ord001Resp;
import com.ai.ecp.app.resp.Ord018Resp;
import com.ai.ecp.app.resp.gds.GdsCollectBaseInfo;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.ui.activity.MainActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ListViewAdapter;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.activity.ShopDetailActivity;
import com.ailk.tool.GlideUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商品收藏
 */
public class ShopCollectFragment extends BaseFragment {


    @BindView(R.id.layout_collect_none)
    View none;
    @BindView(R.id.collect_listview)
    PullToRefreshListView collectListView;

    private CollectAdapter adapter;
    private int index = 1;
    private final String GOOD_ON = "11";//商品已上架

    public ShopCollectFragment() {
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_shop_collect;
    }

    public void initData() {
        Gds009Req req = new Gds009Req();
        req.setPageSize(Constant.PAGE_SIZE);
        index = 1;
        req.setPageNumber(index);
        getJsonService().requestGds009(getActivity(), req, true, new JsonService.CallBack<Gds009Resp>() {
            @Override
            public void oncallback(Gds009Resp gds009Resp) {
                if (0 == gds009Resp.getGdsCollectBaseInfos().size()) {
                    setVisible(none);
                    setGone(collectListView);
                    return;
                }
                adapter.clear();
                for (int i = 0; i < gds009Resp.getGdsCollectBaseInfos().size(); i++) {
                    GdsCollectBaseInfo good = gds009Resp.getGdsCollectBaseInfos().get(i);
                    adapter.add(good);
                }
                adapter.notifyDataSetChanged();
                collectListView.onRefreshComplete();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    public void initView(View view) {
        adapter = new CollectAdapter(getActivity(), R.layout.item_collect);
        collectListView.setAdapter(adapter);
        collectListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                index=1;
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                updateDatas();
            }
        });

    }

    private void updateDatas() {
        Gds009Req req = new Gds009Req();
        req.setPageNumber(++index);
        req.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestGds009(getActivity(), req, false, new JsonService.CallBack<Gds009Resp>() {
            @Override
            public void oncallback(Gds009Resp gds009Resp) {
                collectListView.onRefreshComplete();
                if (gds009Resp.getGdsCollectBaseInfos().size() == 0){
                    ToastUtil.show(getActivity(), R.string.toast_load_more_msg);
                    return;
                }
                for (int i = 0; i < gds009Resp.getGdsCollectBaseInfos().size(); i++) {
                    GdsCollectBaseInfo good = gds009Resp.getGdsCollectBaseInfos().get(i);
                    adapter.add(good);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private class CollectAdapter extends ListViewAdapter {

        public CollectAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public void initView(View view, final int position, final View convertView) {
            final GdsCollectBaseInfo good = (GdsCollectBaseInfo) getItem(position);
            ImageView img = (ImageView) view.findViewById(R.id.collect_img);
            TextView promotion = (TextView) view.findViewById(R.id.collect_promotion);
            TextView name = (TextView) view.findViewById(R.id.collect_name);
            TextView des1 = (TextView) view.findViewById(R.id.collect_des1);//包邮
            TextView des2 = (TextView) view.findViewById(R.id.collect_des2);//浏览过的书
            TextView price = (TextView) view.findViewById(R.id.collect_price);
            TextView add = (TextView) view.findViewById(R.id.collect_addcart);
            TextView del = (TextView) view.findViewById(R.id.collect_delcart);
            if (good != null) {
                name.setText(good.getGdsName());
                MoneyUtils.showSpannedPrice(price, MoneyUtils.GoodListPrice(good.getNowPrice()));
                GlideUtil.loadImg(getActivity(), good.getSkuMainPicUrl(), img);
                if (good.getGdsStatus().equals(GOOD_ON) && (good.getStockInfo()!=null && !good.getStockInfo().equals("无货")))
                {
                    add.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_round_button));
                }
                else if (good.getGdsStatus().equals(GOOD_ON) && (good.getStockInfo()!=null && good.getStockInfo().equals("无货")))
                {
                    add.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_round_button_gray));
                }
                else
                {
                    add.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_round_button_gray));
                }
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!good.getGdsStatus().equals(GOOD_ON)){
                            ToastUtil.show(getActivity(), "添加到购物车失败，商品已下架或删除");
                            return;
                        }
                        if (good.getGdsStatus().equals(GOOD_ON) && good.getStockInfo().equals("无货")){
                            ToastUtil.show(getActivity(), "该商品暂时无货，无法添加到购物车");
                            return;
                        }
                        addToCartSingle(good);
                    }
                });
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Gds010Req gds010Req = new Gds010Req();
                        gds010Req.setCollectId(good.getId());
                        gds010Req.setSkuId(good.getSkuId());
                        gds010Req.setIfCancel(true);
                        getJsonService().requestGds010(getActivity(), gds010Req, true, new JsonService.CallBack<Gds010Resp>() {
                            @Override
                            public void oncallback(Gds010Resp gds010Resp) {
                                Gds009Req req = new Gds009Req();
                                req.setPageSize(Constant.PAGE_SIZE);
                                index = 1;
                                req.setPageNumber(index);
                                getJsonService().requestGds009(getActivity(), req, true, new JsonService.CallBack<Gds009Resp>() {
                                    @Override
                                    public void oncallback(Gds009Resp gds009Resp) {
                                        if (0 == gds009Resp.getGdsCollectBaseInfos().size()) {
                                            setVisible(none);
                                            setGone(collectListView);
                                            return;
                                        }
                                        setGone(none);
                                        setVisible(collectListView);
                                        adapter.clear();
                                        for (int i = 0; i < gds009Resp.getGdsCollectBaseInfos().size(); i++) {
                                            GdsCollectBaseInfo good = gds009Resp.getGdsCollectBaseInfos().get(i);
                                            adapter.add(good);
                                        }
                                        ToastUtil.show(getActivity(), "取消收藏成功");
                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onErro(AppHeader header) {

                                    }
                                });


                            }

                            @Override
                            public void onErro(AppHeader header) {

                            }
                        });
                    }
                });
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GdsCollectBaseInfo good = (GdsCollectBaseInfo) adapter.getItem(position);
                        Bundle bundle = new Bundle();
                        if (good != null) {
                            bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(good.getGdsId()));
                            bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(good.getSkuId()));
                        }
                        launch(ShopDetailActivity.class, bundle);
                    }
                });
            }
        }
        private void addToCartSingle(GdsCollectBaseInfo good) {
            Ord001Req req = new Ord001Req();
            req.setShopId(good.getShopId());
            req.setCartType("01");

            List<Ord00101Req> ord00101Reqs = new ArrayList<>();
            Ord00101Req ord00101Req = new Ord00101Req();
            ord00101Req.setShopId(good.getShopId());
            ord00101Req.setGdsId(good.getGdsId());
            ord00101Req.setGdsName(good.getGdsName());
            ord00101Req.setGdsType(good.getGdsTypeId());
            ord00101Req.setOrderAmount(1L);
            ord00101Req.setSkuId(good.getSkuId());
            ord00101Req.setGroupType("0");
            ord00101Req.setGroupDetail(String.valueOf(good.getSkuId()));
            ord00101Req.setCategoryCode(good.getCatgCode());
            ord00101Reqs.add(ord00101Req);
            req.setOrd00101Reqs(ord00101Reqs);
            getJsonService().requestOrd001(getActivity(), req, true, new JsonService.CallBack<Ord001Resp>() {
                @Override
                public void oncallback(Ord001Resp ord001Resp) {
                    ToastUtil.showIconToast(getActivity(), "加入购物车成功");
                    getCartGoodsNum();
                }

                @Override
                public void onErro(AppHeader header) {
                }
            });
        }
    }

    private void getCartGoodsNum() {
        Ord018Req req = new Ord018Req();
        getJsonService().requestOrd018(getActivity(), req, false, new JsonService.CallBack<Ord018Resp>() {
            @Override
            public void oncallback(Ord018Resp ord018Resp) {
                if (ord018Resp != null) {
                    Intent intent = new Intent(MainActivity.CART_GOODS_NUM);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ord018Resp", ord018Resp);
                    intent.putExtras(bundle);
                    getActivity().sendBroadcast(intent);
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
