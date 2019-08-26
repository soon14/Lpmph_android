package com.ailk.pmph.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.req.Staff111Req;
import com.ai.ecp.app.resp.BaseAreaAdminRespDTO;
import com.ai.ecp.app.resp.Staff111Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.adapter.AreaListAdapter;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:选择地区
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/7 17:15
 */
public class ChoseAreaActivity extends BaseActivity implements View.OnClickListener,
        AreaListAdapter.ClickCityInterface{

    @BindView(R.id.iv_back)           ImageView ivBack;
    @BindView(R.id.tv_place)          TextView tvPlace;
    @BindView(R.id.lv_area_list)      ExpandableListView lvAreaList;
    @BindView(R.id.btn_confirm)       Button btnConfirm;

    private AreaListAdapter adapter;
    private List<BaseAreaAdminRespDTO> provinceList = new ArrayList<>();
    private Map<String, List<BaseAreaAdminRespDTO>> cityMap = new HashMap<>();
    private String provinceName;
    private String province;
    private String cityName;
    private String city;
    private String china;
    private Bundle extras;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_chose_area;
    }

    @Override
    public void initView(){
        Bundle bundle = getIntent().getExtras();
        china = bundle.getString("china");
        if (bundle.getString("provinceName")!=null) {
            provinceName = bundle.getString("provinceName");
        } else {
            provinceName = "";
        }
        if (bundle.getString("cityName")!=null) {
            cityName = bundle.getString("cityName");
        } else {
            cityName = "";
        }
        province = bundle.getString("province");
        city = bundle.getString("city");
        tvPlace.setText(china + provinceName + cityName);
        extras = new Bundle();

    }

    @Override
    public void initData() {
        requestAreaList(true);
        lvAreaList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i=0; i<adapter.getGroupCount(); i++) {
                    if (groupPosition!=i) {
                        lvAreaList.collapseGroup(i);
                    }
                }
            }
        });
        lvAreaList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, final int groupPosition, long id) {
                boolean expanded = parent.isGroupExpanded(groupPosition);
                if (!expanded)
                {
                    provinceName = provinceList.get(groupPosition).getAreaName();
                    extras.putString("china", china);
                    extras.putString("provinceName", provinceName);
                    extras.putString("province", provinceList.get(groupPosition).getAreaCode());
                    tvPlace.setText(china + provinceName);
                    Staff111Req request = new Staff111Req();

                    request.setAreaCode(provinceList.get(groupPosition).getAreaCode());
                    getJsonService().requestStaff111(ChoseAreaActivity.this, request, true, new JsonService.CallBack<Staff111Resp>() {
                        @Override
                        public void oncallback(Staff111Resp staff111Resp) {
                            for (int i = 0; i < provinceList.size(); i++) {
                                BaseAreaAdminRespDTO pronvice = provinceList.get(i);
                                cityMap.put(pronvice.getAreaCode(),staff111Resp.getResList());
                                adapter.setCityMap(cityMap);
                            }
                            lvAreaList.expandGroup(groupPosition);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onErro(AppHeader header) {

                        }
                    });
                    return true;
                }
                return false;
            }
        });
    }

    private void requestAreaList(boolean isShowProgress){
        Staff111Req request = new Staff111Req();
        request.setAreaCode("156");
        getJsonService().requestStaff111(this, request, isShowProgress, new JsonService.CallBack<Staff111Resp>() {
            @Override
            public void oncallback(Staff111Resp staff111Resp) {
                provinceList = staff111Resp.getResList();
                if (provinceList != null) {
                    adapter = new AreaListAdapter(ChoseAreaActivity.this, provinceList);
                    adapter.setClickCityInterface(ChoseAreaActivity.this);
                    lvAreaList.setAdapter(adapter);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void clickCity(BaseAreaAdminRespDTO city) {
        cityName = city.getAreaName();
        tvPlace.setText(china + provinceName + cityName);
        extras.putString("cityName", cityName);
        extras.putString("city", city.getAreaCode());
        requestAreaList(false);
    }

    @Override
    @OnClick({R.id.iv_back, R.id.btn_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_confirm:
                if (extras.size() > 0) {
                    if (StringUtils.isEmpty(cityName) && (StringUtils.equals("台湾", cityName)
                            || StringUtils.equals("香港", cityName)
                            || StringUtils.equals("澳门", cityName))) {
                        ToastUtil.showCenter(this,"请选择县市");
                        return;
                    }
                    Intent intent = new Intent(this, CreateAddressActivity.class);
                    intent.putExtras(extras);
                    setResult(1003, intent);
                }
                onBackPressed();
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
