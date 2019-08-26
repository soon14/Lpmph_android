package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ai.ecp.app.resp.cms.InfoRespVO;
import com.ailk.pmph.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/9/30 10:44
 */

public class BulletinListAdapter extends BaseAdapter {

    private List<InfoRespVO> mList;
    private Context mContext;
    private MyOnItemListener mOnItemListener;

    public BulletinListAdapter(Context context, List<InfoRespVO> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void addData(List<InfoRespVO> data) {
        if (mList != null && data != null && !data.isEmpty()) {
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setOnItemListener(MyOnItemListener mOnItemListener) {
        this.mOnItemListener = mOnItemListener;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public InfoRespVO getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bulletin_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final InfoRespVO bean = getItem(position);
        holder.typeTv.setText("【" + bean.getTypeName() + "】");
        holder.titleTv.setText(bean.getInfoTitle());
        holder.dateTv.setText(bean.getPubTime());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.goToDetail(bean);
            }
        });
        return convertView;
    }

    static class ViewHolder{

        @BindView(R.id.tv_bulletin_type)
        TextView typeTv;
        @BindView(R.id.tv_bulletin_title)
        TextView titleTv;
        @BindView(R.id.tv_bulletin_date)
        TextView dateTv;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

    public interface MyOnItemListener {
        void goToDetail(InfoRespVO infoRespVO);
    }

}
