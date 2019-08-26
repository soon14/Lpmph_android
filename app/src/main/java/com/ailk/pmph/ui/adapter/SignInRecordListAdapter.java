package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord11201Resp;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/10/6 14:16
 */

public class SignInRecordListAdapter extends BaseAdapter {

    private List<Ord11201Resp> mList;
    private Context mContext;
    private RedirectToDetailInterface redirectToDetailInterface;

    public SignInRecordListAdapter(Context context, List<Ord11201Resp> list) {
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
    public Ord11201Resp getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sign_in_record_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Ord11201Resp bean = getItem(position);
        GlideUtil.loadImg(mContext,bean.getPicUrl(),holder.ivGdsImg);
        holder.tvGdsName.setText(bean.getGdsName());
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            holder.tvGdsPrice.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
        } else {
            holder.tvGdsPrice.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        if (bean.getBuyPrice() == null || bean.getBuyPrice() == 0 ) {
            holder.tvGdsPrice.setText(bean.getScore() + "积分");
        } else {
            holder.tvGdsPrice.setText(bean.getScore() + "积分 + " + MoneyUtils.GoodListPrice(bean.getBuyPrice()));
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp orderTime = new Timestamp(bean.getOrderTime().getTime());
        holder.tvGdsDate.setText(df.format(orderTime));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToDetailInterface.redirectToDetail(bean);
            }
        });
        return convertView;
    }

    static class ViewHolder{

        @BindView(R.id.iv_gds_img)
        ImageView ivGdsImg;
        @BindView(R.id.tv_gds_name)
        TextView tvGdsName;
        @BindView(R.id.tv_gds_price)
        TextView tvGdsPrice;
        @BindView(R.id.tv_recode_date)
        TextView tvGdsDate;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    public interface RedirectToDetailInterface {
        void redirectToDetail(Ord11201Resp info);
    }

}
