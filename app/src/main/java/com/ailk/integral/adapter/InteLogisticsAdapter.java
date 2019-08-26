package com.ailk.integral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord10803Resp;
import com.ailk.pmph.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.adp
 * 作者: Chrizz
 * 时间: 2016/5/12 9:14
 */
public class InteLogisticsAdapter extends BaseAdapter {

    private List<Ord10803Resp> mList;
    private Context mContext;

    public InteLogisticsAdapter(Context context, List<Ord10803Resp> list){
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Ord10803Resp getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inte_item_logistics_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Ord10803Resp bean = getItem(position);
        holder.tvExpressName.setText(bean.getExpressName());
        holder.tvExpressNo.setText(bean.getExpressNo());
        return convertView;
    }

    static class ViewHolder{

        @BindView(R.id.tv_express_name)
        TextView tvExpressName;
        @BindView(R.id.tv_express_no)
        TextView tvExpressNo;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

}
