package com.example.p2pmoney12.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import java.util.jar.Attributes;
//伸缩回单
public class MyScrollview extends ScrollView {

    private View innerView;  //这个就是要操作的布局
    private float y;
    private Rect normal = new Rect();
    private boolean animationFinish = true;

    public MyScrollview(Context context) {
        super(context);
    }
    public MyScrollview(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    public MyScrollview(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }
    //[1]当指定的xml view已经解析完毕，布局绘制完毕，变成view对象供你操作，可以回调这个方法
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onFinishInflate() {
        int childCount = getChildCount();
        if (childCount>0){
            innerView = getChildAt(0);

        }
    }
    //[2]重写onTouch事件

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (innerView == null){
            return super.onTouchEvent(ev);
        }else {
            commonTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 定义一个方法，自定义touch事件处理
     * @param ev
     */
    private void commonTouchEvent(MotionEvent ev) {
        if (animationFinish){

        }
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN: //按下
                //记录一下当前Y点
                y = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE: //移动
                //计算上一次与本次的Y轴（拉动距离）而不是按下时候的Y值，和现在移动到的Y值，每上一次和本次的Y值比较
                float perY = y == 0 ? ev.getY() : y;
                float nowY = ev.getY();
                int detailY = (int) (perY-nowY);
                y = nowY;
                //操作view 进行拖动detailY的一半
                if (isNeedMove()){
                    //布局改变位置前，记录一下正常状态的位置
                    if (normal.isEmpty()){
                        normal.set(innerView.getLeft(),innerView.getTop(),innerView.getRight(),innerView.getBottom());

                    }
                    innerView.layout(innerView.getLeft(),innerView.getTop()-detailY/2,
                            innerView.getRight(),innerView.getBottom()-detailY/2);
                }
                break;
            case MotionEvent.ACTION_UP: //抬起
                y = 0;
                //把布局回滚到原来位置
                if (isNeedAnimation()){
                    animation();
                }
                break;
        }
    }
    //定义一个方法
    private void animation() {
        TranslateAnimation ta = new TranslateAnimation(0,0,0,normal.top-innerView.getTop());
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationFinish = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                innerView.clearAnimation();
                innerView.layout(normal.left,normal.top,normal.right,normal.bottom);
                normal.setEmpty();
                animationFinish = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        innerView.startAnimation(ta);
    }

    //定义一个方法，判断是否需要回滚
    private boolean isNeedAnimation() {
       return !normal.isEmpty();

    }

    //定义一个方法，判断是否需要拖动
    private boolean isNeedMove() {
        //offset 高度偏移量
        // getMeasuredHeight 原始测量高度，与屏幕无关，控件多大，它就多大
        //getHeight 是在屏幕上显示的高度
        int offset = innerView.getMeasuredHeight()-getHeight();
        int scrollY = getScrollY();  //Y轴滚动距离
     //   Log.e("验证","offset---"+offset+"---scrollY"+scrollY);
        if (scrollY == 0 || scrollY == offset){
            return true;
        }
        return false;
    }
}
