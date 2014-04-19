package com.binaryme.helloWebViewExtended;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
  *  Maybe I will have to change this View later. For now it is a unchanged WebView.
  */
public class WebViewCanvas extends WebView {
	
	public WebViewCanvas(Context context, AttributeSet attrs, int defStyle,
			boolean privateBrowsing) {
		super(context, attrs, defStyle, privateBrowsing);
		// TODO Auto-generated constructor stub
	}
	
	public WebViewCanvas(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public WebViewCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		setSettings();
	}
	
	public WebViewCanvas(Context context) {
		super(context);
		setSettings();
	}



	//to obtain the Scale level use getScale()
	private float mOldScale;
	
	private SimpleGraphicView child;
	
	public void addChild(SimpleGraphicView s){
		this.child=s;
	}
	
	

	
	
	/**
	 * Internal Method to call for customizing the WebView for our needs. 
	 */
	private void setSettings(){
        WebSettings webSettings = this.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setUseWebViewBackgroundForOverscrollBackground(true);
        webSettings.setEnableSmoothTransition(true);
        
        this.setVerticalScrollBarEnabled(true);
        this.setHorizontalScrollBarEnabled(true);
        this.setHorizontalScrollBarEnabled(true);
        
        //set my variables
        mOldScale = getScale();
	}
	
	/*
	 * Override zoomIn and zoomOut to know, when zoom is done, do adjust the child view's size(non-Javadoc)
	 * super in zoomIn/Out return true, when zoom succeeded
	 * TODO: how do i know to which amount zoom is done?
	 * 
	 */

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		Log.d("Touchpoint","Touch coordinates: x "+ev.getRawX() + ", y "+ev.getRawY());
		
		boolean result = super.onTouchEvent(ev);
		float newScale = getScale();
		if(mOldScale != newScale){
			mOldScale = newScale;
			Log.d("MyActivity", "Zoom Performed. Scale: "+newScale);

			//now redraw the children with new scale level
			//just invalidating this view - doesn't work!
			
			//this.invalidate(); //invalidate() redraws with existing parameters requestlayout() calculates new layout for the whole tree
			//this.requestLayout(); //calculates new layout for the whole tree, by calling layout() for every child. DOESNT SEEM TO WORK.
			
			//TEMP: layout the child
			/**
			 * Laying out the child doesn't work.
			 * Changing the Height and Width of the child depending on Scale (inside of child's onMeasure.) - helps!
			 * TODO: 
			 * 		find out, how the coordinates during the zoom operation change?
			 * 		how should the position of the rectangle be adopted, during the zoom?
			 */
			this.child.requestLayout();
			
			Log.d("MyActivity","View-Width: "+this.getWidth() + ", Height: "+getHeight());
		}
		return result;
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		ColorDrawable mDrawable = new ColorDrawable(Color.RED);		//create a new Rectangle
		mDrawable.setBounds(150, 150, 300, 300);				//set Dimensions to be as Large as Canvas
		mDrawable.draw(canvas);
		
		super.onDraw(canvas);
	}
	
	
}
