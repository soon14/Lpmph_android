package com.ailk.pmph.thirdstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.gds.ShopSearchResultVO;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.thirdstore.activity.StoreActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.activity.ShopDetailActivity;
import com.ailk.tool.GlideUtil;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类描述：
 * 项目名称： pmph
 * Package:  com.ailk.pmph.ui.adapter
 * 创建人：   Nzke
 * 创建时间： 2016/5/31 15:46
 * 修改人：   Nzke
 * 修改时间： 2016/5/31 15:46
 * 修改备注： 2016/5/31 15:46
 * version   v1.0
 */
public class StoreSearchAdapter extends BaseAdapter {

    private Context mContext;
    private List<ShopSearchResultVO> mList;

    public StoreSearchAdapter(Context context, List<ShopSearchResultVO> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList != null && mList.size() > 0 ? mList.size() : 0;
    }

    @Override
    public ShopSearchResultVO getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_store_search, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ShopSearchResultVO shopSearchResultVO = getItem(position);
        GlideUtil.loadImg(mContext, shopSearchResultVO.getLogoUrl(), holder.itemImg);
        holder.itemName.setText(Html.fromHtml(shopSearchResultVO.getShopName()));
        holder.itemSales.setText("销量:"+shopSearchResultVO.getSaleCount());
        holder.itemInventory.setText("共"+shopSearchResultVO.getGdsCount()+"件商品");

        int num = shopSearchResultVO.getGoodList().size() >= 4 ? 4 : shopSearchResultVO.getGoodList().size();

        if (0 != num) {
            holder.layoutImg.setVisibility(View.VISIBLE);
            holder.itemNoStoreImg.setVisibility(View.GONE);
            for (int i = 0; i < 4; i++) {
                if (i >= num) {
                    holder.layoutImg.getChildAt(i).setVisibility(View.INVISIBLE);
                }else {
                    GlideUtil.loadImg(mContext, shopSearchResultVO.getGoodList().get(i).getImageUrl(), (ImageView) holder.layoutImg.getChildAt(i));
                    holder.layoutImg.getChildAt(i).setVisibility(View.VISIBLE);
                    final int j = i;
                    holder.layoutImg.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(shopSearchResultVO.getGoodList().get(j).getId()));
                            bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(shopSearchResultVO.getGoodList().get(j).getFirstSkuId()));
                            ((BaseActivity) mContext).launch(ShopDetailActivity.class, bundle);
                        }
                    });
                }
            }
        }else {
            holder.layoutImg.setVisibility(View.GONE);
            holder.itemNoStoreImg.setVisibility(View.VISIBLE);
        }

        holder.toStoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,StoreActivity.class);
                intent.putExtra(Constant.STORE_ID,shopSearchResultVO.getId());//统一传入String
                ((BaseActivity) mContext).launch(intent);
            }
        });

        return convertView;
    }

    public void addAll(Collection<ShopSearchResultVO> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void remove(int location) {
        mList.remove(location);
        notifyDataSetChanged();
    }


    static class ViewHolder {
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_sales)
        TextView itemSales;
        @BindView(R.id.item_inventory)
        TextView itemInventory;
        @BindView(R.id.to_store_tv)
        TextView toStoreTv;
        @BindView(R.id.layout_img)
        LinearLayout layoutImg;
        @BindView(R.id.item_no_store_img)
        LinearLayout itemNoStoreImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
