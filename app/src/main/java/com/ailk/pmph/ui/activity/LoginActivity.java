package com.ailk.pmph.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.req.Staff001Req;
import com.ai.ecp.app.resp.Staff001Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.integral.fragment.InteHomeFragment;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.thirdstore.activity.StoreActivity;
import com.ailk.pmph.ui.fragment.PersonalCenterFragment;
import com.ailk.pmph.utils.AppUtility;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.pmph.utils.StringUtil;
import com.ailk.pmph.utils.ToastUtil;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/11.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.login_reg)
    TextView loginReg;
    @BindView(R.id.login_forget)
    TextView loginForget;
    @BindView(R.id.login_confirm)
    Button loginConfirm;
    @BindView(R.id.login_account)
    EditText loginAccount;
    @BindView(R.id.login_password)
    EditText loginPassword;

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
    @OnClick({R.id.iv_back, R.id.login_reg, R.id.login_forget, R.id.login_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.login_reg:
                if (AppContext.isLogin){
                    ToastUtil.show(LoginActivity.this, "请先退出后再进行注册操作");
                    return;
                }
                launch(RegActivity.class);
                break;

            case R.id.login_forget:

                break;

            case R.id.login_confirm:
                if (checkLogin()) {
                    ToastUtil.show(LoginActivity.this, "用户名或密码为空，请重新输入");
                    return;
                }
                requestLogin();
                break;
        }
    }

    private void requestLogin() {
        Staff001Req req = new Staff001Req();
        req.setLoginCode(loginAccount.getText().toString());
        req.setPassword(loginPassword.getText().toString());
        getJsonService().requestStaff001(LoginActivity.this, req, true, new JsonService.CallBack<Staff001Resp>() {
            @Override
            public void oncallback(Staff001Resp staff001Resp) {
                LogUtil.e(staff001Resp);
                AppUtility.getInstance().setSessionId(staff001Resp.getTocken());
                PrefUtility.put("token", staff001Resp.getTocken());
                AppContext.isLogin = true;
                ToastUtil.show(LoginActivity.this, "登录成功");

                //刷新积分商城首页
                Intent inteHome = new Intent(InteHomeFragment.REFRESH);
                sendBroadcast(inteHome);
                //刷新店铺首页关注
                Intent intentStore = new Intent(StoreActivity.REFRESH_STORE);
                sendBroadcast(intentStore);
                //签到
                Intent recode = new Intent(SignInActivity.RECODE);
                sendBroadcast(recode);
                finish();
            }

            @Override
            public void onErro(AppHeader header) {
                LogUtil.e(header);
                AppUtility.getInstance().setSessionId("");
                if (PrefUtility.contains("token")){
                    PrefUtility.remove("token");
                }
                AppContext.isLogin = false;
            }
        });
    }

    private boolean checkLogin() {
        if (StringUtil.isNullString(loginAccount.getText().toString())){
            return true;
        }
        if (StringUtil.isNullString(loginPassword.getText().toString())){
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
