package com.ailk.im.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Ord011Req;
import com.ai.ecp.app.resp.Ord011Resp;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.im.net.NetCenter;
import com.ailk.im.tool.EaseSmileUtils;
import com.ailk.im.ui.activity.ViewIMPicActivity;
import com.ailk.imsdk.bean.message.IMessage;
import com.ailk.imsdk.bean.message.body.GoodMessageBody;
import com.ailk.imsdk.bean.message.body.IMessageBody;
import com.ailk.imsdk.bean.message.body.OrderMessageBody;
import com.ailk.imsdk.rx.OnCompleteAction;
import com.ailk.imsdk.rx.OnErrorDialogAction;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.activity.OrderDetailActivity;
import com.ailk.pmph.ui.activity.ShopDetailActivity;
import com.ailk.pmph.ui.view.RoundImageView;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.tool.DateUtil;
import com.ailk.tool.DateUtil2;
import com.ailk.tool.GlideUtil;
import com.apkfuns.logutils.LogUtils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Project : XHS
 * Created by 王可 on 2016/11/15.
 * 掩码说明
 */

public class MessageAdapter extends CommonRecyclerAdapter<IMessage> {

    /**
     * 掩码说明：
     * 1.消息方向掩码：
     * 00 00000 001  = 1 左
     * 00 00000 010  = 2 右
     * 00 00000 100  = 4 中
     * 2.消息类型掩码
     * 00 00001 000 = 8 聊天
     * 00 00010 000 = 16 通知
     * 00 00100 000 = 32 欢迎
     * 00 01000 000 = 64 商品
     * 00 10000 000 = 128 订单
     * 3.内容类型掩码
     * 01 00000 000 = 256 文字
     * 10 00000 000 = 512 图片
     */
    private final static int DIRECTION_LEFT = 1;
    private final static int DIRECTION_RIGHT = 2;
    private final static int DIRECTION_CENTER = 4;


    private final static int TYPE_CHAT = 8;
    private final static int TYPE_INFORM = 16;
    private final static int TYPE_WELCOME = 32;
    private final static int TYPE_GDS = 64;
    private final static int TYPE_ORDER = 128;

    private final static int CONTENT_TEXT = 256;
    private final static int CONTENT_PIC = 512;


    private String currentUser;
    private String mPhoto;
    private String serviceAccount;
    private String servicePhoto;


    public MessageAdapter(Context context, @NonNull String mAccount, String mPhoto, String serviceAccount, String servicePhoto) {
        super(context);
        this.currentUser = mAccount;
        this.mPhoto = mPhoto;
        this.serviceAccount = serviceAccount;
        this.servicePhoto = servicePhoto;

    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if ((viewType & DIRECTION_LEFT) == DIRECTION_LEFT) {
//            direction = DIRECTION_LEFT;
            view = mLayoutInflater.inflate(R.layout.chat_container_left, parent, false);
        } else if ((viewType & DIRECTION_RIGHT) == DIRECTION_RIGHT) {
//            direction = DIRECTION_RIGHT;
            view = mLayoutInflater.inflate(R.layout.chat_container_right, parent, false);
        } else {
//            direction = DIRECTION_CENTER;
            view = mLayoutInflater.inflate(R.layout.chat_container_center, parent, false);
        }


        if ((viewType & TYPE_GDS) == TYPE_GDS) {
            //商品消息
            return new GoodMessageView(view);
        } else if ((viewType & TYPE_ORDER) == TYPE_ORDER) {
            //订单消息
            return new OrderMessageView(view);
        } else if ((viewType & CONTENT_TEXT) == CONTENT_TEXT) {
            //文字消息
            return new TextMessageView(view);
        } else if ((viewType & CONTENT_PIC) == CONTENT_PIC) {
            //图片消息
            return new PicMessageView(view);
        } else {
            //其他类型消息
            return new MessageView(view);

        }

    }

    //1 : 发送; 2 : 接收
    @Override
    public int getItemViewType(int position) {
        IMessage iMessage = getItem(position);
        int type = 0;
        String from = processAccount(iMessage.getReceiveFrom());
        String to = processAccount(iMessage.getSendTo());

        //处理方向
        if (currentUser.equals(from)) {
            //发送方为自己时，显示在右边
            type |= DIRECTION_RIGHT;
        } else if (currentUser.contains(to)) {
            //发送方为对方时，显示在左边
            type |= DIRECTION_LEFT;

        } else {
            //其他情况，显示在中间
            type |= DIRECTION_CENTER;
        }
        //处理消息类型

        if (iMessage.getMessageBody().getMessagetype().equals(IMessageBody.MSG_TYPE_CHAT)) {
            type |= TYPE_CHAT;
        } else if (iMessage.getMessageBody().getMessagetype().equals(IMessageBody.MSG_TYPE_INFORM)) {
            type |= TYPE_INFORM;
        } else if (iMessage.getMessageBody().getMessagetype().equals(IMessageBody.MSG_TYPE_WELCOME)) {
            type |= TYPE_WELCOME;
        } else if (iMessage.getMessageBody().getMessagetype().equals(IMessageBody.MSG_TYPE_GDS)) {
            type |= TYPE_GDS;
        } else if (iMessage.getMessageBody().getMessagetype().equals(IMessageBody.MSG_TYPE_ORDER)) {
            type |= TYPE_ORDER;
        }

        //内容类型
        if (null == iMessage.getMessageBody().getContentType()) {
            type = DIRECTION_LEFT;
        } else {
            if (iMessage.getMessageBody().getContentType().equals(IMessageBody.CONTENT_TYPE_PIC)) {
                type |= CONTENT_PIC;
            } else if (iMessage.getMessageBody().getContentType().equals(IMessageBody.CONTENT_TYPE_TEXT)) {
                type |= CONTENT_TEXT;
            }
        }


        return type;
    }

    private String processAccount(String receiveFrom) {
        if (TextUtils.isEmpty(receiveFrom)) {
            return receiveFrom;
        }

        int end = receiveFrom.indexOf("@");
        return end >= 0 ? receiveFrom.substring(0, end) : receiveFrom;
    }


    public class MessageView extends CommonViewHolder {
        TextView name;
        RoundImageView img;
        FrameLayout content;

        public MessageView(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            img = (RoundImageView) itemView.findViewById(R.id.img);
            content = (FrameLayout) itemView.findViewById(R.id.content);

        }

        @Override
        public void fillView(int position) {
            IMessage iMessage = getItem(position);
            int type = getItemViewType();
            content.removeAllViews();
            if ((type & TYPE_GDS) == TYPE_GDS) {
                //商品消息
                GoodMessageBody goodMessageBody = null;
                try {
                    goodMessageBody = new ObjectMapper().readValue(iMessage.getMessageBody().getBody(), GoodMessageBody.class);
                } catch (IOException e) {
                    LogUtils.e(e);
                }
                ImageView imageViewContent = (ImageView) LayoutInflater.from(mContext)
                        .inflate(R.layout.chat_layout_gds, content)
                        .findViewById(R.id.content_imageview);
                TextView gdsCodeContent = (TextView) LayoutInflater.from(mContext)
                        .inflate(R.layout.chat_layout_gds, content)
                        .findViewById(R.id.gds_code_content);
                TextView gdsNameContent = (TextView) LayoutInflater.from(mContext)
                        .inflate(R.layout.chat_layout_gds, content)
                        .findViewById(R.id.gds_name_content);
                if (null == goodMessageBody) {
                    gdsCodeContent.setText("未知");
                    gdsNameContent.setText("未知");
                } else {
                    GlideUtil.loadImg(mContext, goodMessageBody.getGdsImage(), imageViewContent);
                    gdsCodeContent.setText(goodMessageBody.getPrice());
                    gdsNameContent.setText(goodMessageBody.getGdsName());
                }


            } else if ((type & TYPE_ORDER) == TYPE_ORDER) {
                OrderMessageBody orderbody = null;
                try {
                    orderbody = new ObjectMapper().readValue(iMessage.getMessageBody().getBody(), OrderMessageBody.class);
                } catch (IOException e) {
                    LogUtils.e(e);
                }

                ImageView imageViewContent = (ImageView) LayoutInflater.from(mContext)
                        .inflate(R.layout.chat_layout_order, content)
                        .findViewById(R.id.content_imageview);
                TextView orderCodeContent = (TextView) LayoutInflater.from(mContext)
                        .inflate(R.layout.chat_layout_order, content)
                        .findViewById(R.id.order_code_content);
                TextView orderPriceContent = (TextView) LayoutInflater.from(mContext)
                        .inflate(R.layout.chat_layout_order, content)
                        .findViewById(R.id.order_price_content);
                TextView orderDateContent = (TextView) LayoutInflater.from(mContext)
                        .inflate(R.layout.chat_layout_order, content)
                        .findViewById(R.id.order_date_content);
                if (null == orderbody) {
                    orderCodeContent.setText("未知");
                    orderPriceContent.setText("未知");
                    orderDateContent.setText("未知");
                } else {
                    GlideUtil.loadImg(mContext, orderbody.getOrdImage(), imageViewContent);
                    orderCodeContent.setText(orderbody.getOrdId() + "");
                    orderPriceContent.setText(orderbody.getPrice() + "元");
                    orderDateContent.setText(orderbody.getCreateTime().toString());

                }
            } else if ((type & CONTENT_TEXT) == CONTENT_TEXT) {
                //文字消息
                TextView textMessageContent = (TextView) LayoutInflater.from(mContext)
                        .inflate(R.layout.chat_layout_text, content)
                        .findViewById(R.id.content_message);
                CharSequence charSequence = EaseSmileUtils.emoji2Str(iMessage.getMessageBody().getBody(), mContext);
                textMessageContent.setText(charSequence);
            } else if ((type & CONTENT_PIC) == CONTENT_PIC) {
                //图片消息
                ImageView imageViewContent = (ImageView) LayoutInflater.from(mContext)
                        .inflate(R.layout.chat_layout_pic, content)
                        .findViewById(R.id.content_imageview);
                GlideUtil.loadImg(mContext, iMessage.getMessageBody().getBody(), imageViewContent);
            } else {
                //其他类型消息
                TextView textMessageContent = (TextView) LayoutInflater.from(mContext)
                        .inflate(R.layout.chat_layout_text, content)
                        .findViewById(R.id.content_message);
                textMessageContent.setText("[未知消息类型，请更新到最新版本查看]");
                processPhotoAndName(img, iMessage, name);
            }

        }
    }


    @Override
    public void add(final IMessage data) {
        if (null == mData) {
            mData = new ArrayList<>();
        }
//        for (int i = 0; i < mData.size(); i++) {
//            if (mData.get(i).getId() == data.getId()) {
//                return;
//            }
//        }
        mData.add(data);

    }

    public void insert(final IMessage data) {
        if (null == mData) {
            mData = new ArrayList<>();
        }
//        for (int i = 0; i < mData.size(); i++) {
//            if (mData.get(i).getId() == data.getId()) {
//                return;
//            }
//        }
        mData.add(0, data);

    }

    public void initAdd(IMessage data) {
        if (null == mData) {
            mData = new ArrayList<>();
        }
        mData.add(data);
    }


    public class TextMessageView extends CommonViewHolder {
        TextView name;
        RoundImageView img;
        FrameLayout content;

        TextView textMessageContent;

        public TextMessageView(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            img = (RoundImageView) itemView.findViewById(R.id.img);
            content = (FrameLayout) itemView.findViewById(R.id.content);
            textMessageContent = (TextView) LayoutInflater.from(mContext).
                    inflate(R.layout.chat_layout_text, content).
                    findViewById(R.id.content_message);
        }

        @Override
        public void fillView(int position) {
            IMessage iMessage = getItem(position);
            //文字消息
            CharSequence charSequence = EaseSmileUtils.emoji2Str(iMessage.getMessageBody().getBody(), mContext);
            textMessageContent.setText(charSequence);

            processPhotoAndName(img, iMessage, name);

            textMessageContent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));


        }
    }

    /**
     * 用于处理头像
     *
     * @param img
     * @param iMessage
     */
    private void processPhotoAndName(RoundImageView img, IMessage iMessage, TextView name) {
        String from = processAccount(iMessage.getReceiveFrom());
        String to = processAccount(iMessage.getSendTo());

        //处理方向
        if (currentUser.equals(from)) {
            //发送方为自己时，显示在右边
            GlideUtil.loadImg(mContext, mPhoto, img, R.drawable.avatar_contact);
//            name.setVisibility(View.GONE);
        } else if (currentUser.contains(to)) {
            //发送方为对方时，显示在左边
            GlideUtil.loadImg(mContext, servicePhoto, img, R.drawable.avatar_contact);
//            name.setVisibility(View.VISIBLE);
//            name.setText(serviceAccount);
        } else {
            //其他情况，显示在中间
//            name.setVisibility(View.GONE);
        }


    }


    public class GoodMessageView extends CommonViewHolder {
        TextView name;
        RoundImageView img;
        FrameLayout content;
        ImageView imageViewContent;
        TextView gdsCodeContent;
        TextView gdsNameContent;

        public GoodMessageView(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            img = (RoundImageView) itemView.findViewById(R.id.img);
            content = (FrameLayout) itemView.findViewById(R.id.content);
            imageViewContent = (ImageView) LayoutInflater.from(mContext)
                    .inflate(R.layout.chat_layout_gds, content)
                    .findViewById(R.id.content_imageview);
            gdsCodeContent = (TextView) LayoutInflater.from(mContext)
                    .inflate(R.layout.chat_layout_gds, content)
                    .findViewById(R.id.gds_code_content);
            gdsNameContent = (TextView) LayoutInflater.from(mContext)
                    .inflate(R.layout.chat_layout_gds, content)
                    .findViewById(R.id.gds_name_content);

        }

        @Override
        public void fillView(int position) {
            IMessage iMessage = getItem(position);
            //商品消息
            GoodMessageBody goodMessageBody = null;
            try {
                goodMessageBody = new ObjectMapper().readValue(iMessage.getMessageBody().getBody(), GoodMessageBody.class);
            } catch (IOException e) {
                LogUtils.e(e);
            }

            if (null == goodMessageBody) {
                gdsCodeContent.setText("未知");
                gdsNameContent.setText("未知");
            } else {
                GlideUtil.loadImg(mContext, goodMessageBody.getGdsImage(), imageViewContent);
                final Long gdsId = goodMessageBody.getGdsId();
                gdsCodeContent.setText(goodMessageBody.getPrice());
                gdsNameContent.setText(goodMessageBody.getGdsName());

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, ShopDetailActivity.class);
                        intent.putExtra(Constant.SHOP_GDS_ID, String.valueOf(gdsId));
                        mContext.startActivity(intent);
                    }
                });
            }
            processPhotoAndName(img, iMessage, name);

        }
    }

    public class OrderMessageView extends CommonViewHolder {
        TextView name;
        RoundImageView img;
        FrameLayout content;

        ImageView imageViewContent;
        TextView orderCodeContent;
        TextView orderPriceContent;
        TextView orderDateContent;

        public OrderMessageView(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            img = (RoundImageView) itemView.findViewById(R.id.img);
            content = (FrameLayout) itemView.findViewById(R.id.content);

            imageViewContent = (ImageView) LayoutInflater.from(mContext)
                    .inflate(R.layout.chat_layout_order, content)
                    .findViewById(R.id.content_imageview);
            orderCodeContent = (TextView) LayoutInflater.from(mContext)
                    .inflate(R.layout.chat_layout_order, content)
                    .findViewById(R.id.order_code_content);
            orderPriceContent = (TextView) LayoutInflater.from(mContext)
                    .inflate(R.layout.chat_layout_order, content)
                    .findViewById(R.id.order_price_content);
            orderDateContent = (TextView) LayoutInflater.from(mContext)
                    .inflate(R.layout.chat_layout_order, content)
                    .findViewById(R.id.order_date_content);

        }

        @Override
        public void fillView(int position) {
            IMessage iMessage = getItem(position);
            OrderMessageBody orderbody = null;
            try {
                orderbody = new ObjectMapper().readValue(iMessage.getMessageBody().getBody(), OrderMessageBody.class);
            } catch (IOException e) {
                LogUtils.e(e);
            }


            if (null == orderbody) {
                orderCodeContent.setText("未知");
                orderPriceContent.setText("未知");
                orderDateContent.setText("未知");
            } else {
                GlideUtil.loadImg(mContext, orderbody.getOrdImage(), imageViewContent);
                orderCodeContent.setText(orderbody.getOrdId());
                orderPriceContent.setText(orderbody.getPrice());
                orderDateContent.setText(DateUtil.getDateString(orderbody.getCreateTime()));

                final String orderId = orderbody.getOrdId();
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToOrderDetail(orderId);
                    }
                });
            }
            processPhotoAndName(img, iMessage, name);
        }

        private void goToOrderDetail(String orderId) {
            Ord011Req request = new Ord011Req();
            request.setOrderId(orderId);
            NetCenter.build(mContext)
                    .requestDefault(request, "ord011")
                    .map(new Func1<AppBody, Ord011Resp>() {
                        @Override
                        public Ord011Resp call(AppBody appBody) {
                            return null == appBody ? null : (Ord011Resp) appBody;
                        }
                    }).filter(new Func1<Ord011Resp, Boolean>() {
                @Override
                public Boolean call(Ord011Resp ord011Resp) {
                    return null != ord011Resp;
                }
            }).subscribe(new Action1<Ord011Resp>() {
                @Override
                public void call(Ord011Resp ord011Resp) {
                    Intent intent = new Intent(mContext, OrderDetailActivity.class);
                    intent.putExtra("ord011Resp", ord011Resp);
                    mContext.startActivity(intent);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    ToastUtil.show(mContext, throwable.getMessage());
                }
            });
        }
    }

    public class PicMessageView extends CommonViewHolder {
        TextView name;
        RoundImageView img;
        FrameLayout content;

        ImageView imageViewContent;

        public PicMessageView(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            img = (RoundImageView) itemView.findViewById(R.id.img);
            content = (FrameLayout) itemView.findViewById(R.id.content);

            imageViewContent = (ImageView) LayoutInflater.from(mContext)
                    .inflate(R.layout.chat_layout_pic, content)
                    .findViewById(R.id.content_imageview);

        }

        @Override
        public void fillView(int position) {
            final IMessage iMessage = getItem(position);
            //图片消息
            GlideUtil.loadImg(mContext, iMessage.getMessageBody().getBody(), imageViewContent);
            processPhotoAndName(img, iMessage, name);

            imageViewContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ViewIMPicActivity.class);
                    intent.putExtra("src", iMessage.getMessageBody().getBody());
                    mContext.startActivity(intent);
                }
            });
        }


    }

    public void notifyDataSetChangedA() {
        Collections.sort(mData, new Comparator<IMessage>() {
            @Override
            public int compare(IMessage o1, IMessage o2) {
                if (!o1.getMessageBody().getSendTime().equals(o2.getMessageBody().getSendTime())) {

                    return o1.getMessageBody().getSendTime() > o2.getMessageBody().getSendTime() ? 1 : -1;
                } else {
                    Long extraId1 = o1.getExtraId();
                    Long extraId2 = o2.getExtraId();
                    if (null == extraId1 || null == extraId2) {
                        return -1;
                    } else {
                        return extraId1 > extraId2 ? 1 : -1;
                    }

                }
            }
        });
        refreshTime();
        this.notifyDataSetChanged();
    }

    private synchronized void refreshTime() {

        if (null == mData || 0 == mData.size()) {
            return;
        }
        IMessage lastMsg = null;
        List<IMessage> newMessages = new ArrayList<>();
        for (int i = 0; i < getmData().size(); i++) {
            IMessage msg = getmData().get(i);
            if ("_system".equals(msg.getReceiveFrom()) && "_system".equals(msg.getSendTo())) {
                continue;
            }
            //间隔10分钟，添加时间分割线
            if (lastMsg == null || msg.getMessageBody().getSendTime() - lastMsg.getMessageBody().getSendTime() > 10 * 60 * 1000) {
                IMessage timeMessage = createTimeMessage(msg.getMessageBody().getSendTime());
                newMessages.add(timeMessage);
            }

            newMessages.add(msg);
            lastMsg = msg;
        }
        setData(newMessages);

    }

    private IMessage createTimeMessage(Long sendTime) {
        IMessage timeMessage = new IMessage();
        timeMessage.setReceiveFrom("_system");
        timeMessage.setSendTo("_system");


        IMessageBody timeMessageBody = new IMessageBody();
        timeMessageBody.setSendTime(sendTime);
        timeMessageBody.setBody(DateUtil2.getDateString(new Date(sendTime)));
        timeMessageBody.setContentType(IMessageBody.CONTENT_TYPE_TEXT);
        timeMessageBody.setMessagetype(IMessageBody.MSG_TYPE_CHAT);

        timeMessage.setMessageBody(timeMessageBody);

        return timeMessage;
    }
}
