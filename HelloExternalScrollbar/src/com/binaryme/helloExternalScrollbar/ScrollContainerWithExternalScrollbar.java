package com.binaryme.helloExternalScrollbar;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.HorizontalScrollView;

public class ScrollContainerWithExternalScrollbar extends HorizontalScrollView {
	
	private ExternalScrollbar eScroller;

	public ScrollContainerWithExternalScrollbar(Context context) {
		super(context);
		init();
	}
	public ScrollContainerWithExternalScrollbar(Context context,
			AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public ScrollContainerWithExternalScrollbar(Context context,
			AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	void init(){
		this.setScrollbarFadingEnabled(false);
	}
	
	/**
	 * This Method must be called from the Activity in order to add an External scroll bar to this View
	 * @param e
	 */
	public void setExternalScrollbar(ExternalScrollbar e){
		this.eScroller = e;
	}
	
	@Override
	protected int computeHorizontalScrollRange() {
		int sup = super.computeHorizontalScrollRange();
		 
		 //Computes the full scroll range which is equal to the sum of container's content width.
		 //This method will set the external scroll bar width (by setting external dummy's width) 
		if(this.eScroller!=null ){
			//call child's computeHorizontalScrollRange
			eScroller.computeHorizontalScrollRange(sup);
		}

		 Log.d("Scroller", "computeHorizontalScrollRange: "+sup);
		 return sup;
	}
	
	
	@Override
	protected int computeHorizontalScrollOffset() {
		int sup = super.computeHorizontalScrollOffset();
		 if(this.eScroller!=null){
			 this.eScroller.scrollTo(sup, 0);
		 }
		 return sup;
	}
}

