package com.ailk.integral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.gds.PointGoodSearchResultVO;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.tool.GlideUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.adp
 * 作者: Chrizz
 * 时间: 2016/5/17 16:37
 */
public class InteGoodsListAdapter extends BaseAdapter {

    private List<PointGoodSearchResultVO> mList;
    private Context mContext;
    private ReToDetail reToDetail;

    public InteGoodsListAdapter(Context context, List<PointGoodSearchResultVO> list){
        this.mContext = context;
        this.mList = list;
    }

    public void addData(List<PointGoodSearchResultVO> data) {
        if (mList != null && data != null && !data.isEmpty()) {
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setReToDetail(ReToDetail reToDetail) {
        this.reToDetail = reToDetail;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public PointGoodSearchResultVO getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inte_item_goods_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final PointGoodSearchResultVO bean = getItem(position);
        GlideUtil.loadImg(mContext, bean.getImageUrl(), holder.ivGdsImg);
        if (bean.getGdsNameSrc()!=null) {
            holder.tvGdsName.setText(bean.getGdsNameSrc());
        } else {
            holder.tvGdsName.setText(null);
        }
        long score = bean.getScore();
        long price = bean.getDefaultPrice();
        if (score == 0 && price != 0) {
            holder.tvGdsScore.setText(MoneyUtils.GoodListPrice(bean.getDefaultPrice()));
        } else if (score != 0 && price == 0) {
            holder.tvGdsScore.setText(score + "积分");
        } else if (score != 0 && price != 0) {
            holder.tvGdsScore.setText(score + "积分 + " + MoneyUtils.GoodListPrice(bean.getDefaultPrice()));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reToDetail.reToDetail(bean);
            }
        });
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.iv_gds_img)
        ImageView ivGdsImg;
        @BindView(R.id.tv_gds_name)
        TextView tvGdsName;
        @BindView(R.id.tv_gds_score)
        TextView tvGdsScore;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    public interface ReToDetail{
        void reToDetail(PointGoodSearchResultVO bean);
    }

}
