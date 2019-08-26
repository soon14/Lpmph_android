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

import com.ai.ecp.app.req.Staff106Req;
import com.ai.ecp.app.resp.CustAddrResDTO;
import com.ai.ecp.app.resp.Staff106Resp;
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

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:编辑收货地址
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/7 15:16
 */
public class ModifyAddressActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)                   ImageView ivBack;
    @BindView(R.id.tv_title)                  TextView tvTitle;
    @BindView(R.id.et_contract_name)          ClearEditText etContractName;
    @BindView(R.id.et_contract_phone)         ClearEditText etContractPhone;
    @BindView(R.id.ll_place_layout)           LinearLayout llPlaceLayout;
    @BindView(R.id.tv_place)                  TextView tvPlace;
    @BindView(R.id.et_contract_address)       ClearEditText etContractAddress;
    @BindView(R.id.et_contract_post)          ClearEditText etContractPost;
    @BindView(R.id.btn_confirm)               Button btnConfirm;

    private CustAddrResDTO data;
    private String key;
    private String china = "中国";
    private String pccName;
    private String province;
    private String city;
    private String county;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_edit_address);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit_address;
    }

    @Override
    public void initView(){
        tvTitle.setText("编辑收货地址");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            data = (CustAddrResDTO) bundle.getSerializable("address");
            if (data != null) {
                pccName = data.getPccName();
                province = data.getProvince();
                city = data.getCityCode();
                county = data.getCountyCode();
                key = bundle.getString("key");
                etContractName.setText(data.getContactName());
                etContractPhone.setText(data.getContactPhone());
                tvPlace.setText(pccName);
                etContractAddress.setText(data.getChnlAddress());
                if (data.getPostCode() != null) {
                    etContractPost.setText(data.getPostCode());
                }
            }
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1004 && resultCode==1005) {
            if (data!=null) {
                china = data.getExtras().getString("china");
                pccName = data.getExtras().getString("pccName");
                province = data.getExtras().getString("province");
                city = data.getExtras().getString("city");
                if (pccName==null) {
                    tvPlace.setText("");
                } else {
                    tvPlace.setText(china+pccName);
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
                Intent intent = new Intent(this, EditChoseAreaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("pccName", pccName);
                bundle.putString("province", province);
                bundle.putString("city", city);
                bundle.putString("county", county);
                bundle.putString("china", china);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1004);
                break;
            case R.id.btn_confirm:
                modifyAddress(true);
                break;
            default:
                break;
        }
    }

    private void modifyAddress(boolean isShowProgress){
        Staff106Req request = new Staff106Req();
        request.setId(data.getId());
        if (StringUtils.isEmpty(etContractName.getText().toString())) {
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
        request.setUsingFlag(data.getUsingFlag());
        getJsonService().requestStaff106(this, request, isShowProgress, new JsonService.CallBack<Staff106Resp>() {
            @Override
            public void oncallback(Staff106Resp staff106Resp) {
                if (staff106Resp.isFlag()) {
                    if (key.equals("0")) {
                        launch(AddressActivity.class);
                        onBackPressed();
                    }
                    if (key.equals("1")) {
                        launch(AddressManagerActivity.class);
                        onBackPressed();
                    }
                } else {
                    ToastUtil.showCenter(ModifyAddressActivity.this, "编辑收货地址失败");
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
