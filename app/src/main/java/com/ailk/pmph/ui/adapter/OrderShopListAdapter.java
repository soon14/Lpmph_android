package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.AcctInfoResDTO;
import com.ai.ecp.app.resp.CoupCheckBeanRespDTO;
import com.ai.ecp.app.resp.Ord00201Resp;
import com.ai.ecp.app.resp.Ord00202Resp;
import com.ai.ecp.app.resp.Ord00204Resp;
import com.ai.ecp.app.resp.Ord00205Resp;
import com.ai.ecp.app.resp.Ord00801Resp;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.thirdstore.activity.StoreActivity;
import com.ailk.pmph.ui.view.MyEditText;
import com.ailk.pmph.ui.view.PointLengthFilter;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.ui.view.CustomListView;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/3/28 15:33
 */
public class OrderShopListAdapter extends BaseExpandableListAdapter {

    private List<Ord00201Resp> groups;
    private Map<Long, List<Ord00202Resp>> children;
    private Context mContext;
    private List<Ord00801Resp> ord00801Resps;
    private StartCouponInterface startCouponInterface;
    private StartBillInterface startBillInterface;
    private UseCashInterface useCashInterface;
    private UseContractInterface useContractInterface;
    private PromotionCodeInterface mPromotionCodeInterface;
    private SendDeliverTypeInterface sendDeliverTypeInterface;
    private List<Long> checkCoupIds;
    private RedirectToShopDetailInterface redirectToShopDetailInterface;
    private SendBillInterface sendBillInterface;
    private String billType = "2";//0为普票，2为不开票
    private String deliverTypes;
    private Long coupValue = 0L;
    private Long shopId;
    private String takeSelf;
    private String ifcoupCodeOpen;

//    private List<String> mList;

    public OrderShopListAdapter(Context context, List<Ord00201Resp> groups, Map<Long, List<Ord00202Resp>> children) {
        this.mContext = context;
        this.groups = groups;
//        this.mList = mList;
        this.children = children;
    }

//    public List<String> getList() {
//        return mList;
//    }
//
//    public void add(String s) {
//        mList.add(s);
//        notifyDataSetChanged();
//    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
        notifyDataSetChanged();
    }

    public void setCheckCoupIds(List<Long> checkCoupIds) {
        this.checkCoupIds = checkCoupIds;
    }

    public void setTakeSelf(String takeSelf) {
        this.takeSelf = takeSelf;
        notifyDataSetChanged();
    }

    public void setCoupValue(Long coupValue) {
        this.coupValue = coupValue;
        notifyDataSetChanged();
    }

    public void setOrd00801Resps(List<Ord00801Resp> ord00801Resps) {
        this.ord00801Resps = ord00801Resps;
        notifyDataSetChanged();
    }

    public void setStartCouponInterface(StartCouponInterface startCouponInterface) {
        this.startCouponInterface = startCouponInterface;
    }

    public void setStartBillInterface(StartBillInterface startBillInterface) {
        this.startBillInterface = startBillInterface;
    }

    public void setUseCashInterface(UseCashInterface useCashInterface) {
        this.useCashInterface = useCashInterface;
    }

    public void setUseContractInterface(UseContractInterface useContractInterface) {
        this.useContractInterface = useContractInterface;
    }

    public void setPromotionCodeInterface(PromotionCodeInterface promotionCodeInterface) {
        mPromotionCodeInterface = promotionCodeInterface;
    }

    public void setSendDeliverTypeInterface(SendDeliverTypeInterface sendDeliverTypeInterface) {
        this.sendDeliverTypeInterface = sendDeliverTypeInterface;
    }

    public void setRedirectToShopDetailInterface(RedirectToShopDetailInterface redirectToShopDetailInterface) {
        this.redirectToShopDetailInterface = redirectToShopDetailInterface;
    }

    public void setSendBillInterface(SendBillInterface sendBillInterface) {
        this.sendBillInterface = sendBillInterface;
    }

    public void setIfcoupCodeOpen(String ifcoupCodeOpen) {
        this.ifcoupCodeOpen = ifcoupCodeOpen;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Ord00201Resp group = groups.get(groupPosition);
        Ord00204Resp goods = group.getGroupLists();
        List<Object> productList = new ArrayList<>();
        if (goods!=null) {
            List<Ord00205Resp> groupLists = goods.getGroupLists();
            productList.addAll(groupLists);
        }
        List<Ord00202Resp> childs = children.get(group.getShopId());
        if (childs!=null) {
            productList.addAll(childs);
        }
        return productList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Ord00201Resp group = (Ord00201Resp) getGroup(groupPosition);
        Ord00204Resp goods = group.getGroupLists();
        List<Object> productList = new ArrayList<>();
        if (goods!=null) {
            List<Ord00205Resp> groupLists = goods.getGroupLists();
            productList.addAll(groupLists);
        }
        List<Ord00202Resp> childs = children.get(group.getShopId());
        if (childs!=null) {
            productList.addAll(childs);
        }
        return productList.get(childPosition);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.group_orderconfirm_item, parent, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        final Ord00201Resp group = (Ord00201Resp) getGroup(groupPosition);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            groupHolder.tvGiftInfo.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
        } else {
            groupHolder.tvGiftInfo.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        if (StringUtils.isEmpty(group.getGiftInfo())) {
            groupHolder.tvGiftInfo.setVisibility(View.GONE);
        } else {
            groupHolder.tvGiftInfo.setText("【店铺赠品】 "+ StringUtils.remove(group.getGiftInfo(),"、"));
        }
        groupHolder.tvShopName.setText(group.getShopName());
        if (!StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
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
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, final View convertView, ViewGroup parent) {
        final Ord00201Resp group = groups.get(groupPosition);
        View mView = convertView;
        Object obj = getChild(groupPosition, childPosition);
        Object holder = null;

        if (mView == null)
        {
            if (obj instanceof Ord00205Resp)
            {
                mView = LayoutInflater.from(mContext).inflate(R.layout.item_child_groups_order_confirm, parent, false);
                holder = new ChildGroupHolder(mView);
            }
            else if (obj instanceof Ord00202Resp)
            {
                mView = LayoutInflater.from(mContext).inflate(R.layout.item_child_order_confirm, parent, false);
                holder = new ChildHolder(mView);
            }
        }
        else
        {
            holder = mView.getTag();
            if (obj instanceof Ord00205Resp && !(holder instanceof ChildGroupHolder))
            {
                mView = LayoutInflater.from(mContext).inflate(R.layout.item_child_groups_order_confirm, parent, false);
                holder = new ChildGroupHolder(mView);
            }
            else if (obj instanceof Ord00202Resp && !(holder instanceof ChildHolder))
            {
                mView = LayoutInflater.from(mContext).inflate(R.layout.item_child_order_confirm, parent, false);
                holder = new ChildHolder(mView);
            }
        }

        //显示组合商品
        if (obj instanceof Ord00205Resp && holder instanceof ChildGroupHolder)
        {
            final Ord00205Resp groupLists = (Ord00205Resp) obj;
            final ChildGroupHolder childGroupHolder = (ChildGroupHolder) holder;
            OrderGroupListsAdapter adapter = new OrderGroupListsAdapter(mContext, groupLists.getGroupSkus());
            childGroupHolder.lvGroupsList.setAdapter(adapter);
            if (isLastChild)
            {
                childGroupHolder.llCoupon.setVisibility(View.VISIBLE);
                childGroupHolder.llCash.setVisibility(View.VISIBLE);
                childGroupHolder.llBill.setVisibility(View.VISIBLE);
                childGroupHolder.llDeliver.setVisibility(View.VISIBLE);
                if (StringUtils.isNotEmpty(ifcoupCodeOpen)) {
                    if (StringUtils.equals("1", ifcoupCodeOpen)) {
                        childGroupHolder.llPromotionCodeLayout.setVisibility(View.VISIBLE);
                    } else {
                        childGroupHolder.llPromotionCodeLayout.setVisibility(View.GONE);
                    }
                }
            }
            else
            {
                childGroupHolder.llCoupon.setVisibility(View.GONE);
                childGroupHolder.llCash.setVisibility(View.GONE);
                childGroupHolder.llBill.setVisibility(View.GONE);
                childGroupHolder.llDeliver.setVisibility(View.GONE);
                childGroupHolder.llPromotionCodeLayout.setVisibility(View.GONE);
            }


            childGroupHolder.tvUnBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickUnBill(childGroupHolder.tvUnBill, childGroupHolder.tvBill);
                    billType = "2";
                    childGroupHolder.tvBillOne.setText("");
                    childGroupHolder.tvBillTwo.setText("");
                    childGroupHolder.tvBillThere.setText("");
                    sendBillInterface.sendBill(billType, group.getShopId());
                }
            });
            childGroupHolder.tvBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickBill(childGroupHolder.tvBill, childGroupHolder.tvUnBill);
                    billType = "0";
                    startBillInterface.startBill(childGroupHolder.tvBillOne.getText().toString(),
                            childGroupHolder.tvBillTwo.getText().toString(), childGroupHolder.tvBillThere.getText().toString(), group);
                    sendBillInterface.sendBill(billType, group.getShopId());
                }
            });
            //不开发票
            if (StringUtils.equals("2", billType))
            {
                clickUnBill(childGroupHolder.tvUnBill, childGroupHolder.tvBill);
                childGroupHolder.tvBillOne.setText("");
                childGroupHolder.tvBillTwo.setText("");
                childGroupHolder.tvBillThere.setText("");
            }
            else
            {
                clickBill(childGroupHolder.tvBill, childGroupHolder.tvUnBill);
                if (group.getBillTypeText()!=null)
                {
                    childGroupHolder.tvBillOne.setText(group.getBillTypeText());
                }
                else
                {
                    childGroupHolder.tvBillOne.setText(null);
                }

                if (group.getBillTitle()!=null)
                {
                    childGroupHolder.tvBillTwo.setText(group.getBillTitle());
                }
                else
                {
                    childGroupHolder.tvBillTwo.setText(null);
                }

                if (group.getBillContent()!=null)
                {
                    childGroupHolder.tvBillThere.setText(group.getBillContent());
                }
                else
                {
                    childGroupHolder.tvBillThere.setText(null);
                }
            }
            if (StringUtils.isEmpty(childGroupHolder.tvBillOne.getText().toString())
                    && StringUtils.isEmpty(childGroupHolder.tvBillTwo.getText().toString())
                    && StringUtils.isEmpty(childGroupHolder.tvBillThere.getText().toString()))
            {
                clickUnBill(childGroupHolder.tvUnBill, childGroupHolder.tvBill);
            }

            //显示资金账户、合约账户
            for (final Ord00801Resp bean : ord00801Resps) {
                Long coupNum = 0L;
                if (bean.getShopId().longValue() == group.getShopId().longValue()) {
                    if (bean.getCoupOrdSkuList() == null) {
                        childGroupHolder.llCoupon.setVisibility(View.GONE);
                    } else {
                        for (CoupCheckBeanRespDTO coup : bean.getCoupOrdSkuList()) {
                            coupNum += coup.getCoupSize();
                        }
                        childGroupHolder.tvCoupSize.setText(coupNum + "张可用");
                        if (group.getCoupIdList() != null && group.getCoupValueList() != null) {
                            Long tempValue=0L;
                            for (Long value : group.getCoupValueList()) {
                                tempValue += value;
                            }
                            childGroupHolder.tvCoupValue.setText(MoneyUtils.GoodListPrice(tempValue));
                        } else {
                            childGroupHolder.tvCoupValue.setText(null);

                        }
                        childGroupHolder.llCoupon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startCouponInterface.startCoupon(group,bean.getCoupOrdSkuList(),checkCoupIds);
                            }
                        });
                    }
                    if (bean.getAcctInfoList() == null || bean.getAcctInfoList().size() == 0) {
                        childGroupHolder.llCash.setVisibility(View.GONE);
                    } else {
                        childGroupHolder.etShopCash.setFilters(new InputFilter[]{new PointLengthFilter()});
                        childGroupHolder.etContractCash.setFilters(new InputFilter[]{new PointLengthFilter()});

                        if (bean.getAcctInfoList().size() >1) {
                            final AcctInfoResDTO acctInfo = bean.getAcctInfoList().get(0);
                            childGroupHolder.tvShopCash.setText(acctInfo.getAcctName()+"账户，最大可使用"
                                    + StringUtils.remove(MoneyUtils.GoodListPrice(acctInfo.getDeductOrderMoney()),"￥") +"元，");
                            final AcctInfoResDTO acctInfoRes = bean.getAcctInfoList().get(1);
                            childGroupHolder.tvContractCash.setText(acctInfoRes.getAcctName()+"账户，最大可使用"
                                    + StringUtils.remove(MoneyUtils.GoodListPrice(acctInfoRes.getDeductOrderMoney()), "￥") + "元，");

                            childGroupHolder.tvShopCashUse.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    useCashInterface.useCash(bean.getShopId(),acctInfo, childGroupHolder.etShopCash,
                                            childGroupHolder.tvShopCashInput,childGroupHolder.tvShopCashUse,groupPosition);
                                }
                            });

                            childGroupHolder.tvContractCashUse.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    useContractInterface.useContract(bean.getShopId(),acctInfoRes, childGroupHolder.etContractCash,
                                            childGroupHolder.tvContractInput,childGroupHolder.tvContractCashUse);
                                }
                            });

                            childGroupHolder.llCashLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (childGroupHolder.llShopCashLayout.getVisibility()==View.VISIBLE) {
                                        childGroupHolder.ivCashArrow.setImageResource(R.drawable.arrow_right);
                                        childGroupHolder.llShopCashLayout.setVisibility(View.GONE);
                                        childGroupHolder.llContactLayout.setVisibility(View.GONE);
                                    } else {
                                        childGroupHolder.ivCashArrow.setImageResource(R.drawable.arrow_down);
                                        childGroupHolder.llShopCashLayout.setVisibility(View.VISIBLE);
                                        childGroupHolder.llContactLayout.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        } else {
                            final AcctInfoResDTO acctInfo = bean.getAcctInfoList().get(0);
                            childGroupHolder.tvShopCash.setText(acctInfo.getAcctName()+"账户，最大可使用"
                                    + StringUtils.remove(MoneyUtils.GoodListPrice(acctInfo.getDeductOrderMoney()),"￥") +"元，");

                            childGroupHolder.tvShopCashUse.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    useCashInterface.useCash(bean.getShopId(), acctInfo, childGroupHolder.etShopCash,
                                            childGroupHolder.tvShopCashInput, childGroupHolder.tvShopCashUse, groupPosition);
                                }
                            });
                            childGroupHolder.llContactLayout.setVisibility(View.GONE);
                            childGroupHolder.llCashLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (childGroupHolder.llShopCashLayout.getVisibility()==View.VISIBLE) {
                                        childGroupHolder.ivCashArrow.setImageResource(R.drawable.arrow_right);
                                        childGroupHolder.llShopCashLayout.setVisibility(View.GONE);
                                    } else {
                                        childGroupHolder.ivCashArrow.setImageResource(R.drawable.arrow_down);
                                        childGroupHolder.llShopCashLayout.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                    }
                }
            }

            childGroupHolder.tvPromotionCodeUse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Long coupValue = 0L;
                    List<Long> coupValueList = group.getCoupValueList();
                    if (coupValueList != null && coupValueList.size() != 0) {
                        for (int i = 0; i < coupValueList.size(); i++) {
                            coupValue += coupValueList.get(i);
                        }
                    }
                    if (coupValue != 0 || StringUtils.equals("1", group.getNoExpress())) {
                        ToastUtil.showCenter(mContext, "优惠码与优惠券不能同时使用");
                    } else {
                        if (StringUtils.isNotEmpty(childGroupHolder.etPromotionCode.getText().toString())) {
                            mPromotionCodeInterface.usePromotionCode(group, childGroupHolder.tvPromotionCodeText,
                                    childGroupHolder.etPromotionCode, childGroupHolder.tvPromotionCodeInput,
                                    childGroupHolder.tvPromotionCodeUse);
                        } else {
                            ToastUtil.showCenter(mContext, "请输入优惠码");
                        }
                    }
                }
            });

            //根据配置显示配送方式:0为邮局,1为快递,2为自提
            if (StringUtils.equals(takeSelf, "自提")) {
                clickDeliverTake(childGroupHolder.tvDeliverTake, childGroupHolder.tvDeliverExpress, childGroupHolder.tvDeliverPost);
                group.setCheckDeliverType("2");
                sendDeliverTypeInterface.sendDeliverType(group);
            }
            deliverTypes = group.getDeliverTypes();
            if (!StringUtils.contains(deliverTypes,"1")
                    || (StringUtils.isNotEmpty(group.getNoExpress()) && StringUtils.equals("1", group.getNoExpress()))) {
                group.setExpressMoney(0L);
            } else {
                if (group.getCheckDeliverType()==null) {
                    group.setCheckDeliverType("1");
                }
            }

            if (StringUtils.contains(deliverTypes,","))
            {
                String[] types = StringUtils.split(deliverTypes,",");
                if (types.length == 2)
                {
                    showTwoDelivers(childGroupHolder.tvDeliverExpress,childGroupHolder.tvDeliverTake,childGroupHolder.tvDeliverPost);
                    if (StringUtils.equals("0", types[0]))
                    {
                        childGroupHolder.tvDeliverExpress.setText("邮局挂号");
                    }
                    else if (StringUtils.equals("1", types[0]))
                    {
                        childGroupHolder.tvDeliverExpress.setText("快递");
                    }
                    else if (StringUtils.equals("2", types[0]))
                    {
                        childGroupHolder.tvDeliverExpress.setText("自提");
                    }

                    if (StringUtils.equals("0", types[1]))
                    {
                        childGroupHolder.tvDeliverTake.setText("邮局挂号");
                    }
                    else if (StringUtils.equals("2", types[1]))
                    {
                        childGroupHolder.tvDeliverTake.setText("自提");
                    }
                }
                if (types.length == 3)
                {
                    showAllDelivers(childGroupHolder.tvDeliverExpress,childGroupHolder.tvDeliverTake,childGroupHolder.tvDeliverPost);
                }
            }
            else
            {
                showOneDeliver(childGroupHolder.tvDeliverExpress,childGroupHolder.tvDeliverTake,childGroupHolder.tvDeliverPost);
                if (StringUtils.equals("0", deliverTypes))
                {
                    childGroupHolder.tvDeliverExpress.setText("邮局挂号");
                }
                else if (StringUtils.equals("1", deliverTypes))
                {
                    childGroupHolder.tvDeliverExpress.setText("快递");
                }
                else if (StringUtils.equals("2", deliverTypes))
                {
                    childGroupHolder.tvDeliverExpress.setText("自提");
                }
            }

            childGroupHolder.tvDeliverExpress.setTag(groupPosition);
            childGroupHolder.tvDeliverTake.setTag(groupPosition);
            childGroupHolder.tvDeliverPost.setTag(groupPosition);

            childGroupHolder.tvDeliverExpress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (childGroupHolder.tvDeliverExpress.getTag().equals(groupPosition)) {
                        clickDeliverExpress(childGroupHolder.tvDeliverExpress, childGroupHolder.tvDeliverTake, childGroupHolder.tvDeliverPost);
                        if (StringUtils.equals("快递", childGroupHolder.tvDeliverExpress.getText().toString())) {
                            group.setCheckDeliverType("1");
                            sendDeliverTypeInterface.sendDeliverType(group);
                        } else if (StringUtils.equals("自提", childGroupHolder.tvDeliverExpress.getText().toString())) {
                            group.setCheckDeliverType("2");
                            sendDeliverTypeInterface.sendDeliverType(group);
                        } else if (StringUtils.equals("邮局挂号", childGroupHolder.tvDeliverExpress.getText().toString())) {
                            group.setCheckDeliverType("0");
                            sendDeliverTypeInterface.sendDeliverType(group);
                        }
                    }
                }
            });

            childGroupHolder.tvDeliverTake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (childGroupHolder.tvDeliverTake.getTag().equals(groupPosition)) {
                        clickDeliverTake(childGroupHolder.tvDeliverTake,childGroupHolder.tvDeliverExpress,childGroupHolder.tvDeliverPost);
                        if (StringUtils.equals("自提", childGroupHolder.tvDeliverTake.getText().toString())) {
                            group.setCheckDeliverType("2");
                            sendDeliverTypeInterface.sendDeliverType(group);
                        } else if (StringUtils.equals("邮局挂号", childGroupHolder.tvDeliverTake.getText().toString())) {
                            group.setCheckDeliverType("0");
                            sendDeliverTypeInterface.sendDeliverType(group);
                        }
                    }
                }
            });

            childGroupHolder.tvDeliverPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (childGroupHolder.tvDeliverPost.getTag().equals(groupPosition)) {
                        clickDeliverPost(childGroupHolder.tvDeliverPost,childGroupHolder.tvDeliverExpress,childGroupHolder.tvDeliverTake);
                        group.setCheckDeliverType("0");
                        sendDeliverTypeInterface.sendDeliverType(group);
                    }
                }
            });
        }

        //显示单品
        else if (obj instanceof Ord00202Resp && holder instanceof ChildHolder)
        {
            final Ord00202Resp product = (Ord00202Resp) obj;
            final ChildHolder childHolder = (ChildHolder) holder;
            if (product.getPicUrl()!=null) {
                GlideUtil.loadImg(mContext, product.getPicUrl(), childHolder.ivImage);
            } else {
                childHolder.ivImage.setImageResource(R.drawable.default_img);
            }
            childHolder.tvProductName.setText(product.getGdsName());
            if (!product.isIfFulfillProm())
            {
                childHolder.tvPromInfo.setText(product.getNoFulfilMsg());
                childHolder.tvProductPrice.setText("价格：" + MoneyUtils.GoodListPrice(product.getBuyPrice()));
            }
            else
            {
                childHolder.tvPromInfo.setText(product.getFulfilMsg());
                childHolder.tvProductPrice.setText("价格：" + MoneyUtils.GoodListPrice(product.getDiscountPrice()));
            }
            childHolder.tvProductNum.setText("数量：x " + product.getOrderAmount());
            if (isLastChild)
            {
                childHolder.llCoupon.setVisibility(View.VISIBLE);
                childHolder.llCash.setVisibility(View.VISIBLE);
                childHolder.llBill.setVisibility(View.VISIBLE);
                childHolder.llDeliver.setVisibility(View.VISIBLE);
                if (StringUtils.isNotEmpty(ifcoupCodeOpen)) {
                    if (StringUtils.equals("1", ifcoupCodeOpen)) {
                        childHolder.llPromotionCodeLayout.setVisibility(View.VISIBLE);
                    } else {
                        childHolder.llPromotionCodeLayout.setVisibility(View.GONE);
                    }
                }
            }
            else
            {
                childHolder.llCoupon.setVisibility(View.GONE);
                childHolder.llCash.setVisibility(View.GONE);
                childHolder.llBill.setVisibility(View.GONE);
                childHolder.llDeliver.setVisibility(View.GONE);
                childHolder.llPromotionCodeLayout.setVisibility(View.GONE);
            }
            childHolder.llGoodLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redirectToShopDetailInterface.redirectToShopDetail(product);
                }
            });

            childHolder.tvUnBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickUnBill(childHolder.tvUnBill, childHolder.tvBill);
                    billType = "2";
                    childHolder.tvBillOne.setText("");
                    childHolder.tvBillTwo.setText("");
                    childHolder.tvBillThere.setText("");
                    sendBillInterface.sendBill(billType, group.getShopId());
                }
            });
            childHolder.tvBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickBill(childHolder.tvBill, childHolder.tvUnBill);
                    billType = "0";
                    startBillInterface.startBill(childHolder.tvBillOne.getText().toString(),
                            childHolder.tvBillTwo.getText().toString(), childHolder.tvBillThere.getText().toString(), group);
                    sendBillInterface.sendBill(billType, group.getShopId());
                }
            });
            //不开发票
            if (StringUtils.equals("2", billType))
            {
                clickUnBill(childHolder.tvUnBill, childHolder.tvBill);
                childHolder.tvBillOne.setText("");
                childHolder.tvBillTwo.setText("");
                childHolder.tvBillThere.setText("");
            }
            else
            {
                clickBill(childHolder.tvBill, childHolder.tvUnBill);
                if (group.getBillTypeText()!=null)
                {
                    childHolder.tvBillOne.setText(group.getBillTypeText());
                }
                else
                {
                    childHolder.tvBillOne.setText(null);
                }

                if (group.getBillTitle()!=null)
                {
                    childHolder.tvBillTwo.setText(group.getBillTitle());
                }
                else
                {
                    childHolder.tvBillTwo.setText(null);
                }

                if (group.getBillContent()!=null)
                {
                    childHolder.tvBillThere.setText(group.getBillContent());
                }
                else
                {
                    childHolder.tvBillThere.setText(null);
                }
            }

            if (StringUtils.isEmpty(childHolder.tvBillOne.getText().toString())
                    && StringUtils.isEmpty(childHolder.tvBillTwo.getText().toString())
                    && StringUtils.isEmpty(childHolder.tvBillThere.getText().toString()))
            {
                clickUnBill(childHolder.tvUnBill, childHolder.tvBill);
            }

            //显示资金账户、合约账户
            for (final Ord00801Resp bean : ord00801Resps) {
                Long coupNum = 0L;
                if (bean.getShopId().longValue() == group.getShopId().longValue()) {
                    if (bean.getCoupOrdSkuList() == null) {
                        childHolder.llCoupon.setVisibility(View.GONE);
                    } else {
                        for (CoupCheckBeanRespDTO coup : bean.getCoupOrdSkuList()) {
                            coupNum += coup.getCoupSize();
                        }
                        childHolder.tvCoupSize.setText(coupNum+"张可用");
                        if (group.getCoupIdList() != null && group.getCoupValueList() != null) {
                           Long tempValue=0L;
                           for (Long value : group.getCoupValueList()) {
                               tempValue += value;
                           }
                           childHolder.tvCoupValue.setText(MoneyUtils.GoodListPrice(tempValue));
                        } else {
                           childHolder.tvCoupValue.setText(null);

                        }
                        childHolder.llCoupon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startCouponInterface.startCoupon(group,bean.getCoupOrdSkuList(),checkCoupIds);
                            }
                        });
                    }
                    if (bean.getAcctInfoList() == null || bean.getAcctInfoList().size() == 0) {
                        childHolder.llCash.setVisibility(View.GONE);
                    } else {
                        childHolder.etShopCash.setFilters(new InputFilter[]{new PointLengthFilter()});
                        childHolder.etContractCash.setFilters(new InputFilter[]{new PointLengthFilter()});

                        if (bean.getAcctInfoList().size() >1) {
                            final AcctInfoResDTO acctInfo = bean.getAcctInfoList().get(0);
                            childHolder.tvShopCash.setText(acctInfo.getAcctName()+"账户，最大可使用"
                                    + StringUtils.remove(MoneyUtils.GoodListPrice(acctInfo.getDeductOrderMoney()),"￥") +"元，");
                            final AcctInfoResDTO acctInfoRes = bean.getAcctInfoList().get(1);
                            childHolder.tvContractCash.setText(acctInfoRes.getAcctName()+"账户，最大可使用"
                                    + StringUtils.remove(MoneyUtils.GoodListPrice(acctInfoRes.getDeductOrderMoney()), "￥") + "元，");

                            childHolder.tvShopCashUse.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    useCashInterface.useCash(bean.getShopId(), acctInfo, childHolder.etShopCash,
                                            childHolder.tvShopCashInput, childHolder.tvShopCashUse, groupPosition);
                                }
                            });

                            childHolder.tvContractCashUse.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    useContractInterface.useContract(bean.getShopId(),acctInfoRes, childHolder.etContractCash,
                                            childHolder.tvContractInput,childHolder.tvContractCashUse);
                                }
                            });

                            childHolder.llCashLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (childHolder.llShopCashLayout.getVisibility()==View.VISIBLE) {
                                        childHolder.ivCashArrow.setImageResource(R.drawable.arrow_right);
                                        childHolder.llShopCashLayout.setVisibility(View.GONE);
                                        childHolder.llContactLayout.setVisibility(View.GONE);
                                    } else {
                                        childHolder.ivCashArrow.setImageResource(R.drawable.arrow_down);
                                        childHolder.llShopCashLayout.setVisibility(View.VISIBLE);
                                        childHolder.llContactLayout.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        } else {
                            final AcctInfoResDTO acctInfo = bean.getAcctInfoList().get(0);
                            childHolder.tvShopCash.setText(acctInfo.getAcctName()+"账户，最大可使用"
                                    + StringUtils.remove(MoneyUtils.GoodListPrice(acctInfo.getDeductOrderMoney()),"￥") +"元，");

                            childHolder.tvShopCashUse.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    useCashInterface.useCash(bean.getShopId(), acctInfo, childHolder.etShopCash,
                                            childHolder.tvShopCashInput, childHolder.tvShopCashUse, groupPosition);
                                }
                            });
                            childHolder.llContactLayout.setVisibility(View.GONE);
                            childHolder.llCashLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (childHolder.llShopCashLayout.getVisibility()==View.VISIBLE) {
                                        childHolder.ivCashArrow.setImageResource(R.drawable.arrow_right);
                                        childHolder.llShopCashLayout.setVisibility(View.GONE);
                                    } else {
                                        childHolder.ivCashArrow.setImageResource(R.drawable.arrow_down);
                                        childHolder.llShopCashLayout.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                    }
                }
            }

            childHolder.tvPromotionCodeUse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Long coupValue = 0L;
                    List<Long> coupValueList = group.getCoupValueList();
                    if (coupValueList != null && coupValueList.size() != 0) {
                        for (int i = 0; i < coupValueList.size(); i++) {
                            coupValue += coupValueList.get(i);
                        }
                    }
                    if (coupValue != 0 || StringUtils.equals("1", group.getNoExpress())) {
                        ToastUtil.showCenter(mContext, "优惠码与优惠券不能同时使用");
                    } else {
                        if (StringUtils.isNotEmpty(childHolder.etPromotionCode.getText().toString())) {
                            mPromotionCodeInterface.usePromotionCode(group, childHolder.tvPromotionCodeText,
                                    childHolder.etPromotionCode, childHolder.tvPromotionCodeInput,
                                    childHolder.tvPromotionCodeUse);
                        } else {
                            ToastUtil.showCenter(mContext, "请输入优惠码");
                        }
                    }
                }
            });

            //根据配置显示配送方式:0为邮局,1为快递,2为自提
            if (StringUtils.equals(takeSelf, "自提")) {
                clickDeliverTake(childHolder.tvDeliverTake, childHolder.tvDeliverExpress, childHolder.tvDeliverPost);
                group.setCheckDeliverType("2");
                sendDeliverTypeInterface.sendDeliverType(group);
            }
            deliverTypes = group.getDeliverTypes();
            if (!StringUtils.contains(deliverTypes,"1")
                    || (StringUtils.isNotEmpty(group.getNoExpress()) && StringUtils.equals("1", group.getNoExpress()))) {
                group.setExpressMoney(0L);
            } else {
                if (group.getCheckDeliverType()==null) {
                    group.setCheckDeliverType("1");
                }
            }
            if (StringUtils.contains(deliverTypes,","))
            {
                String[] types = StringUtils.split(deliverTypes,",");
                if (types.length == 2)
                {
                    showTwoDelivers(childHolder.tvDeliverExpress,childHolder.tvDeliverTake,childHolder.tvDeliverPost);
                    if (StringUtils.equals("0", types[0]))
                    {
                        childHolder.tvDeliverExpress.setText("邮局挂号");
                    }
                    else if (StringUtils.equals("1", types[0]))
                    {
                        childHolder.tvDeliverExpress.setText("快递");
                    }
                    else if (StringUtils.equals("2", types[0]))
                    {
                        childHolder.tvDeliverExpress.setText("自提");
                    }

                    if (StringUtils.equals("0", types[1]))
                    {
                        childHolder.tvDeliverTake.setText("邮局挂号");
                    }
                    else if (StringUtils.equals("2", types[1]))
                    {
                        childHolder.tvDeliverTake.setText("自提");
                    }
                }
                if (types.length == 3)
                {
                    showAllDelivers(childHolder.tvDeliverExpress,childHolder.tvDeliverTake,childHolder.tvDeliverPost);
                }
            }
            else
            {
                showOneDeliver(childHolder.tvDeliverExpress,childHolder.tvDeliverTake,childHolder.tvDeliverPost);
                if (StringUtils.equals("0", deliverTypes))
                {
                    childHolder.tvDeliverExpress.setText("邮局挂号");
                }
                else if (StringUtils.equals("1", deliverTypes))
                {
                    childHolder.tvDeliverExpress.setText("快递");
                }
                else if (StringUtils.equals("2", deliverTypes))
                {
                    childHolder.tvDeliverExpress.setText("自提");
                }
            }

            childHolder.tvDeliverExpress.setTag(groupPosition);
            childHolder.tvDeliverTake.setTag(groupPosition);
            childHolder.tvDeliverPost.setTag(groupPosition);

            childHolder.tvDeliverExpress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (childHolder.tvDeliverExpress.getTag().equals(groupPosition)) {
                        clickDeliverExpress(childHolder.tvDeliverExpress, childHolder.tvDeliverTake, childHolder.tvDeliverPost);
                        if (StringUtils.equals("快递", childHolder.tvDeliverExpress.getText().toString())) {
                            group.setCheckDeliverType("1");
                            sendDeliverTypeInterface.sendDeliverType(group);
                        } else if (StringUtils.equals("自提", childHolder.tvDeliverExpress.getText().toString())) {
                            group.setCheckDeliverType("2");
                            sendDeliverTypeInterface.sendDeliverType(group);
                        } else if (StringUtils.equals("邮局挂号", childHolder.tvDeliverExpress.getText().toString())) {
                            group.setCheckDeliverType("0");
                            sendDeliverTypeInterface.sendDeliverType(group);
                        }
                    }
                }
            });

            childHolder.tvDeliverTake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (childHolder.tvDeliverTake.getTag().equals(groupPosition)) {
                        clickDeliverTake(childHolder.tvDeliverTake,childHolder.tvDeliverExpress,childHolder.tvDeliverPost);
                        if (StringUtils.equals("自提", childHolder.tvDeliverTake.getText().toString())) {
                            group.setCheckDeliverType("2");
                            sendDeliverTypeInterface.sendDeliverType(group);
                        } else if (StringUtils.equals("邮局挂号", childHolder.tvDeliverTake.getText().toString())) {
                            group.setCheckDeliverType("0");
                            sendDeliverTypeInterface.sendDeliverType(group);
                        }
                    }
                }
            });

            childHolder.tvDeliverPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (childHolder.tvDeliverPost.getTag().equals(groupPosition)) {
                        clickDeliverPost(childHolder.tvDeliverPost, childHolder.tvDeliverTake, childHolder.tvDeliverExpress);
                        group.setCheckDeliverType("0");
                        sendDeliverTypeInterface.sendDeliverType(group);
                    }
                }
            });
        }
        else
        {
            LogUtil.e("getChildView为空");
        }
        return mView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * 显示所有配送方式
     * @param tvDeliverExpress
     * @param tvDeliverTake
     * @param tvDeliverPost
     */
    private void showAllDelivers(TextView tvDeliverExpress, TextView tvDeliverTake, TextView tvDeliverPost) {
        tvDeliverExpress.setVisibility(View.VISIBLE);
        tvDeliverTake.setVisibility(View.VISIBLE);
        tvDeliverPost.setVisibility(View.VISIBLE);
    }

    /**
     * 显示任意2种配送方式
     * @param tvDeliverExpress
     * @param tvDeliverTake
     * @param tvDeliverPost
     */
    private void showTwoDelivers(TextView tvDeliverExpress, TextView tvDeliverTake, TextView tvDeliverPost) {
        tvDeliverExpress.setVisibility(View.VISIBLE);
        tvDeliverTake.setVisibility(View.VISIBLE);
        tvDeliverPost.setVisibility(View.INVISIBLE);
    }

    /**
     * 显示1种配送方式
     * @param tvDeliverExpress
     * @param tvDeliverTake
     * @param tvDeliverPost
     */
    private void showOneDeliver(TextView tvDeliverExpress, TextView tvDeliverTake, TextView tvDeliverPost) {
        tvDeliverExpress.setVisibility(View.VISIBLE);
        tvDeliverTake.setVisibility(View.INVISIBLE);
        tvDeliverPost.setVisibility(View.INVISIBLE);
    }

    /**
     * 选择快递
     * @param tvDeliverExpress
     * @param tvDeliverTake
     * @param tvDeliverPost
     */
    private void clickDeliverExpress(TextView tvDeliverExpress, TextView tvDeliverTake, TextView tvDeliverPost){
        tvDeliverExpress.setBackgroundResource(R.drawable.shape_round_delbutton);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvDeliverExpress.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
        } else {
            tvDeliverExpress.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        tvDeliverTake.setBackgroundResource(R.drawable.shape_round_textview);
        tvDeliverTake.setTextColor(ContextCompat.getColor(mContext, R.color.gray_312B2D));
        tvDeliverPost.setBackgroundResource(R.drawable.shape_round_textview);
        tvDeliverPost.setTextColor(ContextCompat.getColor(mContext, R.color.gray_312B2D));
    }

    /**
     * 选择自提
     * @param tvDeliverTake
     * @param tvDeliverExpress
     * @param tvDeliverPost
     */
    private void clickDeliverTake(TextView tvDeliverTake, TextView tvDeliverExpress, TextView tvDeliverPost){
        tvDeliverTake.setBackgroundResource(R.drawable.shape_round_delbutton);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvDeliverTake.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
        } else {
            tvDeliverTake.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        tvDeliverExpress.setBackgroundResource(R.drawable.shape_round_textview);
        tvDeliverExpress.setTextColor(ContextCompat.getColor(mContext, R.color.gray_312B2D));
        tvDeliverPost.setBackgroundResource(R.drawable.shape_round_textview);
        tvDeliverPost.setTextColor(ContextCompat.getColor(mContext, R.color.gray_312B2D));
    }

    /**
     * 选择邮局
     * @param tvDeliverPost
     * @param tvDeliverExpress
     * @param tvDeliverTake
     */
    private void clickDeliverPost(TextView tvDeliverPost, TextView tvDeliverExpress, TextView tvDeliverTake){
        tvDeliverPost.setBackgroundResource(R.drawable.shape_round_delbutton);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvDeliverPost.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
        } else {
            tvDeliverPost.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        tvDeliverExpress.setBackgroundResource(R.drawable.shape_round_textview);
        tvDeliverExpress.setTextColor(ContextCompat.getColor(mContext, R.color.gray_312B2D));
        tvDeliverTake.setBackgroundResource(R.drawable.shape_round_textview);
        tvDeliverTake.setTextColor(ContextCompat.getColor(mContext, R.color.gray_312B2D));
    }

    /**
     * 选择不开票
     * @param tvUnBill
     * @param tvBill
     */
    private void clickUnBill(TextView tvUnBill, TextView tvBill){
        tvUnBill.setBackgroundResource(R.drawable.shape_round_delbutton);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvUnBill.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
        } else {
            tvUnBill.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        tvBill.setBackgroundResource(R.drawable.shape_round_textview);
        tvBill.setTextColor(ContextCompat.getColor(mContext, R.color.gray_312B2D));
    }

    /**
     * 选择开票
     * @param tvBill
     * @param tvUnBill
     */
    private void clickBill(TextView tvBill, TextView tvUnBill){
        tvBill.setBackgroundResource(R.drawable.shape_round_delbutton);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvBill.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
        } else {
            tvBill.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        tvUnBill.setBackgroundResource(R.drawable.shape_round_textview);
        tvUnBill.setTextColor(ContextCompat.getColor(mContext, R.color.gray_312B2D));
    }

    class GroupHolder {
        @BindView(R.id.store_layout) LinearLayout storeLayout;
        @BindView(R.id.tv_shop_name) TextView tvShopName;
        @BindView(R.id.tv_gift_info) TextView tvGiftInfo;

        public GroupHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildHolder {
        @BindView(R.id.ll_good_layout)            LinearLayout llGoodLayout;
        @BindView(R.id.iv_image)                  ImageView ivImage;
        @BindView(R.id.tv_product_name)           TextView tvProductName;
        @BindView(R.id.tv_prom_info)              TextView tvPromInfo;
        @BindView(R.id.tv_product_price)          TextView tvProductPrice;
        @BindView(R.id.tv_product_num)            TextView tvProductNum;
        @BindView(R.id.ll_coupon)                 LinearLayout llCoupon;
        @BindView(R.id.ll_cash)                   LinearLayout llCash;
        @BindView(R.id.ll_contact_layout)         LinearLayout llContactLayout;
        @BindView(R.id.ll_bill)                   LinearLayout llBill;
        @BindView(R.id.ll_deliver)                LinearLayout llDeliver;
        @BindView(R.id.ll_cash_layout)            LinearLayout llCashLayout;
        @BindView(R.id.ll_shop_cash)              LinearLayout llShopCashLayout;
        @BindView(R.id.ll_promotion_code_layout)  LinearLayout llPromotionCodeLayout;
        @BindView(R.id.iv_cash_arrow)             ImageView ivCashArrow;
        @BindView(R.id.tv_coupSize)               TextView tvCoupSize;           //优惠券数量
        @BindView(R.id.tv_coup_value)             TextView tvCoupValue;
        @BindView(R.id.tv_shop_cash)              TextView tvShopCash;           //店铺现金返现说明
        @BindView(R.id.et_shop_cash1)             EditText etShopCash;           //店铺现金返现输入
        @BindView(R.id.tv_shop_cash_input1)       TextView tvShopCashInput;
        @BindView(R.id.tv_shop_contract_input1)   TextView tvContractInput;
        @BindView(R.id.tv_shop_cash_use1)         TextView tvShopCashUse;        //店铺现金返现使用
        @BindView(R.id.tv_contract_cash)          TextView tvContractCash;       //合约返利说明
        @BindView(R.id.et_contract_cash1)         EditText etContractCash;       //合约返利输入
        @BindView(R.id.tv_contract_cash_use1)     TextView tvContractCashUse;    //合约返利使用
        @BindView(R.id.tv_promotion_code_text)    TextView tvPromotionCodeText;
        @BindView(R.id.et_promotion_code)         MyEditText etPromotionCode;      //优惠码输入
        @BindView(R.id.tv_promotion_code_input)   TextView tvPromotionCodeInput; //优惠码优惠金额
        @BindView(R.id.tv_promotion_code_use)     TextView tvPromotionCodeUse;   //优惠码使用
        @BindView(R.id.tv_bill_one)               TextView tvBillOne;            //发票1
        @BindView(R.id.tv_bill_two)               TextView tvBillTwo;            //发票2
        @BindView(R.id.tv_bill_three)             TextView tvBillThere;          //发票3
        @BindView(R.id.tv_unbill)                 TextView tvUnBill;             //不开票
        @BindView(R.id.tv_bill)                   TextView tvBill;               //开票
        @BindView(R.id.tv_deliver_post)           TextView tvDeliverPost;        //邮局挂号
        @BindView(R.id.tv_deliver_express)        TextView tvDeliverExpress;     //快递
        @BindView(R.id.tv_deliver_take)           TextView tvDeliverTake;        //自提

        public ChildHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    class ChildGroupHolder {
        @BindView(R.id.lv_groups_list)            CustomListView lvGroupsList;
        @BindView(R.id.ll_coupon)                 LinearLayout llCoupon;
        @BindView(R.id.ll_cash)                   LinearLayout llCash;
        @BindView(R.id.ll_contact_layout)         LinearLayout llContactLayout;
        @BindView(R.id.ll_bill)                   LinearLayout llBill;
        @BindView(R.id.ll_deliver)                LinearLayout llDeliver;
        @BindView(R.id.ll_cash_layout)            LinearLayout llCashLayout;
        @BindView(R.id.ll_shop_cash)              LinearLayout llShopCashLayout;
        @BindView(R.id.ll_promotion_code_layout)  LinearLayout llPromotionCodeLayout;
        @BindView(R.id.iv_cash_arrow)             ImageView ivCashArrow;
        @BindView(R.id.tv_coupSize)               TextView tvCoupSize;           //优惠券数量
        @BindView(R.id.tv_coup_value)             TextView tvCoupValue;
        @BindView(R.id.tv_shop_cash)              TextView tvShopCash;           //店铺现金返现说明
        @BindView(R.id.et_shop_cash)              EditText etShopCash;           //店铺现金返现输入
        @BindView(R.id.tv_shop_cash_input)        TextView tvShopCashInput;
        @BindView(R.id.tv_shop_contract_input)    TextView tvContractInput;
        @BindView(R.id.tv_shop_cash_use)          TextView tvShopCashUse;        //店铺现金返现使用
        @BindView(R.id.tv_contract_cash)          TextView tvContractCash;       //合约返利说明
        @BindView(R.id.et_contract_cash)          EditText etContractCash;       //合约返利输入
        @BindView(R.id.tv_contract_cash_use)      TextView tvContractCashUse;    //合约返利使用
        @BindView(R.id.tv_promotion_code_text)    TextView tvPromotionCodeText;
        @BindView(R.id.et_promotion_code)         MyEditText etPromotionCode;      //优惠码输入
        @BindView(R.id.tv_promotion_code_input)   TextView tvPromotionCodeInput; //优惠码优惠金额
        @BindView(R.id.tv_promotion_code_use)     TextView tvPromotionCodeUse;   //优惠码使用
        @BindView(R.id.tv_bill_one)               TextView tvBillOne;            //发票1
        @BindView(R.id.tv_bill_two)               TextView tvBillTwo;            //发票2
        @BindView(R.id.tv_bill_three)             TextView tvBillThere;          //发票3
        @BindView(R.id.tv_unbill)                 TextView tvUnBill;             //不开票
        @BindView(R.id.tv_bill)                   TextView tvBill;               //开票
        @BindView(R.id.tv_deliver_post)           TextView tvDeliverPost;        //邮局挂号
        @BindView(R.id.tv_deliver_express)        TextView tvDeliverExpress;     //快递
        @BindView(R.id.tv_deliver_take)           TextView tvDeliverTake;        //自提

        public ChildGroupHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    public interface RedirectToShopDetailInterface {
        void redirectToShopDetail(Ord00202Resp product);
    }

    public interface StartBillInterface {
        void startBill(String text1, String text2, String text3, Ord00201Resp shop);
    }

    public interface StartCouponInterface {
        void startCoupon(Ord00201Resp group, List<CoupCheckBeanRespDTO> coupOrdSkuList, List<Long> checkCoupIds);
    }

    public interface UseCashInterface {
        void useCash(Long shopId, AcctInfoResDTO acctInfo, EditText inputView, TextView showView, TextView clickView, int position);
    }

    public interface UseContractInterface {
        void useContract(Long shopId, AcctInfoResDTO acctInfo, EditText inputView, TextView showView, TextView clickView);
    }

    public interface PromotionCodeInterface {
        void usePromotionCode(Ord00201Resp shop, TextView textView, EditText inputView, TextView showView, TextView clickView);
    }

    public interface SendDeliverTypeInterface {
        void sendDeliverType(Ord00201Resp shop);
    }

    public interface SendBillInterface {
        void sendBill(String invoiceType, Long shopId);
    }

}
