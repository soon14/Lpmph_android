package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.cms.CategoryRespVO;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.activity.SearchResultActivity;
import com.ailk.pmph.ui.view.CustomGridView;
import com.ailk.pmph.utils.Constant;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.adp
 * 作者: Chrizz
 * 时间: 2016/5/14 17:40
 */
public class SortChildListAdapter extends BaseExpandableListAdapter {

    private List<CategoryRespVO> mList;
    private Map<String, List<CategoryRespVO>> mItemList;
    private Context mContext;

    public SortChildListAdapter(Context context, List<CategoryRespVO> list, Map<String, List<CategoryRespVO>> itemList){
        this.mContext = context;
        this.mList = list;
        this.mItemList = itemList;
    }

    @Override
    public int getGroupCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mItemList.get(String.valueOf(mList.get(groupPosition).getId())).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupHolder groupHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_child_sort_list, parent, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        if (isExpanded) {
            groupHolder.toggleIv.setImageResource(R.drawable.icon_sort_close);
        } else {
            groupHolder.toggleIv.setImageResource(R.drawable.icon_sort_expand);
        }
        final CategoryRespVO categoryRespVO = (CategoryRespVO) getGroup(groupPosition);
        if (categoryRespVO != null) {
            GlideUtil.loadImg(mContext,categoryRespVO.getVfsUrl(),groupHolder.sortIv);
            groupHolder.sortNameTv.setText(categoryRespVO.getCatgName());
            groupHolder.sortNameTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String catgName = categoryRespVO.getCatgName();
                    String catgCode = categoryRespVO.getCatgCode();
                    Intent intent = new Intent(mContext, SearchResultActivity.class);
                    if (StringUtils.isNotEmpty(catgCode)) {
                        intent.putExtra(Constant.SHOP_CATG_CODE, catgCode);
                        intent.putExtra(Constant.SHOP_CATG_NAME, catgName);
                    }
                    mContext.startActivity(intent);
                }
            });
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder childHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_child_sort_list_gridview, parent, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        List<CategoryRespVO> list = mItemList.get(String.valueOf(mList.get(groupPosition).getId()));
        if (list != null && list.size() != 0) {
            CatgGriViewAdapter adapter = new CatgGriViewAdapter(mContext, list);
            childHolder.catgGv.setAdapter(adapter);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupHolder{
        @BindView(R.id.iv_child_sort_img)
        ImageView sortIv;
        @BindView(R.id.tv_child_sort_name)
        TextView sortNameTv;
        @BindView(R.id.iv_toggle)
        ImageView toggleIv;

        public GroupHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    static class ChildHolder{
        @BindView(R.id.gv_catg)
        CustomGridView catgGv;

        public ChildHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}
