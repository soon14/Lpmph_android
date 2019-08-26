package com.ailk.integral.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ai.ecp.app.req.Pointmgds007Req;
import com.ai.ecp.app.req.Pointmgds011Req;
import com.ai.ecp.app.resp.Pointmgds007Resp;
import com.ai.ecp.app.resp.Pointmgds011Resp;
import com.ai.ecp.search.dubbo.search.result.CollationReuslt;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ListViewAdapter;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.view.CustomGridView;
import com.ailk.pmph.utils.SearchHistoryHelper;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.act
 * 作者: Chrizz
 * 时间: 2016/5/22 17:31
 */
public class InteSearchActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_search_close)
    ImageView ivSearchClose;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_hot_word_gallery)
    LinearLayout llGallery;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.lv_complete)
    ListView lvComplete;
    @BindView(R.id.ll_grid_hot_layout)
    LinearLayout llGridHotLayout;
    @BindView(R.id.gv_hot_word)
    CustomGridView gvHotWord;
    @BindView(R.id.ll_hot_layout)
    LinearLayout llHotLayout;
    @BindView(R.id.ll_history_layout)
    LinearLayout llHistoryLayout;
    @BindView(R.id.lv_history)
    ListView lvHistory;

    private String currentKeyWord = "";
    private String currentCatgCode = "";
    private boolean isComplete = true;
    private HotWordAdapter hotWordAdapter;
    private ArrayAdapter<String> historyAdapter;
    private CompleteAdapter completeAdapter;
    private SearchHistoryHelper mHelper;

    @Override
    protected int getContentViewId() {
        return R.layout.inte_activity_search;
    }

    @Override
    public void initView() {
        initSearchView();
        initHotWordsView();
    }

    @Override
    public void initData() {

    }

    private void initSearchView(){
        try {
            currentCatgCode = getIntent().getStringExtra(Constant.SHOP_CATG_CODE);
        } catch (Exception e) {
            currentCatgCode = "";
        }
        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etSearch.getText().toString().length() > 0) {
                    setGone(llHotLayout,llHistoryLayout,llGridHotLayout);
                    setVisible(ivSearchClose);
                } else {
                    setGone(ivSearchClose);
                    if (querySearchHistory() != null) {
                        if (querySearchHistory().size()==0) {
                            setVisible(llGridHotLayout);
                            setGone(llHotLayout,llHistoryLayout);
                        } else {
                            setGone(llGridHotLayout);
                            setVisible(llHotLayout,llHistoryLayout);
                        }
                    }
                }
                updateAutoCompleteText();
            }
        };
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etSearch.addTextChangedListener(textWatcher);
                } else {
                    etSearch.removeTextChangedListener(textWatcher);
                }
            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case 3:
                        return tvSearch.callOnClick();
                    default:
                        break;
                }
                return false;
            }
        });
        completeAdapter = new CompleteAdapter(this, R.layout.item_complete);
        lvComplete.setAdapter(completeAdapter);
        lvComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etSearch.setFocusableInTouchMode(false);
                etSearch.setFocusable(false);
                CollationReuslt result = (CollationReuslt) completeAdapter.getItem(position);
                setGone(lvComplete);
                if (result != null) {
                    currentKeyWord = result.getCollationQueryString();
                    insertSearchHistory(currentKeyWord);
                    etSearch.setText(currentKeyWord);
                    Intent intent = new Intent(InteSearchActivity.this, InteSearchResultActivity.class);
                    intent.putExtra(Constant.SHOP_KEY_WORD, currentKeyWord);
                    intent.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
                    launch(intent);
                }
            }
        });
    }

    private void updateAutoCompleteText() {
        Pointmgds011Req req = new Pointmgds011Req();
        req.setKeyword(etSearch.getText().toString());
        getInteJsonService().requestPointGds011(this, req, false, new InteJsonService.CallBack<Pointmgds011Resp>() {
            @Override
            public void oncallback(Pointmgds011Resp pointmgds011Resp) {
                if (!isComplete) {
                    setGone(lvComplete);
                    isComplete = true;
                    return;
                }
                completeAdapter.clear();
                if (0 == pointmgds011Resp.getCollationReuslts().size()) {
                    setGone(lvComplete);
                } else {
                    for (int i = 0; i < pointmgds011Resp.getCollationReuslts().size(); i++) {
                        CollationReuslt reuslt = pointmgds011Resp.getCollationReuslts().get(i);
                        completeAdapter.add(reuslt);
                    }
                    setVisible(lvComplete);
                    setGone(llHotLayout,llHistoryLayout);
                }
                completeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void initHotWordsView(){
        List<String> words = querySearchHistory();
        if (words == null || words.size() == 0) {
            setGone(llHotLayout,llHistoryLayout);
            setVisible(llGridHotLayout);
            hotWordAdapter = new HotWordAdapter(this, R.layout.item_prop);
            gvHotWord.setAdapter(hotWordAdapter);
            requestGridHotWords();
            gvHotWord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentKeyWord = (String) hotWordAdapter.getItem(position);
                    insertSearchHistory(currentKeyWord);
                    hotWordAdapter.clear();
                    Intent intent = new Intent(InteSearchActivity.this, InteSearchResultActivity.class);
                    intent.putExtra(Constant.SHOP_KEY_WORD, currentKeyWord);
                    intent.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
                    launch(intent);
                }
            });
        }
        historyAdapter = new ArrayAdapter<>(this, R.layout.simple_listview_item);
        lvHistory.setAdapter(historyAdapter);
        if ((null != words) && (0 != words.size())) {
            setVisible(llHotLayout,llHistoryLayout);
            setGone(llGridHotLayout);
            requestHotWords();
            for (int i = 0; i < words.size(); i++) {
                historyAdapter.add(words.get(i));
            }
            historyAdapter.notifyDataSetChanged();
        }
        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentKeyWord = historyAdapter.getItem(position);
                etSearch.setText(currentKeyWord);
                Intent intent = new Intent(InteSearchActivity.this, InteSearchResultActivity.class);
                intent.putExtra(Constant.SHOP_KEY_WORD, currentKeyWord);
                intent.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
                launch(intent);
            }
        });
    }

    private void requestHotWords(){
        Pointmgds007Req req = new Pointmgds007Req();
        getInteJsonService().requestPointGds007(this, req, true, new InteJsonService.CallBack<Pointmgds007Resp>() {
            @Override
            public void oncallback(Pointmgds007Resp pointmgds007Resp) {
                List<String> hotWords = pointmgds007Resp.getHotkeywords();
                if (hotWords.size() > 0) {
                    for (int i = 0; i < hotWords.size(); i++) {
                        View hotView = LayoutInflater.from(InteSearchActivity.this).inflate(R.layout.inte_activity_search_item_hot_word, llGallery, false);
                        final TextView tvHotWord = (TextView) hotView.findViewById(R.id.tv_hot_word);
                        tvHotWord.setText(hotWords.get(i));
                        llGallery.addView(hotView);
                        tvHotWord.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setGone(llHotLayout,llHistoryLayout);
                                currentKeyWord = tvHotWord.getText().toString();
                                insertSearchHistory(currentKeyWord);
                                etSearch.setText(currentKeyWord);
                                Intent intent = new Intent(InteSearchActivity.this, InteSearchResultActivity.class);
                                intent.putExtra(Constant.SHOP_KEY_WORD, currentKeyWord);
                                intent.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
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

    private void requestGridHotWords(){
        Pointmgds007Req req = new Pointmgds007Req();
        getInteJsonService().requestPointGds007(this, req, true, new InteJsonService.CallBack<Pointmgds007Resp>() {
            @Override
            public void oncallback(Pointmgds007Resp pointmgds007Resp) {
                List<String> hotWords = pointmgds007Resp.getHotkeywords();
                if (0 == hotWords.size()) {
                    setGone(gvHotWord);
                } else {
                    for (int i = 0; i < hotWords.size(); i++) {
                        hotWordAdapter.add(hotWords.get(i));
                    }
                }
                hotWordAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }


    private class HotWordAdapter extends ListViewAdapter {
        public HotWordAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public void initView(View view, int position, View convertView) {
            TextView textView = (TextView) view.findViewById(R.id.prop_textview);
            String word = (String) getItem(position);
            textView.setText(word);
        }
    }

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
    @OnClick({R.id.iv_back,R.id.et_search,R.id.iv_search_close,R.id.tv_search,R.id.tv_empty})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                launch(InteMainActivity.class);
                break;

            case R.id.et_search:
                etSearch.setFocusableInTouchMode(true);
                break;

            case R.id.iv_search_close:
                etSearch.setText(null);
                setVisible(llHotLayout,llHistoryLayout);
                setGone(lvComplete,ivSearchClose);
                llGallery.removeAllViews();
                requestHotWords();
                break;

            case R.id.tv_search:
                if (StringUtils.isNotEmpty(etSearch.getText().toString())) {
                    insertSearchHistory(etSearch.getText().toString());
                }
                Intent intent = new Intent(this, InteSearchResultActivity.class);
                intent.putExtra(Constant.SHOP_KEY_WORD, etSearch.getText().toString());
                intent.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
                launch(intent);
                onBackPressed();
                break;

            case R.id.tv_empty:
                setVisible(llGridHotLayout);
                setGone(llHotLayout,llHistoryLayout);
                if (null != historyAdapter) {
                    historyAdapter.clear();
                    historyAdapter.notifyDataSetChanged();
                }
                deleteHistory();
                hotWordAdapter = new HotWordAdapter(this, R.layout.item_prop);
                gvHotWord.setAdapter(hotWordAdapter);
                requestGridHotWords();
                gvHotWord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        currentKeyWord = (String) hotWordAdapter.getItem(position);
                        insertSearchHistory(currentKeyWord);
                        etSearch.setText(currentKeyWord);
                        etSearch.setFocusableInTouchMode(false);
                        etSearch.setFocusable(false);
                        hotWordAdapter.clear();
                        Intent intent = new Intent(InteSearchActivity.this, InteSearchResultActivity.class);
                        intent.putExtra(Constant.SHOP_KEY_WORD, currentKeyWord);
                        intent.putExtra(Constant.SHOP_CATG_CODE, currentCatgCode);
                        launch(intent);
                    }
                });
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            launch(InteMainActivity.class);
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
