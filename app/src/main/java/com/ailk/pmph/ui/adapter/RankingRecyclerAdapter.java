package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.gds.GdsBaseInfo;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/12/2
 */

public class RankingRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //item类型
    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_CONTENT = 1;
    private static final int ITEM_TYPE_BOTTOM = 2;

    private List<GdsBaseInfo> mList;
    private Context mContext;
    private int mHeaderCount = 1;//头部View个数
    private int mBottomCount = 0;//底部View个数

    private MyItemClickListener mItemClickListener;//item的点击事件

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public RankingRecyclerAdapter(Context mContext, List<GdsBaseInfo> mList) {
        this.mList = mList;
        this.mContext = mContext;
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

        @BindView(R.id.booklist_item_img)
        ImageView userImg;
        @BindView(R.id.item_title)
        TextView titleText;
        @BindView(R.id.sold_price)
        TextView soldPrice;
        @BindView(R.id.origin_price)
        TextView originPrice;

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
            return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.book_list_item, parent, false), mItemClickListener);
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new BottomViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_store_head_nocontent, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof ContentViewHolder) {
            GdsBaseInfo good = mList.get(position - mHeaderCount);
            ((ContentViewHolder) holder).titleText.setText(good.getGdsName());
            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                ((ContentViewHolder) holder).soldPrice.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
            } else {
                ((ContentViewHolder) holder).soldPrice.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            }
            if (good.getBuyPrice() != null) {
                MoneyUtils.showSpannedPrice(((ContentViewHolder) holder).soldPrice, MoneyUtils.GoodListPrice(good.getBuyPrice()));
            }
            if (good.getBasePrice() != null) {
                ((ContentViewHolder) holder).originPrice.setText(MoneyUtils.GoodListPrice(good.getBasePrice()));
                ((ContentViewHolder) holder).originPrice.setPaintFlags(((ContentViewHolder) holder).originPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                ((ContentViewHolder) holder).originPrice.getPaint().setAntiAlias(true);
            }
            GlideUtil.loadImg(mContext, good.getImgPath(), ((ContentViewHolder) holder).userImg);

        } else if (holder instanceof BottomViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

    public void addAll(Collection<GdsBaseInfo> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
    }

    public void addData(List<GdsBaseInfo> data) {
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

    public GdsBaseInfo getItem(int location) {
        return mList.get(location);
    }

}
