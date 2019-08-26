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
 * Type = 20
 * Created by jiangwei on 14-12-2.
 */
@InteHomeViewType(ViewType=20)
public class InteHomeTwentyHolder extends InteHomeViewHolder {

    private ImageView img0;
    private ImageView img1;
    private TextView tvName0;
    private TextView tvName1;
    private TextView tvScore0;
    private TextView tvScore1;

    public InteHomeTwentyHolder(Activity activity, LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity,inflater,viewGroup, R.layout.inte_home_two_layout,R.dimen.home_modey_body_width,R.dimen.home_model_type9_height);
    }

    @Override
    public void initData(Cms101Resp.Model model) {
        super.initData(model);
        List<Cms101Resp.Item> items = model.getItemList();
        setImageViewImg(items.get(0).getImgUrl(),img0,aq,320);
        setImageViewImg(items.get(1).getImgUrl(),img1,aq,320);

        setTextView(items.get(0).getName(),items.get(0).getScore(),items.get(0).getPrice(),tvName0,tvScore0);
        setTextView(items.get(1).getName(),items.get(1).getScore(),items.get(0).getPrice(),tvName1,tvScore1);

        setOnClickListener(img0,items.get(0).getClickUrl(),items.get(0).getShareKey());
        setOnClickListener(img1,items.get(1).getClickUrl(),items.get(1).getShareKey());
    }


    @Override
    protected void initBodyView(ViewGroup viewGroup) {
        this.img0 = (ImageView) viewGroup.findViewById(R.id.img0);
        this.img1 = (ImageView) viewGroup.findViewById(R.id.img1);
        this.tvName0 = (TextView) viewGroup.findViewById(R.id.tv_gds_name0);
        this.tvName1 = (TextView) viewGroup.findViewById(R.id.tv_gds_name1);
        this.tvScore0 = (TextView) viewGroup.findViewById(R.id.tv_gds_score0);
        this.tvScore1 = (TextView) viewGroup.findViewById(R.id.tv_gds_score1);
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

    public TextView getTvName0() {
        return tvName0;
    }

    public void setTvName0(TextView tvName0) {
        this.tvName0 = tvName0;
    }

    public TextView getTvName1() {
        return tvName1;
    }

    public void setTvName1(TextView tvName1) {
        this.tvName1 = tvName1;
    }

    public TextView getTvScore0() {
        return tvScore0;
    }

    public void setTvScore0(TextView tvScore0) {
        this.tvScore0 = tvScore0;
    }

    public TextView getTvScore1() {
        return tvScore1;
    }

    public void setTvScore1(TextView tvScore1) {
        this.tvScore1 = tvScore1;
    }
}
