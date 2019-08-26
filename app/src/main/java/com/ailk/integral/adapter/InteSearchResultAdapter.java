package com.ailk.integral.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.gds.PointGoodSearchResultVO;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.MoneyUtils;
import com.androidquery.AQuery;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.adp
 * 作者: Chrizz
 * 时间: 2016/7/21 9:46
 */
public class InteSearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //item类型
    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_CONTENT = 1;
    private static final int ITEM_TYPE_BOTTOM = 2;

    private List<PointGoodSearchResultVO> mList;
    private Context mContext;
    private int mHeaderCount = 1;//头部View个数
    private int mBottomCount = 0;//底部View个数

    private MyItemClickListener mItemClickListener;//item的点击事件
    private AQuery mAQuery;

    public InteSearchResultAdapter(Context context, List<PointGoodSearchResultVO> list) {
        this.mContext = context;
        this.mList = list;
        this.mAQuery = new AQuery(context);
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
        @BindView(R.id.iv_gds_img)
        ImageView gdsImg;
        @BindView(R.id.tv_gds_name)
        TextView gdsName;
        @BindView(R.id.tv_gds_score)
        TextView gdsScore;

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
            return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.inte_item_goods_list, parent, false), mItemClickListener);
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new BottomViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_store_head_nocontent, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof ContentViewHolder) {
            PointGoodSearchResultVO goodSearchResultVO = mList.get(position - mHeaderCount);
            mAQuery.id(((ContentViewHolder) holder).gdsImg).image(goodSearchResultVO.getImageUrl(), true, true, 200, R.drawable.default_img, AppContext.bmPreset, 0);
            if (goodSearchResultVO.getGdsNameSrc()!=null) {
                ((ContentViewHolder) holder).gdsName.setText(goodSearchResultVO.getGdsNameSrc());
            } else {
                ((ContentViewHolder) holder).gdsName.setText(null);
            }
            long score = goodSearchResultVO.getScore();
            long price = goodSearchResultVO.getDefaultPrice();
            if (score == 0 && price != 0) {
                ((ContentViewHolder) holder).gdsScore.setText(MoneyUtils.GoodListPrice(goodSearchResultVO.getDefaultPrice()));
            } else if (score != 0 && price == 0) {
                ((ContentViewHolder) holder).gdsScore.setText(score + "积分");
            } else if (score != 0 && price != 0) {
                ((ContentViewHolder) holder).gdsScore.setText(score + "积分 + " + MoneyUtils.GoodListPrice(goodSearchResultVO.getDefaultPrice()));
            }
        } else if (holder instanceof BottomViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

    public void addAll(Collection<PointGoodSearchResultVO> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
    }

    public void addData(List<PointGoodSearchResultVO> data) {
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

    public PointGoodSearchResultVO getItem(int location) {
        return mList.get(location);
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

}
