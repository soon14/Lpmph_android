package com.ailk.im.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.vo.IM00301VO;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.MoneyUtils;
import com.bumptech.glide.Glide;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.im.ui.adapter
 * 作者: Chrizz
 * 时间: 2017/2/24
 */

public class BrowseGoodsAdapter extends CommonRecyclerAdapter<IM00301VO> {

    public BrowseGoodsAdapter(Context context) {
        super(context);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.im_item_browse_goods, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends CommonViewHolder {

        ImageView itemGdsImg;
        TextView itemGdsName;
        TextView itemGdsPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemGdsImg = (ImageView) itemView.findViewById(R.id.iv_gds_img);
            itemGdsName = (TextView) itemView.findViewById(R.id.tv_gds_name);
            itemGdsPrice = (TextView) itemView.findViewById(R.id.tv_gds_price);
        }

        @Override
        public void fillView(int position) {
            IM00301VO gdsInfo = getItem(position);
            String imageUrl = gdsInfo.getImageUrl();
            String gdsName = gdsInfo.getGdsName();
            Long gdsPrice = gdsInfo.getDefaultPrice();

            if (!TextUtils.isEmpty(imageUrl)) {
                Glide.with(mContext).load(imageUrl).into(itemGdsImg);
            } else {
                itemGdsImg.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.default_img));
            }

            if (!TextUtils.isEmpty(gdsName)) {
                itemGdsName.setText(gdsName);
            } else {
                itemGdsName.setVisibility(View.GONE);
            }

            if (gdsPrice != null) {
                itemGdsPrice.setText(MoneyUtils.GoodListPrice(gdsPrice));
            } else {
                itemGdsPrice.setVisibility(View.GONE);
            }
        }
    }

}
