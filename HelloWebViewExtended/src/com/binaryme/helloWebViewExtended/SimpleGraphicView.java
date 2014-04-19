package com.binaryme.helloWebViewExtended;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.FillType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;

public class SimpleGraphicView extends View{
		
	private float mOldTouchX;
	private float mOldTouchY;
	
	private WebView canvasView;
	
	
	public SimpleGraphicView(Context context) {
		super(context);
	}
	
	public SimpleGraphicView(Context context, WebView canvasView){
		super(context);
		this.canvasView = canvasView;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		float mscale = canvasView.getScale();
		
		//set static default Size
		Resources res = this.getResources();
		LayoutParams params = this.getLayoutParams();
		
		int width = (int) res.getDimension(R.dimen.block_width);
		int height = (int) res.getDimension(R.dimen.block_height);
		params.width= (int) (width*mscale);
		params.height= (int) (height*mscale);
				
		this.setLayoutParams(params);
	}
	
//	@Override
//	protected void onLayout(boolean changed, int left, int top, int right,
//			int bottom) {
//		
//		float mscale = canvasView.getScale();
////		left = 		(int) (left  * mscale);
//		right = 	(int) (right  * mscale);
////		top = 		(int) (top * mscale);
//		bottom = 	(int) (bottom  * mscale);
//		
//		Log.d("MyActivity","Child: Layout done. Scale: "+mscale);
//		
//		super.onLayout(true, 0, 0, right, bottom);
//	}
	
	@Override
	protected void onDraw(Canvas canvas) {
				
		/*
		 * Inside of onDraw we only can draw on a clipped part of canvas, 
		 * a result of measuring and laying out the view.
		 *  -> 
		 *  	for that start drawing on 0,0 and use getHeight() and getWidth() to get the View size.
		 *   	instead of using top, right, bottom etc.
		 *   
		 *   	If you draw a Rectange of size e.g (x,y,x+width, y+height)
		 *   	it will start drawing on x,y and step make a step to x,y again so that starting point becomes 2x,2y 
		 *   	additionally the rectangle will probably exceed its avaiable clip of canvas and disappear, since clip-overflow is hidden. 
		 * 
		 * Draw coordinates 0,0 are displayed on x,y from LayoutParams.		
		 */
		
	
		int mLeft = getLeft();
		int mTop = getTop();
		int mRight = getRight();
		int mBottom = getBottom();
		
		float mx = this.getX();
		float my = this.getY();
		
		float mtx = this.getTranslationX();
		float mty = this.getTranslationY();
		
		int mwidth = this.getWidth();
		int mheight = this.getHeight();
		
		
		Paint paint = new Paint();
		paint.setColor(Color.CYAN);
		paint.setStyle(Style.FILL);
		
		Path path = new Path();
//		path.addRect(mLeft, mTop, mRight, mBottom, Direction.CCW);
		path.addRect(0, 0, mwidth, mheight, Direction.CCW);
		path.setFillType(FillType.WINDING);
		canvas.drawPath(path, paint);
		
		Log.d("SimpleGraphicsView","onDraw: Left: "+mLeft+" , Right: "+mRight+" , Bottom"+mBottom);
		
		super.onDraw(canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*
		 * Move dragged figures, by calculating an offset between the old touch and the new touch.
		 * Subtract the offset from the figures old position.
		 * 
		 * DON'T set Figures coordinates directly to Coordinates, where event occurred, because it makes figures jump. 
		 */
		
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			this.mOldTouchX = (int)event.getRawX();
			this.mOldTouchY = (int)event.getRawY();
		}
		
		if(event.getAction() == MotionEvent.ACTION_MOVE){
			
						 //RELATIVE Coordinates (relative to THIS view, NOT ABSOLUTE)
						float ex = event.getX();
						float ey = event.getY();
			
			//ASOLUTE Coordinates  (relative to the PARENT View)
			float erawx = event.getRawX();
			float erawy = event.getRawY();

			
			//calculate Offset and update the Touch coordinates		
			float offsetx = this.mOldTouchX-erawx;
			float offsety = this.mOldTouchY-erawy;
			//and update the Touch coordinates	
			this.mOldTouchX = erawx;
			this.mOldTouchY = erawy;
			
			//calculate new Position
			int newx = (int)(this.getX()-offsetx);
			int newy = (int)(this.getY()-offsety);
			
			Log.d("MyActivity", "x: "+ex+" rawX: "+erawx);
			Log.d("MyActivity", "y: "+ey+" rawY: "+erawy);

			//change my Position from inside of this view:
			this.layout(newx, newy, newx+this.getWidth(), newy+this.getHeight());
		}
//		super.onTouchEvent(event);
		return true;
	}

}
