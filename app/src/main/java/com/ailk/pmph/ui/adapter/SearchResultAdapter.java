package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.gds.GoodSearchResultVO;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.activity.LoginActivity;
import com.ailk.pmph.ui.activity.LoginPmphActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/7/20 15:30
 */
public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //item类型
    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_CONTENT = 1;
    private static final int ITEM_TYPE_BOTTOM = 2;

    private List<GoodSearchResultVO> mList;
    private Context mContext;
    private int mHeaderCount = 1;//头部View个数
    private int mBottomCount = 0;//底部View个数

    private MyItemClickListener mItemClickListener;//item的点击事件
    private CollectListener collectListener;
    boolean isStyle = false;

    public SearchResultAdapter(Context context, List<GoodSearchResultVO> list) {
        this.mContext = context;
        this.mList = list;
    }

    private int getContentItemCount() {
        return mList != null && mList.size() > 0 ? mList.size() : 0;
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }

    public void setHeaderCount(int mHeaderCount) {
        this.mHeaderCount = mHeaderCount;
    }

    public void setBottomCount(int mBottomCount) {
        this.mBottomCount = mBottomCount;
    }

    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setCollectListener(CollectListener collectListener) {
        this.collectListener = collectListener;
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    //内容 ViewHolder
    static class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_gds_desc)
        TextView itemDesc;
        @BindView(R.id.item_author)
        TextView itemAutor;
        @BindView(R.id.item_price)
        TextView itemPrice;
        @BindView(R.id.iv_phone_price)
        ImageView itemPhonePrice;
        @BindView(R.id.tv_has)
        TextView itemBookType;
        @BindView(R.id.iv_collect)
        ImageView itemCollect;
        @BindView(R.id.item_img_type)
        ImageView itemImgType;
        @BindView(R.id.iv_service_tag)
        ImageView ivServiceTag;

        private MyItemClickListener mListener;

        public ContentViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    //底部 ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder {
        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_store_head_nocontent, parent, false));
        } else if (viewType == mHeaderCount) {
            if (isStyle) {
                return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.goods_list_item_2, parent, false), mItemClickListener);
            } else {
                return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.goods_list_item, parent, false), mItemClickListener);
            }
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new BottomViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_store_head_nocontent, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof ContentViewHolder) {
            final GoodSearchResultVO goodSearchResultVO = mList.get(position - mHeaderCount);
            GlideUtil.loadImg(mContext, goodSearchResultVO.getImageUrl(), ((ContentViewHolder) holder).itemImg);
            if (null != goodSearchResultVO.getPromotionType() && 0 != goodSearchResultVO.getPromotionType().size()) {
                String activity = " " + goodSearchResultVO.getPromotionType().get(0) + " ";
                String str = activity + " " + Html.fromHtml(goodSearchResultVO.getGdsNameSrc());
                int bStr = str.indexOf(activity);
                int bend = bStr + activity.length();
                int fStr = str.indexOf(activity);
                int fend = fStr + activity.length();
                SpannableStringBuilder style = new SpannableStringBuilder(str);
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    style.setSpan(new BackgroundColorSpan(ContextCompat.getColor(mContext, R.color.orange_ff6a00)), bStr, bend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    style.setSpan(new BackgroundColorSpan(ContextCompat.getColor(mContext, R.color.red)), bStr, bend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                style.setSpan(new ForegroundColorSpan(Color.WHITE), fStr, fend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ((ContentViewHolder) holder).itemName.setText(style);

            } else {
                ((ContentViewHolder) holder).itemName.setText(Html.fromHtml(goodSearchResultVO.getGdsNameSrc()));
            }

            if (isStyle) {
                ((ContentViewHolder) holder).itemDesc.setVisibility(View.GONE);
                ((ContentViewHolder) holder).itemAutor.setVisibility(View.GONE);
            } else {
                if (StringUtils.isNotEmpty(goodSearchResultVO.getGdsSubHeadSrc())) {
                    if (StringUtils.equals("null", goodSearchResultVO.getGdsSubHeadSrc())) {
                        ((ContentViewHolder) holder).itemDesc.setVisibility(View.GONE);
                    } else {
                        ((ContentViewHolder) holder).itemDesc.setVisibility(View.VISIBLE);
                        ((ContentViewHolder) holder).itemDesc.setText(goodSearchResultVO.getGdsSubHeadSrc());
                    }
                } else {
                    ((ContentViewHolder) holder).itemDesc.setVisibility(View.GONE);
                }
                if (StringUtils.isNotEmpty(goodSearchResultVO.getAuthorStr())) {
                    ((ContentViewHolder) holder).itemAutor.setVisibility(View.VISIBLE);
                    ((ContentViewHolder) holder).itemAutor.setText("作者：" + goodSearchResultVO.getAuthorStr());
                } else {
                    ((ContentViewHolder) holder).itemAutor.setVisibility(View.GONE);
                }
            }

            Long appSpecPrice = goodSearchResultVO.getAppSpecPrice();
            if (appSpecPrice != null && appSpecPrice != 0) {
                MoneyUtils.showSpannedPrice(((ContentViewHolder) holder).itemPrice, MoneyUtils.GoodListPrice(appSpecPrice));
                ((ContentViewHolder) holder).itemPhonePrice.setVisibility(View.VISIBLE);
            } else {
                MoneyUtils.showSpannedPrice(((ContentViewHolder) holder).itemPrice, MoneyUtils.GoodListPrice(goodSearchResultVO.getDiscountPrice()));
                ((ContentViewHolder) holder).itemPhonePrice.setVisibility(View.GONE);
            }
            if (StringUtils.equals("2", goodSearchResultVO.getBookOtherType())) {
                ((ContentViewHolder) holder).itemBookType.setVisibility(View.VISIBLE);
            }
            else if (StringUtils.equals("3", goodSearchResultVO.getBookOtherType())) {
                ((ContentViewHolder) holder).itemBookType.setVisibility(View.VISIBLE);
            }
            else {
                ((ContentViewHolder) holder).itemBookType.setVisibility(View.GONE);
            }

            String edbook = goodSearchResultVO.getEdbook();
            if (StringUtils.isNotEmpty(edbook)) {
                ((ContentViewHolder) holder).itemImgType.setVisibility(View.VISIBLE);
                if (StringUtils.equals("0", edbook)) {
                    ((ContentViewHolder) holder).itemImgType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.icon_gds_type_elec));
                } else {
                    ((ContentViewHolder) holder).itemImgType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.icon_gds_type_digital));
                }
//            } else if (goodDetail.getGdsDetailBaseInfo().isHasValueAdded()) {
//                setVisible(gdsTypeImgIv);
//                gdsImgIv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.tag_service));
            }else {
                ((ContentViewHolder) holder).itemImgType.setVisibility(View.GONE);
            }

            //增值服务标签
            if (goodSearchResultVO.isHasValueAdded()){
                ((ContentViewHolder) holder).ivServiceTag.setVisibility(View.VISIBLE);
                ((ContentViewHolder) holder).ivServiceTag.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.tag_service));
            }else {
                ((ContentViewHolder) holder).ivServiceTag.setVisibility(View.GONE);
            }

            Long collectId = goodSearchResultVO.getCollectId();
            if (collectId != null) {
                ((ContentViewHolder) holder).itemCollect.setImageResource(R.drawable.icon_search_collected);
            } else {
                ((ContentViewHolder) holder).itemCollect.setImageResource(R.drawable.icon_search_collect);
            }

            ((ContentViewHolder) holder).itemCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AppContext.isLogin) {
                        collectListener.collect(goodSearchResultVO, ((ContentViewHolder) holder).itemCollect);
                    } else {
                        DialogAnotherUtil.showCustomAlertDialogWithMessage(mContext, null,
                                "您未登录，请先登录", "登录", "取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DialogUtil.dismissDialog();
                                        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                                            mContext.startActivity(new Intent(mContext, LoginPmphActivity.class));
                                        } else {
                                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                                        }
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DialogUtil.dismissDialog();
                                    }
                                });
                    }
                }
            });

        } else if (holder instanceof BottomViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

    public void addAll(Collection<GoodSearchResultVO> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
    }

    public void addData(List<GoodSearchResultVO> data) {
        if (mList !=null && data != null && !data.isEmpty()) {
            mList.addAll(data);
        }
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

    public GoodSearchResultVO getItem(int location) {
        return mList.get(location);
    }

    public void updateStyle(boolean isStyle) {
        this.isStyle = isStyle;
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface CollectListener {
        void collect(GoodSearchResultVO resultVO, ImageView imageView);
    }

}
