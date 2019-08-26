package com.ailk.integral.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord10201Resp;
import com.ai.ecp.app.resp.Ord10202Resp;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.thirdstore.activity.StoreActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.tool.GlideUtil;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.adp
 * 作者: Chrizz
 * 时间: 2016/5/19 14:30
 */
public class InteOrderConfirmShopListAdapter extends BaseExpandableListAdapter {

    private List<Ord10201Resp> groups;
    private Map<Long, List<Ord10202Resp>> productsMap;
    private Context mContext;
    private RedirectToDetail redirectToDetail;

    public InteOrderConfirmShopListAdapter(Context context,List<Ord10201Resp> groups,Map<Long, List<Ord10202Resp>> productsMap){
        this.mContext=context;
        this.groups=groups;
        this.productsMap=productsMap;
    }

    public void setRedirectToDetail(RedirectToDetail redirectToDetail) {
        this.redirectToDetail = redirectToDetail;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Ord10201Resp group = groups.get(groupPosition);
        List<Ord10202Resp> products = productsMap.get(group.getShopId());
        return products.size();
    }

    @Override
    public Ord10201Resp getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Ord10202Resp getChild(int groupPosition, int childPosition) {
        Ord10201Resp group = groups.get(groupPosition);
        List<Ord10202Resp> products = productsMap.get(group.getShopId());
        return products.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inte_item_group_order_confirm, parent, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        final Ord10201Resp group = getGroup(groupPosition);
        groupHolder.tvShopName.setText(group.getShopName());
        groupHolder.storeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (group.getShopId() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.STORE_ID, String.valueOf(group.getShopId()));
                    Intent intent = new Intent(mContext, StoreActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ProductHolder productHolder;
        if (convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inte_item_child_order_confirm, parent, false);
            productHolder = new ProductHolder(convertView);
            convertView.setTag(productHolder);
        } else {
            productHolder = (ProductHolder) convertView.getTag();
        }
        final Ord10202Resp product = getChild(groupPosition, childPosition);
        GlideUtil.loadImg(mContext, product.getPicUrl(), productHolder.ivGdsImg);
        productHolder.tvGdsName.setText(product.getGdsName());
        productHolder.tvGdsPrice.setText(product.getScore()+"积分 + "+ MoneyUtils.GoodListPrice(product.getBuyPrice()));
        productHolder.tvGdsNum.setText("数量：x " + product.getOrderAmount());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToDetail.toDetail(product);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupHolder {
        @BindView(R.id.store_layout)
        LinearLayout storeLayout;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;

        public GroupHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ProductHolder{
        @BindView(R.id.iv_image)
        ImageView ivGdsImg;
        @BindView(R.id.tv_product_name)
        TextView tvGdsName;
        @BindView(R.id.tv_product_price)
        TextView tvGdsPrice;
        @BindView(R.id.tv_product_num)
        TextView tvGdsNum;

        public ProductHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    public interface RedirectToDetail{
        void toDetail(Ord10202Resp product);
    }
}
