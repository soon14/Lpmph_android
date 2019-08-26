package com.ailk.integral.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ai.ecp.app.req.Cms102Req;
import com.ai.ecp.app.resp.Cms102Resp;
import com.ai.ecp.app.resp.cms.CategoryRespVO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.integral.activity.InteCatgDetailActivity;
import com.ailk.integral.adapter.InteChildCatgListAdapter;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;

import org.apache.commons.lang.StringUtils;

import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.frag
 * 作者: Chrizz
 * 时间: 2016/5/28 14:52
 */
public class InteChildCatgFragment extends BaseFragment {

    @BindView(R.id.lv_child_catg_list)
    ListView mListView;

    public static final String TAG = "InteChildCatgFragment";

    @Override
    public int getLayoutResId() {
        return R.layout.inte_fragment_child_catg;
    }

    @Override
    public void initData() {

    }

    public void initView(View view){
        final CategoryRespVO mData = (CategoryRespVO) getArguments().get(TAG);
        if (mData != null) {
            if (mData.getChildCatg() != null && mData.getChildCatg().size() != 0) {
                InteChildCatgListAdapter adapter = new InteChildCatgListAdapter(getActivity(), mData.getChildCatg());
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("sortDetail", mData.getChildCatg().get(position));
                        launch(InteCatgDetailActivity.class,bundle);
                    }
                });
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
