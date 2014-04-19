package com.binaryme.HelloHorizontalScrollView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.HorizontalScrollView;

public class mHorizontalScrollView extends HorizontalScrollView{

	
	public mHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
			super(context, attrs, defStyle);
	        init();
		}
		public mHorizontalScrollView(Context context, AttributeSet attrs) {
			super(context, attrs);
	        init();
		}
		public mHorizontalScrollView(Context context) {
			super(context);
	        init();
		}
		
	    public void init() {
	    } 
	    
	    @Override
	    protected void onDraw(Canvas canvas) {
	    	Log.d("MyActivity","Drawing HorizontalScrollView");
	    	super.onDraw(canvas);
	    }
}
