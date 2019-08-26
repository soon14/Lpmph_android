package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.AcctInfo;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.ui.activity.WalletDetailActivity;
import com.ailk.tool.GlideUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/4/9.
 */
public class WalletListAdapter extends BaseAdapter {

    private List<AcctInfo> mList;
    private Context mContext;

    public WalletListAdapter(Context context, List<AcctInfo> list){
        this.mContext = context;
        this.mList = list;
    }

    public void addData(List<AcctInfo> data) {
        if (mList != null && data != null && !data.isEmpty()) {
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public AcctInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null==convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_wallet_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final AcctInfo bean = getItem(position);
        GlideUtil.loadImg(mContext, bean.getShopLogo(), holder.ivLogo);
        holder.tvShopName.setText(bean.getShopName());
        holder.tvTypeName.setText(bean.getAcctTypeName());
        holder.tvMoney.setText(MoneyUtils.GoodListPrice(bean.getBalance()));
        holder.llWalletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WalletDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("acctInfo", bean);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.iv_shop_logo)
        CircleImageView ivLogo;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_type_name)
        TextView tvTypeName;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.ll_wallet_layout)
        LinearLayout llWalletLayout;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

}
