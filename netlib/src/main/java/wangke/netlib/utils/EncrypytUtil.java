package wangke.netlib.utils;

import com.apkfuns.logutils.LogUtils;

/**
 * Project : Net Lib
 * Created by 王可 on 16/8/31.
 */
public class EncrypytUtil {
    private final static String PASSWORD = "APP_DESKEY";
    public static String enrypytJson(String clearTextString){
        try {
            LogUtils.d("明文：" + clearTextString);
            LogUtils.d("密文：" + DESUtils.encrypt(clearTextString, PASSWORD));
            LogUtils.d("压缩：" + GZipUtils.compress(DESUtils.encrypt(clearTextString, PASSWORD).getBytes()));
            LogUtils.d("BASE64：" + DESUtils.encryptBASE64(GZipUtils.compress(DESUtils.encrypt(clearTextString, PASSWORD).getBytes())));


            return DESUtils.encryptBASE64(GZipUtils.compress(DESUtils.encrypt(clearTextString, PASSWORD).getBytes()));


        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("加密失败：" + clearTextString);
            return "";
        }

    }
    public static String decryptJson(String chiperTextString){
        try {
            String temp = new String(GZipUtils.decompress(DESUtils.decryptBASE64(chiperTextString)), "utf-8");
            String str = DESUtils.decrypt(temp,PASSWORD);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("解密失败：" + chiperTextString);
            return "";
        }

    }
}
