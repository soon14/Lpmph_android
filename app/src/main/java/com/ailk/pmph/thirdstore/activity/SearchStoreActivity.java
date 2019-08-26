package com.ailk.pmph.thirdstore.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ai.ecp.app.req.Gds021Req;
import com.ai.ecp.app.resp.Gds021Resp;
import com.ai.ecp.app.resp.gds.ShopSearchResultVO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.pmph.utils.StringUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.thirdstore.adapter.StoreSearchAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchStoreActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_search_content)
    TextView tvSearchContent;
    @BindView(R.id.listView)
    PullToRefreshListView mListView;
    @BindView(R.id.no_content_store_img)
    ImageView noContentStoreImg;

    private StoreSearchAdapter adapter;
    private Gds021Req gds021Req;
    private int pageNumber = 1;
    private String keyWords;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_store_search;
    }

    @Override
    public void initView() {

    }

    public void initData() {
        keyWords = getIntent().getStringExtra(Constant.SHOP_KEY_WORD);
        tvSearchContent.setText(keyWords);
        gds021Req = new Gds021Req();
        adapter = new StoreSearchAdapter(SearchStoreActivity.this, new ArrayList<ShopSearchResultVO>());
        mListView.setAdapter(adapter);
        loadData(true);

        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNumber=1;
                adapter.clear();
                loadData(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(false);
            }
        });

        //添加至搜索历史记录
        if (StringUtil.isNotEmpty(keyWords)) {
            PrefUtility.putSearchStoreHistory(keyWords);
        }
    }

    private void loadData(boolean flag) {
        gds021Req.setKeyword(keyWords);
        gds021Req.setPageNumber(pageNumber++);
        gds021Req.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestGds021(this, gds021Req, flag, new JsonService.CallBack<Gds021Resp>() {
            @Override
            public void oncallback(Gds021Resp gds021Resp) {
                mListView.onRefreshComplete();
                if (null != gds021Resp.getPageRespVO() && 0 != gds021Resp.getPageRespVO().size()) {
                    adapter.addAll(gds021Resp.getPageRespVO());
                    setGone(noContentStoreImg);
                    setVisible(mListView);
                    mListView.onRefreshComplete();
                } else {
                    if (2 == pageNumber) {
                        setGone(mListView);
                        setVisible(noContentStoreImg);
                    }else {
                        ToastUtil.show(SearchStoreActivity.this,R.string.toast_load_more_msg);
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {
                mListView.onRefreshComplete();
            }
        });
    }

    @Override
    @OnClick({R.id.img_back, R.id.tv_search_content, R.id.tv_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;

            case R.id.tv_search_content:
                onBackPressed();
                break;

            case R.id.tv_search:
                onBackPressed();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
