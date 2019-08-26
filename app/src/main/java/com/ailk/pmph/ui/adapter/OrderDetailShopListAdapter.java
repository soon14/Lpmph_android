package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord01101Resp;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.tool.GlideUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/4/6 12:59
 */
public class OrderDetailShopListAdapter extends BaseAdapter {

    private List<Ord01101Resp> mList;
    private Context mContext;
    private RedirectToShopDetail redirectToShopDetail;

    public OrderDetailShopListAdapter(Context context,List<Ord01101Resp> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void setRedirectToShopDetail(RedirectToShopDetail redirectToShopDetail) {
        this.redirectToShopDetail = redirectToShopDetail;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Ord01101Resp getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_orderdetail_shop_list, parent, false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        final Ord01101Resp product = getItem(position);
        if (product.getPicUrl()!=null) {
            GlideUtil.loadImg(mContext, product.getPicUrl(), holder.ivProductImg);
        } else {
            holder.ivProductImg.setImageResource(R.drawable.default_img);
        }
        holder.tvProductName.setText(product.getGdsName());
        if (product.getDiscountPrice() == null) {
            holder.tvProductPrice.setText("价格："+MoneyUtils.GoodListPrice(product.getBuyPrice()));
        } else {
            holder.tvProductPrice.setText("价格："+MoneyUtils.GoodListPrice(product.getDiscountPrice()));
        }
        holder.tvProductNum.setText("数量：x "+product.getOrderAmount());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToShopDetail.redirectToDetail(product);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_product_image)
        ImageView ivProductImg;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_price)
        TextView tvProductPrice;
        @BindView(R.id.tv_product_num)
        TextView tvProductNum;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

    public interface RedirectToShopDetail{
        void redirectToDetail(Ord01101Resp product);
    }

}
