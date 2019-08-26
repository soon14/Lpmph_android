package com.ailk.pmph.ui.fragment.viewholder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ai.ecp.app.resp.Cms008Resp;
import com.ai.ecp.app.resp.Cms010Resp;
import com.ailk.pmph.R;

import java.util.List;


@HomeViewType(ViewType=41)
public class HomeFortyOneHolder extends HomeViewHolder {

    private ImageView img0;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;

    public HomeFortyOneHolder(Activity activity, LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity,inflater,viewGroup, R.layout.home_forty_one_layout,R.dimen.home_modey_body_width,R.dimen.home_model_type9_height);
    }

    @Override
    public void initData(Cms010Resp.Model model) {
        super.initData(model);
        List<Cms010Resp.Item> items = model.getItemList();

        setImageViewImg(items.get(0).getImgUrl(),img0,mAQuery,320);
        setImageViewImg(items.get(1).getImgUrl(),img1,mAQuery,320);
        setImageViewImg(items.get(2).getImgUrl(),img2,mAQuery,160);
        setImageViewImg(items.get(3).getImgUrl(),img3,mAQuery,160);

        setOnClickListener(img0,items.get(0).getClickUrl(),items.get(0).getShareKey());
        setOnClickListener(img1,items.get(1).getClickUrl(),items.get(1).getShareKey());
        setOnClickListener(img2,items.get(2).getClickUrl(),items.get(2).getShareKey());
        setOnClickListener(img2,items.get(3).getClickUrl(),items.get(3).getShareKey());
    }


    @Override
    protected void initBodyView(ViewGroup viewGroup) {
        this.img0 = (ImageView) viewGroup.findViewById(R.id.img0);
        this.img1 = (ImageView) viewGroup.findViewById(R.id.img1);
        this.img2 = (ImageView) viewGroup.findViewById(R.id.img2);
        this.img3 = (ImageView) viewGroup.findViewById(R.id.img3);
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

    public ImageView getImg3() {
        return img3;
    }

    public void setImg3(ImageView img3) {
        this.img3 = img3;
    }
}
