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

import com.ailk.imsdk.bean.message.body.GoodMessageBody;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.MoneyUtils;
import com.bumptech.glide.Glide;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.im.ui.dialog
 * 作者: Chrizz
 * 时间: 2017/2/17
 */

public class BrowseDialog extends Dialog {

    private Context mContext;
    private String gdsImgUrl;
    private String gdsName;
    private Long gdsPrice;
    private Long gdsId;
    private SendGdsMsgListener mListener;

    public BrowseDialog(Context context) {
        super(context);
    }

    public BrowseDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public void setGdsImgUrl(String gdsImgUrl) {
        this.gdsImgUrl = gdsImgUrl;
    }

    public void setGdsName(String gdsName) {
        this.gdsName = gdsName;
    }

    public void setGdsPrice(Long gdsPrice) {
        this.gdsPrice = gdsPrice;
    }

    public void setGdsId(Long gdsId) {
        this.gdsId = gdsId;
    }

    public void setListener(SendGdsMsgListener listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.dialog_im_item_browse, null);
        ImageView gdsImgIv = (ImageView) contentView.findViewById(R.id.iv_gds_img);
        TextView gdsNameTv = (TextView) contentView.findViewById(R.id.tv_gds_name);
        TextView gdsPriceTv = (TextView) contentView.findViewById(R.id.tv_gds_price);
        TextView confirmTv = (TextView) contentView.findViewById(R.id.tv_confirm);
        TextView cancelTv = (TextView) contentView.findViewById(R.id.tv_cancel);

        if (!TextUtils.isEmpty(gdsImgUrl)) {
            Glide.with(mContext).load(gdsImgUrl).into(gdsImgIv);
        } else {
            gdsImgIv.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.default_img));
        }
        if (!TextUtils.isEmpty(gdsName)) {
            gdsNameTv.setText(gdsName);
        } else {
            gdsNameTv.setVisibility(View.GONE);
        }
        if (gdsPrice != null) {
            gdsPriceTv.setText(MoneyUtils.GoodListPrice(gdsPrice));
        } else {
            gdsPriceTv.setVisibility(View.GONE);
        }

        setContentView(contentView);
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                GoodMessageBody goodMessageBody = new GoodMessageBody();
                if (gdsId != null) {
                    goodMessageBody.setGdsId(gdsId);
                }
                if (!TextUtils.isEmpty(gdsImgUrl)) {
                    goodMessageBody.setGdsImage(gdsImgUrl);
                }
                if (!TextUtils.isEmpty(gdsName)) {
                    goodMessageBody.setGdsName(gdsName);
                }
                if (gdsPrice != null) {
                    goodMessageBody.setPrice(MoneyUtils.GoodListPrice(gdsPrice));
                }
                mListener.sendGdsMsg(goodMessageBody);
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

    public interface SendGdsMsgListener {
        void sendGdsMsg(GoodMessageBody goodMessageBody);
    }

}
