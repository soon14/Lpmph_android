package com.ailk.integral.activity;

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

import com.ai.ecp.app.req.Ord111Req;
import com.ai.ecp.app.resp.Ord111Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.integral.fragment.InteCartFragment;
import com.ailk.integral.fragment.InteHomeFragment;
import com.ailk.integral.fragment.IntePersonalCenterFragment;
import com.ailk.integral.fragment.InteRankFragment;
import com.ailk.integral.fragment.InteSortFragment;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.activity.LoginActivity;
import com.ailk.pmph.ui.activity.LoginPmphActivity;
import com.ailk.pmph.ui.activity.MainActivity;
import com.ailk.pmph.ui.fragment.PersonalCenterFragment;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;

import org.apache.commons.lang.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.act
 * 作者: Chrizz
 * 时间: 2016/5/11 15:04
 */
public class InteMainActivity extends BaseActivity implements View.OnClickListener{

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

    private InteHomeFragment inteHomeFragment;
    private InteSortFragment inteSortFragment;
    private InteRankFragment inteRankFragment;
    private InteCartFragment inteCartFragment;
    private IntePersonalCenterFragment personalFragment;

    public static final String INTE_CART_GOODS_NUM = "inte_cart_goods_num";
    public static final String DIMISS_INTE_CART_GOODS_NUM = "dimiss_inte_cart_goods_num";

    @Override
    protected int getContentViewId() {
        return R.layout.inte_activity_main;
    }

    public void initView() {
        // 初始化默认显示的界面
        if (inteHomeFragment == null) {
            inteHomeFragment = InteHomeFragment.newInstance();
            addFragment(inteHomeFragment);
            showFragment(inteHomeFragment);
        } else {
            showFragment(inteHomeFragment);
        }
        // 设置默认首页为点击时的图片、文字
        ivMenu0.setImageResource(R.drawable.guide_home_on);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvMenuText0.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            tvMenuText0.setTextColor(ContextCompat.getColor(this, R.color.red));
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(INTE_CART_GOODS_NUM);
        registerReceiver(receiver,intentFilter);

        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(DIMISS_INTE_CART_GOODS_NUM);
        registerReceiver(receiver1, intentFilter1);
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equals(intent.getAction(), INTE_CART_GOODS_NUM)) {
                Bundle bundle = intent.getExtras();
                Ord111Resp ord111Resp = (Ord111Resp) bundle.get("ord111Resp");
                if (ord111Resp != null) {
                    Long cartItemNum = ord111Resp.getOrdCartItemNum();
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
            if (StringUtils.equals(intent.getAction(), DIMISS_INTE_CART_GOODS_NUM)) {
                setGone(tvGoodNum);
            }
        }
    };

    @Override
    public void initData() {

    }

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
        if (inteHomeFragment != null) {
            ft.hide(inteHomeFragment);
        }
        if (inteSortFragment != null) {
            ft.hide(inteSortFragment);
        }
        if (inteRankFragment != null) {
            ft.hide(inteRankFragment);
        }
        if (inteCartFragment != null) {
            ft.hide(inteCartFragment);
        }
        if (personalFragment != null) {
            ft.hide(personalFragment);
        }
        ft.show(fragment);
        ft.commitAllowingStateLoss();
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
                if (inteHomeFragment == null) {
                    inteHomeFragment = InteHomeFragment.newInstance();
                    addFragment(inteHomeFragment);
                    showFragment(inteHomeFragment);
                } else {
                    if (inteHomeFragment.isHidden()) {
                        showFragment(inteHomeFragment);
                    }
                }
                break;
            case R.id.ll_sort:
                // 分类
                ivMenu1.setImageResource(R.drawable.guide_classification_on);
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    tvMenuText1.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
                } else {
                    tvMenuText1.setTextColor(ContextCompat.getColor(this, R.color.red));
                }
                if (inteSortFragment == null) {
                    inteSortFragment = InteSortFragment.newInstance();
                }
                if (!inteSortFragment.isHidden()) {
                    addFragment(inteSortFragment);
                }
                showFragment(inteSortFragment);
                break;
            case R.id.ll_rank:
                // 排行榜
                ivMenu2.setImageResource(R.drawable.guide_rank_on);
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    tvMenuText2.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
                } else {
                    tvMenuText2.setTextColor(ContextCompat.getColor(this, R.color.red));
                }
                if (inteRankFragment == null) {
                    inteRankFragment = InteRankFragment.newInstance();
                }
                if (!inteRankFragment.isHidden()) {
                    addFragment(inteRankFragment);
                }
                showFragment(inteRankFragment);
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
                    Ord111Req req = new Ord111Req();
                    getInteJsonService().requestOrd111(this, req, false, new InteJsonService.CallBack<Ord111Resp>() {
                        @Override
                        public void oncallback(Ord111Resp ord111Resp) {
                            if (ord111Resp != null) {
                                Long cartItemNum = ord111Resp.getOrdCartItemNum();
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
                                    sendBroadcast(new Intent(InteCartFragment.REFRESH_INTE_CART));
                                    DialogUtil.dismissDialog();
                                }
                            });
                }
                if (inteCartFragment == null) {
                    inteCartFragment = InteCartFragment.newInstance();
                    addFragment(inteCartFragment);
                    inteCartFragment.getArguments().putString("fromInteShopDetail", "");
                    showFragment(inteCartFragment);
                } else {
                    if (inteCartFragment.isHidden()) {
                        showFragment(inteCartFragment);
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
                if (personalFragment == null) {
                    personalFragment = new IntePersonalCenterFragment();
                    if (!personalFragment.isHidden()) {
                        addFragment(personalFragment);
                        showFragment(personalFragment);
                    }
                } else {
                    if (personalFragment.isHidden()) {
                        showFragment(personalFragment);
                    }
                }
                break;
        }

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (null == inteHomeFragment && fragment instanceof InteHomeFragment) {
            inteHomeFragment = (InteHomeFragment) fragment;
        } else if (inteSortFragment == null && fragment instanceof InteSortFragment) {
            inteSortFragment = (InteSortFragment) fragment;
        } else if (inteRankFragment == null && fragment instanceof InteRankFragment) {
            inteRankFragment = (InteRankFragment) fragment;
        } else if (inteCartFragment == null && fragment instanceof InteCartFragment) {
            inteCartFragment = (InteCartFragment) fragment;
        } else if (personalFragment == null && fragment instanceof PersonalCenterFragment) {
            personalFragment = (IntePersonalCenterFragment) fragment;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            launch(MainActivity.class);
            onBackPressed();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(receiver1);
    }

}
