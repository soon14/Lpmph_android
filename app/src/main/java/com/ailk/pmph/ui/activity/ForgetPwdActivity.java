package com.ailk.pmph.ui.activity;

import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ai.ecp.app.req.Pmphstaff003Req;
import com.ai.ecp.app.req.Pmphstaff005Req;
import com.ai.ecp.app.resp.Pmphstaff003Resp;
import com.ai.ecp.app.resp.Pmphstaff005Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.view.ClearEditText;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.utils.Validator;

import org.apache.commons.lang.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/12/13
 */

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.et_tel_num)
    ClearEditText mPhoneEt;
    @BindView(R.id.btn_code)
    Button mCodeBtn;
    @BindView(R.id.et_code)
    ClearEditText mCodeEt;
    @BindView(R.id.et_pwd)
    ClearEditText mPwdEt;
    @BindView(R.id.et_pwd_confirm)
    ClearEditText mPwdConfirmEt;
    @BindView(R.id.btn_commit)
    Button mCommitBtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void initView() {
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            mCodeBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            mCodeBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        }
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.iv_back, R.id.btn_code, R.id.btn_commit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_code:
                getSmsCode(v);
                break;
            case R.id.btn_commit:
                if (check()) {
                    return;
                }
                commit();
                break;
        }
    }

    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mCodeBtn.setText("获取验证码("+ millisUntilFinished/1000 + ")");
        }

        @Override
        public void onFinish() {
            mCodeBtn.setEnabled(true);
            mCodeBtn.setText("获取验证码");
            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                mCodeBtn.setBackgroundColor(ContextCompat.getColor(ForgetPwdActivity.this, R.color.orange_ff6a00));
            } else {
                mCodeBtn.setBackgroundColor(ContextCompat.getColor(ForgetPwdActivity.this, R.color.red));
            }
        }
    };

    /**
     * 获取验证码
     */
    private void getSmsCode(final View view) {
        if (StringUtils.isEmpty(mPhoneEt.getText().toString())) {
            ToastUtil.show(this, "请输入手机号码");
            return;
        }
        if (!Validator.isMobile(mPhoneEt.getText().toString())) {
            ToastUtil.show(this, "输入的手机号码有误，请重新输入");
            return;
        }
        Pmphstaff003Req req = new Pmphstaff003Req();
        req.setMobile(mPhoneEt.getText().toString());
        getJsonService().requestPmphStaff003(this, req, true, new JsonService.CallBack<Pmphstaff003Resp>() {
            @Override
            public void oncallback(Pmphstaff003Resp pmphstaff003Resp) {
                if (pmphstaff003Resp != null) {
                    String success = pmphstaff003Resp.getSuccess();
                    if (StringUtils.isNotEmpty(success)) {
                        if (StringUtils.equals(success, "ok")) {
                            ToastUtil.show(ForgetPwdActivity.this, "短信已发送");
                            view.setEnabled(false);
                            view.setBackgroundColor(ContextCompat.getColor(ForgetPwdActivity.this, R.color.gray_9b9b9b));
                            timer.start();
                        } else {
                            mCodeBtn.setEnabled(true);
                            mCodeBtn.setText("获取验证码");
                            timer.cancel();
                            ToastUtil.show(ForgetPwdActivity.this, pmphstaff003Resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private boolean check(){
        if (StringUtils.isEmpty(mPhoneEt.getText().toString())) {
            ToastUtil.show(this, "请输入手机号码");
            return true;
        }
        if (!Validator.isMobile(mPhoneEt.getText().toString())) {
            ToastUtil.show(this, "输入的手机号码有误，请重新输入");
            return true;
        }
        if (StringUtils.isEmpty(mCodeEt.getText().toString())) {
            ToastUtil.show(this, "请输入验证码");
            return true;
        }
        if (StringUtils.isEmpty(mPwdEt.getText().toString())) {
            ToastUtil.show(this, "请输入密码");
            return true;
        }
        if (StringUtils.isEmpty(mPwdConfirmEt.getText().toString())) {
            ToastUtil.show(this, "请输入确认密码");
            return true;
        }
        return false;
    }

    private void commit() {
        Pmphstaff005Req req = new Pmphstaff005Req();
        req.setCheckCode(mCodeEt.getText().toString());
        req.setMobile(mPhoneEt.getText().toString());
        req.setNewPwd(mPwdEt.getText().toString());
        getJsonService().requestPmphStaff005(this, req, true, new JsonService.CallBack<Pmphstaff005Resp>() {
            @Override
            public void oncallback(Pmphstaff005Resp pmphstaff005Resp) {
                if (pmphstaff005Resp != null) {
                    String success = pmphstaff005Resp.getSuccess();
                    if (StringUtils.isNotEmpty(success)) {
                        if (StringUtils.equals("ok", success)) {
                            ToastUtil.show(ForgetPwdActivity.this, "修改成功");
                            onBackPressed();
                        } else {
                            ToastUtil.show(ForgetPwdActivity.this, pmphstaff005Resp.getMessage());
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
