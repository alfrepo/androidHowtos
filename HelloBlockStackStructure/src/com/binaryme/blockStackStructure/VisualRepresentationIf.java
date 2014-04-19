package com.binaryme.blockStackStructure;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

public class VisualRepresentationIf extends VisualRepresentation {

	public VisualRepresentationIf(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	  * PASS 1: Measuring the Views in the whole tree.
	  * Here this View has to provide its size, with respect to the wishes of the Parent  
	  */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

       /*
        * How large is a standard sized Block?
        */
       Resources res = getResources();
       int mHeight = (int) res.getDimension(R.dimen.block_height);
       int mWidth = (int) res.getDimension(R.dimen.block_width);
		
       /*
        * At least set the Measured dimensions. 
        * This method has to be called by onMeasure() by Contract, otherwise an Exception will be thrown. 
        */
       setMeasuredDimension(mWidth, mHeight);		
	}
	
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		/*
		 * TODO: Test which helpful Data you can get here for drawing the visual representation of this block. 
		 */
		
		int mLeft = getLeft();
		int mRight = getRight();
		int mTop = getTop();
		int mBottom = getBottom();

		
		ColorDrawable mDrawable = new ColorDrawable(Color.LTGRAY);		//create a new Rectangle
		mDrawable.setBounds(mLeft, mTop, mRight, mBottom);				//set Dimensions to be as Large as Canvas
		
		Log.d("MyActivity", "Canvas Width : "+canvas.getWidth()+ " and Height : "+canvas.getHeight());
		Log.d("MyActivity", "getLeft : "	+mLeft);
		Log.d("MyActivity", "getRight : "	+mRight);
		Log.d("MyActivity", "getTop : "		+mTop);
		Log.d("MyActivity", "getBottom : "	+mBottom);
		
		mDrawable.draw(canvas);
		
		super.onDraw(canvas);
	}
}
