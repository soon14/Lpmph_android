package com.ailk.integral.adapter;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord10201Resp;
import com.ai.ecp.app.resp.Ord10202Resp;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.thirdstore.activity.StoreActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.MoneyUtils;
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
 * 项目名:pmph_android
 * 包名:com.ailk.integral.adp
 * 作者: Chrizz
 * 时间: 2016/5/19 10:29
 */
public class InteCartAdapter extends BaseExpandableListAdapter {

    private List<Ord10201Resp> groups = new ArrayList<>();
    private Map<Long, List<Ord10202Resp>> productsMap = new HashMap<>();
    private Context mContext;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    private DeleteInterface deleteInterface;
    private RedirectToShopDetailInterface redirectInterface;
    private String textEdit = "";

    public InteCartAdapter(Context context,List<Ord10201Resp> groups,Map<Long, List<Ord10202Resp>> productsMap,String text){
        this.mContext=context;
        this.groups=groups;
        this.productsMap=productsMap;
        this.textEdit=text;
    }

    public void setTextEdit(String text) {
        this.textEdit = text;
        notifyDataSetChanged();
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

    public void setRedirectInterface(RedirectToShopDetailInterface redirectInterface) {
        this.redirectInterface = redirectInterface;
    }

    @Override
    public int getGroupCount() {
        return  groups.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inte_item_cart_group_list, parent, false);
            gholder = new GroupHolder(convertView);
            convertView.setTag(gholder);
        }
        else
        {
            gholder = (GroupHolder) convertView.getTag();
        }
        final Ord10201Resp group = getGroup(groupPosition);
        gholder.tvStoreName.setText(group.getShopName());
        gholder.cbStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setIsChoosed(((CheckBox) v).isChecked());
                checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());
            }
        });
        gholder.cbStore.setChecked(group.isChoosed());
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
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ProductHolder productHolder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inte_item_product_list, parent, false);
            productHolder = new ProductHolder(convertView);
            convertView.setTag(productHolder);
        }
        else
        {
            productHolder = (ProductHolder) convertView.getTag();
        }
        final Ord10201Resp group = getGroup(groupPosition);
        final List<Ord10202Resp> products = productsMap.get(group.getShopId());
        final Ord10202Resp product = getChild(groupPosition, childPosition);
        Long score = product.getScore();
        Long buyPrice = product.getBuyPrice();
        if (!product.isGdsStatus())
        {
            product.setIsChoosed(false);
            productHolder.cbItem.setChecked(product.isChoosed());
            productHolder.cbItem.setText("失效");
            productHolder.cbItem.setBackgroundResource(0);
            productHolder.cbItem.setTextColor(ContextCompat.getColor(mContext, R.color.gray_969696));
            productHolder.rlGood.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray_ebebeb));
            productHolder.tvPrice.setVisibility(View.VISIBLE);
            productHolder.flNum.setVisibility(View.GONE);
            GlideUtil.loadImg(mContext, product.getPicUrl(), productHolder.ivItemImg);
            productHolder.tvGoodName.setText(product.getGdsName());
            productHolder.tvGoodName.setTextColor(ContextCompat.getColor(mContext, R.color.gray_969696));
            productHolder.tvBuyPrice.setVisibility(View.INVISIBLE);
            productHolder.tvPrice.setText("￥0.00");
            productHolder.tvPrice.setTextColor(ContextCompat.getColor(mContext, R.color.gray_969696));
            productHolder.ivItemImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redirectInterface.redirectToShopDetail(product);
                }
            });
            if (StringUtils.equals("完成",textEdit)) {
                productHolder.tvDelete.setVisibility(View.VISIBLE);
                productHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteInterface.doDeleteItem(group,product,groupPosition,childPosition);
                    }
                });
            } else {
                productHolder.tvDelete.setVisibility(View.GONE);
            }
        }
        else
        {
            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                productHolder.tvBuyPrice.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
                productHolder.tvTotalBuyPrice.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
            } else {
                productHolder.tvBuyPrice.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                productHolder.tvTotalBuyPrice.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            }
            productHolder.tvGoodName.setText(product.getGdsName());
            GlideUtil.loadImg(mContext, product.getPicUrl(), productHolder.ivItemImg);
            if (score != null && buyPrice != null) {
                productHolder.tvBuyPrice.setText(score + "积分 + " + MoneyUtils.GoodListPrice(buyPrice));
            } else if (score != null && buyPrice == null) {
                productHolder.tvBuyPrice.setText(score + "积分 +  ￥0.00");
            } else if (score == null && buyPrice == null) {
                productHolder.tvBuyPrice.setText("0积分 +  ￥0.00");
            }
            if (StringUtils.equals(product.getGdsType(),"2")) //虚拟商品
            {
                productHolder.tvReduce.setVisibility(View.INVISIBLE);
                productHolder.tvAdd.setVisibility(View.INVISIBLE);
                productHolder.flNum.setBackgroundResource(0);
                productHolder.tvBuyNum.setText("1本");
            }
            else
            {
                productHolder.tvBuyNum.setText(String.valueOf(product.getOrderAmount()));
            }
            productHolder.cbItem.setChecked(product.isChoosed());
            productHolder.cbItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product.setIsChoosed(((CheckBox) v).isChecked());
                    productHolder.cbItem.setChecked(((CheckBox) v).isChecked());
                    checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());
                }
            });
            productHolder.tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doIncrease(groupPosition, childPosition, productHolder.tvBuyNum, productHolder.cbItem.isChecked());
                }
            });
            productHolder.tvReduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doDecrease(groupPosition, childPosition, productHolder.tvBuyNum, productHolder.cbItem.isChecked());
                }
            });
            productHolder.ivItemImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redirectInterface.redirectToShopDetail(product);
                }
            });

        }
        if (isLastChild)
        {
            productHolder.tvTotalBuyPrice.setVisibility(View.VISIBLE);
            Long totalScore = 0L;
            Long totalPrice = 0L;
            Long detailPrice;
            Long detailScore;
            List<Ord10202Resp> noChoosedList = new ArrayList<>();
            for (int i = 0; i < products.size(); i++)
            {
                Ord10202Resp detail = products.get(i);
                if (detail.isGdsStatus())
                {
                    if (detail.isChoosed())
                    {
                        detailPrice = detail.getOrderAmount()*detail.getBuyPrice();
                        totalPrice += detailPrice;
                        detailScore = detail.getOrderAmount()*detail.getScore();
                        totalScore += detailScore;
                        productHolder.tvTotalBuyPrice.setText("小计："+ totalScore + "积分 + " + MoneyUtils.GoodListPrice(totalPrice));
                    }
                    else
                    {
                        noChoosedList.add(detail);
                    }
                }
            }
            if (noChoosedList.size()==products.size())
            {
                productHolder.tvTotalBuyPrice.setText("小计：0积分 + ￥0.00");
            }
        }
        else
        {
            productHolder.tvTotalBuyPrice.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupHolder {
        @BindView(R.id.rl_store)
        RelativeLayout rlStore;
        @BindView(R.id.cb_checkstore)
        CheckBox cbStore;//店铺选择
        @BindView(R.id.tv_storeName)
        TextView tvStoreName;//店铺名

        public GroupHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    static class ProductHolder {
        @BindView(R.id.rl_good)            RelativeLayout rlGood;
        @BindView(R.id.cb_checkitem)       CheckBox cbItem;//商品选择
        @BindView(R.id.iv_itemimg)         ImageView ivItemImg;//商品图片
        @BindView(R.id.tv_goodName)        TextView tvGoodName;//商品名
        @BindView(R.id.fl_num)             FrameLayout flNum;
        @BindView(R.id.tv_reduce)          TextView tvReduce;//减少图标
        @BindView(R.id.tv_add)             TextView tvAdd;//增加图标
        @BindView(R.id.tv_num)             TextView tvBuyNum;//购买数量
        @BindView(R.id.tv_buyPrice)        TextView tvBuyPrice;//购买价格
        @BindView(R.id.tv_totalbuyPrice)   TextView tvTotalBuyPrice;
        @BindView(R.id.tv_delete)          TextView tvDelete;
        @BindView(R.id.tv_price)           TextView tvPrice;

        public ProductHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

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
        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    public interface ModifyCountInterface {
        /**
         * 增加操作
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);
    }

    /**
     * 删除接口
     */
    public interface DeleteInterface {
        /**
         * 删除操作
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         */
        void doDeleteItem(Ord10201Resp shop, Ord10202Resp product, int groupPosition, int childPosition);
    }

    public interface RedirectToShopDetailInterface {
        void redirectToShopDetail(Ord10202Resp product);
    }

}
