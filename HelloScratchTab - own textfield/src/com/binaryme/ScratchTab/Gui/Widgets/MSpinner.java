package com.binaryme.ScratchTab.Gui.Widgets;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.widget.Spinner;

import com.binaryme.LayoutZoomable.ScaleEventListener;
import com.binaryme.ScratchTab.Config.AppRessources;


/** Class to represent a dropdown.
 *  Instances of this class do not position themselves, instead they are embedded into Dummies which are embedded into Slots. Slots are the objects, where positioning and drop events are handled.  
 *  Instances of this class contain the settings and drawing, which differ from android's native implementation of the spinner should be done 
 *  */
public class MSpinner extends Spinner implements  ScaleEventListener{
	
	public MSpinner(Context context) {
		super(context);
	}
	
	public MSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public MSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MSpinner(Activity contextActivity, ArrayList<String> mSpinnerContentArray) {
		this(AppRessources.context.getApplicationContext());
	}

	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(0, 0);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
	}

	@Override
	public void onScaleEvent(float newscale, Point pivot) {
	}
	
	public void setAdapter(ArrayList<String> spinnerArray) {
	}

	public String getValue() {
		return null;
	}


}