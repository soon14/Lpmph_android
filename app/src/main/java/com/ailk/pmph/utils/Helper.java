package com.ailk.pmph.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
	public static String Machine = "FriendlyARM";

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private static String toHexString(byte[] b) { // String to byte
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	public static String SHA(String s) {

		// System.out.println("egGsB1vvmXigLg1hdYEHXn72Vv8=".equals(digest
		// .getSHA("lc")));
		try {
			// Create SHA Hash
			MessageDigest md5 = MessageDigest.getInstance("SHA-1");
			md5.update(s.getBytes());
			byte[] m = md5.digest();// 加密
			return Base64.encodeToString(m, Base64.NO_WRAP);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public static byte[] SHAUnBase64(String s) {

		// System.out.println("egGsB1vvmXigLg1hdYEHXn72Vv8=".equals(digest
		// .getSHA("lc")));
		try {
			// Create SHA Hash
			MessageDigest md5 = MessageDigest.getInstance("SHA-1");
			md5.update(s.getBytes());
			byte[] m = md5.digest();// 加密
			return m;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			return toHexString(messageDigest).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 反转字符串以两个为单位(123456——>214365)
	 *
	 * @param input
	 * @return
	 */
	public static String reverse(String input) {
		byte[] temp = input.getBytes();
		int length = temp.length;
		byte[] result = new byte[length];

		for (int i = 0; i < length; i++) {
			if (i % 2 == 0) {
				if (i != (length - 1)) {
					result[i] = temp[i + 1];
				} else {
					result[i] = temp[i];
				}
			} else {
				result[i] = temp[i - 1];
			}
		}

		return new String(result);
	}
	/**
	 * 反转字符串(123456——>654321)
	 *
	 * @param input
	 * @return
	 */
	public static String reverse2(String input) {
		StringBuffer stringBuffer = new StringBuffer(input);
		return stringBuffer.reverse().toString();
	}


	/**
	 * @return 获取手机信息,0:IMSI,1:IMEI,2:软件版本
	 */
	public static String[] getPhoneInfo(Context ctx) {
		String[] pi = new String[3];
		TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String pnum = tm.getLine1Number();
		LogUtil.i("PhoneInfo callNumber::::" + pnum);
		//
		pi[0] = tm.getSubscriberId();// IMSI
		LogUtil.i("PhoneInfo IMSI::::" + pi[0]);
		pi[1] = tm.getDeviceId();// IMEI
		LogUtil.i("PhoneInfo IMEI::::" + pi[1]);
		//
		LogUtil.i("PhoneInfo manufacture::::" + Build.MANUFACTURER);// 厂商
		LogUtil.i("PhoneInfo model::::" + Build.MODEL);// 型号
		LogUtil.i("PhoneInfo VERSION::::" + "android_"
				+ Build.VERSION.RELEASE);// 手机操作系统

		try {// 从AndroidManifest.xml中读取软件版本
			PackageInfo pack = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0);
			pi[2] = String.valueOf(pack.versionName);
		} catch (NameNotFoundException e) {
			pi[2] = "V1.0.0";// 软件版本
			e.printStackTrace();
		}
		LogUtil.i("PhoneInfo versionCode::::" + pi[2]);

		return pi;
	}

	public static String getVersionName(Context context) {
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = context.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			String version = packInfo.versionName;
			return version;
		} catch (NameNotFoundException e) {
			return "";
		}
	}

	public static boolean isPhoneNumber(String phone) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();     
	}

	public static boolean isEmail(String email) {
		String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();     
	}

	/**
	 * @return P手机，M机具 是否机具
	 */
	public static String isMachine_s() {
		if (Build.MANUFACTURER.equals(Machine)) {
			return "M";
		} else {
			return "P";
		}
	}

    /**
     * @return 是否模拟器
     */
    public static boolean isEmulator(Context context){
        try{
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if (imei != null && imei.equals("000000000000000")){ return true; }
                return (Build.MODEL.equals("sdk")) || (Build.MODEL.equals("google_sdk"));
        }  catch (Exception ioe) {
        }
        return false;
    }


    /**
	 * 检查密码强度</br>
	 * 
	 * @param password
	 * @return false(不够强), true (够强)
	 */
	public static boolean passwodStrongChecke(String password) {
		if (password.length() < 7)
			return false;

		boolean isStrong = ((password.matches(".*[a-z]+.*") || password
				.matches(".*[A-Z]+.*")) && password.matches(".*\\d+.*"));
		if (!isStrong)
			return false;

		StringBuilder cs = new StringBuilder(password);
		try {
			for (int i = 0; i < cs.length() - 1; i++) {
				if (i == 0)
					continue;
				Character preC = cs.charAt(i - 1);
				Character c = cs.charAt(i);
				Character nextChar = cs.charAt(i + 1);
				if (Character.isDigit(preC) && Character.isDigit(c)
						&& Character.isDigit(nextChar)) {
					if (c.compareTo(preC) == 1 && nextChar.compareTo(c) == 1) {
						return false;
					}
					if (c.compareTo(preC) == 0 && nextChar.compareTo(c) == 0) {
						return false;
					}
				}

				if (Character.isLowerCase(preC) && Character.isLowerCase(c)
						&& Character.isLowerCase(nextChar)) {
					if (c.compareTo(preC) == 1 && nextChar.compareTo(c) == 1) {
						return false;
					}
					if (c.compareTo(preC) == 0 && nextChar.compareTo(c) == 0) {
						return false;
					}
				}
				if (Character.isUpperCase(preC) && Character.isUpperCase(c)
						&& Character.isUpperCase(nextChar)) {
					if (c.compareTo(preC) == 1 && nextChar.compareTo(c) == 1) {
						return false;
					}
					if (c.compareTo(preC) == 0 && nextChar.compareTo(c) == 0) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static void effectListView(ListView listView) {
		AnimationSet set = new AnimationSet(true);

		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(50);
		set.addAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(100);
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.5f);
		listView.setLayoutAnimation(controller);
	}
	
	public static int getSDKVersion(){
		return VERSION.SDK_INT;
	}
	
	public static DisplayMetrics getMetrics(Activity context){
		 DisplayMetrics dm = new DisplayMetrics();
		 context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		 return dm;
	}
}
