package com.ailk.pmph.utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UrlParamsDecoder {
	/**
	 * 解析出url请求的路径，包括页面
	 * 
	 * @param strURL
	 *            url地址
	 * @return url路径
	 */
	public static String UrlPage(String strURL) {
		try {
			URL url_net = new URL(strURL);
			// 完整的url
			return url_net.getPath();
		} catch (MalformedURLException e) {
			// 不是完整的url 使用下面的方法解析
			if (!strURL.startsWith("/")) {
				strURL = "/" + strURL;
			}
		}
		String strPage = null;
		String[] arrSplit = null;
		strURL = strURL.trim();
		arrSplit = strURL.split("[?]");
		if (arrSplit.length > 0) {
			strPage = arrSplit[0];
		}
		return strPage;
	}

	public static String getWoegoPage(String url) {
		if (url == null) {
			return "";
		}
		url = url.trim();
		String[] pages = url.split("/");
		System.out.println("pargs.length = "+pages.length);
		if(pages.length == 3){
			return pages[1];
		}else if (pages.length > 2) {
			return pages[2];
		} else {
			return url;
		}
	}

    public static String getWocfPage(String url) {
        if (url == null) {
            return "";
        }
        url = url.trim();
        String[] pages = url.split("\\/");
        LogUtil.e("pargs.length = "+pages.length);
		if (pages.length < 3){
			return null;
		}
		return pages[2];
//        int pageLength = pages.length;
//        if(pageLength>2){
//            return pages[pageLength-2]+"/"+pages[pageLength-1];
//        }else if(pageLength>1){
//            return pages[pageLength-1];
//        }else {
//            return url;
//        }
    }


	/**
	 * 去掉url中的路径，留下请求参数部分
	 * 
	 * @param strURL
	 *            url地址
	 * @return url请求参数部分
	 */
	private static String TruncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;
		strURL = strURL.trim();
		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}
		return strAllParam;
	}

	/**
	 * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * 
	 * @param URL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static Map<String, String> URLRequest(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();
		String[] arrSplit = null;
		String strUrlParam = TruncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键值为一组
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");
			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
                String value = arrSplitEqual[1];
                try {
                    value = java.net.URLDecoder.decode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    LogUtil.e(e);
                }
				mapRequest.put(arrSplitEqual[0],value);
			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}

}
