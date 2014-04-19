package com.binaryme.helloAbsoluteLayoutScrollZoom;

import java.util.ArrayList;

import android.graphics.Point;
import android.util.Log;

public class ScaleHandler {
	
	//TODO set default scale from a resource
	private static float scale = 1f, mMaxScale = 3f, mMinScale=0.5f;
	//pivot Points, scale with this points in the middle
	private static float pivotX=0,pivotY=0;

	public static synchronized float getScale() {
		return scale;
	}

	public static synchronized void setScale(float scale, Point pivot) {

		if(ScaleHandler.scale != scale){
			if( mMinScale<scale && scale<mMaxScale ){
				
				Log.d("scaleHandler", "Scale: "+scale);
				ScaleHandler.scale = scale;
				ScaleHandler.pivotX = pivot.x;
				ScaleHandler.pivotY = pivot.y;
				fireScaleEvent(scale, pivot);
			}
		}
	}

	private static ArrayList<ScaleEventListener> scaleListeners = new ArrayList<ScaleEventListener>();
	
	public static synchronized void addScaleEventListener(ScaleEventListener listener){
		ScaleHandler.scaleListeners.add(listener);
	}
	
	public static synchronized void removeScaleEventListener(ScaleEventListener listener){
		ScaleHandler.scaleListeners.remove(listener);
	}
	
	//Event is fired, if scale level is changed
	private static synchronized void fireScaleEvent(float newscale, Point pivot){
		for(ScaleEventListener s : ScaleHandler.scaleListeners){
			s.onScaleEvent(newscale, pivot);
		}
	}
	

}
