package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.CoupDetailResp;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.MoneyUtils;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/4/8 11:08
 */
public class CouponListAdapter extends BaseAdapter {

    private List<CoupDetailResp> mRespList;
    private Context mContext;
    private String mOpeType;

    public CouponListAdapter(Context context, List<CoupDetailResp> respList, String opeType){
        this.mContext = context;
        this.mRespList = respList;
        this.mOpeType = opeType;
    }

    public void addData(List<CoupDetailResp> data) {
        if (mRespList != null && data != null && !data.isEmpty()) {
            mRespList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mRespList == null ? 0 : mRespList.size();
    }

    @Override
    public CoupDetailResp getItem(int position) {
        return mRespList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null==convertView){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_coupon_list, parent, false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        CoupDetailResp bean = getItem(position);
        String temp = StringUtils.remove(MoneyUtils.GoodListPrice(bean.getCoupValue()),"￥");
        holder.tvCoupPrice.setText(StringUtils.remove(temp,".00"));
        holder.tvShopName.setText(bean.getShopName());
        holder.tvCondition.setText(bean.getConditions());
        if (StringUtils.equals(Constant.OPE_TYPE_OLD, mOpeType)
                && StringUtils.equals(Constant.OPE_TYPE_USE, mOpeType)) {

            holder.ivTime.setVisibility(View.INVISIBLE);
        }
        if (StringUtils.equals(Constant.OPE_TYPE_UN_USE, mOpeType)) {
            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                holder.tvMoneyCode.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
                holder.tvCoupPrice.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
            } else {
                holder.tvMoneyCode.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                holder.tvCoupPrice.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            }
            if (StringUtils.equals("0",bean.getCoupStatus())) {
                holder.llItem.setBackgroundResource(R.drawable.bg_coupon_item);
            }
            if (StringUtils.equals("1",bean.getCoupStatus())) {
                holder.llItem.setBackgroundResource(R.drawable.bg_coupon_new);
            }
            if (StringUtils.equals("2",bean.getCoupStatus())) {
                holder.llItem.setBackgroundResource(R.drawable.bg_coupon_unnew);
            }
        } else if (StringUtils.equals(Constant.OPE_TYPE_OLD, mOpeType)) {
            holder.tvMoneyCode.setTextColor(ContextCompat.getColor(mContext, R.color.gray_312B2D));
            holder.tvCoupPrice.setTextColor(ContextCompat.getColor(mContext, R.color.gray_312B2D));
            holder.llItem.setBackgroundResource(R.drawable.bg_coupon_old);
        } else if (StringUtils.equals(Constant.OPE_TYPE_USE, mOpeType)) {
            holder.tvMoneyCode.setTextColor(ContextCompat.getColor(mContext, R.color.gray_312B2D));
            holder.tvCoupPrice.setTextColor(ContextCompat.getColor(mContext, R.color.gray_312B2D));
            holder.llItem.setBackgroundResource(R.drawable.bg_coupon_use);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        Timestamp activeTime = new Timestamp(bean.getActiveTime().getTime());
        Timestamp inactiveTime = new Timestamp(bean.getInactiveTime().getTime());
        holder.tvTime.setText(df.format(activeTime)+"-"+df.format(inactiveTime));
        return convertView;
    }

    static class ViewHolder{

        @BindView(R.id.tv_money_code)
        TextView tvMoneyCode;
        @BindView(R.id.tv_coup_price)
        TextView tvCoupPrice;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_coup_condition)
        TextView tvCondition;
        @BindView(R.id.iv_time)
        ImageView ivTime;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll_item_layout)
        LinearLayout llItem;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
