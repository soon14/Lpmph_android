package com.ailk.pmph.utils;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.activity.MainActivity;
import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.tool
 * 作者: Chrizz
 * 时间: 2016/6/1 11:41
 */
public class AliPayUtils {

    private BaseActivity activity;
    private String payInfo;
    private Boolean isSuccess = false;
    private static final int SDK_PAY_FLAG = 1;

    //返回的支付宝需要数据
    private String seller;// 签约卖家支付宝账号
    private String partner;// 签约合作者身份ID
    private String privateKey;// 私钥
    private String notifyUrl;// 服务器异步通知页面路径
    private String orderCode;// 商品订单号
    private String subject;// 商品名称
    private String body;// 商品详情
    private String price;// 商品金额

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    AliPayResult payResult = new AliPayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();
                    LogUtil.e("resultStatus================"+resultStatus);

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000"))
                    {
                        ToastUtil.showCenter(activity, "支付成功");
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                    else
                    {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtil.showCenter(activity, "支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtil.showCenter(activity, "支付失败");
                        }
                    }
                    break;
                }
            }
        };
    };

    public AliPayUtils(BaseActivity activity) {
        this.activity = activity;
    }

    public void pay(String privateKey,String seller,String partner,String notifyUrl,
                    String subject,String body,String price,String orderCode) {
        this.seller = seller;
        this.partner = partner;
        this.privateKey = privateKey;
        this.notifyUrl = notifyUrl;
        this.subject = subject;
        this.body = body;
        this.price = price;
        this.orderCode = orderCode;

        if (TextUtils.isEmpty(partner) || TextUtils.isEmpty(privateKey) || TextUtils.isEmpty(seller)) {
            DialogAnotherUtil.showCustomAlertDialogWithMessage(activity, null, "配置参数缺失", "确定", "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogUtil.dismissDialog();
                        }
                    });
            return;
        }

        String orderInfo = getOrderInfo(subject, body, price);
        String _sign = sign(orderInfo, privateKey);
        try {
            // 仅需对sign 做URL编码
            _sign = URLEncoder.encode(_sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        payInfo = orderInfo + "&sign=\"" +_sign + "\"&" + getSignType();
        LogUtil.e("payInfo============" + payInfo);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 调用支付接口，获取支付结果
                PayTask payTask = new PayTask(activity);
                String result = payTask.pay(payInfo);
                LogUtil.e("result===============" + result);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + partner + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + seller + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderCode + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + notifyUrl + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        return orderInfo;
    }

    /**
     * 对订单信息进行签名
     * @param content   待签名订单信息
     */
    private String sign(String content, String privateKey) {
        return SignUtils.sign(content, privateKey);
    }

}
