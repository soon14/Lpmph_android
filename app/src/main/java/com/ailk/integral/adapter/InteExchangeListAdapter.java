package com.ailk.integral.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord10901Resp;
import com.ai.ecp.app.resp.Ord10902Resp;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/4/5 11:31
 */
public class InteExchangeListAdapter extends BaseExpandableListAdapter {

    private List<Ord10901Resp> groups;
    private Map<String, List<Ord10902Resp>> children;
    private Context mContext;
    private ClickChildInterface clickChildInterface;

    public InteExchangeListAdapter(Context context, List<Ord10901Resp> groups, Map<String, List<Ord10902Resp>> children) {
        this.mContext = context;
        this.groups = groups;
        this.children = children;
    }

    public void addData(List<Ord10901Resp> data) {
        if (groups != null && data != null && !data.isEmpty()) {
            groups.addAll(data);
            for (Ord10901Resp bean : data) {
                children.put(bean.getOrderId(),bean.getOrd01102Resps());
            }
        }
        notifyDataSetChanged();
    }

    public void setClickChildInterface(ClickChildInterface clickChildInterface) {
        this.clickChildInterface = clickChildInterface;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(groups.get(groupPosition).getOrderId()).size();
    }

    @Override
    public Ord10901Resp getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Ord10902Resp getChild(int groupPosition, int childPosition) {
        return children.get(groups.get(groupPosition).getOrderId()).get(childPosition);
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
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group_order_list, parent, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        final Ord10901Resp shop = getGroup(groupPosition);
        groupHolder.tvStatus.setVisibility(View.GONE);
        groupHolder.ivComplete.setVisibility(View.GONE);
        groupHolder.tvShopName.setText(shop.getShopName());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_child_order_list, parent, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        final Ord10901Resp shop = getGroup(groupPosition);
        final Ord10902Resp product = getChild(groupPosition, childPosition);
        childHolder.tvBuyAgain.setVisibility(View.GONE);
        childHolder.tvGoPay.setVisibility(View.GONE);
        GlideUtil.loadImg(mContext, product.getPicUrl(), childHolder.ivProductImg);
        childHolder.tvProductName.setText(product.getGdsName());
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            childHolder.tvPayPrice.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
        } else {
            childHolder.tvPayPrice.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        if (isLastChild) {
            childHolder.llPriceLayout.setVisibility(View.VISIBLE);
            childHolder.tvPayPrice.setText(shop.getOrderScore() + "积分 + " + MoneyUtils.GoodListPrice(shop.getRealMoney()));
        } else {
            childHolder.llPriceLayout.setVisibility(View.GONE);
        }
        childHolder.llChildLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickChildInterface.clickChild(product);
            }
        });
        return convertView;
    }

    static class GroupHolder {
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_order_status)
        TextView tvStatus;
        @BindView(R.id.iv_complete)
        ImageView ivComplete;
        public GroupHolder (View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildHolder {
        @BindView(R.id.iv_product_image)
        ImageView ivProductImg;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_pay_price)
        TextView tvPayPrice;
        @BindView(R.id.tv_go_pay)
        TextView tvGoPay;
        @BindView(R.id.tv_buy_again)
        TextView tvBuyAgain;
        @BindView(R.id.ll_order_child_layout)
        LinearLayout llChildLayout;
        @BindView(R.id.ll_price_layout)
        LinearLayout llPriceLayout;

        public ChildHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface ClickChildInterface {
        void clickChild(Ord10902Resp product);
    }

}
