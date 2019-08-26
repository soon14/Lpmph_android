package com.ailk.integral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord10901Resp;
import com.ai.ecp.app.resp.Ord10902Resp;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
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
 * 包名:com.ailk.integral.adp
 * 作者: Chrizz
 * 时间: 2016/5/11 19:50
 */
public class InteOrderListAdapter extends BaseExpandableListAdapter {

    private List<Ord10901Resp> groups;
    private Map<String, List<Ord10902Resp>> children;
    private Context mContext;
    private ClickChildInterface clickChildInterface;
    private PayMoneyInterface payMoneyInterface;
    private TakeGoodsInterface takeGoodsInterface;

    public InteOrderListAdapter(Context context, List<Ord10901Resp> groups, Map<String, List<Ord10902Resp>> children) {
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

    public void setPayMoneyInterface(PayMoneyInterface payMoneyInterface) {
        this.payMoneyInterface = payMoneyInterface;
    }

    public void setTakeGoodsInterface(TakeGoodsInterface takeGoodsInterface) {
        this.takeGoodsInterface = takeGoodsInterface;
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
        groupHolder.tvShopName.setText(shop.getShopName());
        if (StringUtils.equals("01", shop.getStatus()))
        {
            groupHolder.tvOrderStatus.setText("待支付");
            groupHolder.ivComplete.setVisibility(View.GONE);
            groupHolder.ivDelete.setVisibility(View.GONE);
        }
        else if (StringUtils.equals("02", shop.getStatus()))
        {
            groupHolder.tvOrderStatus.setText("待发货");
            groupHolder.ivComplete.setVisibility(View.GONE);
            groupHolder.ivDelete.setVisibility(View.GONE);
        }
        else if (StringUtils.equals("04", shop.getStatus()))
        {
            groupHolder.tvOrderStatus.setText("部分发货");
            groupHolder.ivComplete.setVisibility(View.GONE);
            groupHolder.ivDelete.setVisibility(View.GONE);
        }
        else if (StringUtils.equals("05", shop.getStatus()))
        {
            groupHolder.tvOrderStatus.setText("待收货");
            groupHolder.ivComplete.setVisibility(View.GONE);
            groupHolder.ivDelete.setVisibility(View.GONE);
        }
        else if (StringUtils.equals("06", shop.getStatus()))
        {
            groupHolder.tvOrderStatus.setText("已收货");
            groupHolder.ivComplete.setVisibility(View.VISIBLE);
            groupHolder.ivDelete.setVisibility(View.GONE);
        }
        else if (StringUtils.equals("80", shop.getStatus()))
        {
            groupHolder.tvOrderStatus.setText("已关闭");
            groupHolder.ivComplete.setVisibility(View.GONE);
            groupHolder.ivDelete.setVisibility(View.GONE);
        }
        else if (StringUtils.equals("99", shop.getStatus()))
        {
            groupHolder.tvOrderStatus.setText("已取消");
            groupHolder.ivComplete.setVisibility(View.GONE);
            groupHolder.ivDelete.setVisibility(View.GONE);
        }
        else if (StringUtils.equals("07", shop.getStatus()))
        {
            groupHolder.tvOrderStatus.setText("已退货");
            groupHolder.ivComplete.setVisibility(View.GONE);
            groupHolder.ivDelete.setVisibility(View.GONE);
        }
        else if (StringUtils.equals("08", shop.getStatus()))
        {
            groupHolder.tvOrderStatus.setText("已退款");
            groupHolder.ivComplete.setVisibility(View.GONE);
            groupHolder.ivDelete.setVisibility(View.GONE);
        }
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
        Ord10902Resp product = getChild(groupPosition, childPosition);
        GlideUtil.loadImg(mContext, product.getPicUrl(), childHolder.ivProductImg);
        childHolder.tvProductName.setText(product.getGdsName());
        if (isLastChild) {
            childHolder.llPriceLayout.setVisibility(View.VISIBLE);
            childHolder.tvPayPrice.setText(shop.getOrderScore() + "积分 + " + MoneyUtils.GoodListPrice(shop.getRealMoney()));
            if (StringUtils.equals("01", shop.getStatus())) //待支付
            {
                if (StringUtils.equals("0", shop.getPayType())) //线上支付
                {
                    childHolder.tvGoPay.setVisibility(View.VISIBLE);
                    childHolder.tvBuyAgain.setVisibility(View.GONE);
                }
                else
                {
                    childHolder.tvGoPay.setVisibility(View.INVISIBLE);
                    childHolder.tvBuyAgain.setVisibility(View.GONE);
                }
            }
            else if (StringUtils.equals("02", shop.getStatus())) //已支付
            {
                childHolder.tvGoPay.setVisibility(View.INVISIBLE);
                childHolder.tvBuyAgain.setVisibility(View.GONE);
            }
            else if (StringUtils.equals("04", shop.getStatus())) //部分发货
            {
                childHolder.tvGoPay.setVisibility(View.INVISIBLE);
                childHolder.tvBuyAgain.setVisibility(View.GONE);
            }
            else if (StringUtils.equals("05", shop.getStatus())) //待收货
            {
                childHolder.tvGoPay.setVisibility(View.VISIBLE);
                childHolder.tvBuyAgain.setVisibility(View.GONE);
                childHolder.tvGoPay.setText("确认收货");
            }
            else if (StringUtils.equals("06", shop.getStatus())) //已收货
            {
                childHolder.tvGoPay.setVisibility(View.INVISIBLE);
                childHolder.tvBuyAgain.setVisibility(View.GONE);
            }
            else if (StringUtils.equals("80", shop.getStatus())) //已关闭
            {
                childHolder.tvGoPay.setVisibility(View.INVISIBLE);
                childHolder.tvBuyAgain.setVisibility(View.GONE);
            }
            else if (StringUtils.equals("99", shop.getStatus())) //已取消
            {
                childHolder.tvGoPay.setVisibility(View.INVISIBLE);
                childHolder.tvBuyAgain.setVisibility(View.GONE);
            }
            else if (StringUtils.equals("07", shop.getStatus())) //已退货
            {
                childHolder.tvGoPay.setVisibility(View.INVISIBLE);
                childHolder.tvBuyAgain.setVisibility(View.GONE);
            }
            else if (StringUtils.equals("08", shop.getStatus())) //已退款
            {
                childHolder.tvGoPay.setVisibility(View.INVISIBLE);
                childHolder.tvBuyAgain.setVisibility(View.GONE);
            }
        } else {
            childHolder.llPriceLayout.setVisibility(View.GONE);
        }
        childHolder.llChildLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickChildInterface.clickChild(groupPosition);
            }
        });
        childHolder.tvGoPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.equals(childHolder.tvGoPay.getText().toString(),"确认收货")) {
                    takeGoodsInterface.takeGoods(shop);
                } else {
                    payMoneyInterface.payMoney(shop);
                }
            }
        });
        return convertView;
    }

    static class GroupHolder {
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.iv_complete)
        ImageView ivComplete;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.iv_del)
        ImageView ivDelete;

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
        void clickChild(int groupPosition);
    }

    /**
     * 待付款--去支付接口
     */
    public interface PayMoneyInterface {
        void payMoney(Ord10901Resp shop);
    }

    /**
     * 待收货--确认收货
     */
    public interface TakeGoodsInterface {
        void takeGoods(Ord10901Resp shop);
    }

}
