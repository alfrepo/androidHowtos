package com.binaryme.helloEventsPassing;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class mScrollView extends ScrollView {

	private String TAG = "MyActivity";

	public mScrollView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    setFadingEdgeLength(0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		boolean result = super.onTouchEvent(ev);
		Log.d("onTouchEvent", "ScrollView - MotionEvent:Action "+ ev.getAction()+ " Result is "+result );
		Log.d("Order","ScrollView           : onTouchEvent. Event "+ev.getAction());
	    return result;
	}

	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
	    //Call super first because it does some hidden motion event handling
	    super.onInterceptTouchEvent(ev);
	    boolean result = false;
	    
	    /*
	     * manually redirect Event to onTouchEvent. We could do the redirection, by returning true too,
	     * but then this event wont be redirected to/will be canceled for the other Views' onTouchEvent, 
	     * and I want other Views to handle these events too.
	     * 
	     * Filtering can be done here, by using a GestureDetector.
	     * ScrollView's onTouchEvent needs following actions : 
	     *  MotionEvent.ACTION_MOVE
	     *  
	     *  
	     */
	    this.onTouchEvent(ev);
	    
	    Log.d("onInterception","ScrollView: Event"+ev.getAction());
	    Log.d("Order","ScrollView           : onInterception. Event "+ev.getAction() + " Return "+result);
	    return result;
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean sup = super.dispatchTouchEvent(ev);
		boolean result = sup;
		
		Log.d(TAG, "Event "+ev.getAction() +" dispatched to ScrollView - Root. Return "+result);
		Log.d("Order","ScrollView           : dispatchTouchEvent. Event "+ev.getAction());
		return result;
	}
	
}
