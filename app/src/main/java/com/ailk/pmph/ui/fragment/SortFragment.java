package com.ailk.pmph.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ai.ecp.app.req.Cms007Req;
import com.ai.ecp.app.resp.Cms007Resp;
import com.ai.ecp.app.resp.cms.CategoryRespVO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.ui.activity.MessageActivity;
import com.ailk.pmph.ui.activity.ScanActivity;
import com.ailk.pmph.ui.activity.SearchActivity;
import com.ailk.pmph.ui.adapter.SortListAdapter;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ToastUtil;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/10.
 * 首页->分类的fragment界面
 */
public class SortFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;
    @BindView(R.id.lv_sort_list)
    ListView mListView;

    private Cms007Req mRequest;
    private SortListAdapter mAdapter;
    private SortChildFragment mFragment;
    private List<CategoryRespVO> mList;
    private CategoryRespVO mData;
    public static int mPosition = 0;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    public static SortFragment newInstance() {
        SortFragment fragment = new SortFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_sort;
    }

    public void initData() {

    }

    public void initView(View view) {
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvSearch.setText(getActivity().getResources().getString(R.string.str_searc_books));
        } else {
            tvSearch.setText(getActivity().getResources().getString(R.string.str_searc_goods));
        }
        mRequest = new Cms007Req();
        mList = new ArrayList<>();
        mData = new CategoryRespVO();
        getSortOneList();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                mAdapter.notifyDataSetChanged();
                mData = mList.get(position);
                if (mData != null) {
                    mFragment = new SortChildFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fl_child_sort, mFragment);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SortChildFragment.TAG, mData);
                    mFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    private void getSortOneList() {
        mRequest.setPlaceId(2101L);
        getJsonService().requestCms007(getActivity(), mRequest, true, new JsonService.CallBack<Cms007Resp>() {
            @Override
            public void oncallback(Cms007Resp cms007Resp) {
                mList = cms007Resp.getCatgList();
                if (mList != null && mList.size() != 0) {
                    mAdapter = new SortListAdapter(getActivity(), mList);
                    mListView.setAdapter(mAdapter);
                    mFragment = new SortChildFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fl_child_sort, mFragment);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SortChildFragment.TAG, mList.get(mPosition));
                    mFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    @OnClick({R.id.iv_scan,R.id.search_layout,R.id.iv_message})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_scan:
                checkPermission();
                break;
            case R.id.search_layout:
                launch(SearchActivity.class);
                break;
            case R.id.iv_message:
                if (AppContext.isLogin) {
                    launch(MessageActivity.class);
                } else {
                    showLoginDialog(getActivity());
                }
                break;
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else
        {
            launch(ScanActivity.class);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launch(ScanActivity.class);
            } else {
                ToastUtil.showCenter(getActivity(), "permission denied");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
