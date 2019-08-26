package com.ailk.pmph.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

import com.ailk.pmph.AppContext;
import com.androidquery.util.AQUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PrefUtility {

    private static SharedPreferences mPref;

    public static SharedPreferences getPref() {
        if (mPref == null) {
            mPref = PreferenceManager.getDefaultSharedPreferences(AppContext.getContext());
        }
        return mPref;
    }

    public static void put(String name, String value) {
        SharedPreferences.Editor edit = getPref().edit();
        edit.putString(name, value);
        edit.apply();
    }

    public static void put(String name, int value) {
        SharedPreferences.Editor edit = getPref().edit();
        edit.putInt(name, value);
        edit.apply();
    }

    public static void put(String name, Long value) {
        SharedPreferences.Editor edit = getPref().edit();
        edit.putLong(name, value);
        edit.apply();
    }

    public static void put(String name, Boolean value) {
        SharedPreferences.Editor edit = getPref().edit();
        edit.putBoolean(name, value);
        edit.apply();
    }

    public static void remove(String name) {
        SharedPreferences.Editor edit = getPref().edit();
        edit.remove(name);
        edit.apply();
    }

    public static boolean contains(String name) {
        return getPref().contains(name);
    }

    public static boolean getBoolean(String name, boolean defaultValue) {
        return getPref().getBoolean(name, defaultValue);
    }

    public static Long getLong(String name, Long defaultValue) {
        return getPref().getLong(name, defaultValue);
    }

    public static String get(String name, String defaultValue) {
        return getPref().getString(name, defaultValue);
    }

    public static int get(String name, int defaultValue) {
        return getPref().getInt(name, defaultValue);
    }

    public static void putSearchHistory(String searchWord) {
        Set<String> set;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            SharedPreferences.Editor edit = getPref().edit();
            if (getPref().contains("history")) {
                set = getPref().getStringSet("history", null);
            } else {
                set = new HashSet<>();
            }
            PrefUtility.removeSearchHistory();
            if (set != null) {
                set.add(searchWord);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    edit.putStringSet("history", set);
                    edit.apply();
                }
            }
        }
    }

    public static List<String> getSearchHistory() {
        Set<String> words;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            words = getPref().getStringSet("history", null);
        } else {
            return null;
        }
        if (words == null) {
            return null;
        }
        List<String> searchWords = new ArrayList<>();
        for (String word : words) {
            searchWords.add(word);
        }
        return searchWords;
    }

    public static void removeSearchHistory() {
        SharedPreferences.Editor edit = getPref().edit();
        if (PrefUtility.contains("history")) {
            edit.remove("history");
            edit.apply();
        }
    }


    //添加店铺搜索的历史记录
    public static void putSearchStoreHistory(String searchWord) {
        Set<String> set;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            SharedPreferences.Editor edit = getPref().edit();
            if (getPref().contains("storeHistory")) {
                set = getPref().getStringSet("storeHistory", null);
            } else {
                set = new HashSet<>();
            }
            PrefUtility.removeSearchStoreHistory();
            if (set != null) {
                set.add(searchWord);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    edit.putStringSet("storeHistory", set);
                    edit.apply();
                }
            }
        }
    }

    //获取店铺的搜索历史记录
    public static List<String> getSearchStoreHistory() {
        Set<String> words;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            words = getPref().getStringSet("storeHistory", null);
        } else {
            return null;
        }
        if (words == null) {
            return null;
        }
        List<String> searchWords = new ArrayList<>();
        for (String word : words) {
            searchWords.add(word);
        }
        return searchWords;
    }

    //删除店铺搜索的历史记录
    public static void removeSearchStoreHistory() {
        SharedPreferences.Editor edit = getPref().edit();
        if (PrefUtility.contains("storeHistory")) {
            edit.remove("storeHistory");
            edit.apply();
        }
    }

    public static <T extends Enum<T>> void putEnum(Enum<T> value) {
        String key = value.getClass().getName();
        put(key, value.name());
        mEnums.put(key, value);
    }

    public static void clearEnum(Class<?> cls) {
        String key = cls.getName();
        put(key, (String) null);
    }

    private static Map<String, Object> mEnums = new HashMap<String, Object>();

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T getEnum(Class<T> cls, T defaultValue) {
        String key = cls.getName();
        T result = (T) mEnums.get(key);
        if (result == null) {
            result = PrefUtility.getPrefEnum(cls, defaultValue);
            mEnums.put(key, result);
        }
        return result;
    }

    private static <T extends Enum<T>> T getPrefEnum(Class<T> cls, T defaultValue) {
        T result = null;
        String pref = get(cls.getName(), null);
        if (pref != null) {
            try {
                result = Enum.valueOf(cls, pref);
            } catch (Exception e) {
                clearEnum(cls);
                AQUtility.report(e);
            }
        }

        if (result == null) {
            result = defaultValue;
        }

        return result;
    }



//    public static String getDeviceId() {
//
//        if (deviceId == null) {
//
//            Context context = AppContext.getContext();
//
//            TelephonyManager tm = (TelephonyManager) context
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//
//            String tmDevice, tmSerial, tmPhone, androidId;
//            tmDevice = "" + tm.getDeviceId();
//            tmSerial = "" + tm.getSimSerialNumber();
//            androidId = ""
//                    + android.provider.Settings.Secure.getString(
//                    context.getContentResolver(),
//                    android.provider.Settings.Secure.ANDROID_ID);
//
//            UUID deviceUuid = new UUID(androidId.hashCode(),
//                    ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
//            deviceId = deviceUuid.toString();
//
//        }
//
//        System.err.println("device:" + deviceId);
//
//        return deviceId;
//    }

    public static boolean isShutdown() {
        return getBoolean("hasShutdown", false);
    }

    public static void setShutdown(boolean b) {
        put("hasShutdown", b);
    }
}
