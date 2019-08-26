package com.ailk.pmph.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Staff105Req;
import com.ai.ecp.app.resp.Staff105Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppManager;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.view.ClearEditText;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.utils.Validator;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:新建收货地址
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/7 10:44
 */
public class CreateAddressActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)                   ImageView ivBack;
    @BindView(R.id.tv_title)                  TextView tvTitle;
    @BindView(R.id.et_contract_name)          ClearEditText etContractName;
    @BindView(R.id.et_contract_phone)         ClearEditText etContractPhone;
    @BindView(R.id.ll_place_layout)           LinearLayout llPlaceLayout;
    @BindView(R.id.tv_place)                  TextView tvPlace;
    @BindView(R.id.et_contract_address)       ClearEditText etContractAddress;
    @BindView(R.id.et_contract_post)          ClearEditText etContractPost;
    @BindView(R.id.btn_confirm)               Button btnConfirm;

    private String china = "中国";
    private String province = "";
    private String provinceName = "";
    private String city = "";
    private String cityName = "";
    private String key;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit_address;
    }

    @Override
    public void initView(){
        tvTitle.setText("新增收货地址");
        key = getIntent().getExtras().getString("key");
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1002 && resultCode==1003) {
            if (data!=null) {
                provinceName = data.getExtras().getString("provinceName");
                cityName = data.getExtras().getString("cityName");
                province = data.getExtras().getString("province");
                city = data.getExtras().getString("city");
                if (provinceName==null && cityName==null) {
                    tvPlace.setText("");
                } else if (provinceName!=null && cityName==null) {
                    tvPlace.setText(china+provinceName);
                } else {
                    tvPlace.setText(china+provinceName+cityName);
                }
            } else {
                tvPlace.setText("");
            }
        }
    }

    @Override
    @OnClick({R.id.iv_back, R.id.ll_place_layout, R.id.btn_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ll_place_layout:
                Intent intent = new Intent(this, ChoseAreaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("provinceName", provinceName);
                bundle.putString("cityName", cityName);
                bundle.putString("province", province);
                bundle.putString("city", city);
                bundle.putString("china", china);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1002);
                break;
            case R.id.btn_confirm:
                requestStaff105(true);
                break;
            default:
                break;
        }
    }

    private void requestStaff105(boolean isShowProgress){
        Staff105Req request = new Staff105Req();
        if (StringUtils.isEmpty(etContractName.getText().toString())){
            ToastUtil.showCenter(this, "收货人不能为空");
            return;
        } else {
            request.setContactName(etContractName.getText().toString());
        }
        if (StringUtils.isEmpty(etContractPhone.getText().toString())) {
            ToastUtil.showCenter(this, "手机号码不能为空");
            return;
        } else if (!Validator.isMobile(etContractPhone.getText().toString())) {
            ToastUtil.showCenter(this, "手机号输入有误，请重新输入");
            return;
        } else {
            request.setContactPhone(etContractPhone.getText().toString());
        }
        if (StringUtils.isEmpty(etContractAddress.getText().toString())) {
            ToastUtil.showCenter(this, "收货地址不能为空");
            return;
        } else {
            request.setChnlAddress(etContractAddress.getText().toString());
        }
        if (StringUtils.isEmpty(tvPlace.getText().toString())) {
            ToastUtil.showCenter(this, "所在地区不能为空");
            return;
        } else {
            request.setProvince(province);
            request.setCityCode(city);
        }
        if (StringUtils.isEmpty(etContractPost.getText().toString())) {
            ToastUtil.showCenter(this, "邮编不能为空");
            return;
        } else {
            request.setPostCode(etContractPost.getText().toString());
        }
        request.setCountryCode("156");
        request.setUsingFlag("0");
        getJsonService().requestStaff105(this, request, isShowProgress, new JsonService.CallBack<Staff105Resp>() {
            @Override
            public void oncallback(Staff105Resp staff105Resp) {
                if (staff105Resp.isFlag()) {
                    if (StringUtils.equals("0", key)) {
                        launch(AddressActivity.class);
                        onBackPressed();
                    }
                    if (StringUtils.equals("1", key)) {
                        Intent intent = new Intent(OrderConfirmActivity.REFRESH_ADDRESS);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", etContractName.getText().toString());
                        bundle.putString("phone", etContractPhone.getText().toString());
                        bundle.putString("address", tvPlace.getText().toString() + etContractAddress.getText().toString());
                        bundle.putLong("addrId", staff105Resp.getId());
                        intent.putExtra("adddata",bundle);
                        sendBroadcast(intent);
                        finish();
                    }
                } else {
                    ToastUtil.showCenter(CreateAddressActivity.this, "新增收货地址错误");
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
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
