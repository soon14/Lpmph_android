package com.ailk.integral.widget;

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
import android.widget.ListView;

import com.ai.ecp.app.resp.gds.PointGdsScoreExtRespInfo;
import com.ailk.integral.activity.InteShopDetailActivity;
import com.ailk.integral.adapter.InteDetailScoreListAdapter;
import com.ailk.pmph.R;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.widget
 * 作者: Chrizz
 * 时间: 2016/5/20 11:19
 */
public class CheckScoreDialog extends Dialog implements InteDetailScoreListAdapter.CheckScoreInterface{

    private ListView lvScore;
    private InteDetailScoreListAdapter adapter;
    private List<PointGdsScoreExtRespInfo> scores;
    private Long scoreTypeId;

    private Context mContext;

    public CheckScoreDialog(Context context) {
        super(context);
    }

    public CheckScoreDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public void setScores(List<PointGdsScoreExtRespInfo> scores) {
        this.scores = scores;
    }

    public void setScoreTypeId(Long scoreTypeId) {
        this.scoreTypeId = scoreTypeId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View contentView = getLayoutInflater().inflate(R.layout.inte_dialog_check_score, null);
        lvScore = (ListView)contentView.findViewById(R.id.lv_score);
        adapter = new InteDetailScoreListAdapter(mContext);
        adapter.setScoreTypeId(scoreTypeId);
        adapter.setScores(scores);
        adapter.setCheckScoreInterface(CheckScoreDialog.this);
        lvScore.setAdapter(adapter);
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
    public void checkScore(PointGdsScoreExtRespInfo score) {
        dismiss();
        Intent intent = new Intent(InteShopDetailActivity.REFRESH_PRICE);
        Bundle bundle = new Bundle();
        bundle.putSerializable("score", score);
        intent.putExtras(bundle);
        mContext.sendBroadcast(intent);
    }

}
