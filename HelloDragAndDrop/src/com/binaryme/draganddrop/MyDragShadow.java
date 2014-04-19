package com.binaryme.draganddrop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

public class MyDragShadow extends View.DragShadowBuilder {
	
	public int color = Color.RED;
	
	@Override
	public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
		shadowSize.x = 200;
		shadowSize.y = 200;
		
		shadowTouchPoint.x = 10;
		shadowTouchPoint.y = 10;
		
		Log.d("shadow","The shadow point x : "+shadowTouchPoint.x);
		Log.d("shadow","The shadow point y : "+shadowTouchPoint.y);
		
		Log.d("shadow","The shadow width : "+shadowSize.x);
		Log.d("shadow","The shadow height: "+shadowSize.y);
	}
	
	@Override
	public void onDrawShadow(Canvas canvas) {
		canvas.drawColor(color);
		Log.d("shadow","Drawing shadow");
	}
}