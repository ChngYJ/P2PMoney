package com.example.p2pmoney12.common;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

//import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MyApplication extends Application {

    public static Context context = null;

    public static Handler handler = null;

    public static Thread mainThread = null;

    public static int mainThreadId = 0;

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate() {
        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId =android.os.Process.myTid();
        //系统一启动，一出现异常，就会到CrashHandler类处理，不经过默认的
//        CrashHandler.getInstance().init(this);

    }
}
