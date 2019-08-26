package com.ailk.pmph.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.ai.ecp.app.req.Gds001Req;
import com.ai.ecp.app.resp.Gds001Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.Helper;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.fragment.CommentFragment;
import com.ailk.pmph.ui.fragment.ShopDetailFragment;
import com.ailk.pmph.ui.fragment.ShopFragment;
import com.ailk.pmph.ui.view.ShareDialog;

import org.apache.commons.lang.StringUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 类注释:商品详情
 * 项目名:PMPH
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/3/16 19:38
 */
public class ShopDetailActivity extends BaseActivity implements PlatformActionListener, View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.layout_shop)
    LinearLayout goodLayout;
    @BindView(R.id.layout_detail)
    LinearLayout detailLayout;
    @BindView(R.id.layout_comment)
    LinearLayout commentLayout;
    @BindView(R.id.iv_shopline)
    View ivShopLine;
    @BindView(R.id.iv_detailline)
    View ivDetailLine;
    @BindView(R.id.iv_commentline)
    View ivCommentLine;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_more)
    ImageView ivMore;

    private ShopFragment shopFragment;
    private ShopDetailFragment shopDetailFragment;
    private CommentFragment commentFragment;

    private static Context mContext;

    private PopupWindow popupWindow;
    private ShareDialog shareDialog;

    private Gds001Resp goodDetail;
    private String mSkuId;
    private String mGdsId;
    private String mAdid;
    private String mLinkType;

    private static final String shareText = "我在人卫智慧服务商城发现了一个非常不错的商品，快来看看吧";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_shop_detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initView() {
        mContext = ShopDetailActivity.this;
        mGdsId = getIntent().getStringExtra(Constant.SHOP_GDS_ID);
        mSkuId = getIntent().getStringExtra(Constant.SHOP_SKU_ID);
        mAdid = getIntent().getStringExtra(Constant.SHOP_ADID);
        mLinkType = getIntent().getStringExtra(Constant.SHOP_LINK_TYPE);
        if (StringUtils.isEmpty(mSkuId)) {
            mSkuId = "";
        }
        if (shopFragment == null) {
            shopFragment = new ShopFragment();
            addFragment(shopFragment);
            showFragment(shopFragment);
        } else {
            showFragment(shopFragment);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.iv_back, R.id.layout_shop, R.id.layout_detail, R.id.layout_comment, R.id.iv_share, R.id.iv_more})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.layout_shop:
                goToGood();
                break;
            case R.id.layout_detail:
                goToDetail();
                break;
            case R.id.layout_comment:
                goToComment();
                break;
            case R.id.iv_share:
                getGoodDetail();
                showShareDialog();
                break;
            case R.id.iv_more:
                showPopupWindow(v);
                break;
        }
    }

    /**
     * 跳转至商品
     */
    public void goToGood() {
        setVisible(ivShopLine);
        setInvisible(ivCommentLine,ivDetailLine);
        if (shopFragment == null) {
            shopFragment = new ShopFragment();
            if (!shopFragment.isAdded()) {
                addFragment(shopFragment);
            }
            showFragment(shopFragment);
        } else {
            if (shopFragment.isHidden()) {
                showFragment(shopFragment);
            }
        }
    }

    /**
     * 跳转至详情
     */
    public void goToDetail() {
        setVisible(ivDetailLine);
        setInvisible(ivShopLine,ivCommentLine);
        if (shopDetailFragment == null) {
            shopDetailFragment = ShopDetailFragment.newInstance();
            if (!shopDetailFragment.isAdded()) {
                addFragment(shopDetailFragment);
            }
            showFragment(shopDetailFragment);
        } else {
            if (shopDetailFragment.isHidden()) {
                showFragment(shopDetailFragment);
            }
        }
    }

    /**
     * 跳转至评论
     */
    public void goToComment() {
        setVisible(ivCommentLine);
        setInvisible(ivShopLine,ivDetailLine);
        if (commentFragment == null) {
            commentFragment = CommentFragment.newInstance();
            if (!commentFragment.isAdded()) {
                addFragment(commentFragment);
            }
            showFragment(commentFragment);
        } else {
            if (commentFragment.isHidden()) {
                showFragment(commentFragment);
            }
        }
    }

    /**
     * 获取商品详情
     */
    private void getGoodDetail() {
        Gds001Req req = new Gds001Req();
        if (StringUtils.isNotEmpty(mGdsId)) {
            req.setGdsId(Long.parseLong(mGdsId));
        }
        if (StringUtils.isNotEmpty(mSkuId)) {
            req.setSkuId(Long.parseLong(mSkuId));
        }
        getJsonService().requestGds001(ShopDetailActivity.this, req, false, new JsonService.CallBack<Gds001Resp>() {
            @Override
            public void oncallback(Gds001Resp gds001Resp) {
                goodDetail = gds001Resp;
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void showShareDialog() {
        shareDialog = new ShareDialog(ShopDetailActivity.this,R.style.coupon_dialog);
        shareDialog.setCancelable(true);
        shareDialog.setCanceledOnTouchOutside(true);
        shareDialog.show();
        shareDialog.setOnGridViewItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
                if (item.get("itemText").equals("微信好友"))
                {
                    Wechat.ShareParams sp = new Wechat.ShareParams();
                    ShareSDK.getPlatform(Wechat.NAME);
                    sp.setShareType(Platform.SHARE_WEBPAGE);
                    sp.setTitle(goodDetail.getGdsDetailBaseInfo().getGdsName());
                    sp.setText(shareText);
                    if (goodDetail.getGdsDetailBaseInfo().getImageUrlList() != null
                            && goodDetail.getGdsDetailBaseInfo().getImageUrlList().size() != 0) {
                        sp.setImageUrl(goodDetail.getGdsDetailBaseInfo().getImageUrlList().get(0));
                    }
                    sp.setUrl(goodDetail.getGdsDetailBaseInfo().getShareUrl());
                    Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                    wechat.setPlatformActionListener(ShopDetailActivity.this);
                    wechat.share(sp);
                }
                else if (item.get("itemText").equals("朋友圈"))
                {
                    WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
                    sp.setShareType(Platform.SHARE_WEBPAGE);
                    sp.setTitle(goodDetail.getGdsDetailBaseInfo().getGdsName());
                    sp.setText(shareText);
                    if (goodDetail.getGdsDetailBaseInfo().getImageUrlList() != null
                            && goodDetail.getGdsDetailBaseInfo().getImageUrlList().size() != 0) {
                        sp.setImageUrl(goodDetail.getGdsDetailBaseInfo().getImageUrlList().get(0));
                    }
                    sp.setUrl(goodDetail.getGdsDetailBaseInfo().getShareUrl());
                    Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
                    wechatMoments.setPlatformActionListener(ShopDetailActivity.this);
                    wechatMoments.share(sp);
                }
                else if (item.get("itemText").equals("新浪微博"))
                {
                    SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
                    sp.setTitle(goodDetail.getGdsDetailBaseInfo().getGdsName());
                    sp.setText(shareText + goodDetail.getGdsDetailBaseInfo().getShareUrl());
                    if (goodDetail.getGdsDetailBaseInfo().getImageUrlList() != null
                            && goodDetail.getGdsDetailBaseInfo().getImageUrlList().size() != 0) {
                        sp.setImageUrl(goodDetail.getGdsDetailBaseInfo().getImageUrlList().get(0));
                    }
                    sp.setTitleUrl(goodDetail.getGdsDetailBaseInfo().getShareUrl());
                    Platform sinaWeibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                    sinaWeibo.setPlatformActionListener(ShopDetailActivity.this);
                    sinaWeibo.share(sp);
                }
                else if (item.get("itemText").equals("QQ好友"))
                {
                    QQ.ShareParams sp = new QQ.ShareParams();
                    sp.setTitle(goodDetail.getGdsDetailBaseInfo().getGdsName());
                    sp.setText(shareText);
                    if (goodDetail.getGdsDetailBaseInfo().getImageUrlList() != null
                            && goodDetail.getGdsDetailBaseInfo().getImageUrlList().size() != 0) {
                        sp.setImageUrl(goodDetail.getGdsDetailBaseInfo().getImageUrlList().get(0));
                    }
                    sp.setTitleUrl(goodDetail.getGdsDetailBaseInfo().getShareUrl());
                    Platform qq = ShareSDK.getPlatform(QQ.NAME);
                    qq.setPlatformActionListener(ShopDetailActivity.this);
                    qq.share(sp);
                }
                else if (item.get("itemText").equals("QQ空间"))
                {
                    QZone.ShareParams sp = new QZone.ShareParams();
                    sp.setTitle(goodDetail.getGdsDetailBaseInfo().getGdsName());
                    sp.setText(shareText);
                    if (goodDetail.getGdsDetailBaseInfo().getImageUrlList() != null
                            && goodDetail.getGdsDetailBaseInfo().getImageUrlList().size() != 0) {
                        sp.setImageUrl(goodDetail.getGdsDetailBaseInfo().getImageUrlList().get(0));
                    }
                    sp.setTitleUrl(goodDetail.getGdsDetailBaseInfo().getShareUrl());
                    Platform qzone = ShareSDK.getPlatform(QZone.NAME);
                    qzone.setPlatformActionListener(ShopDetailActivity.this);
                    qzone.share(sp);
                }
                else if (item.get("itemText").equals("复制链接"))
                {
                    ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("url", goodDetail.getGdsDetailBaseInfo().getShareUrl());
                    cmb.setPrimaryClip(clipData);
                    ToastUtil.show(ShopDetailActivity.this, "复制成功");
                    String resultString = "";
                    if (!cmb.hasPrimaryClip()) {
                        ToastUtil.show(ShopDetailActivity.this, "剪切板为空");
                    } else {
                        ClipData clip = cmb.getPrimaryClip();
                        int count = clip.getItemCount();
                        for (int i = 0; i < count; i++) {
                            ClipData.Item it = clip.getItemAt(i);
                            CharSequence str = it.coerceToText(ShopDetailActivity.this);
                            resultString += str;
                        }
                    }
                    LogUtil.e("result=============" + resultString);
                }
                shareDialog.dismiss();
            }
        });
    }

    private void showPopupWindow(View anchor) {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        } else {
            View popView = LayoutInflater.from(this).inflate(R.layout.pop_message_layout, null);
            LinearLayout messageLayout = (LinearLayout) popView.findViewById(R.id.message_layout);
            LinearLayout homeLayout = (LinearLayout) popView.findViewById(R.id.home_layout);
            LinearLayout searchLayout = (LinearLayout) popView.findViewById(R.id.search_layout);
            LinearLayout collectLayout = (LinearLayout) popView.findViewById(R.id.collect_layout);
            LinearLayout shareLayout = (LinearLayout) popView.findViewById(R.id.share_layout);
            popupWindow = new PopupWindow(popView, Helper.getMetrics(this).widthPixels / 3, LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.popup_window_animation);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupWindow.showAsDropDown(anchor, -(popupWindow.getWidth()/2+100), 0);
            messageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                    if (!AppContext.isLogin) {
                        showLoginDialog(ShopDetailActivity.this);
                    } else {
                        launch(MessageActivity.class);
                    }
                }
            });
            homeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                    launch(MainActivity.class);
                }
            });
            searchLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                    launch(SearchActivity.class);
                }
            });
            collectLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                    if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                        if (!AppContext.isLogin) {
                            showLoginDialog(ShopDetailActivity.this);
                        } else {
                            launch(PmphCollectionActivity.class);
                        }
                    } else {
                        if (!AppContext.isLogin) {
                            showLoginDialog(ShopDetailActivity.this);
                        } else {
                            launch(MyCollectionActivity.class);
                        }
                    }
                }
            });
            shareLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                    getGoodDetail();
                    showShareDialog();
                }
            });
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if (platform.getName().equals(SinaWeibo.NAME))
        {
            handler.sendEmptyMessage(1);
        }
        else if (platform.getName().equals(Wechat.NAME))
        {
            handler.sendEmptyMessage(2);
        }
        else if (platform.getName().equals(WechatMoments.NAME))
        {
            handler.sendEmptyMessage(3);
        }
        else if (platform.getName().equals(QQ.NAME))
        {
            handler.sendEmptyMessage(4);
        }
        else if (platform.getName().equals(QZone.NAME))
        {
            handler.sendEmptyMessage(5);
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        throwable.printStackTrace();
        Message msg = new Message();
        msg.what = 6;
        msg.obj = throwable.getMessage();
        handler.sendMessage(msg);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        handler.sendEmptyMessage(5);
    }

    private final MyHandler handler = new MyHandler(this);

    private static class MyHandler extends Handler {

        private final WeakReference<ShopDetailActivity> mActivity;

        public MyHandler(ShopDetailActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ShopDetailActivity activity = mActivity.get();
            if (null != activity) {
                String shareSuccessResult = "分享成功";
                switch (msg.what) {
                    case 1:
//                        ToastUtil.show(mContext, shareSuccessResult);
                        break;
                    case 2:
//                        ToastUtil.show(mContext, shareSuccessResult);
                        break;
                    case 3:
//                        ToastUtil.show(mContext, shareSuccessResult);
                        break;
                    case 4:
//                        ToastUtil.show(mContext, shareSuccessResult);
                        break;
                    case 5:
//                        ToastUtil.show(mContext, shareSuccessResult);
                        break;
                    case 6:
                        LogUtil.e("weibo error============"+msg.obj);
                        ToastUtil.show(mContext, (String) msg.obj);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 添加Fragment
     **/
    public void addFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(R.id.main_detail, fragment);
            ft.commit();
        }
    }

    /**
     * 显示Fragment
     **/
    public void showFragment(Fragment fragment) {
        if (null == fragment.getArguments()) {
            Bundle args = new Bundle();
            fragment.setArguments(args);
        }
        fragment.getArguments().putString(Constant.SHOP_SKU_ID, mSkuId);
        fragment.getArguments().putString(Constant.SHOP_GDS_ID, mGdsId);
        fragment.getArguments().putString(Constant.SHOP_ADID, mAdid);
        fragment.getArguments().putString(Constant.SHOP_LINK_TYPE, mLinkType);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        if (shopFragment != null) {
            ft.hide(shopFragment);
        }
        if (shopDetailFragment != null) {
            ft.hide(shopDetailFragment);
        }
        if (commentFragment != null) {
            ft.hide(commentFragment);
        }
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
