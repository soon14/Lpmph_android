package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ai.ecp.app.resp.AcctTrade;
import com.ai.ecp.app.resp.ScoreTrade;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.MoneyUtils;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * Created by Administrator on 2016/4/9.
 */
public class WalletDetailListAdapter extends BaseAdapter {

    private List<AcctTrade> mList;
    private Context mContext;

    public WalletDetailListAdapter(Context context, List<AcctTrade> list){
        this.mContext = context;
        this.mList = list;
    }

    public void addData(List<AcctTrade> data) {
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
    public AcctTrade getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_wallet_detail_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AcctTrade bean = getItem(position);
        if (bean.getOrderId()!=null) {
            holder.tvOrderType.setText(bean.getTradeTypeName());
            holder.tvOrderCode.setText("订单号:"+bean.getOrderId());
        } else {
            holder.tvOrderType.setText(bean.getTradeTypeName());
            holder.tvOrderCode.setVisibility(View.GONE);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp createTime = new Timestamp(bean.getCreateTime().getTime());
        holder.tvTime.setText(df.format(createTime));
        if (StringUtils.equals("1", bean.getDebitCredit())) {
            holder.tvMoney.setText("+"+ MoneyUtils.GoodListPrice(bean.getTradeMoney()));
        } else {
            holder.tvMoney.setText("-"+ MoneyUtils.GoodListPrice(bean.getTradeMoney()));
        }
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.tv_order_type)
        TextView tvOrderType;
        @BindView(R.id.tv_order_code)
        TextView tvOrderCode;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_money)
        TextView tvMoney;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

}
