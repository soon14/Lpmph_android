package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ai.ecp.app.resp.AcctTrade;
import com.ai.ecp.sys.dubbo.dto.MsgInsiteResDTO;
import com.ailk.pmph.R;

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
 * 时间: 2016/6/13 17:04
 */
public class SystemMsgListAdapter extends BaseAdapter {

    private List<MsgInsiteResDTO> mList;
    private Context mContext;

    public SystemMsgListAdapter(Context context,List<MsgInsiteResDTO> list){
        this.mContext = context;
        this.mList = list;
    }

    public void addData(List<MsgInsiteResDTO> data) {
        if (mList != null && data != null && !data.isEmpty()) {
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public MsgInsiteResDTO getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_system_message, parent, false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        MsgInsiteResDTO bean = getItem(position);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Timestamp ts = new Timestamp(bean.getRecTime().getTime());
        holder.tvDate.setText(sf.format(ts));
        holder.tvStatus.setText(bean.getMsgName());
        holder.tvContent.setText(bean.getMsgContext());
        return convertView;
    }

    static class ViewHolder{

        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }

    }
}
