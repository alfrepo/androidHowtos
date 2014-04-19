package com.binaryme.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;

/**
 * Create own view, based on Google's native TextView implementation {@link http://www.google.com/codesearch/p?hl=en#uX1GffpyOZk/core/java/android/widget/TextView.java&q=textview&exact_package=git://android.git.kernel.org/platform/frameworks/base.git&sa=N&cd=1&ct=rc}
 * @author skip
 *
 */
public class GraphicsView extends View {

	public GraphicsView(Context context) {
				
		super(context);
		
	}
	
	
	/**
	  * PASS 1: Measuring the Views in the whole tree.
	  * Here this View has to provide its size, with respect to the wishes of the Parent  
	  */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /*
         * How large do our own View wants to be?
         */
        Resources res = getResources();
        int mHeight = (int) res.getDimension(R.dimen.block_height);
        int mWidth = (int) res.getDimension(R.dimen.block_width);
		
        /**
         *  Block's size is mode independent 
         */
		
		/*
		 *  How large do our parent want this view to be? 
		 *  Which mode does the parent ask us about: EXACTLY, AT_MOST, UNSPECIFIED? Mode appropriate Integer can be obtained as Constant in "MeasureSpec. "
		 */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        
        if(widthMode == MeasureSpec.EXACTLY ){
        	mWidth = widthSize; 	//Parent has told us how big to be. So be it.
        }else if( widthMode == MeasureSpec.AT_MOST && mWidth > widthSize){
        	mWidth = widthSize;		//Block size exceeds AT_MOST boundaries. Resize it.
        }else{
        	//width not changed
        }
        	
        if(heightMode == MeasureSpec.EXACTLY){
        	mHeight = heightSize; 	//Parent has told us how big to be. So be it.
        }else if( widthMode == MeasureSpec.AT_MOST && mHeight > heightSize){
        	mHeight = heightSize;	//Block size exceeds AT_MOST boundaries. Resize it.
        }else{
        	//height not changed
        }
        

        /*
         * At least set the Measured dimensions. 
         * This method has to be called by onMeasure() by Contract, otherwise an Exception will be thrown. 
         */
        setMeasuredDimension(mWidth, mHeight);		
	}
	
	/**
	  * PASS 2: Setting the layout of Views, including it's position according with respect to parent's wishes and measured size.
	  * Here this View has to provide its size, with respect to the wishes of the Parent
	  * Using default implementation of onLayout : onLayout(boolean changed, int left, int top, int right, int bottom)
	  */
	
	
	/**
	  * PASS 3: Drawing the View, on the positions defined in PASS 3
	  */
	@Override
	protected void onDraw(Canvas canvas) {
		
		int mLeft = getLeft();
		int mRight = getRight();
		int mTop = getTop();
		int mBottom = getBottom();
		
		//create a new Rectangle
		ColorDrawable mDrawable = new ColorDrawable(Color.LTGRAY);
		//set Dimensions to be as Large as Canvas
		mDrawable.setBounds(mLeft, mTop, mRight, mBottom);
		
		Log.d("MyActivity", "Canvas Width : "+canvas.getWidth()+ " and Height : "+canvas.getHeight());
		Log.d("MyActivity", "getLeft : "	+mLeft);
		Log.d("MyActivity", "getRight : "	+mRight);
		Log.d("MyActivity", "getTop : "		+mTop);
		Log.d("MyActivity", "getBottom : "	+mBottom);
		
		mDrawable.draw(canvas);
		
		super.onDraw(canvas);
	}
	

}
