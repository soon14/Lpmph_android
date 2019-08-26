package com.ailk.pmph.thirdstore.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.cms.AdvertiseRespVO;
import com.ai.ecp.app.resp.gds.GoodSearchResultVO;
import com.ailk.jazzyviewpager.JazzyViewPager;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.ui.view.FixedSpeedScroller;
import com.ailk.tool.GlideUtil;
import com.viewpagerindicator.CirclePageIndicator;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类描述：
 * 项目名称： pmph_android
 * Package:  com.ailk.pmph.ui.adapter
 * 创建人：   Nzke
 * 创建时间： 2016/6/5
 * 修改人：   Nzke
 * 修改时间： 2016/6/5
 * 修改备注： 2016/6/5
 * version   v1.0
 */

public class StoreRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;

    private List<GoodSearchResultVO> mList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int mHeaderCount = 1;//头部View个数
    private int mBottomCount = 0;//底部View个数

    private List<AdvertiseRespVO> advertiseList ;
    private MyItemClickListener mItemClickListener;//item的点击事件

    public StoreRecyclerAdapter(Context context, List<GoodSearchResultVO> list) {
        mContext = context;
        this.mList = list;
        mLayoutInflater = LayoutInflater.from(context);
        advertiseList = new ArrayList<>();
    }

    //内容长度
    public int getContentItemCount() {
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
        @BindView(R.id.item_price)
        TextView itemPrice;
        @BindView(R.id.item_people)
        TextView itemPeople;

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

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        LinearLayout picLayout;
        JazzyViewPager viewpager;
        CirclePageIndicator indicator;
        View viewpagerLayout;
        TextView tvRecommend;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            picLayout = (LinearLayout) itemView.findViewById(R.id.pic_layout);
            viewpager = (JazzyViewPager) itemView.findViewById(R.id.viewpager);
            indicator = (CirclePageIndicator) itemView.findViewById(R.id.home_indicator);
            viewpagerLayout = itemView.findViewById(R.id.viewpager_layout);
            tvRecommend = (TextView) itemView.findViewById(R.id.tv_recommend);
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
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.item_store_head_layout, parent, false));
        } else if (viewType == mHeaderCount) {
            return new ContentViewHolder(mLayoutInflater.inflate(R.layout.item_store_recycler, parent, false), mItemClickListener);
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new BottomViewHolder(mLayoutInflater.inflate(R.layout.item_store_head_layout, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder)
        {
//            ((HeaderViewHolder) holder).picLayout.setVisibility(View.GONE);
            ((HeaderViewHolder) holder).viewpagerLayout.setVisibility(View.GONE);
            ((HeaderViewHolder) holder).tvRecommend.setVisibility(View.VISIBLE);
            final StorePicAdapter storePicAdapter = new StorePicAdapter(mContext, new ArrayList<AdvertiseRespVO>());

            createScroller(((HeaderViewHolder) holder).viewpager);
            ((HeaderViewHolder) holder).viewpager.setTransitionEffect(JazzyViewPager.TransitionEffect.Accordion);
            ((HeaderViewHolder) holder).viewpager.setAdapter(storePicAdapter);
            createPageIndicator(((HeaderViewHolder) holder).indicator,((HeaderViewHolder) holder).viewpager,
                    ((HeaderViewHolder) holder).picLayout);
            if (null != advertiseList && 0 != advertiseList.size()) {
                storePicAdapter.addAll(advertiseList);
                ((HeaderViewHolder) holder).picLayout.setVisibility(View.VISIBLE);
                ((HeaderViewHolder) holder).viewpagerLayout.setVisibility(View.VISIBLE);
            }
        }
        else if (holder instanceof ContentViewHolder)
        {
            GoodSearchResultVO goodSearchResultVO = mList.get(position - mHeaderCount);
            if (null != goodSearchResultVO.getPromotionType() && 0 != goodSearchResultVO.getPromotionType().size()) {
                String activity = " " + goodSearchResultVO.getPromotionType().get(0) + " ";
                String str = activity + " " + Html.fromHtml(goodSearchResultVO.getGdsName());
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
                ((ContentViewHolder) holder).itemName.setText(Html.fromHtml(goodSearchResultVO.getGdsName()));
            }
            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                ((ContentViewHolder) holder).itemPrice.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
            } else {
                ((ContentViewHolder) holder).itemPrice.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            }
            MoneyUtils.showSpannedPrice(((ContentViewHolder) holder).itemPrice, MoneyUtils.GoodListPrice(goodSearchResultVO.getDefaultPrice()));
            ((ContentViewHolder) holder).itemPeople.setText(goodSearchResultVO.getSales() + "人付款");
            GlideUtil.loadImg(mContext, goodSearchResultVO.getImageUrl(), ((ContentViewHolder) holder).itemImg);
        }
        else if (holder instanceof BottomViewHolder)
        {

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

    public void setAdvertiseRespVO(Collection<AdvertiseRespVO> advertiseList) {
        if (null != advertiseList && 0 != advertiseList.size()) {
            this.advertiseList.clear();
            this.advertiseList.addAll(advertiseList);
        }
    }

    //广告轮播
    private void createScroller(ViewPager viewPager) {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Interpolator sInterpolator = new AccelerateInterpolator();
            FixedSpeedScroller scroller = new FixedSpeedScroller(
                    viewPager.getContext(), sInterpolator);
            scroller.setFixedDuration(200);
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    private void createPageIndicator(CirclePageIndicator indicator,ViewPager viewPager, LinearLayout picLayout) {
        indicator.setViewPager(viewPager);
        final float density = picLayout.getResources().getDisplayMetrics().density;
        indicator.setRadius(4 * density);
        indicator.setFillColor(ContextCompat.getColor(mContext, R.color.red_e9573b));
        indicator.setPageColor(ContextCompat.getColor(mContext, R.color.gray_969696));
        indicator.setStrokeWidth(0);
    }

}
