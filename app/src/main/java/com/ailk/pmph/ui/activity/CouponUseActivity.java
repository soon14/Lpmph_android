package com.ailk.pmph.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ai.ecp.app.resp.CoupCheckBeanRespDTO;
import com.ai.ecp.app.resp.CoupDetailRespDTO;
import com.ai.ecp.app.resp.Ord00201Resp;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.adapter.CouponUsefulListAdapter;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:使用优惠券
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/3/30 11:30
 */
public class CouponUseActivity extends BaseActivity implements View.OnClickListener,
        CouponUsefulListAdapter.CheckCouponInterface{

    @BindView(R.id.iv_back)           ImageView ivBack;
    @BindView(R.id.tv_useful)         TextView tvUseful;
    @BindView(R.id.useful_line)       View usefulLine;
    @BindView(R.id.tv_useless)        TextView tvUseless;
    @BindView(R.id.useless_line)      View uselessLine;
    @BindView(R.id.lv_coupon_useful)  ListView lvCouponUseful;
    @BindView(R.id.lv_coupon_useless) ListView lvCouponUseless;
    @BindView(R.id.btn_confirm)       Button btnConfirm;

    private Bundle extras;
    private Ord00201Resp group;
    private Long shopId;
    private List<CoupCheckBeanRespDTO> coupOrdSkuList;
    private Long coupNum = 0L;
    List<CoupDetailRespDTO> tempCoupDetails = new ArrayList<>();

    List<Long> detailCoupIds;
    List<Long> detailIds;
    List<Long> checkCoupValues;
    List<CoupDetailRespDTO> checkCoupDetails;
    List<CoupCheckBeanRespDTO> coupCheckBeans;

    Map<Long, List<Long>> detailCoupIdsMap;
    Map<Long, List<Long>> checkCoupIdsMap;
    Map<Long, List<Long>> checkCoupValuesMap;
    Map<Long, List<CoupDetailRespDTO>> checkCoupDetailsMap;
    Map<Long, List<CoupCheckBeanRespDTO>> coupCheckBeansMap;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_coupon_use;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        extras = new Bundle();
        detailIds = new ArrayList<>();
        detailCoupIds = new ArrayList<>();
        checkCoupValues = new ArrayList<>();
        checkCoupDetails = new ArrayList<>();
        coupCheckBeans = new ArrayList<>();
        detailCoupIdsMap = new HashMap<>();
        checkCoupIdsMap = new HashMap<>();
        checkCoupValuesMap = new HashMap<>();
        checkCoupDetailsMap = new HashMap<>();
        coupCheckBeansMap = new HashMap<>();

        Bundle bundle = getIntent().getExtras();
        group = (Ord00201Resp) bundle.get("group");
        if (group != null) {
            shopId =group.getShopId();
        }
        coupOrdSkuList = (List<CoupCheckBeanRespDTO>)bundle.get("coupOrdSkuList");
        if (coupOrdSkuList != null && coupOrdSkuList.size() != 0) {
            for (int i = 0; i < coupOrdSkuList.size(); i++) {
                CoupCheckBeanRespDTO coup = coupOrdSkuList.get(i);
                coupNum += coup.getCoupSize();
                List<CoupDetailRespDTO> coupDetails = coup.getCoupDetails();
                if (coupDetails != null && coupDetails.size() != 0) {
                    tempCoupDetails.addAll(coupDetails);
                }
            }
        }
        CouponUsefulListAdapter adapter = new CouponUsefulListAdapter(this, tempCoupDetails);
        adapter.setCheckCouponInterface(this);
        adapter.setGroup(group);
        lvCouponUseful.setAdapter(adapter);
        tvUseful.setText("可用优惠券(" + coupNum + ")");
        if (group.getCoupValueList()!=null && group.getCoupIdList()!=null) {
            checkCoupValues.addAll(group.getCoupValueList());
            detailIds.addAll(group.getCoupIdList());
            detailCoupIds.addAll(group.getDetailCoupIdList());
            checkCoupDetails.addAll(group.getCoupDetails());
            coupCheckBeans.addAll(group.getCoupCheckBeans());
            checkCoupValuesMap.put(shopId,checkCoupValues);
            checkCoupIdsMap.put(shopId,detailIds);
            checkCoupDetailsMap.put(shopId,checkCoupDetails);
            coupCheckBeansMap.put(shopId,coupCheckBeans);
        }
    }

    @Override
    public void checkCoupon(CoupDetailRespDTO coupDetail, boolean isChecked, CheckBox checkView) {
        if (isChecked) {
            Long coupValue = coupDetail.getCoupValue();
            if (coupValue > 0) {
                checkCoupValues.add(coupValue);
            } else {
                coupValue = 0L;
                checkCoupValues.add(coupValue);
            }
            detailIds.add(coupDetail.getId());
            checkCoupDetails.add(coupDetail);
            detailCoupIds.add(coupDetail.getCoupId());

            group.setCoupIdList(detailIds);
            group.setCoupValueList(checkCoupValues);
            group.setCoupDetails(checkCoupDetails);
            group.setDetailCoupIdList(detailCoupIds);
            if (StringUtils.equals("1", coupDetail.getNoExpress())) {
                group.setNoExpress("1");
            }

            checkCoupDetailsMap.put(shopId,checkCoupDetails);
            checkCoupValuesMap.put(shopId,checkCoupValues);
            checkCoupIdsMap.put(shopId,detailIds);
            detailCoupIdsMap.put(shopId, detailCoupIds);

            extras.putLong("shopId", shopId);
            extras.putString("noExpress", group.getNoExpress());
            extras.putSerializable("checkCoupDetailsMap", (Serializable) checkCoupDetailsMap);
            extras.putSerializable("checkCoupValuesMap", (Serializable) checkCoupValuesMap);
            extras.putSerializable("checkCoupIdsMap", (Serializable) checkCoupIdsMap);
            extras.putSerializable("detailCoupIdsMap",(Serializable) detailCoupIdsMap);

            for (int i = 0; i < coupOrdSkuList.size(); i++) {
                CoupCheckBeanRespDTO coupCheckBean = coupOrdSkuList.get(i);
                if (coupCheckBean.getCoupId().equals(coupDetail.getCoupId())) {
                    coupCheckBeans.add(coupCheckBean);
                }
            }
            group.setCoupCheckBeans(coupCheckBeans);
            coupCheckBeansMap.put(shopId,coupCheckBeans);
            extras.putSerializable("checkCoupBeansMap", (Serializable) coupCheckBeansMap);

            for (int i = 0; i < group.getDetailCoupIdList().size(); i++) {
                Long checkCoupId = group.getDetailCoupIdList().get(i);
                if (!checkCoupId.equals(coupDetail.getCoupId())) {
                    ToastUtil.showCenter(this, "不同的优惠券不能共用");
                    checkView.setChecked(false);
                    checkCoupDetails.remove(coupDetail);
                    detailCoupIds.remove(coupDetail.getCoupId());
                    detailIds.remove(coupDetail.getId());
                    checkCoupValues.remove(coupValue);
                    return;
                }
            }
        } else {
            group.setNoExpress("");
            extras.putLong("shopId", group.getShopId());
            extras.putString("noExpress", group.getNoExpress());
            checkCoupValues.remove(coupDetail.getCoupValue());
            detailIds.remove(coupDetail.getId());
            detailCoupIds.remove(coupDetail.getCoupId());

            List<CoupDetailRespDTO> tempCoupDetailList = new ArrayList<>();
            for (int i = 0; i < checkCoupDetails.size(); i++) {
                CoupDetailRespDTO coupDetailRespDTO = checkCoupDetails.get(i);
                if (coupDetailRespDTO.getId().equals(coupDetail.getId())) {
                    tempCoupDetailList.add(coupDetailRespDTO);
                }
            }
            checkCoupDetails.removeAll(tempCoupDetailList);

//            Iterator<CoupDetailRespDTO> iter = checkCoupDetails.iterator();
//            while (iter.hasNext()) {
//                CoupDetailRespDTO detail = iter.next();
//                if (detail.getId().equals(coupDetail.getId())) {
//                    iter.remove();
//                }
//            }
//            checkCoupDetails.remove(coupDetail);

//            Iterator<CoupCheckBeanRespDTO> iterator = coupOrdSkuList.iterator();
//            while (iterator.hasNext()) {
//                CoupCheckBeanRespDTO coupCheckBean = iterator.next();
//                if (coupCheckBean.getCoupId().equals(coupDetail.getCoupId())) {
//                    iterator.remove();
//                }
//                coupCheckBeans.remove(coupCheckBean);
//            }
            List<CoupCheckBeanRespDTO> tempCoupCheckBeanList = new ArrayList<>();
            for (CoupCheckBeanRespDTO coupCheckBeanRespDTO : coupCheckBeans) {
                if (coupCheckBeanRespDTO.getCoupId().equals(coupDetail.getCoupId())) {
                    tempCoupCheckBeanList.add(coupCheckBeanRespDTO);
                }
            }
            coupCheckBeans.removeAll(tempCoupCheckBeanList);
//            for (CoupCheckBeanRespDTO coupCheckBean : coupOrdSkuList) {
//                if (coupCheckBean.getCoupId().equals(coupDetail.getCoupId())) {
//                    coupCheckBeans.remove(coupCheckBean);
//                }
//            }
            LogUtil.e("优惠券==============" + coupCheckBeans.size());
            checkCoupDetailsMap.put(shopId,checkCoupDetails);
            checkCoupValuesMap.put(shopId,checkCoupValues);
            checkCoupIdsMap.put(shopId,detailIds);
            detailCoupIdsMap.put(shopId, detailCoupIds);
            coupCheckBeansMap.put(shopId,coupCheckBeans);

            extras.putSerializable("checkCoupDetailsMap", (Serializable) checkCoupDetailsMap);
            extras.putSerializable("checkCoupValuesMap", (Serializable) checkCoupValuesMap);
            extras.putSerializable("checkCoupIdsMap", (Serializable) checkCoupIdsMap);
            extras.putSerializable("checkCoupBeansMap", (Serializable) coupCheckBeansMap);
            extras.putSerializable("detailCoupIdsMap", (Serializable) detailCoupIdsMap);
        }
    }

    @Override
    @OnClick({R.id.iv_back, R.id.tv_useful, R.id.tv_useless, R.id.btn_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_useful:
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    tvUseful.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
                } else {
                    tvUseful.setTextColor(ContextCompat.getColor(this, R.color.red));
                }
                tvUseless.setTextColor(ContextCompat.getColor(this, R.color.gray_969696));
                setVisible(usefulLine,lvCouponUseful);
                setGone(uselessLine,lvCouponUseless);
                break;
            case R.id.tv_useless:
                tvUseful.setTextColor(ContextCompat.getColor(this, R.color.gray_969696));
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    tvUseless.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
                } else {
                    tvUseless.setTextColor(ContextCompat.getColor(this, R.color.red));
                }
                setVisible(uselessLine,lvCouponUseless);
                setGone(usefulLine,lvCouponUseful);
                break;
            case R.id.btn_confirm:
                Intent intent = new Intent(this, OrderConfirmActivity.class);
                intent.putExtras(extras);
                setResult(101, intent);
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
