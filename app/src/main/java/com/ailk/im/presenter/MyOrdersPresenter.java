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

import com.ai.ecp.app.req.Ord012Req;
import com.ai.ecp.app.resp.Ord01201Resp;
import com.ai.ecp.app.resp.Ord012Resp;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.im.net.JsonMapFunc;
import com.ailk.im.net.NetCenter;
import com.ailk.im.ui.activity.MessageActivity;
import com.ailk.im.ui.activity.MyOrdersActivity;
import com.ailk.im.ui.adapter.CommonRecyclerAdapter;
import com.ailk.im.ui.adapter.MyOrdersAdapter;
import com.ailk.im.ui.dialog.OrderDialog;
import com.ailk.imsdk.bean.message.body.OrderMessageBody;
import com.ailk.imsdk.rx.OnCompleteAction;
import com.ailk.imsdk.rx.OnErrorDialogAction;
import com.ailk.imsdk.rx.OnNextAction;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.OrderStatus;
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

public class MyOrdersPresenter extends BasePresenter implements CommonRecyclerAdapter.ItemClickListener, OrderDialog.SendOrderMsgListener{

    private MyOrdersActivity mActivity;
    private XRecyclerView mRecyclerView;
    private RelativeLayout mEmptyLayout;
    private MyOrdersAdapter mAdapter;
    private int mPageNo = 1;
    private Long mShopId;

    public MyOrdersPresenter(MyOrdersActivity activity) {
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
        mRecyclerView = (XRecyclerView) mActivity.findViewById(R.id.rv_my_order_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        Drawable dividerDrawable = ContextCompat.getDrawable(mActivity, R.drawable.divider_sample);
        mRecyclerView.addItemDecoration((mRecyclerView.new DividerItemDecoration(dividerDrawable)));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.icon_font_downgrey);
        mAdapter = new MyOrdersAdapter(mActivity);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.refresh();

        requestMyOrders(true);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                requestMyOrders(false);
            }

            @Override
            public void onLoadMore() {
                requestMoreMyOrders();
            }
        });

    }

    private void requestMyOrders(final boolean isShowProgress) {
        mAdapter.clear();
        mPageNo = 1;
        Ord012Req req = new Ord012Req();
        req.setShopId(mShopId);
        req.setPageNo(mPageNo++);
        req.setPageSize(Constant.PAGE_SIZE);
        req.setSiteId(1L);
        req.setStatus(OrderStatus.ALL);
        if (isShowProgress) {
            DialogUtil.showCustomerProgressDialog(mActivity);
        }
        NetCenter.build(mActivity)
                .requestDefault(req, "ord012")
                .map(new JsonMapFunc<AppBody, Ord012Resp>())
                .filter(new Func1<Ord012Resp, Boolean>() {
                    @Override
                    public Boolean call(Ord012Resp ord012Resp) {
                        if (ord012Resp == null
                                || ord012Resp.getOrd01201Resps() == null
                                || ord012Resp.getOrd01201Resps().size() == 0) {
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
                .concatMap(new Func1<Ord012Resp, Observable<Ord01201Resp>>() {
                    @Override
                    public Observable<Ord01201Resp> call(Ord012Resp ord012Resp) {
                        return Observable.from(ord012Resp.getOrd01201Resps());
                    }
                })
                .subscribe(new OnNextAction<Ord01201Resp>() {
                    @Override
                    public void onNext(Ord01201Resp ord01201Resp) {
                        mAdapter.add(ord01201Resp);
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

    private void requestMoreMyOrders() {
        Ord012Req req = new Ord012Req();
        req.setShopId(mShopId);
        req.setPageNo(mPageNo++);
        req.setPageSize(Constant.PAGE_SIZE);
        req.setSiteId(1L);
        req.setStatus(OrderStatus.ALL);
        NetCenter.build(mActivity)
                .requestDefault(req, "ord012")
                .map(new JsonMapFunc<AppBody, Ord012Resp>())
                .filter(new Func1<Ord012Resp, Boolean>() {
                    @Override
                    public Boolean call(Ord012Resp ord012Resp) {
                        if (ord012Resp == null
                                || ord012Resp.getOrd01201Resps() == null
                                || ord012Resp.getOrd01201Resps().size() == 0) {
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
                .subscribe(new OnNextAction<Ord012Resp>() {
                    @Override
                    public void onNext(Ord012Resp ord012Resp) {
                        for (Ord01201Resp data : ord012Resp.getOrd01201Resps()) {
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
        Ord01201Resp orderInfo = mAdapter.getItem(position);
        OrderDialog dialog = new OrderDialog(mActivity, R.style.my_dialog_style);
        if (!TextUtils.isEmpty(orderInfo.getOrd01102Resps().get(0).getPicUrl())) {
            dialog.setOrderImgUrl(orderInfo.getOrd01102Resps().get(0).getPicUrl());
        }
        if (!TextUtils.isEmpty(orderInfo.getOrderId())) {
            dialog.setOrderCode(orderInfo.getOrderId());
        }
        if (orderInfo.getRealMoney() != null) {
            dialog.setOrderPrice(orderInfo.getRealMoney());
        }
        if (orderInfo.getOrderTime() != null) {
            dialog.setOrderTime(orderInfo.getOrderTime().getTime());
        }
        dialog.setListener(this);
        dialog.show();
    }

    @Override
    public void sendOrderMsg(OrderMessageBody orderMessageBody) {
        Intent intent = new Intent(MessageActivity.ORDER_MGS);
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderMessageBody", orderMessageBody);
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
