package com.ailk.pmph.utils;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/5.
 * json数据的加密解密
 * ·加密过程：
 *  采用DES进行加密；
 *  对1的结果，进行gzip压缩
 *  对2的结果，转base64
 * ·解密过程：
 *  将字符串根据base64解密
 *  对1的结果，进行gzip解压
 *  对2的结果，采用DES解密
 * ·密钥：APP_DESKEY
 */
public class EncrypytUtil {
    private final static String PASSWORD = "APP_DESKEY";
    public static String enrypytJson(String clearTextString){
        try {
            LogUtil.d("明文：" + clearTextString);
            LogUtil.d("密文：" + DESUtils.encrypt(clearTextString, PASSWORD));
            LogUtil.d("压缩：" + GZipUtils.compress(DESUtils.encrypt(clearTextString, PASSWORD).getBytes()));
            LogUtil.d("BASE64：" + DESUtils.encryptBASE64(GZipUtils.compress(DESUtils.encrypt(clearTextString, PASSWORD).getBytes())));


            return DESUtils.encryptBASE64(GZipUtils.compress(DESUtils.encrypt(clearTextString, PASSWORD).getBytes()));


        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("加密失败：" + clearTextString);
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
            LogUtil.e("解密失败：" + chiperTextString);
            return "";
        }

    }
}
