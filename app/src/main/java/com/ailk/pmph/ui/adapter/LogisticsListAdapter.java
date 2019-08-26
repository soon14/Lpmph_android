package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord01103Resp;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.StringUtil;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/4/21 10:00
 */
public class LogisticsListAdapter extends BaseAdapter {

    private List<Ord01103Resp> mList;
    private Context mContext;

    public LogisticsListAdapter(Context context, List<Ord01103Resp> list){
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Ord01103Resp getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_logistics_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Ord01103Resp bean = getItem(position);
        if (bean.getExpressNo() != null) {
            holder.tvOrderCode.setText("物流单号："+bean.getExpressNo());
        } else {
            holder.tvOrderCode.setText("物流单号：无");
        }
        if (StringUtils.equals("1", bean.getDeliveryType())) {
            holder.tvDeliverType.setText("送货方式：快递");
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Timestamp ts = new Timestamp(bean.getSendDate().getTime());
        holder.tvSendTime.setText("发货时间："+df.format(ts));
        if (bean.getExpressName() != null) {
            holder.tvCompany.setText("物流公司："+bean.getExpressName());
        } else {
            holder.tvCompany.setText("物流公司：无");
        }
        return convertView;
    }

    static class ViewHolder{

        @BindView(R.id.tv_order_code)
        TextView tvOrderCode;
        @BindView(R.id.tv_deliver_type)
        TextView tvDeliverType;
        @BindView(R.id.tv_send_time)
        TextView tvSendTime;
        @BindView(R.id.tv_company)
        TextView tvCompany;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
