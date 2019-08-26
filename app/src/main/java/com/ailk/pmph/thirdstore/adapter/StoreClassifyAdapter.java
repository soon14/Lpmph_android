package com.ailk.pmph.thirdstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.gds.GdsCategoryVO;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.tool.GlideUtil;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类描述：
 * 项目名称： pmph
 * Package:  com.ailk.pmph.ui.adapter
 * 创建人：   Nzke
 * 创建时间： 2016/5/31 15:46
 * 修改人：   Nzke
 * 修改时间： 2016/5/31 15:46
 * 修改备注： 2016/5/31 15:46
 * version   v1.0
 */
public class StoreClassifyAdapter extends BaseAdapter {

    private Context mContext;
    private List<GdsCategoryVO> mList;

    public StoreClassifyAdapter(Context context, List<GdsCategoryVO> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList != null && mList.size() > 0 ? mList.size() : 0;
    }

    @Override
    public GdsCategoryVO getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_store_classify, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GdsCategoryVO gdsCategoryVO = getItem(position);
        GlideUtil.loadImg(mContext,gdsCategoryVO.getMediaURL(),holder.itemClassifyImg);

        holder.itemNameTv.setText(gdsCategoryVO.getCatgName());
        StringBuilder stringBuilder = new StringBuilder();
        if (null != gdsCategoryVO.getChildren() && 0 != gdsCategoryVO.getChildren().size()) {
            for (int i = 0; i < gdsCategoryVO.getChildren().size(); i++) {
                stringBuilder.append(gdsCategoryVO.getChildren().get(i).getCatgName());
                if (i != gdsCategoryVO.getChildren().size()-1) {
                    stringBuilder.append("/");
                }
            }
        }
        holder.itemClassifyNameTv.setText(stringBuilder);
        return convertView;
    }


    public void addAll(Collection<GdsCategoryVO> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void remove(int location) {
        mList.remove(location);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.item_classify_img)
        ImageView itemClassifyImg;
        @BindView(R.id.item_name_tv)
        TextView itemNameTv;
        @BindView(R.id.item_classify_name_tv)
        TextView itemClassifyNameTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
