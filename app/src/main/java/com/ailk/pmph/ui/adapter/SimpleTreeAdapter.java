package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ailk.pmph.R;
import com.ailk.treeview.Node;
import com.ailk.treeview.TreeListViewAdapter;
import com.ailk.pmph.utils.LogUtil;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/11/18
 */

public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T> {

    public SimpleTreeAdapter(ListView mTree, Context context, List<T> datas,
                             int defaultExpandLevel) throws IllegalArgumentException, IllegalAccessException
    {
        super(mTree, context, datas, defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node node , int position, View convertView, ViewGroup parent)
    {

        ViewHolder viewHolder ;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_base_sort_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.label = (TextView) convertView.findViewById(R.id.tv_catg_name);
            viewHolder.check = (ImageView) convertView.findViewById(R.id.iv_check);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.label.setText(node.getName());

        if (node.isChecked()) {
            viewHolder.label.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            viewHolder.check.setVisibility(View.VISIBLE);
        } else {
            viewHolder.label.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            viewHolder.check.setVisibility(View.GONE);
        }

        if (viewHolder.check.getVisibility() == View.VISIBLE) {
            viewHolder.label.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            viewHolder.check.setVisibility(View.GONE);
        }

        return convertView;
    }

    private final class ViewHolder
    {
        TextView label;
        ImageView check;
    }

}
