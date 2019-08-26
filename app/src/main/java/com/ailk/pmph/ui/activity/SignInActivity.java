package com.ailk.pmph.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ai.ecp.app.req.Cms001Req;
import com.ai.ecp.app.req.Ord112Req;
import com.ai.ecp.app.req.Pointmgds002Req;
import com.ai.ecp.app.req.Staff121Req;
import com.ai.ecp.app.req.Staff122Req;
import com.ai.ecp.app.resp.Cms001Resp;
import com.ai.ecp.app.resp.Ord11201Resp;
import com.ai.ecp.app.resp.Ord112Resp;
import com.ai.ecp.app.resp.Pointmgds002Resp;
import com.ai.ecp.app.resp.Staff121Resp;
import com.ai.ecp.app.resp.Staff122Resp;
import com.ai.ecp.app.resp.cms.AdvertiseRespVO;
import com.ai.ecp.app.resp.gds.PointGdsDetailBaseInfo;
import com.ai.ecp.app.resp.gds.PointGdsScoreExtRespInfo;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.integral.activity.InteShopDetailActivity;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.adapter.SignInGoodListAdapter;
import com.ailk.pmph.ui.adapter.SignInRecordListAdapter;
import com.ailk.pmph.ui.view.CustomListView;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.OrderStatus;
import com.ailk.pmph.utils.PromotionParseUtil;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:签到
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/10/6 10:24
 */

public class SignInActivity extends BaseActivity implements View.OnClickListener,
        SignInGoodListAdapter.RedirectToDetailInterface, SignInRecordListAdapter.RedirectToDetailInterface{

    @BindView(R.id.scrollView)              ScrollView mScrollView;
    @BindView(R.id.iv_back)                 ImageView mBackIv;
    @BindView(R.id.tv_sign_in)              TextView mSingInTv;
    @BindView(R.id.tv_desc1)                TextView mDescOneTv;
    @BindView(R.id.tv_desc2)                TextView mDescTwoTv;
    @BindView(R.id.iv_banner)               ImageView mBannerIv;
    @BindView(R.id.tv_mall_point)           TextView mMallPointTv;
    @BindView(R.id.mall_point_line)         View mMallPointLine;
    @BindView(R.id.tv_recode)               TextView mRecordTv;
    @BindView(R.id.recode_line)             View mRecordLine;
    @BindView(R.id.lv_good_list)            CustomListView mGoodsListView;
    @BindView(R.id.lv_recode_list)          CustomListView mRecodeListView;
    @BindView(R.id.iv_none)                 ImageView ivNone;
    private String mLinkUrl;
    private int pageNo = 1;
    private SpannableString spannableString;

    public static final String RECODE = "recode";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sign_in;
    }

    @Override
    public void initView() {
        if (AppContext.isLogin) {
            requestStaff122();
        } else {
            showLoginDialog(this);
        }
        requestCms001();
        requestPointGds002();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECODE);
        registerReceiver(receiver,intentFilter);
    }

    @Override
    public void initData() {

    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equals(intent.getAction(), RECODE)) {
                requestStaff122();
                requestOrd112();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    @OnClick({R.id.iv_back,R.id.tv_sign_in,R.id.iv_banner,R.id.tv_mall_point,R.id.tv_recode})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.tv_sign_in:
                if (AppContext.isLogin) {
                    requestStaff121();
                } else {
                    showLoginDialog(this);
                }
                break;

            case R.id.iv_banner:
                if (StringUtils.isNotEmpty(mLinkUrl)) {
                    PromotionParseUtil.parsePromotionUrl(SignInActivity.this, mLinkUrl, true, false, null);
                }
                break;

            case R.id.tv_mall_point:
                mScrollView.smoothScrollTo(0, 0);
                mMallPointTv.setTextColor(ContextCompat.getColor(this,R.color.orange_ff6a00));
                setVisible(mMallPointLine, mGoodsListView);
                mRecordTv.setTextColor(ContextCompat.getColor(this,R.color.gray_9b9b9b));
                setGone(mRecordLine, mRecodeListView, ivNone);
                break;

            case R.id.tv_recode:
                mScrollView.smoothScrollTo(0, 0);
                mRecordTv.setTextColor(ContextCompat.getColor(this,R.color.orange_ff6a00));
                setVisible(mRecordLine, mRecodeListView);
                mMallPointTv.setTextColor(ContextCompat.getColor(this,R.color.gray_9b9b9b));
                setGone(mMallPointLine, mGoodsListView);
                if (!AppContext.isLogin) {
                    showLoginDialog(this);
                } else {
                    requestOrd112();
                }
                break;
        }
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
    public void redirectToDetail(Ord11201Resp info) {
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

    /**
     * 判断是否已签到
     */
    private void requestStaff122() {
        Staff122Req req = new Staff122Req();
        getJsonService().requestStaff122(this, req, true, new JsonService.CallBack<Staff122Resp>() {
            @Override
            public void oncallback(Staff122Resp staff122Resp) {
                if (staff122Resp != null) {
                    Long score = staff122Resp.getScore();
                    Long signCnt = staff122Resp.getSignCnt();
                    if (score != null && score != 0) {
                        setVisible(mDescTwoTv);
                        mSingInTv.setText("已签到");
                        if (signCnt != null && signCnt != 0) {
                            if (signCnt < 10) {
                                String signCntText = "已连续签到 " + signCnt + " 天";
                                spannableString = new SpannableString(signCntText);
                                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                        signCntText.length() - 3, signCntText.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                mDescOneTv.setText(spannableString);
                            } else if (signCnt > 10 && signCnt < 100) {
                                String signCntText = "已连续签到 " + signCnt + " 天";
                                spannableString = new SpannableString(signCntText);
                                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                        signCntText.length() - 4, signCntText.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                mDescOneTv.setText(spannableString);
                            } else {
                                String signCntText = "已连续签到 " + signCnt + " 天";
                                spannableString = new SpannableString(signCntText);
                                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                        signCntText.length() - 5, signCntText.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                mDescOneTv.setText(spannableString);
                            }
                        } else {
                            String signText = "已连续签到 0 天";
                            spannableString = new SpannableString(signText);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                    signText.length() - 3, signText.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mDescOneTv.setText(spannableString);
                        }
                        if (score < 10) {
                            String text = "今日签到获得 " + score + " 积分";
                            spannableString = new SpannableString(text);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                    text.length() - 4, text.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mDescTwoTv.setText(spannableString);
                        } else {
                            String text = "今日签到获得 " + score + " 积分";
                            spannableString = new SpannableString(text);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                    text.length() - 5, text.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mDescTwoTv.setText(spannableString);
                        }
                    } else {
                        setGone(mDescTwoTv);
                        mSingInTv.setText("签到");
                        if (signCnt != null && signCnt != 0) {
                            if (signCnt < 10) {
                                String signText = "已连续签到 " + signCnt + " 天";
                                spannableString = new SpannableString(signText);
                                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                        signText.length() - 3, signText.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                mDescOneTv.setText(spannableString);
                            } else {
                                String signText = "已连续签到 " + signCnt + " 天";
                                spannableString = new SpannableString(signText);
                                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                        signText.length() - 4, signText.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                mDescOneTv.setText(spannableString);
                            }
                        } else {
                            String signText = "已连续签到 0 天";
                            spannableString = new SpannableString(signText);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                    signText.length() - 3, signText.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mDescOneTv.setText(spannableString);
                        }
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 获取广告图
     */
    private void requestCms001() {
        Cms001Req req = new Cms001Req();
        req.setPlaceId(1701L);
        getJsonService().requestCms001(this, req, false, new JsonService.CallBack<Cms001Resp>() {
            @Override
            public void oncallback(Cms001Resp cms001Resp) {
                if (cms001Resp != null) {
                    List<AdvertiseRespVO> list = cms001Resp.getAdvertiseList();
                    if (list != null && list.size() != 0) {
                        AdvertiseRespVO respVO = cms001Resp.getAdvertiseList().get(0);
                        if (StringUtils.isNotEmpty(respVO.getVfsUrl())) {
                            GlideUtil.loadImg(SignInActivity.this,respVO.getVfsUrl(),mBannerIv);
                        } else {
                            setGone(mBannerIv);
                        }
                        if (StringUtils.isNotEmpty(respVO.getLinkUrl())) {
                            mLinkUrl = respVO.getLinkUrl();
                        }
                    } else {
                        setGone(mBannerIv);
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 获取积分商品
     */
    private void requestPointGds002() {
        Pointmgds002Req req = new Pointmgds002Req();
        getInteJsonService().requestPointGds002(this, req, true, new InteJsonService.CallBack<Pointmgds002Resp>() {
            @Override
            public void oncallback(Pointmgds002Resp pointmgds002Resp) {
                if (pointmgds002Resp != null) {
                    List<PointGdsDetailBaseInfo> list = pointmgds002Resp.getRankGdsList();
                    if (list != null && list.size() != 0) {
                        SignInGoodListAdapter adapter = new SignInGoodListAdapter(SignInActivity.this, list);
                        adapter.setRedirectToDetailInterface(SignInActivity.this);
                        mGoodsListView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 获取我的兑换记录
     */
    private void requestOrd112() {
        Ord112Req req = new Ord112Req();
        req.setPageNo(pageNo);
        req.setPageSize(200);
        req.setSiteId(2L);
        req.setStatus(OrderStatus.TAKEOVER);
        getInteJsonService().requestOrd112(this, req, true, new InteJsonService.CallBack<Ord112Resp>() {
            @Override
            public void oncallback(Ord112Resp ord112Resp) {
                if (ord112Resp != null) {
                    List<Ord11201Resp> list = ord112Resp.getOrd11201Resps();
                    if (list != null && list.size() != 0) {
                        setGone(ivNone);
                        SignInRecordListAdapter adapter = new SignInRecordListAdapter(SignInActivity.this, list);
                        adapter.setRedirectToDetailInterface(SignInActivity.this);
                        mRecodeListView.setAdapter(adapter);
                    } else {
                        setVisible(ivNone);
                        setGone(mRecodeListView);
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 签到
     */
    private void requestStaff121() {
        Staff121Req req = new Staff121Req();
        getJsonService().requestStaff121(this, req, false, new JsonService.CallBack<Staff121Resp>() {
            @Override
            public void oncallback(Staff121Resp staff121Resp) {
                if (staff121Resp != null) {
                    Long score = staff121Resp.getScore();
                    Long signCnt = staff121Resp.getSignCnt();
                    setVisible(mDescTwoTv);
                    mSingInTv.setText("已签到");
                    if (signCnt != null && signCnt != 0) {
                        if (signCnt < 10) {
                            String signCntText = "已连续签到 " + signCnt + " 天";
                            spannableString = new SpannableString(signCntText);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                    signCntText.length() - 3, signCntText.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mDescOneTv.setText(spannableString);
                        } else if (signCnt > 10 && signCnt < 100) {
                            String signCntText = "已连续签到 " + signCnt + " 天";
                            spannableString = new SpannableString(signCntText);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                    signCntText.length() - 4, signCntText.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mDescOneTv.setText(spannableString);
                        } else {
                            String signCntText = "已连续签到 " + signCnt + " 天";
                            spannableString = new SpannableString(signCntText);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                    signCntText.length() - 5, signCntText.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mDescOneTv.setText(spannableString);
                        }
                    } else {
                        String signText = "已连续签到 0 天";
                        spannableString = new SpannableString(signText);
                        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                signText.length() - 3, signText.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mDescOneTv.setText(spannableString);
                    }
                    if (score != null && score != 0) {
                        if (score < 10) {
                            String text = "今日签到获得 " + score + " 积分";
                            spannableString = new SpannableString(text);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                    text.length() - 4, text.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mDescTwoTv.setText(spannableString);
                        } else {
                            String text = "今日签到获得 " + score + " 积分";
                            spannableString = new SpannableString(text);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignInActivity.this, R.color.orange_ff6a00)),
                                    text.length() - 5, text.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mDescTwoTv.setText(spannableString);
                        }
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

}
