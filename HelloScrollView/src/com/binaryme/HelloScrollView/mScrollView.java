package com.binaryme.HelloScrollView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

public class mScrollView extends ScrollView {

	public mScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	public mScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public mScrollView(Context context) {
		super(context);
		init();
	}
	void init(){}
	
//	@Override
//	protected void onDraw(Canvas canvas) {
//		Log.d("MyActivity", "Before drawing of Absolute Layout");
//		super.onDraw(canvas);
//		Log.d("MyActivity", "After drawing of Absolute Layout");
//	}
//	
//	@Override
//	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		Log.d("MyActivity", "Before drawing of Absolute Layout");
//		super.onLayout(changed, l, t, r, b);
//		Log.d("MyActivity", "After drawing of Absolute Layout");
//	}
//	
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		Log.d("MyActivity", "Before drawing of Absolute Layout");
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
//		Log.d("MyActivity", "After drawing of Absolute Layout");
//	}
}
