package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ai.ecp.app.resp.KillGdsInfoRespVO;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类注释:秒杀列表
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/11/1
 */

public class SecondKillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_CONTENT = 1;
    private static final int ITEM_TYPE_BOTTOM = 2;

    private List<KillGdsInfoRespVO> mList;
    private String mIfStart;//0未开始;1抢购中;2已结束
    private String startTime;
    private Context mContext;
    private int mHeaderCount = 1;//头部View个数
    private int mBottomCount = 0;//底部View个数

    private MyItemClickListener mItemClickListener;//item的点击事件

    public SecondKillAdapter(Context context, List<KillGdsInfoRespVO> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void setIfStart(String ifStart) {
        this.mIfStart = ifStart;
        notifyDataSetChanged();
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
        notifyDataSetChanged();
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
        ImageView itemGdsImg;
        @BindView(R.id.tv_gds_name)
        TextView itemGdsName;
        @BindView(R.id.tv_price)
        TextView itemGdsPrice;
        @BindView(R.id.tv_kill_price)
        TextView itemGdsKillPrice;
        @BindView(R.id.tv_status)
        TextView itemStatus;
        @BindView(R.id.tv_progress)
        TextView itemProgress;
        @BindView(R.id.progressBar)
        ProgressBar itemProgressBar;
        @BindView(R.id.status_layout)
        LinearLayout itemStatusLayout;
        @BindView(R.id.tv_kill_time)
        TextView itemKillTime;

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
            return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_prom_second_kill, parent, false), mItemClickListener);
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new BottomViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_store_head_nocontent, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SearchResultAdapter.HeaderViewHolder) {

        } else if (holder instanceof ContentViewHolder) {
            KillGdsInfoRespVO respVO = mList.get(position - mHeaderCount);
            String gdsName = respVO.getGdsName();
            Long basePrice = respVO.getBasePrice();
            Long killPrice = respVO.getKillPrice();
            String gdsImgUrl = respVO.getURL();
            double percent = respVO.getPercent();
            String ifSell = respVO.getIfSell();//0表示秒杀商品已抢光 1表示秒杀商品还有剩余

            GlideUtil.loadImg(mContext, gdsImgUrl, ((ContentViewHolder)holder).itemGdsImg);
            ((ContentViewHolder)holder).itemGdsName.setText(gdsName);
            ((ContentViewHolder)holder).itemGdsPrice.setText("原价:" + MoneyUtils.GoodListPrice(basePrice));
            ((ContentViewHolder)holder).itemGdsPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            ((ContentViewHolder)holder).itemGdsPrice.getPaint().setAntiAlias(true);
            ((ContentViewHolder)holder).itemGdsKillPrice.setText("秒杀价:" + MoneyUtils.GoodListPrice(killPrice));

            if (StringUtils.equals(mIfStart, "0")) {
                ((ContentViewHolder)holder).itemStatusLayout.setVisibility(View.GONE);
                ((ContentViewHolder)holder).itemKillTime.setVisibility(View.VISIBLE);
                ((ContentViewHolder)holder).itemStatus.setText("即将开始");
                ((ContentViewHolder)holder).itemStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_round_second_kill));
                ((ContentViewHolder)holder).itemKillTime.setText(startTime + "正式开抢");
            }

            if (StringUtils.equals(mIfStart, "1")) {
                //虚拟商品不展示数量
                if (!respVO.getGdsTypeFlag()) {
                    ((ContentViewHolder)holder).itemStatusLayout.setVisibility(View.GONE);
                } else {
                    ((ContentViewHolder)holder).itemStatusLayout.setVisibility(View.VISIBLE);
                }
                ((ContentViewHolder)holder).itemKillTime.setVisibility(View.GONE);
                if (StringUtils.equals(ifSell, "0")) {
                    ((ContentViewHolder)holder).itemStatus.setText("已抢完");
                    ((ContentViewHolder)holder).itemStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_round_gray));
                } else {
                    ((ContentViewHolder)holder).itemStatus.setText("马上抢");
                    ((ContentViewHolder)holder).itemStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_round_button_second_kill));
                }
                DecimalFormat format = new DecimalFormat();
                ((ContentViewHolder)holder).itemProgress.setText("已抢购" + format.format(percent) + "%");
                ((ContentViewHolder)holder).itemProgressBar.setProgress((int) percent);
            }
        } else if (holder instanceof SearchResultAdapter.BottomViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

    public void addAll(Collection<KillGdsInfoRespVO> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
    }

    public void addData(List<KillGdsInfoRespVO> data) {
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

    public KillGdsInfoRespVO getItem(int location) {
        return mList.get(location);
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

}
