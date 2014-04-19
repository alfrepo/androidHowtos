package com.binaryme.helloEventsPassing;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

public class mHorizontalScrollView extends HorizontalScrollView {
	
	private String TAG = "MyActivity";
	
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;

	public mHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		//this can check an Event for it's X or Y component before passing it to onTouchEvent(Event) ,
		//or alternatively all Touch Events can be redirected to onTouchEvent and this check can be performed inside of the onTouchEvent(Event)
	    gestureDetector = new mGestureDetector(new XScrollDetector());
	    setFadingEdgeLength(0);
	}
	

	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		boolean result = super.onTouchEvent(ev);
		Log.d("onTouchEvent", "HorizontalScrollView : onTouchEvent. "+ ev.getAction()+ " Result is "+result );
		Log.d("Order","HorizontalScrollView : onTouchEvent. Event "+ev.getAction());
		return result;
	}

	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean sup = super.dispatchTouchEvent(ev);
		boolean result = sup; 
		Log.d(TAG, "Event "+ev.getAction() +" dispatched to HorizontalScrollView - Child. Return "+result);
		Log.d("Order","HorizontalScrollView : dispatchTouchEvent. Event "+ev.getAction());
		return result;
	}
	

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
	    //Call super first because it does some hidden motion event handling
	    super.onInterceptTouchEvent(ev);
	    
	    /*
	     *  this is the last View in the Touch Event receiving, overlapping hierarchy.
	     *  by returning true, this method signalizes, that Event "ev" is handled by the current view,
	     *  so Android will send further actions of the Event ev to "onTouchEvent(Event)" and not to onInterceptTouchEvent(Event)
	     *  
	     *  means that after returning true, TouchEvents will be redirected to this View's method onTouchEvent(Event) automatically.
	     *  and they will not be redirected to this View's method onInterceptTouchEvent anymore, or to other onInterceptTouchEvent methods
	     *  down in the hierarchy. (Views up in the hierarchy always recieve onTouchEvents inside of onInterceptTouchEvent(Event) methods.)
	     *  
	     *  an alternative would be, to return false here, so that all future events will be send here, and redirect them manually, 
	     *  like implemented in mScrollView 
	     */
	    // 
	    
	    //so
	    boolean result = true;
	    	    
	    Log.d("onInterception","HorizontalScrollView: Event "+ev.getAction());
		Log.d("Order","HorizontalScrollView : onInterception. Event "+ev.getAction() + " Return "+result);
	    return result;
	}
	

	
	// ************************************************************
	// This is how you can check an Event for it's Y or X component 
	// ************************************************************
	
	
	// Return false if we're scrolling in the x direction  
	class XScrollDetector extends SimpleOnGestureListener {
	    @Override
	    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
	        try {
	            if (Math.abs(distanceY) < Math.abs(distanceX)) {
	                return true;
	            } else {
	                return false;
	            }
	        } catch (Exception e) {
	            // nothing
	        }
	        return false;
	    }
	}
	
	// Logs received Events
	class mGestureDetector extends GestureDetector{
		public mGestureDetector(OnGestureListener listener) {
			super(listener);
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent ev) {
			boolean result = super.onTouchEvent(ev);
			//allways handle events
			result = true;
			Log.d("mGestureDetector","HorizontalScrollView: mGestureDetector:onTouchEvent :  receives Event "+ev.getAction() + " Return "+result);
			Log.d("Order","HorizontalScrollView : onTouchEvent. Event "+ev.getAction() +" Return "+result);
			return result;	
		}		
	}
	
}