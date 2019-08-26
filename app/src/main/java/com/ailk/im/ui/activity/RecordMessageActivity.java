package com.ailk.im.ui.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ai.ecp.app.req.IM004Req;
import com.ai.ecp.app.resp.IM004Resp;
import com.ai.ecp.app.resp.vo.MessageHistoryRespVO;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.im.net.JsonMapFunc;
import com.ailk.im.net.NetCenter;
import com.ailk.im.ui.adapter.MessageAdapter;
import com.ailk.imsdk.bean.message.IMessage;
import com.ailk.imsdk.bean.message.body.IMessageBody;
import com.ailk.imsdk.rx.OnCompleteAction;
import com.ailk.imsdk.rx.OnNextAction;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.ToastUtil;
import com.apkfuns.logutils.LogUtils;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exception.XhsException;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Project : pmph_android
 * Created by 王可 on 2017/3/2.
 */

public class RecordMessageActivity extends AppCompatActivity implements OnRefreshListener {
    private static final int UUID_LENGTH = 36;
    RecyclerView recyclerView;
    SwipeToLoadLayout swipeToLoadLayout;
    MessageAdapter adapter;
    List<Subscription> subscriptions = new ArrayList<>();


    /**
     * 当前用户账号
     */
    private String mAccount;
    /**
     * 对方用户账号
     */
    private String serviceAccount;
    /**
     * 客服头像
     */
    private String servicePhoto;
    /**
     * 买家头像
     */
    private String mPhoto;
    private long mShopId;


    final int pageSize = 10;
    private Date mBeginDate = null;


    @Override
    protected void onCreate(@Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_record);
        obtainDataFromIntent();
        initToolbar();
        recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        adapter = new MessageAdapter(RecordMessageActivity.this, mAccount, mPhoto, serviceAccount, servicePhoto);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecordMessageActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.requestFocus();
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipe_refresh);
        swipeToLoadLayout.setOnRefreshListener(this);
        adapter.clear();
        adapter.notifyDataSetChanged();


    }

    @Override
    protected void onResume() {
        super.onResume();


        onRefresh();
    }


    private void onNotifyDataSetChanged() {
        adapter.notifyDataSetChangedA();
    }


    /**
     * 从{@link android.content.Intent 中获取IM所需要的数据 }
     */
    private void obtainDataFromIntent() {

        mAccount = getIntent().getStringExtra("account");
        serviceAccount = getIntent().getStringExtra("serviceAccount");
        String sessionId = getIntent().getStringExtra("sessionId");
        servicePhoto = getIntent().getStringExtra("servicePhoto");
        mPhoto = getIntent().getStringExtra("photo");
        mShopId = getIntent().getLongExtra("shopId", -1L);


        if (TextUtils.isEmpty(mAccount)
                || TextUtils.isEmpty(serviceAccount)
                || TextUtils.isEmpty(sessionId)) {

            Toast.makeText(RecordMessageActivity.this, "参数错误，打开失败", Toast.LENGTH_LONG).show();
            finish();
        }


    }

    private void initToolbar() {
        ImageView back = (ImageView) findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    @Override
    protected void onDestroy() {
        if (null != subscriptions) {
            for (Subscription item : subscriptions) {
                if (!item.isUnsubscribed()) {
                    item.unsubscribe();
                }
            }
        }
        super.onDestroy();
    }

    @Override
    public void onRefresh() {

        IM004Req req = new IM004Req();
        req.setCsaCode(serviceAccount);
        req.setUserCode(mAccount);
        req.setShopId(mShopId);
        req.setBeginDate(mBeginDate);
        req.setPageSize(pageSize);
        req.setPageNo(1);

        NetCenter.build(RecordMessageActivity.this)
                .requestDefault(req, "im004")
                .map(new JsonMapFunc<AppBody, IM004Resp>())
                .concatMap(new Func1<IM004Resp, Observable<MessageHistoryRespVO>>() {
                    @Override
                    public Observable<MessageHistoryRespVO> call(IM004Resp im004Resp) {
                        return Observable.from(im004Resp.getPageResp());
                    }
                })
                .map(new Func1<MessageHistoryRespVO, IMessage>() {
                    @Override
                    public IMessage call(MessageHistoryRespVO messageHistoryRespVO) {
                        if (null == mBeginDate) {
                            mBeginDate = new Date(messageHistoryRespVO.getBeginDate().getTime());
                        } else {
                            //取返回数据中beginTime最小的
                            long longBeginTime = mBeginDate.getTime();
                            long longRecorTime = messageHistoryRespVO.getBeginDate().getTime();
                            mBeginDate = longRecorTime < mBeginDate.getTime() ? new Date(longRecorTime) : new Date(longBeginTime);
                        }


                        IMessage iMessage = new IMessage();
                        iMessage.setSendTo(messageHistoryRespVO.getTo());
                        iMessage.setReceiveFrom(messageHistoryRespVO.getFrom());
                        iMessage.setMessageType(messageHistoryRespVO.getMessageType());
                        iMessage.setId(messageHistoryRespVO.getId());
                        iMessage.setExtraId(processExtraId(messageHistoryRespVO.getId()));


                        IMessageBody iMessageBody = new IMessageBody();
                        iMessageBody.setSessionId(messageHistoryRespVO.getSessionId());
                        iMessageBody.setContentType(messageHistoryRespVO.getContentType());
                        iMessageBody.setMessagetype(messageHistoryRespVO.getMessageType());
                        iMessageBody.setBody(messageHistoryRespVO.getBody());
                        iMessageBody.setSendTime(messageHistoryRespVO.getBeginDate().getTime());

                        iMessage.setMessageBody(iMessageBody);


                        return iMessage;
                    }
                }).subscribe(new OnNextAction<IMessage>() {
            @Override
            public void onNext(IMessage iMessage) {
                adapter.insert(iMessage);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                handleError(throwable);
            }
        }, new OnCompleteAction() {
            @Override
            public void onComplete() {
                onRefreshCompleted(false);
                onNotifyDataSetChanged();
            }
        });

    }

    private Long processExtraId(String stanzaId) {
        Long extraId;
        if (TextUtils.isEmpty(stanzaId)) {
            extraId = null;
        } else if (UUID_LENGTH == stanzaId.length()) {
            extraId = null;
        } else {
            try {

                extraId = Long.valueOf(stanzaId.substring(UUID_LENGTH + 1, stanzaId.length()));
            } catch (Exception e) {
                LogUtils.e(e);
                extraId = null;
            }


        }

        return extraId;
    }

    /**
     * @param loadMore true表示为下拉刷新 false表示上方刷新
     */
    public void onRefreshCompleted(boolean loadMore) {
        if (null == swipeToLoadLayout) {
            return;
        }
        if (loadMore) {
            swipeToLoadLayout.setLoadingMore(false);
        } else {
            swipeToLoadLayout.setRefreshing(false);
        }
    }

    /**
     * 异常的统一处理
     *
     * @param throwable 异常
     */
    private void handleError(Throwable throwable) {
        String errorMsg;
        if (throwable instanceof XhsException) {
            errorMsg = ((XhsException) throwable).getDisplayMessage();
        } else {
            errorMsg = throwable.getMessage();
        }
        ToastUtil.show(RecordMessageActivity.this, errorMsg);
    }
}
