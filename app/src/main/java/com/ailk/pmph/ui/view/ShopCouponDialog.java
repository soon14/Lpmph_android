package com.ailk.pmph.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ai.ecp.app.req.Ord004Req;
import com.ai.ecp.app.req.ROrdCartChangeRequest;
import com.ai.ecp.app.req.ROrdCartCommRequest;
import com.ai.ecp.app.req.ROrdCartItemCommRequest;
import com.ai.ecp.app.req.ROrdCartItemRequest;
import com.ai.ecp.app.resp.Ord00201Resp;
import com.ai.ecp.app.resp.Ord00202Resp;
import com.ai.ecp.app.resp.Ord00203Resp;
import com.ai.ecp.app.resp.Ord004Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.ui.adapter.ShopCouponListAdapter;
import com.ailk.pmph.ui.fragment.ShopCartFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ailk.pmph.ui.view
 * 作者: Chrizz
 * 时间: 2016/3/24 23:46
 */
public class ShopCouponDialog extends Dialog implements ShopCouponListAdapter.CheckShopCouponInterface,
        AdapterView.OnItemClickListener{

    private Ord00201Resp group;
    private Context mContext;
    private JsonService jsonService;

    public ShopCouponDialog(Context context) {
        super(context);
    }

    public ShopCouponDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public void setGroup(Ord00201Resp group) {
        this.group = group;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View contentView = getLayoutInflater().inflate(R.layout.dialog_modify_coupon, null);
        ImageView ivClose = (ImageView)contentView.findViewById(R.id.iv_close);
        ListView lvCoupon = (ListView)contentView.findViewById(R.id.lv_coupon);
        ShopCouponListAdapter adapter = new ShopCouponListAdapter(mContext);
        adapter.setGroup(group);
        adapter.setCheckShopCouponInterface(ShopCouponDialog.this);
        lvCoupon.setAdapter(adapter);
        lvCoupon.setOnItemClickListener(ShopCouponDialog.this);
        jsonService = new JsonService(mContext);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return true;
            }
        });
        setContentView(contentView);

        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = display.getWidth();
        getWindow().setAttributes(layoutParams);

    }

    @Override
    public void doCheckShopCoupon(int position) {
        requestModifyShopCoupon(position);
        dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        requestModifyShopCoupon(position);
        dismiss();
    }

    private void requestModifyShopCoupon(int position){
        Ord004Req request = new Ord004Req();
        List<Ord00202Resp> products = group.getOrdCartItemList();
        Ord00203Resp promInfo = group.getPromInfoDTOList().get(position);

        ROrdCartItemRequest ordCartItem = new ROrdCartItemRequest();
        ROrdCartChangeRequest ordCartChg = new ROrdCartChangeRequest();
        ROrdCartCommRequest rOrdCartCommRequest = new ROrdCartCommRequest();
        List<ROrdCartItemCommRequest> ordCartItemCommList = new ArrayList<>();
        for (Ord00202Resp product : products) {
            ordCartItem.setId(group.getCartId());
            ordCartItem.setPromId(promInfo.getId());
            ordCartItem.setStaffId(group.getStaffId());

            ROrdCartItemCommRequest rOrdCartItemCommRequest = new ROrdCartItemCommRequest();
            rOrdCartItemCommRequest.setId(product.getId());
            rOrdCartItemCommRequest.setPromId(group.getPromId());
            rOrdCartItemCommRequest.setStaffId(group.getStaffId());
            ordCartItemCommList.add(rOrdCartItemCommRequest);

            rOrdCartCommRequest.setId(group.getCartId());
            rOrdCartCommRequest.setPromId(group.getPromId());
        }
        rOrdCartCommRequest.setOrdCartItemCommList(ordCartItemCommList);
        ordCartChg.setrOrdCartCommRequest(rOrdCartCommRequest);
        request.setOrdCartItem(ordCartItem);
        request.setOrdCartChg(ordCartChg);

        jsonService.requestOrd004(mContext, request, false, new JsonService.CallBack<Ord004Resp>() {
            @Override
            public void oncallback(final Ord004Resp ord004Resp) {
                Intent intent = new Intent(ShopCartFragment.SHOP_COUPON_ACTION);
                mContext.sendBroadcast(intent);
            }

            @Override
            public void onErro(AppHeader header) {
                LogUtil.e(header.getRespMsg());
            }
        });
    }

}
