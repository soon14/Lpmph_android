package com.ailk.integral.holder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ai.ecp.app.resp.Cms008Resp;
import com.ai.ecp.app.resp.Cms101Resp;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.fragment.viewholder.HomeViewHolder;
import com.ailk.pmph.ui.fragment.viewholder.HomeViewType;

import java.util.List;


@HomeViewType(ViewType=61)
public class InteHomeSixtyOneHolder extends InteHomeViewHolder {

    private ImageView img0;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;

    public InteHomeSixtyOneHolder(Activity activity, LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity,inflater,viewGroup, R.layout.home_sixty_one_layout,R.dimen.home_modey_body_width,R.dimen.home_model_type0_height);
    }

    @Override
    public void initData(Cms101Resp.Model model) {
        super.initData(model);
        List<Cms101Resp.Item> items = model.getItemList();

        setImageViewImg(items.get(0).getImgUrl(),img0,aq,214);
        setImageViewImg(items.get(1).getImgUrl(),img1,aq,214);
        setImageViewImg(items.get(2).getImgUrl(),img2,aq,214);
        setImageViewImg(items.get(3).getImgUrl(),img3,aq,214);
        setImageViewImg(items.get(4).getImgUrl(),img4,aq,214);
        setImageViewImg(items.get(5).getImgUrl(),img5,aq,214);

        setOnClickListener(img0,items.get(0).getClickUrl(),items.get(0).getShareKey());
        setOnClickListener(img1,items.get(1).getClickUrl(),items.get(1).getShareKey());
        setOnClickListener(img2,items.get(2).getClickUrl(),items.get(2).getShareKey());
        setOnClickListener(img3,items.get(3).getClickUrl(),items.get(3).getShareKey());
        setOnClickListener(img4,items.get(4).getClickUrl(),items.get(4).getShareKey());
        setOnClickListener(img5,items.get(5).getClickUrl(),items.get(5).getShareKey());
    }


    @Override
    protected void initBodyView(ViewGroup viewGroup) {
        this.img0 = (ImageView) viewGroup.findViewById(R.id.img0);
        this.img1 = (ImageView) viewGroup.findViewById(R.id.img1);
        this.img2 = (ImageView) viewGroup.findViewById(R.id.img2);
        this.img3 = (ImageView) viewGroup.findViewById(R.id.img3);
        this.img4 = (ImageView) viewGroup.findViewById(R.id.img4);
        this.img5 = (ImageView) viewGroup.findViewById(R.id.img5);
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

    public ImageView getImg4() {
        return img4;
    }

    public void setImg4(ImageView img4) {
        this.img4 = img4;
    }

    public ImageView getImg5() {
        return img5;
    }

    public void setImg5(ImageView img5) {
        this.img5 = img5;
    }
}
