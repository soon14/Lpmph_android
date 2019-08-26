package com.ailk.integral.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.gds.PointGdsPropValueBaseInfo;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;

import org.apache.commons.lang.StringUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.adp
 * 作者: Chrizz
 * 时间: 2016/5/20 14:56
 */
public class InteRangeListAdapter extends BaseAdapter {

    private List<PointGdsPropValueBaseInfo> mList;
    private Context mContext;
    private Long rangType;

    public InteRangeListAdapter(Context context){
        this.mContext=context;
    }

    public void setValues(List<PointGdsPropValueBaseInfo> values) {
        this.mList = values;
        notifyDataSetChanged();
    }

    public void setRangType(Long rangType) {
        this.rangType = rangType;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public PointGdsPropValueBaseInfo getItem(int position) {
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
        final PointGdsPropValueBaseInfo bean = getItem(position);
        holder.tvGdsScore.setText(bean.getPropValue());
        if (rangType!=null && rangType.equals(bean.getId())) {
            holder.ivCheck.setVisibility(View.VISIBLE);
            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                holder.tvGdsScore.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
            } else {
                holder.tvGdsScore.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            }
        } else {
            holder.ivCheck.setVisibility(View.GONE);
            holder.tvGdsScore.setTextColor(ContextCompat.getColor(mContext, R.color.gray_312B2D));
        }
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

}
