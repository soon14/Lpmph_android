package com.ailk.integral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.cms.CategoryRespVO;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.tool.GlideUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.adp
 * 作者: Chrizz
 * 时间: 2016/5/14 17:40
 */
public class InteChildCatgListAdapter extends BaseAdapter {

    private List<CategoryRespVO> mList;
    private Context mContext;

    public InteChildCatgListAdapter(Context context,List<CategoryRespVO> list){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inte_item_sort_child_catg_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final CategoryRespVO bean = getItem(position);
        GlideUtil.loadImg(mContext, bean.getVfsUrl(), holder.ivCatgImg);
        holder.tvCatgName.setText(bean.getCatgName());
        return convertView;
    }

    static class ViewHolder{

        @BindView(R.id.iv_child_catg_img)
        ImageView ivCatgImg;
        @BindView(R.id.tv_child_catg_name)
        TextView tvCatgName;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}
