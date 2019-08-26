package com.ailk.pmph.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.tool
 * 作者: Chrizz
 * 时间: 2016/4/26 13:44
 */
public class UpdateManager implements Runnable{

    private ProgressDialog dialog = null;
    private static Context context = null;
    private String url = null;

    public UpdateManager(Context context, String url) {
        this.context = context;
        this.url = url;
    }


    @Override
    public void run() {
        Message msg = new Message();
        msg.what = 0x001;
        handler.sendMessage(msg);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x001:
                    downloadApk();
                    break;
                case 0x002:
                    ToastUtil.showCenter(context, "下载新版本失败");
                    break;
            }
        }
    };

    private void downloadApk() {
        dialog = new  ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMessage("正在下载更新");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        new Thread(){
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(url, dialog);
                    sleep(3000);
                    TDevice.installApk(file, context);
                    dialog.dismiss();
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = 0x002;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }}.start();
    }

    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception{
        if(TDevice.hasSDCard(context) && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(path);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "pmph.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len ;
            int total=0;
            while((len =bis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
                total+= len;
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

}
