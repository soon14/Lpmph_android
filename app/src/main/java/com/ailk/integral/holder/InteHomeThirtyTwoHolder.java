package com.ailk.integral.holder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ai.ecp.app.resp.Cms008Resp;
import com.ai.ecp.app.resp.Cms101Resp;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.fragment.viewholder.HomeViewHolder;
import com.ailk.pmph.ui.fragment.viewholder.HomeViewType;

import java.util.List;

/**
 * Type = 32
 * Created by jiangwei on 14-12-2.
 */
@InteHomeViewType(ViewType=32)
public class InteHomeThirtyTwoHolder extends InteHomeViewHolder {

    private ImageView img0;
    private ImageView img1;
    private ImageView img2;

    public InteHomeThirtyTwoHolder(Activity activity, LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity,inflater,viewGroup, R.layout.home_three_layout,R.dimen.home_modey_body_width,R.dimen.home_model_type9_height);
    }

    @Override
    public void initData(Cms101Resp.Model model) {
        super.initData(model);
        //TODO-- 填充模块
        List<Cms101Resp.Item> items = model.getItemList();
        setImageViewImg(items.get(0).getImgUrl(),img0,aq,210);
        setImageViewImg(items.get(1).getImgUrl(),img1,aq,210);
        setImageViewImg(items.get(2).getImgUrl(),img2,aq,210);

        setOnClickListener(img0,items.get(0).getClickUrl(),items.get(0).getShareKey());
        setOnClickListener(img1,items.get(1).getClickUrl(),items.get(1).getShareKey());
        setOnClickListener(img2,items.get(2).getClickUrl(),items.get(2).getShareKey());
    }


    @Override
    protected void initBodyView(ViewGroup viewGroup) {
        this.model_body.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.img0 = (ImageView) viewGroup.findViewById(R.id.img0);
        this.img1 = (ImageView) viewGroup.findViewById(R.id.img1);
        this.img2 = (ImageView) viewGroup.findViewById(R.id.img2);
        this.img0.setScaleType(ImageView.ScaleType.FIT_XY);
        this.img1.setScaleType(ImageView.ScaleType.FIT_XY);
        this.img2.setScaleType(ImageView.ScaleType.FIT_XY);

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

    public ImageView getImg2() {
        return img2;
    }

    public void setImg2(ImageView img2) {
        this.img2 = img2;
    }
}

