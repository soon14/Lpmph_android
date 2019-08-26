package com.ailk.pmph.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppManager;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.activity.LoginActivity;
import com.ailk.pmph.ui.activity.LoginPmphActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.LogUtil;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/8.
 * 所有Activity的基础类
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseViewInterface{
    public HashMap<String, Object> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(HashMap<String, Object> paramsMap) {
        this.paramsMap = paramsMap;
    }

    protected HashMap<String, Object> paramsMap;
    private JsonService jsonService;
    private InteJsonService inteJsonService;
    protected Toolbar mActionBarToolbar;
    private TextView mTitleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        if (getContentViewId() != 0) {
            setContentView(getContentViewId());
        }
        ButterKnife.bind(this);
        paramsMap = new HashMap<>();
        ShareSDK.initSDK(this);
        initView();
        initData();
    }

    protected int getContentViewId() {
        return 0;
    }
    @Deprecated
    protected JsonService getJsonService(){
        if (null == jsonService){
            jsonService = new JsonService(BaseActivity.this);
        }
        return jsonService;
    }
    @Deprecated
    protected InteJsonService getInteJsonService(){
        if (null == inteJsonService) {
            inteJsonService = new InteJsonService(this);
        }
        return inteJsonService;
    }

    protected void setGone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void setInvisible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    protected void setVisible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    protected void showLoginDialog(BaseActivity context) {
        DialogAnotherUtil.showCustomAlertDialogWithMessage(context, null,
                "您未登录，请先登录", "登录", "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogUtil.dismissDialog();
                        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                            launch(LoginPmphActivity.class);
                        } else {
                            launch(LoginActivity.class);
                        }
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogUtil.dismissDialog();
                    }
                });
    }

    protected void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     * start Activity *
     */
    public void launch(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
        overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }

    /**
     * start Activity *
     */
    public void launch(Intent it) {
        startActivity(it);
        overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }

    protected void launchBottom(Intent it) {
        startActivity(it);
        overridePendingTransition(R.anim.push_bottom_in3, android.R.anim.fade_out);
    }

    /**
     * start Activity *
     */
    public void launch_slideright2left(Intent it) {
        startActivity(it);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    public void launch(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        launch_slideright2left(intent);
    }

    public void launch(Class<? extends Activity> clazz, Bundle bundle, int intent_flag) {
        Intent intent = new Intent(this, clazz);
        intent.setFlags(intent_flag);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        launch_slideright2left(intent);
    }

    public void launchForResult(Class<? extends Activity> clazz,
                                int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }

    public void launchForResult(Intent intent,int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }
    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in, R.anim.push_right_out);
            AppManager.getAppManager().finishActivity();
        } catch (Exception e) {
            LogUtil.e("点击返回键报错...:" + e);
        }
    }
    protected ActionBar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.my_toolbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return getSupportActionBar();
    }
    protected TextView getTitleView() {
        if(mTitleView == null) {
            mTitleView = (TextView)findViewById(R.id.toolbar_title);
        }
        return mTitleView;
    }

}
