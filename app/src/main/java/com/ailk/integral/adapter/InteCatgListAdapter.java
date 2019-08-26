package com.ailk.integral.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ai.ecp.app.resp.cms.CategoryRespVO;
import com.ailk.integral.fragment.InteSortFragment;
import com.ailk.pmph.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.adp
 * 作者: Chrizz
 * 时间: 2016/5/14 17:12
 */
public class InteCatgListAdapter extends BaseAdapter {

    private List<CategoryRespVO> mList;
    private Context mContext;
    public static int mPosition;

    public InteCatgListAdapter(Context context,List<CategoryRespVO> list){
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public CategoryRespVO getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inte_item_sort_catg_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        mPosition = position;
        final CategoryRespVO bean = getItem(position);
        holder.tvCatgName.setText(bean.getCatgName());
        if (position == InteSortFragment.mPosition) {
            holder.clickLine.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green_9dca85));
            holder.tvCatgName.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
            convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.clickLine.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue_7ecae2));
            holder.tvCatgName.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_eeeeee));
        }
        return convertView;
    }

    static class ViewHolder{

        @BindView(R.id.view_check)
        View clickLine;
        @BindView(R.id.tv_catg_name)
        TextView tvCatgName;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}
