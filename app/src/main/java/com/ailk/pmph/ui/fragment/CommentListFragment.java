package com.ailk.pmph.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Gds005Req;
import com.ai.ecp.app.resp.Gds005Resp;
import com.ai.ecp.app.resp.gds.GdsEvalRespBaseInfo;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ListViewAdapter;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.activity.ReplyActivity;
import com.ailk.tool.GlideUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/31.
 */
public class CommentListFragment extends BaseFragment {

    @BindView(R.id.commentListview)
    PullToRefreshListView pullToRefreshListView;
    @BindView(R.id.layout_comment_none)
    View none;

    private Long skuId;
    private Long gdsId;
    private String type;
    private int index = 1;
    private List<GdsEvalRespBaseInfo> commentList;
    private CommentAdapter adapter;
    public static final String REFRESH_LIST = "refresh_list";

    public CommentListFragment() {

    }

    public static CommentListFragment newInstance() {
        CommentListFragment fragment = new CommentListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comment_list;
    }

    @Override
    public void initData() {
        try {
            skuId = Long.parseLong(getArguments().getString(Constant.SHOP_SKU_ID));
        } catch (Exception e) {
            LogUtil.e(e);
            skuId = null;
        }
        try {
            gdsId = Long.parseLong(getArguments().getString(Constant.SHOP_GDS_ID));
        } catch (Exception e) {
            LogUtil.e(e);
            gdsId = null;
        }
        type = getArguments().getString("type");
    }

    @Override
    public void initView(View view) {
        pullToRefreshListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
        pullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("刷新中...");
        adapter = new CommentAdapter(getActivity(), R.layout.item_comment);
        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                updateData();

            }
        });
        IntentFilter refreshList = new IntentFilter();
        refreshList.addAction(REFRESH_LIST);
        getActivity().registerReceiver(refreshListReceiver,refreshList);
    }

    @Override
    public void onResume() {
        super.onResume();
        getFirstData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(refreshListReceiver);
    }

    public BroadcastReceiver refreshListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equals(intent.getAction(), REFRESH_LIST)) {
                getFirstData();
            }
        }
    };

    private void getFirstData() {
        Gds005Req req = new Gds005Req();
        req.setPageSize(Constant.PAGE_SIZE);
        req.setPageNo(index);
        req.setSkuId(skuId);
        req.setGdsId(gdsId);
        req.setEvalType(type);
        getJsonService().requestGds005(getActivity(), req, true, new JsonService.CallBack<Gds005Resp>() {
            @Override
            public void oncallback(Gds005Resp gds005Resp) {
                adapter.clear();
                commentList = gds005Resp.getGdsEvalRespList();
                if (null != gds005Resp.getGdsEvalRespList()) {
                    for (int i = 0; i < gds005Resp.getGdsEvalRespList().size(); i++) {
                        GdsEvalRespBaseInfo comment = gds005Resp.getGdsEvalRespList().get(i);
                        adapter.add(comment);
                    }
                }
                adapter.notifyDataSetChanged();
                if (0 == commentList.size()){
                    setVisible(none);
                    setGone(pullToRefreshListView);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void updateData() {
        Gds005Req req = new Gds005Req();
        req.setGdsId(gdsId);
        req.setEvalType(type);
        req.setPageNo(++index);
        req.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestGds005(getActivity(), req, false, new JsonService.CallBack<Gds005Resp>() {
            @Override
            public void oncallback(Gds005Resp gds005Resp) {
                pullToRefreshListView.onRefreshComplete();
                if (null == gds005Resp.getGdsEvalRespList() || 0 == gds005Resp.getGdsEvalRespList().size()){
                    ToastUtil.show(getActivity(), R.string.toast_load_more_msg);
                    return;
                }
                for (int i = 0; i < gds005Resp.getGdsEvalRespList().size(); i++){
                    GdsEvalRespBaseInfo comment = gds005Resp.getGdsEvalRespList().get(i);
                    adapter.add(comment);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private class CommentAdapter extends ListViewAdapter {
        private CircleImageView commentImg;
        private TextView commentName;
        private TextView commentDate;
        private RatingBar commentRating;
        private TextView commentContent;
        private TextView commentDes;
        private TextView commentBuyDate;
        private TextView commentCounts;
        private RelativeLayout rlComment;

        public CommentAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public void initView(View view, int position, View convertView) {
            rlComment = (RelativeLayout) view.findViewById(R.id.rl_comment);
            commentCounts = (TextView) view.findViewById(R.id.comment_text2);
            commentImg = (CircleImageView) view.findViewById(R.id.comment_img);
            commentName = (TextView) view.findViewById(R.id.comment_name);
            commentDate = (TextView) view.findViewById(R.id.comment_date);
            commentRating = (RatingBar) view.findViewById(R.id.comment_ratingbar);
            commentContent = (TextView) view.findViewById(R.id.comment_content);
            commentDes = (TextView) view.findViewById(R.id.comment_des);
            commentBuyDate = (TextView) view.findViewById(R.id.cooment_buydate);

            final GdsEvalRespBaseInfo comment = (GdsEvalRespBaseInfo) getItem(position);
            if (comment != null) {
                String custPic = comment.getCustPic();
                String staffName = comment.getStaffName();
                String staffCode = PrefUtility.get("staffCode", "");
                GlideUtil.loadImg(getActivity(), custPic, commentImg);
                if (StringUtils.equals(staffName, staffCode)) {
                    commentName.setText(staffName);
                } else {
                    commentName.setText(StringUtils.replace(staffName, StringUtils.substring(staffName, 1, staffName.length() - 1), "***"));
                }
                commentContent.setText(comment.getDetail());
                try{
                    commentRating.setNumStars(comment.getScore());
                    commentRating.setRating(comment.getScore());
                }catch (Exception e){
                    LogUtil.e(e);
                    commentRating.setNumStars(0);
                    commentRating.setRating(0);
                }
                commentCounts.setText("(" + comment.getReplyCount() + ")");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    commentDate.setText(sdf.format(comment.getEvaluationTime()));
                }catch (Exception e){
                    LogUtil.e(e);
                    commentDate.setText("");
                }
                rlComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("gdsEvalRespBaseInfo", comment);
                        launch(ReplyActivity.class, bundle);
                    }
                });
            }
        }
    }
}
