package com.zz.fundapp;


import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by corn on 2016/7/18.
 */
public class HyLog {
    public interface OnLogCallBackListener {
        void OnCallBack(String log);
    }

    public static final String LOG_TAG = "hy_log";
    private static boolean DEBUG = false;

    public static void setDEBUG(boolean debug) {
        HyLog.e("setDEBUG :" + debug);
        DEBUG = debug;
    }

    public static void setContext() {
    }


    public static void d(String msg) {
//        Log.e(LOG_TAG,msg);
        Log(Log.DEBUG,msg);
        if (DEBUG) {
            writeTxtToFile("【d】" + LOG_TAG + ":" + msg);
        }
    }

    public static void e(String msg) {
//        Log.e(LOG_TAG,msg);
        Log(Log.ERROR,msg);
        if (DEBUG) {
            writeTxtToFile("【e】" + LOG_TAG + ":" + msg);
        }
    }

    public static void i(String msg) {
//        Log.i(LOG_TAG,msg);
        Log(Log.INFO,msg);
        if (DEBUG) {
            writeTxtToFile("【i】" + LOG_TAG + ":" + msg);
        }
    }

    public static void e(String tag, String msg) {
        Log(Log.ERROR,msg);
        if (DEBUG) {
            writeTxtToFile("【e】" + tag + ":" + msg);
        }
    }

    private static SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
    // 将字符串写入到文本文件中
    public static void writeTxtToFile(String strcontent/*, String filePath, String fileName*/) {
        //存储目录
//        String appName = "Umidigi";
//        String name = "log";
//        String filePath = Objects.requireNonNull(getExternalFilesDir(null)).getAbsolutePath();
//        String fileName = "logcat-" + simpleDateFormat1.format(new Date()) + ".log";
//
//        String path = filePath;
//        makeFilePath(path, fileName);
//
//        String strFilePath = path + fileName;
//        // 每次写入时，都换行写
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        String now = sdf.format(new Date());
//        String strContent = now + ":" + strcontent + "\r\n";
//        try {
//            File file = new File(strFilePath);
//            if (!file.exists()) {
//                Log.d("TestFile", "Create the file:" + strFilePath);
//                if(file.getParentFile() != null){
//                    file.getParentFile().mkdirs();
//                }
//                file.createNewFile();
//            }
//            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
//            raf.seek(file.length());
//            raf.write(strContent.getBytes());
//            raf.close();
//        } catch (Exception e) {
//            Log.e("TestFile", "Error on write File:" + e);
//        }
    }

    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }
    }


    public static void Log(int priority,String msg){
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        int index = findIndex(elements);
        if(index ==  -1 || index >= elements.length){
            Log.e(LOG_TAG,msg);
            return;
        }
        StackTraceElement element = elements[index];
        String tag = handleTag(element, LOG_TAG);
        String content = "(" +element.getFileName()+":"+ element.getLineNumber()+")." + element.getMethodName() + ": " + msg;
//        for (int i = 0; i < elements.length; i++) {
//            element = elements[i];
//            Log.e("test", "(" + element.getFileName()+":"+ element.getLineNumber()+")." + element.getMethodName() + "---");
//        }
        Log.println(priority, tag, content);
    }

    /**
     * 寻找当前调用类在[elements]中的下标
     */
    public static Integer findIndex( StackTraceElement[] elements) {
        int index = 4;
        while (index < elements.length) {
            String className = elements[index].getClassName();
            if (!className.equalsIgnoreCase(HyLog.class.getName()) && !elements[index].getMethodName().startsWith("log")) {
                return index;
            }
            index++;
        }
        return -1;
    }
    public static String handleTag(StackTraceElement element, String customTag){
        if(customTag.isEmpty()){
            return customTag;
        }else{
//            return element.getClassName();/*.substring(".").lastIndexOf()*/
            return LOG_TAG;
        }
    }
}
