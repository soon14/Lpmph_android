package com.ailk.im.ui.activity;

import android.os.Bundle;

import com.ailk.im.presenter.BrowseGoodsPresenter;
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

public class BrowseGoodsActivity extends IMBaseActivity<BrowseGoodsPresenter> {

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
        return R.layout.im_activity_browse_goods;
    }

    @Override
    protected BrowseGoodsPresenter getPresenterInstance() {
        return new BrowseGoodsPresenter(this);
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
