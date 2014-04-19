package com.example.helloandroid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class MHorizontalScrollView extends HorizontalScrollView {

	public MHorizontalScrollView(Context context) {
		super(context);
		init();
	}
	public MHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	    init();
	}
	public MHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	void init(){
	}

}
