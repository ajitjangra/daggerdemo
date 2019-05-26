package com.asj.weather.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.asj.weather.R;

public class AsjCircleView extends View {

  //circle and text colors
  private int circleCol, labelCol;
  //label text
  private String circleText;
  //paint for drawing custom view
  private Paint circlePaint;
  private int viewWidthHalf;
  private int viewHeightHalf;


  public AsjCircleView(Context context, AttributeSet attrs) {
    super(context, attrs);
    System.out.println("asj AsjCircleView is called");

    //paint object for drawing in onDraw
    circlePaint = new Paint();

    //get the attributes specified in attrs.xml using the name we included
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
        R.styleable.LovelyView, 0, 0);

    try {
      //get the text and colors specified using the names in attrs.xml
      circleText = a.getString(R.styleable.LovelyView_circleLabel);
      circleCol = a.getInteger(R.styleable.LovelyView_circleColor, 0);//0 is default
      labelCol = a.getInteger(R.styleable.LovelyView_labelColor, 0);
    } finally {
      a.recycle();
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    //draw the View

    System.out.println("asj onDraw is called");

    //get the radius as half of the width or height, whichever is smaller
//subtract ten so that it has some space around it
    int radius = 0;
    if(viewWidthHalf>viewHeightHalf)
      radius=viewHeightHalf-10;
    else
      radius=viewWidthHalf-10;

    circlePaint.setStyle(Paint.Style.FILL);
    circlePaint.setAntiAlias(true);

    //set the paint color using the circle color specified
    circlePaint.setColor(circleCol);

    canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circlePaint);

    //set the text color using the color specified
    circlePaint.setColor(labelCol);

    //set text properties
    circlePaint.setTextAlign(Paint.Align.CENTER);
    circlePaint.setTextSize(50);

    //draw the text using the string attribute and chosen properties
    canvas.drawText(circleText, viewWidthHalf, viewHeightHalf, circlePaint);
  }

  public void setCircleColor(int newColor){
    //update the instance variable
    circleCol=newColor;
    //redraw the view
    invalidate();
    requestLayout();
  }
  public void setLabelColor(int newColor){
    //update the instance variable
    labelCol=newColor;
    //redraw the view
    invalidate();
    requestLayout();
  }

  public int getCircleColor(){
    return circleCol;
  }

  public int getLabelColor(){
    return labelCol;
  }

  public String getLabelText(){
    return circleText;
  }

  public void setLabelText(String newLabel){
    //update the instance variable
    circleText=newLabel;
    //redraw the view
    invalidate();
    requestLayout();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    System.out.println("asj onMeasure");
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    System.out.println("asj onSizeChanged");

    //get half of the width and height as we are working with a circle
    viewWidthHalf = w/2;
    viewHeightHalf = h/2;
  }
}
