package com.ailk.pmph.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Gds001Req;
import com.ai.ecp.app.req.Gds002Req;
import com.ai.ecp.app.req.Gds003Req;
import com.ai.ecp.app.req.Gds004Req;
import com.ai.ecp.app.req.Gds005Req;
import com.ai.ecp.app.req.Gds008Req;
import com.ai.ecp.app.req.Gds010Req;
import com.ai.ecp.app.req.IM001Req;
import com.ai.ecp.app.req.Ord00101Req;
import com.ai.ecp.app.req.Ord001Req;
import com.ai.ecp.app.req.Ord018Req;
import com.ai.ecp.app.req.gds.GdsSku2PropPropIdxBaseInfo;
import com.ai.ecp.app.req.gds.SearchPropReqInfo;
import com.ai.ecp.app.resp.Gds001Resp;
import com.ai.ecp.app.resp.Gds002Resp;
import com.ai.ecp.app.resp.Gds003Resp;
import com.ai.ecp.app.resp.Gds004Resp;
import com.ai.ecp.app.resp.Gds005Resp;
import com.ai.ecp.app.resp.Gds008Resp;
import com.ai.ecp.app.resp.Gds010Resp;
import com.ai.ecp.app.resp.IM001Resp;
import com.ai.ecp.app.resp.Ord001Resp;
import com.ai.ecp.app.resp.Ord018Resp;
import com.ai.ecp.app.resp.gds.GdsDetailBaseInfo;
import com.ai.ecp.app.resp.gds.GdsPromMatchBaseInfo;
import com.ai.ecp.app.resp.gds.GdsPromMatchSkuBaseInfo;
import com.ai.ecp.app.resp.gds.GdsPropBaseInfo;
import com.ai.ecp.app.resp.gds.GdsPropValueBaseInfo;
import com.ai.ecp.app.resp.gds.GdsSeckillDiscountDTO;
import com.ai.ecp.app.resp.gds.GdsSkuBaseInfo;
import com.ai.ecp.app.resp.gds.GdscatgsCodeAndNameVO;
import com.ai.ecp.app.resp.gds.PromListBaseInfo;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.im.net.NetCenter;
import com.ailk.im.ui.activity.MessageActivity;
import com.ailk.jazzyviewpager.JazzyViewPager;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.countdownview.MainDownTimerView;
import com.ailk.pmph.thirdstore.activity.StoreActivity;
import com.ailk.pmph.ui.activity.LoginActivity;
import com.ailk.pmph.ui.activity.LoginPmphActivity;
import com.ailk.pmph.ui.activity.MainActivity;
import com.ailk.pmph.ui.activity.SearchResultActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DateUtils;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.ListViewAdapter;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.activity.ShopCartActivity;
import com.ailk.pmph.ui.activity.ShopDetailActivity;
import com.ailk.pmph.ui.view.CustomGridView;
import com.ailk.pmph.ui.view.FixedSpeedScroller;
import com.ailk.tool.GlideUtil;
import com.bigkoo.alertview.AlertView;
import com.viewpagerindicator.CirclePageIndicator;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ailk.pmph.ui.fragment
 * 作者: Chrizz
 * 时间: 2016/3/16 20:21
 * EDIT:王可 2016年03月23日
 */
public class ShopFragment extends BaseFragment {

    @BindView(R.id.good_name)                     TextView goodName;
    @BindView(R.id.good_desc)                     TextView goodDesc;
    @BindView(R.id.rl_price_layout)               RelativeLayout rlPriceLayout;
    @BindView(R.id.good_exist)                    TextView goodExist;
    @BindView(R.id.good_price)                    TextView goodPrice;
    @BindView(R.id.good_originprice)              TextView goodOriginprice;
    @BindView(R.id.good_share)                    ImageView goodShare;
    @BindView(R.id.good_collect)                  ImageView goodCollect;
    @BindView(R.id.good_promotion)                TextView goodPromotion;
    @BindView(R.id.good_promotion_label)          TextView goodPromotionLabel;
    @BindView(R.id.good_promotion_des)            TextView goodPromotionDes;
    @BindView(R.id.good_promotion_label1)         TextView goodPromotionLabel1;
    @BindView(R.id.good_promotion_des1)           TextView goodPromotionDes1;
    @BindView(R.id.rl_sale)                       RelativeLayout rlSale;
    @BindView(R.id.rl_sale1)                      RelativeLayout rlSale1;
    @BindView(R.id.good_normal)                   TextView goodNormal;
    @BindView(R.id.good_normal_des)               TextView goodNormalDes;
    @BindView(R.id.good_layout_normal)            RelativeLayout goodLayoutNormal;
    @BindView(R.id.iv_head)                       CircleImageView ivHead;
    @BindView(R.id.tv_username)                   TextView tvUsername;
    @BindView(R.id.tv_content)                    TextView tvContent;
    @BindView(R.id.iv_head1)                      CircleImageView ivHead1;
    @BindView(R.id.tv_username1)                  TextView tvUsername1;
    @BindView(R.id.tv_content1)                   TextView tvContent1;
    @BindView(R.id.iv_head2)                      CircleImageView ivHead2;
    @BindView(R.id.tv_username2)                  TextView tvUsername2;
    @BindView(R.id.tv_content2)                   TextView tvContent2;

    @BindView(R.id.comment_count_layout)          LinearLayout commentCountLayout;
    @BindView(R.id.tv_allcomment)                 TextView tvAllComment;
    @BindView(R.id.promotion_more)                ImageView promotionMore;
    @BindView(R.id.normal_more)                   ImageView normalMore;

    //=====================作者、电子书、手机专享价===================//
    @BindView(R.id.author_layout)                  RelativeLayout authorLayout;
    @BindView(R.id.tv_author)                      TextView tvAuthor;
    @BindView(R.id.tv_more)                        TextView tvMore;
    @BindView(R.id.good_type_layout)               RelativeLayout goodTypeLayout;
    @BindView(R.id.tv_good_type)                   TextView tvGoodType;
    @BindView(R.id.tv_good_tpye_price)             TextView tvGoodTypePrice;
    @BindView(R.id.tv_check)                       TextView tvCheck;
    @BindView(R.id.tv_phone_price)                 TextView tvPhonePrice;
    @BindView(R.id.tv_phone_price_desc)            TextView tvPhonePriceDesc;

    //=====================ISBN、出版日期、分类路径===================//
    @BindView(R.id.isbn_layout)                     RelativeLayout isbnLayout;
    @BindView(R.id.tv_isbn)                         TextView tvIsbn;
    @BindView(R.id.publish_date_layout)             RelativeLayout publishDateLayout;
    @BindView(R.id.tv_publish_date)                 TextView tvPublishDate;
    @BindView(R.id.catg_paths_layout)               RelativeLayout catgPathsLayout;
    @BindView(R.id.catg_path_layout)                LinearLayout catgPathLayout;

    //=====================固定套餐======================//
    @BindView(R.id.package_layout)                        LinearLayout packageLayout;
    @BindView(R.id.package_viewpager)                     JazzyViewPager packageViewpager;
    @BindView(R.id.package_indicator)                     CirclePageIndicator packageIndicator;
    @BindView(R.id.package_change)                        TextView packageChange;

    //=====================同类推荐======================//
    @BindView(R.id.recommend_viewpager)                   JazzyViewPager recommendViewpager;
    @BindView(R.id.recommend_indicator)                   CirclePageIndicator recommendIndicator;
    @BindView(R.id.recommend_layout)                      RelativeLayout recommendLayout;
    @BindView(R.id.recommend_change)                      TextView recommendChange;
    @BindView(R.id.recommend)                             LinearLayout recommend;
    @BindView(R.id.mainpic_viewpager)                     JazzyViewPager mainpicViewpager;
    @BindView(R.id.mainpic_indicator)                     CirclePageIndicator mainpicIndicator;
    @BindView(R.id.mainpic_layout)                        RelativeLayout mainpicLayout;
    @BindView(R.id.shop_layout_addCart)                   TextView shopTextviewAddCart;
    @BindView(R.id.detail_collect)                        ImageView detailCollect;
    @BindView(R.id.bt_menu_2_text)                        TextView tvCollect;

    //=====================评价======================//
    @BindView(R.id.layout_shop_comment)                   LinearLayout layoutShopComment;
    @BindView(R.id.shop_layout_onecomment)                LinearLayout shopLayoutOncomment;
    @BindView(R.id.shop_layout_onecomment1)               LinearLayout shopLayoutOncomment1;
    @BindView(R.id.shop_layout_onecomment2)               LinearLayout shopLayoutOncomment2;
    @BindView(R.id.shop_layout_onecomment_line)           View shopLayoutOnecommentLine;
    @BindView(R.id.shop_layout_onecomment_line1)          View shopLayoutOnecommentLine1;
    @BindView(R.id.shop_no_comment)                       TextView shopNoComment;
    @BindView(R.id.tv_comment_time)                       TextView tvCommentTime;
    @BindView(R.id.tv_comment_time1)                      TextView tvCommentTime1;
    @BindView(R.id.tv_comment_time2)                      TextView tvCommentTime2;
    @BindView(R.id.tv_comment_ratingbar)                  RatingBar tvCommentRatingbar;
    @BindView(R.id.tv_comment_ratingbar1)                 RatingBar tvCommentRatingbar1;
    @BindView(R.id.tv_comment_ratingbar2)                 RatingBar tvCommentRatingbar2;
    @BindView(R.id.shop_no_recommend)                     TextView shopNoRecommend;
    @BindView(R.id.good_off)                              TextView goodOff;

    //=====================底部按钮===================//
    @BindView(R.id.shop_layout_service)                   LinearLayout shopLayoutService;
    @BindView(R.id.shop_layout_store)                     LinearLayout shopLayoutStore;
    @BindView(R.id.shop_layout_collect)                   LinearLayout shopLayoutCollect;
    @BindView(R.id.shop_layout_cart)                      LinearLayout shopCartLayout;
    @BindView(R.id.check_detail_layout)                   LinearLayout checkDetailLayout;
    @BindView(R.id.tv_cart_num)                           TextView tvCartNum;

    //=====================秒杀==========================//
    @BindView(R.id.kill_layout)                           LinearLayout killLayout;
    @BindView(R.id.tv_base_price)                         TextView tvBasePrice;
    @BindView(R.id.tv_kill_price)                         TextView tvKillPrice;
    @BindView(R.id.tv_remain_time)                        MainDownTimerView mTimerView;
    @BindView(R.id.seckill_begin_layout)                  LinearLayout seckillBeginLayout;
    @BindView(R.id.tv_seckill_begin_time)                 TextView tvBeginTime;

    private Gds001Resp goodDetail;//基本信息
    private Gds002Resp recommendGds;//同类推荐
    private Gds003Resp promotionInfo;//促销信息
    private Gds004Resp packageList;//固定套餐
    private Gds005Resp commentList;//评论列表
    private Gds008Resp currentChooseSku;//当前选择单品
    private HashMap<String, GdsPropValueBaseInfo> selectedProp = new HashMap<>();
    private int packageIndex = 0;//初始化指向第一个套餐
    private Long mAppSpecPrice = 0L;//手机专享价

    //同类推荐
    List<GdsDetailBaseInfo> recommendGoods;
    //商品图
    private MainPicAdapter mainPicAdapter;
    //固定套餐
    private PackageAdapter packageAdapter;
    private RecommendAdapter recommendAdapter;
    private OnScrollListener onScrollListener;
    private OnScrollListener onScrollListener1;
    private OnScrollListener onScrollListener2;
    //规格属性选择计数
    private int selectProsCount = 0;
    View contentView;

    private String mGdsId;
    private String mSkuId;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_shop;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {
        mGdsId = getArguments().getString(Constant.SHOP_GDS_ID);
        mSkuId = getArguments().getString(Constant.SHOP_SKU_ID);
        String sAdid = getArguments().getString(Constant.SHOP_ADID);
        String sLinkType = getArguments().getString(Constant.SHOP_LINK_TYPE);
        HashMap<String, Object> sExpandMap = ((BaseActivity)mContext).getParamsMap();
        if (StringUtils.isNotEmpty(sAdid)) {
            sExpandMap.put(Constant.SHOP_ADID, sAdid);
            sExpandMap.put(Constant.SHOP_LINK_TYPE, sLinkType);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getFirstData();
    }

    private void getFirstData() {
        Gds001Req req = new Gds001Req();
        try {
            if (StringUtils.isNotEmpty(mGdsId)) {
                req.setGdsId(Long.parseLong(mGdsId));
            }
            if (StringUtils.isNotEmpty(mSkuId)) {
                req.setSkuId(Long.parseLong(mSkuId));
            }
        } catch (Exception e) {
            ToastUtil.show(getActivity(), "商品信息有误");
            getActivity().onBackPressed();
        }
        getJsonService().requestGds001(getActivity(), req, true, new JsonService.CallBack<Gds001Resp>() {
            @Override
            public void oncallback(Gds001Resp gds001Resp) {
                goodDetail = gds001Resp;
                mAppSpecPrice = goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getAppSpecPrice();
                initProm();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void initProm() {
        Gds003Req req = new Gds003Req();
        req.setGdsId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getGdsId());
        req.setSkuId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId());
        req.setGdsName(goodDetail.getGdsDetailBaseInfo().getGdsName());
        if (mAppSpecPrice != null && mAppSpecPrice != 0) {
            req.setDiscountPrice(mAppSpecPrice);
            req.setRealPrice(mAppSpecPrice);
        } else {
            req.setDiscountPrice(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getDiscountPrice());
            req.setRealPrice(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getRealPrice());
        }
        req.setShopId(goodDetail.getGdsDetailBaseInfo().getShopId());
        getJsonService().requestGds003(getActivity(), req, false, new JsonService.CallBack<Gds003Resp>() {
            @Override
            public void oncallback(Gds003Resp gds003Resp) {
                promotionInfo = gds003Resp;
                initPackage();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void initPackage() {
        Gds004Req req = new Gds004Req();
        req.setGdsId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getGdsId());
        req.setShopId(goodDetail.getGdsDetailBaseInfo().getShopId());
        req.setSkuId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId());
        getJsonService().requestGds004(getActivity(), req, true, new JsonService.CallBack<Gds004Resp>() {
            @Override
            public void oncallback(Gds004Resp gds004Resp) {
                packageList = gds004Resp;
                configView();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });

    }

    private void configView() {
        initMainPic();
        GdsSeckillDiscountDTO secKillDTO = promotionInfo.getSeckill();

        if (AppContext.isLogin) {
            getCartGoodsNum();
        }
        goodName.setText(goodDetail.getGdsDetailBaseInfo().getGdsName());
        if (StringUtils.isNotEmpty(goodDetail.getGdsDetailBaseInfo().getGdsSubHead())) {
            goodDesc.setText(goodDetail.getGdsDetailBaseInfo().getGdsSubHead());
        } else {
            setGone(goodDesc);
        }
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            goodPrice.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange_ff6a00));
        } else {
            goodPrice.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
        }

        //商品收藏
        initCollect();
        //客服部分(人卫按钮)
        initService();
        //店铺部分(人卫隐藏店铺按钮)
        initStore();

        //规格部分
        if (null == goodDetail.getGdsDetailBaseInfo().getParams()
                || 0 == goodDetail.getGdsDetailBaseInfo().getParams().size()) {
            setGone(goodLayoutNormal);
        } else {
            setVisible(goodLayoutNormal);
            setGoodNormalDesByGoodDetail(goodNormalDes);
            goodLayoutNormal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Gds008Req req = new Gds008Req();
                    req.setGdsId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getGdsId());
                    req.setShopId(goodDetail.getGdsDetailBaseInfo().getShopId());
                    req.setSkuId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId());
                    //设置初始属性值，为第一个
                    List<GdsSku2PropPropIdxBaseInfo> params = new ArrayList<>();
                    for (int i = 0; i < goodDetail.getGdsDetailBaseInfo().getParams().size(); i++) {
                        GdsPropBaseInfo info = goodDetail.getGdsDetailBaseInfo().getParams().get(i);
                        GdsSku2PropPropIdxBaseInfo reqInfo = new GdsSku2PropPropIdxBaseInfo();
                        reqInfo.setSkuId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId());
                        reqInfo.setGdsId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getGdsId());
                        reqInfo.setShopId(goodDetail.getGdsDetailBaseInfo().getShopId());
                        reqInfo.setPropId(info.getId());
                        reqInfo.setPropName(info.getPropName());
                        reqInfo.setPropValue(info.getValues().get(0).getPropValue());
                        reqInfo.setPropValueId(info.getValues().get(0).getPropId());
                        params.add(reqInfo);
                    }
                    req.setGdsPropValueReqDTOs(params);
                    getJsonService().requestGds008(getActivity(), req, true, new JsonService.CallBack<Gds008Resp>() {
                        @Override
                        public void oncallback(Gds008Resp gds008Resp) {
//                        currentChooseSku = gds008Resp;
//                        v.setTag(currentChooseSku);
                            if (null == currentChooseSku) {
                                currentChooseSku = new Gds008Resp();
                                GdsDetailBaseInfo info = new GdsDetailBaseInfo();
                                currentChooseSku.setGdsDetailBaseInfo(info);
                                GdsSkuBaseInfo gdsSkuBaseInfo = new GdsSkuBaseInfo();
                                currentChooseSku.getGdsDetailBaseInfo().setGdsSkuBaseInfo(gdsSkuBaseInfo);
                                //
                                //1.图片
//                            currentChooseSku.setMainPicUrl(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo()
//                                    .getMainPicUrl());
                                currentChooseSku.setMainPicUrl(gds008Resp.getMainPicUrl());
                                //2.价格
                                currentChooseSku.getGdsDetailBaseInfo().getGdsSkuBaseInfo().setDiscountPrice(goodDetail
                                        .getGdsDetailBaseInfo().getGdsSkuBaseInfo().getDiscountPrice());
                                //3.编码
                                currentChooseSku.getGdsDetailBaseInfo().setGdsName(goodDetail.getGdsDetailBaseInfo()
                                        .getGdsName());
                                //4.商品描述
                                currentChooseSku.getGdsDetailBaseInfo().setGdsSubHead(goodDetail
                                        .getGdsDetailBaseInfo().getGdsSubHead());

                                    /*
                                    *  aQuery.id(goodImage).image(currentChooseSku.getMainPicUrl(), true, true, 0,
                R.drawable.default_img, AppContext.bmPreset, 0);
        goodPrice.setText(MoneyUtils.GoodListPrice(currentChooseSku.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getDiscountPrice()));
        goodCode.setText(currentChooseSku.getGdsDetailBaseInfo().getGdsName());
        goodDesc.setText(currentChooseSku.getGdsDetailBaseInfo().getGdsSubHead());
        goodDesc.setVisibility(View.GONE);
                                    *
                                    *
                                    * */
//
                            }
                            showPopupWindow(v);
                        }

                        @Override
                        public void onErro(AppHeader header) {

                        }
                    });
                }
            });
        }

        //套餐部分
        showPackage();
        //固定套餐换一批
        packageChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (packageAdapter.getCount() == 1) {
                    ToastUtil.show(getActivity(), "暂无更多固定套餐");
                    return;
                }
                int currentPager = packageViewpager.getCurrentItem();
                currentPager++;
                if (packageAdapter != null && currentPager == packageAdapter.getCount()) {
                    currentPager = 0;
                }
                packageViewpager.setCurrentItem(currentPager, true);
            }
        });
        //显示3个评价
        initComment();
        //同类推荐
        initRecommend();
        //同类推荐换一批
        recommendChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recommendAdapter.getCount() == 1) {
                    ToastUtil.show(getActivity(), "暂无更多推荐");
                    return;
                }
                int currentPager = recommendViewpager.getCurrentItem();
                currentPager++;
                if (recommendAdapter != null && currentPager == recommendAdapter.getCount()) {
                    currentPager = 0;
                }
                recommendViewpager.setCurrentItem(currentPager, true);
            }
        });

        checkDetailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopDetailActivity activity = (ShopDetailActivity) getActivity();
                activity.goToDetail();
            }
        });

        //底部添加到购物车
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            shopTextviewAddCart.setBackground(ContextCompat.getDrawable(getActivity(), R.color.orange_ff6a00));
        } else {
            shopTextviewAddCart.setBackground(ContextCompat.getDrawable(getActivity(), R.color.red));
        }
        shopTextviewAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == currentChooseSku) {
                    if (AppContext.isLogin) {
                        addToCartSingleAtBottom();
                    } else {
                        showLoginDialog(getActivity());
                    }
                } else {
                    if (AppContext.isLogin) {
                        addToCartSingle();
                    } else {
                        showLoginDialog(getActivity());
                    }
                }
            }
        });

        if (secKillDTO.isExist()) {
            if (secKillDTO.isStart()) { //秒杀开始
                setVisible(killLayout);
                setGone(rlPriceLayout,seckillBeginLayout);
                MoneyUtils.showSpannedPrice(tvKillPrice, MoneyUtils.GoodListPrice(promotionInfo.getSaleList().get(0).getPromSkuPriceBaseInfo().getDiscountFinalPrice().longValue()));
                tvBasePrice.setText(MoneyUtils.GoodListPrice(promotionInfo.getSaleList().get(0).getPromSkuPriceBaseInfo().getDiscountCaclPrice().longValue()));
                tvBasePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvBasePrice.getPaint().setAntiAlias(true);
                long mills = secKillDTO.getSeckillProm().getEndTime().getTime() - secKillDTO.getSystemTime().getTime();
                mTimerView.setDownTime((int) mills);
                mTimerView.startDownTimer();
            } else {//秒杀未开始
                if (mAppSpecPrice != null && mAppSpecPrice != 0) {
                    setVisible(tvPhonePrice, tvPhonePriceDesc, goodExist, seckillBeginLayout);
                    setGone(killLayout, goodPrice, goodOriginprice);
                    MoneyUtils.showSpannedPrice(tvPhonePrice,MoneyUtils.GoodListPrice(mAppSpecPrice));
                    Long reducePrice = goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getDiscountPrice() - mAppSpecPrice;
                    tvPhonePriceDesc.setText("比PC省" + (MoneyUtils.formatToNoUnitMoney(reducePrice.doubleValue() / 100) + "元"));
                    goodExist.setText(goodDetail.getGdsDetailBaseInfo().getStockStatusDesc());
                    tvBeginTime.setText("预计" + DateUtils.getTimeStamp(secKillDTO.getSeckillProm().getStartTime()) + "开始");
                } else {
                    setVisible(seckillBeginLayout);
                    setGone(killLayout, tvPhonePrice, tvPhonePriceDesc);
                    tvBeginTime.setText("预计" + DateUtils.getTimeStamp(secKillDTO.getSeckillProm().getStartTime()) + "开始");
                    showPrice();
                }
            }
        } else {
            if (mAppSpecPrice != null && mAppSpecPrice != 0) {
                setVisible(tvPhonePrice, tvPhonePriceDesc, goodExist);
                setGone(killLayout, seckillBeginLayout, goodPrice, goodOriginprice);
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    tvPhonePrice.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange_ff6a00));
                    tvPhonePriceDesc.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange_ff6a00));
                } else {
                    tvPhonePrice.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
                    tvPhonePriceDesc.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
                }
                MoneyUtils.showSpannedPrice(tvPhonePrice,MoneyUtils.GoodListPrice(mAppSpecPrice));
                Long reducePrice = goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getDiscountPrice() - mAppSpecPrice;
                tvPhonePriceDesc.setText("比PC省" + (MoneyUtils.formatToNoUnitMoney(reducePrice.doubleValue() / 100) + "元"));
                if (goodDetail.getGdsDetailBaseInfo().getGdsTypeId() == 2) {
                    goodExist.setText("充足");
                } else {
                    goodExist.setText(goodDetail.getGdsDetailBaseInfo().getStockStatusDesc());
                }
            } else {
                setVisible(goodPrice, goodOriginprice);
                setGone(killLayout, seckillBeginLayout, tvPhonePrice, tvPhonePriceDesc);
                showPrice();
            }
        }

        if (null == promotionInfo || 0 == promotionInfo.getSaleList().size()) {
            setGone(rlSale, rlSale1);
        } else {
            if (promotionInfo.getSaleList().size() > 1) {
                setVisible(rlSale, rlSale1);
                goodPromotionLabel.setText(promotionInfo.getSaleList().get(0).getPromBaseInfo().getNameShort());
                goodPromotionDes.setText(promotionInfo.getSaleList().get(0).getPromBaseInfo().getPromTheme());
                goodPromotionLabel1.setText(promotionInfo.getSaleList().get(1).getPromBaseInfo().getNameShort());
                goodPromotionDes1.setText(promotionInfo.getSaleList().get(1).getPromBaseInfo().getPromTheme());
            } else {
                setVisible(rlSale);
                setGone(rlSale1);
                goodPromotionLabel.setText(promotionInfo.getSaleList().get(0).getPromBaseInfo().getNameShort());
                goodPromotionDes.setText(promotionInfo.getSaleList().get(0).getPromBaseInfo().getPromTheme());
            }

            Long discountCaclPrice = promotionInfo.getSaleList().get(0).getPromSkuPriceBaseInfo().getDiscountCaclPrice().longValue();
            Long discountFinalPrice = promotionInfo.getSaleList().get(0).getPromSkuPriceBaseInfo().getDiscountFinalPrice().longValue();
            Long discountPrice = goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getDiscountPrice();
            boolean flag = true;

            if(discountFinalPrice.equals(discountPrice)){
                flag = false;
            }

            if (null==discountCaclPrice || discountCaclPrice == 0) {
                goodOriginprice.setText(MoneyUtils.GoodListPrice(goodDetail.getGdsDetailBaseInfo().getGuidePrice()));
            }
            else {
                if(flag){
                    goodOriginprice.setText(MoneyUtils.GoodListPrice(discountCaclPrice));
                }else{
                    goodOriginprice.setText(MoneyUtils.GoodListPrice(goodDetail.getGdsDetailBaseInfo().getGuidePrice()));
                }
            }
            if (discountFinalPrice == 0) {
                MoneyUtils.showSpannedPrice(goodPrice,MoneyUtils.GoodListPrice(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getDiscountPrice()));
            } else {
                MoneyUtils.showSpannedPrice(goodPrice,MoneyUtils.GoodListPrice(discountFinalPrice));
            }

            rlSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Gds003Req req = new Gds003Req();
                    req.setShopId(goodDetail.getGdsDetailBaseInfo().getShopId());
                    req.setGdsId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getGdsId());
                    if (mAppSpecPrice != null && mAppSpecPrice != 0) {
                        req.setRealPrice(mAppSpecPrice);
                        req.setDiscountPrice(mAppSpecPrice);
                    } else {
                        req.setRealPrice(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getRealPrice());
                        req.setDiscountPrice(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getDiscountPrice());
                    }
                    req.setGdsName(goodDetail.getGdsDetailBaseInfo().getGdsName());
                    req.setSkuId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId());
                    getJsonService().requestGds003(getActivity(), req, true, new JsonService.CallBack<Gds003Resp>() {
                        @Override
                        public void oncallback(Gds003Resp gds003Resp) {
                            v.setTag(gds003Resp);
                            showPromotionPopUpWindow(v);
                        }

                        @Override
                        public void onErro(AppHeader header) {

                        }
                    });
                }
            });
        }

        //显示作者
        final String author = goodDetail.getGdsDetailBaseInfo().getAuthor();
        if (StringUtils.isNotEmpty(author)) {
            setVisible(authorLayout);
            tvAuthor.setText(author);
            authorLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtils.contains(author, "、")) {
                        List<SearchPropReqInfo> searchProps = new ArrayList<>();
                        String[] split = StringUtils.split(author, "、");
                        List<String> authors = new ArrayList<>();
                        for (int i = 0; i < split.length; i++) {
                            authors.add(split[i]);
                        }
                        SearchPropReqInfo searchPropReqInfo = new SearchPropReqInfo();
                        searchPropReqInfo.setPropertyId(Constant.AUTHOR_ID);
                        searchPropReqInfo.setPropertyValues(authors);
                        searchProps.add(searchPropReqInfo);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constant.AUTHOR_BOOKS,(Serializable) searchProps);
                        launch(SearchResultActivity.class, bundle);
                    } else {
                        List<SearchPropReqInfo> list = new ArrayList<>();
                        SearchPropReqInfo info = new SearchPropReqInfo();
                        List<String> values = new ArrayList<>();
                        values.add(author);
                        info.setPropertyId(Constant.AUTHOR_ID);
                        info.setPropertyValues(values);
                        list.add(info);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constant.AUTHOR_BOOKS,(Serializable) list);
                        launch(SearchResultActivity.class, bundle);
                    }
                }
            });
        } else {
            setGone(authorLayout);
        }

        //显示电子书
        boolean existOtherBook = goodDetail.getGdsDetailBaseInfo().isExistOtherBook();
        String existCatName = goodDetail.getGdsDetailBaseInfo().getCorrespondingCatgName();
        final Long correspondingGdsId = goodDetail.getGdsDetailBaseInfo().getCorrespondingGdsId();
        final Long correspondingSkuId = goodDetail.getGdsDetailBaseInfo().getCorrespondingSkuId();
        Long corrDiscountFinalPrice = goodDetail.getGdsDetailBaseInfo().getCorrDiscountFinalPrice();
        if (existOtherBook) {
            setVisible(goodTypeLayout);
            if (existCatName != null) {
                tvGoodType.setText(existCatName);
            }
            if (corrDiscountFinalPrice != null) {
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    tvGoodTypePrice.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange_ff6a00));
                } else {
                    tvGoodTypePrice.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
                }
                MoneyUtils.showSpannedPrice(tvGoodTypePrice, MoneyUtils.GoodListPrice(corrDiscountFinalPrice));
            }
            goodTypeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    if (correspondingGdsId != null) {
                        bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(correspondingGdsId));
                    }
                    if (correspondingSkuId != null) {
                        bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(correspondingSkuId));
                    }
                    launch(ShopDetailActivity.class, bundle);
                }
            });
        } else {
            setGone(goodTypeLayout);
        }

        //显示isbn、出版日期、分类路径
        String isbn = goodDetail.getGdsDetailBaseInfo().getIsbn();
        String appearDate = goodDetail.getGdsDetailBaseInfo().getAppearDate();
        if (StringUtils.isNotEmpty(isbn)) {
            setVisible(isbnLayout);
            tvIsbn.setText(isbn);
        } else {
            setGone(isbnLayout);
        }

        if (StringUtils.isNotEmpty(appearDate)) {
            setVisible(publishDateLayout);
            tvPublishDate.setText(appearDate);
        } else {
            setGone(publishDateLayout);
        }

        List<GdscatgsCodeAndNameVO> categoryList = goodDetail.getGdsDetailBaseInfo().getCateList();
        if (categoryList != null && categoryList.size() != 0) {
            setVisible(catgPathsLayout);
            catgPathLayout.removeAllViews();
            for (int i = 0; i < categoryList.size(); i++) {
                String catgName = categoryList.get(i).getName();
                final String catgCode = categoryList.get(i).getCode();
                TextView textView = new TextView(getActivity());
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_4169E1));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                if (i == categoryList.size() - 1) {
                    textView.setText(catgName);
                } else {
                    textView.setText(catgName + " > ");
                }
                textView.setPadding(0, 0, 0, 0);
                textView.setSingleLine(true);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                        intent.putExtra(Constant.SHOP_CATG_CODE, catgCode);
                        getActivity().startActivity(intent);
                    }
                });
                catgPathLayout.addView(textView);
            }
        } else {
            setGone(catgPathsLayout);
        }

        handleGoodState();

        shopCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppContext.isLogin) {
                    launch(ShopCartActivity.class);
                } else {
                    DialogAnotherUtil.showCustomAlertDialogWithMessage(getActivity(), null,
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
                                    getActivity().sendBroadcast(new Intent(ShopCartFragment.REFRESH_CART));
                                    DialogUtil.dismissDialog();
                                }
                            });
                }
            }
        });

    }

    /**
     * 显示价格
     */
    private void showPrice() {
        //显示折后价
        if (promotionInfo != null && promotionInfo.getSaleList().size() != 0) {
            MoneyUtils.showSpannedPrice(goodPrice, MoneyUtils.GoodListPrice(promotionInfo.getSaleList().get(0).getPromSkuPriceBaseInfo().getDiscountFinalPrice().longValue()));
        } else {
            MoneyUtils.showSpannedPrice(goodPrice, MoneyUtils.GoodListPrice(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getDiscountPrice()));
        }
        //显示原价
        goodOriginprice.setText(MoneyUtils.GoodListPrice(goodDetail.getGdsDetailBaseInfo().getGuidePrice()));
        goodOriginprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        goodOriginprice.getPaint().setAntiAlias(true);

        setVisible(goodExist);
        //虚拟商品的判断
        if (goodDetail.getGdsDetailBaseInfo().getGdsTypeId() == 2) {
            goodExist.setText("充足");
        } else {
            goodExist.setText(goodDetail.getGdsDetailBaseInfo().getStockStatusDesc());
        }
    }

    /**
     * 用于处理商品已经下架、紧张和无货状态时时的详情展示
     * 其中包括购物车按钮{@link #shopTextviewAddCart}，状态label{@link #goodExist}的展示
     */
    private void handleGoodState() {
        handleAddCartButtonText(goodDetail.getGdsDetailBaseInfo().getAddToCartPromp());

        if (goodDetail.getGdsDetailBaseInfo().getGdsStatus().equals("11")) {
            setVisible(goodExist);
            if (goodDetail.getGdsDetailBaseInfo().getStockStatusDesc().equals("充足"))
            {
                goodExist.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_round_green));
            }
            else if (goodDetail.getGdsDetailBaseInfo().getStockStatusDesc().equals("紧张"))
            {
                goodExist.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_round_red));
            }
            else if (goodDetail.getGdsDetailBaseInfo().getStockStatusDesc().equals("无货")
                    && (goodDetail.getGdsDetailBaseInfo().getGdsTypeId() != 2))
            {
                shopTextviewAddCart.setBackground(ContextCompat.getDrawable(getActivity(),R.color.gray_c0c1c3));
                goodExist.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_round_gray));
                shopTextviewAddCart.setClickable(false);
            }
            else if (goodDetail.getGdsDetailBaseInfo().getStockStatusDesc().equals("无货")
                    && goodDetail.getGdsDetailBaseInfo().getGdsTypeId() == 2)
            {
                shopTextviewAddCart.setBackground(ContextCompat.getDrawable(getActivity(), R.color.orange_ff6a00));
                shopTextviewAddCart.setClickable(true);
            }
            return;
        }
        //已下架
        shopTextviewAddCart.setClickable(false);
        shopTextviewAddCart.setBackground(ContextCompat.getDrawable(getActivity(), R.color.gray_c0c1c3));
        setGone(goodPrice, goodOriginprice, promotionMore, normalMore, goodExist, rlSale, goodLayoutNormal);
        setVisible(goodOff);
    }

    /**
     * 用于在状态调整时改变添加购物车按钮的展示文字
     * @param content 按钮内容
     */
    private void handleAddCartButtonText(String content) {
        if (TextUtils.isEmpty(content)){
            return;
        }
        shopTextviewAddCart.setText(content);
    }

    private void initStore() {
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            setGone(shopLayoutStore);
        } else {
            setVisible(shopLayoutStore);
            shopLayoutStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (goodDetail.getGdsDetailBaseInfo().getShopId() != null) {
                        Intent intent = new Intent(getActivity(), StoreActivity.class);
                        intent.putExtra(Constant.STORE_ID, String.valueOf(goodDetail.getGdsDetailBaseInfo().getShopId()));
                        launch(intent);
                    }
                }
            });
        }
    }

    private void initService() {
        shopLayoutService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppContext.isLogin) {
                    connectIM();
                } else {
                    showLoginDialog(getActivity());
                }
            }
        });
    }

    private void connectIM() {
        IM001Req req = new IM001Req();
        if (goodDetail.getGdsDetailBaseInfo().getShopId() != null) {
            req.setShopId(goodDetail.getGdsDetailBaseInfo().getShopId());
        }
        final Long businessType = 2L;
        req.setBusinessType(businessType);
        if (StringUtils.isNotEmpty(mGdsId)) {
            req.setBusinessId(mGdsId);
        }
        DialogUtil.showCustomerProgressDialog(getActivity());
        NetCenter.build(getActivity())
                .requestDefault(req,"im001")
                .map(new Func1<AppBody, IM001Resp>() {
                    @Override
                    public IM001Resp call(AppBody appBody) {
                        return null == appBody ? null : (IM001Resp) appBody;
                    }
                }).subscribe(new Action1<IM001Resp>() {
            @Override
            public void call(IM001Resp im001Resp) {
                DialogUtil.dismissDialog();
                if (im001Resp != null) {
                    String account = im001Resp.getUserCode();
                    String serviceAccount = im001Resp.getCsaCode();
                    String serviceName = im001Resp.getHotlinePerson();
                    String servicePhoto = im001Resp.getHotlinePhoto();
                    String sessionId = im001Resp.getSessionId();
                    String photo = im001Resp.getCustPic();
                    int waitCount = im001Resp.getWaitCount();

                    String description;
                    if (0 == waitCount){
                        description = "用户"+account+"第"+(waitCount+1)+"次接入";
                        Intent intent = new Intent(getActivity(), MessageActivity.class);
                        if (StringUtils.isNotEmpty(account)) {
                            intent.putExtra("account", account);
                        }
                        if (StringUtils.isNotEmpty(serviceAccount)) {
                            intent.putExtra("serviceAccount", serviceAccount);
                        }
                        if (StringUtils.isNotEmpty(serviceName)) {
                            intent.putExtra("serviceName", serviceName);
                        }
                        if (StringUtils.isNotEmpty(sessionId)) {
                            intent.putExtra("sessionId", sessionId);
                        }
                        intent.putExtra("servicePhoto", servicePhoto);
                        intent.putExtra("photo", photo);
                        intent.putExtra("businessType",businessType);
                        intent.putExtra("businessId",mGdsId);
                        intent.putExtra("waitCount", waitCount);
                        intent.putExtra("shopId", goodDetail.getGdsDetailBaseInfo().getShopId());
                        intent.putExtra("description",description);
                        launch(intent);
                    }
                    else if (waitCount > 0) {
                        new AlertView("提示", "客服正忙，请稍候再试",
                                null, new String[]{"确定"},
                                null, getActivity(), AlertView.Style.Alert, null).show();
                    }
                    else {
                        new AlertView("提示", "对不起，客服还没有上线 \n" + "请在正常工作时间内联系客服人员",
                                null, new String[]{"确定"},
                                null, getActivity(), AlertView.Style.Alert, null).show();
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DialogUtil.dismissDialog();
                LogUtil.e(throwable);
            }
        });
    }

    /**
     * 收藏商品
     */
    private void initCollect() {
        if (!AppContext.isLogin) {
            detailCollect.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.detail_collect));
            detailCollect.setSelected(false);
            tvCollect.setText("收藏");
        } else if (goodDetail.getGdsDetailBaseInfo().getIfBrowse()) {
            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                detailCollect.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.detail_collected_orange));
                tvCollect.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange_ff6a00));
            } else {
                detailCollect.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.detail_collected));
                tvCollect.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
            }
            detailCollect.setSelected(true);
            tvCollect.setText("已收藏");
        } else {
            detailCollect.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.detail_collect));
            detailCollect.setSelected(false);
            tvCollect.setText("收藏");
            tvCollect.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        }
        shopLayoutCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppContext.isLogin) {
                    Gds010Req gds010Req = new Gds010Req();
                    gds010Req.setCollectId(goodDetail.getGdsDetailBaseInfo().getCollectId());
                    gds010Req.setSkuId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId());
                    if (detailCollect.isSelected()) {
                        gds010Req.setIfCancel(true);
                    } else {
                        gds010Req.setIfCancel(false);
                    }
                    getJsonService().requestGds010(getActivity(), gds010Req, false, new JsonService.CallBack<Gds010Resp>() {
                        @Override
                        public void oncallback(Gds010Resp gds010Resp) {
                            if (detailCollect.isSelected()) {
                                detailCollect.setSelected(false);
                                detailCollect.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.detail_collect));
                                tvCollect.setText("收藏");
                                tvCollect.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                                ToastUtil.show(getActivity(), "取消成功");
                            } else {
                                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                                    detailCollect.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.detail_collected_orange));
                                    tvCollect.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange_ff6a00));
                                } else {
                                    detailCollect.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.detail_collected));
                                    tvCollect.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
                                }
                                detailCollect.setSelected(true);
                                tvCollect.setText("已收藏");
                                ToastUtil.show(getActivity(), "收藏成功");
                            }
                        }

                        @Override
                        public void onErro(AppHeader header) {

                        }
                    });
                }
                else {
                    showLoginDialog(getActivity());
                }
            }
        });
    }

    private void setGoodNormalDesByGoodDetail(TextView goodNormalDes) {
        if (null == goodDetail) {
            return;
        }
        if (null == goodDetail.getGdsDetailBaseInfo()) {
            return;
        }
        if (null == goodDetail.getGdsDetailBaseInfo().getParams()) {
            return;
        }
        if (0 == goodDetail.getGdsDetailBaseInfo().getParams().size()) {
            return;
        }
        String str = "";
        for (int i = 0; i < goodDetail.getGdsDetailBaseInfo().getParams().size(); i++) {
            GdsPropBaseInfo info = goodDetail.getGdsDetailBaseInfo().getParams().get(i);
            str += info.getPropName() + " " + info.getValues().get(0).getPropValue() + " ";
        }
        goodNormalDes.setText(str);
    }

    private void setGoodNormalDesBySku(TextView goodNormalDes) {
        if (null == goodDetail
                || null == goodDetail.getGdsDetailBaseInfo()
                || 0 == goodDetail.getGdsDetailBaseInfo().getParams().size()) {
            return;
        }
        String str = "";
        for (int i = 0; i < goodDetail.getGdsDetailBaseInfo().getParams().size(); i++) {
            GdsPropBaseInfo info = goodDetail.getGdsDetailBaseInfo().getParams().get(i);
            GdsPropValueBaseInfo selectProp = selectedProp.get(info.getPropName());
            str += info.getPropName() + " " + selectProp.getPropValue() + " ";
        }
        goodNormalDes.setText(str);
    }

    /**
     * 底部添加到购物车按钮，数量固定为1
     */
    private void addToCartSingleAtBottom() {
        Ord001Req req = new Ord001Req();
//            req.setShopId(currentChooseSku.getGdsDetailBaseInfo().getShopId());
        req.setShopId(goodDetail.getGdsDetailBaseInfo().getShopId());
        req.setCartType("01");

        List<Ord00101Req> ord00101Reqs = new ArrayList<>();
        Ord00101Req ord00101Req = new Ord00101Req();
//            ord00101Req.setShopId(currentChooseSku.getGdsDetailBaseInfo().getShopId());
        ord00101Req.setShopId(goodDetail.getGdsDetailBaseInfo().getShopId());
//            ord00101Req.setGdsId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getGdsId());
        ord00101Req.setGdsId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getGdsId());
        ord00101Req.setGdsName(goodDetail.getGdsDetailBaseInfo().getGdsName());
        ord00101Req.setGdsType(goodDetail.getGdsDetailBaseInfo().getGdsTypeId());
        ord00101Req.setOrderAmount(1L);
//            ord00101Req.setSkuId(currentChooseSku.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId());
        ord00101Req.setSkuId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId());
        ord00101Req.setGroupType("0");
//            ord00101Req.setGroupDetail(String.valueOf(currentChooseSku.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId()));
        ord00101Req.setGroupDetail(String.valueOf(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId()));
        ord00101Req.setCategoryCode(goodDetail.getGdsDetailBaseInfo().getMainCatgs());
        ord00101Reqs.add(ord00101Req);
        req.setOrd00101Reqs(ord00101Reqs);
        getJsonService().requestOrd001(getActivity(), req, false, new JsonService.CallBack<Ord001Resp>() {
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

    private void initMainPic() {
        if (goodDetail.getGdsDetailBaseInfo().getImageUrlList() != null
                && goodDetail.getGdsDetailBaseInfo().getImageUrlList().size() != 0) {
            createScroller(mainpicViewpager);
            mainpicViewpager.setTransitionEffect(JazzyViewPager.TransitionEffect.Accordion);
            mainPicAdapter = new MainPicAdapter();
            mainpicViewpager.setAdapter(mainPicAdapter);

            createMainPicPageIndicator();
            mainPicAdapter.notifyDataSetChanged();
        } else {
            mainpicLayout.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.default_img));
            FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_shop_image, mainpicLayout, false);
            ImageView gdsTypeImgIv = (ImageView) frameLayout.findViewById(R.id.iv_gds_type_img);
            ImageView tagServiceImgIv = (ImageView) frameLayout.findViewById(R.id.iv_service_tag);

            String edbook = goodDetail.getGdsDetailBaseInfo().getEdbook();
            //处理电子书和数字标签
            handleElecDigitalTag(edbook,gdsTypeImgIv);
            //增值服务
            handleHasValueAddedTag(goodDetail.getGdsDetailBaseInfo().isHasValueAdded(),tagServiceImgIv);
            mainpicLayout.addView(frameLayout);//添加页卡

        }
    }

    private void initRecommend() {
        Gds002Req req = new Gds002Req();
        req.setGdsId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getGdsId());
        getJsonService().requestGds002(getActivity(), req, false, new JsonService.CallBack<Gds002Resp>() {
            @Override
            public void oncallback(Gds002Resp gds002Resp) {
                recommendGds = gds002Resp;
                showRecommends();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void showRecommends() {
        recommendGoods = recommendGds.getCommondCatGds();
        setVisible(recommend);
        if (null == recommendGoods || 0 == recommendGoods.size()) {
            setGone(recommendLayout, recommendChange);
            setVisible(shopNoRecommend);
        } else {
            setVisible(recommendLayout);
            setGone(shopNoRecommend);
        }

        createScroller(recommendViewpager);
        recommendViewpager.setTransitionEffect(JazzyViewPager.TransitionEffect.Accordion);
        recommendAdapter = new RecommendAdapter();
        recommendViewpager.setAdapter(recommendAdapter);

        createRecommendPageIndicator();
        recommendAdapter.notifyDataSetChanged();
    }

    private void createMainPicPageIndicator() {
        CirclePageIndicator indicator = (CirclePageIndicator) mainpicLayout.findViewById(R.id.mainpic_indicator);
        if (mainPicAdapter.getCount() == 1) {
            setGone(indicator);
        } else {
            indicator.setViewPager(mainpicViewpager);
            final float density = mainpicLayout.getResources().getDisplayMetrics().density;
            indicator.setRadius(3 * density);
            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                indicator.setFillColor(ContextCompat.getColor(getActivity(), R.color.orange_ff6a00));
            } else {
                indicator.setFillColor(ContextCompat.getColor(getActivity(), R.color.red));
            }
            indicator.setPageColor(ContextCompat.getColor(getActivity(), R.color.gray_c0c1c3));
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

    private void createPackagePageIndicator() {
        CirclePageIndicator indicator = (CirclePageIndicator) packageLayout.findViewById(R.id.package_indicator);
        if (packageAdapter.getCount() == 1) {
            setGone(indicator, packageChange);
        } else {
            indicator.setViewPager(packageViewpager);
            final float density = packageLayout.getResources().getDisplayMetrics().density;
            indicator.setRadius(3 * density);
            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                indicator.setFillColor(ContextCompat.getColor(getActivity(), R.color.orange_ff6a00));
            } else {
                indicator.setFillColor(ContextCompat.getColor(getActivity(), R.color.red));
            }
            indicator.setPageColor(ContextCompat.getColor(getActivity(), R.color.gray_c0c1c3));
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

    private void createRecommendPageIndicator() {
        CirclePageIndicator indicator = (CirclePageIndicator) recommendLayout.findViewById(R.id.recommend_indicator);
        if (recommendAdapter.getCount()==1) {
            setGone(indicator, recommendChange);
        } else {
            indicator.setViewPager(recommendViewpager);
            final float density = recommendLayout.getResources().getDisplayMetrics().density;
            indicator.setRadius(3 * density);
            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                indicator.setFillColor(ContextCompat.getColor(getActivity(), R.color.orange_ff6a00));
            } else {
                indicator.setFillColor(ContextCompat.getColor(getActivity(), R.color.red));
            }
            indicator.setPageColor(ContextCompat.getColor(getActivity(), R.color.gray_c0c1c3));
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


    private void createScroller(ViewPager viewPager) {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Interpolator sInterpolator = new AccelerateInterpolator();
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), sInterpolator);
            scroller.setFixedDuration(200);
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

    }

    public interface OnScrollListener {
        void onScrolling(boolean isScrolling);
    }

    private class MainPicAdapter extends PagerAdapter {
        private SparseArray<FrameLayout> mListViews;

        public MainPicAdapter() {
            this.mListViews = new SparseArray<>();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));//删除页卡
            mListViews.remove(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_shop_image, container, false);
            ImageView gdsImgIv = (ImageView) frameLayout.findViewById(R.id.iv_gds_img);
            ImageView gdsTypeImgIv = (ImageView) frameLayout.findViewById(R.id.iv_gds_type_img);
            ImageView tagServiceImgIv = (ImageView) frameLayout.findViewById(R.id.iv_service_tag);
            mListViews.put(position, frameLayout);
            GlideUtil.loadImg(getActivity(),goodDetail.getGdsDetailBaseInfo().getImageUrlList().get(position), gdsImgIv);
            String edbook = goodDetail.getGdsDetailBaseInfo().getEdbook();
            //处理电子书和数字标签
            handleElecDigitalTag(edbook,gdsTypeImgIv);
            //增值服务
            handleHasValueAddedTag(goodDetail.getGdsDetailBaseInfo().isHasValueAdded(),tagServiceImgIv);
            container.addView(frameLayout);//添加页卡
            return frameLayout;
        }

        @Override
        public int getCount() {
            return goodDetail.getGdsDetailBaseInfo().getImageUrlList() == null ? 0 :
                    goodDetail.getGdsDetailBaseInfo().getImageUrlList().size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /**
     * 处理是否添加增值服务标签
     * @param hasValueAdded true 含有增值服务
     * @param tagServiceImgIv 目标ImageView
     */
    private void handleHasValueAddedTag(boolean hasValueAdded, ImageView tagServiceImgIv) {
        //增值服务标签
        if (hasValueAdded) {
            setVisible(tagServiceImgIv);
            tagServiceImgIv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.tag_service));
        }else {
            setGone(tagServiceImgIv);
        }

    }

    /**
     * 用于处理右上角展示的标签
     * @param edbook edbook类型
     * @param gdsTypeImgIv 标签ImageView
     */
    private void handleElecDigitalTag(String edbook, ImageView gdsTypeImgIv) {
        if (StringUtils.isNotEmpty(edbook)) {
            setVisible(gdsTypeImgIv);
            if (StringUtils.equals("0", edbook)) {
                gdsTypeImgIv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_gds_type_elec));
            } else {
                gdsTypeImgIv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_gds_type_digital));
            }
        }else {
            setGone(gdsTypeImgIv);
        }
    }

    private void showPackage() {
        if (null == packageList
                || null == packageList.getFixedCombineList()
                || 0 == packageList.getFixedCombineList().size()) {
            setGone(packageLayout);
            return;
        }
        setVisible(packageLayout);

        createScroller(packageViewpager);
        packageViewpager.setTransitionEffect(JazzyViewPager.TransitionEffect.Accordion);
        packageAdapter = new PackageAdapter();
        packageViewpager.setAdapter(packageAdapter);

        createPackagePageIndicator();
        packageAdapter.notifyDataSetChanged();
    }

    private class PackageAdapter extends PagerAdapter {
        private SparseArray<View> views;

        public PackageAdapter() {
            this.views = new SparseArray<>();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position);
            if (null == view) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_package, container, false);
            }
            LinearLayout discountPackageLayout = (LinearLayout) view.findViewById(R.id.discount_package_layout);
            TextView packagePrice = (TextView) view.findViewById(R.id.package_price);
            TextView discountPrice = (TextView) view.findViewById(R.id.discount_price);
            TextView shopBuypackage = (TextView) view.findViewById(R.id.shop_buypackage);
            discountPackageLayout.removeAllViews();

            final GdsPromMatchBaseInfo pack = packageList.getFixedCombineList().get(position);
            switch (position) {
                case 0:
                    showItemPackage(pack, shopBuypackage, discountPackageLayout, packagePrice, discountPrice);
                    break;
                case 1:
                    showItemPackage(pack, shopBuypackage, discountPackageLayout, packagePrice, discountPrice);
                    break;
                case 2:
                    showItemPackage(pack, shopBuypackage, discountPackageLayout, packagePrice, discountPrice);
                    break;
                case 3:
                    showItemPackage(pack, shopBuypackage, discountPackageLayout, packagePrice, discountPrice);
                    break;
                case 4:
                    showItemPackage(pack, shopBuypackage, discountPackageLayout, packagePrice, discountPrice);
                    break;
                case 5:
                    showItemPackage(pack, shopBuypackage, discountPackageLayout, packagePrice, discountPrice);
                    break;
                case 6:
                    showItemPackage(pack, shopBuypackage, discountPackageLayout, packagePrice, discountPrice);
                    break;
                case 7:
                    showItemPackage(pack, shopBuypackage, discountPackageLayout, packagePrice, discountPrice);
                    break;
                case 8:
                    showItemPackage(pack, shopBuypackage, discountPackageLayout, packagePrice, discountPrice);
                    break;
                case 9:
                    showItemPackage(pack, shopBuypackage, discountPackageLayout, packagePrice, discountPrice);
                    break;
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
            views.remove(position);
        }

        @Override
        public int getCount() {
            return packageList.getFixedCombineList() == null ? 0 : packageList.getFixedCombineList().size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        private void showItemPackage(final GdsPromMatchBaseInfo pack, TextView shopBuypackage, LinearLayout discountPackageLayout,
                                     TextView packagePrice,TextView discountPrice) {
            long sumPrice = 0;
            long sumDiscount = 0;
            for (int j = 0; j < pack.getGdsPromMatchSkuVOList().size(); j++) {
                View childView = View.inflate(getActivity(), R.layout.item_discountpackage, null);
                ImageView img = (ImageView) childView.findViewById(R.id.package_img);
                TextView soldPrice = (TextView) childView.findViewById(R.id.package_soldprice);
                TextView name = (TextView) childView.findViewById(R.id.package_name);
                TextView originPrice = (TextView) childView.findViewById(R.id.package_originprice);
                ImageView add = (ImageView) childView.findViewById(R.id.package_add);

                final GdsPromMatchSkuBaseInfo info = pack.getGdsPromMatchSkuVOList().get(j);
                GlideUtil.loadImg(getActivity(),info.getSkuInfo().getMainPicUrl(), img);
                soldPrice.setText(MoneyUtils.GoodListPrice(info.getPrice()));
                sumPrice += info.getPrice();
                sumDiscount += (info.getSkuInfo().getRealPrice() - info.getPrice());
                originPrice.setText(MoneyUtils.GoodListPrice(info.getSkuInfo().getRealPrice()));
                originPrice.setPaintFlags(originPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                name.setText(info.getSkuInfo().getGdsName());

                if (j == (pack.getGdsPromMatchSkuVOList().size()-1)) {
                    setGone(add);
                }

                if (!info.getSkuInfo().getGdsStatus().equals("11")) {
                    shopBuypackage.setClickable(false);
                    shopBuypackage.setBackground(ContextCompat.getDrawable(getActivity(), R.color.gray_c0c1c3));
                }
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(info.getGdsId()));
                        bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(info.getSkuId()));
                        launch(ShopDetailActivity.class, bundle);
                    }
                });
                discountPackageLayout.addView(childView);
            }
            packagePrice.setText(MoneyUtils.GoodListPrice(sumPrice));
            discountPrice.setText("(优惠" + MoneyUtils.GoodListPrice(sumDiscount) + ")");
            shopBuypackage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AppContext.isLogin) {
                        buyPackage(pack);
                    } else {
                        showLoginDialog(getActivity());
                    }
                }
            });
        }

    }

    private void buyPackage(GdsPromMatchBaseInfo pack) {
        Ord001Req req = new Ord001Req();
//            req.setShopId(currentChooseSku.getGdsDetailBaseInfo().getShopId());
        req.setShopId(goodDetail.getGdsDetailBaseInfo().getShopId());
        req.setCartType("01");

        ArrayList<Ord00101Req> ord00101Reqs = new ArrayList<>();

        String skuStr = "";
        for (int i = 0; i < pack.getGdsPromMatchSkuVOList().size(); i++) {
            skuStr += ":" + pack.getGdsPromMatchSkuVOList().get(i).getSkuId();
//            if (i != (pack.getGdsPromMatchSkuVOList().size() - 1)) {
//                skuStr += ":";
//            }
        }
        for (int i = 0; i < pack.getGdsPromMatchSkuVOList().size(); i++) {
            GdsPromMatchSkuBaseInfo info = pack.getGdsPromMatchSkuVOList().get(i);
            Ord00101Req ord00101Req = new Ord00101Req();
            ord00101Req.setShopId(info.getShopId());
            ord00101Req.setGdsId(info.getGdsId());
            ord00101Req.setGdsName(info.getSkuInfo().getGdsName());
            ord00101Req.setGdsType(info.getSkuInfo().getGdsTypeId());
            ord00101Req.setOrderAmount(1L);
//            ord00101Req.setSkuId(currentChooseSku.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId());
            ord00101Req.setSkuId(info.getSkuId());
            ord00101Req.setGroupType("1");
            ord00101Req.setGroupDetail(skuStr);
            ord00101Req.setCategoryCode(info.getSkuInfo().getMainCatgs());
            ord00101Req.setPromId(info.getPromId());
            ord00101Reqs.add(ord00101Req);
        }
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

    //同类推荐的recommend
    private class RecommendAdapter extends PagerAdapter {
        private SparseArray<View> views;

        public RecommendAdapter() {
            this.views = new SparseArray<>();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));//删除页卡
            views.remove(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position);
            if (null == view) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_recommend, null);
            }
            ImageView[] imageViews = new ImageView[3];
            TextView[] textViews = new TextView[3];
            imageViews[0] = (ImageView) view.findViewById(R.id.recommend_img_1);
            imageViews[1] = (ImageView) view.findViewById(R.id.recommend_img_2);
            imageViews[2] = (ImageView) view.findViewById(R.id.recommend_img_3);
            textViews[0] = (TextView) view.findViewById(R.id.recommend_tv_1);
            textViews[1] = (TextView) view.findViewById(R.id.recommend_tv_2);
            textViews[2] = (TextView) view.findViewById(R.id.recommend_tv_3);
            if ((position * 3) > (recommendGoods.size() - 1)) {
                setInvisible(textViews[0], imageViews[0]);
            } else {
                textViews[0].setText(recommendGoods.get(position * 3).getGdsName());
                GlideUtil.loadImg(getActivity(),recommendGoods.get(position * 3).getMainPicUrl(),imageViews[0]);
                GdsDetailBaseInfo goodInfo0 = recommendGoods.get(position * 3);
                imageViews[0].setTag(goodInfo0);
            }

            imageViews[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchToShopDetail((GdsDetailBaseInfo) v.getTag());
                }
            });
            if ((3 * position + 1) > (recommendGoods.size() - 1)) {
                setInvisible(textViews[1], imageViews[1]);
            } else {
                textViews[1].setText(recommendGoods.get(3 * position + 1).getGdsName());
                GlideUtil.loadImg(getActivity(),recommendGoods.get(3 * position + 1).getMainPicUrl(),imageViews[1]);
                GdsDetailBaseInfo goodInfo1 = recommendGoods.get(3 * position + 1);
                imageViews[1].setTag(goodInfo1);
            }
            imageViews[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchToShopDetail((GdsDetailBaseInfo) v.getTag());
                }
            });
            if ((3 * position + 2) > (recommendGoods.size() - 1)) {
                setInvisible(textViews[2], imageViews[2]);
            } else {
                textViews[2].setText(recommendGoods.get(3 * position + 2).getGdsName());
                GlideUtil.loadImg(getActivity(),recommendGoods.get(3 * position + 2).getMainPicUrl(),imageViews[2]);
                GdsDetailBaseInfo goodInfo2 = recommendGoods.get(3 * position + 2);
                imageViews[2].setTag(goodInfo2);
            }
            imageViews[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchToShopDetail((GdsDetailBaseInfo) v.getTag());
                }
            });
            container.addView(view);
            return view;
        }

        private void launchToShopDetail(GdsDetailBaseInfo goodDetail) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(goodDetail.getGdsSkuBaseInfo().getGdsId()));
            bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(goodDetail.getGdsSkuBaseInfo().getId()));
            launch(ShopDetailActivity.class, bundle);
        }

        @Override
        public int getCount() {
            return ((recommendGoods.size() % 3) == 0) ? (recommendGoods.size() / 3) : ((recommendGoods.size() / 3) + 1);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private void showPromotionPopUpWindow(View v) {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow_promotion, null);
        ListView promotionList = (ListView) contentView.findViewById(R.id.promotion_list);
        ImageView promotionClose = (ImageView) contentView.findViewById(R.id.promotion_close);

        final PromotionAdapter adapter = new PromotionAdapter(getActivity(), R.layout.item_promotion);
        promotionList.setAdapter(adapter);
        Gds003Resp gds003Resp = (Gds003Resp) v.getTag();
        if (null == gds003Resp
                || null == gds003Resp.getSaleList()
                || 0 == gds003Resp.getSaleList().size()) {
            ToastUtil.show(getActivity(), "当前没有促销信息");
            return;
        }
        for (int i = 0; i < gds003Resp.getSaleList().size(); i++) {
            PromListBaseInfo proInfo = gds003Resp.getSaleList().get(i);
            adapter.add(proInfo);
        }
        adapter.notifyDataSetChanged();
        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.dialog_animation);
        setBackgroundAlpha(0.5f);
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.backgroun_white));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        promotionClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    private void initComment() {
        Gds005Req req = new Gds005Req();
//        req.setSkuId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId());
        req.setGdsId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getGdsId());
        req.setPageNo(0);
        req.setPageSize(1);
        getJsonService().requestGds005(getActivity(), req, false, new JsonService.CallBack<Gds005Resp>() {
            @Override
            public void oncallback(Gds005Resp gds005Resp) {
                commentList = gds005Resp;
                showComment();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void showComment() {
        if (null == commentList.getGdsEvalRespList() || 0 == commentList.getGdsEvalRespList().size()) {
            setGone(shopLayoutOncomment, shopLayoutOncomment1, shopLayoutOncomment2, shopLayoutOnecommentLine, shopLayoutOnecommentLine1, commentCountLayout);
            setVisible(shopNoComment);
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String staffCode = PrefUtility.get("staffCode", "");
        if (commentList.getCount() == 1) {
            setVisible(shopLayoutOncomment);
            setGone(shopLayoutOncomment1, shopLayoutOncomment2, shopNoComment);
            GlideUtil.loadImg(getActivity(),commentList.getGdsEvalRespList().get(0).getCustPic(), ivHead);
            if (StringUtils.equals(commentList.getGdsEvalRespList().get(0).getStaffName(), staffCode)) {
                tvUsername.setText(commentList.getGdsEvalRespList().get(0).getStaffName());
            } else {
                tvUsername.setText(StringUtils.replace(commentList.getGdsEvalRespList().get(0).getStaffName(),
                        StringUtils.substring(commentList.getGdsEvalRespList().get(0).getStaffName(), 1,
                                commentList.getGdsEvalRespList().get(0).getStaffName().length() - 1), "***"));
            }
            tvContent.setText(commentList.getGdsEvalRespList().get(0).getDetail());
            try {
                tvCommentTime.setText(sdf.format(commentList.getGdsEvalRespList().get(0).getEvaluationTime()));
            } catch (Exception e) {
                tvCommentTime.setText("");
            }
            tvCommentRatingbar.setRating(commentList.getGdsEvalRespList().get(0).getScore());
        }

        if (commentList.getCount() == 2) {
            setVisible(shopLayoutOncomment, shopLayoutOncomment1, shopLayoutOnecommentLine);
            setGone(shopLayoutOncomment2, shopNoComment);
            GlideUtil.loadImg(getActivity(),commentList.getGdsEvalRespList().get(0).getCustPic(), ivHead);
            if (StringUtils.equals(commentList.getGdsEvalRespList().get(0).getStaffName(), staffCode)) {
                tvUsername.setText(commentList.getGdsEvalRespList().get(0).getStaffName());
            } else {
                tvUsername.setText(StringUtils.replace(commentList.getGdsEvalRespList().get(0).getStaffName(),
                        StringUtils.substring(commentList.getGdsEvalRespList().get(0).getStaffName(), 1,
                                commentList.getGdsEvalRespList().get(0).getStaffName().length() - 1), "***"));
            }
            tvContent.setText(commentList.getGdsEvalRespList().get(0).getDetail());
            try {
                tvCommentTime.setText(sdf.format(commentList.getGdsEvalRespList().get(0).getEvaluationTime()));
            } catch (Exception e) {
                tvCommentTime.setText("");
            }
            tvCommentRatingbar.setRating(commentList.getGdsEvalRespList().get(0).getScore());

            GlideUtil.loadImg(getActivity(),commentList.getGdsEvalRespList().get(1).getCustPic(), ivHead1);
            if (StringUtils.equals(commentList.getGdsEvalRespList().get(1).getStaffName(), staffCode)) {
                tvUsername1.setText(commentList.getGdsEvalRespList().get(1).getStaffName());
            } else {
                tvUsername1.setText(StringUtils.replace(commentList.getGdsEvalRespList().get(1).getStaffName(),
                        StringUtils.substring(commentList.getGdsEvalRespList().get(1).getStaffName(), 1,
                                commentList.getGdsEvalRespList().get(1).getStaffName().length() - 1), "***"));
            }
            tvContent1.setText(commentList.getGdsEvalRespList().get(1).getDetail());
            try {
                tvCommentTime1.setText(sdf.format(commentList.getGdsEvalRespList().get(1).getEvaluationTime()));
            } catch (Exception e) {
                tvCommentTime1.setText("");
            }
            tvCommentRatingbar1.setRating(commentList.getGdsEvalRespList().get(1).getScore());
        }

        if (commentList.getCount() >= 3) {
            setVisible(shopLayoutOncomment, shopLayoutOncomment1, shopLayoutOncomment2, shopLayoutOnecommentLine, shopLayoutOnecommentLine1);
            setGone(shopNoComment);
            GlideUtil.loadImg(getActivity(),commentList.getGdsEvalRespList().get(0).getCustPic(), ivHead);
            if (StringUtils.equals(commentList.getGdsEvalRespList().get(0).getStaffName(), staffCode)) {
                tvUsername.setText(commentList.getGdsEvalRespList().get(0).getStaffName());
            } else {
                tvUsername.setText(StringUtils.replace(commentList.getGdsEvalRespList().get(0).getStaffName(),
                        StringUtils.substring(commentList.getGdsEvalRespList().get(0).getStaffName(), 1,
                                commentList.getGdsEvalRespList().get(0).getStaffName().length() - 1), "***"));
            }
            tvContent.setText(commentList.getGdsEvalRespList().get(0).getDetail());
            try {
                tvCommentTime.setText(sdf.format(commentList.getGdsEvalRespList().get(0).getEvaluationTime()));
            } catch (Exception e) {
                tvCommentTime.setText("");
            }
            tvCommentRatingbar.setRating(commentList.getGdsEvalRespList().get(0).getScore());

            GlideUtil.loadImg(getActivity(),commentList.getGdsEvalRespList().get(1).getCustPic(), ivHead1);
            if (StringUtils.equals(commentList.getGdsEvalRespList().get(1).getStaffName(), staffCode)) {
                tvUsername1.setText(commentList.getGdsEvalRespList().get(1).getStaffName());
            } else {
                tvUsername1.setText(StringUtils.replace(commentList.getGdsEvalRespList().get(1).getStaffName(),
                        StringUtils.substring(commentList.getGdsEvalRespList().get(1).getStaffName(), 1,
                                commentList.getGdsEvalRespList().get(1).getStaffName().length() - 1), "***"));
            }
            tvContent1.setText(commentList.getGdsEvalRespList().get(1).getDetail());
            try {
                tvCommentTime1.setText(sdf.format(commentList.getGdsEvalRespList().get(1).getEvaluationTime()));
            } catch (Exception e) {
                tvCommentTime1.setText("");
            }
            tvCommentRatingbar1.setRating(commentList.getGdsEvalRespList().get(1).getScore());

            GlideUtil.loadImg(getActivity(),commentList.getGdsEvalRespList().get(2).getCustPic(), ivHead2);
            if (StringUtils.equals(commentList.getGdsEvalRespList().get(2).getStaffName(), staffCode)) {
                tvUsername2.setText(commentList.getGdsEvalRespList().get(2).getStaffName());
            } else {
                tvUsername2.setText(StringUtils.replace(commentList.getGdsEvalRespList().get(2).getStaffName(),
                        StringUtils.substring(commentList.getGdsEvalRespList().get(2).getStaffName(), 1,
                                commentList.getGdsEvalRespList().get(2).getStaffName().length() - 1), "***"));
            }
            tvContent2.setText(commentList.getGdsEvalRespList().get(2).getDetail());
            try {
                tvCommentTime2.setText(sdf.format(commentList.getGdsEvalRespList().get(2).getEvaluationTime()));
            } catch (Exception e) {
                tvCommentTime2.setText("");
            }
            tvCommentRatingbar2.setRating(commentList.getGdsEvalRespList().get(2).getScore());
        }
        tvAllComment.setText("全部评论(" + commentList.getCount() + "评论)");
        tvAllComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopDetailActivity shopDetailActivity = (ShopDetailActivity) getActivity();
                shopDetailActivity.goToComment();
            }
        });
    }

    private void showPopupWindow(View v) {
        selectProsCount = 0;
        selectedProp.clear();
        // 一个自定义的布局，作为显示的内容
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow_choosegood, null);
        updateNormalPopupContentView(contentView);

        ListView goodList = (ListView) contentView.findViewById(R.id.choosegood_listview);
        TextView goodMinus = (TextView) contentView.findViewById(R.id.choosegood_minus);
        final TextView goodAmount = (TextView) contentView.findViewById(R.id.choosegood_amount);
        TextView goodPlus = (TextView) contentView.findViewById(R.id.choosegood_plus);
        Button goodAddToCart = (Button) contentView.findViewById(R.id.choosegood_add);

        goodAmount.setKeyListener(null);//禁止输入内容
        goodMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(goodAmount.getText().toString());
                if (value == 1) {
                    return;
                }
                goodAmount.setText(String.valueOf(--value));
            }
        });
        goodPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(goodAmount.getText().toString());
                goodAmount.setText(String.valueOf(++value));
            }
        });
        goodList.setDivider(null);
        NormalAdapter adapter1 = new NormalAdapter(getActivity(), R.layout.item_choosegood);
        goodList.setAdapter(adapter1);
        for (int i = 0; i < goodDetail.getGdsDetailBaseInfo().getParams().size(); i++) {
            GdsPropBaseInfo propBaseInfo = goodDetail.getGdsDetailBaseInfo().getParams().get(i);
            adapter1.add(propBaseInfo);
        }
        adapter1.notifyDataSetChanged();

        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        goodAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((null != goodDetail)
                        && (null != goodDetail.getGdsDetailBaseInfo())
                        && (goodDetail.getGdsDetailBaseInfo().getParams().size() == selectedProp.size())) {
                    addToCartSingle(popupWindow, goodAmount.getText().toString());
                } else {
                    ToastUtil.show(getActivity(), "请先选择所有商品属性");
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point point = new Point();
            getActivity().getWindowManager().getDefaultDisplay().getSize(point);
            popupWindow.setWidth(point.x * 9 / 10);
            popupWindow.setHeight(point.y * 90 / 100);
        } else {
            int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
            int height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
            popupWindow.setWidth(width * 9 / 10);
            popupWindow.setHeight(height * 90 / 100);
        }

        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.background_login));
        setBackgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        popupWindow.showAtLocation(v, Gravity.END | Gravity.BOTTOM, 0, 0);
    }

    private void addToCartSingle(final PopupWindow popupWindow, String amount) {
        if ((goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getRealAmount() - Integer.valueOf(amount)) < 10) {
            ToastUtil.show(getActivity(), "库存不足，加入购物车失败！");
            return;
        }
        Ord001Req req = new Ord001Req();
        req.setShopId(currentChooseSku.getGdsDetailBaseInfo().getShopId());
        req.setCartType("01");

        List<Ord00101Req> ord00101Reqs = new ArrayList<>();
        Ord00101Req ord00101Req = new Ord00101Req();
        ord00101Req.setShopId(currentChooseSku.getGdsDetailBaseInfo().getShopId());
        ord00101Req.setGdsId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getGdsId());
        ord00101Req.setGdsName(goodDetail.getGdsDetailBaseInfo().getGdsName());
        ord00101Req.setGdsType(goodDetail.getGdsDetailBaseInfo().getGdsTypeId());
        ord00101Req.setOrderAmount(Long.valueOf(amount));
        ord00101Req.setSkuId(currentChooseSku.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId());
        ord00101Req.setGroupType("0");
        ord00101Req.setGroupDetail(String.valueOf(currentChooseSku.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId()));
        ord00101Req.setCategoryCode(currentChooseSku.getGdsDetailBaseInfo().getMainCatgs());
        ord00101Reqs.add(ord00101Req);
        req.setOrd00101Reqs(ord00101Reqs);
        getJsonService().requestOrd001(getActivity(), req, true, new JsonService.CallBack<Ord001Resp>() {
            @Override
            public void oncallback(Ord001Resp ord001Resp) {
                ToastUtil.showIconToast(getActivity(), "加入购物车成功");
                popupWindow.dismiss();
            }

            @Override
            public void onErro(AppHeader header) {
            }
        });
    }

    /**
     * 底部 有单品的时候添加到购物车
     */
    private void addToCartSingle() {
        Ord001Req req = new Ord001Req();
        req.setShopId(currentChooseSku.getGdsDetailBaseInfo().getShopId());
        req.setCartType("01");

        List<Ord00101Req> ord00101Reqs = new ArrayList<>();
        Ord00101Req ord00101Req = new Ord00101Req();
        ord00101Req.setShopId(currentChooseSku.getGdsDetailBaseInfo().getShopId());
        ord00101Req.setGdsId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getGdsId());
        ord00101Req.setGdsName(goodDetail.getGdsDetailBaseInfo().getGdsName());
        ord00101Req.setGdsType(goodDetail.getGdsDetailBaseInfo().getGdsTypeId());
        ord00101Req.setOrderAmount(1L);
        ord00101Req.setSkuId(currentChooseSku.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId());
        ord00101Req.setGroupType("0");
        ord00101Req.setGroupDetail(String.valueOf(currentChooseSku.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getId()));
        ord00101Req.setCategoryCode(currentChooseSku.getGdsDetailBaseInfo().getMainCatgs());
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

    /**
     * 获取购物车数量
     */
    private void getCartGoodsNum() {
        Ord018Req req = new Ord018Req();
        getJsonService().requestOrd018(getActivity(), req, false, new JsonService.CallBack<Ord018Resp>() {
            @Override
            public void oncallback(Ord018Resp ord018Resp) {
                if (ord018Resp != null) {
                    Long cartItemNum = ord018Resp.getOrdCartItemNum();
                    if (cartItemNum != null && cartItemNum != 0) {
                        setVisible(tvCartNum);
                        if (cartItemNum > 99) {
                            tvCartNum.setText("99+");
                        } else {
                            tvCartNum.setText(String.valueOf(cartItemNum));
                        }
                    } else {
                        setGone(tvCartNum);
                    }
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

    private void updateNormalPopupContentView(View contentView) {
        ImageView goodImage = (ImageView) contentView.findViewById(R.id.choosegood_img);
        TextView goodPrice = (TextView) contentView.findViewById(R.id.choosegood_price);
        TextView goodCode = (TextView) contentView.findViewById(R.id.choosegood_code);
        TextView goodDesc = (TextView) contentView.findViewById(R.id.choosegood_des);
        GlideUtil.loadImg(getActivity(),currentChooseSku.getMainPicUrl(),goodImage);
        goodPrice.setText(MoneyUtils.GoodListPrice(currentChooseSku.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getDiscountPrice()));
        goodCode.setText(currentChooseSku.getGdsDetailBaseInfo().getGdsName());
        goodDesc.setText(currentChooseSku.getGdsDetailBaseInfo().getGdsSubHead());
        setGone(goodDesc);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //每个属性选择的listview adapter
    private class NormalAdapter extends ListViewAdapter {
        public NormalAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public void initView(View view, int position, View convertView) {
            TextView propType = (TextView) view.findViewById(R.id.normal_item_type);
            final CustomGridView gridView = (CustomGridView) view.findViewById(R.id.normal_item_gridview);
            final ProItemAdapter itemAdapter = new ProItemAdapter(getActivity(), R.layout.item_prop_two);
            gridView.setAdapter(itemAdapter);
            final GdsPropBaseInfo data = (GdsPropBaseInfo) getItem(position);
            if (data != null) {
                propType.setText(data.getPropName());
                for (int i = 0; i < data.getValues().size(); i++) {
                    GdsPropValueBaseInfo propInfo = data.getValues().get(i);
                    itemAdapter.add(propInfo);
                }
            }

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i < gridView.getChildCount(); i++) {
                        if (i == position) {
                            gridView.getChildAt(i).setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_round_delbutton));
                        } else {
                            gridView.getChildAt(i).setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_round_textview));
                        }
                    }
                    GdsPropValueBaseInfo info = (GdsPropValueBaseInfo) itemAdapter.getItem(position);
//                    for (GdsPropValueBaseInfo prop : data.getValues()) {
//                        if (!info.getId().equals(prop.getId())) {
//                            view.setSelected(false);
//                        }
//                    }
//                    view.setSelected(true);
                    selectedProp.put(data.getPropName(), info);
                    if (selectedProp.size() == goodDetail.getGdsDetailBaseInfo().getParams().size()) {
                        refreshSku();
                    }
                }

            });
            gridView.setSelection(0);
            itemAdapter.notifyDataSetChanged();
        }

        //属性项的类
        private class ProItemAdapter extends ListViewAdapter {

            public ProItemAdapter(Context context, int resource) {
                super(context, resource);
            }

            @Override
            public void initView(View view, int position, View convertView) {
                TextView propValue = (TextView) view.findViewById(R.id.prop_textview);
                GdsPropValueBaseInfo info = (GdsPropValueBaseInfo) getItem(position);
                List<GdsPropBaseInfo> gdsPropBaseInfoList = goodDetail.getGdsDetailBaseInfo().getParams();
                GdsPropValueBaseInfo checkInfo = gdsPropBaseInfoList.get(0).getValues().get(0);
                if (info != null) {
                    if (StringUtils.equals(checkInfo.getPropValue(), info.getPropValue())) {
                        propValue.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.shape_round_delbutton));
                        selectedProp.put(gdsPropBaseInfoList.get(0).getPropName(), info);
                        if (selectedProp.size() == goodDetail.getGdsDetailBaseInfo().getParams().size()) {
                            refreshSku();
                        }
                    }
                    propValue.setText(info.getPropValue());
                }
            }
        }
    }

    /**
     * 用于单品选择之后更新价格信息
     */
    private void refreshSku() {
        final Gds008Req gds008Req = new Gds008Req();
        gds008Req.setGdsId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getGdsId());
        gds008Req.setShopId(goodDetail.getGdsDetailBaseInfo().getShopId());
        List<GdsSku2PropPropIdxBaseInfo> selectPros = new ArrayList<>();
        for (int i =0; i < goodDetail.getGdsDetailBaseInfo().getParams().size(); i++) {
            GdsPropBaseInfo pro = goodDetail.getGdsDetailBaseInfo().getParams().get(i);
            GdsSku2PropPropIdxBaseInfo info = new GdsSku2PropPropIdxBaseInfo();
            GdsPropValueBaseInfo inputInfo = selectedProp.get(pro.getPropName());
            info.setPropValueId(inputInfo.getId());
            info.setPropValue(inputInfo.getPropValue());
            info.setShopId(goodDetail.getGdsDetailBaseInfo().getShopId());
            info.setGdsId(goodDetail.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getGdsId());
            info.setPropName(pro.getPropName());
            info.setPropId(pro.getId());
//            selectPros.get(pro.getPropName()).
//            info.setPropId(goodDetail.getGdsDetailBaseInfo().getParams().get(0).getValues().get(0).getPropId());
//            info.setPropId(goodDetail.getGdsDetailBaseInfo().getParams().get(0).getId());
//            info.setPropId(pro.getValues().get(0).getPropId());
            selectPros.add(info);
        }
        gds008Req.setGdsPropValueReqDTOs(selectPros);
        getJsonService().requestGds008(getActivity(), gds008Req, true, new JsonService.CallBack<Gds008Resp>() {
            @Override
            public void oncallback(Gds008Resp gds008Resp) {
                currentChooseSku = gds008Resp;
                updateSkuUI();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void updateSkuUI() {
        updateNormalPopupContentView(contentView);
        setGoodNormalDesBySku(goodNormalDes);
        setGoodNormalSoldPrice(goodPrice);
    }

    private void setGoodNormalSoldPrice(TextView goodPrice) {
        goodPrice.setText(MoneyUtils.GoodListPrice(currentChooseSku.getGdsDetailBaseInfo().getGdsSkuBaseInfo().getDiscountPrice()));
    }

    private class PromotionAdapter extends ListViewAdapter {

        public PromotionAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public void initView(View view, int position, View convertView) {
            PromListBaseInfo proInfo = (PromListBaseInfo) getItem(position);
            TextView item_promotion_label = (TextView) view.findViewById(R.id.item_promotion_label);
            TextView item_promotion_desc = (TextView) view.findViewById(R.id.item_promotion_desc);
            if (proInfo != null) {
                if (proInfo.getPromBaseInfo() != null) {
                    item_promotion_label.setText(proInfo.getPromBaseInfo().getPromTypeName());
                    item_promotion_desc.setText(proInfo.getPromBaseInfo().getPromTheme());
                }
            }
        }
    }

}
