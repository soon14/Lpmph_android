package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord00201Resp;
import com.ai.ecp.app.resp.Ord00203Resp;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.LogUtil;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/3/26 10:44
 */
public class ShopCouponListAdapter extends BaseAdapter {

    private Context mContext;
    private Ord00201Resp group;
    private HashMap<String,Boolean> mStates = new HashMap<>();
    private CheckShopCouponInterface checkShopCouponInterface;

    public ShopCouponListAdapter(Context context){
        this.mContext = context;
    }

    public void setGroup(Ord00201Resp group) {
        this.group = group;
        notifyDataSetChanged();
    }

    public void setCheckShopCouponInterface(CheckShopCouponInterface checkShopCouponInterface) {
        this.checkShopCouponInterface = checkShopCouponInterface;
    }

    class ViewHolder {
        @BindView(R.id.rb_check)  RadioButton rbCheck;
        @BindView(R.id.tv_coupon) TextView tvCoupon;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getCount() {
        return group.getPromInfoDTOList() == null ? 0 : group.getPromInfoDTOList().size();
    }

    @Override
    public Ord00203Resp getItem(int position) {
        return group.getPromInfoDTOList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_modify_coupon_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Ord00203Resp promInfo = getItem(position);
        holder.tvCoupon.setText(promInfo.getPromTheme());
        if (group.getPromId() !=null) {
            if (group.getPromId().equals(group.getPromInfoDTOList().get(position).getId())) {
                for(String key : mStates.keySet()){
                    mStates.put(key, false);
                }
                mStates.put(String.valueOf(position), true);
                notifyDataSetChanged();
            }
        }
        boolean result;
        if(mStates.get(String.valueOf(position))==null || !mStates.get(String.valueOf(position))){
            result = false;
            mStates.put(String.valueOf(position), false);
        } else {
            result = true;
        }
        holder.rbCheck.setChecked(result);
        holder.rbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkShopCouponInterface.doCheckShopCoupon(position);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkShopCouponInterface.doCheckShopCoupon(position);
            }
        });
        return convertView;
    }

    public interface CheckShopCouponInterface {
        void doCheckShopCoupon(int position);
    }

}
