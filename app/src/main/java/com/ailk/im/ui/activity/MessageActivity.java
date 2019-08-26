package com.ailk.im.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ai.ecp.app.req.IM002Req;
import com.ai.ecp.app.req.IM004Req;
import com.ai.ecp.app.req.IM005Req;
import com.ai.ecp.app.req.IM006Req;
import com.ai.ecp.app.req.IM007Req;
import com.ai.ecp.app.resp.IM002Resp;
import com.ai.ecp.app.resp.IM004Resp;
import com.ai.ecp.app.resp.IM005Resp;
import com.ai.ecp.app.resp.IM007Resp;
import com.ai.ecp.app.resp.vo.MessageHistoryRespVO;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.im.model.UploadResp;
import com.ailk.im.net.JsonMapFunc;
import com.ailk.im.net.JsonService;
import com.ailk.im.net.NetCenter;
import com.ailk.im.tool.IMConstant;
import com.ailk.im.ui.adapter.MessageAdapter;
import com.ailk.im.ui.dialog.SatisfactionDialog;
import com.ailk.im.ui.view.EaseChatExtendMenu;
import com.ailk.im.ui.view.EaseChatInputMenu;
import com.ailk.imsdk.IMSDK;
import com.ailk.imsdk.bean.message.IMessage;
import com.ailk.imsdk.bean.message.IMessageOrigin;
import com.ailk.imsdk.bean.message.body.GoodMessageBody;
import com.ailk.imsdk.bean.message.body.IMessageBody;
import com.ailk.imsdk.bean.message.body.OrderMessageBody;
import com.ailk.imsdk.bean.transfer.TransferInfo;
import com.ailk.imsdk.exception.ConnectionException;
import com.ailk.imsdk.exception.IMNullException;
import com.ailk.imsdk.interfaces.IMChatListener;
import com.ailk.imsdk.interfaces.IMResultListener;
import com.ailk.imsdk.rx.OnCompleteAction;
import com.ailk.imsdk.rx.OnErrorDialogAction;
import com.ailk.imsdk.rx.OnNextAction;
import com.ailk.imsdk.smack.extension.SessionExtension;
import com.ailk.imsdk.smack.extension.ShopIdExtension;
import com.ailk.imsdk.smack.extension.UserCodeExtension;
import com.ailk.imsdk.util.ConfigConstant;
import com.ailk.imsdk.util.HandleMsgUtil;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.TDevice;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.tool.DateUtil2;
import com.ailk.tool.ProgressUtil;
import com.apkfuns.logutils.LogUtils;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;

import org.codehaus.jackson.map.ObjectMapper;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exception.XhsException;
import me.iwf.photopicker.PhotoPicker;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Project : pmph_android
 * Created by 王可 on 2017/2/17.
 */

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class MessageActivity extends AppCompatActivity implements SatisfactionDialog.CommitSatisfyListener {
    private static final int UUID_LENGTH = 36;
    RecyclerView recyclerView;
    MessageAdapter adapter;
    protected EaseChatInputMenu inputMenu;
    //    static final int ITEM_TAKE_PICTURE = 1;
    static final int ITEM_PICTURE = 2;
    static final int ITEM_SATISFY = 3;
    static final int ITEM_HISTORY = 4;
    protected int[] itemStrings = {R.string.attach_picture, R.string.attach_satisfy, R.string.attach_history};
    protected int[] itemdrawables = {R.drawable.icon_send_pic, R.drawable.icon_satisfy, R.drawable.icon_history_msg};
    protected int[] itemIds = {ITEM_PICTURE, ITEM_SATISFY, ITEM_HISTORY};
    protected MyItemClickListener extendMenuItemClickListener;
    //    protected String sender;
    private Uri picUri;
    List<Subscription> subscriptions = new ArrayList<>();


    /**
     * 当前用户账号
     */
    private String mAccount;
    /**
     * 当前用户密码
     */
    private String mPassword;
    /**
     * 对方用户账号
     */
    private String serviceAccount;
    /**
     * 本次会话id
     */
    private String sessionId;
    //客服名称
//    private String serviceName;
    /**
     * 客服头像
     */
    private String servicePhoto;
    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 是否第一次进入
     */
    private boolean isFirst = true;

    private PopupWindow mPopupWindow;
    private Long businessType;
    private String businessId;
    private String mPhoto;

    /**
     * 用于历史记录获取的begin date
     */
    private Date mBeginDate = null;

    public static final String ORDER_MGS = "orderMsg";
    public static final String GDS_MSG = "gdsMsg";
    private boolean isSatisfy = false;

    /**
     * 扩展菜单栏item点击事件
     */
    class MyItemClickListener implements EaseChatExtendMenu.EaseChatExtendMenuItemClickListener {
        @Override
        public void onClick(int itemId, View view) {
            switch (itemId) {
//                case ITEM_TAKE_PICTURE: // 拍照
//                    takePicture();
//                    break;
                case ITEM_PICTURE:
                    getPicture(); // 图库选择图片
                    break;
                case ITEM_SATISFY:
                    if (!isSatisfy) {
                        SatisfactionDialog dialog = new SatisfactionDialog(MessageActivity.this, R.style.my_dialog_style);
                        dialog.setShopId(shopId);
                        dialog.setCsaCode(serviceAccount);
                        dialog.setUserCode(mAccount);
                        dialog.setSessionId(sessionId);
                        dialog.setListener(MessageActivity.this);
                        dialog.show();
                    } else {
                        ToastUtil.show(MessageActivity.this, "已经对客服人员进行满意度评价");
                    }
                    break;
                case ITEM_HISTORY:
                    if (!TDevice.isNetConnected(MessageActivity.this)) {
                        ToastUtil.show(MessageActivity.this, "当前网络已断开");
                    } else {
                        Intent intent = new Intent(MessageActivity.this, RecordMessageActivity.class);
                        intent.putExtra("account", mAccount);
                        intent.putExtra("serviceAccount", serviceAccount);
                        intent.putExtra("sessionId", sessionId);
                        intent.putExtra("servicePhoto", servicePhoto);
                        intent.putExtra("photo", mPhoto);
                        intent.putExtra("shopId", shopId);
                        startActivity(intent);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    void getPicture() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(false)
                .setPreviewEnabled(true)
                .start(MessageActivity.this);

//        PhotoPickerIntent intent = new PhotoPickerIntent(MessageActivity.this);
//        intent.setPhotoCount(1);
//        intent.setShowCamera(false);
//
//        startActivityForResult(intent, IMConstant.SELECT_PICTURE);
    }

    void takePicture() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, IMConstant.REQUEST_TAKE_PHOTO);
                return;
            }
        }

        Intent takePictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(android.os.Environment.getExternalStorageDirectory(),
                "temp.jpg");
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        picUri = Uri.fromFile(photo);
        startActivityForResult(takePictureIntent, IMConstant.TAKE_PICTURE);


    }

    @Override
    public void commitSatisfy(Long shopId, String userCode, String csaCode, String sessionId,
                              String satisfyType, String notSatisfyType, String notSatisfyReason) {
        if (!TDevice.isNetConnected(this)) {
            ToastUtil.show(this, "当前网络已断开");
        } else {
            IM002Req req = new IM002Req();
            req.setShopId(shopId);
            req.setUserCode(userCode);
            req.setCsaCode(csaCode);
            req.setSessionId(sessionId);
            req.setSatisfyType(satisfyType);
            req.setNotSatisfyType(notSatisfyType);
            req.setNotSatisfyReason(notSatisfyReason);
            DialogUtil.showCustomerProgressDialog(this);
            NetCenter.build(this)
                    .requestDefault(req, "im002")
                    .map(new Func1<AppBody, IM002Resp>() {
                        @Override
                        public IM002Resp call(AppBody appBody) {
                            return null == appBody ? null : (IM002Resp) appBody;
                        }
                    })
                    .filter(new Func1<IM002Resp, Boolean>() {
                        @Override
                        public Boolean call(IM002Resp im002Resp) {
                            return !(null == im002Resp);
                        }
                    })
                    .subscribe(new Action1<IM002Resp>() {
                                   @Override
                                   public void call(IM002Resp im002Resp) {

                                   }
                               }, new OnErrorDialogAction() {
                                   @Override
                                   public void onError(Throwable throwable) {
                                       DialogUtil.dismissDialog();
                                   }

                                   @Override
                                   public Context getContext() {
                                       return MessageActivity.this;
                                   }
                               }, new OnCompleteAction() {
                                   @Override
                                   public void onComplete() {
                                       isSatisfy = true;
                                       DialogUtil.dismissDialog();
                                       ToastUtil.show(MessageActivity.this, "评价成功");
                                   }
                               }
                    );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == IMConstant.REQUEST_TAKE_PHOTO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePicture();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_message);
        obtainDataFromIntent();
        initToolbar();
        recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        adapter = new MessageAdapter(MessageActivity.this, mAccount, mPhoto, serviceAccount, servicePhoto);
//        adapter.setItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
//        loadCache();
//        SdkClient.getInstance().addMessageListener(this);
//        if (IMConstant.APPID_SERVICE.equals(id)) {
//            SdkClient.getInstance().addAppSyncListener(this);
//        }
        extendMenuItemClickListener = new MyItemClickListener();
        inputMenu = (EaseChatInputMenu) findViewById(R.id.input_menu);
        registerExtendMenuItem();
        inputMenu.init();
        inputMenu.setChatInputMenuListener(new EaseChatInputMenu.ChatInputMenuListener() {

            @Override
            public void onSendMessage(final String content) {
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.show(MessageActivity.this, "消息不能为空");
                    return;
                }
                IM005Req req = new IM005Req();
                req.setFrom(mAccount + IMConstant.makeMyDomainResource());
                req.setTo(serviceAccount + IMConstant.makeOtherDomainResource());
                req.setBody(content);
                req.setCsaCode(serviceAccount);
                req.setSessionId(sessionId);
                req.setUserCode(mAccount);
                req.setShopId(shopId);
                req.setBusinessId(businessId);
                req.setBusinessType(businessType);
                req.setMessageType(IMessageBody.MSG_TYPE_CHAT);
                req.setContentType(IMessageBody.CONTENT_TYPE_TEXT);

                NetCenter.build(MessageActivity.this)
                        .requestDefault(req, "im005")
                        .map(new Func1<AppBody, IM005Resp>() {
                            @Override
                            public IM005Resp call(AppBody appBody) {
                                return null == appBody ? null : (IM005Resp) appBody;
                            }
                        }).filter(new Func1<IM005Resp, Boolean>() {
                    @Override
                    public Boolean call(IM005Resp im005Resp) {
                        return null != im005Resp;
                    }
                }).subscribe(new Action1<IM005Resp>() {
                    @Override
                    public void call(IM005Resp im005Resp) {
                        sendMsg(content, im005Resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        handleError(throwable);
                    }
                });
            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                return true;
            }
        });
        recyclerView.requestFocus();
        //先获取离线消息，然后初始化IM服务
        obtainRecordMessage();
//        initIM(true);

        IntentFilter filter = new IntentFilter(ORDER_MGS);
        registerReceiver(orderMsgReceiver, filter);
        IntentFilter intentFilter = new IntentFilter(GDS_MSG);
        registerReceiver(gdsMsgReceiver, intentFilter);
        IntentFilter netIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netReceiver, netIntentFilter);

    }

    private void obtainRecordMessage() {
        IM004Req req = new IM004Req();
        req.setCsaCode(serviceAccount);
        req.setUserCode(mAccount);
        req.setShopId(shopId);
        req.setBeginDate(mBeginDate);
        req.setPageSize(10);
        req.setPageNo(1);

        NetCenter.build(MessageActivity.this)
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
                initIM(true);
            }
        }, new OnCompleteAction() {
            @Override
            public void onComplete() {
//                onRefreshCompleted(false);
                initIM(true);
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

    public BroadcastReceiver orderMsgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ORDER_MGS)) {
                OrderMessageBody orderMessageBody = (OrderMessageBody) intent.getExtras().get("orderMessageBody");
                try {
                    if (orderMessageBody != null) {
                        sendOrderMessage(orderMessageBody);
                    }
                } catch (IOException e) {
                    LogUtils.e(e);
                }
            }
        }
    };

    public BroadcastReceiver gdsMsgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GDS_MSG)) {
                GoodMessageBody goodMessageBody = (GoodMessageBody) intent.getExtras().get("goodMessageBody");
                try {
                    if (goodMessageBody != null) {
                        sendGdsMessage(goodMessageBody);
                    }
                } catch (IOException e) {
                    LogUtils.e(e);
                }
            }
        }
    };

    /**
     * 网络状态判断
     */
    public BroadcastReceiver netReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

                ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isAvailable()) {

                    /////////////网络连接
                    if ((null == IMSDK.getMconnection() || !IMSDK.getMconnection().isConnected())
                            && !isFirst) {
                        ToastUtil.show(MessageActivity.this, "当前网络已连接");
                        initIM(false);
                    }


                } else {
                    ////////网络断开
                    ToastUtil.show(MessageActivity.this, "当前网络已断开");
                    IMSDK.getInstance().release();

                }
            }
        }
    };

    private void sendMsg(String content, IM005Resp im005Resp) {
        if (null != mChat) {
            try {
                Message sendMessage = HandleMsgUtil.createTextMessage(mAccount + IMConstant.makeMyDomainResource()
                        , content, serviceAccount + IMConstant.makeOtherDomainResource()
                        , sessionId, im005Resp.getMessageHistory().getBeginDate().getTime());
                sendMessage.setStanzaId(im005Resp.getMessageHistory().getId());
                sendMsg(sendMessage);
                addMsg(sendMessage);
            } catch (IOException e) {
                LogUtils.e(e);
            }

        }
    }

    private void addMsg(Message msg) {
        Observable.just(msg)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Func1<Message, Observable<IMessageOrigin>>() {
                    @Override
                    public Observable<IMessageOrigin> call(Message message) {
                        List<IMessageOrigin> iMessageOrigins = new ArrayList<>();
                        for (Message.Body body : message.getBodies()) {
                            IMessageOrigin iMessageOrigin = new IMessageOrigin();
                            iMessageOrigin.setFrom(mAccount);
                            iMessageOrigin.setTo(serviceAccount);
                            iMessageOrigin.setBodyJson(body.getMessage());
                            iMessageOrigin.setId(message.getStanzaId());
                            iMessageOrigins.add(iMessageOrigin);
                        }
                        return Observable.from(iMessageOrigins);
                    }
                })
                .map(new Func1<IMessageOrigin, IMessage>() {
                    @Override
                    public IMessage call(IMessageOrigin iMessageOrigin) {
                        IMessage imessage = new IMessage();
                        imessage.setSendTo(iMessageOrigin.getTo());
                        imessage.setReceiveFrom(iMessageOrigin.getFrom());
                        imessage.setId(iMessageOrigin.getId());
                        IMessageBody imessageBody;
                        try {
                            imessageBody = new ObjectMapper().readValue(iMessageOrigin.getBodyJson(), IMessageBody.class);
                            imessage.setMessageBody(imessageBody);
                        } catch (IOException e) {
                            Exceptions.propagate(e);
                        }


                        return imessage;
                    }
                })
                .filter(new Func1<IMessage, Boolean>() {
                    @Override
                    public Boolean call(IMessage iMessage) {
                        return null != iMessage.getMessageBody() && null != iMessage.getMessageBody().getSendTime();
                    }
                }).subscribe(new OnNextAction<IMessage>() {
            @Override
            public void onNext(IMessage iMessage) {
                adapter.add(iMessage);
            }
        }, new OnErrorDialogAction() {
            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public Context getContext() {
                return MessageActivity.this;
            }
        }, new OnCompleteAction() {
            @Override
            public void onComplete() {
                onNotifyDataSetChanged();

            }
        });

    }

    private void onNotifyDataSetChanged() {
        adapter.notifyDataSetChangedA();
        try {

            recyclerView.smoothScrollToPosition(adapter.getmData().size() - 1);
        } catch (Exception e) {
            LogUtils.e(e);
        }


    }

    private void initIM(final boolean mIsFirst) {
        ConfigConstant configConstant = new ConfigConstant.Builder()
                .setHOST(IMConstant.openfireHost)
                .build();
        IMSDK.domain = IMConstant.domain;
        IMSDK.otherResource = IMConstant.otherResource;
        IMSDK.resource = IMConstant.resource;
        IMSDK.sessionId = sessionId;

        IMSDK.getInstance().login(MessageActivity.this, mAccount, mPassword, String.valueOf(shopId), new IMResultListener() {
            @Override
            public void onSuccess() {
                if (mIsFirst) {
                    isFirst = false;
                }
                LogUtils.d("success");
                sendPresence();

                try {
                    IMSDK.getInstance().startChat(serviceAccount + IMConstant.makeOtherDomainResource(), imChatListener);
                } catch (ConnectionException e) {
                    LogUtil.e(e);
                }
            }

            @Override
            public void onFaile(Throwable throwable) {
                LogUtils.e(throwable);
                ToastUtil.show(MessageActivity.this, "连接失败");

            }

            @Override
            public void onShutdownSession(Throwable throwable) {
                Observable.just(throwable)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(new OnNextAction<Throwable>() {
                            @Override
                            public void onNext(Throwable data) {
                                onShowClose(data);
                            }
                        }, new OnErrorDialogAction() {
                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public Context getContext() {
                                return MessageActivity.this;
                            }
                        });

            }
        }, configConstant);
    }

    private void onShowClose(Throwable throwable) {
        LogUtils.e(throwable);
        ProgressUtil.dismiss();
        new AlertView("提示", "当前用户已在其他端登录\n 请点击确定退出", null, new String[]{"确定"},
                null, this, AlertView.Style.Alert,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        switch (position) {
                            case 0:
                                finish();
                                break;
                        }
                    }
                }).show();
    }

    /**
     * 从{@link Intent 中获取IM所需要的数据 }
     */
    private void obtainDataFromIntent() {

        mAccount = getIntent().getStringExtra("account");
        mPassword = "123456";
        serviceAccount = getIntent().getStringExtra("serviceAccount");
        sessionId = getIntent().getStringExtra("sessionId");
        String serviceName = getIntent().getStringExtra("serviceName");
        servicePhoto = getIntent().getStringExtra("servicePhoto");

        businessType = getIntent().getLongExtra("businessType", -1);
        businessId = getIntent().getStringExtra("businessId");
        shopId = getIntent().getLongExtra("shopId", -1);
        mPhoto = getIntent().getStringExtra("photo");
        if (TextUtils.isEmpty(mAccount)
                || TextUtils.isEmpty(mPassword)
                || TextUtils.isEmpty(serviceAccount)
                || TextUtils.isEmpty(sessionId)
                || TextUtils.isEmpty(serviceName)
                || TextUtils.isEmpty(businessId)
                || -1 == businessType
                || -1 == shopId) {

            ToastUtil.show(MessageActivity.this, "参数错误，打开失败");
            finish();
        }


    }

    private void initToolbar() {
        ImageView back = (ImageView) findViewById(R.id.iv_back);
        ImageView more = (ImageView) findViewById(R.id.iv_more);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });

        TextView myOrderTv = (TextView) findViewById(R.id.tv_my_order);
        TextView browseGoodTv = (TextView) findViewById(R.id.tv_browse_good);
        myOrderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TDevice.isNetConnected(MessageActivity.this)) {
                    ToastUtil.show(MessageActivity.this, "当前网络已断开");
                } else {
                    Intent intent = new Intent(MessageActivity.this, MyOrdersActivity.class);
                    intent.putExtra(Constant.SHOP_ID, shopId);
                    startActivity(intent);
                }
            }
        });
        browseGoodTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TDevice.isNetConnected(MessageActivity.this)) {
                    ToastUtil.show(MessageActivity.this, "当前网络已断开");
                } else {
                    Intent intent = new Intent(MessageActivity.this, BrowseGoodsActivity.class);
                    intent.putExtra(Constant.SHOP_ID, shopId);
                    startActivity(intent);
                }
            }
        });

    }

    private void showPopupWindow(View asView) {
        if (null != mPopupWindow && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        } else {
            View popView = LayoutInflater.from(this).inflate(R.layout.im_popup_layout, null);
            LinearLayout myOrderLayout = (LinearLayout) popView.findViewById(R.id.my_order_layout);
            LinearLayout browseGoodLayout = (LinearLayout) popView.findViewById(R.id.browse_good_layout);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            mPopupWindow = new PopupWindow(popView, displayMetrics.widthPixels / 3, LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setAnimationStyle(R.style.popup_window_animation);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            mPopupWindow.showAsDropDown(asView, -(mPopupWindow.getWidth() / 2 + 100), 0);
            myOrderLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPopupWindow != null) {
                        mPopupWindow.dismiss();
                    }
                    if (!TDevice.isNetConnected(MessageActivity.this)) {
                        ToastUtil.show(MessageActivity.this, "当前网络已断开");
                    } else {
                        Intent intent = new Intent(MessageActivity.this, MyOrdersActivity.class);
                        intent.putExtra(Constant.SHOP_ID, shopId);
                        startActivity(intent);
                    }
                }
            });
            browseGoodLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPopupWindow != null) {
                        mPopupWindow.dismiss();
                    }
                    if (!TDevice.isNetConnected(MessageActivity.this)) {
                        ToastUtil.show(MessageActivity.this, "当前网络已断开");
                    } else {
                        Intent intent = new Intent(MessageActivity.this, BrowseGoodsActivity.class);
                        intent.putExtra(Constant.SHOP_ID, shopId);
                        startActivity(intent);
                    }
                }
            });
        }
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
        if (null != mChat) {
            mChat.close();
        }
        IMSDK.getInstance().release();
        super.onDestroy();
        unregisterReceiver(orderMsgReceiver);
        unregisterReceiver(gdsMsgReceiver);
        unregisterReceiver(netReceiver);
    }


//    private void checkBackGroundAndPlay(IMessage message) {
//        NotificationManager mNotificationManager = (NotificationManager)getSystemServic(Context.NOTIFICATION_SERVICE) ;
//    }

    /**
     * 注册底部菜单扩展栏item; 覆盖此方法时如果不覆盖已有item，item的id需大于3
     */
    protected void registerExtendMenuItem() {
        for (int i = 0; i < itemStrings.length; i++) {
            inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], extendMenuItemClickListener);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.i("蓝信", "take or select picture fail:" + resultCode);
            return;
        }

        Bitmap bmp = null;
        if (requestCode == IMConstant.TAKE_PICTURE) {
            if (null != data) {
                bmp = (Bitmap) data.getExtras().get("data");
            } else if (null != picUri) {
                ContentResolver cr = this.getContentResolver();
                try {
//                    if (bmp != null)//如果不释放的话，不断取图片，将会内存不够
//                        bmp.recycle();
                    bmp = BitmapFactory.decodeStream(cr.openInputStream(picUri));
                } catch (FileNotFoundException e) {
                    LogUtils.e(e);
                }
            } else {
                LogUtils.e("拍照发送失败");
                return;
            }
            uploadImage(bmp);
        } else if (requestCode == PhotoPicker.REQUEST_CODE || requestCode == IMConstant.SELECT_PICTURE_KITKAT) {
            try {
                if (data != null) {
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    String uri = photos.get(0);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    bmp = BitmapFactory.decodeFile(uri, options);
                    uploadImage(bmp);
                }

            } catch (Exception e) {
                LogUtils.e(e);
            }
        } else {
            Log.e("蓝信", "invalide request code:" + requestCode);
        }

    }

    protected void uploadImage(Bitmap bmp) {
        if (null == bmp) {
            return;
        }
        ProgressUtil.show(MessageActivity.this, "上传中");

        JsonService.requestImageByPost(MessageActivity.this, bmp, new JsonService.CallBackImage<UploadResp>() {
            @Override
            public void oncallback(UploadResp s) {
                LogUtils.e(s);
                ProgressUtil.dismiss();
                sendImage(s);
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtils.e(throwable);
                ProgressUtil.dismiss();

            }
        });


    }

    /**
     * 发送图片消息
     * 发送图片时使用id，接收和展示图片的时候使用url
     * 目前由于图片只能发送单张，并且内容为该图片资源id，因此可以使用{@link MessageActivity#sendMsg(String, IM005Resp)}方法
     *
     * @param data 上传文件信息
     */
    private void sendImage(final UploadResp data) {
        IM005Req req = new IM005Req();
        req.setFrom(mAccount + IMConstant.makeMyDomainResource());
        req.setTo(serviceAccount + IMConstant.makeOtherDomainResource());
        req.setBody(data.getFileId());
        req.setCsaCode(serviceAccount);
        req.setSessionId(sessionId);
        req.setUserCode(mAccount);
        req.setShopId(shopId);
        req.setBusinessId(businessId);
        req.setBusinessType(businessType);
        req.setMessageType(IMessageBody.MSG_TYPE_CHAT);
        req.setContentType(IMessageBody.CONTENT_TYPE_PIC);

        NetCenter.build(MessageActivity.this)
                .requestDefault(req, "im005")
                .map(new Func1<AppBody, IM005Resp>() {
                    @Override
                    public IM005Resp call(AppBody appBody) {
                        return null == appBody ? null : (IM005Resp) appBody;
                    }
                }).filter(new Func1<IM005Resp, Boolean>() {
            @Override
            public Boolean call(IM005Resp im005Resp) {
                return null != im005Resp;
            }
        }).subscribe(new Action1<IM005Resp>() {
            @Override
            public void call(IM005Resp im005Resp) {
                sendImageMsg(data.getFileId(), im005Resp);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                handleError(throwable);
            }
        });


    }

    /**
     * 类似于：{@link #sendMsg(String, IM005Resp)}
     *
     * @param fileId    资源id，作为消息内容
     * @param im005Resp 服务端保存信息
     */
    private void sendImageMsg(String fileId, IM005Resp im005Resp) {
        if (null != mChat) {
            try {
                Message sendMessage = HandleMsgUtil.createPicMessage(mAccount + IMConstant.makeMyDomainResource(), fileId
                        , serviceAccount + IMConstant.makeOtherDomainResource()
                        , sessionId, im005Resp.getMessageHistory().getBeginDate().getTime());
                Message sendMessageShow = HandleMsgUtil.createPicMessage(mAccount, IMConstant.makeImageUrl(fileId)
                        , serviceAccount + IMConstant.makeOtherDomainResource()
                        , sessionId, im005Resp.getMessageHistory().getBeginDate().getTime());

                sendMessage.setStanzaId(im005Resp.getMessageHistory().getId());
                sendMessageShow.setStanzaId(im005Resp.getMessageHistory().getId());

                sendMsg(sendMessage);

                addImgMsg(sendMessageShow);
            } catch (IOException e) {
                LogUtils.e(e);
            }

        }

    }

    /**
     * {@link #addMsg(Message)}
     *
     * @param sendMessage 发送消息
     */
    private void addImgMsg(Message sendMessage) {
        Observable.just(sendMessage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Func1<Message, Observable<IMessageOrigin>>() {
                    @Override
                    public Observable<IMessageOrigin> call(Message message) {
                        List<IMessageOrigin> iMessageOrigins = new ArrayList<>();
                        for (Message.Body body : message.getBodies()) {
                            IMessageOrigin iMessageOrigin = new IMessageOrigin();
                            iMessageOrigin.setFrom(mAccount);
                            iMessageOrigin.setTo(serviceAccount);
                            iMessageOrigin.setBodyJson(body.getMessage());
                            iMessageOrigin.setId(message.getStanzaId());
                            iMessageOrigins.add(iMessageOrigin);
                        }
                        return Observable.from(iMessageOrigins);
                    }
                })
                .map(new Func1<IMessageOrigin, IMessage>() {
                    @Override
                    public IMessage call(IMessageOrigin iMessageOrigin) {
                        IMessage imessage = new IMessage();
                        imessage.setSendTo(iMessageOrigin.getTo());
                        imessage.setReceiveFrom(iMessageOrigin.getFrom());
                        imessage.setId(iMessageOrigin.getId());
                        IMessageBody imessageBody;
                        try {
                            imessageBody = new ObjectMapper().readValue(iMessageOrigin.getBodyJson(), IMessageBody.class);
                            imessage.setMessageBody(imessageBody);
                        } catch (IOException e) {
                            Exceptions.propagate(e);
                        }


                        return imessage;
                    }
                })
                .filter(new Func1<IMessage, Boolean>() {
                    @Override
                    public Boolean call(IMessage iMessage) {
                        return null != iMessage.getMessageBody() && null != iMessage.getMessageBody().getSendTime();
                    }
                }).subscribe(new OnNextAction<IMessage>() {
            @Override
            public void onNext(IMessage iMessage) {
                adapter.add(iMessage);
            }
        }, new OnErrorDialogAction() {
            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public Context getContext() {
                return MessageActivity.this;
            }
        }, new OnCompleteAction() {
            @Override
            public void onComplete() {
                onNotifyDataSetChanged();

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent();
//        intent.putExtra("opened", id);
//        setResult(RESULT_OK, intent);
//        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        if (IMConstant.APPID_SERVICE.equals(id)) {
//            getMenuInflater().inflate(R.menu.menu_service, menu);
//
//        } else {
//            getMenuInflater().inflate(R.menu.menu_single, menu);
//
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (R.id.single_member == item.getItemId()) {
//            toMember();
//            return true;
//        } else if (R.id.service_shutdown == item.getItemId()) {
//            toShutDown();
//            return true;
//        } else {
//
        return super.onOptionsItemSelected(item);
//        }
    }


    @Override
    protected void onStop() {
//        SdkClient.getInstance().enterBackground();
        super.onStop();
    }

    @Override
    protected void onResume() {
//        SdkClient.getInstance().enterForeground();
        super.onResume();
    }

    Chat mChat;
    IMChatListener imChatListener = new IMChatListener() {
        @Override
        public void chatCreated(Chat chat, boolean createdLocally) {
            if (!createdLocally) {
                return;
            }
            mChat = chat;
            try {
//                sendPresence();
                IMSDK.getInstance().retriveOfflineMsg(chat, imChatListener);
                sendInformMessage(chat);
            } catch (SmackException.NotConnectedException | IMNullException | SmackException.NoResponseException | XMPPException.XMPPErrorException e) {
                LogUtils.e(e);
            }
        }

        @Override
        public void receive(Chat chat, final IMessage iMessage) {
            IM006Req im006Req = new IM006Req();
            im006Req.setId(processId(iMessage.getId()));
            im006Req.setStatus("20");

            NetCenter.build(MessageActivity.this)
                    .requestDefault(im006Req, "im006")
                    .subscribe(new Action1<AppBody>() {
                        @Override
                        public void call(AppBody appBody) {
                            adapter.add(iMessage);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            LogUtils.e(throwable);
                        }
                    }, new Action0() {
                        @Override
                        public void call() {
                            onNotifyDataSetChanged();
                        }
                    });

        }

        @Override
        public void receiveFailure(Chat chat, Throwable throwable) {
            LogUtils.e("消息接受失败：" + throwable);
        }

        @Override
        public void receiveStatus(Chat chat, Message message) {

        }

        @Override
        public void onServiceClosed() {
            //服务端通知app关闭客服
            Runnable closeRunnable = new Runnable() {
                @Override
                public void run() {
                    IMSDK.getInstance().release();
                    new AlertView("提示", "客服关闭会话，会话已结束，请您谅解。",
                            null, new String[]{"确定"},
                            null, MessageActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            switch (position) {
                                case 0:
                                    finish();
                                    break;
                            }
                        }
                    }).show();
                }
            };
            new Handler(MessageActivity.this.getMainLooper())
                    .post(closeRunnable);

        }

        @Override
        public void onTransfer(final TransferInfo transferInfo) {

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(MessageActivity.this)
                            .setTitle("提示")
                            .setMessage(transferInfo.getGreeting())
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    synchronized (this) {
                                        sessionId = transferInfo.getSessionId();
                                        serviceAccount = transferInfo.getCsaCode();
                                        transfer();
                                        dialog.dismiss();
                                    }
                                }
                            }).setCancelable(false)
                            .show();
                }
            };

            new Handler(MessageActivity.this.getMainLooper())
                    .post(runnable);


        }

    };

    private void transfer() {
//        ConfigConstant configConstant = new ConfigConstant.Builder()
//                .setHOST(IMConstant.openfireHost)
//                .build();
//        IMSDK.domain = IMConstant.domain;
//        IMSDK.otherResource = IMConstant.otherResource;
//        IMSDK.resource = IMConstant.resource;
//
//        ChatManager chatmanager = ChatManager.getInstanceFor(IMSDK.getMconnection());
//
//        chatmanager.getChatListeners().removeAll(chatmanager.getChatListeners());
        IMSDK.getInstance().release();
        initIM(true);
//        try {
//            IMSDK.getInstance().startChat(serviceAccount + IMConstant.makeOtherDomainResource(), imChatListener);
//        } catch (ConnectionException e) {
//            LogUtil.e(e);
//        }


    }

    private String processId(String id) {
        return id.substring(0, 36);
    }

    private void sendPresence() {
        if (null == IMSDK.getMconnection()) {
            return;
        }
        Presence presence = new Presence(Presence.Type.available);
        presence.setFrom(mAccount + IMConstant.makeMyDomainResource());
        presence.setStatus("I am online!");
        presence.setMode(Presence.Mode.available);
        UserCodeExtension userCodeExtension = new UserCodeExtension(mAccount);
        ShopIdExtension shopIdExtension = new ShopIdExtension(String.valueOf(shopId));
        if (!TextUtils.isEmpty(sessionId)) {
            SessionExtension sessionExtension = new SessionExtension(sessionId);
            presence.addExtension(sessionExtension);

        }
        presence.addExtension(userCodeExtension);
        presence.addExtension(shopIdExtension);
        try {
            IMSDK.getMconnection().sendStanza(presence);
        } catch (SmackException.NotConnectedException e) {
            LogUtils.e(e);
        }


    }

    private void sendInformMessage(final Chat chat) {
        IM007Req im007Req = new IM007Req();
        im007Req.setUserCode(mAccount);
        NetCenter.build(MessageActivity.this)
                .requestDefault(im007Req, "im007")
                .map(new JsonMapFunc<AppBody, IM007Resp>())
                .subscribe(new OnNextAction<IM007Resp>() {
                    @Override
                    public void onNext(IM007Resp data) {
                        Long count = data.getCount();
                        String name = trimUserPrefix(mAccount);
                        String serviceName = trimServicePrefix(serviceAccount);
                        String description = null;
                        if (null == data.getSessionTime()) {
                            description = "用户" + name + "第" + count + "次接入" + serviceName;
                        } else {
                            description = "用户" + name + "第" + count + "次接入，上次由客服" + serviceName
                                    + "接入于：" + DateUtil2.getDateString(new Date(data.getSessionTime().getTime()));
                        }


                        Message inform;
                        try {
                            inform = HandleMsgUtil.createInformMessage(mAccount + IMConstant.makeMyDomainResource(), description
                                    , serviceAccount + IMConstant.makeOtherDomainResource()
                                    , sessionId, new Date().getTime());
                            sendMsg(inform);
                        } catch (IOException e) {
                            LogUtil.e(e);
                        }
                    }
                }, new OnErrorDialogAction() {
                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public Context getContext() {
                        return MessageActivity.this;
                    }
                });

    }

    private String trimUserPrefix(@NonNull String mAccount) {
        return mAccount.startsWith("user_") ? mAccount.substring(5, mAccount.length()) : mAccount;
    }

    private String trimServicePrefix(@NonNull String mAccount) {
        return mAccount.startsWith("csa_") ? mAccount.substring(4, mAccount.length()) : mAccount;
    }


    /**
     * 发送商品信息
     *
     * @param goodMessageBody 发送的商品消息内容，{@link IMessageBody#body}，注
     *                        意，{@link GoodMessageBody#gdsImage}发送时需要为url
     */
    private void sendGdsMessage(@NonNull final GoodMessageBody goodMessageBody) throws IOException {
        final String bodyString = new ObjectMapper().writeValueAsString(goodMessageBody);
        IM005Req req = new IM005Req();
        req.setFrom(mAccount + IMConstant.makeMyDomainResource());
        req.setTo(serviceAccount + IMConstant.makeOtherDomainResource());
        req.setBody(bodyString);
        req.setCsaCode(serviceAccount);
        req.setSessionId(sessionId);
        req.setUserCode(mAccount);
        req.setShopId(shopId);
        req.setBusinessId(businessId);
        req.setBusinessType(businessType);
        req.setMessageType(IMessageBody.MSG_TYPE_GDS);
        req.setContentType(IMessageBody.CONTENT_TYPE_TEMPLATE);

        NetCenter.build(MessageActivity.this)
                .requestDefault(req, "im005")
                .map(new Func1<AppBody, IM005Resp>() {
                    @Override
                    public IM005Resp call(AppBody appBody) {
                        return null == appBody ? null : (IM005Resp) appBody;
                    }
                }).filter(new Func1<IM005Resp, Boolean>() {
            @Override
            public Boolean call(IM005Resp im005Resp) {
                return null != im005Resp;
            }
        }).subscribe(new Action1<IM005Resp>() {
            @Override
            public void call(IM005Resp im005Resp) {
                sendGdsMsg(goodMessageBody, im005Resp);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                handleError(throwable);
            }
        });

    }

    private void sendGdsMsg(GoodMessageBody goodMessageBody, IM005Resp im005Resp) {

        if (null != mChat) {
            try {
                Message sendMessage = HandleMsgUtil.createGdsMessage(mAccount + IMConstant.makeMyDomainResource(), goodMessageBody
                        , serviceAccount + IMConstant.makeOtherDomainResource()
                        , sessionId, im005Resp.getMessageHistory().getBeginDate().getTime());
                goodMessageBody.setGdsImage(IMConstant.makeImageUrl(goodMessageBody.getGdsImage()));
                sendMessage.setStanzaId(im005Resp.getMessageHistory().getId());
                sendMsg(sendMessage);
                addGdsMsg(sendMessage);
            } catch (IOException e) {
                LogUtils.e(e);
            }

        }
    }

    /**
     * 暂时直接调用{@link #addMsg(Message)}
     *
     * @param sendMessageShow 用于展示的message
     */
    private void addGdsMsg(Message sendMessageShow) {
        addMsg(sendMessageShow);

    }

    /**
     * 发送商品信息
     *
     * @param orderMessageBody 发送的订单消息内容，{@link IMessageBody#body}，注
     *                         意，{@link OrderMessageBody#ordImage}发送时需要为url
     */
    private void sendOrderMessage(@NonNull final OrderMessageBody orderMessageBody) throws IOException {
        final String bodyString = new ObjectMapper().writeValueAsString(orderMessageBody);
        IM005Req req = new IM005Req();
        req.setFrom(mAccount + IMConstant.makeMyDomainResource());
        req.setTo(serviceAccount + IMConstant.makeOtherDomainResource());
        req.setBody(bodyString);
        req.setCsaCode(serviceAccount);
        req.setSessionId(sessionId);
        req.setUserCode(mAccount);
        req.setShopId(shopId);
        req.setBusinessId(businessId);
        req.setBusinessType(businessType);
        req.setMessageType(IMessageBody.MSG_TYPE_ORDER);
        req.setContentType(IMessageBody.CONTENT_TYPE_TEMPLATE);

        NetCenter.build(MessageActivity.this)
                .requestDefault(req, "im005")
                .map(new Func1<AppBody, IM005Resp>() {
                    @Override
                    public IM005Resp call(AppBody appBody) {
                        return null == appBody ? null : (IM005Resp) appBody;
                    }
                }).filter(new Func1<IM005Resp, Boolean>() {
            @Override
            public Boolean call(IM005Resp im005Resp) {
                return null != im005Resp;
            }
        }).subscribe(new Action1<IM005Resp>() {
            @Override
            public void call(IM005Resp im005Resp) {
                sendOrderMsg(orderMessageBody, im005Resp);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                handleError(throwable);
            }
        });

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
        ToastUtil.show(MessageActivity.this, errorMsg);
    }

    private void sendOrderMsg(OrderMessageBody orderMessageBody, IM005Resp im005Resp) {
        if (null != mChat) {
            try {
                Message sendMessage = HandleMsgUtil.createOrderMessage(mAccount + IMConstant.makeMyDomainResource(), orderMessageBody
                        , serviceAccount + IMConstant.makeOtherDomainResource()
                        , sessionId, im005Resp.getMessageHistory().getBeginDate().getTime());
                orderMessageBody.setOrdImage(IMConstant.makeImageUrl(orderMessageBody.getOrdImage()));
                sendMessage.setStanzaId(im005Resp.getMessageHistory().getId());
                sendMsg(sendMessage);
                addOrderMsg(sendMessage);
            } catch (IOException e) {
                LogUtils.e(e);
            }

        }
    }

    /**
     * 暂时使用{@link #addMsg(Message)}
     *
     * @param sendMessageShow 订单消息
     */
    private void addOrderMsg(Message sendMessageShow) {
        addMsg(sendMessageShow);

    }

    private void sendMsg(Message message) {
        if (null == mChat) {
            return;
        }
        try {
            IMSDK.getInstance().sendMsg(mChat, message);
        } catch (SmackException.NotConnectedException e) {
            new AlertView("提示", "当前网络未连接",
                    null, new String[]{"确定"},
                    null, MessageActivity.this, AlertView.Style.Alert, null).show();
        } catch (IMNullException e) {
            LogUtils.e(e);
        }
    }
}
