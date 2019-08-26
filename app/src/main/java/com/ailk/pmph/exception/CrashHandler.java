package com.ailk.pmph.exception;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Looper;
import android.text.format.Time;
import android.widget.Toast;


import com.ailk.pmph.utils.AppUtility;
import com.ailk.pmph.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.TreeSet;

public class CrashHandler implements UncaughtExceptionHandler {

    /** Debug Log tag */
    public static final String TAG = "CrashHandler";
    /** 系统默认的UncaughtException处理类 */
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /** CrashHandler实例 */
    private static CrashHandler INSTANCE;
    /** 程序的Context对象 */
    private Context mContext;
    /** 使用Properties来保存设备的信息和错误堆栈信息 */
    private StringBuilder myStringBuilder = new StringBuilder(2048);
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    /** 错误报告文件的扩展名 */
    private static final String CRASH_REPORTER_EXTENSION = ".txt";

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        LogUtil.e(ex);

        if (ex instanceof SQLiteException) {
            LogUtil.e("handleException --- SQLiteException ,部分机型webview读取history出错，略过");
            collectInfo(ex);
            return;
        }

        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
//            ObjectSaveUtil.saveObj(mContext, AppUtility.getInstance());
            AppUtility.getInstance().setShutdown(true);
            if (mContext instanceof Activity) {
                AppUtility.getInstance().setShutdown(true);
                ((Activity) mContext).finish();
            }

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);

        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            LogUtil.e("handleException --- ex==null");
            return true;
        }
        final String msg = ex.getLocalizedMessage();
        if (msg == null) {
            return false;
        }
        // 使用Toast来显示异常信息
//		ToastUtil.show(mContext.getApplicationContext(), "程序出错，即将退出:\r\n" + msg);
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "程序出错，即将退出", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        collectInfo(ex);

        try {
            Thread.sleep(3000);
        } catch(InterruptedException e) {

        }
        AppUtility.getInstance().setShutdown(true);
        if (mContext instanceof Activity) {
            ((Activity) mContext).finish();
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
        return true;
    }

    /**
     * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
     */
    public void sendPreviousReportsToServer() {
        sendCrashReportsToServer(mContext);
    }

    private void collectInfo(Throwable ex) {
        // 收集设备信息
        collectCrashDeviceInfo(mContext);
        // 保存错误报告文件
        saveCrashInfoToFile(ex);
        // 发送错误报告到服务器
        sendCrashReportsToServer(mContext);
    }

    /**
     * 把错误报告发送给服务器,包含新产生的和以前没发送的.
     *
     * @param ctx
     */
    private void sendCrashReportsToServer(Context ctx) {
        String[] crFiles = getCrashReportFiles(ctx);
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<String>();
            sortedFiles.addAll(Arrays.asList(crFiles));
            for (String fileName : sortedFiles) {
                File cr = new File(ctx.getFilesDir(), fileName);
                postReport(cr);
            }
        }
    }

    /**
     * @param file
     */
    private void postReport(final File file) {
       /*
       *  CGerrorRequest request = new CGerrorRequest();
        try {
            request.setError_Content(FileUtil.readFile(file));
            try {
                String fileName = file.getName();
                fileName = fileName.substring(fileName.indexOf("-")+1, fileName.lastIndexOf("-"));
                request.setDevice_info(fileName);
            } catch (Exception e) {
                request.setDevice_info("android");
            }
            new JsonService(mContext).requestError(mContext, request,
                    new CallBack<GXCBody>() {

                        @Override
                        public void oncallback(GXCBody t) {
                            file.delete();
                        }

                        @Override
                        public void onErro(GXCHeader header) {

                        }
                    });
        } catch (IOException e) {
            LogUtil.e(e);
        }

       * */
    }

    /**
     * 获取错误报告文件名
     *
     * @param ctx
     * @return
     */
    private String[] getCrashReportFiles(Context ctx) {
        File filesDir = ctx.getFilesDir();
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        return filesDir.list(filter);
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return
     */
    private String saveCrashInfoToFile(Throwable ex) {
        try {
            Writer info = new StringWriter();
            PrintWriter printWriter = new PrintWriter(info);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            String result = info.toString().replaceAll("\n\t", "\r\n");
            printWriter.close();
            myStringBuilder.append(result);

            // long timestamp = System.currentTimeMillis();
            Time t = new Time("GMT+8");
            t.setToNow(); // 取得系统时间
            int date = t.year * 10000 + t.month * 100 + t.monthDay;
            int time = t.hour * 10000 + t.minute * 100 + t.second;
            String fileName = "crash-" + date + "-" + time
                    + CRASH_REPORTER_EXTENSION;
            FileOutputStream trace = mContext.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            byte [] bytes = myStringBuilder.toString().trim().getBytes();
            trace.write(bytes);
            trace.flush();
            trace.close();
            return fileName;
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return null;
    }


    /**
     * 收集程序崩溃的设备信息
     *
     * @param ctx
     */
    public void collectCrashDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                myStringBuilder.append(VERSION_NAME+":"+
                        pi.versionName == null ? "not set" : pi.versionName);
                myStringBuilder.append("\t");
                myStringBuilder.append(VERSION_CODE+":"+ pi.versionCode);
                myStringBuilder.append("\r\n");
            }
        } catch (NameNotFoundException e) {
            LogUtil.e(e);
        }
        // 使用反射来收集设备信息.在Build类中包含各种设备信息,
        // 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        // 具体信息请参考后面的截图
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                myStringBuilder.append(field.getName()+":" + field.get(null)+" ;");
                myStringBuilder.append("\t");
                LogUtil.e(field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }
        myStringBuilder.append("\r\n");
    }
}