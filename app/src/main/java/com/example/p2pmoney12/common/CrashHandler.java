package com.example.p2pmoney12.common;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * 程序当中的全局异常捕获
 *
 * CrashHandler 设成单例模式
 *
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private  static CrashHandler crashHandler = null;
    //构造单例模式
    private CrashHandler(){

    }
    public static CrashHandler getInstance(){
        if (crashHandler == null){
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    private Context mContext;
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    //把CrashHandler它设置成系统默认的异常捕获处理器
    public void init(Context context){
        this.mContext = context;
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //this 是我们的CrashHandler
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 把提示信息汉化，
     * 记录一下日志信息，反馈给后台
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
  //    Log.e("验证","CrashHandler---uncaughtException...");
        if (isHandle(e)){
            //自己处理
            handleException(t,e);
        }else {
            //系统处理
            defaultUncaughtExceptionHandler.uncaughtException(t,e);
        }


    }

    /**
     * 判断是否需要自己处理
     * @param e
     * @return
     */
    public boolean isHandle(Throwable e){
        if (e == null){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 自定义异常处理
     * @param t
     * @param e
     */
    private void handleException(Thread t,Throwable e) {
        new Thread(){
            @Override
            public void run() {
                //Android 系统当中，默认情况下，线程是没有开启looper消息处理的，但是主线程除外
                Looper.prepare(); //开启loop
                Toast.makeText(mContext,"抱歉，系统出现未知异常，即将退出...",Toast.LENGTH_SHORT).show();
                Looper.loop(); //loop工作
            }
        }.start();
        //定义一个方法，搜集异常信息
        collectionException(e);
        try {
            Thread.sleep(2000);
            AppManager.getInstance().removeAll();
            android.os.Process.killProcess(android.os.Process.myPid());
            //关闭虚拟机，释放所有内存
            System.exit(0);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    //定义一个方法，搜集崩溃异常信息
    private void collectionException(Throwable e) {
        final String deviceInfo = Build.DEVICE+Build.VERSION.SDK_INT+Build.MODEL+Build.PRODUCT;
        final String errorInfo = e.getMessage();
        new Thread(){
            @Override
            public void run() {
                Log.e("验证","deviceInfo---"+deviceInfo+"errorInfo"+errorInfo);

            }
        }.start();
    }
}
