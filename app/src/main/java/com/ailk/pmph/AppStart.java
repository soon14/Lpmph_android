package com.ailk.pmph;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.ailk.pmph.ui.activity.MainActivity;
import com.ailk.pmph.utils.Constant;

import org.apache.commons.lang.StringUtils;

/**
 * 类注释:启动页
 * 项目名:pmph_android
 * 包名:com.ailk.pmph
 * 作者: Chrizz
 * 时间: 2016/5/9 11:05
 */
public class AppStart extends Activity {

    private TextView tvSkip;
    private Handler handler = new Handler();
    private TextView tvGo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = AppManager.getActivity(MainActivity.class);
        if (activity != null && !activity.isFinishing()) {
            finish();
        }

        View view = View.inflate(this, R.layout.activity_app_start, null);
        setContentView(view);
        tvSkip = (TextView) view.findViewById(R.id.tv_skip);
        tvGo = (TextView) view.findViewById(R.id.tv_go_main);
        MyCountDownTimer countDownTimer = new MyCountDownTimer(3000, 1000);
        countDownTimer.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoActivity();
            }
        }, 3000);
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity();
            }
        });
        tvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity();
            }
        });
    }

    private void gotoActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            tvSkip.setText("跳过(0s)");
            gotoActivity();
        }

        public void onTick(long millisUntilFinished) {
            tvSkip.setText("跳过(" + millisUntilFinished / 1000 + "s)");
        }
    }

}
