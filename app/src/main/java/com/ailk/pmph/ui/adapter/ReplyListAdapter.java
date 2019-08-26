package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.resp.AcctTrade;
import com.ai.ecp.app.resp.gds.GdsEvalReplyRespBaseInfo;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/5/3 23:51
 */
public class ReplyListAdapter extends BaseAdapter {

    private Context mContext;
    private List<GdsEvalReplyRespBaseInfo> mList = new ArrayList<>();
    private ReplyInterface replyInterface;

    public ReplyListAdapter(Context context, List<GdsEvalReplyRespBaseInfo> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void addData(List<GdsEvalReplyRespBaseInfo> data) {
        if (mList != null && data != null && !data.isEmpty()) {
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setReplyInterface(ReplyInterface replyInterface) {
        this.replyInterface = replyInterface;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public GdsEvalReplyRespBaseInfo getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_reply_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final GdsEvalReplyRespBaseInfo info = getItem(position);
        String custPic = info.getCustPic();
        String staffName = info.getStaffName();
        String staffCode = PrefUtility.get("staffCode", "");
        GlideUtil.loadImg(mContext, custPic, holder.ivHead);
        if (StringUtils.equals(staffName, staffCode)) {
            holder.tvUserName.setText(staffName);
        } else {
            holder.tvUserName.setText(StringUtils.replace(staffName, StringUtils.substring(staffName, 1, staffName.length() - 1), "***"));
        }
        holder.tvFloor.setText(position+1+"#");
        holder.tvReplyContent.setText(info.getDetail());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp replyTime = new Timestamp(info.getCreateTime().getTime());
        holder.tvReplyTime.setText(df.format(replyTime));
        holder.tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyInterface.reply(info);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_head)               CircleImageView ivHead;
        @BindView(R.id.tv_username)           TextView tvUserName;
        @BindView(R.id.tv_floor)              TextView tvFloor;
        @BindView(R.id.tv_reply_content)      TextView tvReplyContent;
        @BindView(R.id.tv_reply_time)         TextView tvReplyTime;
        @BindView(R.id.tv_reply)              TextView tvReply;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface ReplyInterface {
        void reply(GdsEvalReplyRespBaseInfo info);
    }

}
