package com.ailk.pmph.ui.fragment.viewholder;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.req.Cms010Req;
import com.ai.ecp.app.req.Gds010Req;
import com.ai.ecp.app.resp.Cms010Resp;
import com.ai.ecp.app.resp.Cms011Resp;
import com.ai.ecp.app.resp.Gds010Resp;
import com.ai.ecp.app.resp.Ord010Resp;
import com.ai.ecp.app.resp.gds.GoodSearchResultVO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.activity.LoginActivity;
import com.ailk.pmph.ui.activity.LoginPmphActivity;
import com.ailk.pmph.ui.activity.SearchResultActivity;
import com.ailk.pmph.ui.activity.ShopDetailActivity;
import com.ailk.pmph.ui.adapter.LikeListAdapter;
import com.ailk.pmph.ui.view.CustomGridLayoutManager;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.fragment.viewholder
 * 作者: Chrizz
 * 时间: 2016/10/11 10:30
 */
@HomeViewType(ViewType=410)
public class HomeLikeHolder extends HomeViewHolder {

    private RecyclerView mRecyclerView;
    private LikeListAdapter mAdapter;
    private List<Cms010Resp.Item> mList;

    public HomeLikeHolder(Activity activity, LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity, inflater, viewGroup, R.layout.home_like_layout, R.dimen.home_modey_body_width, R.dimen.home_model_type13_height);
    }

    @Override
    protected void initBodyView(ViewGroup viewGroup) {
        mRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.rv_like_list);
    }

    @Override
    public void initData(Cms010Resp.Model model) {
        super.initData(model);
        mList = model.getItemList();
        final CustomGridLayoutManager mGridLayoutManager = new CustomGridLayoutManager(mConText, 2);
        mGridLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (mAdapter.isHeaderView(position) || mAdapter.isBottomView(position)) ? mGridLayoutManager.getSpanCount() : 1;
            }
        });
        mAdapter = new LikeListAdapter(mConText, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemViewClickListener(new LikeListAdapter.ItemViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_gds_img);
                TextView textView = (TextView) view.findViewById(R.id.tv_similar);
                final ImageView collect = (ImageView) view.findViewById(R.id.iv_collect);
                final Cms010Resp.Item item = mAdapter.getItem(position - 1);
                imageView.setTag(item);
                textView.setTag(item);
                collect.setTag(item);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Long gdsId = item.getGdsId();
                        Long skuId = item.getSkuId();
                        Intent intent = new Intent(mConText, ShopDetailActivity.class);
                        if (gdsId != null) {
                            intent.putExtra(Constant.SHOP_GDS_ID, String.valueOf(gdsId));
                        }
                        if (skuId != null) {
                            intent.putExtra(Constant.SHOP_SKU_ID, String.valueOf(skuId));
                        }
                        mConText.startActivity(intent);
                    }
                });
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mConText, SearchResultActivity.class);
                        String catgCode = item.getCatgCode();
                        if (StringUtils.isNotEmpty(catgCode)) {
                            intent.putExtra(Constant.SHOP_CATG_CODE, catgCode);
                        }
                        mConText.startActivity(intent);
                    }
                });
                collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (AppContext.isLogin) {
                            Long collectId = item.getCollectId();
                            Long skuId = item.getSkuId();
                            Gds010Req request = new Gds010Req();
                            request.setCollectId(collectId);
                            if (skuId != null) {
                                request.setSkuId(skuId);
                            }
                            if (!collect.isSelected()) {
                                request.setIfCancel(true);
                            } else {
                                request.setIfCancel(false);
                            }
                            JsonService jsonService = new JsonService(mConText);
                            jsonService.requestGds010(mConText, request, true, new JsonService.CallBack<Gds010Resp>() {
                                @Override
                                public void oncallback(Gds010Resp gds010Resp) {
                                    if (collect.isSelected()) {
                                        collect.setSelected(false);
                                        collect.setImageDrawable(ContextCompat.getDrawable(mConText, R.drawable.icon_like_collect));
                                        ToastUtil.show(mConText, "已取消收藏该商品");
                                    } else {
                                        collect.setImageDrawable(ContextCompat.getDrawable(mConText, R.drawable.icon_like_collected));
                                        collect.setSelected(true);
                                        ToastUtil.show(mConText, gds010Resp.getMsg());
                                    }
                                }

                                @Override
                                public void onErro(AppHeader header) {

                                }
                            });
                        } else {
                            DialogAnotherUtil.showCustomAlertDialogWithMessage(mConText, null,
                                    "您未登录，请先登录", "登录", "取消",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            DialogUtil.dismissDialog();
                                            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                                                mConText.startActivity(new Intent(mConText, LoginPmphActivity.class));
                                            } else {
                                                mConText.startActivity(new Intent(mConText, LoginActivity.class));
                                            }
                                        }
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            DialogUtil.dismissDialog();
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }

}
