package com.ailk.pmph.countdownview;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ailk.pmph.R;
import com.ailk.pmph.utils.LogUtil;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.countdownview
 * 作者: Chrizz
 * 时间: 2016/11/2
 */

public abstract class BaseCountDownTimerView extends LinearLayout {

    private Context mContext;

    /**
     * 倒计时控制器
     */
    private CountDownTimer mCountDownTimer;

    private OnCountDownTimerListener OnCountDownTimerListener;

    private int mMillis;

    /**
     * 天
     */
    private TextView mDayTextView;

    /**
     * 时
     */
    private TextView mHourTextView;

    /**
     * 分
     */
    private TextView mMinTextView;

    /**
     * 秒
     */
    private TextView mSecondTextView;

    /**
     * 获取边框颜色
     *
     * @return
     */
    protected abstract String getStrokeColor();

    /**
     * 设置背景色
     *
     * @return
     */
    protected abstract String getBackgroundColor();

    /**
     * 获取文字颜色
     *
     * @return
     */
    protected abstract String getTextColor();

    /**
     * 获取边框圆角
     *
     * @return
     */
    protected abstract int getCornerRadius();

    /**
     * 获取标签文字大小
     *
     * @return
     */
    protected abstract int getTextSize();

    public BaseCountDownTimerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    public BaseCountDownTimerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCountDownTimerView(Context context) {
        this(context, null);
    }

    private void init() {
        this.setOrientation(HORIZONTAL);// 设置布局排列方式
        createView();// 创造标签
        addLabelView();// 添加标签到容器中
    }

    /**
     * 创建时、分、秒的标签
     */
    private void createView() {
        mDayTextView = createLabel();
        mHourTextView = createLabel();
        mMinTextView = createLabel();
        mSecondTextView = createLabel();
    }

    /**
     * 添加标签到容器中
     */
    private void addLabelView() {
        removeAllViews();
        this.addView(mDayTextView);
        this.addView(createColon());
        this.addView(mHourTextView);
        this.addView(createColon());
        this.addView(mMinTextView);
        this.addView(createColon());
        this.addView(mSecondTextView);
    }

    /**
     * 创建红色冒号
     *
     * @return
     */
    private TextView createColon() {
        TextView textView = new TextView(mContext);
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.red_f2315e));
        textView.setText(":");
        return textView;
    }


    /**
     * 创建标签
     *
     * @return
     */
    private TextView createLabel() {
        return new GradientTextView(mContext)
                .setTextColor(getTextColor()).setStrokeColor(getStrokeColor())
                .setBackgroundColor(getBackgroundColor())
                .setTextSize(getTextSize()).setStrokeRadius(getCornerRadius())
                .build();
    }

    /**
     * 创建倒计时
     */
    private void createCountDownTimer() {
        mCountDownTimer = new CountDownTimer(mMillis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                setSecond(millisUntilFinished);// 设置秒
            }

            @Override
            public void onFinish() {
                if (OnCountDownTimerListener != null) {
                    OnCountDownTimerListener.onFinish();
                }
            }
        };
    }

    /**
     * 设置秒
     *
     * @param millis
     */
    private void setSecond(long millis) {
        long totalSeconds = millis / 1000;
        String second = (int) (totalSeconds % 60) + "";// 秒
        long totalMinutes = totalSeconds / 60;
        String minute = (int) (totalMinutes % 60) + "";// 分
        long totalHours = totalMinutes / 60;
        String hour = (int) (totalHours % 24) + "";// 时
        long totalDays = totalHours / 24;
        String day = (int) totalDays + "";// 天
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
        mDayTextView.setText(day);
        mHourTextView.setText(hour);
        mMinTextView.setText(minute);
        mSecondTextView.setText(second);
    }

    /**
     * 设置监听事件
     * @param listener
     */
    public void setDownTimerListener(OnCountDownTimerListener listener){
        this.OnCountDownTimerListener=listener;
    }

    /**
     * 设置时间值
     *
     * @param millis
     */
    public void setDownTime(int millis) {
        this.mMillis = millis;
    }

    /**
     * 开始倒计时
     */
    public void startDownTimer() {
        createCountDownTimer();// 创建倒计时
        mCountDownTimer.start();
    }

    public void cancelDownTimer() {
        mCountDownTimer.cancel();
    }

}
