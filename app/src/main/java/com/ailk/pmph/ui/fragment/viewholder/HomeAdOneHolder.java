package com.ailk.pmph.ui.fragment.viewholder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ai.ecp.app.resp.Cms010Resp;
import com.ailk.pmph.R;

import java.util.List;

/**
 * 类注释:图片广告（1张图片）
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.fragment.viewholder
 * 作者: Chrizz
 * 时间: 2016/10/11 15:37
 */
@HomeViewType(ViewType=210)
public class HomeAdOneHolder extends HomeViewHolder {

    private ImageView img0;

    public HomeAdOneHolder(Activity activity, LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity,inflater,viewGroup, R.layout.home_ad_one_layout,R.dimen.home_modey_body_width,R.dimen.home_model_type2_height);
    }

    @Override
    public void initData(Cms010Resp.Model model) {
        super.initData(model);
        List<Cms010Resp.Item> items = model.getItemList();
        setImageViewImg(items.get(0).getImgUrl(),img0,mAQuery,640);
        setOnClickListener(img0,items.get(0).getClickUrl(),items.get(0).getShareKey());
    }

    @Override
    protected void initBodyView(ViewGroup viewGroup) {
        this.img0 = (ImageView) viewGroup.findViewById(R.id.img0);
    }

    public ImageView getImg0() {
        return img0;
    }

    public void setImg0(ImageView img0) {
        this.img0 = img0;
    }
}
