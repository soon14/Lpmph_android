package com.ailk.pmph.thirdstore.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ai.ecp.app.resp.cms.AdvertiseRespVO;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.PromotionParseUtil;
import com.ailk.tool.GlideUtil;

import java.util.Collection;
import java.util.List;

/**
 * 类描述：
 * 项目名称： pmph_android
 * Package:  com.ailk.pmph.thirdstore.adapter
 * 创建人：   Nzke
 * 创建时间： 2016/6/13
 * 修改人：   Nzke
 * 修改时间： 2016/6/13
 * 修改备注： 2016/6/13
 * version   v1.0
 */
public class StorePicAdapter extends PagerAdapter {

    private Context mContext;
    private List<AdvertiseRespVO> mList;
    private SparseArray<ImageView> mListViews;

    public StorePicAdapter(Context context,List<AdvertiseRespVO> list) {
        this.mContext = context;
        this.mList = list;
        this.mListViews = new SparseArray<>();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mListViews.get(position).setImageBitmap(null);
        container.removeView(mListViews.get(position));//删除页卡
        mListViews.remove(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = mListViews.get(position);
        if (imageView == null) {
            imageView = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.maskable_image_view, container, false);
            mListViews.put(position, imageView);
        }
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        GlideUtil.loadImg(mContext,mList.get(position).getVfsUrl(),imageView);
        container.addView(imageView);//添加页卡
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromotionParseUtil.parsePromotionUrl(mContext,mList.get(position).getLinkUrl(),true,false,null);
            }
        });
        return imageView;
    }

    @Override
    public int getCount() {
        return null != mList && 0 != mList.size() ? mList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void addAll(Collection<AdvertiseRespVO> collection) {
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
}


