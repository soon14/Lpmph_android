package com.ailk.pmph.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.activity.LoginActivity;
import com.ailk.pmph.ui.activity.LoginPmphActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;

import org.apache.commons.lang.StringUtils;

import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/11.
 * 所有Fragment的基础类
 */
public abstract class BaseFragment extends Fragment {

    protected View mView;
    protected LayoutInflater mInflater;
    protected FragmentActivity mActivity;
    protected static Context mContext;

    public abstract int getLayoutResId();

    private JsonService jsonService;
    private InteJsonService inteJsonService;

    protected JsonService getJsonService(){
        if (null==jsonService) {
            jsonService = new JsonService(getActivity());
        }
        return jsonService;
    }

    protected InteJsonService getInteJsonService(){
        if (null==inteJsonService) {
            inteJsonService = new InteJsonService(getActivity());
        }
        return inteJsonService;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutResId(), container, false);
        this.mInflater = inflater;
        this.mActivity = getSupportActivity();
        this.mContext = mActivity;
        ShareSDK.initSDK(mContext);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
    }

    public abstract void initView(View view);

    public abstract void initData();

    public FragmentActivity getSupportActivity() {
        return super.getActivity();
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

    protected void showLoginDialog(FragmentActivity context) {
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
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        mActivity.getWindow().setAttributes(lp);
    }

    /** start Activity **/
    protected void launch(Class<? extends Activity> clazz) {
        getActivity().startActivity(new Intent(getActivity(), clazz));
        getActivity().overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }

    protected void launch(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }

    protected void launch(Intent it) {
        getActivity().startActivity(it);
        getActivity().overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }

    protected void launchBottom(Intent it) {
        getActivity().startActivity(it);
        getActivity().overridePendingTransition(R.anim.push_bottom_in3, android.R.anim.fade_out);
    }

    protected void launchBottomForResult(Class<? extends Activity> clazz,
                                         int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.push_bottom_in3, android.R.anim.fade_out);
    }

    protected void launchForResult(Class<? extends Activity> clazz,
                                   int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }
}
