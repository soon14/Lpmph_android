package com.ailk.pmph.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.req.Ord018Req;
import com.ai.ecp.app.req.Pmphstaff001Req;
import com.ai.ecp.app.req.Pmphstaff007Req;
import com.ai.ecp.app.resp.Ord018Resp;
import com.ai.ecp.app.resp.Pmphstaff001Resp;
import com.ai.ecp.app.resp.Pmphstaff007Resp;
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
import com.ailk.pmph.utils.InputUtil;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.pmph.utils.TDevice;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.utils.UnionIdUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/10/13 9:25
 */

public class LoginPmphActivity extends BaseActivity implements View.OnClickListener, PlatformActionListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.login_reg)
    TextView loginReg;
    @BindView(R.id.login_forget)
    TextView loginForget;
    @BindView(R.id.login_confirm)
    Button loginConfirm;
    @BindView(R.id.login_account)
    ClearEditText loginAccount;
    @BindView(R.id.login_password)
    ClearEditText loginPassword;
    @BindView(R.id.iv_wechat)
    ImageView mWechatIv;
    @BindView(R.id.iv_sina)
    ImageView mSinaIv;
    @BindView(R.id.iv_qq)
    ImageView mQqIv;

    private static final int MSG_AUTH_CANCEL = 1;
    private static final int MSG_AUTH_ERROR = 2;
    private static final int MSG_AUTH_COMPLETE = 3;

    private static final String QQ_UNIONID_URL = "https://graph.qq.com/oauth2.0/me";
    private String mUnionId;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    @Override
    @OnClick({R.id.iv_back, R.id.login_reg, R.id.login_forget, R.id.login_confirm,
            R.id.iv_wechat, R.id.iv_sina, R.id.iv_qq})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.login_reg:
                if (AppContext.isLogin) {
                    ToastUtil.show(this, "请先退出后再进行注册操作");
                    return;
                }
                launch(RegisterActivity.class);
                break;

            case R.id.login_forget:
                launch(ForgetPwdActivity.class);
                break;

            case R.id.login_confirm:
                if (checkLogin()) {
                    ToastUtil.show(this, "用户名或密码为空，请重新输入");
                    return;
                }
                requestLogin();
                break;

            case R.id.iv_wechat:
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                if (wechat.isAuthValid()) {
                    wechat.removeAccount(true);
                }
                authorize(wechat);
                break;

            case R.id.iv_sina:
                Platform sinaWeibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                if (sinaWeibo.isAuthValid()) {
                    sinaWeibo.removeAccount(true);
                }
                authorize(sinaWeibo);
                break;

            case R.id.iv_qq:
                Platform qzone = ShareSDK.getPlatform(QZone.NAME);
                if (qzone.isAuthValid()) {
                    qzone.removeAccount(true);
                }
                authorize(qzone);
                break;
        }
    }

    private void requestLogin() {
        Pmphstaff001Req req = new Pmphstaff001Req();
        req.setLoginCode(loginAccount.getText().toString());
        req.setPassword(loginPassword.getText().toString());
        DialogUtil.showCustomerProgressDialog(this);
        NetCenter.build(this)
                .requestDefault(req, "pmphstaff001")
                .map(new Func1<AppBody, Pmphstaff001Resp>() {
                    @Override
                    public Pmphstaff001Resp call(AppBody appBody) {
                        return null == appBody ? null : (Pmphstaff001Resp) appBody;
                    }
                }).subscribe(new Action1<Pmphstaff001Resp>() {
            @Override
            public void call(Pmphstaff001Resp pmphstaff001Resp) {
                DialogUtil.dismissDialog();
                String tocken = pmphstaff001Resp.getTocken();
                Long staffId = pmphstaff001Resp.getStaffId();
                String mobile = pmphstaff001Resp.getMobile();
                if (StringUtils.isNotEmpty(tocken)) {
//                    if (StringUtils.isNotEmpty(mobile)) {
                    AppUtility.getInstance().setSessionId(tocken);
                    PrefUtility.put("token", tocken);
                    PrefUtility.put("staffId", staffId);
                    AppContext.isLogin = true;
                    ToastUtil.show(LoginPmphActivity.this, "登录成功");

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
//                    }
//                    else {
//                        launch(TelephoneActivity.class);
//                    }
                } else {
                    ToastUtil.show(LoginPmphActivity.this, pmphstaff001Resp.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DialogUtil.dismissDialog();
                AppUtility.getInstance().setSessionId("");
                if (PrefUtility.contains("token")) {
                    PrefUtility.remove("token");
                }
                if (PrefUtility.contains("staffId")) {
                    PrefUtility.remove("staffId");
                }
                if (PrefUtility.contains("staffCode")) {
                    PrefUtility.remove("staffCode");
                }
                AppContext.isLogin = false;
            }
        });
    }

    private boolean checkLogin() {
        if (StringUtils.isEmpty(loginAccount.getText().toString()) || StringUtils.isEmpty(loginPassword.getText().toString())) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前购物车数量
     */
    private void getCartNum() {
        Ord018Req req = new Ord018Req();
        NetCenter.build(this)
                .requestDefault(req, "ord018")
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
                ToastUtil.show(LoginPmphActivity.this, throwable.getMessage());
            }
        });
    }

    private void authorize(Platform plat) {
        if (plat == null) {
            return;
        }

        plat.setPlatformActionListener(this);
        plat.SSOSetting(false);
        plat.showUser(null);
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        if (action == Platform.ACTION_USER_INFOR) {
            Message msg = new Message();
            msg.what = MSG_AUTH_COMPLETE;
            msg.obj = new Object[]{platform.getName(), hashMap};
            mHandler.sendMessage(msg);
        }
    }

    @Override
    public void onError(Platform platform, int action, Throwable throwable) {
        if (action == Platform.ACTION_USER_INFOR) {
            mHandler.sendEmptyMessage(MSG_AUTH_ERROR);
        }
        throwable.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            mHandler.sendEmptyMessage(MSG_AUTH_CANCEL);
        }
    }

    private MyHandler mHandler = new MyHandler(this);

    private class MyHandler extends Handler {

        private final WeakReference<LoginPmphActivity> mActivity;

        public MyHandler(LoginPmphActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final LoginPmphActivity activity = mActivity.get();
            if (null != activity) {
                switch (msg.what) {
                    case MSG_AUTH_CANCEL:
                        ToastUtil.show(activity, "取消授权");
                        break;

                    case MSG_AUTH_ERROR:
                        ToastUtil.show(activity, "授权失败");
                        break;

                    case MSG_AUTH_COMPLETE:
                        ToastUtil.show(activity, "授权成功");
                        Object[] objs = (Object[]) msg.obj;
                        final String platformName = (String) objs[0];
                        HashMap<String, Object> res = (HashMap<String, Object>) objs[1];
                        final Platform platform = ShareSDK.getPlatform(platformName);
                        String userId = platform.getDb().getUserId();
                        String userName = platform.getDb().getUserName();
                        String platCode;
                        String platformNameCN;
                        if (StringUtils.equals(platformName, SinaWeibo.NAME)) {
                            platCode = "Weibo";
                            platformNameCN = "新浪微博";
                            requestPmphstaff007(platCode, userId, mUnionId, userName, platformNameCN, activity);
                        }

                        if (StringUtils.equals(platformName, QZone.NAME)) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    getQQUnionId(platform, activity);
                                }
                            }).start();
                        }

                        if (StringUtils.equals(platformName, Wechat.NAME)) {
                            platCode = "Wechat";
                            platformNameCN = "微信";
                            requestPmphstaff007(platCode, userId, mUnionId, userName, platformNameCN, activity);
                        }

                        break;
                }
            }
        }

    }

    /**
     * 获取QQ联合ID
     *
     * @param plat
     * @param activity
     */
    private void getQQUnionId(final Platform plat, final LoginPmphActivity activity) {
        RequestParams params = new RequestParams();
        params.put("access_token", plat.getDb().getToken());
        params.put("unionid", 1);
        UnionIdUtils.get(QQ_UNIONID_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        String responseData = new String(responseBody);
                        String[] split = responseData.split(":");
                        responseData = split[split.length - 1];
                        split = responseData.split("\"");
                        responseData = split[1];
                        mUnionId = responseData;
                        new Handler(getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                requestPmphstaff007("QQ", plat.getDb().getUserId(), mUnionId, plat.getDb().getUserName(), "QQ", activity);
                            }
                        });
                    } catch (Exception e) {
                        LogUtil.e(e);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode != 200) {
                    ToastUtil.show(LoginPmphActivity.this, "get unionid fail");
                }
            }
        });
    }

    /**
     * 第三方登录sso
     *
     * @param platCode
     * @param userId
     * @param unionId
     * @param userName
     * @param platformNameCN
     * @param activity
     */
    private void requestPmphstaff007(final String platCode, final String userId, final String unionId, final String userName,
                                     final String platformNameCN, final LoginPmphActivity activity) {
        Pmphstaff007Req req = new Pmphstaff007Req();
        req.setPlatCode(platCode);
        req.setPlatUID(userId);
        req.setPublicPlatUID(unionId);
        DialogUtil.showCustomerProgressDialog(this);
        NetCenter.build(this)
                .requestDefault(req, "pmphstaff007")
                .map(new Func1<AppBody, Pmphstaff007Resp>() {
                    @Override
                    public Pmphstaff007Resp call(AppBody appBody) {
                        return null == appBody ? null : (Pmphstaff007Resp) appBody;
                    }
                }).subscribe(new Action1<Pmphstaff007Resp>() {
            @Override
            public void call(Pmphstaff007Resp pmphstaff007Resp) {
                DialogUtil.dismissDialog();
                String tocken = pmphstaff007Resp.getTocken();
                String telephone = pmphstaff007Resp.getTelephone();
                if (StringUtils.isNotEmpty(tocken)) {
//                    if (StringUtils.isNotEmpty(telephone)) {
                    AppUtility.getInstance().setSessionId(tocken);
                    PrefUtility.put("token", tocken);
                    AppContext.isLogin = true;
                    ToastUtil.show(activity, "登录成功");

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
//                    } else {
//                        launch(TelephoneActivity.class);
//                    }
                } else {
                    //跳转绑定页面
                    Intent intent = new Intent(activity, AccountBindActivity.class);
                    intent.putExtra("userName", userName);
                    intent.putExtra("platformName", platformNameCN);
                    intent.putExtra("platCode", platCode);
                    intent.putExtra("userId", userId);
                    launch(intent);
                    AppManager.getAppManager().finishActivity();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DialogUtil.dismissDialog();
                AppUtility.getInstance().setSessionId("");
                if (PrefUtility.contains("token")) {
                    PrefUtility.remove("token");
                }
                if (PrefUtility.contains("staffId")) {
                    PrefUtility.remove("staffId");
                }
                if (PrefUtility.contains("staffCode")) {
                    PrefUtility.remove("staffCode");
                }
                AppContext.isLogin = false;
                ToastUtil.show(activity, "登录失败");
            }
        });
    }

}
