package com.ailk.integral.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Ord10101Req;
import com.ai.ecp.app.req.Ord101Req;
import com.ai.ecp.app.req.Ord111Req;
import com.ai.ecp.app.req.Pointmgds001Req;
import com.ai.ecp.app.resp.Ord101Resp;
import com.ai.ecp.app.resp.Ord111Resp;
import com.ai.ecp.app.resp.Pointmgds001Resp;
import com.ai.ecp.app.resp.gds.PointGdsDetailBaseInfo;
import com.ai.ecp.app.resp.gds.PointGdsScoreExtRespInfo;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.integral.fragment.InteCartFragment;
import com.ailk.integral.widget.CheckScoreDialog;
import com.ailk.jazzyviewpager.JazzyViewPager;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.activity.LoginActivity;
import com.ailk.pmph.ui.activity.LoginPmphActivity;
import com.ailk.pmph.ui.activity.MainActivity;
import com.ailk.pmph.ui.activity.MessageActivity;
import com.ailk.pmph.ui.fragment.ShopCartFragment;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.view.FixedSpeedScroller;
import com.ailk.tool.GlideUtil;
import com.viewpagerindicator.CirclePageIndicator;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.act
 * 作者: Chrizz
 * 时间: 2016/5/16 19:31
 */
public class InteShopDetailActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)                   ImageView ivBack;
    @BindView(R.id.ll_cart)                   LinearLayout llCartLayout;
    @BindView(R.id.rl_main_pic_layout)        RelativeLayout rlPicLayout;
    @BindView(R.id.mainpic_viewpager)         JazzyViewPager mainpicViewpager;
    @BindView(R.id.tv_gds_name)               TextView tvGdsName;
    @BindView(R.id.tv_score)                  TextView tvScore;
    @BindView(R.id.tv_guide_price)            TextView tvGuidePrice;
    @BindView(R.id.tv_gds_info)               TextView tvGdsInfo;
    @BindView(R.id.tv_gds_status)             TextView tvStockStatus;
    @BindView(R.id.tv_check_score)            TextView tvCheckScore;
    @BindView(R.id.tv_reduce)                 TextView tvReduce;
    @BindView(R.id.tv_num)                    TextView tvNum;
    @BindView(R.id.tv_add)                    TextView tvAdd;
    @BindView(R.id.rl_add_cart)               RelativeLayout rlAddCart;
    @BindView(R.id.rl_no_add_cart)            RelativeLayout rlNoAddCart;
    @BindView(R.id.tv_exchange)               TextView tvExchange;
    @BindView(R.id.tv_no_exchange)            TextView tvNoExchange;
    @BindView(R.id.wb_shop_detail)            WebView wbShopDetail;
    @BindView(R.id.ll_no_desc_layout)         LinearLayout llNoDescLayout;
    @BindView(R.id.tv_cart_num)               TextView tvCartNum;

    private Pointmgds001Resp mData;
    private PointGdsDetailBaseInfo gdsDetail;
    private OnScrollListener onScrollListener;
    private Long scoreTypeId;
    public static final String REFRESH_PRICE = "refresh_price";
    private TimeTask timeTask;
    private boolean taskAlive = true;
    private boolean firstStart = true;
    private Handler mHandler;
    private MainpicAdapter mainpicAdapter;
    private SpannableString spannableString;

    @Override
    protected int getContentViewId() {
        return R.layout.inte_activity_shop_detail;
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equals(REFRESH_PRICE, intent.getAction())) {
                Bundle bundle = intent.getExtras();
                PointGdsScoreExtRespInfo score = (PointGdsScoreExtRespInfo) bundle.get("score");
                if (score != null) {
                    scoreTypeId = score.getId();
                    Long detailPrice = score.getPrice();
                    Long detailScore = score.getScore();
                    if ((detailPrice==null || detailPrice==0) && (detailScore==null || detailScore==0)) {
                        tvCheckScore.setText("0积分+0元");
                    } else if ((detailScore==null || detailScore==0) && (detailPrice != null && detailPrice!=0)) {
                        String priceStr = StringUtils.remove(MoneyUtils.GoodListPrice(detailPrice)+"元","￥");
                        spannableString = new SpannableString(priceStr);
                        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(InteShopDetailActivity.this, R.color.orange_ff6a00)),0,priceStr.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else {
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(InteShopDetailActivity.this, R.color.red)),0,priceStr.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        tvCheckScore.setText(spannableString);
                    } else if ((detailPrice != null && detailPrice!=0) && (detailScore != null && detailScore != 0)) {
                        String strScore = detailScore+"积分+";
                        String strPrice = StringUtils.remove(MoneyUtils.GoodListPrice(detailPrice)+"元","￥");
                        String str = strScore+strPrice;
                        spannableString = new SpannableString(str);
                        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(InteShopDetailActivity.this, R.color.orange_ff6a00)),0,str.length()-strPrice.length()-3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(InteShopDetailActivity.this, R.color.orange_ff6a00)),str.length()-strPrice.length(),str.length()-1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else {
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(InteShopDetailActivity.this, R.color.red)),0,str.length()-strPrice.length()-3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(InteShopDetailActivity.this, R.color.red)),str.length()-strPrice.length(),str.length()-1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        tvCheckScore.setText(spannableString);
                    } else if ((detailPrice==null || detailPrice==0) && (detailScore != null && detailScore!=0)) {
                        String scoreStr = detailScore+"积分";
                        spannableString = new SpannableString(scoreStr);
                        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(InteShopDetailActivity.this, R.color.orange_ff6a00)),0,scoreStr.length()-2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else {
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(InteShopDetailActivity.this, R.color.red)),0,scoreStr.length()-2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        tvCheckScore.setText(spannableString);
                    }
                }
            }
        }
    };

    public void initView(){
        String gdsId = getIntent().getStringExtra("gdsId");
        String skuId = getIntent().getStringExtra("skuId");
        String adid = getIntent().getStringExtra("adid");
        String linkType = getIntent().getStringExtra("linktype");
        Pointmgds001Req req = new Pointmgds001Req();
        if (StringUtils.isNotEmpty(gdsId)) {
            req.setGdsId(Long.parseLong(gdsId));
        }
        if (StringUtils.isNotEmpty(skuId)) {
            req.setSkuId(Long.parseLong(skuId));
        }
        if (StringUtils.isNotEmpty(adid)) {
            HashMap<String, Object> paramsMap = getParamsMap();
            paramsMap.put("adid", adid);
            paramsMap.put("linktype", linkType);
        }
        getInteJsonService().requestPointGds001(this, req, true, new InteJsonService.CallBack<Pointmgds001Resp>() {
            @Override
            public void oncallback(Pointmgds001Resp pointmgds001Resp) {
                mData = pointmgds001Resp;
                if (mData != null) {
                    gdsDetail = mData.getGdsDetailBaseInfo();
                    init();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });

    }

    @Override
    public void initData() {

    }

    private void init(){
        if (AppContext.isLogin) {
            getCartGoodsNum();
        }
        initPic();
        tvGdsName.setText(gdsDetail.getGdsName());
        tvGuidePrice.setText(MoneyUtils.GoodListPrice(gdsDetail.getGuidePrice()));
        tvGuidePrice.setPaintFlags(tvGuidePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        if (mData.getRemainScore()==null) {
            tvScore.setText("0");
        } else {
            tvScore.setText(String.valueOf(mData.getRemainScore()));
        }
        tvStockStatus.setText(gdsDetail.getStockStatusDesc());
        if (StringUtils.equals("22", gdsDetail.getGdsStatus()) || StringUtils.equals("99", gdsDetail.getGdsStatus())) {
            tvGdsInfo.setText("该商品已下架");
            setGone(tvStockStatus,rlAddCart,tvExchange);
            setVisible(rlNoAddCart,tvNoExchange);
            rlNoAddCart.setClickable(false);
            tvNoExchange.setClickable(false);
        } else {
            if (StringUtils.equals("无货", gdsDetail.getStockStatusDesc())) {
                setGone(rlAddCart,tvExchange);
                setVisible(rlNoAddCart,tvNoExchange);
                rlNoAddCart.setClickable(false);
                tvNoExchange.setClickable(false);
            } else {
                setGone(rlNoAddCart,tvNoExchange);
                setVisible(rlAddCart,tvExchange);
            }
        }
        if (gdsDetail.getScores().size() > 0) {
            for (int i = 0; i < gdsDetail.getScores().size(); i++) {
                PointGdsScoreExtRespInfo score = gdsDetail.getScores().get(i);
                if (StringUtils.equals(score.getIfDefault(),"1")) {
                    scoreTypeId = score.getId();
                    Long detailPrice = score.getPrice();
                    Long detailScore = score.getScore();
                    if ((detailPrice==null || detailPrice==0) && (detailScore==null || detailScore==0)) {
                        tvCheckScore.setText("0积分+0元");
                    } else if ((detailScore==null || detailScore==0) && (detailPrice != null && detailPrice!=0)) {
                        String priceStr = StringUtils.remove(MoneyUtils.GoodListPrice(detailPrice)+"元","￥");
                        spannableString = new SpannableString(priceStr);
                        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.orange_ff6a00)),0,priceStr.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else {
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.red)),0,priceStr.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        tvCheckScore.setText(spannableString);
                    } else if ((detailPrice != null && detailPrice!=0) && (detailScore != null && detailScore != 0)) {
                        String strScore = detailScore+"积分+";
                        String strPrice = StringUtils.remove(MoneyUtils.GoodListPrice(detailPrice)+"元","￥");
                        String str = strScore+strPrice;
                        spannableString = new SpannableString(str);
                        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.orange_ff6a00)),0,str.length()-strPrice.length()-3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.orange_ff6a00)),str.length()-strPrice.length(),str.length()-1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else {
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.red)),0,str.length()-strPrice.length()-3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.red)),str.length()-strPrice.length(),str.length()-1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        tvCheckScore.setText(spannableString);
                    } else if ((detailPrice==null || detailPrice==0) && (detailScore != null && detailScore!=0)) {
                        String scoreStr = detailScore+"积分";
                        spannableString = new SpannableString(scoreStr);
                        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.orange_ff6a00)),0,scoreStr.length()-2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else {
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.red)),0,scoreStr.length()-2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        tvCheckScore.setText(spannableString);
                    }
                }
            }
        } else {
            tvCheckScore.setText("无");
            tvCheckScore.setClickable(false);
        }
        initGdsDesc();
        timeTask = new TimeTask();
        timeTask.execute();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REFRESH_PRICE);
        registerReceiver(receiver, intentFilter);
    }

    private void initPic(){
        createScroller(mainpicViewpager);
        mainpicViewpager.setTransitionEffect(JazzyViewPager.TransitionEffect.Accordion);
        mainpicAdapter = new MainpicAdapter();
        mainpicViewpager.setAdapter(mainpicAdapter);
        createPageIndicator2();
        mainpicAdapter.notifyDataSetChanged();
    }

    @SuppressLint({ "NewApi" })
    private void initGdsDesc(){
        String mUrl = gdsDetail.getGdsDesc();
        if (StringUtils.isEmpty(mUrl)) {
            setGone(wbShopDetail);
            setVisible(llNoDescLayout);
        }
        else {
            setVisible(wbShopDetail);
            setGone(llNoDescLayout);

            WebSettings webSettings = wbShopDetail.getSettings();
            webSettings.setTextZoom(webSettings.getTextZoom()+130);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setUseWideViewPort(true);//关键点

            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            webSettings.setDisplayZoomControls(false);
            webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
            webSettings.setAllowFileAccess(true); // 允许访问文件
            webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
            webSettings.setSupportZoom(true); // 支持缩放

            webSettings.setLoadWithOverviewMode(true);

            int screenDensity = getResources().getDisplayMetrics().densityDpi ;
            WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM ;
            switch (screenDensity){
                case DisplayMetrics.DENSITY_LOW :
                    zoomDensity = WebSettings.ZoomDensity.CLOSE;
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    zoomDensity = WebSettings.ZoomDensity.FAR;
                    break ;
            }
            webSettings.setDefaultZoom(zoomDensity);
            wbShopDetail.loadUrl(mUrl);
        }

    }

    @Override
    @OnClick({R.id.iv_back,R.id.ll_cart,R.id.tv_check_score,R.id.tv_reduce,R.id.tv_add,R.id.rl_add_cart,R.id.tv_exchange})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ll_cart:
                if (!AppContext.isLogin) {
                    DialogAnotherUtil.showCustomAlertDialogWithMessage(this, null,
                            "您未登录，请先登录", "登录", "取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DialogUtil.dismissDialog();
                                    if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                                        launch(LoginPmphActivity.class);
                                    } else {
                                        launch(LoginActivity.class);
                                    }
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sendBroadcast(new Intent(InteCartFragment.REFRESH_INTE_CART));
                                    DialogUtil.dismissDialog();
                                }
                            });
                } else {
                    launch(InteCartActivity.class);
                }
                break;
            case R.id.tv_check_score:
                CheckScoreDialog dialog = new CheckScoreDialog(this, R.style.coupon_dialog);
                dialog.setScoreTypeId(scoreTypeId);
                dialog.setScores(gdsDetail.getScores());
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
            case R.id.tv_reduce:
                reduceNum(tvNum, gdsDetail);
                break;
            case R.id.tv_add:
                addNum(tvNum, gdsDetail);
                break;
            case R.id.rl_add_cart:
                if (!AppContext.isLogin) {
                    showLoginDialog(this);
                } else {
                    requestAddCart(true);
                }
                break;
            case R.id.tv_exchange:
                if (!AppContext.isLogin) {
                    showLoginDialog(this);
                } else {
                    requestAddCart(true);
                    launch(InteCartActivity.class);
                }
                break;
        }
    }

    private void reduceNum(TextView tvNum,PointGdsDetailBaseInfo gdsDetail){
        Long currentNum = Long.parseLong(tvNum.getText().toString());
        Long realAmount = gdsDetail.getGdsSkuBaseInfo().getRealAmount();
        Long stockLackNum = gdsDetail.getStockLackNum();
        if (currentNum > (realAmount-stockLackNum)) {
            ToastUtil.showCenter(this, "购买数量超过上限!");
            return;
        }
        if (currentNum==1)
            return;
        currentNum--;
        tvNum.setText(String.valueOf(currentNum));
    }

    private void addNum(TextView tvNum,PointGdsDetailBaseInfo gdsDetail){
        Long currentNum = Long.parseLong(tvNum.getText().toString());
        Long realAmount = gdsDetail.getGdsSkuBaseInfo().getRealAmount();
        Long stockLackNum = gdsDetail.getStockLackNum();
        currentNum++;
        if (currentNum > (realAmount-stockLackNum)) {
            ToastUtil.showCenter(this, "购买数量超过上限!");
            return;
        }
        tvNum.setText(String.valueOf(currentNum));
    }

    private void requestAddCart(boolean isShowProgress){
        Ord101Req req = new Ord101Req();
        req.setCartType("01");
        req.setPromId(-1L);
        req.setShopId(gdsDetail.getShopId());
        List<Ord10101Req> ord10101Reqs = new ArrayList<>();
        Ord10101Req request = new Ord10101Req();
        request.setShopId(gdsDetail.getShopId());
        request.setGdsId(gdsDetail.getGdsSkuBaseInfo().getGdsId());
        request.setGdsName(gdsDetail.getGdsSkuBaseInfo().getGdsName());
        request.setSkuId(gdsDetail.getGdsSkuBaseInfo().getId());
        request.setSkuInfo(gdsDetail.getGdsSkuBaseInfo().getSkuProps());
        request.setGroupType("0");
        request.setGroupDetail(String.valueOf(gdsDetail.getGdsSkuBaseInfo().getId()));
        request.setGdsType(gdsDetail.getGdsSkuBaseInfo().getGdsTypeId());
        request.setOrderAmount(Long.parseLong(tvNum.getText().toString()));
        request.setCategoryCode(gdsDetail.getMainCatgs());
        request.setScoreTypeId(scoreTypeId);
        request.setPrnFlag("0");
        request.setPromId(-1L);
        ord10101Reqs.add(request);
        req.setOrd10101Reqs(ord10101Reqs);
        getInteJsonService().requestOrd101(this, req, isShowProgress, new InteJsonService.CallBack<Ord101Resp>() {
            @Override
            public void oncallback(Ord101Resp ord101Resp) {
                ToastUtil.showIconToast(InteShopDetailActivity.this, "加入购物车成功");
                getCartGoodsNum();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void getCartGoodsNum() {
        Ord111Req req = new Ord111Req();
        getInteJsonService().requestOrd111(this, req, false, new InteJsonService.CallBack<Ord111Resp>() {
            @Override
            public void oncallback(Ord111Resp ord111Resp) {
                if (ord111Resp != null) {
                    Long cartItemNum = ord111Resp.getOrdCartItemNum();
                    if (cartItemNum != 0) {
                        setVisible(tvCartNum);
                        if (cartItemNum > 99) {
                            tvCartNum.setText("99+");
                        } else {
                            tvCartNum.setText(String.valueOf(cartItemNum));
                        }
                    } else {
                        setGone(tvCartNum);
                    }
                    Intent intent = new Intent(InteMainActivity.INTE_CART_GOODS_NUM);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ord111Resp", ord111Resp);
                    intent.putExtras(bundle);
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    public interface OnScrollListener {
        void onScrolling(boolean isScrolling);
    }

    private class MainpicAdapter extends PagerAdapter {

        private SparseArray<ImageView> mListViews;

        public MainpicAdapter() {
            this.mListViews = new SparseArray<>();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mListViews.get(position).setImageBitmap(null);
            container.removeView(mListViews.get(position));//删除页卡
            mListViews.remove(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = mListViews.get(position);
            if (imageView == null) {
                imageView = (ImageView) LayoutInflater.from(InteShopDetailActivity.this).inflate(R.layout.maskable_image_view, null, false);
                mListViews.put(position, imageView);
            }
            GlideUtil.loadImg(InteShopDetailActivity.this, gdsDetail.getImageUrlList().get(position), imageView);
            container.addView(imageView);//添加页卡
            return imageView;
        }

        @Override
        public int getCount() {
            if ((null != gdsDetail) || (null != gdsDetail.getImageUrlList())) {
                return gdsDetail.getImageUrlList().size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private void createScroller(ViewPager viewPager) {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Interpolator sInterpolator = new AccelerateInterpolator();
            FixedSpeedScroller scroller = new FixedSpeedScroller(
                    viewPager.getContext(), sInterpolator);
            scroller.setFixedDuration(200);
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    private void createPageIndicator2() {
        CirclePageIndicator indicator = (CirclePageIndicator) rlPicLayout.findViewById(R.id.mainpic_indicator);
        if (mainpicAdapter.getCount()==1) {
            indicator.setVisibility(View.GONE);
        } else {
            indicator.setViewPager(mainpicViewpager);
            final float density = rlPicLayout.getResources().getDisplayMetrics().density;
            indicator.setRadius(4 * density);
            indicator.setPageColor(0xFFFFFFFF);
            indicator.setFillColor(0xFFFF7700);
            indicator.setStrokeWidth(0);

            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (onScrollListener != null) {
                        if (state == ViewPager.SCROLL_STATE_IDLE) {
                            onScrollListener.onScrolling(false);
                        } else if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                            onScrollListener.onScrolling(true);
                        } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
                            onScrollListener.onScrolling(true);
                        }
                    }
                }
            });
        }
    }

    class TimeTask extends AsyncTask<Void, Void, Void> {

        private boolean suspend = false;

        private String control = ""; // 只是需要一个对象而已，这个对象没有实际意义

        public boolean isSuspend() {
            return this.suspend;
        }

        public void setSuspend(boolean suspend) {
            LogUtil.e("suspend = " + this.suspend + " --> params = " + suspend);
            if (this.suspend == suspend) {
                return;
            }
            this.suspend = suspend;
            if (!this.suspend) {
                synchronized (control) {
                    control.notifyAll();
                }
            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            while (taskAlive) {
                synchronized (control) {
                    if (suspend) {
                        try {
                            control.wait();
                        } catch (InterruptedException e) {
                            LogUtil.e(e);
                        }
                    }
                }
                try {
                    Thread.sleep(3000);
                    publishProgress();
                } catch (InterruptedException e) {
                    LogUtil.e(e);
                }

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... params) {
            showNextpage();
        }

    }

    private void showNextpage() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentPager = mainpicViewpager.getCurrentItem();
                currentPager++;
                if (mainpicAdapter != null && currentPager == mainpicAdapter.getCount()) {
                    currentPager = 0;
                }
                mainpicViewpager.setCurrentItem(currentPager, true);
            }
        }, 300);

    }

    @Override
    public void onResume() {
        super.onResume();
        getCartGoodsNum();
        if (!firstStart) {
            showNextpage();
            setSuspend(false);
        } else {
            firstStart = false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        setSuspend(true);
    }

    public void setSuspend(boolean b) {
        if(timeTask!=null) {
            timeTask.setSuspend(b);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        if(timeTask!=null) {
            taskAlive = false;
            timeTask.cancel(true);
        }
    }

}
