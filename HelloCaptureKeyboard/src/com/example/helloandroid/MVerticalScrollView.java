package com.example.helloandroid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;


public class MVerticalScrollView extends ScrollView {
	public MVerticalScrollView(Context context) {
		super(context);
		init();
	}
	public MVerticalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	    init();
	}
	public MVerticalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	void init(){
	}

}
