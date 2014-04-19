package com.binaryme.ScratchTab.Gui.Widgets;

import android.content.Context;
import android.util.AttributeSet;

/** Class to represent an NumField.
 *  Instances of this class do not position themselves, instead they are dropped into Widgets, which handle positioning and drop events.  
 *  Here all the settings and drawing, which differ from android's native implementation of the spinner should be done */
public class MNumField extends MTextField {
	
	public MNumField(Context context) {
		super(context);
	}
	
	public MNumField(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public MNumField(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public Double getValueAsDouble() {
		return null;
	}

}
