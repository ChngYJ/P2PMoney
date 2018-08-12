package com.example.p2pmoney12.common;

import android.app.Activity;

import java.util.Stack;

/**
 * 统一app程序当中所有的activity栈管理
 *
 * AppManager 设计成单例模式
 *
 * 添加，删除指定，删除当前，删除所有，求栈大小...
 */
public class AppManager {

    private Stack<Activity> activityStack = new Stack<>();

    public static AppManager appManager = null;

    private AppManager(){

    }

    public static AppManager getInstance(){
        if(appManager == null){
            appManager = new AppManager();
        }
        return appManager;
    }
    //添加Activity
    public void addActivity(Activity activity){
        activityStack.add(activity);
    }
    //删除Activity
    public void removeActivity(Activity activity){
        //反着拿
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity1 = activityStack.get(i);
            if (activity1.getClass().equals(activity.getClass())){
                activity1.finish();
                activityStack.remove(activity1);
                break;
            }
        }
        //增强的for循环 for(a b: c),把a类型的c集合中的每一个元素赋给b
//        for(Activity temp :activityStack){
//            if (temp.getClass().equals(activity.getClass())){
//                temp.finish();
//                activityStack.remove(temp);
//            }
//        }
    }
    //删除当前Activity
    public void removeCurrent(){
        Activity lastElement = activityStack.lastElement();
        lastElement.finish();
        activityStack.remove(lastElement);
    }
    //删除所有Activity
    public void removeAll(){
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity1 = activityStack.get(i);
            activity1.finish();
            activityStack.remove(activity1);
        }
    }
    //计算栈内有多少个activity
    public int getSize(){
        return activityStack.size();
    }
}
