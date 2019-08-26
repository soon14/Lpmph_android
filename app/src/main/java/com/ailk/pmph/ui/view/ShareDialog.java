package com.ailk.pmph.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ailk.pmph.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.view
 * 作者: Chrizz
 * 时间: 2016/6/16 10:30
 */
public class ShareDialog extends Dialog{

    private Context mContext;
    private GridView gvShare;
    private TextView tvCancel;
    private SimpleAdapter adapter;
    private int[] images = {
            R.drawable.logo_wechat,R.drawable.logo_wechatmoments,R.drawable.logo_sinaweibo,
            R.drawable.logo_qq, R.drawable.logo_qzone,R.drawable.logo_link};
    private String[] texts = {"微信好友","朋友圈","新浪微博","QQ好友","QQ空间","复制链接"};

    public ShareDialog(Context context) {
        super(context);
    }

    public ShareDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.dialog_share, null);
        gvShare = (GridView) contentView.findViewById(R.id.gv_share);
        tvCancel = (TextView) contentView.findViewById(R.id.tv_cancel);
        List<Map<String, Object>> shareList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("itemImage", images[i]);
            map.put("itemText", texts[i]);
            shareList.add(map);
        }
        adapter = new SimpleAdapter(mContext, shareList, R.layout.item_share,
                new String[]{"itemImage","itemText"}, new int[]{R.id.iv_logo,R.id.tv_text});
        gvShare.setAdapter(adapter);
        tvCancel.setOnClickListener(new View.OnClickListener() {
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

    public void setOnGridViewItemClickListener(AdapterView.OnItemClickListener listener) {
        gvShare.setOnItemClickListener(listener);
    }

}
