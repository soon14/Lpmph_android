package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.BaseAreaAdminRespDTO;
import com.ailk.pmph.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/4/13 14:41
 */
public class AreaListAdapter extends BaseExpandableListAdapter {

    private List<BaseAreaAdminRespDTO> mProvinceList = new ArrayList<>();
    private Map<String, List<BaseAreaAdminRespDTO>> mCityMap = new HashMap<>();
    private Context mContext;
    private ClickCityInterface clickCityInterface;

    public AreaListAdapter(Context context, List<BaseAreaAdminRespDTO> provinceList){
        this.mContext = context;
        this.mProvinceList = provinceList;
    }

    public void setCityMap(Map<String, List<BaseAreaAdminRespDTO>> cityMap) {
        this.mCityMap = cityMap;
        notifyDataSetChanged();
    }

    public void setClickCityInterface(ClickCityInterface clickCityInterface) {
        this.clickCityInterface = clickCityInterface;
    }

    @Override
    public int getGroupCount() {
        return mProvinceList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mCityMap.get(mProvinceList.get(groupPosition).getAreaCode()).size();
    }

    @Override
    public BaseAreaAdminRespDTO getGroup(int groupPosition) {
        return mProvinceList.get(groupPosition);
    }

    @Override
    public BaseAreaAdminRespDTO getChild(int groupPosition, int childPosition) {
        return mCityMap.get(mProvinceList.get(groupPosition).getAreaCode()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (null==convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group_province, parent, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        BaseAreaAdminRespDTO province = getGroup(groupPosition);
        if (province != null) {
            groupHolder.tvProvince.setText(province.getAreaName());
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder;
        if (null==convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_child_city, parent, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        final BaseAreaAdminRespDTO city = getChild(groupPosition, childPosition);
        childHolder.tvCity.setText(city.getAreaName());
        childHolder.llCityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCityInterface.clickCity(city);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupHolder{
        @BindView(R.id.tv_province)
        TextView tvProvince;

        public GroupHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    static class ChildHolder{
        @BindView(R.id.ll_city_layout)
        LinearLayout llCityLayout;
        @BindView(R.id.tv_city)
        TextView tvCity;

        public ChildHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

    public interface ClickCityInterface {
        void clickCity(BaseAreaAdminRespDTO city);
    }

}
