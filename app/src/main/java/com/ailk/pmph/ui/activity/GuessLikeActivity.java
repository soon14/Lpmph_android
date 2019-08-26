package com.ailk.pmph.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.req.Cms010Req;
import com.ai.ecp.app.req.Gds010Req;
import com.ai.ecp.app.resp.Cms010Resp;
import com.ai.ecp.app.resp.Gds010Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.adapter.LikeListAdapter;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 类注释:猜你喜欢
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/10/14 20:04
 */

public class GuessLikeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_guess_like_list)
    PullToRefreshRecyclerView mRecyclerView;

    private LikeListAdapter mAdapter;
    private Cms010Req mRequest;
    private int mPageNo = 2;
    private int mPageSize = 8;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_guess_like;
    }

    @Override
    public void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRequest = new Cms010Req();
        mAdapter = new LikeListAdapter(this, new ArrayList<Cms010Resp.Item>());
        mAdapter.clear();
        mRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        final GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
        mGridLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (mAdapter.isHeaderView(position) || mAdapter.isBottomView(position)) ? mGridLayoutManager.getSpanCount() : 1;
            }
        });
        getGuessLikeList();
        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                loadMoreData();
                mRecyclerView.onRefreshComplete();
            }
        });
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
                        Intent intent = new Intent(GuessLikeActivity.this, ShopDetailActivity.class);
                        if (gdsId != null) {
                            intent.putExtra(Constant.SHOP_GDS_ID, String.valueOf(gdsId));
                        }
                        if (skuId != null) {
                            intent.putExtra(Constant.SHOP_SKU_ID, String.valueOf(skuId));
                        }
                        startActivity(intent);
                    }
                });
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GuessLikeActivity.this, SearchResultActivity.class);
                        String catgCode = item.getCatgCode();
                        if (StringUtils.isNotEmpty(catgCode)) {
                            intent.putExtra(Constant.SHOP_CATG_CODE, catgCode);
                        }
                        startActivity(intent);
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
                            getJsonService().requestGds010(GuessLikeActivity.this, request, true, new JsonService.CallBack<Gds010Resp>() {
                                @Override
                                public void oncallback(Gds010Resp gds010Resp) {
                                    if (collect.isSelected()) {
                                        collect.setSelected(false);
                                        collect.setImageDrawable(ContextCompat.getDrawable(GuessLikeActivity.this, R.drawable.icon_like_collect));
                                        ToastUtil.show(GuessLikeActivity.this, "已取消收藏该商品");
                                    } else {
                                        collect.setImageDrawable(ContextCompat.getDrawable(GuessLikeActivity.this, R.drawable.icon_like_collected));
                                        collect.setSelected(true);
                                        ToastUtil.show(GuessLikeActivity.this, gds010Resp.getMsg());
                                    }
                                }

                                @Override
                                public void onErro(AppHeader header) {

                                }
                            });
                        } else {
                            DialogAnotherUtil.showCustomAlertDialogWithMessage(GuessLikeActivity.this, null,
                                    "您未登录，请先登录", "登录", "取消",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            DialogUtil.dismissDialog();
                                            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                                                startActivity(new Intent(GuessLikeActivity.this, LoginPmphActivity.class));
                                            } else {
                                                startActivity(new Intent(GuessLikeActivity.this, LoginActivity.class));
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

    @Override
    public void initData() {

    }

    private void getGuessLikeList() {
        mAdapter.clear();
        mPageNo = 2;
        mRequest = new Cms010Req();
        mRequest.setGuessPageNo(mPageNo++);
        mRequest.setGuessPageSize(mPageSize);
        getJsonService().requestCms010(this, mRequest, true, new JsonService.CallBack<Cms010Resp>() {
            @Override
            public void oncallback(Cms010Resp cms010Resp) {
                if (cms010Resp != null) {
                    List<Cms010Resp.Model> modelList = cms010Resp.getDatas();
                    if (modelList != null && modelList.size() != 0) {
                        List<Cms010Resp.Item> itemList = modelList.get(0).getItemList();
                        mAdapter.addAll(itemList);
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void loadMoreData() {
        mRequest = new Cms010Req();
        mRequest.setGuessPageNo(mPageNo++);
        mRequest.setGuessPageSize(mPageSize);
        getJsonService().requestCms010(this, mRequest, true, new JsonService.CallBack<Cms010Resp>() {
            @Override
            public void oncallback(Cms010Resp cms010Resp) {
                if (cms010Resp != null) {
                    List<Cms010Resp.Model> modelList = cms010Resp.getDatas();
                    if (modelList != null && modelList.size() != 0) {
                        List<Cms010Resp.Item> itemList = modelList.get(0).getItemList();
                        if (itemList.size() == 0) {
                            ToastUtil.showCenter(GuessLikeActivity.this, R.string.toast_load_more_msg);
                        } else {
                            mAdapter.addData(itemList);
                        }
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

}
