package com.ailk.pmph.thirdstore.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ai.ecp.app.req.Gds025Req;
import com.ai.ecp.app.resp.Gds025Resp;
import com.ai.ecp.app.resp.gds.GdsCategoryVO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.thirdstore.adapter.StoreClassifyAdapter;
import com.ailk.pmph.utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class StoreClassifyActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.no_content_img)
    ImageView noContentImg;

    private StoreClassifyAdapter adapter;

    private String storeId = null;//店铺id

    @Override
    protected int getContentViewId() {
        return R.layout.activity_store_classify;
    }

    @Override
    public void initView() {

    }

    public void initData() {
        adapter = new StoreClassifyAdapter(StoreClassifyActivity.this, new ArrayList<GdsCategoryVO>());
        listView.setAdapter(adapter);
        storeId = getIntent().getStringExtra(Constant.STORE_ID);

        loadData();
    }

    private void loadData() {
        Gds025Req gds025Req = new Gds025Req();
        gds025Req.setId(storeId);
        getJsonService().requestGds025(StoreClassifyActivity.this, gds025Req, true, new JsonService.CallBack<Gds025Resp>() {
            @Override
            public void oncallback(Gds025Resp gds025Resp) {
                if (null != gds025Resp && 0 != gds025Resp.getCatgList().size()) {
                    setGone(noContentImg);
                    setVisible(listView);
                    adapter.addAll(gds025Resp.getCatgList());
                } else {
                    setGone(listView);
                    setVisible(noContentImg);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GdsCategoryVO gdsCategoryVO = adapter.getItem(position);
                Intent intent = new Intent(StoreClassifyActivity.this,SearchResultInStoreActivity.class);
                intent.putExtra(Constant.STORE_ID,storeId);
                intent.putExtra(Constant.STORE_KEY_WORDS,gdsCategoryVO.getCatgName());
                intent.putExtra(Constant.STORE_SOURCE, Constant.STORE_CLASSIFY);//设置跳转来源
                launch(intent);
            }
        });
    }

    @Override
    @OnClick({R.id.iv_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
