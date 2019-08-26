package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ai.ecp.app.resp.cms.CategoryRespVO;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.activity.SearchResultActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.LogUtil;

import org.apache.commons.lang.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/10/4 11:04
 */

public class CatgGriViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<CategoryRespVO> mList;

    public CatgGriViewAdapter(Context context, List<CategoryRespVO> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_catg, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CategoryRespVO bean = getItem(position);
        holder.catgTv.setText(bean.getCatgName());
        holder.catgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String catgName = bean.getCatgName();
                String catgCode = bean.getCatgCode();
                Intent intent = new Intent(mContext, SearchResultActivity.class);
                if (StringUtils.isNotEmpty(catgCode)) {
                    intent.putExtra(Constant.SHOP_CATG_CODE, catgCode);
                    intent.putExtra(Constant.SHOP_CATG_NAME, catgName);
                }
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.prop_textview)
        TextView catgTv;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }

    }

}
