package com.ailk.im.ui.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.ailk.im.presenter.BasePresenter;
import com.ailk.pmph.R;
import com.apkfuns.logutils.LogUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Project : pmph_android
 * Created by 王可 on 2017/2/15.
 */

public abstract class IMBaseActivity<T extends BasePresenter> extends AppCompatActivity {
    public Toolbar mToolbar;
    protected T presenter;

    protected List<Subscription> mSubscriptions;

    /**
     * onCreate(Bundle savedInstanceState)中调用
     *
     * @param savedInstanceState Bundle
     */
    protected abstract void create(Bundle savedInstanceState);

    /**
     * onDestroy()中调用
     */
    protected abstract void destroy();

    /**
     * onStart()中调用
     */
    protected abstract void start();

    /**
     * onStop()中调用
     */
    protected abstract void stop();

    /**
     * onResume()中调用
     */
    protected abstract void resume();

    /**
     * onPause()中调用
     */
    protected abstract void pause();

    /**
     * @return 设置ContentViewId
     */
    protected abstract int getContentViewId();

    protected abstract T getPresenterInstance();

    protected abstract int getTitleResId();

    protected boolean applySystemBar() {
        return false;
    }

    protected boolean applyTranslucentStatus() {
        return false;
    }

    @Deprecated
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new ArrayList<>();
        setTranslucentStatus(applyTranslucentStatus());
        if (applySystemBar()) {
            setSystemBarTint(R.color.window_color);
        }
        setContentView(getContentViewId());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != mToolbar) {
            if (getTitleResId() != 0) {
                mToolbar.setTitle(getTitleResId());
                mToolbar.setTitleTextColor(ContextCompat.getColor(IMBaseActivity.this, R.color.toolbar_textcolor));
            }
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (null == presenter) {
            presenter = getPresenterInstance();
        }
        create(savedInstanceState);
    }

    @Deprecated
    @Override
    protected void onStart() {
        super.onStart();
        start();
    }

    @Deprecated
    @Override
    protected void onResume() {
        super.onResume();
        resume();
    }

    @Deprecated
    @Override
    protected void onPause() {
        super.onPause();
        pause();

    }

    @Deprecated
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Deprecated
    @Override
    protected void onDestroy() {
        destroy();
        cancelSubscriptions();
        mSubscriptions = null;
        if (null != presenter) {
            presenter.onDestroy();
        }
        presenter = null;
        LogUtils.d("destroyed context in presenter");
        super.onDestroy();
    }

    public void cancelSubscriptions() {
        if (null == mSubscriptions) {
            return;
        }
        for (Subscription subscription : mSubscriptions) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();

            }

            subscription = null;
        }

    }

    private void setSystemBarTint(int colorResId) {
        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);

        // 自定义颜色
        tintManager.setTintColor(ContextCompat.getColor(IMBaseActivity.this, colorResId));
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void changeStatusBarColor(@ColorRes int color) {
        if (applySystemBar()) {
            setSystemBarTint(color);
        }
    }

    public void setDetaulfStatusBarColor() {
        changeStatusBarColor(R.color.colorPrimary);
    }


    public void addSubscription(Subscription subscription) {
        if (null == mSubscriptions) {
            mSubscriptions = new ArrayList<>();
        }
        mSubscriptions.add(subscription);
    }

}