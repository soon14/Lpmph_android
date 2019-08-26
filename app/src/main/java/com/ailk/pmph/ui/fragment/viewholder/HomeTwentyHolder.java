package com.ailk.pmph.ui.fragment.viewholder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.ai.ecp.app.resp.Cms008Resp;
import com.ai.ecp.app.resp.Cms010Resp;
import com.ailk.pmph.R;

import java.util.List;

/**
 * Type = 20
 * Created by jiangwei on 14-12-2.
 */
@HomeViewType(ViewType=20)
public class HomeTwentyHolder extends HomeViewHolder {

    private ImageView img0;
    private ImageView img1;

    public HomeTwentyHolder(Activity activity, LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity,inflater,viewGroup, R.layout.home_two_layout,R.dimen.home_modey_body_width,R.dimen.home_model_type9_height);
    }

    @Override
    public void initData(Cms010Resp.Model model) {
        super.initData(model);
        List<Cms010Resp.Item> items = model.getItemList();
        setImageViewImg(items.get(0).getImgUrl(),img0,mAQuery,320);
        setImageViewImg(items.get(1).getImgUrl(),img1,mAQuery,320);

        setOnClickListener(img0,items.get(0).getClickUrl(),items.get(0).getShareKey());
        setOnClickListener(img1,items.get(1).getClickUrl(),items.get(1).getShareKey());
    }


    @Override
    protected void initBodyView(ViewGroup viewGroup) {
        this.img0 = (ImageView) viewGroup.findViewById(R.id.img0);
        this.img1 = (ImageView) viewGroup.findViewById(R.id.img1);
    }

    public ImageView getImg0() {
        return img0;
    }

    public void setImg0(ImageView img0) {
        this.img0 = img0;
    }

    public ImageView getImg1() {
        return img1;
    }

    public void setImg1(ImageView img1) {
        this.img1 = img1;
    }
}
