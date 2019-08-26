package com.ailk.pmph.countdownview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ailk.pmph.ui.activity.MainActivity;
import com.ailk.pmph.ui.activity.SecondKillActivity;

import org.apache.commons.lang.StringUtils;


/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.countdownview
 * 作者: Chrizz
 * 时间: 2016/11/3
 */

public class TimerTextView extends TextView implements Runnable {

    private Context mContext;

    public TimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    private long mDay = 0, mHour = 0, mMin = 0, mSecond = 0;//天，小时，分钟，秒
    private boolean run = false; //是否启动了

    public void setTimes(long millis) {
        long totalSeconds = millis / 1000;
        long totalMinutes = totalSeconds / 60;
        long totalHours = totalMinutes / 60;
        long totalDays = totalHours / 24;
        mSecond = totalSeconds % 60;
        mMin = (totalMinutes % 60);
        mHour = (totalHours % 24);
        mDay = totalDays;
    }

    /**
     * 倒计时计算
     */
    private void ComputeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    mHour = 23;
                    mDay--;
                }
            }
        }
    }

    public boolean isRun() {
        return run;
    }

    public void beginRun() {
        this.run = true;
        run();
    }

    public void stopRun(){
        this.run = false;
    }


    @Override
    public void run() {
        if(run){
            ComputeTime();
            String day = mDay + "";
            String hour = mHour + "";
            String minute = mMin + "";
            String second = mSecond + "";
            if (day.length() == 1) {
                day = "0" + day;
            }
            if (hour.length() == 1) {
                hour = "0" + hour;
            }
            if (minute.length() == 1) {
                minute = "0" + minute;
            }
            if (second.length() == 1) {
                second = "0" + second;
            }
            String strTime = day +"天 "+ hour+"时 "+ minute+"分 "+second+"秒";
            this.setText(strTime);
            if (StringUtils.equals(day, "00")
                    && StringUtils.equals(hour, "00")
                    && StringUtils.equals(minute, "00")
                    && StringUtils.equals(second, "00")) {
                Intent intent = new Intent(SecondKillActivity.REFRESH_KILL);
                mContext.sendBroadcast(intent);
            }
            postDelayed(this, 1000);
        } else {
            removeCallbacks(this);
            stopRun();
        }
    }

}
