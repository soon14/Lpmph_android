package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ailk.pmph.R;
import com.ailk.pmph.utils.LogUtil;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/6/29 17:40
 */
public class InvoiceAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mInvoiceConList;
    private CheckDetailInterface checkDetailInterface;
    private int mSelectedPosition = -1;

    public InvoiceAdapter(Context context, List<String> invoiceConList) {
        this.mContext = context;
        this.mInvoiceConList = invoiceConList;
    }

    public void setSelectedItem (int itemPosition) {
        mSelectedPosition = itemPosition;
        notifyDataSetChanged();
    }

    public void setCheckDetailInterface(CheckDetailInterface checkDetailInterface) {
        this.checkDetailInterface = checkDetailInterface;
    }

    @Override
    public int getCount() {
        return mInvoiceConList == null ? 0 : mInvoiceConList.size();
    }

    @Override
    public String getItem(int position) {
        return mInvoiceConList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bill_content_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String detail = getItem(position);
        holder.tvDetail.setText(detail);
        if (mSelectedPosition == position) {
            holder.rbDetail.setChecked(true);
        } else {
            holder.rbDetail.setChecked(false);
        }
        holder.rbDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedItem(position);
                checkDetailInterface.checkDetail(detail, ((RadioButton) v).isChecked());
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedItem(position);
                if (holder.rbDetail.isChecked()) {
                    holder.rbDetail.setChecked(false);
                } else {
                    holder.rbDetail.setChecked(true);
                }
                checkDetailInterface.checkDetail(detail, holder.rbDetail.isChecked());
            }
        });
        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.rb_detail)
        RadioButton rbDetail;
        @BindView(R.id.tv_detail)
        TextView tvDetail;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface CheckDetailInterface {
        void checkDetail(String detail, boolean isChecked);
    }

}
