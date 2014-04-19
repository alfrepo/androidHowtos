package com.binaryme.HelloScrollView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class tempRect extends View {
	
	private int mWidth = 200; 
	private int mHeight = 200; 
	
	public tempRect(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	public tempRect(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public tempRect(Context context) {
		super(context);
		init();
	}
	
	void init(){
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		
		//fill canvas
		canvas.drawColor(Color.YELLOW);
		
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(false);
		paint.setColor(Color.RED);
		canvas.drawRect(0, 0, 400, 40, paint);
		
		paint.setColor(Color.CYAN);
		canvas.drawRect(190, 190, mWidth/2, mHeight/2, paint);
		
		Log.d("MyActivity","Drawing now");
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int scaledW = mWidth;
		int scaledH = mHeight;
		
		super.onMeasure(scaledW,scaledH);
		
		this.setMeasuredDimension(scaledW, scaledH);
		
		int h = this.getMeasuredHeight();
		int w = this.getMeasuredWidth();
		Log.d("MyActivity","hows measured size now?");
	}
	
	
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		
		int l = this.getLeft();
		int t = this.getTop();
		int r = this.getRight();
		int b = this.getBottom();
		super.onLayout(true, left, top, right, bottom);
		Log.d("subview","Top: "+top+" , Bottom "+bottom);
	}

}
