package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord00201Resp;
import com.ai.ecp.app.resp.Ord00202Resp;
import com.ai.ecp.app.resp.Ord00204Resp;
import com.ai.ecp.app.resp.Ord00205Resp;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.thirdstore.activity.StoreActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.ui.view.CustomListView;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/3/21 00:40
 */
public class ShopCartExpandableListAdapter extends BaseExpandableListAdapter {

    private List<Ord00201Resp> groups = new ArrayList<>();
    private Map<Long, List<Ord00202Resp>> children = new HashMap<>();
    private Ord00204Resp goods = new Ord00204Resp();
    private Context mContext;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    private DeleteInterface deleteInterface;
    private ShowShopCouponDialogInterface showShopCouponDialogInterface;
    private ShowProductCouponDialogInterface showProductCouponDialogInterface;
    private RedirectToShopDetailInterface redirectToShopDetailInterface;
    private String textEdit = "";

    public ShopCartExpandableListAdapter(List<Ord00201Resp> groups, Map<Long, List<Ord00202Resp>> children,
                                         Context context, String text) {
        this.groups = groups;
        this.children = children;
        this.mContext = context;
        this.textEdit = text;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    public void setDeleteInterface(DeleteInterface deleteInterface) {
        this.deleteInterface = deleteInterface;
    }

    public void setShowShopCouponDialogInterface(ShowShopCouponDialogInterface showShopCouponDialogInterface) {
        this.showShopCouponDialogInterface = showShopCouponDialogInterface;
    }

    public void setShowProductCouponDialogInterface(ShowProductCouponDialogInterface showProductCouponDialogInterface) {
        this.showProductCouponDialogInterface = showProductCouponDialogInterface;
    }

    public void setRedirectToShopDetailInterface(RedirectToShopDetailInterface redirectToShopDetailInterface) {
        this.redirectToShopDetailInterface = redirectToShopDetailInterface;
    }

    public void setTextEdit(String _textEdit) {
        this.textEdit = _textEdit;
        notifyDataSetChanged();
    }

    public void setGroups(List<Ord00201Resp> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    public void setChildren(Map<Long, List<Ord00202Resp>> children) {
        this.children = children;
        notifyDataSetChanged();
    }

    public void setGoods(Ord00204Resp goods) {
        this.goods = goods;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Ord00201Resp group = groups.get(groupPosition);
        List<Ord00202Resp> childs = children.get(group.getShopId());
        goods = group.getGroupLists();
        List<Ord00205Resp> groupLists = goods.getGroupLists();
        List<Object> productList = new ArrayList<>();
        productList.addAll(groupLists);
        productList.addAll(childs);
        return productList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Ord00201Resp group = (Ord00201Resp) getGroup(groupPosition);
        goods = group.getGroupLists();
        List<Ord00205Resp> groupLists = goods.getGroupLists();
        List<Ord00202Resp> childs = children.get(group.getShopId());
        List<Object> productList = new ArrayList<>();
        productList.addAll(groupLists);
        productList.addAll(childs);
        return productList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupHolder gholder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shopcart_list, parent, false);
            gholder = new GroupHolder(convertView);
            convertView.setTag(gholder);
        }
        else
        {
            gholder = (GroupHolder) convertView.getTag();
        }
        final Ord00201Resp group = (Ord00201Resp) getGroup(groupPosition);
        if (group != null)
        {
            gholder.tvStoreName.setText(group.getShopName());
            if (group.getPromId()!=null && group.getPromId()==-1)
            {
                gholder.tvNameShort.setVisibility(View.INVISIBLE);
                gholder.tvPromTheme.setText("不使用活动优惠");
            }
            if (StringUtils.isEmpty(group.getFulfilMsg()) && StringUtils.isEmpty(group.getNoFulfilMsg()))
            {
                gholder.viewLine.setVisibility(View.GONE);
                gholder.promLayout.setVisibility(View.GONE);
            }
            else
            {
                if (group.isIfFulfillProm())
                {
                    if (group.getPromInfoDTOList()!=null && group.getPromInfoDTOList().size()>0)
                    {
                        for (int i = 0; i < group.getPromInfoDTOList().size(); i++) {
                            if (group.getPromId() != null)
                            {
                                if (group.getPromId().equals(group.getPromInfoDTOList().get(i).getId()))
                                {
                                    gholder.tvNameShort.setText(group.getPromInfoDTOList().get(i).getNameShort());
                                }
                            }
                        }
                    }
                    gholder.tvPromTheme.setText(group.getFulfilMsg());
                }
                else
                {
                    if (group.getPromInfoDTOList()!=null && group.getPromInfoDTOList().size()>0){
                        for (int i = 0; i < group.getPromInfoDTOList().size(); i++) {
                            if (group.getPromId() != null)
                            {
                                if (group.getPromId().equals(group.getPromInfoDTOList().get(i).getId()))
                                {
                                    gholder.tvNameShort.setText(group.getPromInfoDTOList().get(i).getNameShort());
                                }
                            }
                        }
                    }
                    gholder.tvPromTheme.setText(group.getNoFulfilMsg());
                }
            }
            if (group.getPromInfoDTOList()==null || (group.getPromInfoDTOList()!=null && group.getPromInfoDTOList().size()==0))
            {
                gholder.viewLine.setVisibility(View.GONE);
                gholder.promLayout.setVisibility(View.GONE);
            }
            if (group.getPromInfoDTOList()!=null && group.getPromInfoDTOList().size()>0)
            {
                gholder.viewLine.setVisibility(View.VISIBLE);
                gholder.promLayout.setVisibility(View.VISIBLE);
                if (StringUtils.equals(textEdit, "完成")) {
                    gholder.llProm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showShopCouponDialogInterface.showShopCouponDialog(groupPosition);
                        }
                    });
                } else {
                    gholder.llProm.setClickable(false);
                }
            }

            gholder.cbStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    group.setIsChoosed(((CheckBox) v).isChecked());
                    checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());
                }
            });
            gholder.cbStore.setChecked(group.isChoosed());

            if (!StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                gholder.rlStore.setOnClickListener(new View.OnClickListener() {
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
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        final Ord00201Resp group = (Ord00201Resp) getGroup(groupPosition);
        final List<Ord00205Resp> groupLists = group.getGroupLists().getGroupLists();
        final List<Ord00202Resp> childs = children.get(group.getShopId());
        String temp = textEdit;
        View mView = convertView;
        Object obj = getChild(groupPosition, childPosition);
        Object holder = null;
        final Ord00205Resp ord00205Resp;
        final ChildGroupHolder childGroupHolder;
        final Ord00202Resp product;
        final ChildHolder cholder;
        if (mView == null)
        {
            if (obj instanceof Ord00205Resp)
            {
                mView = LayoutInflater.from(mContext).inflate(R.layout.item_shopcart_grouplist, parent, false);
                holder = new ChildGroupHolder(mView);
            }
            else if (obj instanceof Ord00202Resp)
            {
                mView = LayoutInflater.from(mContext).inflate(R.layout.item_item_shopcartlist, parent, false);
                holder = new ChildHolder(mView);
            }
        }
        else
        {
            holder = mView.getTag();
            if (obj instanceof Ord00205Resp && !(holder instanceof ChildGroupHolder))
            {
                mView = LayoutInflater.from(mContext).inflate(R.layout.item_shopcart_grouplist, parent, false);
                holder = new ChildGroupHolder(mView);
            }
            else if (obj instanceof Ord00202Resp && !(holder instanceof ChildHolder))
            {
                mView = LayoutInflater.from(mContext).inflate(R.layout.item_item_shopcartlist, parent, false);
                holder = new ChildHolder(mView);
            }
        }

        if (obj instanceof Ord00205Resp && holder instanceof ChildGroupHolder)
        {
            ord00205Resp = (Ord00205Resp) obj;
            childGroupHolder = (ChildGroupHolder) holder;
            Long buyPrice = 0L;
            for (int i = 0; i < ord00205Resp.getGroupSkus().size(); i++) {
                Ord00202Resp sku = ord00205Resp.getGroupSkus().get(i);
                if (!sku.isGdsStatus()) {
                    childGroupHolder.tvGroupsPrice.setText("0.00");
                    childGroupHolder.rlGroups.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray_ebebeb));
                    childGroupHolder.cbGroups.setText("失效");
                    childGroupHolder.cbGroups.setBackgroundResource(0);
                    childGroupHolder.cbGroups.setTextColor(ContextCompat.getColor(mContext, R.color.gray_969696));
                    childGroupHolder.cbGroups.setChecked(false);
                    childGroupHolder.tvGroupsName.setTextColor(ContextCompat.getColor(mContext, R.color.gray_969696));
                    childGroupHolder.flGroupsNum.setVisibility(View.GONE);
                    childGroupHolder.tvGroupsPrice.setTextColor(ContextCompat.getColor(mContext, R.color.gray_969696));
                    if (StringUtils.equals("完成", temp)) {
                        childGroupHolder.tvDeleteGroups.setVisibility(View.VISIBLE);
                    } else {
                        childGroupHolder.tvDeleteGroups.setVisibility(View.GONE);
                    }
                    GroupListAdapter adapter = new GroupListAdapter(mContext,ord00205Resp.getGroupSkus());
                    childGroupHolder.lvGroups.setAdapter(adapter);
                }
                else {
                    childGroupHolder.tvGroupsNum.setText(String.valueOf(sku.getOrderAmount()));
                    if (sku.isIfFulfillProm()) {
                        buyPrice += (sku.getDiscountCaclPrice()*sku.getOrderAmount()-sku.getDiscountMoney());
                    } else {
                        buyPrice += sku.getBuyPrice()*sku.getOrderAmount();
                    }
                    String buyPriceStr = MoneyUtils.GoodListPrice(buyPrice);
                    MoneyUtils.showSpannedPrice(childGroupHolder.tvGroupsPrice, buyPriceStr);
                    GroupListAdapter adapter = new GroupListAdapter(mContext,ord00205Resp.getGroupSkus());
                    childGroupHolder.lvGroups.setAdapter(adapter);
                }
            }

            childGroupHolder.cbGroups.setChecked(goods.isChoosed());
            childGroupHolder.cbGroups.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goods.setIsChoosed(((CheckBox) v).isChecked());
                    childGroupHolder.cbGroups.setChecked(((CheckBox) v).isChecked());
                    checkInterface.checkGroups(groupPosition, childPosition, childGroupHolder.cbGroups.isChecked());
                }
            });

            childGroupHolder.tvAddGroups.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doAddGroups(groupPosition, childPosition, childGroupHolder.tvGroupsNum);
                }
            });

            childGroupHolder.tvReduceGroups.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doReduceGrouops(groupPosition, childPosition, childGroupHolder.tvGroupsNum);
                }
            });
            if (StringUtils.equals("完成", temp))
            {
                childGroupHolder.tvDeleteGroups.setVisibility(View.VISIBLE);
                childGroupHolder.tvDeleteGroups.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteInterface.doDeleteGroups(groupPosition, childPosition, childGroupHolder.cbGroups.isChecked());
                    }
                });
            }
            else
            {
                childGroupHolder.tvDeleteGroups.setVisibility(View.GONE);
            }
            if (isLastChild) //最后一项
            {
                childGroupHolder.tvTotalBuyPrice.setVisibility(View.VISIBLE);
                childGroupHolder.tvTotalMinusPrice.setVisibility(View.VISIBLE);
                Long totalPrice = 0L;
                Long totalFalsePrice = 0L;
                Long totalDiscount = 0L;
                Long price;
                Long _totalPrice = 0L;
                Long _totalDiscount = 0L;
                Long _price;
                if (groupLists.size()>0 && group.getOrdCartItemList().size()>0)
                {
                    for (int i = 0; i < groupLists.size(); i++) {
                        Ord00205Resp entity = groupLists.get(i);
                        boolean isFlag = false;
                        for (int j = 0; j < entity.getGroupSkus().size(); j++) {
                            Ord00202Resp sku = entity.getGroupSkus().get(j);
                            if (!sku.isGdsStatus()) {
                                isFlag =true;
                                break;
                            }
                        }
                        if (isFlag) {
                            for (int k = 0; k < entity.getGroupSkus().size(); k++) {
                                Ord00202Resp sku = entity.getGroupSkus().get(k);
                                sku.setGdsStatus(false);
                            }
                            totalFalsePrice = 0L;
                        } else {
                            if (entity.isChoosed()) {
                                for (int a = 0; a < entity.getGroupSkus().size(); a++) {
                                    Ord00202Resp bean = entity.getGroupSkus().get(a);
                                    if (bean.isGdsStatus())
                                    {
                                        if (bean.isIfFulfillProm()) {
                                            price = bean.getOrderAmount()*bean.getDiscountCaclPrice()-bean.getDiscountMoney();
                                            totalPrice += price;
                                            totalDiscount += bean.getDiscountMoney();
                                        } else {
                                            price = bean.getOrderAmount()*bean.getBuyPrice();
                                            totalPrice += price;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    for (int i = 0; i < childs.size(); i++) {
                        Ord00202Resp child = childs.get(i);
                        if (child.isGdsStatus())
                        {
                            if (child.isChoosed())
                            {
                                if (child.isIfFulfillProm())
                                {
                                    _price = child.getOrderAmount()*child.getDiscountCaclPrice();
                                    _totalPrice += _price;
                                    _totalDiscount += child.getDiscountMoney();
                                }
                                else
                                {
                                    _price = child.getOrderAmount()*child.getBuyPrice();
                                    _totalPrice += _price;
                                }
                                childGroupHolder.tvTotalBuyPrice.setText("小计:" + MoneyUtils.GoodListPrice(totalPrice+totalFalsePrice+_totalPrice));
                                if (group.isIfFulfillProm())
                                {
                                    childGroupHolder.tvTotalMinusPrice.setText("立减" + MoneyUtils.GoodListPrice(_totalDiscount+group.getDiscountMoney()));
                                }
                                else
                                {
                                    childGroupHolder.tvTotalMinusPrice.setText("立减" + MoneyUtils.GoodListPrice(_totalDiscount));
                                }
                            }
                        }
                    }
                }
                else if (groupLists.size()>0 && childs.size()==0)
                {
                    for (int i = 0; i < groupLists.size(); i++) {
                        Ord00205Resp entity = groupLists.get(i);
                        boolean isFlag = false;
                        for (int j = 0; j < entity.getGroupSkus().size(); j++) {
                            Ord00202Resp sku = entity.getGroupSkus().get(j);
                            if (!sku.isGdsStatus()) {
                                isFlag =true;
                                break;
                            }
                        }
                        if (isFlag) {
                            for (int k = 0; k < entity.getGroupSkus().size(); k++) {
                                Ord00202Resp sku = entity.getGroupSkus().get(k);
                                sku.setGdsStatus(false);
                            }
                            totalFalsePrice = 0L;
                        } else {
                            if (entity.isChoosed()) {

                                for (int m = 0; m < entity.getGroupSkus().size(); m++) {
                                    Ord00202Resp bean = entity.getGroupSkus().get(m);
                                    if (bean.isGdsStatus())
                                    {
                                        if (bean.isIfFulfillProm()) {
                                            price = bean.getOrderAmount()*bean.getDiscountCaclPrice()-bean.getDiscountMoney();
                                            totalPrice += price;
                                            totalDiscount += bean.getDiscountMoney();
                                        } else {
                                            price = bean.getOrderAmount()*bean.getBuyPrice();
                                            totalPrice += price;
                                        }

                                        childGroupHolder.tvTotalBuyPrice.setText("小计:" + MoneyUtils.GoodListPrice(totalPrice+totalFalsePrice));
                                        childGroupHolder.tvTotalMinusPrice.setText("");
                                    }
                                }
                            }
                        }
                    }
                }
                else if (childs.size() > 0 && groupLists.size() == 0)
                {
                    for (int i = 0; i < childs.size(); i++) {
                        Ord00202Resp child = childs.get(i);
                        if (child.isGdsStatus())
                        {
                            if (child.isChoosed())
                            {
                                if (child.isIfFulfillProm())
                                {
                                    _price = child.getOrderAmount()*child.getDiscountCaclPrice();
                                    _totalPrice += _price;
                                    _totalDiscount += child.getDiscountMoney();
                                }
                                else
                                {
                                    _price = child.getOrderAmount()*child.getBuyPrice();
                                    _totalPrice += _price;
                                }
                                childGroupHolder.tvTotalBuyPrice.setText("小计:" + MoneyUtils.GoodListPrice(_totalPrice));
                                if (group.isIfFulfillProm())
                                {
                                    childGroupHolder.tvTotalMinusPrice.setText("立减" + MoneyUtils.GoodListPrice(_totalDiscount+group.getDiscountMoney()));
                                }
                                else
                                {
                                    childGroupHolder.tvTotalMinusPrice.setText("立减" + MoneyUtils.GoodListPrice(_totalDiscount));
                                }
                            }
                        }
                    }
                }
            } else {
                childGroupHolder.tvTotalBuyPrice.setVisibility(View.GONE);
                childGroupHolder.tvTotalMinusPrice.setVisibility(View.GONE);
            }
        }
        else if (obj instanceof Ord00202Resp && holder instanceof ChildHolder)
        {
            product = (Ord00202Resp) obj;
            cholder = (ChildHolder) holder;
            if (!product.isGdsStatus()) //下架商品
            {
                product.setIsChoosed(false);
                cholder.cbItem.setChecked(product.isChoosed());
                cholder.cbItem.setText("失效");
                cholder.cbItem.setBackgroundResource(0);
                cholder.cbItem.setTextColor(ContextCompat.getColor(mContext, R.color.gray_969696));
                cholder.rlGood.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray_ebebeb));
                cholder.tvPromName.setVisibility(View.INVISIBLE);
                cholder.tvModifyProm.setVisibility(View.INVISIBLE);
                cholder.flNum.setVisibility(View.GONE);
                if (product.getPicUrl()!=null) {
                    GlideUtil.loadImg(mContext, product.getPicUrl(), cholder.ivItemImg);
                } else {
                    cholder.ivItemImg.setImageResource(R.drawable.default_img);
                }
                cholder.tvGoodName.setText(product.getGdsName());
                cholder.tvGoodName.setTextColor(ContextCompat.getColor(mContext, R.color.gray_969696));
                cholder.tvSkuName.setText(product.getGdsCateName());
                cholder.tvBuyPrice.setText("￥0.00");
                cholder.tvBuyPrice.setTextColor(ContextCompat.getColor(mContext, R.color.gray_969696));
                if (StringUtils.equals(temp, "完成")) {
                    cholder.tvDelItem.setVisibility(View.VISIBLE);
                    cholder.tvDelItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteInterface.doDeleteItem(product,groupPosition, childPosition, cholder.cbItem.isChecked());
                        }
                    });
                } else {
                    cholder.tvDelItem.setVisibility(View.GONE);
                }
            }
            else
            {
                cholder.tvGoodName.setText(product.getGdsName());
                if (!product.isIfFulfillProm())
                {
                    cholder.tvPromName.setText(product.getNoFulfilMsg());
                    String buyPrice = MoneyUtils.GoodListPrice(product.getBuyPrice());
                    MoneyUtils.showSpannedPrice(cholder.tvBuyPrice, buyPrice);
                    cholder.tvTotalMinusPrice.setVisibility(View.GONE);
                }
                else
                {
                    cholder.tvPromName.setText(product.getFulfilMsg());
                    String discountPrice = MoneyUtils.GoodListPrice(product.getDiscountPrice());
                    MoneyUtils.showSpannedPrice(cholder.tvBuyPrice, discountPrice);
                }
                cholder.tvSkuName.setText(product.getGdsCateName());
                if (product.getPicUrl()!=null) {
                    GlideUtil.loadImg(mContext, product.getPicUrl(), cholder.ivItemImg);
                } else {
                    cholder.ivItemImg.setImageResource(R.drawable.default_img);
                }
                if (StringUtils.equals(product.getGdsType(),"2")) //虚拟商品
                {
                    cholder.tvReduce.setVisibility(View.INVISIBLE);
                    cholder.tvAdd.setVisibility(View.INVISIBLE);
                    cholder.flNum.setBackgroundResource(0);
                    cholder.tvBuyNum.setText("1本");
                }
                else
                {
                    cholder.tvBuyNum.setText(String.valueOf(product.getOrderAmount()));
                }
                cholder.cbItem.setChecked(product.isChoosed());
                cholder.cbItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        product.setIsChoosed(((CheckBox) v).isChecked());
                        cholder.cbItem.setChecked(((CheckBox) v).isChecked());
                        checkInterface.checkChild(product, groupPosition, childPosition, ((CheckBox) v).isChecked());
                    }
                });
                cholder.tvAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        modifyCountInterface.doIncrease(groupPosition, childPosition, cholder.tvBuyNum, cholder.cbItem.isChecked());
                    }
                });
                cholder.tvReduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        modifyCountInterface.doDecrease(groupPosition, childPosition, cholder.tvBuyNum, cholder.cbItem.isChecked());
                    }
                });

                if (StringUtils.equals(temp, "完成"))
                {
                    cholder.tvDelItem.setVisibility(View.VISIBLE);
                    cholder.tvDelItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteInterface.doDeleteItem(product, groupPosition, childPosition, cholder.cbItem.isChecked());
                        }
                    });
                }
                else
                {
                    cholder.tvDelItem.setVisibility(View.GONE);
                }
                if (StringUtils.equals(temp, "完成")) {
                    if (product.getPromInfoDTOList()!=null && product.getPromInfoDTOList().size()>0)
                    {
                        cholder.tvModifyProm.setVisibility(View.VISIBLE);
                        cholder.tvModifyProm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showProductCouponDialogInterface.showProductCouponDialog(groupPosition, childPosition);
                            }
                        });
                    }
                    else
                    {
                        cholder.tvModifyProm.setVisibility(View.GONE);
                    }
                }
                else
                {
                    cholder.tvModifyProm.setVisibility(View.GONE);
                }
                cholder.ivItemImg.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        redirectToShopDetailInterface.redirectToShopDetail(product);
                    }
                });

            }
            if (isLastChild) //最后一项
            {
                cholder.tvTotalBuyPrice.setVisibility(View.VISIBLE);
                cholder.tvTotalMinusPrice.setVisibility(View.VISIBLE);
                Long totalPrice = 0L;
                Long totalDiscount = 0L;
                Long price;
                Long _totalPrice = 0L;
                Long _totalDiscount = 0L;
                Long _price;
                Long totalFalsePrice = 0L;
                if (groupLists.size()>0 && group.getOrdCartItemList().size()>0)
                {
                    for (int i = 0; i < groupLists.size(); i++) {
                        Ord00205Resp entity = groupLists.get(i);
                        boolean isFlag = false;
                        for (int j = 0; j < entity.getGroupSkus().size(); j++) {
                            Ord00202Resp sku = entity.getGroupSkus().get(j);
                            if (!sku.isGdsStatus()) {
                                isFlag =true;
                                break;
                            }
                        }
                        if (isFlag) {
                            for (int k = 0; k < entity.getGroupSkus().size(); k++) {
                                Ord00202Resp sku = entity.getGroupSkus().get(k);
                                sku.setGdsStatus(false);
                            }
                            totalFalsePrice = 0L;
                        } else {
                            if (entity.isChoosed()) {
                                for (int m = 0; m < entity.getGroupSkus().size(); m++) {
                                    Ord00202Resp bean = entity.getGroupSkus().get(m);
                                    if (bean.isGdsStatus())
                                    {
                                        if (bean.isIfFulfillProm())
                                        {
                                            price = bean.getOrderAmount()*bean.getDiscountCaclPrice()-bean.getDiscountMoney();
                                            totalPrice += price;
                                            totalDiscount += bean.getDiscountMoney();
                                        }
                                        else
                                        {
                                            price = bean.getOrderAmount()*bean.getBuyPrice();
                                            totalPrice += price;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    for (int i = 0; i < childs.size(); i++) {
                        Ord00202Resp child = childs.get(i);
                        if (child.isGdsStatus())
                        {
                            if (child.isChoosed())
                            {
                                if (child.isIfFulfillProm())
                                {
                                    _price = child.getOrderAmount()*child.getDiscountCaclPrice();
                                    _totalPrice += _price;
                                    _totalDiscount += child.getDiscountMoney();
                                }
                                else
                                {
                                    _price = child.getOrderAmount()*child.getBuyPrice();
                                    _totalPrice += _price;
                                }
                            }
                            cholder.tvTotalBuyPrice.setText("小计:" + MoneyUtils.GoodListPrice(totalPrice+totalFalsePrice+_totalPrice));
                            if (group.isIfFulfillProm())
                            {
                                cholder.tvTotalMinusPrice.setText("立减" + MoneyUtils.GoodListPrice(_totalDiscount+group.getDiscountMoney()));
                            }
                            else
                            {
                                cholder.tvTotalMinusPrice.setText("立减" + MoneyUtils.GoodListPrice(_totalDiscount));
                            }
                        }
                    }
                }
                else if (groupLists.size()>0 && childs.size()==0)
                {
                    for (int i = 0; i < groupLists.size(); i++) {
                        Ord00205Resp entity = groupLists.get(i);
                        boolean isFlag = false;
                        for (int j = 0; j < entity.getGroupSkus().size(); j++) {
                            Ord00202Resp sku = entity.getGroupSkus().get(j);
                            if (!sku.isGdsStatus()) {
                                isFlag =true;
                                break;
                            }
                        }
                        if (isFlag) {
                            for (int k = 0; k < entity.getGroupSkus().size(); k++) {
                                Ord00202Resp sku = entity.getGroupSkus().get(k);
                                sku.setGdsStatus(false);
                            }
                            totalFalsePrice = 0L;
                            cholder.tvTotalBuyPrice.setText("小计:" + MoneyUtils.GoodListPrice(totalPrice+totalFalsePrice));
                            cholder.tvTotalMinusPrice.setText("");
                        } else {
                            if (entity.isChoosed()) {
                                for (int m = 0; m < entity.getGroupSkus().size(); m++) {
                                    Ord00202Resp bean = entity.getGroupSkus().get(m);
                                    if (bean.isGdsStatus())
                                    {
                                        if (bean.isIfFulfillProm())
                                        {
                                            price = bean.getOrderAmount()*bean.getDiscountCaclPrice()-bean.getDiscountMoney();
                                            totalPrice += price;
                                            totalDiscount += bean.getDiscountMoney();
                                        }
                                        else
                                        {
                                            price = bean.getOrderAmount()*bean.getBuyPrice();
                                            totalPrice += price;
                                        }
                                        cholder.tvTotalBuyPrice.setText("小计:" + MoneyUtils.GoodListPrice(totalPrice+totalFalsePrice));
                                        cholder.tvTotalMinusPrice.setText("");
                                    }
                                }
                            }
                        }
                    }
                }
                else if (childs.size() > 0 && groupLists.size()==0)
                {
                    List<Ord00202Resp> noChoosedList = new ArrayList<>();
                    for (int i = 0; i < childs.size(); i++) {
                        Ord00202Resp child = childs.get(i);
                        if (child.isGdsStatus())
                        {
                            if (child.isChoosed())
                            {
                                if (child.isIfFulfillProm())
                                {
                                    _price = child.getOrderAmount()*child.getDiscountCaclPrice();
                                    _totalPrice += _price;
                                    _totalDiscount += child.getDiscountMoney();
                                }
                                else
                                {
                                    _price = child.getOrderAmount()*child.getBuyPrice();
                                    _totalPrice += _price;
                                }
                                cholder.tvTotalBuyPrice.setText("小计:" + MoneyUtils.GoodListPrice(_totalPrice));

                                if (group.isIfFulfillProm())
                                {
                                    cholder.tvTotalMinusPrice.setText("立减" + MoneyUtils.GoodListPrice(_totalDiscount+group.getDiscountMoney()));
                                }
                                else
                                {
                                    cholder.tvTotalMinusPrice.setText("立减" + MoneyUtils.GoodListPrice(_totalDiscount));
                                }
                            }
                            else
                            {
                                noChoosedList.add(child);
                            }
                        }
                    }
                    if (noChoosedList.size()==childs.size()) {
                        cholder.tvTotalBuyPrice.setText("小计:￥0.00");
                        cholder.tvTotalMinusPrice.setText("");
                    }
                }
            }
            else {
                cholder.tvTotalBuyPrice.setVisibility(View.GONE);
                cholder.tvTotalMinusPrice.setVisibility(View.GONE);
            }
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

    class GroupHolder {
        @BindView(R.id.rl_store)      RelativeLayout rlStore;
        @BindView(R.id.cb_checkstore) CheckBox cbStore;//店铺选择
        @BindView(R.id.iv_shop_logo)  ImageView ivShopLogo;
        @BindView(R.id.tv_storeName)  TextView tvStoreName;//店铺名
        @BindView(R.id.prom_layout)   RelativeLayout promLayout;
        @BindView(R.id.ll_prom)       LinearLayout llProm;
        @BindView(R.id.tv_nameShort)  TextView tvNameShort;//促销名
        @BindView(R.id.tv_promTheme)  TextView tvPromTheme;//促销样式
        @BindView(R.id.view_line)     View viewLine;//分割线

        public GroupHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    class ChildHolder {
        @BindView(R.id.rl_good)            RelativeLayout rlGood;
        @BindView(R.id.cb_checkitem)       CheckBox cbItem;//商品选择
        @BindView(R.id.iv_itemimg)         ImageView ivItemImg;//商品图片
        @BindView(R.id.tv_goodName)        TextView tvGoodName;//商品名
        @BindView(R.id.tv_promName)        TextView tvPromName;//优惠信息
        @BindView(R.id.tv_skuName)         TextView tvSkuName;//规格
        @BindView(R.id.fl_num)             FrameLayout flNum;
        @BindView(R.id.tv_reduce)          TextView tvReduce;//减少图标
        @BindView(R.id.tv_add)             TextView tvAdd;//增加图标
        @BindView(R.id.tv_num)             TextView tvBuyNum;//购买数量
        @BindView(R.id.tv_buyPrice)        TextView tvBuyPrice;//购买价格
        @BindView(R.id.ll_buy)             RelativeLayout llBuy;
        @BindView(R.id.tv_totalbuyPrice)   TextView tvTotalBuyPrice;
        @BindView(R.id.tv_totalminusPrice) TextView tvTotalMinusPrice;
        @BindView(R.id.tv_modifyProm)      TextView tvModifyProm;
        @BindView(R.id.tv_delItem)         TextView tvDelItem;

        public ChildHolder(View view){
            ButterKnife.bind(this,view);
            view.setTag(this);
        }
    }

    class ChildGroupHolder{
        @BindView(R.id.rl_groups)          RelativeLayout rlGroups;
        @BindView(R.id.cb_groups)          CheckBox cbGroups;
        @BindView(R.id.tv_gropus_name)     TextView tvGroupsName;
        @BindView(R.id.fl_groups_num)      FrameLayout flGroupsNum;
        @BindView(R.id.tv_reduce_groups)   TextView tvReduceGroups;
        @BindView(R.id.tv_groups_num)      TextView tvGroupsNum;
        @BindView(R.id.tv_add_groups)      TextView tvAddGroups;
        @BindView(R.id.tv_groups_price)    TextView tvGroupsPrice;
        @BindView(R.id.tv_del_groups)      TextView tvDeleteGroups;
        @BindView(R.id.lv_groups)          CustomListView lvGroups;
        @BindView(R.id.ll_buy)             RelativeLayout llBuy;
        @BindView(R.id.tv_total_price)     TextView tvTotalBuyPrice;
        @BindView(R.id.tv_total_minus)     TextView tvTotalMinusPrice;

        public ChildGroupHolder(View view){
            ButterKnife.bind(this,view);
            view.setTag(this);
        }
    }

    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         * @param groupPosition 组元素位置
         * @param isChecked 组元素选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变时触发的事件
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param isChecked 子元素选中与否
         */
        void checkChild(Ord00202Resp product, int groupPosition, int childPosition, boolean isChecked);

        /**
         * 组合商品选择
         * @param groupPosition
         * @param childPosition
         * @param isChecked
         */
        void checkGroups(int groupPosition, int childPosition, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked 子元素选中与否
         */
        void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked 子元素选中与否
         */
        void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 增加组合商品
         * @param groupPosition
         * @param childPosition
         */
        void doAddGroups(int groupPosition, int childPosition, View showCountView);

        /**
         * 删减组合商品
         * @param groupPosition
         * @param childPosition
         */
        void doReduceGrouops(int groupPosition, int childPosition, View showCountView);
    }

    /**
     * 删除接口
     */
    public interface DeleteInterface {
        /**
         * 删除操作
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param isChecked 子元素选中与否
         */
        void doDeleteItem(Ord00202Resp product, int groupPosition, int childPosition, boolean isChecked);

        void doDeleteGroups(int groupPosition, int childPosition, boolean isChecked);
    }

    public interface ShowShopCouponDialogInterface {
        void showShopCouponDialog(int groupPosition);
    }

    public interface ShowProductCouponDialogInterface {
        void showProductCouponDialog(int groupPosition, int childPosition);
    }

    public interface RedirectToShopDetailInterface {
        void redirectToShopDetail(Ord00202Resp product);
    }

}
