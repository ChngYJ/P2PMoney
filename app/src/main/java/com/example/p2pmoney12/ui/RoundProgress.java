package com.example.p2pmoney12.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.p2pmoney12.R;

public class RoundProgress extends View {

    private Paint paint = new Paint();

    private int roundColor;
    private int roundProgressColor;
    private int textColor;
    private float textSize;
    private float roundWidth;
    private int max = 100;

    private int progress = 50;

    public RoundProgress(Context context) {
        this(context,null);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
        //圆环的颜色
        roundColor = mTypedArray.getColor(R.styleable.RoundProgress_roundColor, Color.RED);
        //圆环进度的颜色
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgress_roundProgressColor, Color.GREEN);
        //中间进度百分比文字字符串的颜色
        textColor = mTypedArray.getColor(R.styleable.RoundProgress_textColor, Color.GREEN);
        //中间进度百分比的字符串的字体大小
        textSize = mTypedArray.getDimension(R.styleable.RoundProgress_textSize, 15);
        //圆环的宽度
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgress_roundWidth, 5);
        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //[1]绘制一个最外层的圆
        paint.setColor(roundColor);
        paint.setStrokeWidth(roundWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        int center = getWidth()/2;  //圆心值
        int radius = (int) (center-roundWidth/2);  //半径
        canvas.drawCircle(center,center,radius,paint);

        //[2]绘制正中间的文本
        float textWidth = paint.measureText(progress+"%");
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);
        canvas.drawText(progress+"%",center-textWidth/2,center+textSize/2,paint);

        //[3]
        /**
         * oval：绘制弧形圈所包含的矩形范围轮廓
         * 0 ：开始的角度
         * 360*progress/max ：扫描的角度
         * false ：是否包含圆心
         * paint:绘制弧形的画笔
         */
        RectF oval = new RectF(center-radius,center-radius,center+radius,center+radius);
        paint.setColor(roundProgressColor);
        paint.setStrokeWidth(roundWidth);
        paint.setStyle(Paint.Style.STROKE);  //STROKE 空心
        canvas.drawArc(oval,0,360*progress/max,false,paint);

    }
   //定义一个方法，动态绘制弧形
   public void setProgess(int progess){
        this.progress = progess;
        if (progess>100){
            this.progress = 100;
        }
        postInvalidate(); //导致onDraw重新执行


   }
}
