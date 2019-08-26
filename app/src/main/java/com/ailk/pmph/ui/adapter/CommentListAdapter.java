package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.gds.GdsEvalBaseInfo;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/4/15 21:40
 */
public class CommentListAdapter extends BaseAdapter {

    private List<GdsEvalBaseInfo> mGdsEvalRespList;
    private Context mContext;
    private String status;
    private GoCommentInterface goCommentInterface;
    private RedirectToShopDetailInterface redirectToShopDetailInterface;

    public CommentListAdapter(Context context, List<GdsEvalBaseInfo> gdsEvalRespList){
        this.mContext = context;
        this.mGdsEvalRespList = gdsEvalRespList;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setGoCommentInterface(GoCommentInterface goCommentInterface) {
        this.goCommentInterface = goCommentInterface;
    }

    public void setRedirectToShopDetailInterface(RedirectToShopDetailInterface redirectToShopDetailInterface) {
        this.redirectToShopDetailInterface = redirectToShopDetailInterface;
    }

    public void addData(List<GdsEvalBaseInfo> data) {
        if (mGdsEvalRespList != null && data != null && !data.isEmpty()) {
            mGdsEvalRespList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mGdsEvalRespList == null ? 0 : mGdsEvalRespList.size();
    }

    @Override
    public GdsEvalBaseInfo getItem(int position) {
        return mGdsEvalRespList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comment_center_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final GdsEvalBaseInfo bean = getItem(position);
        if (StringUtils.equals(status, "0")) {//未评价
            holder.tvDetail.setVisibility(View.GONE);
            holder.tvMsg.setVisibility(View.VISIBLE);
            holder.tvComment.setVisibility(View.VISIBLE);

            GlideUtil.loadImg(mContext, bean.getUrl(), holder.ivGdsImg);
            holder.tvGdsName.setText(bean.getGdsName());
            holder.tvMsg.setText(bean.getSkuProps());
            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                holder.tvComment.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ff6a00));
            } else {
                holder.tvComment.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            }
            holder.tvComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goCommentInterface.goComment(bean);
                }
            });
        }
        if (StringUtils.equals(status, "1")) {//已评价
            holder.tvDetail.setVisibility(View.VISIBLE);
            holder.tvMsg.setVisibility(View.GONE);
            holder.tvComment.setVisibility(View.GONE);

            GlideUtil.loadImg(mContext, bean.getUrl(), holder.ivGdsImg);
            holder.tvGdsName.setText(bean.getGdsName());
            holder.tvDetail.setText(bean.getDetail());
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToShopDetailInterface.redirectToShopDetail(bean.getSkuId(), bean.getGdsId());
            }
        });
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.iv_gds_img)
        ImageView ivGdsImg;
        @BindView(R.id.tv_good_name)
        TextView tvGdsName;
        @BindView(R.id.tv_msg)
        TextView tvMsg;
        @BindView(R.id.tv_content)
        TextView tvDetail;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    public interface GoCommentInterface{
        void goComment(GdsEvalBaseInfo bean);
    }

    public interface RedirectToShopDetailInterface {
        void redirectToShopDetail(Long skuId, Long gdsId);
    }
}
