package com.ailk.pmph.ui.activity;

import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.req.Pmphstaff003Req;
import com.ai.ecp.app.resp.Pmphstaff003Resp;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.im.net.NetCenter;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.view.ClearEditText;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.utils.Validator;

import org.apache.commons.lang.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 类注释:沉淀手机号
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2017/5/8
 */

public class TelephoneActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.et_telephone)
    ClearEditText mTelephoneEt;
    @BindView(R.id.et_code)
    ClearEditText mCodeEt;
    @BindView(R.id.tv_code)
    TextView mCodeTv;
    @BindView(R.id.btn_finish)
    Button mFinishBtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_telephone;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.iv_back, R.id.tv_code, R.id.btn_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.tv_code:
                getSmsCode(view);
                break;

            case R.id.btn_finish:
                if (check()) {
                    return;
                }
                commitTelephone();
                break;
        }
    }

    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mCodeTv.setText("获取验证码("+ millisUntilFinished/1000 + ")");
        }

        @Override
        public void onFinish() {
            mCodeTv.setEnabled(true);
            mCodeTv.setText("获取验证码");
            mCodeTv.setBackground(ContextCompat.getDrawable(TelephoneActivity.this, R.drawable.shape_round_button));
        }
    };

    /**
     * 获取验证码
     * @param view
     */
    private void getSmsCode(final View view) {
        if (StringUtils.isEmpty(mTelephoneEt.getText().toString())) {
            ToastUtil.show(this, "请输入手机号码");
            return;
        }
        if (!Validator.isMobile(mTelephoneEt.getText().toString())) {
            ToastUtil.show(this, "输入的手机号码有误，请重新输入");
            return;
        }
        Pmphstaff003Req req = new Pmphstaff003Req();
        req.setMobile(mTelephoneEt.getText().toString());
        DialogUtil.showCustomerProgressDialog(this);
        NetCenter.build(this)
                .requestDefault(req,"pmphstaff003")
                .map(new Func1<AppBody, Pmphstaff003Resp>() {
                    @Override
                    public Pmphstaff003Resp call(AppBody appBody) {
                        return null == appBody ? null : (Pmphstaff003Resp) appBody;
                    }
                }).subscribe(new Action1<Pmphstaff003Resp>() {
            @Override
            public void call(Pmphstaff003Resp pmphstaff003Resp) {
                DialogUtil.dismissDialog();
                String success = pmphstaff003Resp.getSuccess();
                if (StringUtils.isNotEmpty(success)) {
                    if (StringUtils.equals(success, "ok")) {
                        ToastUtil.show(TelephoneActivity.this, "短信已发送");
                        view.setEnabled(false);
                        mCodeTv.setBackground(ContextCompat.getDrawable(TelephoneActivity.this, R.drawable.shape_round_button_gray));
                        timer.start();
                    } else {
                        mCodeTv.setEnabled(true);
                        mCodeTv.setText("获取验证码");
                        timer.cancel();
                        ToastUtil.show(TelephoneActivity.this, pmphstaff003Resp.getMessage());
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DialogUtil.dismissDialog();
                ToastUtil.show(TelephoneActivity.this, throwable.getMessage());
            }
        });
    }

    private boolean check(){
        if (StringUtils.isEmpty(mTelephoneEt.getText().toString())) {
            ToastUtil.show(this, "请输入手机号码");
            return true;
        }
        if (!Validator.isMobile(mTelephoneEt.getText().toString())) {
            ToastUtil.show(this, "输入的手机号码有误，请重新输入");
            return true;
        }
        if (StringUtils.isEmpty(mCodeEt.getText().toString())) {
            ToastUtil.show(this, "请输入验证码");
            return true;
        }
        return false;
    }

    private void commitTelephone() {

    }

}
