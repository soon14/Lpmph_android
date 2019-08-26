package com.ailk.pmph.ui.fragment.viewholder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.Cms008Resp;
import com.ai.ecp.app.resp.Cms010Resp;
import com.ailk.pmph.R;


/**
 * Type = 8
 * Created by jiangwei on 14-12-2.
 */
@HomeViewType(ViewType=8)
public class HomeFooterHolder extends HomeViewHolder {

    private ImageView img_bg;
    private TextView text;

    public HomeFooterHolder(Activity activity,LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity,inflater,viewGroup, R.layout.home_footer_layout,R.dimen.home_modey_body_width,R.dimen.home_model_type8_height);
    }

    @Override
    protected void initBodyView(ViewGroup viewGroup) {
        img_bg = (ImageView) viewGroup.findViewById(R.id.img_bg);
        text = (TextView) viewGroup.findViewById(R.id.text0);
    }

    @Override
    public void initData(Cms010Resp.Model model) {
        super.initData(model);
        //TODO-- 填充模块

    }


    public ImageView getImg_bg() {
        return img_bg;
    }

    public void setImg_bg(ImageView img_bg) {
        this.img_bg = img_bg;
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }
}
