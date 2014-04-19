package com.binaryme.draganddrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

public class TestDraggable extends View {

	public int color = Color.CYAN;
	
	public TestDraggable(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		this.setMeasuredDimension(100, 100);
		Log.d("shadow","Measuring TestDraggable");
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(color);
		Log.d("shadow","Drawing TestDraggable");
	}
}
