package com.example.p2pmoney12.util;

import android.content.Context;
import android.view.View;

import android.os.Handler;

import com.example.p2pmoney12.common.MyApplication;



public class UIUtils {

    public static int getColor(int colorId){
        return getContext().getResources().getColor(colorId);
    }

    public static View getXmlView(int layoutId){
        return View.inflate(getContext(),layoutId,null);
    }

    public static String[] getStringArr(int arrId){
        return getContext().getResources().getStringArray(arrId);
    }

    /**
     * dp单位转换到px
     * 1dp---1px
     * 1dp--0.75px
     * 1dp--0.5px
     * @param dp
     * @return
     */
    public static int dp2px(int dp){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int)(dp*density+0.5);
    }
    public static int px2dp(int px){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int)(px/density+0.5);
    }

    public static Context getContext(){
        return MyApplication.context;
    }


    public static Handler gethandler(){
        return MyApplication.handler;
    }

    /**
     * 定义一个方法
     * 保证runnable对象的run方法是运行在主线程当中
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable){
        if (isInMainThread()){
            runnable.run(); //是主线程，就run
        }else {
            gethandler().post(runnable);


        }
    }
    //定义一个方法
    private static boolean isInMainThread() {
        //当前线程的id
        int tid = android.os.Process.myTid();
        if (tid == MyApplication.mainThreadId){
            return true; //是主线程
        }

        return false;
    }
}
