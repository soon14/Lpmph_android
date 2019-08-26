package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/5/7 16:15
 */
public class RankListAdapter extends BaseAdapter {

    private List<GdsBaseInfo> mList;
    private Context mContext;
    private RedirectToDetail redirectToDetail;

    public RankListAdapter(Context context, List<GdsBaseInfo> list){
        this.mContext = context;
        this.mList = list;
    }

    public void addData(List<GdsBaseInfo> data) {
        if (mList != null && data != null && !data.isEmpty()) {
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setRedirectToDetail(RedirectToDetail redirectToDetail) {
        this.redirectToDetail = redirectToDetail;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public GdsBaseInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.book_list_item, parent, false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final GdsBaseInfo good = getItem(position);
        holder.titleText.setText(good.getGdsName());
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            holder.soldPrice.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
        } else {
            holder.soldPrice.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        MoneyUtils.showSpannedPrice(holder.soldPrice, MoneyUtils.GoodListPrice(good.getBuyPrice()));
        holder.originPrice.setText(MoneyUtils.GoodListPrice(good.getBasePrice()));
        holder.originPrice.setPaintFlags(holder.originPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.originPrice.getPaint().setAntiAlias(true);
        GlideUtil.loadImg(mContext, good.getImgPath(), holder.userImg);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToDetail.redirect(good);
            }
        });
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.booklist_item_img)
        ImageView userImg;
        @BindView(R.id.item_title)
        TextView titleText;
        @BindView(R.id.sold_price)
        TextView soldPrice;
        @BindView(R.id.origin_price)
        TextView originPrice;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    public interface RedirectToDetail{
        void redirect(GdsBaseInfo good);
    }
}
