package com.ailk.pmph.ui.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ai.ecp.app.resp.gds.GdsPropBaseInfo;
import com.ai.ecp.app.resp.gds.GdsPropValueBaseInfo;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.ListViewAdapter;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.view.CustomGridView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 类描述：
 * 项目名称： pmph_android
 * Package:  com.ailk.pmph.ui.adapter
 * 创建人：   Nzke
 * 创建时间： 2016/6/12
 * 修改人：   Nzke
 * 修改时间： 2016/6/12
 * 修改备注： 2016/6/12
 * version   v1.0
 */
public class SearchByPropsAdapter extends ListViewAdapter {

    private CustomGridView gridView;
    private int index;
    public SearchByPropsAdapter(Context context, int position,int resource) {
        super(context, resource);
        this.index = position;
    }

    private GetPropValueListener listener;
    public void setListener(GetPropValueListener listener) {
        this.listener = listener;
    }

    @Override
    public void initView(View view, int position, View convertView) {
        TextView propType = (TextView) view.findViewById(R.id.normal_item_type);
        gridView = (CustomGridView) view.findViewById(R.id.normal_item_gridview);
        final ProItemAdapter itemAdapter = new ProItemAdapter(getContext(), index,new ArrayList<GdsPropValueBaseInfo>());
        gridView.setAdapter(itemAdapter);
        final GdsPropBaseInfo data = (GdsPropBaseInfo) getItem(position);
        if (data != null) {
            propType.setText(data.getPropName());
            itemAdapter.addAll(data.getValues());
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < gridView.getChildCount(); i++) {
                    if (i == position) {
                        gridView.getChildAt(i).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.store_prop_bg));
                        if (listener != null) {
                            if (data != null) {
                                listener.onChildClick(position,data.getValues().get(position));
                            }
                        }
                    } else {
                        gridView.getChildAt(i).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.shape_round_textview));
                    }
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void clearPositionBg() {
        for (int i = 0; i < gridView.getChildCount(); i++) {
            gridView.getChildAt(i).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_round_textview));
        }
    }

    //属性项的类
    private class ProItemAdapter extends BaseAdapter {

        private Context context;
        private List<GdsPropValueBaseInfo> list;
        int index;

        public ProItemAdapter(Context context,int dex, List<GdsPropValueBaseInfo> list) {
            this.context = context;
            this.list = list;
            this.index = dex;
        }

        @Override
        public int getCount() {
            return list != null && list.size() > 0 ? list.size() : 0;
        }

        @Override
        public GdsPropValueBaseInfo getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_prop_store, null);
                holder = new ViewHolder();
                holder.propTextView = (TextView) convertView.findViewById(R.id.prop_textView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            GdsPropValueBaseInfo propInfo = getItem(position);
            holder.propTextView.setText(propInfo.getPropValue());
            if (-1 != index) {
                if (index == position) {
                    holder.propTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.store_prop_bg));
                }else {
                    holder.propTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_round_textview));
                }
            }
            return convertView;
        }


        public void addAll(Collection<GdsPropValueBaseInfo> collection) {
            list.addAll(collection);
            notifyDataSetChanged();
        }

        public void clear() {
            list.clear();
            notifyDataSetChanged();
        }

        public void remove(int location) {
            list.remove(location);
            notifyDataSetChanged();
        }

        class ViewHolder {
            TextView propTextView;
        }
    }

    public interface GetPropValueListener {
        void onChildClick(int position,GdsPropValueBaseInfo gdsPropValueBaseInfo);
    }
}
