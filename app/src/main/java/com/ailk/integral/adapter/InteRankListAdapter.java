package com.ailk.integral.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.gds.PointGdsDetailBaseInfo;
import com.ai.ecp.app.resp.gds.PointGdsScoreExtRespInfo;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.adp
 * 作者: Chrizz
 * 时间: 2016/5/14 21:23
 */
public class InteRankListAdapter extends BaseAdapter {

    private Context mContext;
    private List<PointGdsDetailBaseInfo> mList;
    private RedirectToDetailInterface redirectToDetailInterface;

    public InteRankListAdapter(Context context, List<PointGdsDetailBaseInfo> list){
        this.mContext = context;
        this.mList = list;
    }

    public void setRedirectToDetailInterface(RedirectToDetailInterface redirectToDetailInterface) {
        this.redirectToDetailInterface = redirectToDetailInterface;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public PointGdsDetailBaseInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inte_item_rank_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        PointGdsDetailBaseInfo bean = getItem(position);
        GlideUtil.loadImg(mContext, bean.getMainPicUrl(), holder.ivGdsImg);
        holder.tvGdsName.setText(bean.getGdsName());
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            holder.tvGdsPrice.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
        } else {
            holder.tvGdsPrice.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        List<PointGdsScoreExtRespInfo> scores =  bean.getScores();
        for (int i = 0; i < scores.size(); i++) {
            final PointGdsScoreExtRespInfo info = scores.get(i);
            if (StringUtils.equals(info.getIfDefault(), "1")) {
                if (info.getScore()!=null && (info.getPrice()==null || info.getPrice()==0)) {
                    holder.tvGdsPrice.setText(info.getScore()+"积分");
                } else if ((info.getScore()==null || info.getScore()==0) && info.getPrice()!=null) {
                    holder.tvGdsPrice.setText(MoneyUtils.GoodListPrice(info.getPrice()));
                } else {
                    holder.tvGdsPrice.setText(info.getScore()+"积分 + "+MoneyUtils.GoodListPrice(info.getPrice()));
                }
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redirectToDetailInterface.redirectToDetail(info);
                }
            });
        }
        return convertView;
    }

    static class ViewHolder{

        @BindView(R.id.iv_gds_img)
        ImageView ivGdsImg;
        @BindView(R.id.tv_gds_name)
        TextView tvGdsName;
        @BindView(R.id.tv_gds_price)
        TextView tvGdsPrice;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    public interface RedirectToDetailInterface {
        void redirectToDetail(PointGdsScoreExtRespInfo info);
    }


}
