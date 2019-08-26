package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ai.ecp.app.resp.ScoreTrade;
import com.ailk.pmph.R;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/4/8 16:39
 */
public class ScoreDetailListAdapter extends BaseAdapter {

    private List<ScoreTrade> mList;
    private Context mContext;

    public ScoreDetailListAdapter(Context context, List<ScoreTrade> list){
        this.mContext = context;
        this.mList = list;
    }

    public void addData(List<ScoreTrade> data) {
        if (mList != null && data != null && !data.isEmpty()) {
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public ScoreTrade getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null==convertView) {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_score_list, parent, false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        ScoreTrade bean = getItem(position);
        if (StringUtils.isNotEmpty(bean.getOrderId())) {
            holder.tvOrderType.setText(bean.getScoreType());
            holder.tvOrderCode.setVisibility(View.VISIBLE);
            holder.tvOrderCode.setText("订单号:"+bean.getOrderId());
        } else {
            holder.tvOrderType.setText(bean.getScoreType());
            holder.tvOrderCode.setVisibility(View.GONE);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp createTime = new Timestamp(bean.getCreateTime().getTime());
        holder.tvCreateTime.setText(df.format(createTime));
        if (StringUtils.equals(bean.getScoreType(),"使用积分")) {
            holder.tvAddScore.setText("-"+bean.getScore());
        } else {
            holder.tvAddScore.setText("+"+bean.getScore());
        }
        return convertView;
    }


    static class ViewHolder{
        @BindView(R.id.tv_order_type)
        TextView tvOrderType;
        @BindView(R.id.tv_order_code)
        TextView tvOrderCode;
        @BindView(R.id.tv_time)
        TextView tvCreateTime;
        @BindView(R.id.tv_add_score)
        TextView tvAddScore;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

}
