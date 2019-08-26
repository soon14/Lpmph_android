package com.ailk.integral.holder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.Cms008Resp;
import com.ai.ecp.app.resp.Cms101Resp;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.fragment.viewholder.HomeViewHolder;
import com.ailk.pmph.ui.fragment.viewholder.HomeViewType;

import java.util.List;


/**
 * type = 10
 * Created by jiangwei on 14-12-2.
 */
@InteHomeViewType(ViewType=10)
public class InteHomeTenHolder extends InteHomeViewHolder {

    private ImageView img0;
    private TextView tvName;
    private TextView tvScore;

    public InteHomeTenHolder(Activity activity, LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity,inflater,viewGroup, R.layout.inte_home_one_layout,R.dimen.home_modey_body_width,R.dimen.home_model_type9_height);

    }

    @Override
    public void initData(Cms101Resp.Model model) {
        super.initData(model);
        List<Cms101Resp.Item> items = model.getItemList();
        setImageViewImg(items.get(0).getImgUrl(),img0,aq,640);
        setTextView(items.get(0).getName(), items.get(0).getScore(),items.get(0).getPrice(), tvName, tvScore);
        setOnClickListener(img0,items.get(0).getClickUrl(),items.get(0).getShareKey());
    }


    @Override
    protected void initBodyView(ViewGroup viewGroup) {
        this.img0 = (ImageView) viewGroup.findViewById(R.id.img0);
        this.img0.setScaleType(ImageView.ScaleType.FIT_XY);
        this.tvName = (TextView) viewGroup.findViewById(R.id.tv_gds_name);
        this.tvScore = (TextView) viewGroup.findViewById(R.id.tv_gds_score);
    }

    public ImageView getImg0() {
        return img0;
    }

    public void setImg0(ImageView img0) {
        this.img0 = img0;
    }

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public TextView getTvScore() {
        return tvScore;
    }

    public void setTvScore(TextView tvScore) {
        this.tvScore = tvScore;
    }
}
