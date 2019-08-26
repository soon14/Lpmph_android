package com.ailk.integral.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.gds.PointGdsScoreExtRespInfo;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.MoneyUtils;

import org.apache.commons.lang.StringUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.adp
 * 作者: Chrizz
 * 时间: 2016/5/18 19:21
 */
public class InteDetailScoreListAdapter extends BaseAdapter {

    private List<PointGdsScoreExtRespInfo> mList;
    private Context mContext;
    private CheckScoreInterface checkScoreInterface;
    private Long scoreTypeId;

    public InteDetailScoreListAdapter(Context context){
        this.mContext = context;
    }

    public void setScoreTypeId(Long scoreTypeId) {
        this.scoreTypeId = scoreTypeId;
    }

    public void setScores(List<PointGdsScoreExtRespInfo> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void setCheckScoreInterface(CheckScoreInterface checkScoreInterface) {
        this.checkScoreInterface = checkScoreInterface;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public PointGdsScoreExtRespInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (null==convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inte_item_score_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final PointGdsScoreExtRespInfo score = getItem(position);
        Long detailPrice = score.getPrice();
        Long detailScore = score.getScore();
        if ((detailPrice==null || detailPrice==0) && (detailScore==null || detailScore==0)) {
            holder.tvGdsScore.setText("0积分+0元");
        } else if ((detailScore==null || detailScore==0) && (detailPrice != null && detailPrice!=0)) {
            holder.tvGdsScore.setText(StringUtils.remove(MoneyUtils.GoodListPrice(detailPrice)+"元","￥"));
        } else if ((detailPrice != null && detailPrice!=0) && (detailScore != null && detailScore != 0)) {
            String price = StringUtils.remove(MoneyUtils.GoodListPrice(detailPrice)+"元","￥");
            holder.tvGdsScore.setText(detailScore+"积分+"+price);
        } else if ((detailPrice==null || detailPrice==0) && (detailScore != null && detailScore!=0)) {
            holder.tvGdsScore.setText(detailScore+"积分");
        }

        if (scoreTypeId.equals(score.getId())) {
            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                holder.tvGdsScore.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
            } else {
                holder.tvGdsScore.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            }
            holder.ivCheck.setVisibility(View.VISIBLE);
        } else {
            holder.tvGdsScore.setTextColor(ContextCompat.getColor(mContext, R.color.gray_312B2D));
            holder.ivCheck.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkScoreInterface.checkScore(score);
            }
        });
        return convertView;
    }

    static class ViewHolder{

        @BindView(R.id.tv_gds_score)
        TextView tvGdsScore;
        @BindView(R.id.iv_check)
        ImageView ivCheck;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    public interface CheckScoreInterface{
        void checkScore(PointGdsScoreExtRespInfo score);
    }

}
