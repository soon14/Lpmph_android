package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ai.ecp.app.resp.Pay00301Resp;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.MoneyUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/4/10 16:51
 */
public class DealShopListAdapter extends BaseAdapter {

    private List<Pay00301Resp> mList;
    private Context mContext;
    private CheckPayForShopInterface checkPayForShopInterface;
    private int mSelectedPosition = -1;

    public DealShopListAdapter(Context context, List<Pay00301Resp> list){
        this.mContext = context;
        this.mList = list;
    }

    public void setCheckPayForShopInterface(CheckPayForShopInterface checkPayForShopInterface) {
        this.checkPayForShopInterface = checkPayForShopInterface;
    }

    public void setSelectedItem (int itemPosition) {
        mSelectedPosition = itemPosition;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Pay00301Resp getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (null==convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_deal_confirm_shop_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Pay00301Resp bean = getItem(position);
        holder.tvOrderCode.setText("订单编号："+bean.getOrderId());
        holder.tvShopName.setText(bean.getShopName());
        holder.tvRealMoney.setText(MoneyUtils.GoodListPrice(bean.getRealMoney()));
//        if (mSelectedPosition == position) {
//            holder.cbCheck.setChecked(true);
//        } else {
//            holder.cbCheck.setChecked(false);
//        }
        holder.cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setSelectedItem(position);
                bean.setIsChoosed(((CheckBox) v).isChecked());
                checkPayForShopInterface.checkPayForShop(bean, ((CheckBox) v).isChecked());
            }
        });
        holder.cbCheck.setChecked(bean.isChoosed());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cbCheck.isChecked()) {
                    holder.cbCheck.setChecked(false);
                } else {
                    holder.cbCheck.setChecked(true);
                }
                bean.setIsChoosed(holder.cbCheck.isChecked());
                checkPayForShopInterface.checkPayForShop(bean, holder.cbCheck.isChecked());
            }
        });
        holder.cbCheck.setChecked(bean.isChoosed());
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.cb_check)
        CheckBox cbCheck;
        @BindView(R.id.tv_order_code)
        TextView tvOrderCode;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_real_money)
        TextView tvRealMoney;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    public interface CheckPayForShopInterface{
        void checkPayForShop(Pay00301Resp bean, boolean isChecked);
    }

}
