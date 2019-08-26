package com.ailk.pmph.ui.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ai.ecp.app.req.Staff006Req;
import com.ai.ecp.app.resp.Staff006Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.StringUtil;
import com.ailk.pmph.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Project : PMPH
 * Created by 王可 on 16/4/11.
 */
public class ChangeNameActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.changename_editview)
    EditText editText;
    @BindView(R.id.changename_button)
    Button button;
    String nickname;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_changename;
    }

    @Override
    public void initView() {
        nickname = getIntent().getStringExtra("nickname");
        if (null == nickname){
            editText.setHint("请输入昵称");
            editText.setText("");
        }else {
            editText.setHint("");
            editText.setText(nickname);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.iv_back, R.id.changename_button})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.changename_button:
                if (StringUtil.isEmpty(editText.getText().toString())){
                    ToastUtil.show(ChangeNameActivity.this, "昵称不允许为空");
                    return;
                }
                if (editText.getText().toString().length() > 16){
                    ToastUtil.show(ChangeNameActivity.this, "昵称过长，请修改后重试");
                    return;
                }
                Staff006Req req = new Staff006Req();
                req.setNickname(editText.getText().toString());
                getJsonService().requestStaff006(ChangeNameActivity.this, req, true, new JsonService.CallBack<Staff006Resp>() {
                    @Override
                    public void oncallback(Staff006Resp staff006Resp) {
                        if (staff006Resp.isFlag()){
                            ToastUtil.show(ChangeNameActivity.this, "修改成功");
                        }else {
                            ToastUtil.show(ChangeNameActivity.this, "修改失败");
                        }
                        onBackPressed();
                    }

                    @Override
                    public void onErro(AppHeader header) {
                        ToastUtil.show(ChangeNameActivity.this, "修改失败");
                        onBackPressed();
                    }
                });
                break;
        }
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
