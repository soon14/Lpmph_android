package com.ailk.integral.holder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ai.ecp.app.resp.Cms010Resp;
import com.ai.ecp.app.resp.Cms101Resp;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.fragment.viewholder.HomeViewHolder;
import com.ailk.pmph.ui.fragment.viewholder.HomeViewType;

import java.util.List;

/**
 * 类注释:广告图片（4张）
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.fragment.viewholder
 * 作者: Chrizz
 * 时间: 2016/10/11 15:54
 */
@InteHomeViewType(ViewType=40)
public class InteHomeFortyHolder extends InteHomeViewHolder {

    private ImageView img0;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;

    public InteHomeFortyHolder(Activity activity, LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity,inflater,viewGroup, R.layout.home_forty_layout, R.dimen.home_modey_body_width, R.dimen.home_model_type10_height);
    }

    @Override
    protected void initBodyView(ViewGroup viewGroup) {
        this.img0 = (ImageView) viewGroup.findViewById(R.id.img0);
        this.img1 = (ImageView) viewGroup.findViewById(R.id.img1);
        this.img2 = (ImageView) viewGroup.findViewById(R.id.img2);
        this.img3 = (ImageView) viewGroup.findViewById(R.id.img3);
    }

    @Override
    public void initData(Cms101Resp.Model model) {
        super.initData(model);
        List<Cms101Resp.Item> items = model.getItemList();
        setImageViewImg(items.get(0).getImgUrl(),img0,aq,160);
        setImageViewImg(items.get(1).getImgUrl(),img1,aq,160);
        setImageViewImg(items.get(2).getImgUrl(),img2,aq,160);
        setImageViewImg(items.get(3).getImgUrl(),img3,aq,160);
        setOnClickListener(img0,items.get(0).getClickUrl(),items.get(0).getShareKey());
        setOnClickListener(img1,items.get(1).getClickUrl(),items.get(1).getShareKey());
        setOnClickListener(img2,items.get(2).getClickUrl(),items.get(2).getShareKey());
        setOnClickListener(img3,items.get(3).getClickUrl(),items.get(3).getShareKey());
    }

}
