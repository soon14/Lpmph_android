package com.ailk.im.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ailk.pmph.R;
import com.ailk.pmph.utils.ToastUtil;


/**
 * 类注释:满意度弹窗
 * 项目名:pmph_android
 * 包名:com.ailk.im.ui.dialog
 * 作者: Chrizz
 * 时间: 2017/2/21
 */

public class SatisfactionDialog extends Dialog{

    private Context mContext;
    private Long mShopId;
    private String mUserCode;
    private String mCsaCode;
    private String mSessionId;
    private String mSatisfyType = "";////满意度类型：{非常满意：5,满意：4，一般：3，不满意：2，非常不满意：1}
    private String mNotSatisfyType = "";//不满意类型 1：业务；2：服务
    private String mNotSatisfyReason = "";//不满意原因
    private CommitSatisfyListener mListener;

    public SatisfactionDialog(Context context) {
        super(context);
    }

    public SatisfactionDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public Long getShopId() {
        return mShopId;
    }

    public void setShopId(Long shopId) {
        mShopId = shopId;
    }

    public String getUserCode() {
        return mUserCode;
    }

    public void setUserCode(String userCode) {
        mUserCode = userCode;
    }

    public String getCsaCode() {
        return mCsaCode;
    }

    public void setCsaCode(String csaCode) {
        mCsaCode = csaCode;
    }

    public String getSessionId() {
        return mSessionId;
    }

    public void setSessionId(String sessionId) {
        mSessionId = sessionId;
    }

    public void setListener(CommitSatisfyListener listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.dialog_im_satisfaction, null);
        final TextView textView1 = (TextView) contentView.findViewById(R.id.tv_satisfaction1);
        final TextView textView2 = (TextView) contentView.findViewById(R.id.tv_satisfaction2);
        final TextView textView3 = (TextView) contentView.findViewById(R.id.tv_satisfaction3);
        final TextView textView4 = (TextView) contentView.findViewById(R.id.tv_satisfaction4);
        final TextView textView5 = (TextView) contentView.findViewById(R.id.tv_satisfaction5);
        final EditText input = (EditText) contentView.findViewById(R.id.et_input_reason);
        final TextView commit = (TextView) contentView.findViewById(R.id.tv_commit);
        ImageView close = (ImageView) contentView.findViewById(R.id.iv_close);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setVisibility(View.GONE);
                textView1.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_confirm));
                textView2.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView3.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView4.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView5.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                mSatisfyType = "5";
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setVisibility(View.GONE);
                textView2.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_confirm));
                textView1.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView3.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView4.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView5.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                mSatisfyType = "4";
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setVisibility(View.GONE);
                textView3.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_confirm));
                textView1.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView2.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView4.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView5.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                mSatisfyType = "3";
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setVisibility(View.GONE);
                textView4.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_confirm));
                textView1.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView2.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView3.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView5.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                mSatisfyType = "2";
                mNotSatisfyType = "2";
                mNotSatisfyReason = input.getText().toString();
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setVisibility(View.VISIBLE);
                textView5.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_confirm));
                textView1.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView2.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView3.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                textView4.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_im_textview_satisfaction));
                mSatisfyType = "2";
                mNotSatisfyType = "1";
                mNotSatisfyReason = input.getText().toString();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.getVisibility() == View.VISIBLE && TextUtils.isEmpty(input.getText().toString())) {
                    ToastUtil.show(mContext, "请输入不满意的原因");
                }
                else if (TextUtils.isEmpty(mSatisfyType)) {
                    ToastUtil.show(mContext, "请选择您的满意度");
                }
                else {
                    dismiss();
                    mListener.commitSatisfy(mShopId,mUserCode,mCsaCode,mSessionId,mSatisfyType,mNotSatisfyType,mNotSatisfyReason);
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setContentView(contentView);
        if (getWindow() != null) {
            WindowManager windowManager = getWindow().getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.width = display.getWidth() - 100;
            getWindow().setAttributes(layoutParams);
        }
    }

    public interface CommitSatisfyListener {
        void commitSatisfy(Long shopId, String userCode, String csaCode, String sessionId,
                           String satisfyType, String notSatisfyType, String notSatisfyReason);
    }

}
