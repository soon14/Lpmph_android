package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord00202Resp;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.ui.activity.ShopDetailActivity;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/4/20 14:07
 */
public class OrderGroupListsAdapter extends BaseAdapter {

    private List<Ord00202Resp> mList;
    private Context mContext;

    public OrderGroupListsAdapter(Context context, List<Ord00202Resp> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Ord00202Resp getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_child_order_confirm_groupsitem, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Ord00202Resp bean = getItem(position);
        if (bean.getPicUrl()!=null) {
            GlideUtil.loadImg(mContext, bean.getPicUrl(), holder.ivImage);
        } else {
            holder.ivImage.setImageResource(R.drawable.default_img);
        }
        holder.tvProductName.setText(bean.getGdsName());
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            holder.tvPromInfo.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
        } else {
            holder.tvPromInfo.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        if (bean.isIfFulfillProm()) {
            holder.tvPromInfo.setText(bean.getFulfilMsg());
            holder.tvProductPrice.setText("价格："+ MoneyUtils.GoodListPrice(bean.getDiscountPrice()));
        } else {
            holder.tvPromInfo.setText(bean.getNoFulfilMsg());
            holder.tvProductPrice.setText("价格："+ MoneyUtils.GoodListPrice(bean.getBuyPrice()));
        }
        holder.tvProductNum.setText("数量：x "+bean.getOrderAmount());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(bean.getGdsId()));
                bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(bean.getSkuId()));
                Intent intent = new Intent(mContext, ShopDetailActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.iv_image)          ImageView ivImage;
        @BindView(R.id.tv_product_name)   TextView tvProductName;
        @BindView(R.id.tv_prom_info)      TextView tvPromInfo;
        @BindView(R.id.tv_product_price)  TextView tvProductPrice;
        @BindView(R.id.tv_product_num)    TextView tvProductNum;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
