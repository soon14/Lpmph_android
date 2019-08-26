package com.ailk.pmph.thirdstore.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.Constant;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchInStoreActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_search)
    EditText edSearch;
    @BindView(R.id.iv_search_close)
    ImageView ivSearchClose;

    private String storeId = null;//店铺id

    @Override
    protected int getContentViewId() {
        return R.layout.activity_store_in_search;
    }

    @Override
    public void initView() {

    }

    public void initData() {
        storeId = getIntent().getStringExtra(Constant.STORE_ID);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edSearch.setSelection(edSearch.getText().length());
                if (0 < edSearch.getText().length()) {
                    setVisible(ivSearchClose);
                }else {
                    setGone(ivSearchClose);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) edSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(edSearch, 0);
            }
        }, 500);
    }

    @Override
    @OnClick({R.id.iv_back, R.id.iv_search_close, R.id.search_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.iv_search_close:
                edSearch.setText("");
                break;

            case R.id.search_tv:
                Intent intent = new Intent(SearchInStoreActivity.this, SearchResultInStoreActivity.class);
                intent.putExtra(Constant.STORE_ID, storeId);
                intent.putExtra(Constant.STORE_KEY_WORDS, edSearch.getText().toString());
                intent.putExtra(Constant.STORE_KEY_WORDS, edSearch.getText().toString());
                intent.putExtra(Constant.STORE_SOURCE, Constant.STORE_SEARCH);//设置跳转来源
                launch(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
