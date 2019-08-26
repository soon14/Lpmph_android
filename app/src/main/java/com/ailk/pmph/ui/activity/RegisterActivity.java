package com.ailk.pmph.ui.activity;

import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ai.ecp.app.req.Pmphstaff002Req;
import com.ai.ecp.app.req.Pmphstaff003Req;
import com.ai.ecp.app.resp.Pmphstaff002Resp;
import com.ai.ecp.app.resp.Pmphstaff003Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.view.ClearEditText;
import com.ailk.pmph.utils.AppUtility;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.utils.Validator;

import org.apache.commons.lang.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:人卫注册界面
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/10/11 11:47
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_tel_num)
    ClearEditText etPhoneNum;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.et_code)
    ClearEditText etCode;
    @BindView(R.id.et_pwd)
    ClearEditText etPwd;
    @BindView(R.id.et_pwd_confirm)
    ClearEditText etPwdConfirm;
    @BindView(R.id.btn_reg)
    Button btnReg;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        if (AppContext.isLogin){
            ToastUtil.show(this, "请先退出账号后再注册");
            finish();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.iv_back, R.id.btn_code, R.id.btn_reg})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.btn_code:
                getSmsCode(v);
                break;

            case R.id.btn_reg:
                if (checkReg()) {
                    return;
                }
                register();
                break;
        }
    }

    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            btnCode.setText("获取验证码("+ millisUntilFinished/1000 + ")");
        }

        @Override
        public void onFinish() {
            btnCode.setEnabled(true);
            btnCode.setText("获取验证码");
            btnCode.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.orange_ff6a00));
        }
    };

    /**
     * 获取验证码
     */
    private void getSmsCode(final View view) {
        if (StringUtils.isEmpty(etPhoneNum.getText().toString())) {
            ToastUtil.show(this, "请输入手机号码");
            return;
        }
        if (!Validator.isMobile(etPhoneNum.getText().toString())) {
            ToastUtil.show(this, "输入的手机号码有误，请重新输入");
            return;
        }
        Pmphstaff003Req req = new Pmphstaff003Req();
        req.setMobile(etPhoneNum.getText().toString());
        getJsonService().requestPmphStaff003(this, req, true, new JsonService.CallBack<Pmphstaff003Resp>() {
            @Override
            public void oncallback(Pmphstaff003Resp pmphstaff003Resp) {
                if (pmphstaff003Resp != null) {
                    String success = pmphstaff003Resp.getSuccess();
                    if (StringUtils.isNotEmpty(success)) {
                        if (StringUtils.equals(success, "ok")) {
                            ToastUtil.show(RegisterActivity.this, "短信已发送");
                            view.setEnabled(false);
                            timer.start();
                        } else {
                            btnCode.setEnabled(true);
                            btnCode.setText("获取验证码");
                            timer.cancel();
                            ToastUtil.show(RegisterActivity.this, pmphstaff003Resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void register() {
        Pmphstaff002Req req = new Pmphstaff002Req();
        req.setSerialNumber(etPhoneNum.getText().toString());
        req.setStaffCode(etPhoneNum.getText().toString());
        req.setStaffPassword(etPwd.getText().toString());
        req.setSmsCode(etCode.getText().toString());
        getJsonService().requestPmphStaff002(this, req, true, new JsonService.CallBack<Pmphstaff002Resp>() {
            @Override
            public void oncallback(Pmphstaff002Resp pmphstaff002Resp) {
                if (pmphstaff002Resp != null) {
                    String success = pmphstaff002Resp.getSuccess();
                    String tocken = pmphstaff002Resp.getTocken();
                    if (StringUtils.isNotEmpty(success)) {
                        if (StringUtils.equals(success, "ok")) {
                            if (StringUtils.isNotEmpty(tocken)) {
                                ToastUtil.show(RegisterActivity.this, "注册成功");
                                AppUtility.getInstance().setSessionId(tocken);
                                if (PrefUtility.contains("token")){
                                    PrefUtility.remove("token");
                                }
                                PrefUtility.put("token", tocken);
                                AppContext.isLogin = true;
                                launch(MainActivity.class);
                                finish();
                            }
                        } else {
                            ToastUtil.show(RegisterActivity.this, "注册失败");
                        }
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {
                AppContext.isLogin = false;
                AppUtility.getInstance().setSessionId(null);
                if (PrefUtility.contains("token"))
                {
                    PrefUtility.remove("token");
                }
            }
        });
    }

    private boolean checkReg() {
        if (StringUtils.isEmpty(etPhoneNum.getText().toString())
                ||StringUtils.isEmpty(etPwd.getText().toString())
                ||StringUtils.isEmpty(etPwdConfirm.getText().toString())
                ||StringUtils.isEmpty(etCode.getText().toString())) {
            ToastUtil.show(this, "注册信息不能为空");
            return true;
        }
        if (!Validator.isMobile(etPhoneNum.getText().toString())) {
            ToastUtil.show(this, "手机号输入有误，请重新输入");
            return true;
        }
        if (!StringUtils.equals(etPwd.getText().toString(), etPwdConfirm.getText().toString())) {
            ToastUtil.show(this, "密码与确认密码不一致，请重新输入");
            return true;
        }
        if (etPwd.getText().toString().length() < 6) {
            ToastUtil.show(this, "密码长度为6-16个字符，请重新输入");
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
