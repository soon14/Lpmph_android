package com.ailk.im.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord01201Resp;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.MoneyUtils;
import com.bumptech.glide.Glide;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.im.ui.adapter
 * 作者: Chrizz
 * 时间: 2017/2/24
 */

public class MyOrdersAdapter extends CommonRecyclerAdapter<Ord01201Resp> {

    public MyOrdersAdapter(Context context) {
        super(context);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.im_item_my_orders, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends CommonViewHolder {

        ImageView itemOrderImg;
        TextView itemOrderCode;
        TextView itemOrderPrice;
        TextView itemOrderTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemOrderImg = (ImageView) itemView.findViewById(R.id.iv_order_img);
            itemOrderCode = (TextView) itemView.findViewById(R.id.tv_order_code);
            itemOrderPrice = (TextView) itemView.findViewById(R.id.tv_order_price);
            itemOrderTime = (TextView) itemView.findViewById(R.id.tv_order_time);
        }

        @Override
        public void fillView(int position) {
            Ord01201Resp orderInfo = getItem(position);
            String picUrl = orderInfo.getOrd01102Resps().get(0).getPicUrl();
            String orderId = orderInfo.getOrderId();
            Long realMoney = orderInfo.getRealMoney();

            if (!TextUtils.isEmpty(picUrl)) {
                Glide.with(mContext).load(picUrl).into(itemOrderImg);
            } else {
                itemOrderImg.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.default_img));
            }

            if (!TextUtils.isEmpty(orderId)) {
                itemOrderCode.setText(orderId);
            } else {
                itemOrderCode.setVisibility(View.GONE);
            }

            if (realMoney != null) {
                itemOrderPrice.setText(MoneyUtils.GoodListPrice(realMoney));
            } else {
                itemOrderPrice.setVisibility(View.GONE);
            }

            if (orderInfo.getOrderTime() != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp timestamp = new Timestamp(orderInfo.getOrderTime().getTime());
                itemOrderTime.setText(df.format(timestamp));
            } else {
                itemOrderTime.setVisibility(View.GONE);
            }
        }

    }

}
