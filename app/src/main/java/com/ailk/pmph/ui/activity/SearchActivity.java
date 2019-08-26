package com.ailk.pmph.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ai.ecp.app.req.Gds007Req;
import com.ai.ecp.app.req.Gds011Req;
import com.ai.ecp.app.req.Gds022Req;
import com.ai.ecp.app.resp.Gds007Resp;
import com.ai.ecp.app.resp.Gds011Resp;
import com.ai.ecp.app.resp.Gds022Resp;
import com.ai.ecp.search.dubbo.search.result.CollationReuslt;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.thirdstore.activity.SearchStoreActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ListViewAdapter;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.pmph.ui.view.CustomGridView;
import com.ailk.pmph.utils.SearchHistoryHelper;
import com.ailk.pmph.utils.ToastUtil;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/6/22 17:31
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.title_goodlist_back)
    ImageView back;
    @BindView(R.id.title_goodlist_content)
    EditText searchContent;
    @BindView(R.id.title_goodlist_search)
    TextView search;
    @BindView(R.id.shop_good_tv)
    TextView shopStoreTv;
    @BindView(R.id.title_goodlist_close)
    ImageView searchClose;

    @BindView(R.id.complete_listview)
    ListView completeListView;
    @BindView(R.id.hotwords_gridview)
    CustomGridView hotWordsGridView;
    @BindView(R.id.layout_search)
    View hotWordLayout;
    @BindView(R.id.history_empyt)
    TextView historyEmpty;
    @BindView(R.id.history_listview)
    ListView historyListView;
    ArrayAdapter<String> historyAdapter;
    ArrayAdapter<String> historyStoreAdapter;

    @BindView(R.id.ll_hot_layout)
    LinearLayout llHotLayout;
    @BindView(R.id.ll_hot_word_gallery)
    LinearLayout llGallery;
    @BindView(R.id.ll_grid_hot_layout)
    LinearLayout llGridHotLayout;
    @BindView(R.id.ll_history_layout)
    LinearLayout llHistoryLayout;
    @BindView(R.id.shop_layout)
    FrameLayout shopFrameLayout;  //商品历史搜索的layout
    @BindView(R.id.listView_store)
    ListView listViewStore;  //店铺历史搜索的listView
    @BindView(R.id.empty_store_tv)
    TextView emptyStoreTv;  //清空店铺历史搜索的按钮
    @BindView(R.id.layout_store_search_frame)
    FrameLayout layoutStoreSearchFrame; //店铺搜索的layout（整体）
    @BindView(R.id.layout_store_search_linear)
    LinearLayout layoutStoreSearchLinear; //店铺历史搜索显示的layout
    @BindView(R.id.listView_store_complete)
    ListView listViewStoreComplete; //店铺历史搜索的搜索建议列表

    private String currentCatgCode = "";
    private String currentKeyWord = "";
    private boolean isFromHome;

    private CompleteAdapter completeAdapter;
    private HotWordsAdapter hotWordsAdapter;
    private CompleteStoreAdapter completeStoreAdapter;
    private SearchHistoryHelper mHelper;
    private PopupWindow popupWindow;
    private Context mContext;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //显示商品的搜索历史记录
        List<String> shopWords = querySearchHistory();
        if (null != shopWords && 0 != shopWords.size()) {
            setVisible(llHotLayout,llHistoryLayout);
            setGone(llGridHotLayout);
            llGallery.removeAllViews();
            requestHotWords();
            historyAdapter.clear();
            for (int i = 0; i < shopWords.size(); i++) {
                String word = shopWords.get(i);
                if (StringUtils.isNotEmpty(word)) {
                    historyAdapter.add(word);
                }
            }
            historyAdapter.notifyDataSetChanged();
        }
        //显示店铺的搜索历史记录
        List<String> storeWords = PrefUtility.getSearchStoreHistory();
        if (null != storeWords && 0 != storeWords.size()) {
            historyStoreAdapter.clear();
            for (int i = 0; i < storeWords.size(); i++) {
                String word = storeWords.get(i);
                if (StringUtils.isNotEmpty(word)) {
                    historyStoreAdapter.add(word);
                }
            }
            historyStoreAdapter.notifyDataSetChanged();
        }
    }

    public void initView() {
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            searchContent.setHint(R.string.str_searc_books);
            shopStoreTv.setText("商品");
            shopStoreTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else {
            searchContent.setHint(R.string.str_searc_goods);
        }
        mContext = SearchActivity.this;
        isFromHome = getIntent().getBooleanExtra("home", false);
        currentCatgCode = getIntent().getStringExtra(Constant.SHOP_CATG_CODE);
        if (currentCatgCode == null) {
            currentCatgCode = "";
        }
        searchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = searchContent.getText().toString().length();
                searchContent.setSelection(length);
                //商品搜索
                if (StringUtils.equals("商品",shopStoreTv.getText().toString()))
                {
                    if (length > 0) {
                        setVisible(searchClose,completeListView);
                        setGone(hotWordLayout);
                        updateAutoCompleteText();
                    } else {
                        setGone(completeListView,searchClose);
                        setVisible(hotWordLayout);
                    }
                }
                //店铺搜索
                else if (StringUtils.equals("店铺", shopStoreTv.getText().toString()))
                {
                    if (length > 0) {
                        setVisible(searchClose,listViewStoreComplete);
                        setGone(layoutStoreSearchLinear);
                        requestGds022();
                    } else {
                        setGone(searchClose,listViewStoreComplete);
                        setVisible(layoutStoreSearchLinear);
                    }
                }
            }
        });

        searchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (StringUtils.equals("商品", shopStoreTv.getText().toString()))
                    {
                        //属于商品搜索
                        if (StringUtils.isNotEmpty(searchContent.getText().toString())) {
                            currentKeyWord = searchContent.getText().toString();
                            insertSearchHistory(currentKeyWord);
                        }
                        Intent intent = new Intent(mContext, SearchResultActivity.class);
                        intent.putExtra(Constant.SHOP_KEY_WORD, searchContent.getText().toString());
                        intent.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
                        launch(intent);
                    }
                    else if (StringUtils.equals("店铺", shopStoreTv.getText().toString()))
                    {
                        //属于店铺搜索
                        if (StringUtils.isNotEmpty(searchContent.getText().toString())) {
                            insertSearchHistory(searchContent.getText().toString());
                        }
                        Intent intent = new Intent(mContext, SearchStoreActivity.class);
                        intent.putExtra(Constant.SHOP_KEY_WORD, searchContent.getText().toString());
                        launch(intent);
                    }
                }
                return false;
            }
        });

        completeAdapter = new CompleteAdapter(mContext, R.layout.item_complete);
        completeListView.setAdapter(completeAdapter);
        completeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CollationReuslt result = (CollationReuslt) completeAdapter.getItem(position);
                if (result != null) {
                    currentKeyWord = result.getCollationQueryString();
                    insertSearchHistory(currentKeyWord);
                }
                Intent intent = new Intent(mContext, SearchResultActivity.class);
                intent.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
                intent.putExtra(Constant.SHOP_KEY_WORD, currentKeyWord);
                launch(intent);
            }
        });

        initHotWordView();
        initStoreData();
    }

    @Override
    public void initData() {

    }

    private void initHotWordView() {
//        if (!isFromHome) {
//            setGone(hotWordLayout);
//            return;
//        }

        hotWordsAdapter = new HotWordsAdapter(mContext, R.layout.item_prop);
        hotWordsGridView.setAdapter(hotWordsAdapter);

        historyAdapter = new ArrayAdapter<>(mContext, R.layout.simple_listview_item);
        historyListView.setAdapter(historyAdapter);

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentKeyWord = historyAdapter.getItem(position);
                Intent intent = new Intent(mContext, SearchResultActivity.class);
                intent.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
                intent.putExtra(Constant.SHOP_KEY_WORD, currentKeyWord);
                launch(intent);
            }
        });
        setVisible(hotWordLayout);
        List<String> words = querySearchHistory();
        if ((null != words) && (0 != words.size())) {
            setGone(llGridHotLayout);
            setVisible(llHotLayout,llHistoryLayout);
            for (int i = 0; i < words.size(); i++) {
                historyAdapter.add(words.get(i));
            }
            historyAdapter.notifyDataSetChanged();
        } else  {
            setGone(llHotLayout,llHistoryLayout);
            setVisible(llGridHotLayout);
            requestGridHotWords();
            hotWordsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentKeyWord = (String) hotWordsAdapter.getItem(position);
                    insertSearchHistory(currentKeyWord);
                    Intent intent = new Intent(mContext, SearchResultActivity.class);
                    intent.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
                    intent.putExtra(Constant.SHOP_KEY_WORD, currentKeyWord);
                    launch(intent);
                }
            });
        }
    }

    private void requestHotWords() {
        Gds007Req req = new Gds007Req();
        getJsonService().requestGds007(this, req, true, new JsonService.CallBack<Gds007Resp>() {
            @Override
            public void oncallback(Gds007Resp gds007Resp) {
                List<String> hotWords = gds007Resp.getHotkeywords();
                if (hotWords != null && hotWords.size() != 0) {
                    for (int i = 0; i < hotWords.size(); i++) {
                        View hotView = LayoutInflater.from(mContext).inflate(R.layout.inte_activity_search_item_hot_word, llGallery, false);
                        final TextView tvHotWord = (TextView) hotView.findViewById(R.id.tv_hot_word);
                        tvHotWord.setText(hotWords.get(i));
                        llGallery.addView(hotView);
                        tvHotWord.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                currentKeyWord = tvHotWord.getText().toString();
                                insertSearchHistory(currentKeyWord);
                                Intent intent = new Intent(mContext, SearchResultActivity.class);
                                intent.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
                                intent.putExtra(Constant.SHOP_KEY_WORD, currentKeyWord);
                                launch(intent);
                            }
                        });
                    }
                } else {
                    setGone(llGallery);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void requestGridHotWords() {
        Gds007Req req = new Gds007Req();
        getJsonService().requestGds007(mContext, req, false, new JsonService.CallBack<Gds007Resp>() {
            @Override
            public void oncallback(Gds007Resp gds007Resp) {
                if (0 == gds007Resp.getHotkeywords().size()) {
                    setGone(hotWordLayout);
                    return;
                }
                for (int i = 0; i < gds007Resp.getHotkeywords().size(); i++) {
                    String word = gds007Resp.getHotkeywords().get(i);
                    hotWordsAdapter.add(word);
                }
                hotWordsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void initStoreData() {

        historyStoreAdapter = new ArrayAdapter<>(mContext, R.layout.simple_listview_item);
        listViewStore.setAdapter(historyStoreAdapter);

        completeStoreAdapter = new CompleteStoreAdapter(mContext, R.layout.item_complete);
        listViewStoreComplete.setAdapter(completeStoreAdapter);

        List<String> words = PrefUtility.getSearchStoreHistory();
        if (words == null || words.size() == 0) {
            setGone(layoutStoreSearchLinear);
        } else {
            setVisible(layoutStoreSearchLinear);
        }

        //点击店铺搜索推荐列表，跳转搜索结果页面
        listViewStoreComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CollationReuslt result = (CollationReuslt) completeStoreAdapter.getItem(position);
                if (result != null) {
                    PrefUtility.putSearchStoreHistory(result.getCollationQueryString());
                    Intent intent = new Intent(mContext, SearchStoreActivity.class);
                    intent.putExtra(Constant.SHOP_KEY_WORD, result.getCollationQueryString());
                    launch(intent);
                }
            }
        });

        //点击店铺搜索历史记录，跳转至搜索结果页面
        listViewStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchContent.setText(historyStoreAdapter.getItem(position));
                Intent intent = new Intent(mContext, SearchStoreActivity.class);
                intent.putExtra(Constant.SHOP_KEY_WORD, historyStoreAdapter.getItem(position));
                launch(intent);
            }
        });

        emptyStoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtility.removeSearchStoreHistory();
                layoutStoreSearchLinear.setVisibility(View.GONE);
                historyStoreAdapter.clear();
                historyStoreAdapter.notifyDataSetChanged();
            }
        });

    }

    /**
     * 查询历史搜索记录
     * @return
     */
    private List<String> querySearchHistory() {
        mHelper = new SearchHistoryHelper(this);
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + Constant.HISTORY_TABLENAME + " order by _id desc", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex("h_name"));
            list.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 插入数据
     * @param search
     */
    private void insertSearchHistory(String search) {
        mHelper = new SearchHistoryHelper(this);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("insert or ignore into " + Constant.HISTORY_TABLENAME + "(h_name)" + " values('" + search + "')");
        db.close();
    }

    /**
     * 清空数据
     */
    private void deleteHistory() {
        mHelper = new SearchHistoryHelper(this);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("delete from " + Constant.HISTORY_TABLENAME);
        db.close();
    }

    @Override
    @OnClick({R.id.title_goodlist_back, R.id.shop_good_tv, R.id.title_goodlist_close, R.id.title_goodlist_search, R.id.history_empyt})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_goodlist_back:
                launch(MainActivity.class);
                break;

            case R.id.shop_good_tv:
                if (!StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    selectShopOrStore(shopStoreTv);
                }
                break;

            case R.id.title_goodlist_close:
                searchContent.setText(null);
                List<String> historyList = querySearchHistory();
                if (historyList!=null && historyList.size()!=0) {
                    setVisible(llHotLayout,llHistoryLayout);
                    setGone(llGridHotLayout,completeListView,searchClose);
                    llGallery.removeAllViews();
                    requestHotWords();
                } else {
                    setVisible(llGridHotLayout);
                    setGone(llHotLayout,llHistoryLayout);
                }
                break;

            case R.id.title_goodlist_search:
                if (StringUtils.equals("商品", shopStoreTv.getText().toString()))
                {
                    //属于商品搜索
                    if (StringUtils.isNotEmpty(searchContent.getText().toString())) {
                        currentKeyWord = searchContent.getText().toString();
                        insertSearchHistory(currentKeyWord);
                    }
                    Intent intent = new Intent(mContext, SearchResultActivity.class);
                    intent.putExtra(Constant.SHOP_KEY_WORD, searchContent.getText().toString());
                    intent.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
                    launch(intent);
                }
                else if (StringUtils.equals("店铺", shopStoreTv.getText().toString()))
                {
                    //属于店铺搜索
                    if (StringUtils.isNotEmpty(searchContent.getText().toString())) {
                        insertSearchHistory(searchContent.getText().toString());
                    }
                    Intent intent = new Intent(mContext, SearchStoreActivity.class);
                    intent.putExtra(Constant.SHOP_KEY_WORD, searchContent.getText().toString());
                    launch(intent);
                }
                break;

            case R.id.history_empyt:
                deleteHistory();
                setVisible(llGridHotLayout);
                setGone(llHotLayout,llHistoryLayout);
                if (null != historyAdapter) {
                    historyAdapter.clear();
                    historyAdapter.notifyDataSetChanged();
                }
                hotWordsAdapter = new HotWordsAdapter(mContext, R.layout.item_prop);
                hotWordsGridView.setAdapter(hotWordsAdapter);
                requestGridHotWords();
                hotWordsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        currentKeyWord = (String) hotWordsAdapter.getItem(position);
                        insertSearchHistory(currentKeyWord);
                        Intent intent = new Intent(mContext, SearchResultActivity.class);
                        intent.putExtra(Constant.SHOP_KEY_WORD, currentKeyWord);
                        if (StringUtils.isNotEmpty(currentCatgCode)) {
                            intent.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
                        }
                        launch(intent);
                    }
                });
                break;
        }
    }

    /**
     * 搜索店铺的建议列表展示
     */
    private void requestGds022() {
        Gds022Req gds022Req = new Gds022Req();
        gds022Req.setKeyword(searchContent.getText().toString().trim());
        getJsonService().requestGds022(mContext, gds022Req, false, new JsonService.CallBack<Gds022Resp>() {
            @Override
            public void oncallback(Gds022Resp gds022Resp) {
                completeStoreAdapter.clear();
                if (0 != gds022Resp.getCollationReuslts().size()) {
                    for (int i = 0; i < gds022Resp.getCollationReuslts().size(); i++) {
                        CollationReuslt reuslt = gds022Resp.getCollationReuslts().get(i);
                        completeStoreAdapter.add(reuslt);
                    }
                }
                completeStoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void selectShopOrStore(final View asView) {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        } else {
            View popView = LayoutInflater.from(mContext).inflate(R.layout.popu_search_laout, null);
            LinearLayout shopLayout = (LinearLayout) popView.findViewById(R.id.shop_layout);
            LinearLayout storeLayout = (LinearLayout) popView.findViewById(R.id.store_layout);
            popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            //点击外面收起
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupWindow.showAsDropDown(asView);

            shopLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((TextView) asView).setText("商品");
                    searchContent.setHint("搜索商品");
                    setVisible(shopFrameLayout);
                    setGone(layoutStoreSearchFrame);
                    if (StringUtils.isNotEmpty(searchContent.getText().toString())) {
                        setVisible(completeListView);
                    }
                    popupWindow.dismiss();
                }
            });

            storeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((TextView) asView).setText("店铺");
                    searchContent.setHint("搜索店铺");
                    setVisible(layoutStoreSearchFrame);
                    setGone(shopFrameLayout);
                    if (StringUtils.isNotEmpty(searchContent.getText().toString())) {
                        setVisible(listViewStoreComplete);
                    }
                    popupWindow.dismiss();
                }
            });
        }
    }

    private void updateAutoCompleteText() {
        Gds011Req req = new Gds011Req();
        req.setKeyword(searchContent.getText().toString());
        getJsonService().requestGds011(mContext, req, false, new JsonService.CallBack<Gds011Resp>() {
            @Override
            public void oncallback(Gds011Resp gds011Resp) {
                completeAdapter.clear();
                if (0 != gds011Resp.getCollationReuslts().size()) {
                    for (int i = 0; i < gds011Resp.getCollationReuslts().size(); i++) {
                        CollationReuslt reuslt = gds011Resp.getCollationReuslts().get(i);
                        completeAdapter.add(reuslt);
                    }
                }
                completeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 热词
     */
    private class HotWordsAdapter extends ListViewAdapter {
        public HotWordsAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public void initView(View view, int position, View convertView) {
            TextView textView = (TextView) view.findViewById(R.id.prop_textview);
            String word = (String) getItem(position);
            textView.setText(word);
        }
    }

    /**
     * 搜索商品自动提示
     */
    private class CompleteAdapter extends ListViewAdapter {

        public CompleteAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public void initView(View view, int position, View convertView) {
            TextView textView = (TextView) view.findViewById(R.id.item_complete_textview);
            CollationReuslt reuslt = (CollationReuslt) getItem(position);
            if (reuslt != null) {
                textView.setText(reuslt.getCollationQueryString());
            }
        }
    }

    /**
     * 搜索店铺自动提示
     */
    private class CompleteStoreAdapter extends ListViewAdapter {

        public CompleteStoreAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public void initView(View view, int position, View convertView) {
            TextView textView = (TextView) view.findViewById(R.id.item_complete_textview);
            CollationReuslt reuslt = (CollationReuslt) getItem(position);
            if (reuslt != null) {
                textView.setText(reuslt.getCollationQueryString());
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            launch(MainActivity.class);
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
