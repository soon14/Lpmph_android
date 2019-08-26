package com.ailk.pmph.thirdstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.req.Staff115Req;
import com.ai.ecp.app.resp.Staff115Resp;
import com.ai.ecp.app.resp.staff.CollectionShopInfo;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.thirdstore.activity.StoreActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.tool.GlideUtil;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类描述：
 * 项目名称： pmph
 * Package:  com.ailk.pmph.ui.adapter
 * 创建人：   Nzke
 * 创建时间： 2016/5/31 15:46
 * 修改人：   Nzke
 * 修改时间： 2016/5/31 15:46
 * 修改备注： 2016/5/31 15:46
 * version   v1.0
 */
public class StoreCollectAdapter extends BaseAdapter {

    private Context mContext;
    private List<CollectionShopInfo> mList;
    private JsonService jsonService;

    public StoreCollectAdapter(Context context, List<CollectionShopInfo> list) {
        this.mContext = context;
        this.mList = list;
        jsonService = new JsonService(context);
    }

    private GetCollectNumListener mListener;
    public void setListener(GetCollectNumListener listener) {
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return mList != null && mList.size() > 0 ? mList.size() : 0;
    }

    @Override
    public CollectionShopInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_store_collect, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CollectionShopInfo collectionShopInfo = getItem(position);
        GlideUtil.loadImg(mContext,collectionShopInfo.getLogoPathURL(),holder.itemImg);
        holder.itemName.setText(collectionShopInfo.getShopName());
        holder.itemSales.setText("销量：" + collectionShopInfo.getSaleGdsCnt());
        holder.itemInventory.setText("共" + collectionShopInfo.getGdsCnt() + "件宝贝");

        holder.toStoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StoreActivity.class);
                intent.putExtra(Constant.STORE_ID, String.valueOf(collectionShopInfo.getId()));//统一传入String
                ((BaseActivity) mContext).launch(intent);
            }
        });

        holder.cancelCollectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Staff115Req staff115Req = new Staff115Req();
                staff115Req.setShopId(collectionShopInfo.getId());
                jsonService.requestStaff115(mContext, staff115Req, true, new JsonService.CallBack<Staff115Resp>() {
                    @Override
                    public void oncallback(Staff115Resp staff115Resp) {
                        if (staff115Resp.isFlag()) {
                            remove(position);
                            ToastUtil.show(mContext,"取消成功!");
                            if (0 == mList.size()) {
                                if (mListener != null) {
                                    mListener.onCollectNum(mList.size());
                                }
                            }
                        }
                    }

                    @Override
                    public void onErro(AppHeader header) {

                    }
                });
            }
        });

        return convertView;
    }

    public interface GetCollectNumListener {
        void onCollectNum(int num);
    }

    public void addAll(Collection<CollectionShopInfo> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void remove(int location) {
        mList.remove(location);
        notifyDataSetChanged();
    }


    static class ViewHolder {
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_sales)
        TextView itemSales;
        @BindView(R.id.item_inventory)
        TextView itemInventory;
        @BindView(R.id.to_store_tv)
        TextView toStoreTv;
        @BindView(R.id.cancle_collect_tv)
        TextView cancelCollectTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
