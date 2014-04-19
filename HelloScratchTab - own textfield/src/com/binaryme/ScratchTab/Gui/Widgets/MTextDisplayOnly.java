package com.binaryme.ScratchTab.Gui.Widgets;

import android.content.Context;
import android.util.AttributeSet;


/** Class to display some text, which should not be editable by the user. 
 *  Instances of this class do not position themselves, instead they are dropped into Widgets, which handle positioning and drop events.  
 *  Here all the settings and drawing, which differ from android's native implementation of the spinner should be done */
public class MTextDisplayOnly extends MTextField {

	public MTextDisplayOnly(Context context) {
		super(context);
	}
	
	public MTextDisplayOnly(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public MTextDisplayOnly(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
}
