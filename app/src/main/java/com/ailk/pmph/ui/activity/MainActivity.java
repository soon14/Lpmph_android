package com.ailk.pmph.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Cms004Req;
import com.ai.ecp.app.req.Gds017Req;
import com.ai.ecp.app.req.Ord018Req;
import com.ai.ecp.app.resp.Cms004Resp;
import com.ai.ecp.app.resp.Gds017Resp;
import com.ai.ecp.app.resp.Ord018Resp;
import com.ai.ecp.app.resp.gds.AppVersionInfo;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.fragment.ClassificationFragment;
import com.ailk.pmph.ui.fragment.RankingListFragment;
import com.ailk.pmph.ui.fragment.SortFragment;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.TDevice;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.fragment.HomeFragment;
import com.ailk.pmph.ui.fragment.PersonalCenterFragment;
import com.ailk.pmph.ui.fragment.ShopCartFragment;
import com.ailk.pmph.utils.UpdateUtils;

import org.apache.commons.lang.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.ll_sort)
    LinearLayout llSort;
    @BindView(R.id.ll_rank)
    LinearLayout llRank;
    @BindView(R.id.ll_cart)
    LinearLayout llCart;
    @BindView(R.id.ll_personal)
    LinearLayout llPersonal;

    @BindView(R.id.iv_menu_0)
    ImageView ivMenu0;
    @BindView(R.id.iv_menu_1)
    ImageView ivMenu1;
    @BindView(R.id.iv_menu_2)
    ImageView ivMenu2;
    @BindView(R.id.iv_menu_3)
    ImageView ivMenu3;
    @BindView(R.id.iv_menu_4)
    ImageView ivMenu4;

    @BindView(R.id.bt_menu_0_text)
    TextView tvMenuText0;
    @BindView(R.id.bt_menu_1_text)
    TextView tvMenuText1;
    @BindView(R.id.bt_menu_2_text)
    TextView tvMenuText2;
    @BindView(R.id.bt_menu_3_text)
    TextView tvMenuText3;
    @BindView(R.id.bt_menu_4_text)
    TextView tvMenuText4;
    @BindView(R.id.tv_good_num)
    TextView tvGoodNum;

    /**
     * 首页界面
     */
    private HomeFragment homeFragment;
    /**
     * 分类界面
     */
    private SortFragment sortFragment;
    /**
     * 非核心版本分类界面
     */
    private ClassificationFragment classificationFragment;
    /**
     * 排行榜界面
     */
    private RankingListFragment rankFragment;
    /**
     * 购物车界面
     */
    private ShopCartFragment cartFragment;
    /**
     * 个人中心界面
     */
    private PersonalCenterFragment userCenterFragment;

    private long currentBackPressedTime = 0;
    private static final int BACK_PRESSED_INTERVAL = 2000;
    public static final String CART_GOODS_NUM = "cart_goods_num";
    public static final String DIMISS_CART_GOODS_NUM = "dimiss_cart_goods_num";
    private UpdateUtils mUpdate;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        // 初始化默认显示的界面
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
            addFragment(homeFragment);
            showFragment(homeFragment);
        } else {
            showFragment(homeFragment);
        }

        // 设置默认首页为点击时的图片、文字
        ivMenu0.setImageResource(R.drawable.guide_home_on);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvMenuText0.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            tvMenuText0.setTextColor(ContextCompat.getColor(this, R.color.red));
        }

        if (!AppContext.isDebugAble()
                && (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)
                || StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_ASIA_INFO))) {
            checkUpdateInfo();
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CART_GOODS_NUM);
        registerReceiver(receiver,intentFilter);

        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(DIMISS_CART_GOODS_NUM);
        registerReceiver(receiver1, intentFilter1);
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equals(intent.getAction(), CART_GOODS_NUM)) {
                Bundle bundle = intent.getExtras();
                Ord018Resp ord018Resp = (Ord018Resp) bundle.get("ord018Resp");
                if (ord018Resp != null) {
                    Long cartItemNum = ord018Resp.getOrdCartItemNum();
                    if (cartItemNum != 0) {
                        setVisible(tvGoodNum);
                        if (cartItemNum > 99) {
                            tvGoodNum.setText("99+");
                        } else {
                            tvGoodNum.setText(String.valueOf(cartItemNum));
                        }
                    } else {
                        setGone(tvGoodNum);
                    }
                }
            }
        }
    };

    public BroadcastReceiver receiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equals(intent.getAction(), DIMISS_CART_GOODS_NUM)) {
                setGone(tvGoodNum);
            }
        }
    };

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        int id = getIntent().getIntExtra("id", 0);
        if (id == 1) {
            if (homeFragment == null) {
                homeFragment = HomeFragment.newInstance();
                addFragment(homeFragment);
                showFragment(homeFragment);
            } else {
                showFragment(homeFragment);
            }
        }
        super.onResume();
    }

    /**
     * 检查更新
     */
    private void checkUpdateInfo() {
        Gds017Req request = new Gds017Req();
        request.setVerOs("01");
        request.setVerProgram("RW_MALL");
        Long verNo = (long) TDevice.getVersionCode(this);
        request.setVerNo(verNo);
        getJsonService().requestGds017(this, request, false, new JsonService.CallBack<Gds017Resp>() {
            @Override
            public void oncallback(Gds017Resp gds017Resp) {
                final AppVersionInfo appVersionInfo = gds017Resp.getAppVersionInfo();
                String verDetail = appVersionInfo.getVerDetail();
                if (appVersionInfo.getIfFore()) {
                    DialogAnotherUtil.showCustomAlertDialogWithMessage(MainActivity.this, "版本升级",
                            verDetail, "强制更新", "取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DialogUtil.dismissDialog();
                                    mUpdate = new UpdateUtils(MainActivity.this, appVersionInfo.getVerUrl());
                                    mUpdate.checkUpdateInfo();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DialogUtil.dismissDialog();
                                    MainActivity.this.finish();
                                }
                            });
                } else {
                    if (appVersionInfo.getIfUpdate()) {
                        DialogAnotherUtil.showCustomAlertDialogWithMessage(MainActivity.this, "版本升级",
                                verDetail, "更新", "取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mUpdate = new UpdateUtils(MainActivity.this, appVersionInfo.getVerUrl());
                                        mUpdate.checkUpdateInfo();
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DialogUtil.dismissDialog();
                                    }
                                });
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 蒲公英检查更新
     */
//    private void checkUpdateInfo() {
//        PgyUpdateManager.register(MainActivity.this,
//                new UpdateManagerListener() {
//                    @Override
//                    public void onUpdateAvailable(final String result) {
//                        final AppBean appBean = getAppBeanFromString(result);
//                        DialogAnotherUtil.showCustomAlertDialogWithMessage(MainActivity.this, "发现新版本", appBean.getReleaseNote(), "更新", "取消"
//                                , new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        DialogAnotherUtil.dismissDialog();
//                                        startDownloadTask(
//                                                MainActivity.this,
//                                                appBean.getDownloadURL());
//                                    }
//                                }, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        DialogAnotherUtil.dismissDialog();
//                                    }
//                                });
//                    }
//
//                    @Override
//                    public void onNoUpdateAvailable() {
//                    }
//                });
//        String firToken = "ffc323864ef68f7db27f8e671065f822";
//        FIR.checkForUpdateInFIR(firToken, new VersionCheckCallback() {
//            @Override
//            public void onSuccess(String versionJson) {
//                try {
//                    ObjectMapper mapper = new ObjectMapper();
//                    final FirResult result = mapper.readValue(versionJson, FirResult.class);
//                    int oldVersionCode = TDevice.getVersionCode(MainActivity.this);
//                    int newVersionCode = Integer.valueOf(result.getVersion());
//                    if (newVersionCode > oldVersionCode) {
//                        DialogAnotherUtil.showCustomAlertDialogWithMessage(MainActivity.this, null,
//                                "发现新版本，是否更新", "确定", "取消",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                                        intent.setData(Uri.parse(result.getUpdate_url()));
//                                        startActivity(intent);
//                                        DialogUtil.dismissDialog();
//                                    }
//                                }, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        DialogUtil.dismissDialog();
//                                    }
//                                });
//                    }
//                } catch (Exception e) {
//                    LogUtil.e(e.getMessage());
//                }
//
//            }
//
//            @Override
//            public void onFail(Exception e) {
//                ToastUtil.show(getApplicationContext(), e.getMessage());
//            }
//
//            @Override
//            public void onStart() {
//            }
//
//            @Override
//            public void onFinish() {
//            }
//        });
//    }

    /**
     * 添加Fragment
     **/
    public void addFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            this.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            this.getSupportFragmentManager().beginTransaction().add(R.id.main_framelayout, fragment).commit();
        }
    }

    /**
     * 删除Fragment
     **/
    public void removeFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

    /**
     * 显示Fragment
     **/
    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        // 判断页面是否已经创建，如果已经创建，那么就隐藏掉
        if (homeFragment != null) {
            ft.hide(homeFragment);
        }
        if (sortFragment != null) {
            ft.hide(sortFragment);
        }
        if (classificationFragment != null) {
            ft.hide(classificationFragment);
        }
        if (rankFragment != null) {
            ft.hide(rankFragment);
        }
        if (cartFragment != null) {
            ft.hide(cartFragment);
        }
        if (userCenterFragment != null) {
            ft.hide(userCenterFragment);
        }

        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    @OnClick({R.id.ll_home,R.id.ll_sort,R.id.ll_rank,R.id.ll_cart,R.id.ll_personal})
    public void onClick(View v) {
        resetColor();
        switch (v.getId()) {
            case R.id.ll_home:
                // 首页
                ivMenu0.setImageResource(R.drawable.guide_home_on);
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    tvMenuText0.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
                } else {
                    tvMenuText0.setTextColor(ContextCompat.getColor(this, R.color.red));
                }
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                    addFragment(homeFragment);
                    showFragment(homeFragment);
                } else {
                    if (homeFragment.isHidden()) {
                        showFragment(homeFragment);
                    }
                }
                break;
            case R.id.ll_sort:
                // 分类
                ivMenu1.setImageResource(R.drawable.guide_classification_on);
                tvMenuText1.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
                if (sortFragment == null) {
                    sortFragment = SortFragment.newInstance();
                    addFragment(sortFragment);
                    showFragment(sortFragment);
                } else {
                    if (sortFragment.isHidden()) {
                        showFragment(sortFragment);
                    }
                }
                break;
            case R.id.ll_rank:
                // 排行榜
                ivMenu2.setImageResource(R.drawable.guide_rank_on);
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    tvMenuText2.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
                } else {
                    tvMenuText2.setTextColor(ContextCompat.getColor(this, R.color.red));
                }
                final Cms004Req cms004Req = new Cms004Req();
                cms004Req.setPlaceId(Long.valueOf("10"));
                getJsonService().requestCms004(MainActivity.this, cms004Req, true, new JsonService.CallBack<Cms004Resp>() {
                    @Override
                    public void oncallback(Cms004Resp cms004Resp) {
                        if (rankFragment == null) {
                            rankFragment = RankingListFragment.newInstance();
                        }
                        if (!rankFragment.isHidden()) {
                            addFragment(rankFragment);
                        }
                        rankFragment.getArguments().putSerializable("cm004", cms004Resp);
                        showFragment(rankFragment);
                    }

                    @Override
                    public void onErro(AppHeader header) {

                    }
                });
                break;
            case R.id.ll_cart:
                // 购物车界面
                ivMenu3.setImageResource(R.drawable.guide_cart_on);
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    tvMenuText3.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
                } else {
                    tvMenuText3.setTextColor(ContextCompat.getColor(this, R.color.red));
                }
                if (AppContext.isLogin) {
                    Ord018Req req = new Ord018Req();
                    getJsonService().requestOrd018(this, req, false, new JsonService.CallBack<Ord018Resp>() {
                        @Override
                        public void oncallback(Ord018Resp ord018Resp) {
                            if (ord018Resp != null) {
                                Long cartItemNum = ord018Resp.getOrdCartItemNum();
                                if (cartItemNum != 0) {
                                    setVisible(tvGoodNum);
                                    if (cartItemNum > 99) {
                                        tvGoodNum.setText("99+");
                                    } else {
                                        tvGoodNum.setText(String.valueOf(cartItemNum));
                                    }
                                } else {
                                    setGone(tvGoodNum);
                                }
                            }
                        }

                        @Override
                        public void onErro(AppHeader header) {

                        }
                    });
                } else {
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
                                    sendBroadcast(new Intent(ShopCartFragment.REFRESH_CART));
                                    DialogUtil.dismissDialog();
                                }
                            });
                }
                if (cartFragment == null) {
                    cartFragment = ShopCartFragment.newInstance();
                    addFragment(cartFragment);
                    cartFragment.getArguments().putString("fromShopDetail", "");
                    showFragment(cartFragment);
                } else {
                    if (cartFragment.isHidden()) {
                        showFragment(cartFragment);
                    }
                }
                break;
            case R.id.ll_personal:
                // 个人中心
                ivMenu4.setImageResource(R.drawable.guide_account_on);
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    tvMenuText4.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
                } else {
                    tvMenuText4.setTextColor(ContextCompat.getColor(this, R.color.red));
                }
                if (userCenterFragment == null) {
                    userCenterFragment = new PersonalCenterFragment();
                    if (!userCenterFragment.isHidden()) {
                        addFragment(userCenterFragment);
                        showFragment(userCenterFragment);
                    }
                } else {
                    if (userCenterFragment.isHidden()) {
                        showFragment(userCenterFragment);
                    }
                }
                break;
        }
    }

    private void resetColor(){
        ivMenu0.setImageResource(R.drawable.guide_home_nm);
        ivMenu1.setImageResource(R.drawable.guide_classification_nm);
        ivMenu2.setImageResource(R.drawable.guide_rank_nm);
        ivMenu3.setImageResource(R.drawable.guide_cart_nm);
        ivMenu4.setImageResource(R.drawable.guide_account_nm);

        tvMenuText0.setTextColor(ContextCompat.getColor(this, R.color.black));
        tvMenuText1.setTextColor(ContextCompat.getColor(this, R.color.black));
        tvMenuText2.setTextColor(ContextCompat.getColor(this, R.color.black));
        tvMenuText3.setTextColor(ContextCompat.getColor(this, R.color.black));
        tvMenuText4.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK)
        {
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
                currentBackPressedTime = System.currentTimeMillis();
                ToastUtil.show(this, "再按一次退出程序");
                return true;
            } else {
                finish();
            }
        }
        else if(event.getKeyCode() == KeyEvent.KEYCODE_MENU)
        {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (null == homeFragment && fragment instanceof HomeFragment) {
            homeFragment = (HomeFragment) fragment;
        } else if (sortFragment == null && fragment instanceof SortFragment) {
            sortFragment = (SortFragment) fragment;
        } else if (classificationFragment == null && fragment instanceof ClassificationFragment) {
            classificationFragment = (ClassificationFragment) fragment;
        } else if (rankFragment == null && fragment instanceof RankingListFragment) {
            rankFragment = (RankingListFragment) fragment;
        } else if (cartFragment == null && fragment instanceof ShopCartFragment) {
            cartFragment = (ShopCartFragment) fragment;
        } else if (userCenterFragment == null && fragment instanceof PersonalCenterFragment) {
            userCenterFragment = (PersonalCenterFragment) fragment;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(receiver1);
    }
}
