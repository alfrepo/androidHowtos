package com.binaryme.helloDualScrollable;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

public class SnappyHorizontalScrollView extends HorizontalScrollView {
	
	String TAG = "MyActivity";
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;

	
	public SnappyHorizontalScrollView(Context context){
	    super(context);
	    setOnTouchListener(mOnTouchListener);
	    gestureDetector = new GestureDetector(new YScrollDetector());
	    setFadingEdgeLength(0);
	}
	
	public SnappyHorizontalScrollView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    setOnTouchListener(mOnTouchListener);
	    gestureDetector = new GestureDetector(new YScrollDetector());
	    setFadingEdgeLength(0);
	}
	
	public SnappyHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	    setOnTouchListener(mOnTouchListener);
	    gestureDetector = new GestureDetector(new YScrollDetector());
	    setFadingEdgeLength(0);
	}
	
	private OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ){
                int scrollX = getScrollX();
                	Log.d(TAG, "scrollX : "+scrollX );
                	
                // ViewPortWidth
                int itemWidth = getMeasuredWidth();
                	Log.d(TAG, "itemWidth : "+itemWidth );
                	
                // scroll X coordinate, divided by ViewPortWidth without rest
                int activeItem = ((scrollX + itemWidth / 2) / itemWidth); //rounding, if the View-width does not divide the Viewport-width
                	Log.d(TAG, "activeItem : "+activeItem );
                	
                // how many ViewPortWidths to scroll
                int scrollTo = activeItem * itemWidth;
            		Log.d(TAG, "scrollTo : "+scrollTo );
                smoothScrollTo(scrollTo, 0);

                return true;
            } else {
                return false;
            }
        }
    };
    
    //Snapping functionality

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //Call super first because it does some hidden motion event handling
        boolean result = super.onInterceptTouchEvent(ev);
        //Now see if we are scrolling vertically with the custom gesture detector
        if (gestureDetector.onTouchEvent(ev)) {
            return result;
        } 
        //If not scrolling vertically (more y than x), don't hijack the event.
        else {
            return false;
        }
    }

    // Return false if we're scrolling in the x direction  
    class YScrollDetector extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            try {
                if (Math.abs(distanceY) > Math.abs(distanceX)) {
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

}
