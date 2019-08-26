package com.ailk.im.ui.activity;

import android.os.Bundle;

import com.ailk.im.presenter.MyOrdersPresenter;
import com.ailk.im.ui.base.IMBaseActivity;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.im.ui.activity
 * 作者: Chrizz
 * 时间: 2017/2/24
 */

public class MyOrdersActivity extends IMBaseActivity<MyOrdersPresenter> {

    @Override
    protected void create(Bundle savedInstanceState) {
        Long shopId = getIntent().getLongExtra(Constant.SHOP_ID, 0);
        presenter.initView(shopId);
    }

    @Override
    protected void destroy() {

    }

    @Override
    protected void start() {

    }

    @Override
    protected void stop() {

    }

    @Override
    protected void resume() {

    }

    @Override
    protected void pause() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.im_activity_my_orders;
    }

    @Override
    protected MyOrdersPresenter getPresenterInstance() {
        return new MyOrdersPresenter(this);
    }

    @Override
    protected int getTitleResId() {
        return 0;
    }

    @Override
    protected boolean applySystemBar() {
        return false;
    }

    @Override
    protected boolean applyTranslucentStatus() {
        return false;
    }

}
