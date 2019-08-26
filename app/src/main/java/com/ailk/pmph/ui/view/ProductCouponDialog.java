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

import com.ai.ecp.app.req.Ord005Req;
import com.ai.ecp.app.req.ROrdCartChangeRequest;
import com.ai.ecp.app.req.ROrdCartCommRequest;
import com.ai.ecp.app.req.ROrdCartItemCommRequest;
import com.ai.ecp.app.req.ROrdCartItemRequest;
import com.ai.ecp.app.resp.Ord00201Resp;
import com.ai.ecp.app.resp.Ord00202Resp;
import com.ai.ecp.app.resp.Ord00203Resp;
import com.ai.ecp.app.resp.Ord005Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.ui.adapter.ProductCouponListAdapter;
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
public class ProductCouponDialog extends Dialog implements AdapterView.OnItemClickListener,
        ProductCouponListAdapter.CheckProductCouponInterface {

    private Ord00201Resp group;
    private Ord00202Resp product;
    private Context mContext;
    private JsonService jsonService;

    public ProductCouponDialog(Context context) {
        super(context);
    }

    public ProductCouponDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public void setGroup(Ord00201Resp group) {
        this.group = group;
    }

    public void setProduct(Ord00202Resp product) {
        this.product = product;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View contentView = getLayoutInflater().inflate(R.layout.dialog_modify_coupon, null);
        jsonService = new JsonService(mContext);
        ImageView ivClose = (ImageView)contentView.findViewById(R.id.iv_close);
        ListView lvCoupon = (ListView)contentView.findViewById(R.id.lv_coupon);
        ProductCouponListAdapter adapter = new ProductCouponListAdapter(mContext);
        adapter.setProduct(product);
        adapter.setCheckProductCouponInterface(ProductCouponDialog.this);
        lvCoupon.setAdapter(adapter);
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
    public void doCheckProductCoupon(int position) {
        requestProductCouponModify(position);
        dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        requestProductCouponModify(position);
        dismiss();
    }

    private void requestProductCouponModify(int position) {
        Ord005Req request = new Ord005Req();
        List<Ord00202Resp> products = group.getOrdCartItemList();

        ROrdCartItemRequest ordCartItem = new ROrdCartItemRequest();
        ROrdCartChangeRequest ordCartChg = new ROrdCartChangeRequest();
        ROrdCartCommRequest rOrdCartCommRequest = new ROrdCartCommRequest();
        List<ROrdCartItemCommRequest> ordCartItemCommList = new ArrayList<>();
        Ord00203Resp promInfo = product.getPromInfoDTOList().get(position);

        ordCartItem.setId(product.getId());
        ordCartItem.setPromId(promInfo.getId());
        ordCartItem.setStaffId(group.getStaffId());
        for (Ord00202Resp product : products) {
            rOrdCartCommRequest.setId(group.getCartId());
            rOrdCartCommRequest.setPromId(group.getPromId());

            ROrdCartItemCommRequest rOrdCartItemCommRequest = new ROrdCartItemCommRequest();
            rOrdCartItemCommRequest.setId(product.getId());
            rOrdCartItemCommRequest.setPromId(promInfo.getId());
            ordCartItemCommList.add(rOrdCartItemCommRequest);
            rOrdCartCommRequest.setOrdCartItemCommList(ordCartItemCommList);
        }
        ordCartChg.setrOrdCartCommRequest(rOrdCartCommRequest);
        request.setOrdCartItem(ordCartItem);
        request.setOrdCartChg(ordCartChg);

        jsonService.requestOrd005(mContext, request, false, new JsonService.CallBack<Ord005Resp>() {
            @Override
            public void oncallback(Ord005Resp ord005Resp) {
                Intent intent = new Intent(ShopCartFragment.PRODUCT_COUPON_ACTION);
                mContext.sendBroadcast(intent);
            }

            @Override
            public void onErro(AppHeader header) {
            }
        });
    }
}
