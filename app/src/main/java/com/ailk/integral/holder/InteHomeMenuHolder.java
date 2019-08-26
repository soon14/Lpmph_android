package com.ailk.integral.holder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.Cms008Resp;
import com.ai.ecp.app.resp.Cms101Resp;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.ui.fragment.viewholder.HomeViewHolder;
import com.ailk.pmph.ui.fragment.viewholder.HomeViewType;

import java.util.List;

/**
 * Type = 1
 * Created by jiangwei on 14-12-2.
 */
@InteHomeViewType(ViewType=310)
public class InteHomeMenuHolder extends InteHomeViewHolder {

    private final static int ITEMCOUNT = 4;
    private List<Cms101Resp.Item> items;
    private ImageView[] imgs;
    private TextView[] texts;

    public InteHomeMenuHolder(Activity activity, LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity,inflater,viewGroup, R.layout.inte_home_menu_layout,R.dimen.home_modey_body_width,R.dimen.home_model_type1_height);
    }

    @Override
    public void initData(Cms101Resp.Model model) {
        super.initData(model);
        items = model.getItemList();
        int maxCount = Math.min(items.size(), ITEMCOUNT);
        for(int i = 0; i < maxCount; i++) {
            setImageViewImg(items.get(i).getImgUrl(), imgs[i], aq, 70);
            aq.id(texts[i]).text(items.get(i).getName());
            setOnClickListener(imgs[i], items.get(i).getClickUrl(),items.get(i).getShareKey());
        }
    }


    @Override
    protected void initBodyView(ViewGroup viewGroup) {
        this.model_body.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if(imgs == null) {
            imgs = new ImageView[ITEMCOUNT];
            texts = new TextView[ITEMCOUNT];
        }
        imgs[0] = (ImageView) viewGroup.findViewById(R.id.item0).findViewById(R.id.img);
        imgs[1] = (ImageView) viewGroup.findViewById(R.id.item1).findViewById(R.id.img);
        imgs[2] = (ImageView) viewGroup.findViewById(R.id.item2).findViewById(R.id.img);
        imgs[3] = (ImageView) viewGroup.findViewById(R.id.item3).findViewById(R.id.img);
//        imgs[4] = (ImageView) viewGroup.findViewById(R.id.item4).findViewById(R.id.img);
//        imgs[5] = (ImageView) viewGroup.findViewById(R.id.item5).findViewById(R.id.img);
//        imgs[6] = (ImageView) viewGroup.findViewById(R.id.item6).findViewById(R.id.img);
//        imgs[7] = (ImageView) viewGroup.findViewById(R.id.item7).findViewById(R.id.img);

        texts[0] = (TextView) viewGroup.findViewById(R.id.item0).findViewById(R.id.text);
        texts[1] = (TextView) viewGroup.findViewById(R.id.item1).findViewById(R.id.text);
        texts[2] = (TextView) viewGroup.findViewById(R.id.item2).findViewById(R.id.text);
        texts[3] = (TextView) viewGroup.findViewById(R.id.item3).findViewById(R.id.text);
//        texts[4] = (TextView) viewGroup.findViewById(R.id.item6).findViewById(R.id.text);
//        texts[5] = (TextView) viewGroup.findViewById(R.id.item7).findViewById(R.id.text);
//        texts[6] = (TextView) viewGroup.findViewById(R.id.item7).findViewById(R.id.text);
//        texts[7] = (TextView) viewGroup.findViewById(R.id.item7).findViewById(R.id.text);
    }

}
