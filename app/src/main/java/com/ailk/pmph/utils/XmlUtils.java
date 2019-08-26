package com.ailk.pmph.utils;

import android.support.annotation.Nullable;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 类注释: 解析鸿支付返回的xml
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.tool
 * 作者: Chrizz
 * 时间: 2016/6/5 23:36
 */
public class XmlUtils {

    /**
     * 根据字符串创建 document 对象
     * @param xmlStr
     * @return
     */
    public static Document getDocByStr(String xmlStr){
        Document document = null;
        try {
            document = DocumentHelper.parseText(xmlStr);
        } catch (DocumentException e) {
            LogUtil.e("字符串转换成XML失败:" + e.getMessage());
        }
        return document;
    }

    @Nullable
    public static Map<Object,Object> readFormData(String xmlStr) {
        Map<Object,Object> map = new HashMap<>();
        String newXmlStr = StringUtils.replace(xmlStr, "&quot;", "\"");
        Document doc = getDocByStr(newXmlStr);
        if (null == doc) {
            return null;
        }
        Element rootElement = doc.getRootElement();
        Iterator iter = rootElement.elementIterator("BODY");
        while (iter.hasNext()) {
            Element bodyElement = (Element) iter.next();
            Iterator iters = bodyElement.elementIterator("TRANS_SUM");
            while (iters.hasNext()) {
                Element sumElement = (Element) iters.next();
                String payerId = sumElement.elementTextTrim("PAYER_PARTNER_ACCT_ID");
                String payerName = sumElement.elementTextTrim("PAYER_PARTNER_ACCT_NAME");
                String orderMoney = sumElement.elementTextTrim("TOTAL_AMOUNT");
                String notifyUrl = sumElement.elementTextTrim("NOTIFY_URL");
                map.put("payerId", payerId);
                map.put("payerName", payerName);
                map.put("orderMoney", StringUtils.remove(MoneyUtils.GoodListPrice(Long.parseLong(orderMoney)),"￥"));
                map.put("notifyUrl", notifyUrl);
            }
            Iterator iterss = bodyElement.elementIterator("TRADE_DETAILS");
            while (iterss.hasNext()) {
                Element detailsElement = (Element) iterss.next();
                Iterator itersss = detailsElement.elementIterator("TRANS_DETAIL");
                while (itersss.hasNext()) {
                    Element detailElement = (Element) itersss.next();
                    String payeeId = detailElement.elementTextTrim("PAYEE_PARTNER_ACCT_ID");
                    String payeeName = detailElement.elementTextTrim("PAYEE_PARTNER_ACCT_NAME");
                    map.put("payeeId", payeeId);
                    map.put("payeeName", payeeName);
                }
            }
        }
        return map;
    }



}
