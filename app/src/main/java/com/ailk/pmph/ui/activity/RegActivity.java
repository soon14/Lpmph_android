package com.ailk.pmph.ui.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ai.ecp.app.req.Staff002Req;
import com.ai.ecp.app.resp.Staff002Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.view.ClearEditText;
import com.ailk.pmph.utils.AppUtility;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.utils.Validator;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/11.
 * 注册Activity
 */
public class RegActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.reg_account)
    ClearEditText regAccount;
    @BindView(R.id.reg_password)
    ClearEditText regPassword;
    @BindView(R.id.reg_password_again)
    ClearEditText regPasswordAgain;
    @BindView(R.id.reg_tel)
    ClearEditText regTel;
    @BindView(R.id.reg_btn)
    Button regBtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_reg;
    }

    @Override
    public void initView() {
        if (AppContext.isLogin){
            ToastUtil.show(RegActivity.this, "请先退出后再注册");
            finish();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.iv_back, R.id.reg_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.reg_btn:
                if (checkReg()){
                    return;
                }
                Staff002Req req = new Staff002Req();
                req.setStaffCode(regAccount.getText().toString());
                req.setStaffPassword(regPassword.getText().toString());
                req.setSerialNumber(regTel.getText().toString());
                getJsonService().requestStaff002(RegActivity.this, req, true, new JsonService.CallBack<Staff002Resp>() {
                    @Override
                    public void oncallback(Staff002Resp staff002Resp) {
                        ToastUtil.show(RegActivity.this, "注册成功");
                        AppUtility.getInstance().setSessionId(staff002Resp.getTocken());
                        if (PrefUtility.contains("token")){
                            PrefUtility.remove("token");
                        }
                        PrefUtility.put("token", staff002Resp.getTocken());
                        AppContext.isLogin = true;
                        launch(MainActivity.class);
                        finish();
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
                break;
        }
    }

    private boolean checkReg() {
        if (StringUtils.isEmpty(regAccount.getText().toString())
                ||StringUtils.isEmpty(regPassword.getText().toString())
                ||StringUtils.isEmpty(regPasswordAgain.getText().toString())
                ||StringUtils.isEmpty(regTel.getText().toString())){
            ToastUtil.show(RegActivity.this, "注册信息不能为空");
            return true;
        }
        if (!Pattern.matches("^[a-zA-Z0-9]{6,15}$", regAccount.getText().toString())){
            ToastUtil.show(RegActivity.this, "用户名必须为6-15位，并只由数字或字母组成，请重新输入");
            return true;
        }

        if (!StringUtils.equals(regPassword.getText().toString(), regPasswordAgain.getText().toString())){
            ToastUtil.show(RegActivity.this, "密码与确认密码不一致，请重新输入");

            return true;
        }
        if (regPassword.getText().toString().length() < 6) {
            ToastUtil.show(this, "密码长度为6-16个字符，请重新输入");
            return false;
        }
        if (!Validator.isMobile(regTel.getText().toString())) {
            ToastUtil.show(this, "手机号输入有误，请重新输入");
            return  true;
        }

        if (!Validator.isPassword(regPassword.getText().toString())) {
            ToastUtil.show(this, "密码必须由大写字母+小写字母+数字+字符（至少包含2种)，请重新输入");
            return true;
        }
        return false;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
