package com.binaryme.blockStackStructure;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class BlockIf extends Block implements InterfaceIf {

	private RelativeLayout slotBottom;
	
	public BlockIf(Context context) {
		super(context);
		Log.d("MyActivity", "Block ID: "+this.getId());
	}

	@Override
	protected void initmVisualRepresentation() {
		this.mVisualRepresentation = new VisualRepresentationIf(getContext());		
	}

	@Override
	public void addToSlotCondition(Block newblock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToSlotBottom(Block newblock) {
		//remember the new Block
		this.slotBottom = newblock;
						
		newblock.measure(	MeasureSpec.makeMeasureSpec(9999, MeasureSpec.UNSPECIFIED), 
        						MeasureSpec.makeMeasureSpec(9999, MeasureSpec.UNSPECIFIED) );
        int measuredHeight =  newblock.getMeasuredHeight();
       	int measuredWidth  =  newblock.getMeasuredWidth();

       	
		//layout the new Block
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(measuredWidth, measuredHeight);
		layoutParams.addRule(RelativeLayout.BELOW,this.getmVisualRepresentation().getId());		//POSITIONING THE BLOCK FOR THIS SPECIAL SLOT
		
       	this.addView(newblock, layoutParams);
		
	}
	
	
	

}
