package com.ailk.integral.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord10801Resp;
import com.ai.ecp.app.resp.Ord10803Resp;
import com.ai.ecp.app.resp.Ord108Resp;
import com.ailk.integral.adapter.InteLogisticsAdapter;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.act
 * 作者: Chrizz
 * 时间: 2016/5/11 20:45
 */
public class InteOrderExchangeDetailActivity extends BaseActivity{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_gds_img)
    ImageView ivGdsImg;
    @BindView(R.id.tv_gds_name)
    TextView tvGdsName;
    @BindView(R.id.tv_gds_num)
    TextView tvGdsNum;
    @BindView(R.id.tv_gds_score)
    TextView tvGdsScore;
    @BindView(R.id.tv_gds_time)
    TextView tvGdsTime;
    @BindView(R.id.tv_gds_info)
    TextView tvGdsInfo;
    @BindView(R.id.lv_logistics_info)
    ListView lvLogisticsList;

    @Override
    protected int getContentViewId() {
        return R.layout.inte_activity_exchange_detail;
    }

    public void initView(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Ord108Resp ord108Resp = (Ord108Resp) bundle.get("ord108Resp");
            if (ord108Resp != null) {
                List<Ord10801Resp> ord10801Resps = ord108Resp.getOrd10801Resps();
                List<Ord10803Resp> ord10803Resps = ord108Resp.getOrd10803Resps();
                Ord10801Resp detail = ord10801Resps.get(0);
                GlideUtil.loadImg(this, detail.getPicUrl(), ivGdsImg);
                tvGdsName.setText(detail.getGdsName());
                tvGdsNum.setText("X "+detail.getOrderAmount()+"份");
                if (detail.getBuyPrice()!=null && detail.getScore()!=null) {
                    String strScore = detail.getScore()+"积分+";
                    String strPrice = detail.getBuyPrice()/100+"元";
                    String str = strScore+strPrice;
                    SpannableString spannableString = new SpannableString(str);
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED),0,str.length()-strPrice.length()-3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED),str.length()-strPrice.length(),str.length()-1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvGdsScore.setText(spannableString);
                } else if (detail.getBuyPrice()==null && detail.getScore()!=null) {
                    String scoreStr = detail.getScore()+"积分";
                    SpannableString spannableString = new SpannableString(scoreStr);
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED),0,scoreStr.length()-2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvGdsScore.setText(spannableString);
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp orderTime = new Timestamp(ord108Resp.getOrderTime().getTime());
                tvGdsTime.setText(df.format(orderTime));
                if (ord10803Resps!=null && ord10803Resps.size()>0) {
                    tvGdsInfo.setText("");
                    InteLogisticsAdapter adapter = new InteLogisticsAdapter(this,ord10803Resps);
                    lvLogisticsList.setAdapter(adapter);
                } else {
                    if (!StringUtils.equals("1",ord108Resp.getDispatchType())) {
//                tvGdsInfo.setText("暂无物流信息");
                        lvLogisticsList.setVisibility(View.GONE);
                    } else {
//                tvGdsInfo.setText("暂未发货");
                        lvLogisticsList.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
