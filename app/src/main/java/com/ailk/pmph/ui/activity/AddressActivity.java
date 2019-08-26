package com.ailk.pmph.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Staff107Req;
import com.ai.ecp.app.req.Staff109Req;
import com.ai.ecp.app.req.Staff112Req;
import com.ai.ecp.app.resp.CustAddrResDTO;
import com.ai.ecp.app.resp.Staff107Resp;
import com.ai.ecp.app.resp.Staff109Resp;
import com.ai.ecp.app.resp.Staff112Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppManager;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.adapter.AddressManagerListAdapter;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类注释:收货地址管理界面（从个人中心进入）
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/7 20:32
 */
public class AddressActivity extends BaseActivity implements
        View.OnClickListener, AddressManagerListAdapter.CheckDefaultInterface,
        AddressManagerListAdapter.EditAddressInterface, AddressManagerListAdapter.DeleteAddressInterface{

    @BindView(R.id.iv_back)               ImageView ivBack;
    @BindView(R.id.lv_address_list)       ListView lvAddressList;
    @BindView(R.id.rl_bottom_layout)      RelativeLayout rlBottom;
    @BindView(R.id.btn_create)            Button btnCreate;
    @BindView(R.id.rl_empty_address)      RelativeLayout rlEmpty;
    @BindView(R.id.ll_unempty_address)    LinearLayout llUnEmpty;

    private AddressManagerListAdapter adapter;
    public static final String MODIFY_ADDRESS = "modify_address";
    private List<CustAddrResDTO> addressList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_address_manager;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(MODIFY_ADDRESS);
        registerReceiver(modifyAddressReceiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestAddressList(true);
    }

    public BroadcastReceiver modifyAddressReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equals(intent.getAction(), MODIFY_ADDRESS)) {
                requestAddressList(true);
            }
        }
    };

    private void requestAddressList(boolean isShowProgress){
        Staff107Req request = new Staff107Req();
        getJsonService().requestStaff107(this, request, isShowProgress, new JsonService.CallBack<Staff107Resp>() {
            @Override
            public void oncallback(Staff107Resp staff107Resp) {
                addressList = staff107Resp.getResList();
                if (addressList != null) {
                    if (addressList.size()==0)
                    {
                        setVisible(rlEmpty);
                        setGone(llUnEmpty);
                    }
                    else
                    {
                        setGone(rlEmpty);
                        setVisible(llUnEmpty,rlBottom);
                        adapter = new AddressManagerListAdapter(AddressActivity.this, addressList);
                        adapter.setCheckDefaultInterface(AddressActivity.this);
                        adapter.setEditAddressInterface(AddressActivity.this);
                        adapter.setDeleteAddressInterface(AddressActivity.this);
                        lvAddressList.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void setDefault(RadioButton check, TextView text, CustAddrResDTO addrResDTO, boolean isChecked) {
        setDefaultAddress(true, text, addrResDTO);
    }

    @Override
    public void editAddress(CustAddrResDTO custAddrResDTO) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("address", custAddrResDTO);
        bundle.putString("key","0");
        launch(ModifyAddressActivity.class, bundle);
        finish();
    }

    @Override
    public void deleteAddress(final CustAddrResDTO custAddrResDTO) {
        DialogAnotherUtil.showCustomAlertDialogWithMessage(this, null,
                "确认删除地址吗？", "确定", "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAddress(true, custAddrResDTO);
                        DialogUtil.dismissDialog();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogUtil.dismissDialog();
                    }
                });
    }

    private void setDefaultAddress(boolean isShowProgress, TextView text, final CustAddrResDTO addrResDTO){
        Staff112Req request = new Staff112Req();
        request.setId(addrResDTO.getId());
        getJsonService().requestStaff112(this, request, isShowProgress, new JsonService.CallBack<Staff112Resp>() {
            @Override
            public void oncallback(Staff112Resp staff112Resp) {
                if (staff112Resp.isFlag())
                {
                    requestAddressList(true);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    ToastUtil.showCenter(AddressActivity.this, "设为默认地址失败");
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void deleteAddress(boolean isShowProgress, final CustAddrResDTO custAddrResDTO){
        Staff109Req request = new Staff109Req();
        request.setId(custAddrResDTO.getId());
        getJsonService().requestStaff109(this, request, isShowProgress, new JsonService.CallBack<Staff109Resp>() {
            @Override
            public void oncallback(Staff109Resp staff109Resp) {
                if (staff109Resp.isFlag())
                {
                    adapter.deleteItem(custAddrResDTO);
                }
                else
                {
                    ToastUtil.showCenter(AddressActivity.this, "删除地址失败");
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    @OnClick({R.id.iv_back, R.id.btn_create})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                launch(InfoActivity.class);
                onBackPressed();
                break;
            case R.id.btn_create:
                if (addressList != null && addressList.size() >= 10) {
                    ToastUtil.showCenter(this, "亲,收货地址最多10条哦");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("key","0");
                launch(CreateAddressActivity.class, bundle);
                finish();
                break;
            default:
                break;
        }
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
        unregisterReceiver(modifyAddressReceiver);
    }

}

