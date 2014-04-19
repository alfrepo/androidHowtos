package com.binaryme.blockStackStructure;

import android.content.Context;
import android.util.Log;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;

public class BlockForever extends Block implements InterfaceIf {

	private RelativeLayout slotBottom;
	
	public BlockForever(Context context) {
		super(context);
		Log.d("MyActivity", "Block ID: "+this.getId());
	}

	@Override
	protected void initmVisualRepresentation() {
		this.mVisualRepresentation = new VisualRepresentationForever(getContext());		
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
		layoutParams.addRule(RelativeLayout.BELOW, this.mVisualRepresentation.getId());//POSITIONING THE BLOCK FOR THIS SPECIAL SLOT
		
       	this.addView(newblock, layoutParams);
		
	}
	
	
	

}
