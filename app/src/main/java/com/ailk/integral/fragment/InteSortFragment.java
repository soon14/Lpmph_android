package com.ailk.integral.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ai.ecp.app.req.Cms102Req;
import com.ai.ecp.app.resp.Cms102Resp;
import com.ai.ecp.app.resp.cms.CategoryRespVO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.integral.adapter.InteCatgListAdapter;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:积分分类
 * 项目名:pmph_android
 * 包名:com.ailk.integral.frag
 * 作者: Chrizz
 * 时间: 2016/5/11 15:18
 */
public class InteSortFragment extends BaseFragment{

    @BindView(R.id.lv_catg_list)
    ListView lvCatgList;

    private InteChildCatgFragment fragment;
    private List<CategoryRespVO> catgList;
    private InteCatgListAdapter adapter;
    private CategoryRespVO catg;
    public static int mPosition = 0;

    public static InteSortFragment newInstance() {
        InteSortFragment fragment = new InteSortFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.inte_fragment_sort;
    }

    @Override
    public void initData() {

    }

    public void initView(View view){
        catgList = new ArrayList<>();
        catg = new CategoryRespVO();
        getSortOneList();

        lvCatgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                adapter.notifyDataSetChanged();
                catg = catgList.get(position);
                if (catg != null) {
                    fragment = new InteChildCatgFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fl_child_catg, fragment);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(InteChildCatgFragment.TAG, catg);
                    fragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    private void getSortOneList(){
        Cms102Req req = new Cms102Req();
        getInteJsonService().requestCms102(getActivity(), req, true, new InteJsonService.CallBack<Cms102Resp>() {
            @Override
            public void oncallback(Cms102Resp cms102Resp) {
                catgList = cms102Resp.getCatgList();
                if (catgList != null && catgList.size() != 0) {
                    adapter = new InteCatgListAdapter(getActivity(), catgList);
                    lvCatgList.setAdapter(adapter);
                    fragment = new InteChildCatgFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fl_child_catg, fragment);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(InteChildCatgFragment.TAG, catgList.get(mPosition));
                    fragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
