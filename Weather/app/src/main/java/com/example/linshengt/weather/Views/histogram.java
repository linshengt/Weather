package com.example.linshengt.weather.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by 94437 on 2016/11/7.
 */
public class histogram extends View {

    private String TAG = "histogram";
    private Paint mPaint;
    private Paint mPaintText;
    private int w, h;
    private int highTemp=20, lowTemp=10;
    private float offset, histogramW;


    public histogram(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(Color.GRAY);
        mPaintText.setTextSize(30);

        histogramW = w/4;

    }

    public void setTemp(int nhighTemp, int nlowTemp){
        this.highTemp = nhighTemp;
        this.lowTemp = nlowTemp;

        invalidate();

    }

    private int x2y(int x){
        return -2*h*(x - 20)/120;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();

        canvas.translate(w/2, h/2);

        canvas.drawCircle(0,x2y(highTemp), histogramW, mPaint);
        canvas.drawRect(-histogramW, x2y(highTemp), histogramW, x2y(lowTemp), mPaint);
        canvas.drawCircle(0, x2y(lowTemp), histogramW, mPaint);
        canvas.drawText(""+highTemp, -mPaintText.measureText(""+highTemp)/2, x2y(highTemp)-w, mPaintText);
        canvas.drawText(""+lowTemp, -mPaintText.measureText(""+highTemp)/2, x2y(lowTemp)+w, mPaintText);
    }
}
