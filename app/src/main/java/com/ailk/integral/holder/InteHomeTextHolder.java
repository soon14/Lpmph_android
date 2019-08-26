package com.ailk.integral.holder;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ai.ecp.app.resp.Cms101Resp;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;

import org.apache.commons.lang.StringUtils;

import java.util.List;


/**
 * Type = 8
 * Created by jiangwei on 14-12-2.
 */
@InteHomeViewType(ViewType=610)
public class InteHomeTextHolder extends InteHomeViewHolder {

    private TextView tvScore;
    private TextView tvRecord;
    private List<Cms101Resp.Item> items;

    public InteHomeTextHolder(Activity activity, LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity,inflater,viewGroup, R.layout.inte_home_text_layout,R.dimen.home_modey_body_width,R.dimen.home_model_type2_height);
    }

    @Override
    protected void initBodyView(ViewGroup viewGroup) {
        tvScore = (TextView) viewGroup.findViewById(R.id.tv_score);
        tvRecord = (TextView) viewGroup.findViewById(R.id.tv_record);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvScore.setTextColor(ContextCompat.getColor(mConText, R.color.orange_ff6a00));
        } else {
            tvScore.setTextColor(ContextCompat.getColor(mConText, R.color.red));
        }
    }

    @Override
    public void initData(Cms101Resp.Model model) {
        super.initData(model);
        this.items = model.getItemList();
        if (!AppContext.isLogin) {
            tvScore.setText("0");
            tvRecord.setText(items.get(1).getName());
        } else {
            if (items.get(0).getScore()==null) {
                tvScore.setText("0");
            } else {
                tvScore.setText(String.valueOf(items.get(0).getScore()));
            }
            tvRecord.setText(items.get(1).getName());
            redirectTo(tvRecord);
        }
    }


    public TextView getTvScore() {
        return tvScore;
    }

    public void setTvScore(TextView tvScore) {
        this.tvScore = tvScore;
    }

    public TextView getTvRecord() {
        return tvRecord;
    }

    public void setTvRecord(TextView tvRecord) {
        this.tvRecord = tvRecord;
    }
}
