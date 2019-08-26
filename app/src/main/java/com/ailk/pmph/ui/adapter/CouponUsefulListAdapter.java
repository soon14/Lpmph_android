package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ai.ecp.app.resp.CoupCheckBeanRespDTO;
import com.ai.ecp.app.resp.CoupDetailRespDTO;
import com.ai.ecp.app.resp.Ord00201Resp;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.ToastUtil;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/3/30 11:33
 */
public class CouponUsefulListAdapter extends BaseAdapter {

    private Context mContext;
    private List<CoupDetailRespDTO> mList;
    private CheckCouponInterface checkCouponInterface;
    private Ord00201Resp group;

    public CouponUsefulListAdapter(Context context, List<CoupDetailRespDTO> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void setGroup(Ord00201Resp group) {
        this.group = group;
        notifyDataSetChanged();
    }

    public void setCheckCouponInterface(CheckCouponInterface checkCouponInterface) {
        this.checkCouponInterface = checkCouponInterface;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public CoupDetailRespDTO getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_coupon_use_list, parent, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        final CoupDetailRespDTO data = getItem(position);
        if (data != null) {
            if (StringUtils.isNotEmpty(data.getCoupValueShow())) {
                childHolder.tvCoupDetailName.setText(data.getCoupName() + " " + (data.getCoupValueShow() + "元"));
            } else {
                childHolder.tvCoupDetailName.setText(data.getCoupName());
            }
            if (StringUtils.isEmpty(data.getConditionsShow())) {
                childHolder.tvConditionShow.setText("无");
            } else {
                childHolder.tvConditionShow.setText(data.getConditionsShow());
            }
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            final Timestamp ts = new Timestamp(data.getInactiveTime().getTime());
            childHolder.tvInactiveTime.setText("有效期至：" + df.format(ts));
            if (group.getCoupIdList()!=null && group.getCoupIdList().contains(data.getId())) {
                childHolder.check.setChecked(true);
            } else {
                childHolder.check.setChecked(false);
            }
            childHolder.check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtils.isEmpty(group.getPromotionHashKey())) {
                        checkCouponInterface.checkCoupon(data, childHolder.check.isChecked(), childHolder.check);
                    } else {
                        ToastUtil.showCenter(mContext, "优惠码与优惠券不能同时使用");
                    }
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtils.isEmpty(group.getPromotionHashKey())) {
                        if (!childHolder.check.isChecked()) {
                            childHolder.check.setChecked(true);
                        } else {
                            childHolder.check.setChecked(false);
                        }
                        checkCouponInterface.checkCoupon(data, childHolder.check.isChecked(), childHolder.check);
                    } else {
                        ToastUtil.showCenter(mContext, "优惠码与优惠券不能同时使用");
                    }
                }
            });
        }
        return convertView;
    }

    static class ChildHolder {
        @BindView(R.id.cb_check_coupon)
        CheckBox check;
        @BindView(R.id.tv_coup_detail_name)
        TextView tvCoupDetailName;
        @BindView(R.id.tv_condition_show)
        TextView tvConditionShow;
        @BindView(R.id.tv_inactive_time)
        TextView tvInactiveTime;

        public ChildHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface CheckCouponInterface {
        void checkCoupon(CoupDetailRespDTO child, boolean isChecked, CheckBox checkView);
    }
}
