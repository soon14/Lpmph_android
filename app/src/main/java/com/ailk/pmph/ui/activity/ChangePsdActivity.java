package com.ailk.pmph.ui.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ai.ecp.app.req.Staff004Req;
import com.ai.ecp.app.req.Staff005Req;
import com.ai.ecp.app.resp.Staff004Resp;
import com.ai.ecp.app.resp.Staff005Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.AppUtility;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.pmph.utils.StringUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.utils.Validator;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Project : PMPH
 * Created by 王可 on 16/4/12.
 */
public class ChangePsdActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.changepsd_old)
    EditText changeOldPwd;
    @BindView(R.id.changepsd_new)
    EditText changeNewPwd;
    @BindView(R.id.changepsd_confirm)
    EditText changeConfirmPwd;
    @BindView(R.id.changepsd_button)
    Button changePwdBtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_changepsd;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.iv_back,R.id.changepsd_button})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                launch(InfoActivity.class);
                onBackPressed();
                break;
            case R.id.changepsd_button:
                changePassword();
                break;
        }
    }

    private void changePassword() {
        if (!checkPassword()){
            return;
        }
        Staff004Req req = new Staff004Req();
        req.setOldPwd(changeOldPwd.getText().toString());
        req.setNewPwd(changeNewPwd.getText().toString());
        getJsonService().requestStaff004(ChangePsdActivity.this, req, true, new JsonService.CallBack<Staff004Resp>() {
            @Override
            public void oncallback(Staff004Resp staff004Resp) {
                if (staff004Resp.isFlag()){
                    ToastUtil.show(ChangePsdActivity.this, "修改成功");
                    loginOut();
                } else {
                    ToastUtil.show(ChangePsdActivity.this, "修改失败");
                }
            }

            @Override
            public void onErro(AppHeader header) {
                    ToastUtil.show(ChangePsdActivity.this, header.getRespMsg());

            }
        });

    }

    private void loginOut() {
        if (null == AppUtility.getInstance().getSessionId()) {
            return;
        }
        Staff005Req req = new Staff005Req();
        req.setTocken(AppUtility.getInstance().getSessionId());
        getJsonService().requestStaff005(ChangePsdActivity.this, req, true, new JsonService.CallBack<Staff005Resp>() {
            @Override
            public void oncallback(Staff005Resp staff005Resp) {
                if (staff005Resp.isFlag()) {
                    AppUtility.getInstance().setSessionId(null);
                    if (PrefUtility.contains("token")) {
                        PrefUtility.remove("token");
                    }
                    AppContext.isLogin = false;
                    launch(LoginActivity.class);
                    finish();
                }
            }

            @Override
            public void onErro(AppHeader header) {
                LogUtil.e(header);
                AppUtility.getInstance().setSessionId(null);
                if (PrefUtility.contains("token")) {
                    PrefUtility.remove("token");
                }
                AppContext.isLogin = false;
            }
        });
    }

    private boolean checkPassword() {
        if (StringUtil.isEmpty(changeOldPwd.getText().toString())){
            ToastUtil.show(ChangePsdActivity.this, "原密码不能为空");
            return false;
        }
        if (StringUtil.isEmpty(changeNewPwd.getText().toString())){
            ToastUtil.show(ChangePsdActivity.this, "新密码不能为空");
            return false;
        }
        if (StringUtil.isEmpty(changeConfirmPwd.getText().toString())){
            ToastUtil.show(ChangePsdActivity.this, "确认密码不能为空");
            return false;
        }

        if (!StringUtil.equals(changeNewPwd.getText().toString(),changeConfirmPwd.getText().toString())){
            ToastUtil.show(ChangePsdActivity.this, "新密码与确认密码不一致，请确认后再输入");
            return false;
        }
        if (StringUtil.equals(changeNewPwd.getText().toString(),changeOldPwd.getText().toString())){
            ToastUtil.show(ChangePsdActivity.this, "新密码与原密码不能相同，请确认后再输入");
            return false;
        }
        if (changeNewPwd.getText().toString().length() < 6) {
            ToastUtil.show(this, "密码长度为6-16个字符，请重新输入");
            return false;
        }
        if (!Validator.isPassword(changeNewPwd.getText().toString())) {
            ToastUtil.show(this, "密码必须由大写字母+小写字母+数字+字符（至少包含2种)，请重新输入");
            return false;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            launch(InfoActivity.class);
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
