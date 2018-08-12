package com.example.p2pmoney12.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class Flowlayout extends ViewGroup {

    public Flowlayout(Context context) {
        super(context);
    }
    public Flowlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Flowlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *  重写onMeasure方法，求取布局的宽高
     *  测量规格 = 测量模式和 测量值的混合
     * @param widthMeasureSpec --- 宽度的测量规格
     * @param heightMeasureSpec ---高度的测量规格
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;
        int lineWidth = 0;
        int lineHeight = 0;
        //求取at_most 模式下的布局宽，高值
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            MarginLayoutParams mp = (MarginLayoutParams) child.getLayoutParams();
            if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin >widthSize){
                //换行: 宽度--对比获取
                width = Math.max(width,lineWidth);
                height+=lineHeight;
                //变量重置一下
                lineWidth = childWidth+mp.leftMargin+mp.rightMargin;
                lineHeight = childHeight+mp.topMargin+mp.bottomMargin;
            }else {
                //不换行: 行高--对比获取
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = Math.max(lineHeight,childHeight+mp.topMargin+mp.bottomMargin);
            }
            //最后一次 还要参与计算一次
            if (i == cCount-1){
                width = Math.max(width,lineWidth);
                height += lineHeight;

            }

        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ?widthSize: width,
                heightMode == MeasureSpec.EXACTLY ? heightSize:height);

    }

    //每一行所包含的所有子View
    private List<List<View>> allViews = new ArrayList<>();

    private List<Integer> allHeights = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int width = getWidth();  //获取总宽度
        int cCount = getChildCount(); //获得所有的子View

        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<>();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth(); //当前宽度
            int childHeight = child.getMeasuredHeight();
            MarginLayoutParams mp = (MarginLayoutParams) child.getLayoutParams();

            if (lineWidth+childWidth+mp.leftMargin+mp.rightMargin >width){
                //换行
                allViews.add(lineViews);
                allHeights.add(lineHeight);
                //重置一下变量
                lineViews = new ArrayList<>();
                lineWidth = 0;
                lineHeight = childHeight + mp.topMargin + mp.bottomMargin;
            }
            //不换行
            lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
            lineHeight = Math.max(lineHeight,childHeight+mp.topMargin+mp.bottomMargin);
            lineViews.add(child);

            if (i == cCount-1){
                allViews.add(lineViews);
                allHeights.add(lineHeight);
            }

        }
//        Log.e("Cyj","allViews:"+allViews.size()+"----allHeights"+allHeights.size());

        //通过计算每一行的每一个子View的left,top,right,bottom 来摆放每一行的每一个子View的位置
        int left = 0;
        int top = 0;

        for (int i = 0; i < allViews.size(); i++) {
            int curLineHeught = allHeights.get(i);
            //当前行的所有子View
            List<View> views = allViews.get(i);
            for (View view : views) {
                int viewWidth = view.getMeasuredWidth();
                int viewHeught = view.getMeasuredHeight();
                MarginLayoutParams mp = (MarginLayoutParams) view.getLayoutParams();


            }
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        MarginLayoutParams mp = new MarginLayoutParams(getContext(), attrs);
        return mp;
    }
}
