package com.ailk.pmph.ui.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ai.ecp.goods.dubbo.dto.GdsPropRespDTO;
import com.ai.ecp.goods.dubbo.dto.GdsPropValueRespDTO;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.view.CustomGridView;
import com.ailk.pmph.utils.ListViewAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/11/18
 */

public class SearchBySortPropsAdapter extends ListViewAdapter {

    private GdsPropValueListener mListener;

    public SearchBySortPropsAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void setListener(GdsPropValueListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void initView(View view, int position, View convertView) {
        final TextView propName = (TextView) view.findViewById(R.id.tv_prop_name);
        TextView propImg = (TextView) view.findViewById(R.id.tv_prop_img);
        final CustomGridView gridView = (CustomGridView) view.findViewById(R.id.gv_prop_list);
        final PropItemAdapter itemAdapter = new PropItemAdapter(getContext(), new ArrayList<GdsPropValueRespDTO>());
        gridView.setAdapter(itemAdapter);
        final GdsPropRespDTO data = (GdsPropRespDTO) getItem(position);
        if (data != null) {
            propName.setText(data.getPropName());
            itemAdapter.addAll(data.getValues());
            propImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.isNameIsChecked()) {
                        ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_down_black,0);
                    } else {
                        ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_right_black,0);
                    }
                    itemAdapter.notifyDataSetChangedByImg(data.isNameIsChecked(), data.getValues());
                    data.setNameIsChecked(!data.isNameIsChecked());
                }
            });

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    data.getValues().get(position).setChecked(!data.getValues().get(position).isChecked());
                    for (int i = 0; i < data.getValues().size(); i++) {
                        if (i == position) {
                            continue;
                        }
                        data.getValues().get(i).setChecked(false);
                    }
                    for (int i = 0; i < gridView.getChildCount(); i++) {
                        if (i == position) {
                            gridView.getChildAt(i).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.store_prop_bg));
                            if (mListener != null) {
                                mListener.onChildClick(position,data.getValues().get(position));
                            }
                        } else {
                            gridView.getChildAt(i).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.shape_round_textview));
                        }
                    }
                }
            });
        }

    }

    //属性项的类
    private class PropItemAdapter extends BaseAdapter {

        private Context context;
        private List<GdsPropValueRespDTO> list;

        public PropItemAdapter(Context context, List<GdsPropValueRespDTO> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list != null && list.size() > 0 ? list.size() : 0;
        }

        @Override
        public GdsPropValueRespDTO getItem(int position) {
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
            GdsPropValueRespDTO propInfo = getItem(position);
            holder.propTextView.setText(propInfo.getPropValue());
            if (propInfo.isChecked()) {
                holder.propTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.store_prop_bg));
            } else {
                holder.propTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_round_textview));
            }
            return convertView;
        }


        public void addAll(Collection<GdsPropValueRespDTO> collection) {
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

        public void notifyDataSetChangedByImg(boolean isUnfold, List<GdsPropValueRespDTO> tempData) {
            if (tempData == null || 0 == tempData.size()) {
                return;
            }
            list.clear();
            // 如果是展开的，则加入全部data，反之则只显示3条
            if (isUnfold) {
                list.addAll(tempData);
            } else {
                list.removeAll(tempData);
            }
            notifyDataSetChanged();
        }

        class ViewHolder {
            TextView propTextView;
        }
    }

    public interface GdsPropValueListener {
        void onChildClick(int position,GdsPropValueRespDTO bean);
    }

}
