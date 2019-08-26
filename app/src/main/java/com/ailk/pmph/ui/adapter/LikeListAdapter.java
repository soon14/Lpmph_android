package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.Cms010Resp;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.tool.GlideUtil;


import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/10/11 9:27
 */

public class LikeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_CONTENT = 1;
    private static final int ITEM_TYPE_BOTTOM = 2;

    private int mHeaderCount = 1;//头部View个数
    private int mBottomCount = 0;//底部View个数

    private List<Cms010Resp.Item> mList;
    private Context mContext;
    private ItemViewClickListener itemViewClickListener;

    public LikeListAdapter(Context context, List<Cms010Resp.Item> list){
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

    public void setItemViewClickListener(ItemViewClickListener itemViewClickListener) {
        this.itemViewClickListener = itemViewClickListener;
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
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_store_head_nocontent, parent, false));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_like_list, parent, false), itemViewClickListener);
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new BottomViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_store_head_nocontent, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof ItemHolder) {
            Cms010Resp.Item item = mList.get(position - mHeaderCount);
            GlideUtil.loadImg(mContext, item.getImgUrl(),((ItemHolder) holder).ivGdsImg);
            ((ItemHolder)holder).tvGdsName.setText(item.getName());
            if (item.getDiscountPrice() != null) {
                MoneyUtils.showSpannedPrice(((ItemHolder)holder).tvGdsPrice, MoneyUtils.GoodListPrice(item.getDiscountPrice()));
            }
            Long collectId = item.getCollectId();
            if (collectId != null) {
                ((ItemHolder)holder).ivCollect.setImageResource(R.drawable.icon_like_collected);
            } else {
                ((ItemHolder)holder).ivCollect.setImageResource(R.drawable.icon_like_collect);
            }
        } else if (holder instanceof BottomViewHolder) {

        }
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.iv_gds_img)
        ImageView ivGdsImg;
        @BindView(R.id.tv_gds_name)
        TextView tvGdsName;
        @BindView(R.id.tv_gds_price)
        TextView tvGdsPrice;
        @BindView(R.id.tv_similar)
        TextView tvSimilar;
        @BindView(R.id.iv_collect)
        ImageView ivCollect;

        private ItemViewClickListener mListener;

        public ItemHolder(View itemView, ItemViewClickListener listener) {
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

    public void addAll(Collection<Cms010Resp.Item> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
    }

    public void addData(List<Cms010Resp.Item> data) {
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

    public Cms010Resp.Item getItem(int location) {
        return mList.get(location);
    }

    public interface ItemViewClickListener {
        void onItemClick(View view, int position);
    }

}
