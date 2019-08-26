package com.ailk.im.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ailk.imsdk.bean.message.body.OrderMessageBody;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.MoneyUtils;
import com.bumptech.glide.Glide;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.im.ui.dialog
 * 作者: Chrizz
 * 时间: 2017/2/17
 */

public class OrderDialog extends Dialog {

    private Context mContext;
    private String orderImgUrl;
    private String orderCode;
    private Long orderPrice;
    private Long orderTime;
    private SendOrderMsgListener mListener;

    public OrderDialog(Context context) {
        super(context);
    }

    public OrderDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public void setOrderImgUrl(String orderImgUrl) {
        this.orderImgUrl = orderImgUrl;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setOrderPrice(Long orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setOrderTime(Long orderTime) {
        this.orderTime = orderTime;
    }

    public void setListener(SendOrderMsgListener listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.dialog_im_item_my_order, null);
        ImageView orderImg = (ImageView) contentView.findViewById(R.id.iv_order_img);
        TextView orderCodeTv = (TextView) contentView.findViewById(R.id.tv_order_code);
        TextView orderPriceTv = (TextView) contentView.findViewById(R.id.tv_order_price);
        TextView orderTimeTv = (TextView) contentView.findViewById(R.id.tv_order_time);
        TextView confirmTv = (TextView) contentView.findViewById(R.id.tv_confirm);
        TextView cancelTv = (TextView) contentView.findViewById(R.id.tv_cancel);

        if (!TextUtils.isEmpty(orderImgUrl)) {
            Glide.with(mContext).load(orderImgUrl).into(orderImg);
        } else {
            orderImg.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.default_img));
        }
        if (!TextUtils.isEmpty(orderCode)) {
            orderCodeTv.setText(orderCode);
        } else {
            orderCodeTv.setVisibility(View.GONE);
        }
        if (orderPrice != null) {
            orderPriceTv.setText(MoneyUtils.GoodListPrice(orderPrice));
        } else {
            orderPriceTv.setVisibility(View.GONE);
        }
        if (orderTime != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            orderTimeTv.setText(df.format(new Timestamp(orderTime)));
        } else {
            orderTimeTv.setVisibility(View.GONE);
        }

        setContentView(contentView);
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                OrderMessageBody orderMessageBody = new OrderMessageBody();
                if (!TextUtils.isEmpty(orderCode)) {
                    orderMessageBody.setOrdId(orderCode);
                }
                if (!TextUtils.isEmpty(orderImgUrl)) {
                    orderMessageBody.setOrdImage(orderImgUrl);
                }
                if (orderPrice != null) {
                    orderMessageBody.setPrice(MoneyUtils.GoodListPrice(orderPrice));
                }
                if (orderTime != null) {
                    orderMessageBody.setCreateTime(new Date(orderTime));
                }
                mListener.sendOrderMsg(orderMessageBody);
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (getWindow() != null) {
            WindowManager windowManager = getWindow().getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.width = display.getWidth() - 100;
            getWindow().setAttributes(layoutParams);
        }
    }

    public interface SendOrderMsgListener {
        void sendOrderMsg(OrderMessageBody orderMessageBody);
    }

}
