package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord00202Resp;
import com.ai.ecp.app.resp.Ord00203Resp;
import com.ai.ecp.app.resp.Ord01201Resp;
import com.ailk.pmph.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/3/23 19:03
 */
public class ProductCouponListAdapter extends BaseAdapter {

    private Context mContext;
    private Ord00202Resp product;
    private CheckProductCouponInterface checkProductCouponInterface;
    private HashMap<String,Boolean> states=new HashMap<>();

    public ProductCouponListAdapter(Context context){
        this.mContext = context;
    }

    public void setProduct(Ord00202Resp product) {
        this.product = product;
        notifyDataSetChanged();
    }

    public void setCheckProductCouponInterface(CheckProductCouponInterface checkProductCouponInterface) {
        this.checkProductCouponInterface = checkProductCouponInterface;
    }

    class ViewHolder {
        @BindView(R.id.rb_check)
        RadioButton rbCheck;
        @BindView(R.id.tv_coupon)
        TextView tvCoupon;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getCount() {
        return product.getPromInfoDTOList() == null ? 0 : product.getPromInfoDTOList().size();
    }

    @Override
    public Ord00203Resp getItem(int position) {
        return product.getPromInfoDTOList().get(position);
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
        if (product.getPromId().equals(product.getPromInfoDTOList().get(position).getId())) {
            for(String key:states.keySet()){
                states.put(key, false);
            }
            states.put(String.valueOf(position), true);
            notifyDataSetChanged();
        }
        boolean result;
        if(states.get(String.valueOf(position))==null || !states.get(String.valueOf(position))){
            result = false;
            states.put(String.valueOf(position), false);
        } else {
            result = true;
        }
        holder.rbCheck.setChecked(result);
        holder.rbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkProductCouponInterface.doCheckProductCoupon(position);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkProductCouponInterface.doCheckProductCoupon(position);
            }
        });
        return convertView;
    }

    public interface CheckProductCouponInterface {
        void doCheckProductCoupon(int position);
    }

}
