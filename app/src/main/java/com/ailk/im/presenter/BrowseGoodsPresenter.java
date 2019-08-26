package com.ailk.im.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ai.ecp.app.req.IM003Req;
import com.ai.ecp.app.resp.IM003Resp;
import com.ai.ecp.app.resp.vo.IM00301VO;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.im.net.JsonMapFunc;
import com.ailk.im.net.NetCenter;
import com.ailk.im.ui.activity.BrowseGoodsActivity;
import com.ailk.im.ui.activity.MessageActivity;
import com.ailk.im.ui.adapter.BrowseGoodsAdapter;
import com.ailk.im.ui.adapter.CommonRecyclerAdapter;
import com.ailk.im.ui.dialog.BrowseDialog;
import com.ailk.imsdk.bean.message.body.GoodMessageBody;
import com.ailk.imsdk.rx.OnCompleteAction;
import com.ailk.imsdk.rx.OnErrorDialogAction;
import com.ailk.imsdk.rx.OnNextAction;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import rx.Observable;
import rx.functions.Func1;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.im.presenter
 * 作者: Chrizz
 * 时间: 2017/2/24
 */

public class BrowseGoodsPresenter extends BasePresenter implements CommonRecyclerAdapter.ItemClickListener, BrowseDialog.SendGdsMsgListener{

    private BrowseGoodsActivity mActivity;
    private XRecyclerView mRecyclerView;
    private RelativeLayout mEmptyLayout;
    private BrowseGoodsAdapter mAdapter;
    private int mPageNo = 1;
    private Long mShopId;

    public BrowseGoodsPresenter(BrowseGoodsActivity activity) {
        mActivity = activity;
    }

    public void initView(Long shopId) {
        mShopId = shopId;
        ImageView back = (ImageView) mActivity.findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });

        mEmptyLayout = (RelativeLayout) mActivity.findViewById(R.id.rl_empty_layout);
        mRecyclerView = (XRecyclerView) mActivity.findViewById(R.id.rv_browse_good_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        Drawable dividerDrawable = ContextCompat.getDrawable(mActivity, R.drawable.divider_sample);
        mRecyclerView.addItemDecoration((mRecyclerView.new DividerItemDecoration(dividerDrawable)));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.icon_font_downgrey);
        mAdapter = new BrowseGoodsAdapter(mActivity);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.refresh();

        requestBrowseGoods(true);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                requestBrowseGoods(false);
            }

            @Override
            public void onLoadMore() {
                requestMoreBrowseGoods();
            }
        });
    }

    private void requestBrowseGoods(final boolean isShowProgress) {
        mAdapter.clear();
        mPageNo = 1;
        IM003Req req = new IM003Req();
        req.setPageNumber(mPageNo++);
        req.setPageSize(Constant.PAGE_SIZE);
        req.setShopId(mShopId);
        if (isShowProgress) {
            DialogUtil.showCustomerProgressDialog(mActivity);
        }
        NetCenter.build(mActivity)
                .requestDefault(req, "im003")
                .map(new JsonMapFunc<AppBody, IM003Resp>())
                .filter(new Func1<IM003Resp, Boolean>() {
                    @Override
                    public Boolean call(IM003Resp im003Resp) {
                        if (im003Resp == null
                                || im003Resp.getPageResp() == null
                                || im003Resp.getPageResp().size() == 0) {
                            new Handler(mActivity.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    mRecyclerView.setEmptyView(mEmptyLayout);
                                }
                            });
                            return false;
                        }
                        return true;
                    }
                })
                .concatMap(new Func1<IM003Resp, Observable<IM00301VO>>() {
                    @Override
                    public Observable<IM00301VO> call(IM003Resp im003Resp) {
                        return Observable.from(im003Resp.getPageResp());
                    }
                })
                .subscribe(new OnNextAction<IM00301VO>() {
                    @Override
                    public void onNext(IM00301VO im00301VO) {
                        mAdapter.add(im00301VO);
                    }
                }, new OnErrorDialogAction() {
                    @Override
                    public void onError(Throwable throwable) {
                        if (isShowProgress) {
                            DialogUtil.dismissDialog();
                        }
                        mPageNo = 1;
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                    }

                    @Override
                    public Context getContext() {
                        return mActivity;
                    }
                }, new OnCompleteAction() {
                    @Override
                    public void onComplete() {
                        if (isShowProgress) {
                            DialogUtil.dismissDialog();
                        }
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                    }
                });
    }

    private void requestMoreBrowseGoods() {
        IM003Req req = new IM003Req();
        req.setPageNumber(mPageNo++);
        req.setPageSize(Constant.PAGE_SIZE);
        req.setShopId(mShopId);
        NetCenter.build(mActivity)
                .requestDefault(req, "im003")
                .map(new JsonMapFunc<AppBody, IM003Resp>())
                .filter(new Func1<IM003Resp, Boolean>() {
                    @Override
                    public Boolean call(IM003Resp im003Resp) {
                        if (im003Resp == null
                                || im003Resp.getPageResp() == null
                                || im003Resp.getPageResp().size() == 0) {
                            new Handler(mActivity.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.show(mActivity, R.string.toast_load_more_msg);
                                }
                            });
                            return false;
                        }
                        return true;
                    }
                })
                .subscribe(new OnNextAction<IM003Resp>() {
                    @Override
                    public void onNext(IM003Resp im003Resp) {
                        for (IM00301VO data : im003Resp.getPageResp()) {
                            mAdapter.add(data);
                        }
                    }
                }, new OnErrorDialogAction() {
                    @Override
                    public void onError(Throwable throwable) {
                        mPageNo--;
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public Context getContext() {
                        return mActivity;
                    }
                }, new OnCompleteAction() {
                    @Override
                    public void onComplete() {
                        mRecyclerView.loadMoreComplete();
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onItemClick(int position) {
        IM00301VO gdsInfo = mAdapter.getItem(position);
        BrowseDialog dialog = new BrowseDialog(mActivity, R.style.my_dialog_style);
        if (!TextUtils.isEmpty(gdsInfo.getImageUrl())) {
            dialog.setGdsImgUrl(gdsInfo.getImageUrl());
        }
        if (!TextUtils.isEmpty(gdsInfo.getGdsName())) {
            dialog.setGdsName(gdsInfo.getGdsName());
        }
        if (gdsInfo.getDefaultPrice() != null) {
            dialog.setGdsPrice(gdsInfo.getDefaultPrice());
        }
        if (!TextUtils.isEmpty(gdsInfo.getId())) {
            dialog.setGdsId(Long.parseLong(gdsInfo.getId()));
        }
        dialog.setListener(this);
        dialog.show();
    }

    @Override
    public void sendGdsMsg(GoodMessageBody goodMessageBody) {
        Intent intent = new Intent(MessageActivity.GDS_MSG);
        Bundle bundle = new Bundle();
        bundle.putSerializable("goodMessageBody", goodMessageBody);
        intent.putExtras(bundle);
        mActivity.sendBroadcast(intent);
        mActivity.finish();
    }

    @Override
    public void onHeaderClick() {

    }

    @Override
    public void onFooterClick() {

    }

    @Override
    public void onDestroy() {
        mActivity = null;
    }

}
