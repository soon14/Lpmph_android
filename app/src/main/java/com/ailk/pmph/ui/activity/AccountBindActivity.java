package com.ailk.pmph.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.req.Ord018Req;
import com.ai.ecp.app.req.Pmphstaff006Req;
import com.ai.ecp.app.resp.Ord018Resp;
import com.ai.ecp.app.resp.Pmphstaff006Resp;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.im.net.NetCenter;
import com.ailk.integral.fragment.InteHomeFragment;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.AppManager;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.thirdstore.activity.StoreActivity;
import com.ailk.pmph.ui.view.ClearEditText;
import com.ailk.pmph.utils.AppUtility;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.pmph.utils.ToastUtil;

import org.apache.commons.lang.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2017/2/24
 */

public class AccountBindActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.tv_bind_text)
    TextView mBindTextTv;
    @BindView(R.id.tv_no_bind)
    TextView mNoBindTv;
    @BindView(R.id.et_account)
    ClearEditText mAccountEt;
    @BindView(R.id.et_password)
    ClearEditText mPasswordEt;
    @BindView(R.id.tv_bind)
    TextView mBindTv;

    private String mUserId;
    private String mPlatCode;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_account_bind;
    }

    @Override
    public void initView() {
        String sUserName = getIntent().getStringExtra("userName");
        String sPlatformName = getIntent().getStringExtra("platformName");
        mPlatCode = getIntent().getStringExtra("platCode");
        mUserId = getIntent().getStringExtra("userId");
        mBindTextTv.setText("尊敬的用户：" + sUserName
                + "，您已成功通过" + sPlatformName + "登录，如您已经有人卫医学网的账号请绑定。绑定后将避免您人卫医学网账户信息丢失。");
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.iv_back, R.id.tv_no_bind, R.id.tv_bind})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                launch(LoginPmphActivity.class);
                AppManager.getAppManager().finishActivity();
                break;

            case R.id.tv_no_bind:
                unBindAccount();
                break;

            case R.id.tv_bind:
                if (StringUtils.isEmpty(mAccountEt.getText().toString())) {
                    ToastUtil.show(this, "请输入账号");
                } else if (StringUtils.isEmpty(mPasswordEt.getText().toString())) {
                    ToastUtil.show(this, "请输入密码");
                } else {
                    bindAccount();
                }
                break;
        }
    }

    /**
     * 不绑定直接登录
     */
    private void unBindAccount() {
        Pmphstaff006Req req = new Pmphstaff006Req();
        req.setPlatCode(mPlatCode);
        req.setPlatUID(mUserId);
        req.setNoUserAction("AutoCreate");
        DialogUtil.showCustomerProgressDialog(this);
        NetCenter.build(this)
                .requestDefault(req,"pmphstaff006")
                .map(new Func1<AppBody, Pmphstaff006Resp>() {
                    @Override
                    public Pmphstaff006Resp call(AppBody appBody) {
                        return null == appBody ? null : (Pmphstaff006Resp) appBody;
                    }
                }).subscribe(new Action1<Pmphstaff006Resp>() {
            @Override
            public void call(Pmphstaff006Resp pmphstaff006Resp) {
                DialogUtil.dismissDialog();
                String tocken = pmphstaff006Resp.getTocken();
                if (StringUtils.isNotEmpty(tocken)) {
                    AppUtility.getInstance().setSessionId(tocken);
                    PrefUtility.put("token", tocken);
                    AppContext.isLogin = true;
                    ToastUtil.show(AccountBindActivity.this, "登录成功");

                    getCartNum();
                    //刷新积分商城首页
                    Intent inteHome = new Intent(InteHomeFragment.REFRESH);
                    sendBroadcast(inteHome);
                    //刷新店铺首页关注
                    Intent intentStore = new Intent(StoreActivity.REFRESH_STORE);
                    sendBroadcast(intentStore);
                    //签到
                    Intent recode = new Intent(SignInActivity.RECODE);
                    sendBroadcast(recode);
                    AppManager.getAppManager().finishActivity();
                } else {
                    AppUtility.getInstance().setSessionId("");
                    if (PrefUtility.contains("token")){
                        PrefUtility.remove("token");
                    }
                    if (PrefUtility.contains("staffCode")){
                        PrefUtility.remove("staffCode");
                    }
                    AppContext.isLogin = false;
                    ToastUtil.show(AccountBindActivity.this, pmphstaff006Resp.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                LogUtil.e(throwable);
                DialogUtil.dismissDialog();
                AppUtility.getInstance().setSessionId("");
                if (PrefUtility.contains("token")){
                    PrefUtility.remove("token");
                }
                if (PrefUtility.contains("staffCode")){
                    PrefUtility.remove("staffCode");
                }
                AppContext.isLogin = false;
                ToastUtil.show(AccountBindActivity.this, "登录失败");
            }
        });
    }

    /**
     * 获取当前购物车数量
     */
    private void getCartNum() {
        Ord018Req req = new Ord018Req();
        NetCenter.build(this)
                .requestDefault(req,"ord018")
                .map(new Func1<AppBody, Ord018Resp>() {
                    @Override
                    public Ord018Resp call(AppBody appBody) {
                        return null == appBody ? null : (Ord018Resp) appBody;
                    }
                }).subscribe(new Action1<Ord018Resp>() {
            @Override
            public void call(Ord018Resp ord018Resp) {
                Intent intent = new Intent(MainActivity.CART_GOODS_NUM);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ord018Resp", ord018Resp);
                intent.putExtras(bundle);
                sendBroadcast(intent);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ToastUtil.show(AccountBindActivity.this, throwable.getMessage());
            }
        });
    }

    /**
     * 绑定账号
     */
    private void bindAccount() {
        Pmphstaff006Req req = new Pmphstaff006Req();
        req.setPlatCode(mPlatCode);
        req.setPlatUID(mUserId);
        req.setNoUserAction("BindExistUser");
        req.setLoginId(mAccountEt.getText().toString());
        req.setPassword(mPasswordEt.getText().toString());
        DialogUtil.showCustomerProgressDialog(this);
        NetCenter.build(this)
                .requestDefault(req,"pmphstaff006")
                .map(new Func1<AppBody, Pmphstaff006Resp>() {
                    @Override
                    public Pmphstaff006Resp call(AppBody appBody) {
                        return null == appBody ? null : (Pmphstaff006Resp) appBody;
                    }
                }).subscribe(new Action1<Pmphstaff006Resp>() {
            @Override
            public void call(Pmphstaff006Resp pmphstaff006Resp) {
                DialogUtil.dismissDialog();
                String tocken = pmphstaff006Resp.getTocken();
                String telephone = pmphstaff006Resp.getTelephone();
                if (StringUtils.isNotEmpty(tocken)) {
                    if (StringUtils.isNotEmpty(telephone)) {
                        AppUtility.getInstance().setSessionId(tocken);
                        PrefUtility.put("token", tocken);
                        AppContext.isLogin = true;
                        ToastUtil.show(AccountBindActivity.this, "登录成功");

                        getCartNum();
                        //刷新积分商城首页
                        Intent inteHome = new Intent(InteHomeFragment.REFRESH);
                        sendBroadcast(inteHome);
                        //刷新店铺首页关注
                        Intent intentStore = new Intent(StoreActivity.REFRESH_STORE);
                        sendBroadcast(intentStore);
                        //签到
                        Intent recode = new Intent(SignInActivity.RECODE);
                        sendBroadcast(recode);
                        AppManager.getAppManager().finishActivity();
                    } else {
                        launch(TelephoneActivity.class);
                    }
                } else {
                    AppUtility.getInstance().setSessionId("");
                    if (PrefUtility.contains("token")){
                        PrefUtility.remove("token");
                    }
                    if (PrefUtility.contains("staffCode")){
                        PrefUtility.remove("staffCode");
                    }
                    AppContext.isLogin = false;
                    ToastUtil.show(AccountBindActivity.this, pmphstaff006Resp.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                LogUtil.e(throwable);
                DialogUtil.dismissDialog();
                AppUtility.getInstance().setSessionId("");
                if (PrefUtility.contains("token")){
                    PrefUtility.remove("token");
                }
                if (PrefUtility.contains("staffCode")){
                    PrefUtility.remove("staffCode");
                }
                AppContext.isLogin = false;
                ToastUtil.show(AccountBindActivity.this, "登录失败");
            }
        });
    }

}
