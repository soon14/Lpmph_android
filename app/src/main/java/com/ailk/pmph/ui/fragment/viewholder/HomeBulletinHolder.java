package com.ailk.pmph.ui.fragment.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.ai.ecp.app.resp.Cms010Resp;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.activity.BulletinActivity;

import java.util.List;
import java.util.Random;

/**
 * 类注释:快报楼层
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.fragment.viewholder
 * 作者: Chrizz
 * 时间: 2016/10/10 21:06
 */
@HomeViewType(ViewType=510)
public class HomeBulletinHolder extends HomeViewHolder{

    private TextSwitcher tsTag;
    private TextSwitcher tsTitle;
    private List<Cms010Resp.Item> items;

    public HomeBulletinHolder(Activity activity, LayoutInflater inflater, ViewGroup viewGroup) {
        super(activity,inflater,viewGroup, R.layout.home_bulletin_layout,R.dimen.home_modey_body_width,R.dimen.home_model_bulletin_height);
    }

    @Override
    public void initData(Cms010Resp.Model model) {
        super.initData(model);
        items = model.getItemList();
    }

    @Override
    protected void initBodyView(ViewGroup viewGroup) {
        this.tsTag = (TextSwitcher) viewGroup.findViewById(R.id.ts_tag);
        this.tsTitle = (TextSwitcher) viewGroup.findViewById(R.id.ts_title);
        TextView tvMore = (TextView) viewGroup.findViewById(R.id.tv_more);
        tsTag.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final TextView tv = new TextView(mConText);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(10, 5, 10, 5);
                tv.setTextColor(Color.RED);
                return tv;
            }
        });

        tsTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final TextView tv = new TextView(mConText);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tv.setTextColor(Color.BLACK);
                tv.setPadding(20, 20, 20,20);
                tv.setEms(15);
                tv.setMaxLines(1);
                tv.setEllipsize(TextUtils.TruncateAt.END);
                return tv;
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                if (items.size() > 0) {
                    final int next = random.nextInt(items.size());
                    tsTag.setBackgroundResource(R.drawable.corner);
                    tsTag.setText(items.get(next).getTypeName());
                    tsTitle.setText(items.get(next).getName());
                    tsTitle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setOnClickListener(tsTitle, items.get(next).getClickUrl(), null);
                        }
                    });
                    handler.postDelayed(this, 3000);
                }
            }
        }, 3000);

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConText.startActivity(new Intent(mConText, BulletinActivity.class));
            }
        });
    }

}
