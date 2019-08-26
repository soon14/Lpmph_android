package com.ailk.pmph.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MoneyUtils {
	private final static DecimalFormat nf = new DecimalFormat("￥###,##0.00");
	private final static DecimalFormat nfNoUnit = new DecimalFormat(
			"###,##0.00");

	private final static DecimalFormat  DOUBLE_FORMAT = new DecimalFormat("#.##");
	public static String doubleFormat(double number){

		return DOUBLE_FORMAT.format(number);

	}


	public static String formatToMoney(String s) {
		try {
			if (s == null || "".equals(s)|| "null".equalsIgnoreCase(s)) {
				return "￥0.00";
			}
			return formatToMoney(Double.parseDouble(s));
		} catch (Exception e) {
			// TODO: handle exception
			return String.valueOf(s);
		}
	}

	/**
	 * 将内容除100后返回
	 *
	 * @param s
	 * @return
	 */
	public static String formatToMoney100(long s) {
		try {
			if (s < 0) {
				return "-￥"+formatToMoneyNoUnit(((double)(-s))/100);
			}
			return formatToMoney(((double)(s))/100);
		} catch (Exception e) {
			// TODO: handle exception
			return String.valueOf(s);
		}
	}

	public static String formatToMoney1000(long s) {
		try {
			if (s < 0) {
				return "-￥"+formatToMoneyNoUnit(((double)(-s))/100);
			}
			return formatToMoney(((double)(s))/1000);
		} catch (Exception e) {
			// TODO: handle exception
			return String.valueOf(s);
		}
	}
	

	/**
	 * 将内容除1000后返回
	 *
	 * @param s
	 * @return
	 */
	public static String formatToMoney100Nounit(String s) {
		try {
			if (s == null || "".equals(s) || "null".equalsIgnoreCase(s)) {
				return "0.00";
			}
			return formatToNoUnitMoney(new BigDecimal(s)
					.divide(new BigDecimal("100")));
		} catch (Exception e) {
			// TODO: handle exception
			return String.valueOf(s);
		}
	}

	public static String formatToMoney(double d) {
		try {
			return nf.format(d);
		} catch (Exception e) {
			return String.valueOf(d);
		}
	}
	public static String formatToMoneyNoUnit(double d) {
		try {
			return nfNoUnit.format(d);
		} catch (Exception e) {
			return String.valueOf(d);
		}
	}

	public static boolean isEnough(String accout, String payment) {
		if (Double.parseDouble(accout.replaceAll("￥", "").replaceAll(",", "")) >= Double
				.parseDouble(payment.replace("￥", "").replaceAll(",", ""))) {
			return true;
		}
		return false;
	}

	public static String formatToMoney(BigDecimal d) {
		try {
			if (d.doubleValue() >= 0)
				return nf.format(d);
			else
				return "-￥" + nfNoUnit.format(d.abs());
		} catch (Exception e) {
			return String.valueOf(d);
		}
	}

	// /////////////////

	public static String formatToNoUnitMoney(String s) {
		try {
			if (s == null || "".equals(s)|| "null".equalsIgnoreCase(s)) {
				return "0.00";
			}
			return formatToNoUnitMoney(Double.parseDouble(s));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return String.valueOf(s);
		}
	}

	public static String formatToNoUnitMoney(double d) {
		try {
			return nfNoUnit.format(d);
		} catch (Exception e) {
			return String.valueOf(d);
		}
	}

	public static String formatToNoUnitMoney(BigDecimal d) {
		try {
			return nfNoUnit.format(d);
		} catch (Exception e) {
			return String.valueOf(d);
		}
	}

    public static String AddUnitPrefix(String money) {
        if(!TextUtils.isEmpty(money)) {
            return new String("￥" + money + "元");
        }
        return "￥0元";
    }
//	折扣价
	public static String AddDiscountPricePrefix(String money){
		if(!TextUtils.isEmpty(money)){
			return new String("折扣价:￥" + money + "元");
		}
		return "￥0元";
	}
	//商品列表
	public static String GoodListPrice(Long price){
		return null == price ? "" :nf.format((price.doubleValue() / 100));
	}

	/**
	 * 显示价格
	 * @param textView
	 * @param price
     */
	public static void showSpannedPrice(TextView textView, String price) {
		SpannableString sp = new SpannableString(price);
		sp.setSpan(new RelativeSizeSpan(1.5f), 1, price.length()-3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(sp);
	}

}
