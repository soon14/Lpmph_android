package com.ailk.pmph.ui.fragment.viewholder;

import android.app.Activity;
import android.graphics.Paint;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.Cms010Resp;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.MoneyUtils;

import java.util.List;

/**
 * 类注释:限时秒杀
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.fragment.viewholder
 * 作者: Chrizz
 * 时间: 2016/11/2
 */
@HomeViewType(ViewType=132)
public class HomeSecondKillHolder extends HomeViewHolder {

    private ImageView img0;
    private ImageView img1;
    private ImageView img2;

    private TextView killPrice0;
    private TextView killPrice1;
    private TextView killPrice2;

    public HomeSecondKillHolder(Activity activity, LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity,inflater,viewGroup, R.layout.home_second_kill_layout,R.dimen.home_modey_body_width,R.dimen.home_model_type9_height);
    }

    @Override
    public void initData(Cms010Resp.Model model) {
        super.initData(model);
        List<Cms010Resp.Item> items = model.getItemList();
        setImageViewImg(items.get(0).getImgUrl(),img0,mAQuery,210);
        setImageViewImg(items.get(1).getImgUrl(),img1,mAQuery,210);
        setImageViewImg(items.get(2).getImgUrl(),img2,mAQuery,210);

        killPrice0.setText(MoneyUtils.GoodListPrice(items.get(0).getDiscountPrice()));
        killPrice1.setText(MoneyUtils.GoodListPrice(items.get(1).getDiscountPrice()));
        killPrice2.setText(MoneyUtils.GoodListPrice(items.get(2).getDiscountPrice()));

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

        this.killPrice0 = (TextView) viewGroup.findViewById(R.id.killPrice0);
        this.killPrice1 = (TextView) viewGroup.findViewById(R.id.killPrice1);
        this.killPrice2 = (TextView) viewGroup.findViewById(R.id.killPrice2);
    }

}
