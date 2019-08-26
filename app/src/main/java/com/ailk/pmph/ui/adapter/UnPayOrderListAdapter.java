package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord01201Resp;
import com.ai.ecp.app.resp.Ord01202Resp;
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
public class UnPayOrderListAdapter extends BaseExpandableListAdapter {

    private List<Ord01201Resp> groups;
    private Map<String, List<Ord01202Resp>> children;
    private Context mContext;
    private ClickChildInterface clickChildInterface;
    private PayMoneyInterface payMoneyInterface;
    private RefundInterface refundInterface;
    private TakeGoodsInterface takeGoodsInterface;
    private SalesReturnInterface salesReturnInterface;
    private DeleteOrderInterface deleteOrderInterface;
    private GroupInterface groupInterface;
    private String paraValue = "";

    public UnPayOrderListAdapter(Context context, List<Ord01201Resp> groups, Map<String, List<Ord01202Resp>> children) {
        this.mContext = context;
        this.groups = groups;
        this.children = children;
    }

    public void deleteData(Ord01201Resp data) {
        if (groups != null && data != null) {
            groups.remove(data);
            children.remove(data.getOrderId());
        }
        notifyDataSetChanged();
    }

    public void addData(List<Ord01201Resp> data) {
        if (groups != null && data != null && !data.isEmpty()) {
            groups.addAll(data);
            for (int i = 0; i < data.size(); i++) {
                Ord01201Resp bean = data.get(i);
                children.put(bean.getOrderId(),bean.getOrd01102Resps());
            }
        }
        notifyDataSetChanged();
    }

    public void setParaValue(String paraValue) {
        this.paraValue = paraValue;
    }

    public void setClickChildInterface(ClickChildInterface clickChildInterface) {
        this.clickChildInterface = clickChildInterface;
    }

    public void setPayMoneyInterface(PayMoneyInterface payMoneyInterface) {
        this.payMoneyInterface = payMoneyInterface;
    }

    public void setRefundInterface(RefundInterface refundInterface) {
        this.refundInterface = refundInterface;
    }

    public void setTakeGoodsInterface(TakeGoodsInterface takeGoodsInterface) {
        this.takeGoodsInterface = takeGoodsInterface;
    }

    public void setSalesReturnInterface(SalesReturnInterface salesReturnInterface) {
        this.salesReturnInterface = salesReturnInterface;
    }

    public void setDeleteOrderInterface(DeleteOrderInterface deleteOrderInterface) {
        this.deleteOrderInterface = deleteOrderInterface;
    }

    public void setGroupInterface(GroupInterface groupInterface) {
        this.groupInterface = groupInterface;
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
    public Ord01201Resp getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Ord01202Resp getChild(int groupPosition, int childPosition) {
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
        final GroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_unpay_group_order_list, parent, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        final Ord01201Resp shop = getGroup(groupPosition);
        if (StringUtils.isNotEmpty(paraValue)) {
            if (StringUtils.equals("1", paraValue) && StringUtils.equals("0",shop.getPayType())) {
                groupHolder.cbCheck.setVisibility(View.VISIBLE);
            } else {
                groupHolder.cbCheck.setVisibility(View.GONE);
            }
        }
        groupHolder.tvShopName.setText(shop.getShopName());
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            groupHolder.tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
        } else {
            groupHolder.tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        if (StringUtils.equals("01", shop.getStatus()))
        {
            groupHolder.tvOrderStatus.setText("待支付");
            groupHolder.ivComplete.setVisibility(View.GONE);
            groupHolder.ivDelete.setVisibility(View.GONE);
        }
        else if (StringUtils.equals("02", shop.getStatus()))
        {
            groupHolder.tvOrderStatus.setText("已支付");
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
            groupHolder.tvOrderStatus.setText("已发货");
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

        if (groupHolder.cbCheck.getVisibility() == View.VISIBLE) {
            groupHolder.cbCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shop.setIsChoosed(((CheckBox) v).isChecked());
                    groupInterface.checkGroup(groupPosition,((CheckBox) v).isChecked());
                }
            });
            groupHolder.cbCheck.setChecked(shop.isChoosed());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (groupHolder.cbCheck.isChecked()) {
                        groupHolder.cbCheck.setChecked(false);
                    } else {
                        groupHolder.cbCheck.setChecked(true);
                    }
                    shop.setIsChoosed(groupHolder.cbCheck.isChecked());
                    groupInterface.checkGroup(groupPosition,groupHolder.cbCheck.isChecked());
                }
            });
            groupHolder.cbCheck.setChecked(shop.isChoosed());
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
        final Ord01201Resp shop = getGroup(groupPosition);
        Ord01202Resp product = getChild(groupPosition, childPosition);
        if (product.getPicUrl()!=null) {
            GlideUtil.loadImg(mContext, product.getPicUrl(), childHolder.ivProductImg);
//            mAQuery.id(childHolder.ivProductImg).image(product.getPicUrl(), true, true, 200, R.drawable.default_img, AppContext.bmPreset, 0);
        } else {
            childHolder.ivProductImg.setImageResource(R.drawable.default_img);
        }
        childHolder.tvProductName.setText(product.getGdsName());
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            childHolder.tvPayPrice.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
        } else {
            childHolder.tvPayPrice.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        if (isLastChild) {
            childHolder.llPriceLayout.setVisibility(View.VISIBLE);
            childHolder.tvPayPrice.setText(MoneyUtils.GoodListPrice(shop.getRealMoney()));
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
                childHolder.tvGoPay.setVisibility(View.GONE);
                childHolder.tvBuyAgain.setVisibility(View.VISIBLE);
                childHolder.tvBuyAgain.setVisibility(View.GONE);
            }
            else if (StringUtils.equals("04", shop.getStatus())) //部分发货
            {
                childHolder.tvGoPay.setVisibility(View.GONE);
                childHolder.tvBuyAgain.setVisibility(View.GONE);
            }
            else if (StringUtils.equals("05", shop.getStatus())) //已发货
            {
                childHolder.tvGoPay.setVisibility(View.GONE);
                childHolder.tvBuyAgain.setVisibility(View.VISIBLE);
                childHolder.tvBuyAgain.setText("确认收货");
            }
            else if (StringUtils.equals("06", shop.getStatus())) //已收货
            {
                childHolder.tvGoPay.setVisibility(View.GONE);
                childHolder.tvBuyAgain.setVisibility(View.VISIBLE);
                childHolder.tvBuyAgain.setVisibility(View.GONE);
            }
            else if (StringUtils.equals("80", shop.getStatus())) //已关闭
            {
                childHolder.tvGoPay.setVisibility(View.GONE);
                childHolder.tvBuyAgain.setVisibility(View.GONE);
            }
            else if (StringUtils.equals("99", shop.getStatus())) //已取消
            {
                childHolder.tvGoPay.setVisibility(View.GONE);
                childHolder.tvBuyAgain.setVisibility(View.GONE);
            }
            else if (StringUtils.equals("07", shop.getStatus())) //已退货
            {
                childHolder.tvGoPay.setVisibility(View.GONE);
                childHolder.tvBuyAgain.setVisibility(View.GONE);
            }
            else if (StringUtils.equals("08", shop.getStatus())) //已退款
            {
                childHolder.tvGoPay.setVisibility(View.GONE);
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
                payMoneyInterface.payMoney(shop);
            }
        });
        childHolder.tvBuyAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.equals(childHolder.tvBuyAgain.getText().toString(),"申请退款"))
                {
                    refundInterface.refund(shop);
                }
                if (StringUtils.equals(childHolder.tvBuyAgain.getText().toString(),"确认收货"))
                {
                    takeGoodsInterface.takeGoods(shop);
                }
                if (StringUtils.equals(childHolder.tvBuyAgain.getText().toString(),"申请退货"))
                {
                    salesReturnInterface.salesReturn(shop);
                }
            }
        });
        return convertView;
    }

    static class GroupHolder {
        @BindView(R.id.cb_check)
        CheckBox cbCheck;
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

    public interface GroupInterface {
        void checkGroup(int groupPosition, boolean isChecked);
    }

    public interface ClickChildInterface {
        void clickChild(int groupPosition);
    }

    /**
     * 待付款--去支付接口
     */
    public interface PayMoneyInterface {
        void payMoney(Ord01201Resp shop);
    }

    /**
     * 待发货--申请退款接口
     */
    public interface RefundInterface {
        void refund(Ord01201Resp shop);
    }

    /**
     * 待收货--确认收货
     */
    public interface TakeGoodsInterface {
        void takeGoods(Ord01201Resp shop);
    }

    /**
     * 待评价--申请退货
     */
    public interface SalesReturnInterface {
        void salesReturn(Ord01201Resp shop);
    }

    /**
     * 删除订单
     */
    public interface DeleteOrderInterface {
        void deleteOrder(Ord01201Resp shop, int groupPostion);
    }

}
