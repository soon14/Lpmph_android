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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord00202Resp;
import com.ailk.pmph.AppContext;
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
 * 时间: 2016/4/18 15:05
 */
public class GroupListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Ord00202Resp> mList;

    public GroupListAdapter(Context context, List<Ord00202Resp> list){
        this.mContext = context;
        this.mList = list;
        boolean isFlag = false;
        for (int i =0; i < list.size(); i++) {
            Ord00202Resp product = list.get(i);
            if (!product.isGdsStatus()) {
                isFlag =true;
                break;
            }
        }
        if (isFlag){
            for (int j =0; j < list.size(); j++) {
                Ord00202Resp product = list.get(j);
                product.setGdsStatus(false);
            }
        }
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_item_shopcart_grouplist, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Ord00202Resp bean = getItem(position);
        if (bean.getPicUrl()!=null) {
            GlideUtil.loadImg(mContext, bean.getPicUrl(), holder.ivPic);
        } else {
            holder.ivPic.setImageResource(R.drawable.default_img);
        }
        holder.ivPic.setOnClickListener(new View.OnClickListener() {
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
        holder.tvGdsName.setText(bean.getGdsName());
        holder.tvCateName.setText(bean.getGdsCateName());
        if (bean.isGdsStatus())
        {
            if (StringUtils.equals(bean.getGdsType(),"2")) //虚拟商品
            {
                holder.tvAmount.setText("1本x1");
            }
            else
            {
                holder.tvAmount.setText("1件x"+bean.getOrderAmount());
            }
            if (bean.isIfFulfillProm()) {
                String discountPrice = MoneyUtils.GoodListPrice(bean.getDiscountPrice());
                MoneyUtils.showSpannedPrice(holder.tvPrice, discountPrice);
            } else {
                String buyPrice = MoneyUtils.GoodListPrice(bean.getBuyPrice());
                MoneyUtils.showSpannedPrice(holder.tvPrice, buyPrice);
            }
        }
        else
        {
            holder.rlGood.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray_ebebeb));
            if (StringUtils.equals(bean.getGdsType(),"2")) //虚拟商品
            {
                holder.tvAmount.setText("1本x0");
            }
            else
            {
                holder.tvAmount.setText("1件x0");
            }
            holder.tvPrice.setText("0.00");
            holder.tvPrice.setTextColor(ContextCompat.getColor(mContext, R.color.gray_969696));
            holder.tvGdsName.setTextColor(ContextCompat.getColor(mContext, R.color.gray_969696));
        }
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.tv_gds_name)
        TextView tvGdsName;
        @BindView(R.id.tv_cate_name)
        TextView tvCateName;
        @BindView(R.id.tv_amount)
        TextView tvAmount;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.rl_good)
        RelativeLayout rlGood;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
