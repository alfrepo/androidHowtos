package com.binaryme.helloEventsPassing;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MButton extends Button {

	public MButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public MButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public MButton(Context context) {
		super(context);
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		//boolean sup = super.dispatchTouchEvent(ev);
		boolean result = true;
		Log.d("Order","MButton              : "+ev.getEventTime()+" dispatchTouchEvent. Event "+ev.getAction()+ "Return "+result);
		
		//call the onTouch manually for the Button to react on it.
		onTouchEvent(ev);
		
		/*
		 * change the event, 
		 * so that no View above this in the tree hierarchy will react on this event anymore because it is handled now.
		 * No reaction on MotionEvent -1 is implemented manually in the Views, above in the view Hierarchy
		 * (here that are mhorizontalScrollView and mScrollView)
		 */
//		ev.setAction(-1);
		
		return result;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		boolean sup = super.onTouchEvent(ev);
		boolean result = true;
		
		this.setText("onTouchEVENT "+ev.getEventTime());
		
		Log.d("Order","MButton              : "+ev.getEventTime()+" onTouchEvent. Event "+ev.getAction() +" Return "+result);
		return result;
	}

}
